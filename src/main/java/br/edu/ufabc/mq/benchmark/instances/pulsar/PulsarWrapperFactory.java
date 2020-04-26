package br.edu.ufabc.mq.benchmark.instances.pulsar;

import java.util.Map;

import org.apache.pulsar.client.api.PulsarClient;

import br.edu.ufabc.mq.exception.MessagingException;
import br.edu.ufabc.mq.factory.AbstractWrapperFactory;

public class PulsarWrapperFactory
		extends AbstractWrapperFactory<PulsarConsumer, PulsarProducer, PulsarMessage, PulsarClientFactory> {

	/**
	 * Default constructor for <b>Pulsar</b>. It requires, on the property map, the
	 * following information:
	 *
	 * <ul>
	 * <li>{@link PulsarProperty#SERVICE_URL} -- pulsar://localhost:6650</li>
	 * </ul>
	 *
	 * @param properties
	 * @throws MessagingException
	 * @see AbstractWrapperFactory
	 */
	public PulsarWrapperFactory(final Map<String, Object> properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected PulsarClientFactory createClientFactory(final Map<String, Object> clientFactoryProperties)
			throws Exception {
		final PulsarClient pulsarClient = PulsarClient.builder()
													  .serviceUrl((String) clientFactoryProperties.get(PulsarProperty.SERVICE_URL.getValue()))
													  .build();
		return new PulsarClientFactory(clientFactoryProperties, pulsarClient);
	}

	@Override
	protected void startConsumerImpl(final PulsarConsumer client, final Map<String, Object> clientStartProperties,
			final PulsarClientFactory clientFactory) throws Exception {
		// Unnecessary
	}

	@Override
	protected void startProducerImpl(final PulsarProducer client, final Map<String, Object> clientStartProperties,
			final PulsarClientFactory clientFactory) throws Exception {
		// Unnecessary
	}

	@Override
	protected PulsarMessage createMessageForProducerImpl(final byte[] body, final String destination,
			final PulsarProducer producer, final Map<String, Object> messageProperties,
			final PulsarClientFactory clientFactory) throws Exception {
		return createDefaultMessage(body, destination, messageProperties);
	}

	@Override
	protected PulsarMessage createMessageForConsumerImpl(final byte[] body, final String destination,
			final PulsarConsumer consumer, final Map<String, Object> messageProperties,
			final PulsarClientFactory clientFactory) throws Exception {
		return createDefaultMessage(body, destination, messageProperties);
	}

	private PulsarMessage createDefaultMessage(final byte[] body, final String destination,
			final Map<String, Object> messageProperties) {
		return new PulsarMessage(body, destination, messageProperties);
	}

}
