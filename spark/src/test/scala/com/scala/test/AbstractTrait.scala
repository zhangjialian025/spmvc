package com.scala.test

import java.text.SimpleDateFormat
import java.util.Date


/**
  * Created by Administrator on 2017/3/19.
  */
class AbstractTrait {

}

/**
  * 抽象类
  */
abstract class Person1{
  def getName(name:String) //抽象类方法不加方法体，参数参入不加val或var，否则会报错，限定了参数只在继承类使用
  //var name:String
  var age:Int //抽象类字段声明的时候不给赋值
  var gender:String
  var height:Int
}

class Worker extends Person1{
  //var name="jialian"
  override var age=25 //实现抽象类的方法及属性可以不加override，，但建议都写上
  var gender="male"
  override var height=172
  override def getName(name: String){
    println("implement success!"+name)
  }
}

object AbstractTrait {
  def main(args: Array[String]){
      /*val w=new Worker
      w.getName("jialian")*/

   /* var a=new AuditLogger
    a.myLog()*/

    //var logHelp=new LogHelp
    var logHelp=new LogHelp with MessageLogger
    logHelp.log()
  }
}

trait Logger{
  def getLog(log: String): Unit ={
    val format=new SimpleDateFormat("yyyy-MM-dd")
    println("logger:"+format.format(new Date())+"  --- "+log)
  }
}

trait MessageLogger extends Logger{
  override def getLog(log:String){
    val format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    println("MessageLogger:"+format.format(new Date())+"  --- "+log)
  }
}

abstract class LogUtil{
  def log
}

class LogHelp extends LogUtil with Logger{
  override def log(): Unit ={
    getLog("Please check the log from thr program" )
  }
}


/*class AuditLogger extends Logger{
  def myLog(): Unit ={
    getLog("error happen!")
  }
}*/



