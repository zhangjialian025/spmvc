package com.pingan.spark.common


import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/4/4.
  */
object SparkUtil {
  val master="spark://hadoop03:7077"
  //val master="local[1]"
  def getScByCluster(name:String): SparkContext ={
   val conf=getScConf(master,name)//获取SparkConf对象
    val sc=new SparkContext(conf) //获取SparkContext对象并设置相关参数
    //val sc= getSparkSession(master,name).sparkContext
    sc.hadoopConfiguration.set("dfs.permissions", "false")
    sc.hadoopConfiguration.set("fs.defaultFS", "hdfs://cluster1")
    sc.hadoopConfiguration.set("dfs.nameservices", "cluster1")
    sc.hadoopConfiguration.set("dfs.ha.namenodes.cluster1", "nn1,nn2")
    sc.hadoopConfiguration.set("dfs.namenode.rpc-address.cluster1.nn1", "hadoop01:9000")
    sc.hadoopConfiguration.set("dfs.namenode.rpc-address.cluster1.nn2", "hadoop02:9000")
    sc.hadoopConfiguration.set("dfs.client.failover.proxy.provider.cluster1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider")
    sc
  }

  def getSc(name:String):SparkContext={
    val conf=getScConf("local[2]",name) //获取sparkConf对象，用于获取sparkContext
    new SparkContext(conf) //通过sparkContext，才能访问Spark服务端
    //getSparkSession("local[2]",name).sparkContex
  }

  def getScConf(master:String,appName:String):SparkConf={
    new SparkConf().setMaster(master).setAppName(appName)
  }
  /**
    * 获取sparkSession对象
    */
  def getSparkSession(master:String,appName:String):SparkSession={
    val session=SparkSession.builder.config(getScConf(master,appName))
    .config("spark.sql.warehouse.dir", "file:///E:/developer/spark-warehouse")
     .enableHiveSupport() //使得SparkSession支持hive，类似于HiveContext
       .getOrCreate()
    //val session = SparkSession.builder().config(getScConf(master,appName)).getOrCreate()
    session
  }

  def getStreamingContext(masterName:String,appName:String,seconds: Int):StreamingContext={
    val conf=getScConf(masterName,appName) //获取sparkConf对象，用于获取sparkContext
    new StreamingContext(conf,Seconds(seconds))//获取StreamingContext对象
  }
}
