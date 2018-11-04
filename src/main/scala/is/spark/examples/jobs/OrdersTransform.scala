package is.spark.examples.jobs

import org.apache.spark.sql.DataFrame
import scala.tools.nsc.transform.Transform
import org.apache.spark.sql.functions.lit

object OrdersTransform {
  // name of table   override val name: String = "orders"
  // schema version   override val version: Int = 1

  def executeNew(orders: DataFrame, payments: DataFrame): DataFrame = {
   val factTable = orders.withColumn("cool", lit("dude"))
    factTable
  }
  def executeNew()= {
    print("ok")
  }
}