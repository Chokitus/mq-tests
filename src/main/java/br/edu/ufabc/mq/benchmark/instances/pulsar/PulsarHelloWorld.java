package br.edu.ufabc.mq.benchmark.instances.pulsar;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class PulsarHelloWorld {
	private static final String TOPIC_NAME = "teste";

	public static void main(final String[] args) throws PulsarClientException {
		final PulsarClient client = PulsarClient.builder().serviceUrl("pulsar://localhost:6650").build();

		doProducer(client);
		doConsumer(client);
	}

	private static void doProducer(final PulsarClient client) throws PulsarClientException {
		try (final Producer<byte[]> producer = client.newProducer().topic(TOPIC_NAME).create()) {
			producer.send("Bom dia!".getBytes());
		}
	}

	private static void doConsumer(final PulsarClient client) throws PulsarClientException {
		try(final Consumer<byte[]> consumer = client.newConsumer().topic(TOPIC_NAME).subscriptionName("nome_consumer").subscribe()) {
			final Message<byte[]> message = consumer.receive();
			System.out.println("Recebida: " + new String(message.getData()));
			consumer.acknowledge(message);
		}
	}
}
