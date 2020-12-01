package br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.client;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.message.PulsarMessage;
import org.apache.pulsar.client.api.Producer;

import br.edu.ufabc.chokitus.mq.client.AbstractProducer;

public class PulsarProducer extends AbstractProducer<Producer<byte[]>, PulsarMessage> {

	public PulsarProducer(final Producer<byte[]> client, final ConfigurationProperties properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		client.close();
	}

	@Override
	public void startImpl(final ConfigurationProperties properties) throws Exception {
		// Unnecessary
	}

	@Override
	protected PulsarMessage sendImpl(final PulsarMessage message) throws Exception {
		client.send(message.getBody());
		return new PulsarMessage(null, message.getDestination(), message.getProperties());
	}

}
