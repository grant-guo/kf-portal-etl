package io.kf.etl.processor.common.inject

import com.google.inject.AbstractModule
import com.typesafe.config.Config
import org.apache.spark.sql.SparkSession
import org.apache.hadoop.fs.{FileSystem => HDFS}

abstract class ProcessorInjectModule(val sparkSession: SparkSession,
                                     val hdfs: HDFS,
                                     val appRootPath: String,
                                     val config: Option[Config]) extends AbstractModule{
  type CONTEXT
  type PROCESSOR
  type SOURCE
  type SINK
  type TRANSFORMER

  def getContext(): CONTEXT
  def getProcessor(): PROCESSOR

  def getSource(context: CONTEXT): SOURCE
  def getSink(context: CONTEXT): SINK
  def getTransformer(context: CONTEXT): TRANSFORMER

}
