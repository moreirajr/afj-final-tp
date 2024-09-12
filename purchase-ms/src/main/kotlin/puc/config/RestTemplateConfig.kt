package puc.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.slf4j.MDC
import java.util.UUID

@Configuration
class RestTemplateConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
      
        restTemplate.interceptors.add(ClientHttpRequestInterceptor { request, body, execution ->
            val traceId = MDC.get("Trace-Id") ?: generateTraceId()
            val correlationId = MDC.get("Correlation-Id") ?: generateCorrelationId()

            request.headers.add("Trace-Id", traceId)
            request.headers.add("Correlation-Id", correlationId)

            execution.execute(request, body)
        })

        return restTemplate
    }

    private fun generateTraceId(): String = UUID.randomUUID().toString()

    private fun generateCorrelationId(): String = UUID.randomUUID().toString()
}
