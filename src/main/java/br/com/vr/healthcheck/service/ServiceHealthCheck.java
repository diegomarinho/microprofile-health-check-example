package br.com.vr.healthcheck.service;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author dmarinho
 */
@Health
@ApplicationScoped
public class ServiceHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse
                .named("system-load").up()
                .withData("memory", Runtime.getRuntime().freeMemory())
                .withData("total memory", Runtime.getRuntime().totalMemory())
                .withData("loadAverage max", Runtime.getRuntime().maxMemory())
                .withData("availableProcessors", Runtime.getRuntime().availableProcessors());
        return responseBuilder.state(true).build();
    }
}
