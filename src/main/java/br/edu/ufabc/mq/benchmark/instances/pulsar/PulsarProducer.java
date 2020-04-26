package br.edu.ufabc.mq.benchmark.instances.pulsar;

import java.util.Map;

import org.apache.pulsar.client.api.Producer;

import br.edu.ufabc.mq.client.AbstractProducer;

public class PulsarProducer extends AbstractProducer<Producer<byte[]>, PulsarMessage> {

	public PulsarProducer(final Producer<byte[]> client, final Map<String, Object> properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		client.close();
	}

	@Override
	public void startImpl(final Map<String, Object> properties) throws Exception {
		// Unnecessary
	}

	@Override
	protected PulsarMessage sendImpl(final PulsarMessage message) throws Exception {
		client.send(message.getBody());
		return new PulsarMessage(null, message.getDestination(), message.getProperties());
	}

}
