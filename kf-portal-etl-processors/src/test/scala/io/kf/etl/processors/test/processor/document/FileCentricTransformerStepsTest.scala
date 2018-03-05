package io.kf.etl.processors.test.processor.document

import java.net.URL

import io.kf.etl.context.Context
import io.kf.etl.model.Participant
import io.kf.etl.processors.common.ProcessorCommonDefinitions.{DatasetsFromDBTables, ParticipantToGenomicFiles}
import io.kf.etl.processors.common.step.Step
import io.kf.etl.processors.common.step.impl._
import io.kf.etl.processors.common.step.posthandler.WriteParticipantsToJsonFile
import io.kf.etl.processors.filecentric.context.FileCentricContext
import io.kf.etl.processors.filecentric.inject.FileCentricInjectModule
import io.kf.etl.processors.filecentric.source.FileCentricSource
import io.kf.etl.processors.filecentric.transform.FileCentricTransformer
import io.kf.etl.processors.filecentric.transform.steps._
import io.kf.etl.processors.filecentric.transform.steps.context.StepContext
import io.kf.etl.processors.filecentric.transform.steps.posthandler.WriteFileCentricToJsonFile
import io.kf.etl.processors.repo.Repository
import io.kf.etl.test.common.KfEtlUnitTestSpec
import org.apache.spark.sql.Dataset
import org.scalatest.BeforeAndAfter

import scala.util.{Failure, Success, Try}

class FileCentricTransformerStepsTest extends KfEtlUnitTestSpec with BeforeAndAfter{
  private lazy val module = createModule()

  private lazy val ctx = getContext()
  private lazy val source = getDocumentSource()

  private lazy val transformer = createTransformer()
  private lazy val step_ctx = getStepContext()
  private lazy val allDatasets = getDBTables()

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

  private def createTransformer(): FileCentricTransformer = {
    new FileCentricTransformer(ctx)
  }
  private def getDocumentSource(): FileCentricSource = {
    new FileCentricSource(ctx)
  }

  private def getDBTables(): DatasetsFromDBTables = {
    source.source(Repository(getInputDataPath()))
  }

  private def getStepContext():StepContext = {
    StepContext(
      spark = ctx.sparkSession,
      "filecentric",
      processorDataPath = ctx.getProcessorDataPath(),
      hdfs = ctx.hdfs,
      dbTables = allDatasets
    )
  }


  "Step1" should "join demographic into participant" in {

    val start = System.currentTimeMillis()

    import step_ctx.spark.implicits._
    val step1 = Step("step1", new MergeStudy(step_ctx), new WriteParticipantsToJsonFile(step_ctx, "step1"))
    val step2 = Step("step2", new MergeDemographic(step_ctx), new WriteParticipantsToJsonFile(step_ctx, "step2"))
    val step3 = Step("step3", new MergeDiagnosis(step_ctx), new WriteParticipantsToJsonFile(step_ctx, "step3"))
    val step4 = Step("step4", new MergePhenotype(step_ctx), new WriteParticipantsToJsonFile(step_ctx, "step4"))
    val step5 = Step("step5", new MergeAvailableDataTypesForParticipant(step_ctx), new WriteParticipantsToJsonFile(step_ctx, "step5"))
    val step6 = Step("step6", new MergeFamilyMember(step_ctx), new WriteParticipantsToJsonFile(step_ctx, "step6"))
    val step7 = Step("step7", new MergeSample(step_ctx), new WriteParticipantsToJsonFile(step_ctx, "step7"))
    val step8 = Step("step8", new BuildFileCentric(step_ctx), new WriteFileCentricToJsonFile(step_ctx))

    val output =
      Function.chain(
        Seq(
          step1,
          step2,
          step3,
          step4,
          step5,
          step6,
          step7
        )
      ).andThen(
        step8
      )(ctx.sparkSession.emptyDataset[Participant])

    println(output.collect().size)

    val end = System.currentTimeMillis()

    println(s"transformation runs ${end - start} millis")
  }


}
