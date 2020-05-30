package br.edu.ufabc.mq.benchmark.instances.rocketmq;

import java.util.List;

import java.nio.charset.StandardCharsets;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class RocketMQHelloWorld {

	public static void main(final String[] args) throws MQClientException, RemotingException, InterruptedException {
		doProducer();
		doConsumer();
	}

	private static void doProducer() throws MQClientException, RemotingException, InterruptedException {
		final DefaultMQProducer producer = new DefaultMQProducer("groupName");

		producer.setNamesrvAddr("localhost:9876");

		producer.start();

		for (int i = 0; i < 4; i++) {
			// Create a message instance, specifying topic, tag and message body.
			final Message msg = new Message("TopicTest" /* Topic */, "TagA" /* Tag */,
					("RocketMQ " + i).getBytes(StandardCharsets.UTF_8) /* Message body */
			);
			// Call send message to deliver message to one of brokers.
			producer.sendOneway(msg);
		}
		// Shut down once the producer instance is not longer in use.
		producer.shutdown();
	}

	public static void doConsumer() throws MQClientException {
		// Instantiate with specified consumer group name.
		final DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("groupName");

		// Specify name server addresses.
		consumer.setNamesrvAddr("localhost:9876");
		consumer.setPullBatchSize(2);

		// Subscribe one more more topics to consume.
		consumer.subscribe("TopicTest", "*");
		consumer.start();

		for (int i = 0; i < 10; i++) {
			final List<MessageExt> list = consumer.poll(500);
			if (!list.isEmpty()) {
				final MessageExt msg = list.get(0);
				System.out.println("A mensagem: " + new String(msg.getBody()));
			} else {
				System.out.println("Vazio... " + i);
			}
		}
		consumer.shutdown();
	}

}
