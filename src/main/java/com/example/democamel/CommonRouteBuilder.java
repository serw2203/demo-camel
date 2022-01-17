package com.example.democamel;

import org.apache.camel.CamelContext;
import org.apache.camel.NamedNode;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultMessageHistory;
import org.apache.logging.log4j.util.Strings;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.democamel.AnsiColor.*;

public abstract class CommonRouteBuilder extends RouteBuilder {
    final String TRACER_TEMPLATE = "%s TRACER  -->%s" +
            "\t%s MESSAGE_ID %s: %s{}%s;" +
            "\t%s NODE %s: %s{} = {}%s;" +
            "\t%s ROUTE_ID %s: %s{}%s;" +
            "\t%s ENDPOINT %s: {};" +
            "\t%s BODY %s: {};" +
            "\t%s HEADERS %s: {};" +
            "\t%s PROPERTIES %s: {}";

    final String TRACER = String.format(TRACER_TEMPLATE, CYAN_BOLD_BRIGHT, RESET
            /*MESSAGE_ID*/, YELLOW, RESET, GREEN_BOLD, RESET
            /*NODE*/, YELLOW, RESET, WHITE_BRIGHT, RESET
            /*ROUTE_ID*/, YELLOW, RESET, MAGENTA_BRIGHT, RESET
            /*ENDPOINT*/, YELLOW, RESET
            /*BODY*/, YELLOW, RESET
            /*HEADERS*/, YELLOW, RESET
            /*PROPERTIES*/, YELLOW, RESET
    );

    public CommonRouteBuilder() {
        super();
        this.registerInterceptor();
    }

    @SuppressWarnings("unused")
    public CommonRouteBuilder(CamelContext context) {
        super(context);
        this.registerInterceptor();
    }

    private void registerInterceptor() {
        this.tracerInterceptor();
    }

    private String restrict(Object value) {
        return value == null ? null : Strings.left(value.toString(), 2000);
    }

    private void tracerInterceptor() {
        final String interceptorName = "tracer";

        intercept().process(exchange -> {
            @SuppressWarnings("unchecked") Optional<DefaultMessageHistory> messageHistory = Optional.ofNullable(exchange.getProperties())
                    .map(p -> p.get("CamelMessageHistory"))
                    .map(p -> (List<DefaultMessageHistory>) p)
                    .orElse(Collections.emptyList()).stream()
                    .filter(m -> Objects.nonNull(m.getNode()))
                    .filter(m -> !interceptorName.equals(m.getNode().getId()))
                    .reduce((f, s) -> s);

            log.debug(TRACER
                    , exchange.getMessage().getMessageId()
                    , messageHistory.map(DefaultMessageHistory::getNode).map(NamedNode::getId).orElse(null)
                    , messageHistory.map(DefaultMessageHistory::getNode).map(this::restrict).orElse(null)
                    , messageHistory.map(DefaultMessageHistory::getRouteId).orElse(null)
                    , exchange.getFromEndpoint()
                    , restrict(exchange.getIn().getBody())
                    , restrict(exchange.getIn().getHeaders())
                    , restrict(exchange.getProperties()));
        }).id(interceptorName).end();
    }
}
