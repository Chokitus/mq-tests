package br.edu.ufabc.mq.benchmark.instances.zeromq;

import java.util.Map;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

import br.edu.ufabc.mq.connection.ConnectionFactoryWrapper;
import br.edu.ufabc.mq.exception.MessageQueueException;

public class ZMQConnectionFactory extends ConnectionFactoryWrapper<ZContext, Socket, ZMQClient> {

	public ZMQConnectionFactory(final Map<String, Object> properties) throws MessageQueueException {
		super(properties);
	}

	@Override
	protected ZMQClient getNewClientImpl(final Socket connection, final Map<String, Object> clientProperties) throws Exception {

		return null;
	}

	@Override
	protected ZContext getFactoryImpl() throws Exception {
		return new ZContext();
	}

	@Override
	protected Socket getConnectionImpl(final Map<String, Object> properties) throws Exception {
		return factory.createSocket((SocketType) properties.get(ZMQClient.SOCKET_TYPE));
	}

}
