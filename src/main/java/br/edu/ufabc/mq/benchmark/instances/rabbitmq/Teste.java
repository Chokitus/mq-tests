package br.edu.ufabc.mq.benchmark.instances.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.edu.ufabc.mq.connection.Connection;
import br.edu.ufabc.mq.exception.MessageQueueException;
import br.edu.ufabc.mq.message.Message;
import br.edu.ufabc.mq.utils.PropertyUtils;

public class Teste {

	private static final String FILA_TESTE = "teste";

	public static void main(final String[] args) throws MessageQueueException, IOException, InterruptedException {

		final Map<String, Object> properties = new HashMap<>();
		properties.put(PropertyUtils.HOST, "localhost");
		properties.put(PropertyUtils.PORT, 5672);

		final RMQConnectionFactory factory = new RMQConnectionFactory(properties);
		final Connection<com.rabbitmq.client.Connection> connection = factory.getConnection();

		final Map<String, Object> clientProperties = new HashMap<>();
		clientProperties.put(RMQClient.QUEUE_PROPERTY, FILA_TESTE);

		final RMQClient sender = factory.getNewClient(connection, clientProperties);
		final RMQClient receiver = factory.getNewClient(connection, clientProperties);

		sender.send(new Message(FILA_TESTE, "Mensagem 1".getBytes()));
		sender.send(new Message(FILA_TESTE, "Mensagem 2".getBytes()));
		Message receive = receiver.receive(FILA_TESTE);
		receive = receiver.receive(FILA_TESTE);

		sender.close();
		receiver.close();
		connection.close();
	}
}
