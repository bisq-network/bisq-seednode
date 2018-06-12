# bisq-seednode
[![Build Status](https://travis-ci.org/bisq-network/bisq-seednode.svg?branch=master)](https://travis-ci.org/bisq-network/bisq-seednode)

## Prerequisite

Be sure to have the bcprov-jdk15on-1.56.jar file inside of the JDKs ext directory:

    [PATH TO JAVA_HOME]/jre/lib/ext/bcprov-jdk15on-1.56.jar
    
## Build

    sh create_jar.sh  (or ./gradlew build -x test shadowJar)

## Run

    java -jar ./build/libs/bisq-seednode-0.7.0-SNAPSHOT-all.jar  [options]

See also [docs/run-on-tor-and-regtest.md](docs/run-on-tor-and-regtest.md)

## Import into IDEA

You'll first need to install the Lombok plugin for IDEA. Then import the project as you normally would.

## Further information

 - See the [docs](docs) directory
 - See the [Seednode Maintainer](https://github.com/bisq-network/roles/issues/6) and [Operator](https://github.com/bisq-network/roles/issues/15) roles
 - See the [Docker related instructions](docker/README.md)
