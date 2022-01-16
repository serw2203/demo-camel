package com.example.democamel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.MDCUnitOfWork;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.UnitOfWorkFactory;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DemoCamelConfiguration {
    @Bean
    public CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                camelContext.setUseMDCLogging(true);
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
                //noop
            }
        };
    }

    static class UnitOfWork extends MDCUnitOfWork {
        public UnitOfWork(Exchange exchange) {
            super(exchange);
            MDC.put("RqUID", exchange.getIn().getHeader("RqUID", String.class));
        }
    }

    @Bean
    public UnitOfWorkFactory unitOfWorkFactory() {
        return UnitOfWork::new;
    }

    @Bean
    public RouteBuilder routeBuilder(CamelContext camelContext) {
        return new CommonRouteBuilder() {
            @Override
            public void configure() {
                restConfiguration().component("undertow").port(8082).bindingMode(RestBindingMode.auto);

                rest("/start").get().to("direct:start");

                from("direct:start").routeId("Маршрут")
                        .setBody(header("param"))
                        .delay(1000)
                        .process(exchange -> {
                           if (System.currentTimeMillis() % 11 == 0) throw new RuntimeException("" + System.currentTimeMillis());
                        })
                        .to("stream:out");
            }
        };
    }

}
