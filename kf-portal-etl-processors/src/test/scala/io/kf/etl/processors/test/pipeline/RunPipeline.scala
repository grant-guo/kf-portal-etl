package io.kf.etl.processors.test.pipeline

import io.kf.etl.pipeline.Pipeline
import io.kf.etl.test.common.KfEtlUnitTestSpec

class RunPipeline extends KfEtlUnitTestSpec {

  "A Pipeline" should "run all processors" in {
    val start = System.currentTimeMillis()
    Pipeline.run()
    val end = System.currentTimeMillis()

    println(s"Pipeline takes ${end - start} millis")
  }

}
