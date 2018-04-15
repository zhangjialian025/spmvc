package com.pingan.spark.streaming.kafka

import java.util.UUID

import org.apache.kafka.clients.producer.ProducerRecord

/**
  * Created by Administrator on 2017/4/12.
  */
object kafkaProducer {
  def main(args: Array[String]): Unit = {
    val topic="kafka-topic"
    val producer = KafkaUtil.getsProducer
    for (i <- 1 to 100){
      System.out.println("producer sends message: " + i)
      var uuid=UUID.randomUUID().toString.replaceAll("-","")
      producer.send(new ProducerRecord[String, String](topic, "key="+uuid + "", i + "")) //发布信息
      Thread.sleep(1000)
    }
    producer.close()
  }
}
