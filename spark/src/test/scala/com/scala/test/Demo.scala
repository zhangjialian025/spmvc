package com.scala.test

/**
  * Created by Administrator on 2017/3/19.
  */
class ScalaDemo {

}

object Basic{
  def hello(name:String="spark"): String ={
    "hello:"+name
  }
  def getStrs(strs:String*) ={
    strs.foreach(str=>println(str))
  }
  val a=5
  def add=(x:Int,y:Int)=>x+y
  def main(args:Array[String]){
    //println(hello("scala"))
    println(hello())
    println(add(1,2))
    //String[] strs={"a","b"};
    getStrs("a","b","c","d")
    //if表达式
    val a=1
    var b=2
    b=if(a>1) b+1
    else 0
    println("b="+b)
    //while循环表达式
    var (m,n)=(0,100)
    while(n>=1){
      m=m+n
      n=n-1
    }
    println("m="+m)
    //for表达式
    for(i<- 1 to 10 if i%2==0){
      print(i+" ")
    }
    println()
    for(i<- 1 until 10 if i%2==0){
      print(i+" ")
    }
  }
}