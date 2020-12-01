package br.edu.ufabc.chokitus.mq.benchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;

import java.io.IOException;

public class Application {

	public static void main(final String[] args) throws IOException {
		//				Main.main(args);
		final String benchmark = BenchmarkInitializer.MULTI_PRODUCER.toString().toLowerCase();
		final String instance = InstanceInitializer.RABBITMQ.toString().toLowerCase();
		final String path = "benchmark/" + benchmark + "/" + instance + ".json";

		final BenchmarkConfiguration config = new ObjectMapper().readValue(Resources.getResource(path),
		                                                                   BenchmarkConfiguration.class);
		config.initTest();
	}

}
