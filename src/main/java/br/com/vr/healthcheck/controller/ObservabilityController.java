package br.com.vr.healthcheck.controller;

import br.com.vr.healthcheck.service.ServiceHealthCheck;
import lombok.extern.java.Log;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author dmarinho
 */
@Log
@RequestScoped
@Path("/health")
@Consumes(MediaType.APPLICATION_JSON) @Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Health check service", description = "Observability with Microprofile from a web component Java")
public class ObservabilityController extends ServiceHealthCheck {

    @GET
    @Operation(description = "Getting HealthCheck")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful, returning HealthCheck",
                    content = @Content(schema = @Schema(implementation = HealthCheckResponse.class)))
    })
    public Response getHealthCheck(
            @Parameter(name = "name", description = "Component name",
                    required = true, allowEmptyValue = false, example = "AGW-xyz")
            @QueryParam("name") String name) {

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse
                .named(name).up()
                .withData("memory", Runtime.getRuntime().freeMemory())
                .withData("total memory", Runtime.getRuntime().totalMemory())
                .withData("loadAverage max", Runtime.getRuntime().maxMemory())
                .withData("availableProcessors", Runtime.getRuntime().availableProcessors());

        return Response.ok(responseBuilder.state(true).build()).build();
    }
}
