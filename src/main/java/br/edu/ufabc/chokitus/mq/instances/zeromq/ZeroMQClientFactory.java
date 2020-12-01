package br.edu.ufabc.chokitus.mq.instances.zeromq;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

import br.edu.ufabc.chokitus.mq.factory.AbstractClientFactory;
import lombok.ToString;

@ToString(callSuper = true)
public class ZeroMQClientFactory extends AbstractClientFactory<ZeroMQReceiver, ZeroMQProducer> {

	private final ZContext context;

	public ZeroMQClientFactory(final ConfigurationProperties clientFactoryProperties, final ZContext context) {
		super(clientFactoryProperties);
		this.context = context;
	}

	@Override
	protected void closeImpl() throws Exception {
		context.close();
	}

	@Override
	protected ZeroMQReceiver createConsumerImpl(final ConfigurationProperties consumerProperties) throws Exception {
		return new ZeroMQReceiver(getSocket(consumerProperties), consumerProperties);
	}

	@Override
	protected ZeroMQProducer createProducerImpl(final ConfigurationProperties producerProperties) throws Exception {
		return new ZeroMQProducer(getSocket(producerProperties), producerProperties);
	}

	private Socket getSocket(final ConfigurationProperties clientProperties) {
		return context.createSocket(SocketType.valueOf((String) clientProperties.get(ZeroMQProperty.SOCKET_TYPE.getValue())));
	}

}
