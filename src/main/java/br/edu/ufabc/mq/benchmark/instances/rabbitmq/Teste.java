package br.edu.ufabc.mq.benchmark.instances.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.edu.ufabc.mq.connection.Connection;
import br.edu.ufabc.mq.exception.MessageQueueException;
import br.edu.ufabc.mq.message.Message;
import br.edu.ufabc.mq.utils.PropertyUtils;

public class Teste {

	public static void main(final String[] args) throws MessageQueueException, IOException {
		final Map<String, Object> properties = new HashMap<>();
		properties.put(PropertyUtils.HOST, "localhost");
		properties.put(PropertyUtils.PORT, 5672);

		final RMQConnectionFactory factory = new RMQConnectionFactory(properties);
		final Connection<com.rabbitmq.client.Connection> connection = factory.getConnection();

		final Map<String, Object> clientProperties = new HashMap<>();
		clientProperties.put(RMQClient.QUEUE_PROPERTY, "teste");
		final RMQClient client = factory.getNewClient(connection, clientProperties);
		client.send(new Message("teste", "bom dia!!".getBytes()));

		client.close();
		connection.close();
	}

}
