#!/usr/bin/env sh
set -eu

if command -v mvn >/dev/null 2>&1; then
  exec mvn "$@"
fi

if command -v docker >/dev/null 2>&1; then
  exec docker run --rm \
    -v carhub360-maven-cache:/root/.m2 \
    -v "$PWD:/workspace" \
    -w /workspace \
    maven:3.9.6-eclipse-temurin-17 \
    mvn "$@"
fi

echo "Maven is not installed and Docker is not available." >&2
exit 1
