package com.scala.HighFunc


import scala.collection.mutable.ArrayBuffer

/**
  * Created by Administrator on 2017/4/10.
  */

class ImplictDemo {

}
class ArrayTest[T](var arr:ArrayBuffer[T]){
  def setArray(t:T):Unit={
    arr+=t
  }

  def myMap[M](fun:(T)=>M):ArrayBuffer[M]={
    val buff=new ArrayBuffer[M]()
    arr.foreach(x=>buff+=fun(x))
    buff
  }
}
object Context{
  //隐式类
  implicit class ImpArray[T](a:ArrayTest[T]){
      def getHello(): Unit =println("good")
      def getArray():ArrayBuffer[T]=a.arr
  }
  //隐式方法
  implicit def getFunc[T](arr:ArrayBuffer[T])= new ArrayTest(arr)
  //隐式参数
  implicit val ss:String="nice"
}


//object对象
object ImplictDemo{
  def main(args: Array[String]): Unit = {
    import Context._
    val buffer=new ArrayBuffer[String]()
    buffer+="you are right"
    val arrayTest=new ArrayTest(buffer)
    arrayTest.setArray("I love you")
    arrayTest.setArray("you understand me")

    //使用隐式转换
    //1、隐式类加强：扩展某个类方法
    arrayTest.getArray().foreach(println)
    //arrayTest.getArray().foreach(println)
    arrayTest.getHello()
    //2.隐式方法：扩展某个类方法
    val s=buffer.myMap(x=>x.toString().split(" "))
    var data=""
    s.foreach{
      x=>x.foreach(x=>data+=x+"===")
        println(data)
        data=""
    }

    //隐式参数实现类方法加强：类型匹配
    def getInfo(implicit info:String):Unit={
      println(info)
    }
    getInfo


  }
}
