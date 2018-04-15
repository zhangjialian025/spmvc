package com.pingan.spark.demo

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/3/26.
  */
object FlowAnalysis {
  val master="spark://hadoop03:7077"
  //val master="local[1]"
  def getSparkContext(name:String): SparkContext ={
    val conf=new SparkConf//获取SparkConf对象
    conf.setMaster(master).setAppName(name)

    val sc=new SparkContext(conf) //获取SparkContext对象并设置相关参数
    sc.hadoopConfiguration.set("dfs.permissions", "false")
    sc.hadoopConfiguration.set("fs.defaultFS", "hdfs://cluster1")
    sc.hadoopConfiguration.set("dfs.nameservices", "cluster1")
    sc.hadoopConfiguration.set("dfs.ha.namenodes.cluster1", "nn1,nn2")
    sc.hadoopConfiguration.set("dfs.namenode.rpc-address.cluster1.nn1", "hadoop01:9000")
    sc.hadoopConfiguration.set("dfs.namenode.rpc-address.cluster1.nn2", "hadoop02:9000")
    sc.hadoopConfiguration.set("dfs.client.failover.proxy.provider.cluster1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider")
    return sc
  }

  def main(args: Array[String]): Unit = {

    val Array(input,output)=args//输入输出路径
    //SparkContext对象
    val sc=getSparkContext("FlowTest")
   //sc.hadoopConfiguration.addResource("/home/test/spark-2.0.0/conf/core-site.xml")
   // sc.hadoopConfiguration.addResource("/home/test/spark-2.0.0/conf/hdfs-site.xml")
    val flowFile=sc.textFile(input).map(_.split("\t"))  //将输入路径的文件生成RCCS
    flowFile.cache()//缓存至内存中
    val filter=flowFile.map(x=>(x(1),(x(x.length-3).toLong,x(x.length-2).toLong)))
      .reduceByKey((x,y)=>(x._1+y._1,x._2+y._2)).map(x=>(x._2._1+x._2._2,(x._1,x._2._1,x._2._2,x._2._1+x._2._2))).sortByKey(false)
      .map(x=>x._2._1+'\t'+x._2._2+'\t'+x._2._3+'\t'+x._2._4)

    filter.saveAsTextFile(output)

    sc.stop()//关闭连接
  }
}
