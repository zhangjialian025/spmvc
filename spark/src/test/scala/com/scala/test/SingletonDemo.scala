package com.scala.test

/**
  * Created by Administrator on 2017/3/19.
  */
class SingletonDemo (data:String,age:Int){
  def hello(name:String):String={
    val data:String="haha   ---"
    data+"name:"+name
  }
  //def hello =println("good")
  def hello(){println("good")}
  override def toString():String={
    data+" "+age
  }
}

object SingletonDemo{
  def apply(data:String,age:Int)=new SingletonDemo(data,age)
  /*def SingletonDemo(tpye:String,age:Int): SingletonDemo ={
    new SingletonDemo("Single",25)
  }*/
  def main(args: Array[String]): Unit = {
    val s1=SingletonDemo("nice",25)

    /*
        var s2: SingletonDemo=null
        if(s1!=null)
        s2 =s1*/
    //println(s1)
    println(s1.hello("jialian"))
    s1.hello()   //加()更好
    println(s1.toString())
  }
}


