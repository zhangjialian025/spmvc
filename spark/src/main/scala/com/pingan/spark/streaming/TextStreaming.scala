package com.pingan.spark.streaming

import com.pingan.spark.common.SparkUtil
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Administrator on 2017/4/4.
  */
object TextStreaming {
  def main(args: Array[String]): Unit = {
    val conf=SparkUtil.getScByCluster("textStreaming")//获取sparkconf,集群模式
    val ssc=new StreamingContext(conf,Seconds(3))//获取StreamingContext对象
    val strStream=ssc.textFileStream("hdfs://cluster1/mytest/")
    val temp=strStream.map(_.split("\t")).map(x=>(x(1),(x(2).toDouble,x(3).toDouble)))
      .reduceByKey((x,y)=>(x._1+y._1,x._2+y._2)).map(x=>(x._2._1+x._2._2,(x._1,x._2._1,x._2._2,x._2._1+x._2._2)))//处理数据
    val result=temp.transform(_.sortByKey(false)).map(x=>x._2._1+'\t'+x._2._2+'\t'+x._2._3+'\t'+x._2._4)//排序并处理输出

    //输出结果
    result.print()
    ssc.start()//启动socket
    //等待中断
    ssc.awaitTermination()
  }
}
