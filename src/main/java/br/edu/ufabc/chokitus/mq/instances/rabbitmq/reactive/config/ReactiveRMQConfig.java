package br.edu.ufabc.chokitus.mq.instances.rabbitmq.reactive.config;

import br.edu.ufabc.chokitus.reactive.mq.config.ReactiveConfig;
import lombok.Data;

@Data
public class ReactiveRMQConfig extends ReactiveConfig {

	private String host;
	private String password;
	private String username;
	private Integer port;
	private String virtualHost;

}
