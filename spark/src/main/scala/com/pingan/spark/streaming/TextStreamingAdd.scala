package com.pingan.spark.streaming

import com.pingan.spark.common.SparkUtil
import org.apache.spark.HashPartitioner
import org.apache.spark.streaming.{Seconds, StreamingContext}

case class Trade(income:Double,expense:Double){
  val surplus=income+expense
}
/**
  * Created by Administrator on 2017/4/4.
  */
object TextStreamingAdd {
  /**
    * String key值
    * Seq[Int]当前所有value值
    *  Option[Int]上一版旧的状态值
    * @return
    */
  def updateFunc=(iter:Iterator[(String, Seq[Trade], Option[Trade])])=> {
    //iter.flatMap(x=>Some(x._2.sum+x._3.getOrElse(0)).map(y=>(x._1,y)))
   // iter.map(x => (x._1, x._2.sum + x._3.getOrElse(0)))
    var income=0.0
    var expense=0.0
    iter.foreach(x =>x._2.foreach{y=>
      income+=y.income
      expense+=y.expense
    })
    iter.map(x =>{
        income+=x._3.get.income
        income+=x._3.get.expense
    })
    iter.map(x=>(x._1,Trade(income,expense)))
  }
  def main(args: Array[String]): Unit = {
    val conf=SparkUtil.getScByCluster("textStreaming")//获取sparkconf,集群模式
    val ssc=new StreamingContext(conf,Seconds(3))//获取StreamingContext对象
    ssc.checkpoint(".")
    val strStream=ssc.textFileStream("hdfs://cluster1/mytest/")
    val temp=strStream.map(_.split("\t")).map(x=>(x(1),(x(2).toDouble,x(3).toDouble)))
      .reduceByKey((x,y)=>(x._1+y._1,x._2+y._2)).map(x=>(x._2._1+x._2._2,(x._1,x._2._1,x._2._2,x._2._1+x._2._2)))//处理数据
    val result=temp.transform(_.sortByKey(false)).map(x=>(x._2._1,Trade(x._2._2,x._2._3)))//排序并处理输出
    val totalResult=result.updateStateByKey(updateFunc,new HashPartitioner(ssc.sparkContext.defaultParallelism),true)
    result.print()
    println("")
    //输出结果
    totalResult.print()
    ssc.start()//启动socket
    //等待中断
    ssc.awaitTermination()
  }
}
