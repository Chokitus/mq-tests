package br.edu.ufabc.chokitus.mq.instances.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerRecord;

import br.edu.ufabc.chokitus.mq.message.AbstractMessage;

public class KafkaMessage extends AbstractMessage<ProducerRecord<String, byte[]>> {

	public KafkaMessage(final byte[] message, final String destination, final Map<String, Object> properties) {
		super(new ProducerRecord<>(destination, message), destination, properties);
	}

	@Override
	protected byte[] getBodyImpl() {
		return message.value();
	}

}
