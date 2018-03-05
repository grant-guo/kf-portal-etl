package io.kf.etl.processors.test.processor.document

import java.net.URL
import java.sql.DriverManager

import io.kf.etl.context.Context
import io.kf.etl.processors.filecentric.context.FileCentricContext
import io.kf.etl.processors.filecentric.inject.FileCentricInjectModule
import io.kf.etl.processors.filecentric.source.FileCentricSource
import io.kf.etl.processors.filecentric.transform.FileCentricTransformer
import io.kf.etl.processors.repo.Repository
import io.kf.etl.test.common.KfEtlUnitTestSpec

import scala.util.{Failure, Success, Try}

class FileCentricTransformerTest extends KfEtlUnitTestSpec{
  private lazy val module = createModule()

  private lazy val ctx = getContext()

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

  private def calculateParticipantAndGenomicFileWithGroupByParticipantId():Long = {
    val sql =
      """
        SELECT count(*) from (
        SELECT count(*) from
          (select participant.kf_id as kfid, AA.gfid, AA.datatype from participant left join
                   (select sample.participant_id as kfid, BB.gfid, BB.datatype from sample left join
                     (select aliquot.sample_id as kfid, CC.gfid, CC.datatype from aliquot left join
                        (select sequencing_experiment.aliquot_id as kfid, genomic_file.kf_id as gfid, genomic_file.data_type as datatype from sequencing_experiment left join genomic_file on sequencing_experiment.kf_id = genomic_file.sequencing_experiment_id) as CC
                       on aliquot.kf_id = CC.kfid) as BB
                     on sample.kf_id = BB.kfid ) as AA
                  on participant.kf_id = AA.kfid )
            as AAA GROUP BY AAA.kfid ) as AAAA;
      """.stripMargin

    val postgresql = Context.postgresql
    val conn = DriverManager.getConnection("jdbc:postgresql://" + postgresql.host + "/" + postgresql.database, postgresql.user, postgresql.password)
    val stmt = conn.createStatement()
    val rs = stmt.executeQuery(sql)
    rs.next()
    rs.getLong(1)

  }
}
