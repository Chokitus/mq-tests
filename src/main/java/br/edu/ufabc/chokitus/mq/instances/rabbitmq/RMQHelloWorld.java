package br.edu.ufabc.chokitus.mq.instances.rabbitmq;

import java.util.concurrent.TimeoutException;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

public class RMQHelloWorld {

	private static final String FILA_TESTE = "teste";

	public static void main(final String[] args) throws IOException, TimeoutException {

		final ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("dev_local");

		connectionFactory.setUsername("teste");
		connectionFactory.setPassword("teste");

		final Connection connection = connectionFactory.newConnection();
		final Channel producer = connection.createChannel();
		producer.queueDeclare(FILA_TESTE, true, false, false, null);

		final Channel consumer = connection.createChannel();

		producer.basicPublish("", FILA_TESTE, null, "Bom dia!!".getBytes());

		final GetResponse basicGet = consumer.basicGet(FILA_TESTE, true);
		System.out.println(new String(basicGet.getBody()));

		producer.close();
		consumer.close();
		connection.close();
	}
}
