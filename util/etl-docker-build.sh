#!/bin/bash

mkdir docker
cd docker
cp /Users/gguo/work/git/kf-portal-etl/kf-portal-etl-pipeline/target/scala-2.11/kf-portal-etl.jar .
cp /Users/gguo/work/data/kf/http/kf_etl.conf .
cp /Users/gguo/work/git/kf-portal-etl/docker/Dockerfile .
cp /Users/gguo/work/git/kf-portal-etl/docker/kf-etl-submit.sh .

docker build --build-arg SPARK_DEPLOY_MODE=client --build-arg SPARK_MASTER=spark://10.11.8.252:6066 -f /Users/gguo/work/git/kf-portal-etl/docker/Dockerfile -t kids-first/etl:1.0.0 .

cd ..
rm -fr docker
