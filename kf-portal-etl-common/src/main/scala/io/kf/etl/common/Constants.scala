package io.kf.etl.common

object Constants {
  val CONFIG_FILE_URL = "kf.etl.config"
  val DEFAULT_CONFIG_FILE_NAME = "kf_etl.conf"
  val DEFAULT_APP_NAME = "Kids-First-ETL"
  val ROOT_PACKAGE = "io.kf.etl"
  val PROCESSOR_PACKAGE = "io.kf.etl.processor"
  val POSTGRESQL = "postgresql"
  val MYSQL = "mysql"

  val CONFIG_NAME_SPARK_APP_NAME = s"${ROOT_PACKAGE}.spark.app.name"
  val CONFIG_NAME_SPARK_MASTER = s"${ROOT_PACKAGE}.spark.master"
  val CONFIG_NAME_SPARK_PROPERTIES = s"${ROOT_PACKAGE}.spark.properties"
  val CONFIG_NAME_ES_HOST = s"${ROOT_PACKAGE}.elasticsearch.host"
  val CONFIG_NAME_ES_CLUSTER_NAME = s"${ROOT_PACKAGE}.elasticsearch.cluster_name"
  val CONFIG_NAME_ES_HTTP_PORT = s"${ROOT_PACKAGE}.elasticsearch.http_port"
  val CONFIG_NAME_ES_TRANSPORT_PORT = s"${ROOT_PACKAGE}.elasticsearch.transport_port"
  val CONFIG_NAME_ES_CONFIGS = s"${ROOT_PACKAGE}.elasticsearch.configs"
  val CONFIG_NAME_DATASERVICE_URL = s"${ROOT_PACKAGE}.dataservice.url"
  val CONFIG_NAME_HDFS_FS = s"${ROOT_PACKAGE}.hdfs.defaultFS"
  val CONFIG_NAME_HDFS_PATH = s"${ROOT_PACKAGE}.hdfs.root"
  val CONFIG_NAME_AWS_S3_PROFILE = s"${ROOT_PACKAGE}.aws.s3.profile"
  val CONFIG_NAME_PROCESSORS = s"${ROOT_PACKAGE}.processors"
  val CONFIG_NAME_PIPELINE = s"${ROOT_PACKAGE}.pipeline"
  val CONFIG_NAME_POSTGRESQL_HOST = s"${ROOT_PACKAGE}.${POSTGRESQL}.host"
  val cONFIG_NAME_POSTGRESQL_DATABASE = s"${ROOT_PACKAGE}.${POSTGRESQL}.database"
  val CONFIG_NAME_POSTGRESQL_USER = s"${ROOT_PACKAGE}.${POSTGRESQL}.user"
  val CONFIG_NAME_POSTGRESQL_PASSWORD = s"${ROOT_PACKAGE}.${POSTGRESQL}.password"
  val CONFIG_NAME_MYSQL_HOST = s"${ROOT_PACKAGE}.${MYSQL}.host"
  val cONFIG_NAME_MYSQL_DATABASE = s"${ROOT_PACKAGE}.${MYSQL}.database"
  val CONFIG_NAME_MYSQL_USER = s"${ROOT_PACKAGE}.${MYSQL}.user"
  val CONFIG_NAME_MYSQL_PASSWORD = s"${ROOT_PACKAGE}.${MYSQL}.password"
  val CONFIG_NAME_MYSQL_PROPERTIES = s"${ROOT_PACKAGE}.${MYSQL}.properties"
  val CONFIG_NAME_DATA_PATH = "data_path"
  val CONFIG_NAME_DUMP_PATH = "dump_path"
  val CONFIG_NAME_WRITE_INTERMEDIATE_DATA = "write_intermediate_data"
  val CONFIG_NAME_HPO = s"${ROOT_PACKAGE}.hpo"
  val CONFIG_NAME_ALIASACTIONENABLED = "aliasActionEnabled"
  val CONFIG_NAME_ALIASHANDLERCLASS = "aliasHandlerClass"
  val DEFAULT_ALIASHANDLERCLASS = "io.kf.etl.processors.index.posthandler.impl.ReplaceIndexInAlias"
  val DEFAULT_FILE_CENTRIC_ALIAS = "file_centric"
  val DEFAULT_PARTICIPANT_CENTRIC_ALIAS = "participant_centric"
  val HPO_REF_DATA = "hpo_ref"
  val HPO_GRAPH_PATH = "graph_path"
  val FILE_CENTRIC_MAPPING_FILE_NAME = "file_centric.mapping.json"
  val PARTICIPANT_CENTRIC_MAPPING_FILE_NAME = "participant_centric.mapping.json"

  val FILE_CENTRIC_PROCESSOR_NAME = DEFAULT_FILE_CENTRIC_ALIAS
  val PARTICIPANT_CENTRIC_PROCESSOR_NAME = DEFAULT_PARTICIPANT_CENTRIC_ALIAS
  val RELEASE_TAG = "release_tag"
  val RELEASE_TAG_CLASS_NAME = "release_tag_class_name"

  val DOWNLOAD_DEFAULT_DATA_PATH = "download"
  val FILECENTRIC_DEFAULT_DATA_PATH = "filecentric"
  val PARTICIPANTCENTRIC_DEFAULT_DATA_PATH = "participantcentric"
  val INDEX_DEFAULT_DATA_PATH = "index"
  val PARTICIPANT_COMMON_DEFAULT_DATA_PATH = "participantcommon"

  val DATASOURCE_OPTION_PROCESSOR_NAME = "kf.etl.processor.name"
  val SPARK_DATASOURCE_OPTION_PATH = "path"
  val HDFS_DATASOURCE_SHORT_NAME = "kf-hdfs"
  val RAW_DATASOURCE_SHORT_NAME = "kf-raw"

  val PROCESSOR_DOCUMENT = "document"
  val PROCESSOR_INDEX = "index"

}
