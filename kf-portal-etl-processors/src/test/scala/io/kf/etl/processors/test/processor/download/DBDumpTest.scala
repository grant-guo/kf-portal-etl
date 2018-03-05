package io.kf.etl.processors.test.processor.download

import java.io.File
import java.net.URL

import io.kf.etl.context.Context
import io.kf.etl.processors.common.exceptions.KfExceptions.{CreateDumpDirectoryFailedException, DataDumpTargetNotSupportedException}
import io.kf.etl.processors.download.context.DownloadContext
import io.kf.etl.processors.download.dump._
import io.kf.etl.processors.download.inject.DownloadInjectModule
import io.kf.etl.test.common.KfEtlUnitTestSpec
import org.apache.commons.io.FileUtils
import org.scalatest.BeforeAndAfter

class DBDumpTest extends KfEtlUnitTestSpec with BeforeAndAfter{

  lazy val context = getDownloadContext()

  private def getDownloadContext(): DownloadContext = {
    val module = new DownloadInjectModule(Context.sparkSession, null, "", Context.config.processorsConfig.get("download"))
    module.getContext()
  }

  before {
    val url = new URL(s"${context.config.dumpPath}")
    url.getProtocol match {
      case "hdfs" => {

      }
      case "file" => {
        val dir = new File(url.getFile)
        if(dir.exists())
          FileUtils.deleteDirectory(dir)
        dir.mkdir() match {
          case false => throw CreateDumpDirectoryFailedException(url)
          case true =>
        }
      }
      case value => throw DataDumpTargetNotSupportedException(url)
    }
  }

  "PostgresqlDump.dump" should "dump data service tables into targed directory" in {
    PostgresqlDump.dump(context)
  }

  "MysqlDump.dump" should "dump mysql graph_path table into targeted directory" in {
    MysqlDump.dump(context)
  }

  "DataSourceDump" should "dump postgresql and mysql table into targeted directory" in {
    new DataSourceDump(context).dump()
  }
}
