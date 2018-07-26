#!/bin/bash

/Users/gguo/env/spark-2.3.0-bin-hadoop2.7/bin/spark-submit --master spark://127.0.0.1:6066 --deploy-mode cluster --class io.kf.etl.ETLMain --driver-java-options "-Dkf.etl.config=http://localhost:9090/kf_etl.conf" --conf "spark.executor.extraJavaOptions=-Dkf.etl.config=http://localhost:9090/kf_etl.conf" /Users/gguo/work/git/kf-portal-etl/kf-portal-etl-pipeline/target/scala-2.11/kf-portal-etl.jar -study_id_file file:///Users/gguo/work/data/kf/http/study_ids.txt -release_id RE_00000002
