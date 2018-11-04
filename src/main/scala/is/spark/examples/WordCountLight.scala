package is.spark.examples

import is.spark.examples.jobs.OrdersTransform
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCountLight {
    def main(args: Array[String]) {
 
        /* configure spark application */
        val conf = new SparkConf().setAppName("Spark Scala WordCount Example").setMaster("local[1]")
        /* spark context*/
        val sc = new SparkContext(conf)
        val input = List("hi", "hi holden", "bye")
        val rdd = tokenize(sc.parallelize(input)).collect().toList
        val expected = List(List("hi"), List("hi", "holden"), List("bye"))

        val job = OrdersTransform

    }
    def tokenize(f: RDD[String]) = {
        f.map(_.split(" ").toList)
    }

}