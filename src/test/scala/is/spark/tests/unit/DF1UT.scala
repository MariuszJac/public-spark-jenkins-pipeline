package is.spark.tests.unit

import com.holdenkarau.spark.testing.{DataframeGenerator, SharedSparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalacheck.Prop._
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers


class DF1UT extends FunSuite with SharedSparkContext with Checkers {
  test("assert dataframes generated correctly") {
    val schema = StructType(List(StructField("name", StringType), StructField("age", IntegerType)))
    val sqlContext = new SQLContext(sc)
    val dataframeGen = DataframeGenerator.arbitraryDataFrame(sqlContext, schema)
  
    val property =
      forAll(dataframeGen.arbitrary) {
        dataframe => dataframe.schema === schema && dataframe.count >= 0
      }

    check(property)
  }
}
