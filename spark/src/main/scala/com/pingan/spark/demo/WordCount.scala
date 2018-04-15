package com.pingan.spark.demo

import com.pingan.spark.common.SparkUtil
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/3/20.
  */
object WordCount {
  def getSc():SparkContext={
    val conf=new SparkConf().setAppName("HelloSpark") //获取sparkConf对象，用于获取sparkContext
    return new SparkContext(conf) //通过sparkContext，才能访问Spark服务端
  }

  def main(args: Array[String]): Unit = {
    val sc=SparkUtil.getScByCluster("HelloSpark")
    val file= sc.textFile("/mytest")
    val wordcount=file.flatMap(line=>line.split(" ")).map(word=>(word,1)).reduceByKey((x,y)=>x+y)
    //保存统计文件到wordcount.txt中
    println("==================开始========================")
    wordcount.saveAsTextFile("/mytest/wordcount")
    println("====保存至/home/test/tmp/doc/wordcount====")
    println("==================成功========================")
  }

}
