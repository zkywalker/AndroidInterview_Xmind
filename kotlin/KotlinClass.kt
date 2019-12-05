package org.zky.demo


fun main(arg: String) {
    //第一个参数有默认值就可以省略，但是因为俩参数的类型一样所以需要指定是哪个参数
    KotlinClass(用来初始化的参数 = "kotlin").构造参数
    //不可以调用为声明的参数哦
//    KotlinClass("hello","kotlin").用来初始化的参数
    KotlinClass("dada", "sdad")

}


class KotlinClass constructor(val 构造参数: String = "可以给构造参数添加默认值", 用来初始化的参数: String) : Any() {
//主构造器没有注解或可见度修饰符可以省略constructor关键字

    //在 JVM 上，如果主构造函数的所有的参数都有默认值，编译器会生成 一个额外的无参构造函数，它将使用默认值。
    // 这使得 Kotlin 更易于使用像 Jackson 或者 JPA 这样的通过无参构造函数创建类的实例的库。


    var 用来初始化的参数: String = 用来初始化的参数 //这样就可以使用未在构造函数里声明的参数了（可以简化为第一个参数的形式直接声明）

    init {
        //主构造器不能有代码，初始化的代码需要在【初始化块】里。
        print("this is a property: $构造参数")
    }

    val 第二个参数 = "给属性赋值的操作叫做属性初始化器"

    init {
        //初始化代码块可以有多个，和属性初始化器会按顺序执行
        print("this is the second property: $第二个参数")
    }

    //含有主构造器的类的次级构造器必须直接或"间接通过一个其他次级构造器"代理主构造器
    constructor(构造参数2: Int) : this("主构造器的参数", "这个参数外部不可访问") {

    }

    private var field1: String = "可变属性"

    val field2: String = "不可变属性"

    var field3: String = "val不可以有set方法"
        get() = field
        /**
         * field关键字叫后端变量（backing field）
         * 在kotlin的getter和setter是不允许本身的局部变量的，
         * 因为属性的调用也是对get的调用，因此会产生递归，造成内存溢出。
         */
        set(value) {
            if (field.length > 5) {
                field.substring(0, 5)
            }
            field = value
        }

    val 创建的对象 = KotlinClass("我是创建的对象", "这个参数外部不可访问")

    val 使用属性 = print(创建的对象.field1)


    fun 成员函数() {
        print("hello")
    }

    //kotlin可以重写父类的open的函数
    //添加final子类就不能再重写了
    final override fun hashCode(): Int {
        return super.hashCode()
    }
    //你也可以用一个 var 属性覆盖一个 val 属性，但反之则不行。这是允许的，
    // 因为一个 val 属性本质上声明了一个 getter 方法，而将其覆盖为 var 只是在子类中额外声明一个 setter 方法。

    //----------------------------类---------------------------------------

    /**
     * 类修饰符
     * abstract    // 抽象类
     * final       // 类不可继承，默认属性
     * enum        // 枚举类
     * open        // 类可继承，类默认是final的
     * annotation  // 注解类
     *
     * 访问修饰符
     * private    // 仅在同一个文件中可见
     * protected  // 同一个文件中或子类可见
     * public     // 所有调用的地方都可见
     * internal   // 同一个模块中可见
     */

    open class 嵌套类 {
        fun 嵌套类方法() = "hello"

        fun 调用嵌套类的例子(): String {
            return KotlinClass.嵌套类().嵌套类方法()
        }

        open fun a() {
            print("function a")
        }
    }

    abstract class 抽象类 : 嵌套类() {
        //可以把open的父类成员重写成抽象的
        abstract override fun a()
    }

    open inner class 内部类 {
        //内部类持有外部类的引用，可以直接使用外部的属性
        fun 内部类方法(): String {
            return field1
        }

        fun 内部类访问外部类作用域() {
            print(this@KotlinClass::class.java)
        }

    }

    interface 接口和JAVA一样 {
        fun 接口方法()

        fun a() {
            print("interface a")
        }
    }

    //------------对象表达式--------------------

    val 创建匿名类 = object : 接口和JAVA一样 {
        override fun a() {
            print("a")
        }

        override fun 接口方法() {
            print("[对象表达式]来创建接口的实例对象（匿名内部类）")
        }
    }

    //匿名类不仅可以实现自接口，还可以是类的子类。这个是java匿名类实现不了的
    val 创建匿名类2: 嵌套类 = object : 嵌套类(), 接口和JAVA一样 {
        open override fun 接口方法() {
            print("hello")
        }

        //如果有重名的成员需要重写，使用尖括号确认使用睡得父类成员
        override fun a() {
            super<接口和JAVA一样>.a()
            super<嵌套类>.a()
        }

    }

    val 甚至可以凭空造匿名类 = object {

        val name = "object"

        fun method() {
            print("hello")
        }

    }

    private val 凭空造匿名类 = object {

        val name = "object"

        fun method() {
            print("可以访问匿名类的其他成员: $name")
        }

    }

    fun 测试匿名对象类型和访问权限() {
//        val a = 甚至可以凭空造匿名类.name
        //public 的匿名对象的类型是超类型或any，不能访问匿名对象的成员。
        //private 匿名对象可以用作只在本地和私有作用域中声明的类型

        val b = 凭空造匿名类.name
    }

