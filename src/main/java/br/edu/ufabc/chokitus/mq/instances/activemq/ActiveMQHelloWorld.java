package br.edu.ufabc.chokitus.mq.instances.activemq;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ClientConsumer;
import org.apache.activemq.artemis.api.core.client.ClientMessage;
import org.apache.activemq.artemis.api.core.client.ClientProducer;
import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;
import org.apache.activemq.artemis.api.core.client.ServerLocator;

public class ActiveMQHelloWorld {

	private static final String NOME_FILA = "teste";

	public static void main(final String[] args) throws Exception {
		try (final ServerLocator locator = ActiveMQClient.createServerLocator("tcp://localhost:61616");
			 final ClientSessionFactory sessionFactory = locator.createSessionFactory();
			 final ClientSession session = sessionFactory.createSession("mq-test","mq-test", true, true, true, true, 1)) {
			doProducer(session);
			doConsumer(session);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static void doConsumer(final ClientSession session) throws ActiveMQException {
		try (final ClientConsumer consumer = session.createConsumer(NOME_FILA)) {
			session.start();
			final ClientMessage received = consumer.receive();
			final byte[] bytes = new byte[received.getBodySize()];
			received.getBodyBuffer().readBytes(bytes);
			System.out.println(new String(bytes));
		}
	}

	private static void doProducer(final ClientSession session) throws ActiveMQException {
		try (final ClientProducer producer = session.createProducer(NOME_FILA)) {
			final ClientMessage message = session.createMessage(true);
			message.getBodyBuffer().writeBytes("Bom dia!".getBytes());
//			session.createQueue(NOME_FILA, RoutingType.ANYCAST, NOME_FILA, true);
			producer.send(message);
		}
	}

}
