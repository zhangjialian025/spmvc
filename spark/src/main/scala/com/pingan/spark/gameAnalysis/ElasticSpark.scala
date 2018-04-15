package com.pingan.spark.gameAnalysis

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/9/23.
  */
class ElasticSpark {

}

/**
  * spark通过条件从对应的Elastic服务器获取信息进行分析
  */
object ElasticSpark{
  def main(args: Array[String]): Unit = {
    val conf=new SparkConf().setAppName("ElasticSpark-game").setMaster("local[2]")//获取sprakConf对象
    conf.set("es.codes","192.168.0.21,192.168.0.22,192.168.0.23") //设置es集群IP地址
    conf.set("es.port","9200")//指定es端口
    conf.set("es.index.auto.create","true")//设置可以自动创建索引
    //创建sparkcontext对象
    val sc = new SparkContext(conf)
    val query:String="""{
      "query" : {
        "filtered" : {
          "filter" : {
              "bool" : {
                "must" : [
                  {"term" : {"access.type" : "1"}},
                  {
                    "range" : {"access.time" :{
                        "gte":"$start",
                        "lte":"$end"
                      }
                    }
                  }
                ]
              }
           }
        }
      }
    }"""
   /* val esRdd = sc.esRDD("accesslogs",query)//根据查询条件获取elastic结果
    println(esRdd.collect().toBuffer)
    println(esRdd.collect().size)*/
  }
}

