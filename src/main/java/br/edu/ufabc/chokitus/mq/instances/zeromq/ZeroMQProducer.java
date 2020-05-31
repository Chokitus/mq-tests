package br.edu.ufabc.chokitus.mq.instances.zeromq;

import java.util.Map;

import org.zeromq.ZMQ.Socket;

import br.edu.ufabc.chokitus.mq.client.AbstractProducer;

public class ZeroMQProducer extends AbstractProducer<Socket, ZeroMQMessage> {

	public ZeroMQProducer(final Socket client, final Map<String, Object> properties) {
		super(client, properties);
	}

	@Override
	public void startImpl(final Map<String, Object> properties) throws Exception {
		client.connect((String) properties.get(ZeroMQProperty.SOCKET_URL.getValue()));
	}

	@Override
	protected ZeroMQMessage sendImpl(final ZeroMQMessage message) throws Exception {
		client.send(message.getBody(), 0);
		return new ZeroMQMessage(new byte[0], message.getDestination(), message.getProperties());
	}

	@Override
	public void closeImpl() throws Exception {
		client.close();
	}
}
