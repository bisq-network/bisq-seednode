#!/usr/bin/env bash

if [ "$SKIP_MAVEN_BUILD" != "true" ]; then
    mvn install -DskipTests
fi
