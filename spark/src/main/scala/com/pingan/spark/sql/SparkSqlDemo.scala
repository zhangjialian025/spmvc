package com.pingan.spark.sql

import com.pingan.spark.common.SparkUtil
/**
  * Created by Administrator on 2017/4/7.
  */
case class Person(id:String,name:String)

object SparkSqlDemo {
  def main(args: Array[String]): Unit = {

    val spark=SparkUtil.getSparkSession("local[2]","sparkSqlTest")//获取sparkSession对象
    val sc=spark.sparkContext
    //println(sc.master+" "+sc.appName)
    import spark.sqlContext.implicits._
    //val rdd=sc.parallelize(List(("1","zhangsan"),("2","haha"),("3","wangwu"),("4","zhouqi")))
    //val rdd2=sc.textFile("E:/document/others/test").map(_.split("\t"))

    //[1]通过case class字段映射生成DataFrame
   /* println(rdd2.count())
    val df=rdd2.map(x=>Person(x(0),x(1))).toDF()
     df.show()
     df.printSchema()


    //[2]通过structType形式生成schema，生成DataFrame
    import org.apache.spark.sql.types._
    //val schema = new StructType().add("id","string",false).add("name",StringType,true)
    //val schema =StructType(Seq(StructField("id",StringType,true),StructField("name",IntegerType,true)))
     val array=new ArrayBuffer[StructField]()
     array+=StructField("id",StringType,true)
     array+=StructField("name",StringType,true)
     val schema=new StructType(array.toArray)
     val df2=spark.sqlContext.createDataFrame(rdd2.map(x=>Row(x(0),x(1))),schema)

     df2.show()
     df2.printSchema()*/

    import org.apache.spark.sql.functions._

    val line=spark.read.text("E:/document/others/test")
      .as[String]
    //println(line.show())
    val groupWords=line
      .flatMap(_.split("\t"))
      .groupBy($"value")
      .agg(count("*") as "counts")
      .orderBy($"counts" desc)
    groupWords.show()
    //groupWords.write.format("parquet").save("E:/document/others/myparquet")
     sc.stop()//停止资源



    //System.exit(0)
  }
}
