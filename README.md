# bisq-seednode

## Build

    mvn package

## Run

    java -jar target/SeedNode.jar

See also [docs/run-on-tor-and-regtest.md](docs/run-on-tor-and-regtest.md)

## Import into IDEA

You'll first need to install the Lombok plugin for IDEA. Then import the project as you normally would.

 ## Convenience setup for developers
 
 If you work on changes in core which are relevant to seed node and run a local developer seed node you might prefer to have your changes reflected in the seed node without waiting until the changes are merged into master.
By default Bisq-seednode uses the jar file created by jitpack from the latest master version.
If you want to use your locally built maven artefacts from exchange you can change the dependency to core in the pom.xml to:
 
    <dependency>
        <groupId>io.bisq</groupId>
        <artifactId>core</artifactId>
        <version>[VERSION]</version>
    </dependency>

Furthermore you can import the Bisq-seednode project as module in your exchange project in IntelliJ, so that each code change will get reflected without external maven build as well you can debug a seednode instance directly from IntelliJ.

## Further information

 - See the [docs](docs) directory
 - See the [Seednode Maintainer](https://github.com/bisq-network/roles/issues/6) and [Operator](https://github.com/bisq-network/roles/issues/15) roles
