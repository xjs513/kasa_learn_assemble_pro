#  基本注意点

>   语句结尾不用;

>   不用 return

>   函数返回类型不用明确指定(递归调用除外)

>    基本类型也都是对象

>    if 表达式有值，块也有值-是它最后一个表达式的值
>
>    赋值表达式本身没有值(Unit)

>    没有 break、continue
>
>    没有 ++、 -- 操作符
>
>    没有受检异常

>    注意 _ 在不同的上下文环境中的含义

>    没有静态方法，但是有单例对象，注意伴生类和伴生对象

>    注意 apply 方法 、 yield、lazy关键字



#  基本数据类型

Any、 AnyRef、 AnyValue、 Unit、 Null、 Nothing

Boolean、String

Byte、 Char、 Short、 Int、 Long、 Float、 Double

##  需要关注的概念

Product、 Seq、 TraversableOnce

#  工具类-集合类

==Array== 	==ArrayBuffer==	List	ListBuffer	Map	TreeMap	LinkedHashMap	元组(组元)

生成器	守卫	==to	until	by==	Range	Clusive

#  1. 类

每个类都有主构造器，主构造器不用 this 方法定义，而是与类定义交织在一起。

- 主构造器的参数直接放置在类名后
- 主构造器会执行类定义中除方法外的所有语句

```scala
var val private[com] private[Person]
package kasa.learn.chapter05
import scala.beans.BeanProperty

// 主构造器	辅助构造器
// 辅助构造器首先调用=》主构造器
class Person(@BeanProperty var id:String, @BeanProperty var address:String) {
  @BeanProperty var name:String = _
  @BeanProperty var age:Int = _
  @BeanProperty var salary:Float = _
  // 没有主构造器或默认生成无参主构造器

  // 辅助构造器1
  def this(id:String, address:String, name:String,age:Int) {
    this(id, address) // 辅助构造器=》首先调用=》主构造器
    this.setName(name)
    this.setAge(age)
  }
  // 方便检测结果重写 toString 方法
  override def toString: String = {
    "Person[id=" + this.getId +
      ", address=" + this.getAddress +
      ", name=" + this.getName +
      ", age=" + this.getAge +
      ", salary=" + this.getSalary +
      "]"
  }
}

object Test2{
  def main(args: Array[String]): Unit = {
    val person = new Person("#3456", "BJ", age = 20, name = "Lily")
    person.setSalary(2902.23F)
    println(person)
  }
}
```

- 如果想让主构造器私有，可以如下编写代码：

```scala
class Person private(id:String, address:String) {......}
```

##  子类 

在 Scala 中几乎可以进行任意的语法嵌套，函数定义 嵌套，类 定义嵌套

```scala
package kasa.learn.chapter05
import scala.collection.mutable.ArrayBuffer
class Network {
  class Member(var  name:String) {
    val contacts = new ArrayBuffer[Member]
  }
  private val members = new ArrayBuffer[Member]
  def join(name:String) = {
    val m =new Member(name)
    members += m
    m
  }
}
object Network {
  def main(args: Array[String]): Unit = {
    val chatter = new Network
    val myFace = new Network
    val fred: chatter.Member = chatter.join("fred")
    val wilma: chatter.Member = chatter.join("wilma")
    fred.contacts += wilma
    val barney = myFace.join("Barney")
    // 不可以这样, 不能将一个 myFace.Member 添加到 chatter.Member 元素缓冲当中
    fred.contacts += barney   // 这里  IDEA 报错
    println(fred.contacts)
    fred.contacts.foreach(m =>  println(m.name))
  }
}
// Scala new chatter.Member; java  chatter.new Member()
```

>   解决方案一：将 Member 类移到别处, 一个不错 的位置是 Network  的半生对象 

```scala
package kasa.learn.chapter05
import scala.collection.mutable.ArrayBuffer
class Network {
  private val members = new ArrayBuffer[Network.Member]
  def join(name:String) = {
    val m =new Network.Member(name)
    members += m
    m
  }
}
object Network {
  class Member(var  name:String) {
    val contacts = new ArrayBuffer[Network.Member]
  }
  def main(args: Array[String]): Unit = {
    val chatter = new Network
    val myFace = new Network
    val fred: Network.Member = chatter.join("fred")
    val wilma: Network.Member = chatter.join("wilma")
    fred.contacts += wilma
    val barney = myFace.join("Barney")
    fred.contacts += barney
    println(fred.contacts)
    fred.contacts.foreach(m =>  println(m.name))
  }

}
```

>   解决方案二：使用投影类型 Network#Member，表示 ==“任何 Network 的 Member”==

```scala
class Network {
  class Member(var  name:String) {
    val contacts = new ArrayBuffer[Network#Member]
  }
  private val members = new ArrayBuffer[Member]
  def join(name:String) = {
    val m =new Member(name)
    members += m
    m
  }
}
```

##   引用外部类

在嵌套类中，可以用==外部类.this==的方式类访问外部类的 this 引用，这点和java一样

也可以用如下方法建立一个指向该引用的别名

