# camel-jaxb-route-example
Example of JAXB unmarshal on camel route

This project contains an example camel route where an xml file is unmarshalled with JAXB.

The xml file structure used in this example is like this:
```
<person>
  <age>12</age>
  <name>Teppo testi</name>
  <id>asdfh2354</id>
  <hobbies>
    <hobby>football</hobby>
    <hobby>cooking</hobby>
    <hobby>music</hobby>
  </hobbies>
</person>
```

Successfull processing of XML will output following log:
```
[                          main] route1                         INFO  processing person: Teppo testi
[                          main] route1                         INFO  ....age 12
[                          main] route1                         INFO  ....ID: asdfh2354
[                          main] route1                         INFO  ....hobby: football
[                          main] route1                         INFO  ....hobby: cooking
[                          main] route1                         INFO  ....hobby: music
```

Camel route used to achieve this is:
```
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
```        
        
