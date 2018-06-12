#!/bin/bash

./gradlew --include-build ../common --include-build ../assets --include-build ../p2p --include-build ../core build -x test shadowJar
