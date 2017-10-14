package com.sro.poc.jaxb_test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.sro.poc.jaxb_test.xml.Person;

public class TestRoute extends CamelTestSupport {
	@Produce(uri = "direct:start")
	protected ProducerTemplate start;

	@EndpointInject(uri = "mock:file:target")
	private MockEndpoint mockResult;

	@Test
	public void messageReachesTarted() throws Exception {
		mockResult.expectedBodiesReceived("Test message");

		String testxml = Resources.toString(
				Resources.getResource("test.xml"), Charsets.UTF_8);
		start.sendBody(testxml);
		
		mockResult.setExpectedMessageCount(1);
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				// Initialize JAXB
				JAXBContext jaxbContext;
				JaxbDataFormat jaxbDataFormat;
				try {
					jaxbContext = JAXBContext.newInstance(Person.class);
					jaxbDataFormat = new JaxbDataFormat(jaxbContext);
				} catch (JAXBException e) {
					e.printStackTrace();
					return;
				}
				
				// Create camel route
				from("direct:start")
				.log("got message ${body}")
		        .unmarshal(jaxbDataFormat) // convert XML string to POJO.        
		        .log("processing person: ${body.getName}")
		        .log("....age ${body.getAge}")
		        .log("....ID: ${body.getId}")
		        
		        .split().simple("${body.hobbies}")
		        	// Process each hobby separately.
		        	.log("....hobby: ${body.getName}")
		        .end()
				.to("mock:test");
			}
		};
	}
}
