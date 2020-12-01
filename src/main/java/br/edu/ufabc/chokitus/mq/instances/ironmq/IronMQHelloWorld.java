package br.edu.ufabc.chokitus.mq.instances.ironmq;

import java.io.IOException;

import io.iron.ironmq.Client;
import io.iron.ironmq.Cloud;
import io.iron.ironmq.Message;
import io.iron.ironmq.Queue;

public class IronMQHelloWorld {

	private static final String QUEUE_NAME = "queue_name";

	public static void main(final String[] args) throws IOException {
		final Client producer = new Client("projectId", "token", new Cloud("localhost:8080"));
		final Client consumer = new Client("projectId", "token", new Cloud("localhost:8080"));

		doProducer(producer);
		doConsumer(consumer);
	}

	private static void doProducer(final Client producer) throws IOException {
		final Queue queue = producer.queue(QUEUE_NAME);
		queue.create();
		queue.push("Bom dia!");
	}

	private static void doConsumer(final Client consumer) throws IOException {
		final Queue queue = consumer.queue(QUEUE_NAME);
		queue.create();
		final Message reserve = queue.reserve();
		System.out.println(reserve.getBody());
	}

}
