package br.edu.ufabc.chokitus.mq.instances.kafka.blocking.message;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.message.AbstractMessage;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaMessage extends AbstractMessage<ProducerRecord<String, byte[]>> {

	public KafkaMessage(final byte[] message, final String destination, final ConfigurationProperties properties) {
		super(new ProducerRecord<>(destination, message), destination, properties);
	}

	@Override
	protected byte[] getBodyImpl() {
		return message.value();
	}

}
