package com.sunyesle.shared;

import io.micrometer.tracing.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class FilterConfig {

    @Bean
    TraceIdHeaderFilter traceIdHeaderFilter(Tracer tracer) {
        return new TraceIdHeaderFilter(tracer);
    }

    @Bean
    HeaderLoggerFilter headerLoggerFilter() {
        return new HeaderLoggerFilter();
    }
}
