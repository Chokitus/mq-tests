package br.edu.ufabc.mq.benchmark.instances.zeromq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

public class ZMQHelloWorld {

	public static void main(final String[] args) {
		final ZContext c = new ZContext();

		final ExecutorService pool = Executors.newFixedThreadPool(10);

		pool.execute(() -> doSend(c));
		pool.execute(() -> doSend(c));
		pool.execute(() -> doSend(c));
		pool.execute(() -> doReceive(c, 2));

		c.close();
	}

	private static void doReceive(final ZContext c, final int i) {
		final Socket receiver = c.createSocket(SocketType.REP);
		receiver.bind("tcp://*:5555");

		while (true) {
			final String string = new String(receiver.recv(0));
			receiver.send("");
			if ("Pare!".equals(string)) {
				return;
			}
			System.out.println(string + i);
		}
	}

	private static void doSend(final ZContext c) {
		final Socket socket = c.createSocket(SocketType.REQ);

		socket.connect("tcp://*:5555");
		for (int i = 0; i < 5; i++) {
			socket.send("Bom dia!");
			socket.recv(0);
		}

		socket.send("Pare!");

		System.out.println("Enviei tudo!");
	}

}
