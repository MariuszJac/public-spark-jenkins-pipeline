package is.spark.examples.jobs.base

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SaveMode

class Job {
  
  var saveRootPath: String = "processed";
  var saveProceedFolderName: String = "processed";
  var savePipelineFolderName: String = "saveBaseFolderName";
  var savePipelineVersionName: String = "savePipelineVersionName";
  
  def writeToParquet()(df: DataFrame): Unit = {
    val pathToWriteParquetTo = new java.io.File(saveRootPath+"/"+saveProceedFolderName+"/"+savePipelineFolderName+"/"+savePipelineVersionName).getCanonicalPath
    df.write.mode(SaveMode.Overwrite).parquet(pathToWriteParquetTo)
  }
}