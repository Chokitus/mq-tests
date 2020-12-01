package br.edu.ufabc.chokitus.mq.instances.kafka.blocking.client;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.client.AbstractReceiver;
import br.edu.ufabc.chokitus.mq.instances.kafka.blocking.message.KafkaMessage;
import br.edu.ufabc.chokitus.mq.instances.kafka.blocking.config.KafkaProperty;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

public class KafkaReceiver extends AbstractReceiver<org.apache.kafka.clients.consumer.KafkaConsumer<String, byte[]>, KafkaMessage> {

	public KafkaReceiver(final org.apache.kafka.clients.consumer.KafkaConsumer<String, byte[]> client,
	                     final ConfigurationProperties properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		client.close();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void startImpl(final ConfigurationProperties properties) throws Exception {
		client.subscribe(properties.<Collection<String>>getProp(KafkaProperty.TOPIC_LIST.getValue()));
	}

	@Override
	protected KafkaMessage instantiateEmptyMessage() {
		return new KafkaMessage(null, null, null);
	}

	@Override
	protected KafkaMessage consumeImpl(final String property) throws Exception {
		final var iterator = client.poll(Duration.of(1, ChronoUnit.SECONDS)).records(property).iterator();
		client.commitSync();

		return iterator.hasNext() ? this.empty : new KafkaMessage(iterator.next().value(), property, null);
	}

}
