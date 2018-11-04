package is.spark.examples.jobs

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SaveMode

object OrdersJob {
  var saveRootPath: String = "processed";
  var saveProceedFolderName: String = "processed";
  var savePipelineFolderName: String = "saveBaseFolderName";
  var savePipelineVersionName: String = "savePipelineVersionName";

  //override protected val transform = OrdersTransform

//    transform.executeNew(orders, payments)

  def writeToParquet()(df: DataFrame): Unit = {
    val pathToWriteParquetTo = new java.io.File(saveRootPath + "/" + saveProceedFolderName + "/" + savePipelineFolderName + "/" + savePipelineVersionName).getCanonicalPath
    df.write.mode(SaveMode.Overwrite).parquet(pathToWriteParquetTo)
  }

}