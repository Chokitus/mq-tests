package br.edu.ufabc.chokitus.mq.instances.ironmq;

import java.util.Map;

import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;

public class IronMQWrapperFactory extends AbstractWrapperFactory<IronMQConsumer, IronMQProducer, IronMQMessage, IronMQClientFactory> {

	public IronMQWrapperFactory(final Map<String, Object> properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected IronMQClientFactory createClientFactory(final Map<String, Object> clientFactoryProperties) throws Exception {
		return new IronMQClientFactory(clientFactoryProperties);
	}

	@Override
	protected void startConsumerImpl(final IronMQConsumer client, final Map<String, Object> clientStartProperties,
			final IronMQClientFactory clientFactory) throws Exception {
		// Unnecessary
	}

	@Override
	protected void startProducerImpl(final IronMQProducer client, final Map<String, Object> clientStartProperties,
			final IronMQClientFactory clientFactory) throws Exception {
		// Unnecessary
	}

	@Override
	protected IronMQMessage createMessageForProducerImpl(final byte[] body, final String destination, final IronMQProducer producer,
			final Map<String, Object> messageProperties, final IronMQClientFactory clientFactory) throws Exception {
		return new IronMQMessage(body, destination, messageProperties);
	}

}
