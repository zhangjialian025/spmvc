import AssemblyKeys._

name := "spark"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(// 引入Spark dependency
  "org.apache.spark" % "spark-core_2.11" % "2.0.0",
  "org.apache.spark" % "spark-streaming_2.11" % "2.0.0",
  "org.apache.spark" % "spark-streaming-flume_2.11" % "2.0.0",
  "org.apache.spark" % "spark-sql_2.11" % "2.0.0",
  "org.apache.spark" % "spark-hive_2.11" % "2.0.0",
  "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.0.0",
  "org.apache.kafka" % "kafka-clients" % "0.10.1.0",
  "mysql"%"mysql-connector-java"%"5.1.17",
  "net.sf.jopt-simple" % "jopt-simple" % "4.3",
  "joda-time" % "joda-time" % "2.0",
  "redis.clients"%"jedis"%"2.8.1",
  "org.elasticsearch"%"elasticsearch"%"2.4.6",
  "org.elasticsearch"%"elasticsearch-spark_2.11"%"2.1.0")

//该声明包括assembly plug-in功能
assemblySettings

// 使用 assembly plug-in配置jar
jarName in assembly := "my-project-assembly.jar"

// 从我们的assembly JAR中排除Scala, 因为Spark已经绑定了Scala
assemblyOption in assembly :=(assemblyOption in assembly).value.copy(includeScala = false)




