package com.pingan.socket

import java.net.ServerSocket
import java.util.concurrent.Executors

/**
  * Created by Administrator on 2017/4/4.
  */
class ServerSocketListener {

}
object ServerSocketListener {
  val port=9889
  var isRunning=true
  def main(args: Array[String]): Unit = {
    val server=new ServerSocket(port)
    val executors=Executors.newCachedThreadPool()//新建线程池
    println("启动socket Server 成功......")
    while(isRunning){
      val cli=server.accept()
      executors.execute(new SparkSocketThread(cli))
    }
    server.close()//关闭socket
    println("socket server 退出......")
  }
}
