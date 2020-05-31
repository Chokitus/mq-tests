package br.edu.ufabc.chokitus.mq.instances.kafka;

import java.util.Map;

import br.edu.ufabc.mq.message.AbstractMessage;

public class KafkaMessage extends AbstractMessage<byte[]> {

	public KafkaMessage(final byte[] message, final String destination, final Map<String, Object> properties) {
		super(message, destination, properties);
	}

	@Override
	protected byte[] getBodyImpl() {
		return message;
	}

}
