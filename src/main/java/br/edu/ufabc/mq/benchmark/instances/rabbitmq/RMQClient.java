package br.edu.ufabc.mq.benchmark.instances.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;

import br.edu.ufabc.mq.client.MessagingReceiver;
import br.edu.ufabc.mq.message.Message;

public class RMQClient extends MessagingReceiver<Channel> {

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
		final GetResponse get = channel.basicGet(from, true);
		return new Message(from, get.getBody());
	}

	@Override
	protected void closeImpl() throws IOException, TimeoutException {
		channel.close();
	}

}
