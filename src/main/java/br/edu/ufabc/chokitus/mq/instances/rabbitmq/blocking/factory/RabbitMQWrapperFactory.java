package br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.factory;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.client.RabbitMQClient;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.message.RabbitMQMessage;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.client.RabbitMQProducer;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.config.RabbitMQProperty;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.client.RabbitMQReceiver;
import com.rabbitmq.client.ConnectionFactory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.util.Map;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RabbitMQWrapperFactory extends AbstractWrapperFactory<RabbitMQReceiver, RabbitMQProducer, RabbitMQMessage, RabbitMQClientFactory> {

	public RabbitMQWrapperFactory(final ConfigurationProperties properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected RabbitMQClientFactory createClientFactory(final ConfigurationProperties clientFactoryProperties) throws Exception {
		final ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(properties.getProp(RabbitMQProperty.HOST.getValue()));
		factory.setPort(properties.getAsInteger(RabbitMQProperty.PORT.getValue()));
		factory.setUsername(properties.getProp(RabbitMQProperty.USERNAME.getValue()));
		factory.setPassword(properties.getProp(RabbitMQProperty.PASSWORD.getValue()));
		factory.setVirtualHost("dev_local");

		return new RabbitMQClientFactory(clientFactoryProperties, factory);
	}

	@Override
	protected void startConsumerImpl(final RabbitMQReceiver client,
	                                 final ConfigurationProperties clientStartProperties,
	                                 final RabbitMQClientFactory clientFactory) throws Exception {
		initializeQueue(client, clientStartProperties);
	}

	@Override
	protected void startProducerImpl(final RabbitMQProducer client,
	                                 final ConfigurationProperties clientStartProperties,
	                                 final RabbitMQClientFactory clientFactory) throws Exception {
		initializeQueue(client, clientStartProperties);
	}

	@Override
	protected RabbitMQMessage createMessageForProducerImpl(final byte[] body,
	                                                       final String destination,
	                                                       final RabbitMQProducer producer,
	                                                       final ConfigurationProperties messageProperties,
	                                                       final RabbitMQClientFactory clientFactory) throws Exception {
		return getMessage(body, destination, messageProperties);
	}

	private RabbitMQMessage getMessage(final byte[] body,
	                                   final String destination,
	                                   final ConfigurationProperties messageProperties) {
		return new RabbitMQMessage(body, destination, messageProperties);
	}

	private void initializeQueue(final RabbitMQClient client,
	                             final ConfigurationProperties clientStartProperties) throws IOException {
		final String queueName = clientStartProperties.getPropOrDefault(RabbitMQProperty.QUEUE_PROPERTY.getValue(), "");
		final boolean durable = clientStartProperties.getBooleanPropOrDefault(RabbitMQProperty.DURABLE_PROPERTY.getValue(),
		                                                                      true);
		final boolean exclusive =
						clientStartProperties.getBooleanPropOrDefault(RabbitMQProperty.EXCLUSIVE_PROPERTY.getValue(),
		                                                                        false);
		final boolean autoDelete =
						clientStartProperties.getBooleanPropOrDefault(RabbitMQProperty.AUTO_DELETE_PROPERTY.getValue(),
		                                                                         false);
		final Map<String, Object> arguments =
						clientStartProperties.getPropOrDefault(RabbitMQProperty.ARGUMENTS_PROPERTY.getValue(),
		                                                                             null);

		client.getChannel().queueDeclare(queueName, durable, exclusive, autoDelete, arguments);
	}

}
