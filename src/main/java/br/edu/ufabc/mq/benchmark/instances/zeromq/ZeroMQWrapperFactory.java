package br.edu.ufabc.mq.benchmark.instances.zeromq;

import java.util.Map;

import br.edu.ufabc.mq.exception.MessagingException;
import br.edu.ufabc.mq.factory.AbstractWrapperFactory;

public class ZeroMQWrapperFactory
		extends AbstractWrapperFactory<ZeroMQConsumer, ZeroMQProducer, ZeroMQMessage, ZeroMQClientFactory> {

	public ZeroMQWrapperFactory(final Map<String, Object> properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected ZeroMQClientFactory createClientFactory(final Map<String, Object> clientFactoryProperties)
			throws Exception {
		return null;
	}

	@Override
	protected void startConsumerImpl(final ZeroMQConsumer client, final Map<String, Object> clientStartProperties,
			final ZeroMQClientFactory clientFactory) throws Exception {
		client.start(clientStartProperties);
	}

	@Override
	protected void startProducerImpl(final ZeroMQProducer client, final Map<String, Object> clientStartProperties,
			final ZeroMQClientFactory clientFactory) throws Exception {
		client.start(clientStartProperties);
	}

	@Override
	protected ZeroMQMessage createMessageForProducerImpl(final byte[] body, final String destination,
			final ZeroMQProducer producer, final Map<String, Object> messageProperties,
			final ZeroMQClientFactory clientFactory) throws Exception {
		return new ZeroMQMessage(body, destination, messageProperties);
	}

	@Override
	protected ZeroMQMessage createMessageForConsumerImpl(final byte[] body, final String destination,
			final ZeroMQConsumer consumer, final Map<String, Object> messageProperties,
			final ZeroMQClientFactory clientFactory) throws Exception {
		return new ZeroMQMessage(body, destination, messageProperties);
	}

}
