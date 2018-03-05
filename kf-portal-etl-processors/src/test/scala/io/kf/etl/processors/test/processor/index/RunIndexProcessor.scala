package io.kf.etl.processors.test.processor.index

import java.net.URL

import com.google.inject.Guice
import io.kf.etl.context.Context
import io.kf.etl.processors.index.IndexProcessor
import io.kf.etl.processors.index.inject.IndexInjectModule
import io.kf.etl.processors.repo.Repository
import io.kf.etl.test.common.KfEtlUnitTestSpec

class RunIndexProcessor extends KfEtlUnitTestSpec{

  "A IndexProcessor" should "read parquet files from DocumentProcessor and store data into Elasticsearch" in {

    val start = System.currentTimeMillis()
    val module = new IndexInjectModule(Context.sparkSession, null, "", Context.config.processorsConfig.get("index"))

    val guice = Guice.createInjector(module)

    val repo = Repository(new URL(Context.config.processorsConfig.get("document").get.getString("data_path") + "/sink"))

    val processor = guice.getInstance(classOf[IndexProcessor])

    processor.process(("file-centric",repo))
    val end = System.currentTimeMillis()

    println(s"IndexProcessor takes ${end - start} millis")

  }

}
