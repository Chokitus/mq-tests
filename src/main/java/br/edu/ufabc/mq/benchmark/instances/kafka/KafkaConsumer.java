package br.edu.ufabc.mq.benchmark.instances.kafka;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.edu.ufabc.mq.client.AbstractConsumer;

public class KafkaConsumer
		extends AbstractConsumer<org.apache.kafka.clients.consumer.KafkaConsumer<String, byte[]>, KafkaMessage> {

	public KafkaConsumer(final org.apache.kafka.clients.consumer.KafkaConsumer<String, byte[]> client,
			final Map<String, Object> properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		client.close();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void startImpl(final Map<String, Object> properties) throws Exception {
		client.subscribe((List<String>) properties.get(KafkaProperty.TOPIC_LIST.getValue()));
	}

	@Override
	protected KafkaMessage consumeImpl(final String property) throws Exception {
		final Iterator<ConsumerRecord<String, byte[]>> iterator = client.poll(Duration.of(1, ChronoUnit.SECONDS))
																		.records(property)
																		.iterator();
		return new KafkaMessage(iterator.hasNext() ? iterator.next().value() : new byte[0], property, null);
	}

}
