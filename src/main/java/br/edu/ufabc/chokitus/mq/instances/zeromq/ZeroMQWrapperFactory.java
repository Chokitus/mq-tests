package br.edu.ufabc.chokitus.mq.instances.zeromq;

import java.util.Map;

import org.zeromq.ZContext;

import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;

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
		return new ZeroMQClientFactory(clientFactoryProperties, new ZContext());
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
}
