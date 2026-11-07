#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p bin
javac -cp "lib/*" -d bin src/*.java
java -cp "bin:lib/*" App
