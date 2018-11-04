package is.spark.tests.unit

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.FunSuite

class TestDF2 extends FunSuite with DataFrameSuiteBase {
  test("simple test") {
    val sqlCtx = sqlContext
    import sqlCtx.implicits._

    val input1 = sc.parallelize(List(1, 2, 3)).toDF
    assertDataFrameEquals(input1, input1) // equal

    val input2 = sc.parallelize(List(4, 5, 6)).toDF
    intercept[org.scalatest.exceptions.TestFailedException] {
        assertDataFrameEquals(input1, input2) // not equal
    }
  }
}