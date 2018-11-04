package is.spark.tests.integration

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import is.spark.examples.jobs.OrdersTransform
import org.apache.spark.rdd.RDD
import org.scalatest.Matchers._
import org.scalatest.{FeatureSpec, GivenWhenThen}

class TestWordCount extends FeatureSpec with GivenWhenThen with DataFrameSuiteBase {
  feature("Word Count") {
    scenario("When dealing with rdds") {

      Given("A list of words")
      val input = List("hi", "hi holden", "bye")


      When("Parralising the RDD")
      val rdd = tokenize(sc.parallelize(input)).collect().toList

      Then("We should get the expected output")
      val expected = List(List("hi"), List("hi", "holden"), List("bye"))
      rdd should be (expected)

      val transf = OrdersTransform
      transf.executeNew()
    }
    def tokenize(f: RDD[String]) = {
      f.map(_.split(" ").toList)
    }
  }
}