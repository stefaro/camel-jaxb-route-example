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

	
	public void configure() {
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
        from("file:xml-in")
        .unmarshal(jaxbDataFormat) // convert XML string to POJO.        
        .log("processing person: ${body.getName}")
        .log("....age ${body.getAge}")
        .log("....ID: ${body.getId}")
        
        .split().simple("${body.hobbies}")
        	// Process each hobby separately.
        	.log("....hobby: ${body.getName}")
        .end();
    }
}
