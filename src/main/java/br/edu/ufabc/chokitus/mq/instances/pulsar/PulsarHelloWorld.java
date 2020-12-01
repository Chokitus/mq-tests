package br.edu.ufabc.chokitus.mq.instances.pulsar;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Messages;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class PulsarHelloWorld extends Object {
	private static final String TOPIC_NAME = "teste";

	public static void main(final String[] args) throws PulsarClientException {
		final PulsarClient client = PulsarClient.builder().serviceUrl("pulsar://localhost:6650").build();

		System.out.println("Conectei");
		doProducer(client);
		doConsumer(client);
		client.close();
	}

	private static void doProducer(final PulsarClient client) throws PulsarClientException {
		try (final Producer<byte[]> producer = client.newProducer().topic(TOPIC_NAME).create()) {
			System.out.println("Enviei");
			for(int i = 0; i < 100; i++) {
				producer.send("Bom dia!".getBytes());
			}
		}
	}

	private static void doConsumer(final PulsarClient client) throws PulsarClientException {
		try(final Consumer<byte[]> consumer = client.newConsumer()
													.topic(TOPIC_NAME)
													.subscriptionName("nome_consumer")
													.subscribe()) {
			final Messages<byte[]> batchReceive = consumer.batchReceive();
			int i = 0;
			for (final Message<byte[]> message : batchReceive) {
				System.out.println(i++);
			}
			consumer.acknowledge(batchReceive);
		}
	}
}
