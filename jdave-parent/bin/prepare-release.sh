#!/bin/sh

export JAVA_HOME=/usr/java/jdk1.5
export PATH=$JAVA_HOME/bin:$PATH

mvn release:prepare
