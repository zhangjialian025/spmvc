package com.pingan.spark.streaming

import com.pingan.spark.common.SparkUtil
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Administrator on 2017/4/4.
  */
class FlumeStreaming {

}
object FlumeStreaming {
  def main(args: Array[String]): Unit = {
    val conf=SparkUtil.getScConf("local[2]","FlumeTest")
    val ssc=new StreamingContext(conf,Seconds(5))
    val flumeDs=FlumeUtils.createStream(ssc,"hadoop03",10001,StorageLevel.MEMORY_AND_DISK_2)//获取Flume监听的数据
    flumeDs.count().map(x=>println("received=>"+x+"events!"))

    //处理数据
    val temp=flumeDs.map(x=>new String(x.event.getBody.array()).split("\t"))
      .map(x=>(x(1),(x(2).toDouble,x(3).toDouble)))
      .reduceByKey((x,y) =>(x._1+y._1,x._2+y._2)).map(x=>(x._2._1+x._2._2,(x._1,x._2._1,x._2._2,x._2._1+x._2._2)))//处理数据
    val result=temp.transform(_.sortByKey(false)).map(x=>x._2._1+'\t'+x._2._2+'\t'+x._2._3+'\t'+x._2._4)//排序并处理输出
    result.print()//打印结果
    ssc.start()
    ssc.awaitTermination()
  }
}
