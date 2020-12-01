package br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.client;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.client.AbstractReceiver;
import br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.message.PulsarMessage;
import org.apache.pulsar.client.api.Consumer;

public class PulsarReceiver extends AbstractReceiver<Consumer<byte[]>, PulsarMessage> {

	public PulsarReceiver(final Consumer<byte[]> client, final ConfigurationProperties properties) {
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
	protected PulsarMessage instantiateEmptyMessage() {
		return new PulsarMessage(null, null, null);
	}

	/**
	 * This won't ever return an empty message, as {@link org.apache.pulsar.client.api.Consumer#receive()} blocks until a
	 * message is available.
	 *
	 * @param destination
	 *
	 * @return A never-empty message
	 *
	 * @throws Exception
	 */
	@Override
	protected PulsarMessage consumeImpl(final String destination) throws Exception {
		return new PulsarMessage(client.receive().getData(), destination, null);
	}

}
