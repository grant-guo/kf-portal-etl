package io.kf.etl.processors.test.processor.document

import java.net.URL

import com.google.inject.Guice
import io.kf.etl.context.Context
import io.kf.etl.processors.filecentric.FileCentricProcessor
import io.kf.etl.processors.filecentric.inject.FileCentricInjectModule
import io.kf.etl.processors.repo.Repository
import io.kf.etl.test.common.KfEtlUnitTestSpec

class RunFileCentricProcessor extends KfEtlUnitTestSpec{
  "A DocumentProcessor" should "load parquet files from DownloadProcessor and generate FileCentric-based parquet file" in {

    val start = System.currentTimeMillis()
    val module = new FileCentricInjectModule(Context.sparkSession, null, "", Context.config.processorsConfig.get("document"))
    val guice = Guice.createInjector(module)

    val processor = guice.getInstance(classOf[FileCentricProcessor])

    processor.process(Repository(new URL(Context.config.processorsConfig.get("download").get.getString("data_path"))))
    val end = System.currentTimeMillis()

    println(s"DocumentProcess takes ${end - start} millis")
  }
}
