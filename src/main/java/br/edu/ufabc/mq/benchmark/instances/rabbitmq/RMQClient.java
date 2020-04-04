package br.edu.ufabc.mq.benchmark.instances.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

import br.edu.ufabc.mq.client.MessagingClient;
import br.edu.ufabc.mq.message.Message;

public class RMQClient extends MessagingClient<Channel> {

	public RMQClient(final Channel channel) {
		super(channel);
	}

	@Override
	public void send(final Message message) {
		// TODO Auto-generated method stub
	}

	@Override
	public Message receive(final byte[] message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeImpl() throws IOException, TimeoutException {
		channel.close();
	}

}
