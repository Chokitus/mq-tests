package br.edu.ufabc.mq.benchmark.instances.rabbitmq;

import java.util.concurrent.TimeoutException;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;

import br.edu.ufabc.mq.client.AbstractConsumer;

public class RMQClient extends AbstractConsumer<Channel> {

	public static final String DEFAULT_EXCHANGE = "";


	public RMQClient(final Channel channel) {
		super(channel);
	}

	@Override
	protected void sendImpl(final Message message) throws IOException {
		channel.basicPublish(DEFAULT_EXCHANGE, message.getDestination(), null, message.getContent());
	}

	@Override
	protected Message consumeImpl(final String from) throws IOException {
		final GetResponse get = channel.basicGet(from, true);
		return new Message(from, get.getBody());
	}

	@Override
	protected void closeImpl() throws IOException, TimeoutException {
		channel.close();
	}

}
