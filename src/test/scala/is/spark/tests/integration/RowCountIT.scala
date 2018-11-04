package is.spark.tests.integration

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions.{col, explode}
import org.scalatest.Matchers._
import org.scalatest.{FeatureSpec, GivenWhenThen}

class RowCountIT extends FeatureSpec with GivenWhenThen with DataFrameSuiteBase {
  feature("Raw Extract") {
    scenario("When reading raw data") {

      Given("Some Json Data")
      //val sampleJson =
      //  """ |{ |	"customers": [{ |	"id": 1, |	"email": "abc1@abc.com" |	}, |	{ |	"id": 2, |	"email": "abc2@abc.com" |	}, |	{ |	"id": 3, |	"email": "abc3@abc.com" |	} |	] |} """.stripMargin

      val sampleJson = """{"customers": [{"value": "foo"}, {"value": "bar"}]}"""

          
      val df = sqlContext.read.json(sc.parallelize(Seq(sampleJson)))
        .withColumn("customers",explode(col("customers")))


      When("We Save To Parquet")
      val pathToWriteParquetTo = "out/output.parquet"
      df.write.mode(SaveMode.Overwrite).parquet(pathToWriteParquetTo)

      Then("We should clean and standardize the output to parquet")
      val expectedParquet = spark.read.parquet(pathToWriteParquetTo)

      Then("We should have the correct number of rows")
      val actualRowCount = expectedParquet.select("customers").count()
      actualRowCount should not be 0
      actualRowCount should be (2)
    }
  }
}