package com.pingan.socket

import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import java.net.Socket
import java.util.Random

/**
  * Created by Administrator on 2017/4/4.
  */
class SparkSocketThread(cli: Socket) extends Runnable{

  override def run() = {
    val br=new BufferedReader(new InputStreamReader(cli.getInputStream))
    val pw=new PrintWriter(cli.getOutputStream,true)
    while(true){
      Thread.sleep(200)
      val data=generateData(new Random().nextInt(5))
      //println(data)
      pw.println(data)
    }
  }

  def generateData(value:Int):String={
    val strs=List("Hello","my","friend","I","really","miss","you")
    strs(value)
  }
}
