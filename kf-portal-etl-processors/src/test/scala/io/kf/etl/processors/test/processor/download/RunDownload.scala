package io.kf.etl.processors.test.processor.download

import java.sql.DriverManager

import com.google.inject.Guice
import io.kf.etl.context.Context
import io.kf.etl.dbschema.{TParticipant, TPhenotype}
import io.kf.etl.processors.download.DownloadProcessor
import io.kf.etl.processors.download.context.DownloadContext
import io.kf.etl.processors.download.inject.DownloadInjectModule
import io.kf.etl.test.common.KfEtlUnitTestSpec


class RunDownload extends KfEtlUnitTestSpec {

  "DownloadProcessor" should "dump data out of postgresql and mysql" in {

    val module = new DownloadInjectModule(Context.sparkSession, Context.hdfs, Context.rootPath, Context.config.processorsConfig.get("download"))

    val guice = Guice.createInjector(module)

    val downloader = guice.getInstance(classOf[DownloadProcessor])

    downloader.process(Unit)

    val numOfRecords = readCountsOfTable("participant", module.getContext())

    val ctx = module.getContext()
    assert(
      readCountsOfTable("participant", ctx) ==
      readTParticipantDataset(s"${ctx.getJobDataPath()}/participant", ctx)
    )

    assert(
      readCountsOfTable("phenotype", ctx) ==
      readTPhenotypeDataset(s"${ctx.getJobDataPath()}/phenotype", ctx)
    )

  }

  private def readCountsOfTable(table:String, ctx: DownloadContext):Long = {
    val postgresql = ctx.config.postgresql
    val conn = DriverManager.getConnection("jdbc:postgresql://" + postgresql.host + "/" + postgresql.database, postgresql.user, postgresql.password)
    val stmt = conn.createStatement()
    val rs = stmt.executeQuery(s"select count(*) from ${table}")
    rs.next()
    rs.getLong(1)
  }

  private def readTParticipantDataset(path:String, ctx:DownloadContext):Long = {
    import ctx.sparkSession.implicits._
    ctx.sparkSession.read.parquet(path).as[TParticipant].collect().size
  }

  private def readTPhenotypeDataset(path:String, ctx:DownloadContext):Long = {
    import ctx.sparkSession.implicits._
    ctx.sparkSession.read.parquet(path).as[TPhenotype].collect().size
  }
}