    //------------对象声明--------------------

    //可以使用object来声明对象，来获取单例
    //todo (初始化过程是线程安全的 //编译的字节码是在静态代码块里创建的事例，能保证绝对的线程安全吗)
    object 单例 : 嵌套类() {//对象可以有超类型

        val data: List<String> = arrayListOf("1", "2")

    }

    //当对象声明在另一个类的内部时，这个对象并不能通过外部类的实例访问到该对象，
    // 而只能通过类名来访问，同样该对象也不能直接访问到外部类的方法和变量。

    //------------伴生对象实现类似java类的静态成员--------------------

    //JAVA static的缺点：静态的变量和方法属于一个类，而普通的变量和方法属于具体的对象。实际上他们在代码结构里混在一起
    //会使他们的职能区分变得模糊。所以kotlin使用了半生对象来实现static。

    //一个类只能有一个伴生对象，规范是伴生对象在类的最后声明
    companion object 伴生对象的类名可以省略 {
        //@JvmField
        var 属性 = false

        const val 常量这样声明 = true

        //@JvmStatic
        fun 函数(num1: Int, num2: Int): Int {
            return num1 + num2
        }

        //使用JvmField、JvmStatic注解，在 Java 代码中调用的时候就和 Java 类调用静态成员的形式一致了
    }

    //外部中， KotlinClass.Companion访问伴生对象，
    // 调用伴生对象：
//    println(KotlinClass.函数(1, 2))  // 3
//    println(KotlinClass.属性)  // false

    //扩展伴生对象方法: 类名.伴生对象名.方法名() or 类名.Companion.方法名()

    //半生对象可以用来实现工厂方法模式

    //todo 伴生对象扩展属性

    /**
     *
     *  对象表达式是在使用他们的地方立即执行的
     *
     *  对象声明是在第一次被访问到时延迟初始化的
     *
     *  伴生对象的初始化是在相应的类被加载（解析）时，与 Java 静态初始化器的语义相匹配
     */

    //-------------------------------------------------------------------


}

//数据类
//数据类只用来保存数据，编译器自动根据【主构造器声明的所有属性】导出以下函数：equals/hashCode/toString/解构函数componentN/copy
//不可以使用abstract open sealed inner修饰
data class Person(var name: String, var age: Int = 0, var city: String = "") {
    //自己实现属性的解构函数
    private val sex = 1
    operator fun component4():Int{
        return this.sex
    }
}

//copy函数可以根据原对象创建一个对象，需要注意的是因为hashcode重写了，你比较的话两者如果属性一样就会相等，但是是两个对象。

fun 解构声明() {
    val person = Person("kyle", 1, "beijing")
    val (name, age, city) = person
    print("name = $name, age = $age")
    //更多的时候是在诸如map这种容器代替Entry实现多变量返回

    //对数组进行解构，最多赋值5个变量
    val str = "kyle 18 18"
    val (name1, age1, length1) = str.split(" ")

    //除了上述两种kotlin还支持了Pair和Triple 直接使用解构声明。
    //Pair和Triple分别是两个和三个属性的数据类，实现了Serializable接口，重写了toString
    val triple = Triple("kyle", 18, 18)
    val (name2, age2, length2) = triple

    //todo lambda中的解构
}

data class 数据类可以继承其他类(val 主构造器参数: String) : PrivateClass() {
    //类体参数不会被编译到上述自动生成的函数里
    var value: String = ""
    //这会导致 诸如下面的情况
    // val a = 数据类可以继承其他类("hello")
    //val b = 数据类可以继承其他类("hello").apply{ value = "kotlin" }
    // a.equals(b)  true
}

//密封类
sealed class PrivateClass {
    //密封类是抽象的，可以有抽象成员，不可以直接实例化
}

open class 密闭类的子类只能在该类的同一个文件 : PrivateClass() {

}
//密封类子类的子类可以在其他文件
//官方使用密闭类的例子是使用when语句

//嵌套类和内部类
class Outer {
    private val value: Int = 1

    //嵌套类
    class Nested {
        fun foo() = 2
    }

    //内部类是一种特殊的嵌套类，持有外部类的引用，可以访问外部类的成员
    inner class Inner {
        fun foo() = value
    }
}

//类型别名
typealias simpleName = 密闭类的子类只能在该类的同一个文件

typealias inner = Outer.Inner

typealias lamb = (String, Int) -> Unit

typealias gen<T> = (T) -> Boolean

//集合类泛型很长的时候是个很好的使用时机

//类委托
interface Printable {
    val text:String
    fun print()
    fun print2()
}

class PrintImp : Printable {
    override val text = "hello kotlin"

    override fun print2() {
        println("print2 - $text")
    }

    override fun print() {
        println("print1 - $text")
    }
}

class PrintDerived(p:PrintImp):Printable by p{
    override val text = "---"

    override fun print2() {
        println("derived - $text")
    }
}

/**
 * val pd = PrintDerived(PrintImp())
 * pd.print()
 * pd.print2()
 * println("pd.text = ${pd.text}")
 *
 * 输出为
 * print1 - hello kotlin
 * derived - ---
 * pd.text = ---
 */

//除了接口继承和组合模式，kotlin可以使用委托来实现多继承

//todo 委托属性
//todo 枚举类
//todo kotlin 泛型

//todo 内联类，1.3
