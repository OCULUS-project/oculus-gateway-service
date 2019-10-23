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
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator = builder.routes()
                .route("patients-db-service", "patients", patientsDbServiceHost)
                .route("images-service", "img", imagesServiceHost)
                .build()

    companion object {
        private fun host(host: String) = "http://$host"

        private fun  RouteLocatorBuilder.Builder.route(id: String, path: String, host: String) = apply {
            route(id) { r ->
                r.path("/$path/**") to host
            }
            route("$id-swagger") { r ->
                val swaggerPath = "/swagger/$path"
                r.path("$swaggerPath/**")
                        .filters{ it.remove(swaggerPath) } to host
            }
        }

        private infix fun UriSpec.to(path: String) = uri(host(path))
        private fun GatewayFilterSpec.remove(regex: String) = rewritePath(regex, "")
    }
}