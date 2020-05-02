package br.edu.ufabc.mq.benchmark.instances.kafka;

import java.util.Arrays;
import java.util.Map;

import br.edu.ufabc.mq.exception.MessagingException;
import br.edu.ufabc.mq.factory.AbstractWrapperFactory;

public class KafkaWrapperFactory extends AbstractWrapperFactory<KafkaConsumer, KafkaProducer, KafkaMessage, KafkaClientFactory> {

	private static final String COMMA = ",";

	public KafkaWrapperFactory(final Map<String, Object> properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected KafkaClientFactory createClientFactory(final Map<String, Object> clientFactoryProperties) throws Exception {
		return new KafkaClientFactory(clientFactoryProperties);
	}

	@Override
	protected void startConsumerImpl(final KafkaConsumer client, final Map<String, Object> clientStartProperties,
			final KafkaClientFactory clientFactory) throws Exception {
		final String commaSeparatedTopicList = (String) clientStartProperties.get(KafkaProperty.TOPIC_LIST.getValue());

		client.getClient().subscribe(Arrays.asList(commaSeparatedTopicList.split(COMMA)));
	}

	@Override
	protected void startProducerImpl(final KafkaProducer client, final Map<String, Object> clientStartProperties,
			final KafkaClientFactory clientFactory) throws Exception {
		// Unnecessary
	}

	@Override
	protected KafkaMessage createMessageForProducerImpl(final byte[] body, final String destination, final KafkaProducer producer,
			final Map<String, Object> messageProperties, final KafkaClientFactory clientFactory) throws Exception {
		return new KafkaMessage(body, destination, messageProperties);
	}

}
