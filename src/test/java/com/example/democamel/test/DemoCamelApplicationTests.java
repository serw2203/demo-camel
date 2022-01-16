package com.example.democamel.test;

import com.example.democamel.CommonRouteBuilder;
import com.example.democamel.DemoCamelConfiguration;
import org.apache.camel.*;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.assertj.core.util.Preconditions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = DemoCamelApplicationTests.TestSimple.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {DemoCamelApplicationTests.TestConfig.class, DemoCamelConfiguration.class})
public class DemoCamelApplicationTests {

    @Autowired
    private CamelContext camelContext;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;


    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Before
    public void before () {
        resultEndpoint.reset();
    }

    @SpringBootApplication
    public static class TestSimple {
        public static void main(String[] args) {
            SpringApplication.run(TestSimple.class, args);
        }
    }

    @Configuration
    public static class TestConfig {
        @Bean
        public RouteBuilder routeBuilder(CamelContext camelContext) {
            Preconditions.checkNotNull(camelContext);
            return new CommonRouteBuilder() {
                @Override
                public void configure() {
                    from("direct:first")
                            .to("mock:bar").id("bar")
                            .to("mock:foo").id("foo")
                            .to("mock:result").id("result");


                    from("direct:start").routeId("Маршрут")
                            .log(LoggingLevel.WARN, " <><><><><><><> ${body}")
                            .log(LoggingLevel.ERROR, " <><><><><><><> ${body}")
                            .filter((v) -> v.getIn().getBody(String.class).equals("<matched/>"))
                            .to("mock:result").id("channel2");
                }
            };
        }
    }

    @Ignore
    @Test
    public void contextLoads() {
        Assert.assertNotNull(camelContext);
        Assert.assertNotNull(resultEndpoint);
        Assert.assertNotNull(template);
    }

    @Test
    public void testSendFirstMessage() throws Exception {
        String expectedBody = "<matched/>";
        resultEndpoint.expectedBodiesReceived(expectedBody);
        camelContext.createProducerTemplate().sendBody("direct:first", expectedBody);
        resultEndpoint.assertIsSatisfied();
    }

    @Test
    public void testSendMatchingMessage() throws Exception {
        String expectedBody = "<matched/>";
        resultEndpoint.expectedBodiesReceived(expectedBody);
        resultEndpoint.expectedHeaderReceived("foo", "bar");
        template.sendBodyAndHeader(expectedBody, "foo", "bar");
        resultEndpoint.assertIsSatisfied();
    }

    @Ignore
    @Test
    public void shouldProduceMessages() throws Exception {
        // we expect that one or more messages is automatic done by the Camel
        // route as it uses a timer to trigger
        NotifyBuilder notify = new NotifyBuilder(camelContext)
                .from("direct:first").whenDone(1)
                .and()
                .from("direct:start").whenDone(1)
                .create();

        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();

        producerTemplate.sendBody("direct:first", "expectedBody");
        producerTemplate.sendBody("direct:start", "expectedBody");

        assertTrue(notify.matches(1, TimeUnit.SECONDS));
    }

    @Ignore
    @Test
    public void testIntercept() throws Exception {
        String expectedBody = "<matched/>";

        NotifyBuilder notify = new NotifyBuilder(camelContext)
                .from("direct:first").whenDone(1)
                .create();

        camelContext.createProducerTemplate().sendBody("direct:first", expectedBody);

        assertTrue(notify.matches(1, TimeUnit.SECONDS));
    }
}
