package br.edu.ufabc.chokitus.mq.instances.zeromq;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import org.zeromq.ZMQ.Socket;

import br.edu.ufabc.chokitus.mq.client.AbstractReceiver;

public class ZeroMQReceiver extends AbstractReceiver<Socket, ZeroMQMessage> {

	public ZeroMQReceiver(final Socket client, final ConfigurationProperties properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		client.close();
	}

	@Override
	public void startImpl(final ConfigurationProperties properties) throws Exception {
		client.bind((String) properties.get(ZeroMQProperty.SOCKET_URL.getValue()));
	}

	@Override
	protected ZeroMQMessage instantiateEmptyMessage() {
		return new ZeroMQMessage(null, null, null);
	}

	@Override
	protected ZeroMQMessage consumeImpl(final String property) throws Exception {
		return new ZeroMQMessage(client.recv(), property, null);
	}

}
