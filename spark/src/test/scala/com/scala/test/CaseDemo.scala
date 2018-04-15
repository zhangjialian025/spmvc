package com.scala.test

/**
  * Created by Administrator on 2017/3/20.
  */
//case模式匹配
class CaseDemo{

}

object CaseDemo extends App{//继承App类可直接调用函数
val value=5   //不需要分号隔开
def getType(obj:Any):Unit= obj match {
  case  x:String=>println("String")
  case  x:Int=>println("Int")
  case  x:Float=>println("Float")
  case  x:Double=>println("Double")
  case  _=>println("other type")
}
  val result= value match {
    //第一种用法
    /*case 1 =>println("1")
    case 2 =>println("2")
    case 3 =>println("3")
    case 4 =>println("4")
    case 5 =>println("5")
    case _=>println("others")*/
    //scala新用法:case与if结合使用
    case i if i<0  =>println("小于0")
    case i if i==0  =>println("等于0")
    case i if i>0  =>println("大于0")

  }
  //result
  //getType(1.88d)

  //高阶函数:map reduce filter函数等
  val list1=List(1,2,3,4,5,6)
  val list2=List(3,4,6,8,9,6)
  //list1+=(4)
  val list=List(list1,list2).flatten //组合
  println((1 to 10).toList.foldLeft(0)(_+_)) //累加0+1 1+2 3+3
  //val set=Set(1,2,3,4,3,2)
  //println(list)
  //println(set)
  //val newList=list.map((x:Int)=>2*x) //(1)
  //val newList=list.map((x)=>2*x)//(2)
  val newList=list1.map(x=>2*x)//(3)
  //val newList=list.map(2*_)//(4):最常见的方法
  //foreach用法
  //list.foreach(println((x:Int)=>(x.toString())))

  println("++++++++++++++++++++++++++++++++++++++++")
  val list3=List(("zhangsan",2),("lisi",3))
  println(list3(1)._1)



  println("++++++++++++++++++++++++++++++++++++++++")
  //数组
  val tt=Array(1,2,3)
  println(tt(0)+"  "+tt(1)+" "+tt(2))
  //元组
  val arr=("jdbc:mysql://localhost:3306:oradb","localhost",8080)
  println(arr._1+"  "+arr._2+" "+arr._3)
  println("==============================")

  //map
  //val map1=Map("one"->1)
  var map2=Map("one"->1,"two"->2,"thire"->3)
  var map3=Map("thire"->3,"four"->4,"six"->6)
  map2+=("seven"->7)
  val allmap=map2++map3  //组合





  //println(map2.toString())
  println(allmap.toString())
  //取值
  println(map2.get("two"))  //(1)
  println(map2("two"))  //(2)
  println(map2.getOrElse("two","none"))  //(3)
  //println(map2.get("two").getOrElse("none"))
}
