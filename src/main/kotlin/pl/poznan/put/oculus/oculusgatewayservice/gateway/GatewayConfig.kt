package pl.poznan.put.oculus.oculusgatewayservice.gateway

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayConfig(
        @Value("\${oculus.patients-db-service}")
        val patientsDbServiceHost: String
) {
    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
                .route("patients-db-service") { r ->
                    r.path("/patients-db/**")
                            .filters { f -> f.rewritePath("/patients-db", "") }
                            .uri(host(patientsDbServiceHost))
                }
                .build()
    }

    private fun host(host: String): String = "http://$host"
}