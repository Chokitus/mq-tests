package br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.factory;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.message.PulsarMessage;
import br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.config.PulsarProperty;
import br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.client.PulsarProducer;
import br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.client.PulsarReceiver;
import org.apache.pulsar.client.api.PulsarClient;

import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;

public class PulsarWrapperFactory
		extends AbstractWrapperFactory<PulsarReceiver, PulsarProducer, PulsarMessage, PulsarClientFactory> {

	/**
	 * Default constructor for <b>Pulsar</b>. It requires, on the property map, the
	 * following information:
	 *
	 * <ul>
	 * <li>{@link br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.config.PulsarProperty#SERVICE_URL} -- pulsar://localhost:6650</li>
	 * </ul>
	 *
	 * @param properties
	 * @throws MessagingException
	 * @see AbstractWrapperFactory
	 */
	public PulsarWrapperFactory(final ConfigurationProperties properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected PulsarClientFactory createClientFactory(final ConfigurationProperties clientFactoryProperties)
			throws Exception {
		final PulsarClient pulsarClient = PulsarClient.builder()
													  .serviceUrl((String) clientFactoryProperties.get(PulsarProperty.SERVICE_URL.getValue()))
													  .build();
		return new PulsarClientFactory(clientFactoryProperties, pulsarClient);
	}

	@Override
	protected void startConsumerImpl(final PulsarReceiver client, final ConfigurationProperties clientStartProperties,
	                                 final PulsarClientFactory clientFactory) throws Exception {
		// Unnecessary
	}

	@Override
	protected void startProducerImpl(final PulsarProducer client, final ConfigurationProperties clientStartProperties,
			final PulsarClientFactory clientFactory) throws Exception {
		// Unnecessary
	}

	@Override
	protected PulsarMessage createMessageForProducerImpl(final byte[] body, final String destination,
			final PulsarProducer producer, final ConfigurationProperties messageProperties,
			final PulsarClientFactory clientFactory) throws Exception {
		return createDefaultMessage(body, destination, messageProperties);
	}

	private PulsarMessage createDefaultMessage(final byte[] body, final String destination,
			final ConfigurationProperties messageProperties) {
		return new PulsarMessage(body, destination, messageProperties);
	}

}
