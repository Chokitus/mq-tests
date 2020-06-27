package br.edu.ufabc.chokitus.mq.instances.zeromq;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

public class ZMQHelloWorld {

	public static void main(final String[] args) {
		final ZContext c = new ZContext();

		final ExecutorService pool = Executors.newFixedThreadPool(2);

		final List<CompletableFuture<Void>> futures = new ArrayList<>();

		doSend(c);
		doReceive(c, 2);

		futures.add(CompletableFuture.runAsync(() -> doSend(c)));
		futures.add(CompletableFuture.runAsync(() -> doReceive(c, 2)));

		futures.forEach(CompletableFuture::join);

		pool.shutdownNow();

		c.close();

	}

	private static void doReceive(final ZContext c, final int i) {
		final Socket receiver = c.createSocket(SocketType.PULL);
		receiver.bind("tcp://*:5555");

		while (true) {
			final String string = new String(receiver.recv(0));
//			receiver.send("");
			if ("Pare!".equals(string)) {
				return;
			}
			System.out.println(string + i);
		}
	}

	private static void doSend(final ZContext c) {
		final Socket socket = c.createSocket(SocketType.PUSH);

		socket.connect("tcp://*:5555");
		for (int i = 0; i < 5; i++) {
			socket.send("Bom dia!");
//			socket.recv(0);
		}

		socket.send("Pare!");

		System.out.println("Enviei tudo!");
	}

}
