package io.kf.etl.common.conf


import com.typesafe.config.Config
import io.kf.etl.common.Constants._

import scala.collection.convert.WrapAsScala
import scala.util.{Failure, Success, Try}

class KFConfig(private val config: Config){

  lazy val sparkConfig = getSparkConfig()
  lazy val esConfig = getESConfig()
  lazy val hdfsConfig = getHDFSConfig()
  lazy val processorsConfig = getProcessors()
  lazy val pipelineConfig = getPipeline()
  lazy val postgresqlConfig = getPostgresql()
  lazy val mysqlConfig = getMysql()
  lazy val dataServiceConfig = getDataService()
  lazy val awsConfig = getAWSConfig()

  private def getSparkConfig(): SparkConfig = {
    KFConfigExtractors.parseSpark(config)
  }

  private def getESConfig(): ESConfig = {
    KFConfigExtractors.parseElasticsearch(config)
  }

  private def getHDFSConfig(): HDFSConfig = {
    KFConfigExtractors.parseHDFS(config)
  }

  private def getProcessors(): Map[String, Config] = {
    WrapAsScala.asScalaBuffer( config.getConfigList(CONFIG_NAME_PROCESSORS) ).map(config => {
      (config.getString("name"), config)
    }).toMap
  }

  private def getPipeline(): Config = {
    config.getConfig(CONFIG_NAME_PIPELINE)
  }

  private def getPostgresql(): PostgresqlConfig = {
    KFConfigExtractors.parsePostgresQL(config)
  }

  private def getMysql(): MysqlConfig = {
    KFConfigExtractors.parseMySQL(config)
  }

  private def getDataService(): DataServiceConfig = {
    KFConfigExtractors.parseDataService(config)
  }

  private def getAWSConfig(): Option[AWSConfig] = {

    Try{
      config.getString(CONFIG_NAME_AWS_S3_PROFILE)
    } match {
      case Success(aws_profile) => {
        Some(
          AWSConfig(
            s3 = AWSS3Config(
              profile = aws_profile
            )
          )
        )
      }
      case Failure(_) => None
    }

  }

}

object KFConfig{
  def apply(config: Config): KFConfig = {
    new KFConfig(config)
  }
}

case class SparkConfig(appName:String, master:Option[String], properties: Map[String, String])

case class HDFSConfig(fs:String, root:String)

case class ESConfig(cluster_name:String, host:String, http_port:Int, transport_port:Int, configs: Map[String, String])

case class PostgresqlConfig(host:String, database:String, user:String, password:String)

case class MysqlConfig(host:String, database:String, user:String, password:String, properties: Seq[String])

case class DataServiceConfig(url:String)

case class AWSS3Config(profile:String)

case class AWSConfig(s3: AWSS3Config)
