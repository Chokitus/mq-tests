package br.edu.ufabc.chokitus.mq.instances.pulsar;

import java.util.Map;

import org.apache.pulsar.client.api.Consumer;

import br.edu.ufabc.chokitus.mq.client.AbstractConsumer;

public class PulsarConsumer extends AbstractConsumer<Consumer<byte[]>, PulsarMessage>{

	public PulsarConsumer(final Consumer<byte[]> client, final Map<String, Object> properties) {
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
	protected PulsarMessage consumeImpl(final String property) throws Exception {
		return new PulsarMessage(client.receive().getData(), property, null);
	}

}
