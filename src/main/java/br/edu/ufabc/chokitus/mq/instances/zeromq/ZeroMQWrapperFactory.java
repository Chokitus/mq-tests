package br.edu.ufabc.chokitus.mq.instances.zeromq;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import org.zeromq.ZContext;

import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;

public class ZeroMQWrapperFactory
		extends AbstractWrapperFactory<ZeroMQReceiver, ZeroMQProducer, ZeroMQMessage, ZeroMQClientFactory> {

	public ZeroMQWrapperFactory(final ConfigurationProperties properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected ZeroMQClientFactory createClientFactory(final ConfigurationProperties clientFactoryProperties)
			throws Exception {
		return new ZeroMQClientFactory(clientFactoryProperties, new ZContext());
	}

	@Override
	protected void startConsumerImpl(final ZeroMQReceiver client, final ConfigurationProperties clientStartProperties,
	                                 final ZeroMQClientFactory clientFactory) throws Exception {
		client.start(clientStartProperties);
	}

	@Override
	protected void startProducerImpl(final ZeroMQProducer client, final ConfigurationProperties clientStartProperties,
			final ZeroMQClientFactory clientFactory) throws Exception {
		client.start(clientStartProperties);
	}

	@Override
	protected ZeroMQMessage createMessageForProducerImpl(final byte[] body, final String destination,
			final ZeroMQProducer producer, final ConfigurationProperties messageProperties,
			final ZeroMQClientFactory clientFactory) throws Exception {
		return new ZeroMQMessage(body, destination, messageProperties);
	}
}
