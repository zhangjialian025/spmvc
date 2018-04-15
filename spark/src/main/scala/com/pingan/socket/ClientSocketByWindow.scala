package com.pingan.socket

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Administrator on 2017/4/4.
  */
object ClientSocketByWindow {
  /**
    * String 单词，key值
    * Seq[Int]当前所有value值
    *  Option[Int]上一版旧的状态值
    * @return
    */

def main(args: Array[String]): Unit = {
  val conf=new SparkConf().setMaster("local[2]").setAppName("SparkStreamingDemo")//获取sparkconf
  val ssc=new StreamingContext(conf,Seconds(2))//获取StreamingContext对象
  ssc.checkpoint(".")
  val strStream=ssc.socketTextStream("localhost",9889,StorageLevel.MEMORY_AND_DISK)//获取socket数据流
  val temp=strStream.flatMap(_.split(" ")).map((_,1)) //处理数据
  //输出结果
  val windowCount=temp.reduceByKeyAndWindow(_+_,_-_,Seconds(8),Seconds(4))
  windowCount.print()
  ssc.start()//启动socket
  //等待中断
  ssc.awaitTermination()
}
}
