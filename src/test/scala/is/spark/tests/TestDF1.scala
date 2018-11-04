package is.spark.tests

import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.SQLContext

import com.holdenkarau.spark.testing.{RDDComparisons, RDDGenerator, SharedSparkContext,DataframeGenerator}

import org.apache.spark.rdd.RDD
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.FunSuite
import org.scalacheck.Arbitrary
import org.scalacheck.Prop._
import org.scalatest.prop.Checkers
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.{FlatSpec, Matchers}


class TestDF1 extends FunSuite with SharedSparkContext with Checkers {
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
