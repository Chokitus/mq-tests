package br.edu.ufabc.chokitus.mq.benchmark;

import java.util.function.Function;

import br.edu.ufabc.chokitus.mq.factory.AbstractWrapperFactory;
import br.edu.ufabc.chokitus.mq.instances.activemq.blocking.factory.ActiveMQWrapperFactory;
import br.edu.ufabc.chokitus.mq.instances.ironmq.IronMQWrapperFactory;
import br.edu.ufabc.chokitus.mq.instances.kafka.blocking.factory.KafkaWrapperFactory;
import br.edu.ufabc.chokitus.mq.instances.pulsar.blocking.factory.PulsarWrapperFactory;
import br.edu.ufabc.chokitus.mq.instances.rabbitmq.blocking.factory.RabbitMQWrapperFactory;
import br.edu.ufabc.chokitus.mq.instances.rocketmq.RocketMQWrapperFactory;
import br.edu.ufabc.chokitus.mq.instances.zeromq.ZeroMQWrapperFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InstanceInitializer {

	ACTIVEMQ(ActiveMQWrapperFactory::new), //
	IRONMQ(IronMQWrapperFactory::new), //
	KAFKA(KafkaWrapperFactory::new), //
	PULSAR(PulsarWrapperFactory::new), //
	RABBITMQ(RabbitMQWrapperFactory::new), //
	ROCKETMQ(RocketMQWrapperFactory::new), //
	ZEROMQ(ZeroMQWrapperFactory::new);

	private final Function<ConfigurationProperties, AbstractWrapperFactory<?, ?, ?, ?>> initializer;

	public AbstractWrapperFactory<?, ?, ?, ?> getFactory(final ConfigurationProperties properties) {
		return initializer.apply(properties);
	}

}
