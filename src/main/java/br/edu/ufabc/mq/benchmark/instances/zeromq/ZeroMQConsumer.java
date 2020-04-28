package br.edu.ufabc.mq.benchmark.instances.zeromq;

import java.util.Map;

import org.zeromq.ZMQ.Socket;

import br.edu.ufabc.mq.client.AbstractConsumer;

public class ZeroMQConsumer extends AbstractConsumer<Socket, ZeroMQMessage> {

	public ZeroMQConsumer(final Socket client, final Map<String, Object> properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		client.close();
	}

	@Override
	public void startImpl(final Map<String, Object> properties) throws Exception {
		client.bind((String) properties.get(ZeroMQProperty.SOCKET_URL.getValue()));
	}

	@Override
	protected ZeroMQMessage consumeImpl(final String property) throws Exception {
		return new ZeroMQMessage(client.recv(), property, null);
	}

}
