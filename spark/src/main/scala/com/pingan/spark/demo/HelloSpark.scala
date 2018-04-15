package com.pingan.spark.demo

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/3/20.
  */
class HelloSpark {

}

object HelloSpark{
  def main(args: Array[String]): Unit = {
    val conf=new SparkConf().setAppName("HelloSpark") //获取sparkConf对象，用于获取sparkContext
    val sc=new SparkContext(conf) //通过sparkContext，才能访问Spark服务端
    val file= sc.textFile("/home/test/tmp/doc/test.txt")
    val count=file.count()  //计算行数
    val firstLine=file.first() //第一行
    println("行数:"+count)
    println("第一行内容:"+firstLine)
    file.collect().foreach(x=>println(x)) //输出每一行内容
    val worldLine=file.filter(line=>line.contains("world")) //过滤筛选出存在world字段的行
    println("=================过滤筛选出存在world字段的行:开始=============================")
    worldLine.collect().foreach(x=>println(x))//输出存在world的行
    println("=================过滤筛选出存在world字段的行:结束============================")
  }
}
