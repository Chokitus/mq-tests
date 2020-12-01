package br.edu.ufabc.chokitus.mq.instances.activemq.blocking.client;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.instances.activemq.blocking.message.ActiveMQMessage;
import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.ClientConsumer;
import org.apache.activemq.artemis.api.core.client.ClientMessage;
import org.apache.activemq.artemis.api.core.client.ClientSession;

import br.edu.ufabc.chokitus.mq.client.AbstractReceiver;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ActiveMQReceiver extends AbstractReceiver<ClientConsumer, ActiveMQMessage> implements ActiveMQClient {

	private final ClientSession session;

	public ActiveMQReceiver(final ClientConsumer client, final ClientSession session, final ConfigurationProperties properties) {
		super(client, properties);
		this.session = session;
	}

	@Override
	public void closeImpl() throws ActiveMQException {
		client.close();
	}

	@Override
	public void startImpl(final ConfigurationProperties properties) {
		throw new UnsupportedOperationException("Um cliente do ActiveMQ deve ser iniciado através de seu objeto Session");
	}

	@Override
	protected ActiveMQMessage instantiateEmptyMessage() {
		return new ActiveMQMessage(null);
	}

	@Override
	protected ActiveMQMessage consumeImpl(final String property) throws ActiveMQException {
		final ClientMessage message = client.receive();
		return new ActiveMQMessage(message);
	}

}
