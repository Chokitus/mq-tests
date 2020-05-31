package br.edu.ufabc.chokitus.mq.instances.zeromq;

import java.util.Map;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

import br.edu.ufabc.chokitus.mq.factory.AbstractClientFactory;
import lombok.ToString;

@ToString(callSuper = true)
public class ZeroMQClientFactory extends AbstractClientFactory<ZeroMQConsumer, ZeroMQProducer> {

	private final ZContext context;

	public ZeroMQClientFactory(final Map<String, Object> clientFactoryProperties, final ZContext context) {
		super(clientFactoryProperties);
		this.context = context;
	}

	@Override
	protected void closeImpl() throws Exception {
		context.close();
	}

	@Override
	protected ZeroMQConsumer createConsumerImpl(final Map<String, Object> consumerProperties) throws Exception {
		return new ZeroMQConsumer(getSocket(consumerProperties), consumerProperties);
	}

	@Override
	protected ZeroMQProducer createProducerImpl(final Map<String, Object> producerProperties) throws Exception {
		return new ZeroMQProducer(getSocket(producerProperties), producerProperties);
	}

	private Socket getSocket(final Map<String, Object> consumerProperties) {
		return context.createSocket(SocketType.valueOf((String) consumerProperties.get(ZeroMQProperty.SOCKET_TYPE.getValue())));
	}

}
