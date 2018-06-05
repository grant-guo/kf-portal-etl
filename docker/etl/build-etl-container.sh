#!/bin/bash

imagename="kids-first/etl"
imageversion="0.0.1"
sparkmaster="localhost:7077"
docker build --build-arg SPARK_VERSION=2.3.0 --build-arg SPARK_MASTER=${sparkmaster} -t ${imagename}:${imageversion} .