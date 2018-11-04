package is.spark.tests.unit

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.FunSuite

class TestDF3 extends FunSuite with DataFrameSuiteBase {
  test("simple test") {
    val sqlCtx = sqlContext
    import sqlCtx.implicits._

    val input1 = sc.parallelize(List[(Int, Double)]((1, 1.1), (2, 2.2), (3, 3.3))).toDF
    val input2 = sc.parallelize(List[(Int, Double)]((1, 1.2), (2, 2.3), (3, 3.4))).toDF
    assertDataFrameApproximateEquals(input1, input2, 0.11) // equal

    intercept[org.scalatest.exceptions.TestFailedException] {
      assertDataFrameApproximateEquals(input1, input2, 0.05) // not equal
    }
  }
}

