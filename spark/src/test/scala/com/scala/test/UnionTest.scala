package com.scala.test

/**
  * Created by Administrator on 2017/9/19.
  */
object UnionTest {
  def main(args: Array[String]): Unit = {
    //并集
    //println("并集:"+(Set(1,2,3) | Set(2,4)))
    println("并集:"+(Array(1,2,3).toSet | Array(2,4).toSet))
    //交集
    println("交集:"+(Set(1,2,3) & Set(2,4)))
    //补集
    println("补集:"+(Set(1,2,3) -- Set(2,4)))
  }
}
