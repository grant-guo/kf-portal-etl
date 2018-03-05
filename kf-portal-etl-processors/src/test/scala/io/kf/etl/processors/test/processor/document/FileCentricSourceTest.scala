package io.kf.etl.processors.test.processor.document

import java.net.URL
import java.sql.DriverManager

import io.kf.etl.common.conf.MysqlConfig
import io.kf.etl.context.Context
import io.kf.etl.processors.filecentric.context.FileCentricContext
import io.kf.etl.processors.filecentric.inject.FileCentricInjectModule
import io.kf.etl.processors.filecentric.source.FileCentricSource
import io.kf.etl.processors.repo.Repository
import io.kf.etl.test.common.KfEtlUnitTestSpec

import scala.util.{Failure, Success, Try}

class FileCentricSourceTest extends KfEtlUnitTestSpec{

  private lazy val module = createModule()

  private lazy val ctx = getContext()

  "A DocumentSource" should "read the output from DownloadProcessor and load parquet files into Spark" in {

    val source = new FileCentricSource(ctx)

    val tables = source.source(Repository(getInputDataPath()))

    assert(
      tables.graphPath.collect().size == readCountsOfGraphPathTable()
    )

  }

  private def getContext(): FileCentricContext = {
    module.getContext()
  }

  private def createModule(): FileCentricInjectModule = {
    new FileCentricInjectModule(Context.sparkSession, null, "", Context.config.processorsConfig.get("document"))
  }

  private def getInputDataPath():URL = {
    Try(Context.config.processorsConfig.get("download").get.getString("data_path")) match {
      case Success(path) => new URL(path)
      case Failure(_) => {
        new URL(s"${System.getProperty("java.io.tmpdir")}/kf/download")
      }
    }
  }

  private def readCountsOfGraphPathTable():Long = {

    val mysqlConfig = Context.config.processorsConfig.get("download").get.getConfig("hpo.mysql")

    val mysql = MysqlConfig(host = mysqlConfig.getString("host"), database = mysqlConfig.getString("database"), user = mysqlConfig.getString("user"), password = mysqlConfig.getString("password"))

    val conn = DriverManager.getConnection("jdbc:mysql://" + mysql.host + "/" + mysql.database, mysql.user, mysql.password)
    val stmt = conn.createStatement()
    val rs = stmt.executeQuery(s"select count(*) from graph_path")
    rs.next()
    rs.getLong(1)
  }

}
