package com.sro.poc.jaxb_test;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class TestRoute extends CamelTestSupport {

	@Produce("direct:start")
	protected ProducerTemplate start;

	@EndpointInject("mock:file:target")
	private MockEndpoint mockResult;

	@Before
	public void prepareRoute() throws Exception {
		/**
		 * Replace starting point from route for easier testing.
		 */
		AdviceWith.adviceWith(context, "processXmlFile", a -> {
			a.replaceFromWith("direct:start");
		});
	}

	@Test
	public void messageReachesTarted() throws Exception {
		mockResult.expectedBodiesReceived("Test message");

		String testxml = Resources.toString(Resources.getResource("test.xml"), Charsets.UTF_8);
		start.sendBody(testxml);

		mockResult.setExpectedMessageCount(1);
	}

	@Override
	protected RoutesBuilder[] createRouteBuilders() throws Exception {
		return new RouteBuilder[] { new MyRouteBuilder() };
	}
}
