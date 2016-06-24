# gluonsoft-excel-or-word-export

This Gluonsoft  do export of word and excel in your data, for example in data the table or anywhere type of json.

Configure your pom.xml with required dependecies  are below: 

```
<!-- build files in office format -->
<dependency>
	<groupId>org.apache.poi</groupId>
	<artifactId>poi</artifactId>
	<version>3.14</version>
</dependency>

<!-- required for excel builder -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>3.14</version>
</dependency>

<!-- JAX-RS Jersey for REST -->
<dependency>
	<groupId>org.glassfish.jersey.containers</groupId>
	<artifactId>jersey-container-servlet-core</artifactId>
	<version>2.22.1</version>
</dependency>
		
<!-- resolve of media-type http -->
<dependency>
    <groupId>org.glassfish.jersey.media</groupId>
    <artifactId>jersey-media-json-jackson</artifactId>
    <version>2.22.1</version>
</dependency>
	
<!-- json object -->
<dependency>
    <groupId>com.googlecode.json-simple</groupId>
    <artifactId>json-simple</artifactId>
    <version>1.1.1</version>
</dependency>
```
