package br.edu.ufabc.chokitus.mq.instances.kafka.blocking.factory;

import java.util.Arrays;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.exception.MessagingException;
import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;
import br.edu.ufabc.chokitus.mq.instances.kafka.blocking.message.KafkaMessage;
import br.edu.ufabc.chokitus.mq.instances.kafka.blocking.config.KafkaProperty;
import br.edu.ufabc.chokitus.mq.instances.kafka.blocking.client.KafkaProducer;
import br.edu.ufabc.chokitus.mq.instances.kafka.blocking.client.KafkaReceiver;

public class KafkaWrapperFactory extends AbstractWrapperFactory<KafkaReceiver, KafkaProducer, KafkaMessage, KafkaClientFactory> {


	public KafkaWrapperFactory(final ConfigurationProperties properties) throws MessagingException {
		super(properties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected KafkaClientFactory createClientFactory(final ConfigurationProperties clientFactoryProperties) throws Exception {
		return new KafkaClientFactory(clientFactoryProperties);
	}

	@Override
	protected void startConsumerImpl(final KafkaReceiver client, final ConfigurationProperties clientStartProperties,
	                                 final KafkaClientFactory clientFactory) throws Exception {
		final String commaSeparatedTopicList = (String) clientStartProperties.get(KafkaProperty.TOPIC_LIST.getValue());
		client.getClient().subscribe(Arrays.asList(commaSeparatedTopicList.split(",")));
	}

	@Override
	protected void startProducerImpl(final KafkaProducer client, final ConfigurationProperties clientStartProperties,
			final KafkaClientFactory clientFactory) throws Exception {
		// Unnecessary
	}

	@Override
	protected KafkaMessage createMessageForProducerImpl(final byte[] body, final String destination, final KafkaProducer producer,
			final ConfigurationProperties messageProperties, final KafkaClientFactory clientFactory) throws Exception {
		return new KafkaMessage(body, destination, messageProperties);
	}

}
