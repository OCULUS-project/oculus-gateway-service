package pl.poznan.put.oculus.oculusgatewayservice.gateway

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.UriSpec
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayConfig(
        @Value("\${oculus.patients-db-service}")
        val patientsDbServiceHost: String,
        @Value("\${oculus.images-service}")
        val imagesServiceHost: String
) {
    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()

                // patients
                .route("patients-db-service") { r ->
                    r.path("/patients-db/**")
                            .filters { it.remove("/patients-db") }
                            .to(patientsDbServiceHost)
                }

                // images
                .route("images-service") { r ->
                    r.path("/img/**")
                            .uri(host(imagesServiceHost))
                }
                .route("images-service-swagger") { r ->
                    r.path("/swagger/img/**")
                            .filters{ it.remove("/swagger/img") }
                            .to(imagesServiceHost)
                }

                .build()
    }

    companion object {
        private fun host(host: String) = "http://$host"

        private fun UriSpec.to(path: String) = uri(host(path))
        private fun GatewayFilterSpec.remove(regex: String) = rewritePath(regex, "")
    }
}