package com.pingan.spark.streaming.kafka

import java.util

import com.pingan.spark.common.SparkUtil
import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import scala.collection.JavaConversions._
/**
  * Created by Administrator on 2017/4/15.
  */
class KafkaCustomer{

}
object KafkaCustomers {
  def main(args: Array[String]): Unit = {
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "hadoop04:9092,hadoop05:9092,hadoop06:9092",//Kafka集群连接串
      "group.id" -> "kafka-test",//customer group
      "auto.offset.reset" -> "latest",//latest自动重置偏移量为最新的偏移量
      "enable.auto.commit" -> (false: java.lang.Boolean),//是否自动提交已拉取消息的offset
      "auto.commit.interval.ms"-> "1000",//自动提交offset的间隔毫秒数
      //消息key/value的序列器Class，根据key和value的类型决定
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer]
    )
    val topic=Array("test-topic")
    //max.partition.fetch.bytes：每次从单个分区中拉取的消息最大尺寸（byte），默认为1M
    //创建DStream，返回接收到的输入数据
    val ssc=SparkUtil.getStreamingContext("local[2]","kafka-streaming",3)

    val stream=KafkaUtils.createDirectStream(ssc,
      LocationStrategies.PreferConsistent,ConsumerStrategies.Subscribe[String, String](topic,kafkaParams))
    //每一个stream都是一个ConsumerRecord
    stream.map(s =>(s.key(),s.value())).print()
    ssc.start()
    ssc.awaitTermination()
  }
}


object KafkaCust{
  def main(args: Array[String]): Unit = {
    val consumer :KafkaConsumer[String, String]= KafkaUtil.getCustomer
    val list=new util.ArrayList[String]()
    list.add("kafka-topic")
    consumer.subscribe(list)
    while (true) {
      /*val records:ConsumerRecords[String, String] = consumer.poll(100)
      val iter=records.partitions().iterator()
      while (iter.hasNext) {
        val pr=records.records(classOf[TopicPartition].cast(iter.next()))
        for(record:ConsumerRecord[String,String]<-pr){
          println(record.topic()+"---"+record.key()+"---"+record.value())
        }
      }*/
      val records:ConsumerRecords[String, String] = consumer.poll(100)
      for(par:TopicPartition<-records.partitions()){
        val pr=records.records(par)
        for(record:ConsumerRecord[String,String]<-pr){
          println(record.topic()+"---"+record.key()+"---"+record.value())
        }
      }
    }
  }
}
