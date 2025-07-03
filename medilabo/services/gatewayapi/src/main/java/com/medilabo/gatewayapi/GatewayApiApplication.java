package com.medilabo.gatewayapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApiApplication {

    public static void main(String[] args) {
	SpringApplication.run(GatewayApiApplication.class, args);
    }

    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
	return routeLocatorBuilder.routes()

		.route("patientapi", p -> p

			.path("/api/patients/**")

			.filters(f -> f

				.circuitBreaker(config -> config.setName("patientapiCircuitBreaker")))

			.uri("lb://patientapi"))

		.route(p -> p

			.path("/api/notes/**")

			.filters(f -> f.circuitBreaker(config -> config.setName("noteapiCircuitBreaker")))

			.uri("lb://noteapi"))

		.route("evaluationrisqueapi", p -> p

			.path("/api/evaluationrisque/**")

			.filters(f -> f.circuitBreaker(config -> config.setName("evaluationrisqueapiCircuitBreaker")))

			.uri("lb://evaluationrisqueapi"))

		.route("authapi", p -> p

			.path("/auth/**")

			.filters(f -> f.circuitBreaker(config -> config.setName("authapiCircuitBreaker")))

			.uri("lb://authapi"))

		.build();
    }
}
