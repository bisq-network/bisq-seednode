#!/bin/bash

./gradlew --include-build ../bisq-common --include-build ../bisq-assets --include-build ../bisq-p2p --include-build ../bisq-core build -x test shadowJar
