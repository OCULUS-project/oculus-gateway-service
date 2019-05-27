package pl.poznan.put.oculus.oculusgatewayservice.rest

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FallbackController {

    @RequestMapping("fallback/not-available")
    fun instanceNotAvailable() = "not available"
}