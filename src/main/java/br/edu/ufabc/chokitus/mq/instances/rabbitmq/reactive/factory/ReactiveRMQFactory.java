package br.edu.ufabc.chokitus.mq.instances.rabbitmq.reactive.factory;

import br.edu.ufabc.chokitus.mq.instances.rabbitmq.reactive.client.ReactiveRMQProducer;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.reactive.client.ReactiveRMQReceiver;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.reactive.config.ReactiveRMQConfig;
import br.edu.ufabc.chokitus.reactive.mq.factory.ReactiveFactory;
import br.edu.ufabc.chokitus.utils.ResourceUtils;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.ReceiverOptions;
import reactor.rabbitmq.SenderOptions;

import java.util.Map;

@Slf4j
@ToString
public class ReactiveRMQFactory extends ReactiveFactory<ReactiveRMQConfig, ReactiveRMQReceiver, ReactiveRMQProducer> {

	private final Mono<Connection> connectionMono;

	public ReactiveRMQFactory(final ReactiveRMQConfig config) {
		super(config);
		final ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(config.getHost());
		factory.setPassword(config.getPassword());
		factory.setUsername(config.getUsername());
		factory.setPort(config.getPort());
		factory.setVirtualHost(config.getVirtualHost());
		factory.useNio();

		this.connectionMono = Mono.fromCallable(() -> factory.newConnection("reactor-rabbit")).cache();
	}


	@Override
	public void close() {
		connectionMono.subscribe(ResourceUtils::closeResource);
	}

	@Override
	public ReactiveRMQReceiver createReceiver(final Map<String, String> config) {
		return new ReactiveRMQReceiver(RabbitFlux.createReceiver(new ReceiverOptions().connectionMono(connectionMono)));
	}

	@Override
	public ReactiveRMQProducer createProducer(final Map<String, String> config) {
		return new ReactiveRMQProducer(RabbitFlux.createSender(new SenderOptions().connectionMono(connectionMono)));
	}
}
