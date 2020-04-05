package br.edu.ufabc.mq.benchmark.instances.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import br.edu.ufabc.mq.client.MessagingClient;
import br.edu.ufabc.mq.message.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RMQClient extends MessagingClient<Channel> {

	public static final String DEFAULT_EXCHANGE = "";

	public static final String QUEUE_PROPERTY = "queue";
	public static final String DURABLE_PROPERTY = "durable";
	public static final String EXCLUSIVE_PROPERTY = "exclusive";
	public static final String AUTO_DELETE_PROPERTY = "autodelete";
	public static final String ARGUMENTS_PROPERTY = "args";

	public RMQClient(final Channel channel) {
		super(channel);
	}

	@Override
	protected void sendImpl(final Message message) throws IOException {
		channel.basicPublish(DEFAULT_EXCHANGE, message.getDestination(), null, message.getContent());
	}

	@Override
	protected Message receiveImpl(final String from) throws IOException {
		final Message message = new Message(from, null);
		final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			log.info(delivery.toString());
			log.info(new String(delivery.getBody()));
			message.setContent("aaaaa".getBytes());
		};
		channel.basicConsume(from, true, deliverCallback, consumerTag -> { });
		return message;
	}

	@Override
	protected void closeImpl() throws IOException, TimeoutException {
		channel.close();
	}

}
