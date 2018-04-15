package com.pingan.spark.streaming.kafka

import java.util.Properties

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer

/**
  * Created by Administrator on 2017/4/12.
  */
object KafkaUtil {
  def getsProducer():KafkaProducer[String,String]={
    val props = new Properties()
    props.put("bootstrap.servers", "hadoop04:9092,hadoop05:9092,hadoop06:9092");////Kafka集群连接串
    props.put("retries", "3") //发送失败时Producer端的重试次数，默认为0
    props.put("batch.size", "16384") //当同时有大量消息要向同一个分区发送时，Producer端会将消息打包后进行批量发送。
    props.put("linger.ms", "1") //发送消息前等待的毫秒数，与batch.size配合使用
    /**
      * acks：broker消息确认的模式，有三种：
      * 0：不进行消息接收确认，即Client端发送完成后不会等待Broker的确认
      * 1：由Leader确认，Leader接收到消息后会立即返回确认信息
      * all(-1)：集群完整确认，Leader会等待所有in-sync的follower节点都确认收到消息后，再返回确认信息
      */
    props.put("acks", "all")
    //消息key/value的序列器Class，根据key和value的类型决定
    props.put("buffer.memory", "33554432")//消息缓冲池大小
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String,String](props)
  }

  def getCustomer:KafkaConsumer[String, String] ={
    val props = new Properties()
    props.put("bootstrap.servers", "hadoop04:9092,hadoop05:9092,hadoop06:9092") //Kafka集群连接串
    props.put("group.id", "kafka-test")  //customer group
    props.put("enable.auto.commit", "true")    //是否自动提交已拉取消息的offset
    props.put("auto.commit.interval.ms", "1000")  //自动提交offset的间隔毫秒数
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    //max.partition.fetch.bytes：每次从单个分区中拉取的消息最大尺寸（byte），默认为1M
    new KafkaConsumer[String,String](props)
  }

}
