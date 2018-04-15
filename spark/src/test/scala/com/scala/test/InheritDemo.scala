package com.scala.test

/**
  * Created by Administrator on 2017/3/19.
  */


class InheritDemo {

}
//private[this]只能在本类的方法和属性中调用，对象调用会异常
//主构造器执行时，会执行类中的所有语句
//主构造器参数可在类上，参数最终会被编译成字段，如果构造器参数不带val or var，则相当与private[this]
//副构造器需要继承主构造器才能生效
class Person(val name:String,val age:Int){
  println("you　will create a object:Person")
  var a:String =_
  var b :Int =_
  //private[this] val gender = "male"
  var gender:String =_
  def this(name:String,age:Int,gender:String){
    this(name,age)
    this.gender=gender
  }
}

class Student(override val name:String, override val age:Int) extends Person(name,age){
  println("you　will create a object:Student")
  override def toString="Overvide toString Success"
}

object Basic2{
  def main(args: Array[String]): Unit = {
    /*val p=new Person("jialian",25,"male")
    print(p.name+" "+p.age+" "+p.gender)*/
    val s=new Student("jingqin",23)
    print(s.name+" "+s.age)
  }
}
