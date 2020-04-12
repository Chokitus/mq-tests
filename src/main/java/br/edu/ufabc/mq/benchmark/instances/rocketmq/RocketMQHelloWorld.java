package br.edu.ufabc.mq.benchmark.instances.rocketmq;

import java.nio.charset.StandardCharsets;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
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

		for (int i = 0; i < 100; i++) {
			// Create a message instance, specifying topic, tag and message body.
			final Message msg = new Message("TopicTest" /* Topic */, "TagA" /* Tag */,
					("Hello RocketMQ " + i).getBytes(StandardCharsets.UTF_8) /* Message body */
					);
			// Call send message to deliver message to one of brokers.
			producer.sendOneway(msg);
		}
		// Shut down once the producer instance is not longer in use.
		producer.shutdown();
	}

	public static void doConsumer() throws MQClientException {
		// Instantiate with specified consumer group name.
		final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("groupName");

		// Specify name server addresses.
		consumer.setNamesrvAddr("localhost:9876");

		// Subscribe one more more topics to consume.
		consumer.subscribe("TopicTest", "*");
		// Register callback to execute on arrival of messages fetched from brokers.
		consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
			System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});

		// Launch the consumer instance.
		consumer.start();

		System.out.printf("Consumer Started.%n");
	}

}
