package br.edu.ufabc.mq.benchmark.instances.zeromq;

import org.zeromq.ZMQ.Socket;

import br.edu.ufabc.mq.client.MessagingClient;
import br.edu.ufabc.mq.message.Message;

public class ZMQClient extends MessagingClient<Socket> {

	public static final String SOCKET_TYPE = "socket_type";

	public ZMQClient(final Socket socket) {
		super(socket);
	}

	@Override
	protected void sendImpl(final Message message) throws Exception {
		// TODO: Adicionar opção para mudar flag
		channel.send(message.getContent(), 0);
	}

	@Override
	protected Message receiveImpl(final String from) throws Exception {
		final byte[] obj = channel.recv();
		return new Message(from, obj);
	}

	@Override
	protected void closeImpl() throws Exception {
		channel.close();
	}

}
