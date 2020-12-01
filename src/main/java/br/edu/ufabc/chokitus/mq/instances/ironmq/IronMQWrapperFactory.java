package br.edu.ufabc.chokitus.mq.instances.ironmq;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;

public class IronMQWrapperFactory extends AbstractWrapperFactory<IronMQReceiver, IronMQProducer, IronMQMessage, IronMQClientFactory> {

	public IronMQWrapperFactory(final ConfigurationProperties properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected IronMQClientFactory createClientFactory(final ConfigurationProperties clientFactoryProperties) throws Exception {
		return new IronMQClientFactory(clientFactoryProperties);
	}

	@Override
	protected void startConsumerImpl(final IronMQReceiver client, final ConfigurationProperties clientStartProperties,
	                                 final IronMQClientFactory clientFactory) throws Exception {
		// Unnecessary
	}

	@Override
	protected void startProducerImpl(final IronMQProducer client, final ConfigurationProperties clientStartProperties,
			final IronMQClientFactory clientFactory) throws Exception {
		// Unnecessary
	}

	@Override
	protected IronMQMessage createMessageForProducerImpl(final byte[] body, final String destination, final IronMQProducer producer,
			final ConfigurationProperties messageProperties, final IronMQClientFactory clientFactory) throws Exception {
		return new IronMQMessage(body, destination, messageProperties);
	}

}
