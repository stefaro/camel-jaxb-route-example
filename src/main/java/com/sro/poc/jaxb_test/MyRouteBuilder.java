package com.sro.poc.jaxb_test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

import com.sro.poc.jaxb_test.xml.Person;

/**
 * A Camel Java8 DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

	public void configure() throws JAXBException {
		// Initialize JAXB
		JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
		JaxbDataFormat jaxbDataFormat = new JaxbDataFormat(jaxbContext);

		// Create camel route
		//@formatter:off
        from("file:xml-in").routeId("processXmlFile")
        	.unmarshal(jaxbDataFormat) // convert XML string to POJO.        
        	.log("processing person: ${body.getName}")
        	.log("....age ${body.getAge}")
        	.log("....ID: ${body.getId}")
        
        	.split().simple("${body.hobbies}")
        		// Process each hobby separately.
        		.log("....hobby: ${body.getName}")
        	.end()
        ;
    }
}
