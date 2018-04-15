package com.pingan.socket

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
/**
  * Created by Administrator on 2017/4/4.
  */
object ClientSocket {
  /**
    * String 单词，key值
    * Seq[Int]当前所有value值
    *  Option[Int]上一版旧的状态值
    * @return
    */
  def updateFunc=(iter:Iterator[(String, Seq[Int], Option[Int])])=>{
    //iter.flatMap(x=>Some(x._2.sum+x._3.getOrElse(0)).map(y=>(x._1,y)))
    iter.map(x=>(x._1,x._2.sum+x._3.getOrElse(0)))
  }

def main(args: Array[String]): Unit = {
  val conf=new SparkConf().setMaster("local[2]").setAppName("SparkStreamingDemo")//获取sparkconf
  val ssc=new StreamingContext(conf,Seconds(3))//获取StreamingContext对象
  ssc.checkpoint(".")
  val strStream=ssc.socketTextStream("localhost",9889,StorageLevel.MEMORY_AND_DISK)//获取socket数据流
  val count=strStream.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_) //处理数据
  //输出结果
  val countAppend=count.updateStateByKey(updateFunc,new HashPartitioner(ssc.sparkContext.defaultParallelism),true)
  countAppend.print()
  ssc.start()//启动socket
  //等待中断
  ssc.awaitTermination()
}
}
