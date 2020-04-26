package br.edu.ufabc.mq.benchmark.instances.pulsar;

import java.util.Map;

import br.edu.ufabc.mq.message.AbstractMessage;

public class PulsarMessage extends AbstractMessage<byte[]> {

	public PulsarMessage(final byte[] message, final String destination, final Map<String, Object> properties) {
		super(message, destination, properties);
	}

	@Override
	protected byte[] getBodyImpl() {
		return message;
	}

}
