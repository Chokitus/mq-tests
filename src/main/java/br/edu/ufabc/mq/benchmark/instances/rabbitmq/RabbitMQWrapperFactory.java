package br.edu.ufabc.mq.benchmark.instances.rabbitmq;

import java.util.Map;

import java.io.IOException;

import com.rabbitmq.client.ConnectionFactory;

import br.edu.ufabc.mq.exception.MessagingException;
import br.edu.ufabc.mq.factory.AbstractWrapperFactory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RabbitMQWrapperFactory
extends AbstractWrapperFactory<RabbitMQConsumer, RabbitMQProducer, RabbitMQMessage, RabbitMQClientFactory> {

	public RabbitMQWrapperFactory(final Map<String, Object> properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected void closeImpl() throws Exception {
		clientFactory.close();
	}

	@Override
	protected RabbitMQClientFactory createClientFactory(final Map<String, Object> clientFactoryProperties) throws Exception {
		final ConnectionFactory factory = new ConnectionFactory();
		factory.setHost((String) properties.get(RabbitMQProperty.HOST.getValue()));
		factory.setPort((int) properties.get(RabbitMQProperty.PORT.getValue()));
		return new RabbitMQClientFactory(clientFactoryProperties, factory);
	}

	@Override
	protected void startConsumerImpl(final RabbitMQConsumer client, final Map<String, Object> clientStartProperties,
			final RabbitMQClientFactory clientFactory) throws Exception {
		initializeQueue(client, clientStartProperties);
	}

	@Override
	protected void startProducerImpl(final RabbitMQProducer client, final Map<String, Object> clientStartProperties,
			final RabbitMQClientFactory clientFactory) throws Exception {
		initializeQueue(client, clientStartProperties);
	}

	@Override
	protected RabbitMQMessage createMessageForProducerImpl(final byte[] body, final String destination, final RabbitMQProducer producer,
			final Map<String, Object> messageProperties, final RabbitMQClientFactory clientFactory) throws Exception {
		return getMessage(body, destination, messageProperties);
	}

	@Override
	protected RabbitMQMessage createMessageForConsumerImpl(final byte[] body, final String destination, final RabbitMQConsumer consumer,
			final Map<String, Object> messageProperties, final RabbitMQClientFactory clientFactory) throws Exception {
		return getMessage(body, destination, messageProperties);
	}

	private RabbitMQMessage getMessage(final byte[] body, final String destination, final Map<String, Object> messageProperties) {
		return new RabbitMQMessage(body, destination, messageProperties);
	}

	@SuppressWarnings("unchecked")
	private void initializeQueue(final RabbitMQClient client, final Map<String, Object> clientStartProperties) throws IOException {
		client.getChannel().queueDeclare(
				(String) clientStartProperties.getOrDefault(RabbitMQProperty.QUEUE_PROPERTY.getValue(), ""),
				(Boolean) clientStartProperties.getOrDefault(RabbitMQProperty.DURABLE_PROPERTY.getValue(), true),
				(Boolean) clientStartProperties.getOrDefault(RabbitMQProperty.EXCLUSIVE_PROPERTY.getValue(), false),
				(Boolean) clientStartProperties.getOrDefault(RabbitMQProperty.AUTO_DELETE_PROPERTY.getValue(), false),
				(Map<String, Object>) clientStartProperties.getOrDefault(RabbitMQProperty.ARGUMENTS_PROPERTY.getValue(), null));
	}

}
