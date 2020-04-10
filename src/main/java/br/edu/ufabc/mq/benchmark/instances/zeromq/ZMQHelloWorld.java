package br.edu.ufabc.mq.benchmark.instances.zeromq;

import java.util.HashMap;
import java.util.Map;

import org.zeromq.ZMQ.Socket;

import br.edu.ufabc.mq.connection.Connection;
import br.edu.ufabc.mq.exception.MessageQueueException;

public class ZMQHelloWorld {

	public static void main(final String[] args) throws MessageQueueException {
		final Map<String, Object> properties = new HashMap<>();

		final ZMQConnectionFactory factory = new ZMQConnectionFactory(properties);
		final Connection<Socket> connection = factory.getConnection(properties);

		final ZMQClient client = factory.getNewClient(connection, null);

	}

}
