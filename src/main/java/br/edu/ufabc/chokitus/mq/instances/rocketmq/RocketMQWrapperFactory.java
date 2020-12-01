package br.edu.ufabc.chokitus.mq.instances.rocketmq;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import org.apache.rocketmq.common.message.Message;

import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;

public class RocketMQWrapperFactory extends AbstractWrapperFactory<RocketMQReceiver, RocketMQProducer, RocketMQMessage, RocketMQClientFactory> {

	public RocketMQWrapperFactory(final ConfigurationProperties properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected RocketMQClientFactory createClientFactory(final ConfigurationProperties clientFactoryProperties) throws Exception {
		return new RocketMQClientFactory(clientFactoryProperties);
	}

	@Override
	protected void startConsumerImpl(final RocketMQReceiver client, final ConfigurationProperties clientStartProperties,
	                                 final RocketMQClientFactory clientFactory) throws Exception {
		client.start(clientStartProperties);
	}

	@Override
	protected void startProducerImpl(final RocketMQProducer client, final ConfigurationProperties clientStartProperties,
			final RocketMQClientFactory clientFactory) throws Exception {
		client.start(clientStartProperties);
	}

	@Override
	protected RocketMQMessage createMessageForProducerImpl(final byte[] body, final String destination, final RocketMQProducer producer,
			final ConfigurationProperties messageProperties, final RocketMQClientFactory clientFactory) throws Exception {
		final Message message = new Message(destination, body);
		return new RocketMQMessage(message , destination, messageProperties);
	}

}
