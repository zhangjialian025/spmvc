package com.pingan.spark.common
import org.apache.log4j.{Level, Logger}
/**
  * Created by Administrator on 2017/4/4.
  */
object LogUtil{
    def setLevel(level:Level): Unit ={
      val isInit=Logger.getRootLogger.getAllAppenders.hasMoreElements()
      if(!isInit){
        Logger.getRootLogger.setLevel(level)
      }
    }
}