```scala
package kasa.learn.chapter05
import scala.collection.mutable.ArrayBuffer
class Network(val name:String) {
  outer => class Member(var  name:String) {
    val contacts = new ArrayBuffer[Network#Member]

    def description = name + " inside " + outer.name
  }
  private val members = new ArrayBuffer[Member]
  def join(name:String) = {
    val m =new Member(name)
    members += m
    m
  }
}
object Network {
  def main(args: Array[String]): Unit = {
    val chatter = new Network("chatter")
    val myFace = new Network("myFace")
    val fred: chatter.Member = chatter.join("fred")
    val wilma: chatter.Member = chatter.join("wilma")
    fred.contacts += wilma
    val barney = myFace.join("Barney")
    fred.contacts += barne
    println(fred.contacts)
    fred.contacts.foreach(m =>  println(m.description))
  }

}
```

#   2. 对象

- 用对象作为单例或存放工具方法
- 类可以有一个同名的伴生对象
- 对象可以扩展类或特质
- 对象的 apply 方法通常用来构造伴生类的新实例
- 不想显示定义 main 方法，可以扩展 APP 特质的对象
- 可以通过扩展 Enumeration 对象来实现枚举

##   2.1 单例对象

Scala 没有静态方法和静态字段，用 object 达到同样目的，定义了某个类的单个 实例，包含了所需特性 。

```scala
object Accounts {
    private var lastNumber = 0
    def newUniqueNumber() = {lastNumber +=1; lastNumber}
}
```

需要新账号ID时，调用 `Accounts.newUniqueNumber` 即可。

- 对象的构造器在第一次被使用时调用，如从未被使用则不会执行。

- 对象可以有类的所有特质(可以扩展其他类或特质)，但是：  ==不能提供 构造器参数 ==
- 用途
  - 存放工具函数或常量
  - 高效共享单个不可变实例
  - 需要单个实例来 协调某个服务(参考单例模式)

>  很多人低看单例 模式。scala 提供工具，做出好的还是 糟糕的设计需要个人判断。

##  2.2 伴生对象 

>   在  Java 或 C++ 中，常用到既有实例方法又有静态方法的类。在  Scala 中通过伴生对象来实现

==类和伴生对象可以互相访问私有属性，必须同名且在同一个源文件中==

>   在 REPL中 :paste 进入粘贴模式，Ctrl + D 退出粘贴模式

```scala
package kasa.learn.chapter05
object AppDemo extends App {
  if (args.length > 0)
    println("Hello, " + args(0))
  else
    print("Hello, World!")
  private val point = Point(20, 80)
  println(point)
}
class Point(x:Int,  y:Int) {
  override def toString: String = {
    "Point[x=" + this.x +
      ", y=" + this.y +
      "]"
  }
}
object Point{
  def apply(x: Int, y: Int): Point = new Point(x, y)
}
```

#  包和引入

- 包的嵌套声明
- 作用域规则，冲突解决办法
- 文件顶部标记法
- 包对象

##  包对象

包可以包含类、对象和特质，但不能包含函数和变量，这是虚拟机的局限。

把工具函数或常量添加到包而不是某个Utils对象是更加合理的做法。

包对象的出现就是为了解决这个问题。

每个包可以有一个包对象，在父包中定义，且与子包同名

```scala
package com.horstmann.impatient

package object people{
    val defaultName = "John Q. Public"
}

package people{
    class Person {
        var name = defaultName // 从包对象拿到的常量
    }
}
```

##  引入

引入可以用更短的名称而不是长名称，这是引入语句的唯一目的。

如果你不介意使用长名称，你完全不需要使用引入。

引入可以出现在任何地方，作用范围延伸到包含该语句的块末尾。

```scala
import java.awt.Color 		// 引入某个类
import java.awt._	  		// 引入某个包的全部成员
import java.awt.Color._ 	// 引入某个类或对象的全部成员

import java.awt.{Color,Font} // 选择几个成员的选取器
import java.util.{HashMap => JavaHashMap} // 重命名选到的成员
import java.util.{HashMap => _, _} // 隐藏选到的成员

// 一旦引入了某个包，可以使用短名称访问其子包
import java.awt._

def handle(evt:event.ActionEvent){// java.awt.event.ActionEvent
    ...
}
// event包是 java.awt包的成员，因此引入语句把它也带进了作用域。
```

隐式引入 aka 默认引入每个 scala 程序都隐式地以如下代码开始：

```scala
import java.lang._
import scala._
import Predef._
```

scala 包的引入允许覆盖之前的引入 scala.StringBuilder 会覆盖 java.lang.StringBuilder.

##  对象的相等性

AnyRef 的 eq 方法检查两个引用是否指向同一个对象。

AnyRef 的 equals 方法调用 eq，因此自己写的类应当重写 equals 方法。

```scala
final override def equals(other Any) = {
    val that = other.asInstanceOf[Item]
    if(null == that) false
    else description == that.description && price == that.price
}
```

>  请确保 equals 方法的参数类型为 Any 。下面的代码是==错误==的

```scala
final def equals(other:Item)= {......}
```

>   这是一个不相关的方法，并不会重写 AnyRef 的 equals 方法。

>   记得最好重写 hashCode 方法。

>   upply  update unapply unapplySeq  方法