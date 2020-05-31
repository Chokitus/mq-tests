package br.edu.ufabc.chokitus.mq.instances.rocketmq;

import java.util.Map;

import org.apache.rocketmq.common.message.Message;

import br.edu.ufabc.mq.exception.MessagingException;
import br.edu.ufabc.mq.factory.AbstractWrapperFactory;

public class RocketMQWrapperFactory extends AbstractWrapperFactory<RocketMQConsumer, RocketMQProducer, RocketMQMessage, RocketMQClientFactory> {

	public RocketMQWrapperFactory(final Map<String, Object> properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected RocketMQClientFactory createClientFactory(final Map<String, Object> clientFactoryProperties) throws Exception {
		return new RocketMQClientFactory(clientFactoryProperties);
	}

	@Override
	protected void startConsumerImpl(final RocketMQConsumer client, final Map<String, Object> clientStartProperties,
			final RocketMQClientFactory clientFactory) throws Exception {
		client.start(clientStartProperties);
	}

	@Override
	protected void startProducerImpl(final RocketMQProducer client, final Map<String, Object> clientStartProperties,
			final RocketMQClientFactory clientFactory) throws Exception {
		client.start(clientStartProperties);
	}

	@Override
	protected RocketMQMessage createMessageForProducerImpl(final byte[] body, final String destination, final RocketMQProducer producer,
			final Map<String, Object> messageProperties, final RocketMQClientFactory clientFactory) throws Exception {
		final Message message = new Message(destination, body);
		return new RocketMQMessage(message , destination, messageProperties);
	}

}
