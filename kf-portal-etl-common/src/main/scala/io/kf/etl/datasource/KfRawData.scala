package io.kf.etl.datasource

import io.kf.etl.transform.ProtoBuf2StructType
import io.kf.model.Doc
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.sql.sources.{BaseRelation, TableScan}
import org.apache.spark.sql.types.StructType

class KfRawData(override val sqlContext: SQLContext) extends BaseRelation with TableScan{

  override val schema: StructType = ProtoBuf2StructType.parseDescriptor(Doc.scalaDescriptor)

  override def buildScan(): RDD[Row] = ???


}

object KfRawData {
  def apply(sc: SQLContext): KfRawData = {
    new KfRawData(sc)
  }
}