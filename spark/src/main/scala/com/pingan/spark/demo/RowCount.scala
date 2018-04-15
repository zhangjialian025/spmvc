package com.pingan.spark.demo

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/3/26.
  */
object RowCount {
  def getSc():SparkContext={
    val conf=new SparkConf().setMaster("local[2]")setAppName("count") //获取sparkConf对象，用于获取sparkContext
    return new SparkContext(conf) //通过sparkContext，才能访问Spark服务端
  }

  def main(args: Array[String]): Unit = {
    val sc=getSc()
    val file= sc.textFile("E:/document/others/test").map(_.split("\t"))
   /* val wordcount=file.flatMap(line=>line.split(" ")).map(word=>(word,1)).reduceByKey((x,y)=>x+y).map(x=>(x._2,x._1))
      .sortByKey(false).map(x=>(x._2,x._1))*/
    file.cache()
    val wordcount=file.count.toString
    println(wordcount)
    //保存统计文件到wordcount.txt中
    println("==================开始========================")
    //wordcount.saveAsTextFile("E:/scala/output/wordcount")
    println("====保存至/home/test/tmp/doc/wordcount====")
    println("==================成功========================")

  }
}
