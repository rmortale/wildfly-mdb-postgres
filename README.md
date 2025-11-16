
# getting-started

The `getting-started` project is a simple Jakarta EE application with a HTTP endpoint that is running in
[WildFly](https://wildfly.org).

The `src/main` folder contains a simple 'Hello world' style Jakarta EE application using JAX-RS. And also shows the usage of MDB, postgres.

## Building the application

To run the application, you use Maven:

```shell
mvn clean package
```

Maven will compile the application and provision a WildFly server.
The WildFly server is created in `target/server` with the application deployed in it.

Provisioning is done by using WildFly Glow discovery: Glow parses the deployment and adds all necessary WildFly features.
Discovery is configured in "pom.xml" for the "wildfly-maven-plugin":

```shell
<configuration>
	<discover-provisioning-info>
		<version>${version.wildfly.bom}</version>
	</discover-provisioning-info>
	<add-ons>
        <addon>postgresql</addon>
        <addOn>remote-activemq</addOn>
    </add-ons>
</configuration>
```
A `cli` file is used to configure the Artemis connection factory.

## Running the application

To run the application, run the commands:

```shell
source .env
./target/server/bin/standalone.sh
```

The `.env` file contains environment variables used in the wildfly configuration file `standalone.xml`.


Once WildFly is running, the application can be accessed at http://localhost:8080/

## Testing the application
Call the `/hello` endpoint to insert some rows in the postgres database or send a message to the JMS queue.


