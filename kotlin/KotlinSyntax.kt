package org.zky.demo

import java.io.File
import kotlin.math.max

//单行注释

/*
多行块注释
 */


/**
 * 方法注释
 */
fun main(args: Array<String>) {

    //不可变变量
    val age = 18
    //可变变量
    var name = "kyle"
    //String的扩展函数 format
//    println("name: %s,age: %d".format(name, age))
    //指定参数类型
    val intAge: Int = 19
    //编译器支持自动判断类型，不声明类型是可行的
    name = "hahaha"

    //字符串模板 $表示变量名或变量值
    val str = " age is $age"
    //${方法返回值}
    val retStr = "1 + 2 = ${sum(1, 2)} "

    //Kotlin的空安全设计对于声明可为空的参数，在使用时要进行空判断处理，有两种处理方式，字段后加!!像Java一样抛出空异常，
    // 另一种字段后加?可不做处理返回值为 null或配合?:做空判断处理
    val nullableAge: Int? = 123

    nullableAge!!.compareTo(12)//如果空了就抛出异常

    nullableAge?.compareTo(12)//如果空了就不管啦

    val res = nullableAge ?: -1//如果空就返回一个值

    //类型判断
    var 类型判断: (Any) -> String = { obj -> if (obj is String) "是字符串哎" else "不是字符串哎" }

    var 类型判断完直接用可以: (Any) -> Unit = { obj ->
        if (obj is String && obj.length > 0) {
            print("这是一个字符串并且长度大于1哎")
        }
    }

    var 区间的使用 = { for (i in 1..4) print(i) }
    //这里插播一条命名规则：``可以使不合法的字符当做变量名
    var `区间加步长(其实就是中缀表达式)` = { for (i in 1..10 step 2) print(i) }
    var 反向的 = { for (i in 10 downTo 1) print(i) }
    var 排除最后一个元素 = { for (i in 1 until 10) print(i) }//右开区间

    var when语句: (Any) -> Unit = { x ->
        when (x) {
            1 -> print("1")
            2, 3 -> print("23")
            in 4..10 -> print("4-10")
            !in arrayOf(11, 12) -> print("! 11 ,12")
            else -> print("else")
        }
    }

    var intName = name.toInt()
    val file = File("KotlinDemo.iml")
    //File的扩展函数，kt自带的，扩展对象的调用函数。
    // 我的理解就是对类的对象动态的添加了一个方法，有点像 静态工具方法的对对象的操作
//    file.readText()

    vars(1, 2, 3, 4)

    val lambdaSum: (Int, Int) -> Int = { x, y -> x + y }
    //拉姆达表达式加if语句直接作为表达式输出结果
    val max: (Int, Int) -> Int = { a, b -> if (a > b) a else b }
    var 检测数值在某个区间: (Any) -> Unit = { x -> if (x in 1..10) print("在这个区间") }

    println(lambdaSum(1, 2))

}


fun sum(a: Int, b: Int): Int {
    return a + b;
}

//只有一行表达式的时候，可以直接把表达式赋值给函数
fun sum2(a: Int, b: Int): Int = a + b

//表达式的时候返回类型自动判断
fun sum3(a: Int, b: Int) = a + b

//public 方法则必须明确写出返回类型 todo 这条不对吧，不写返回值也能过编译啊
//应该是只写一个表达式的时候编译的时候自动给添加上了类型
public fun sum4(a: Int, b: Int) = a + b

//无返回值
fun printSum(a: Int, b: Int): Unit {
    print(a + b)
}

//无返回值可以省略unit
fun printSum2(a: Int, b: Int) {
    print(a + b)
}

//可变参数
fun vars(vararg vars: Int) {
    for (v in vars) {
        println(v)
    }
}

typealias 给函数起别名 = (Int, Int) -> Unit

fun 高阶函数和lambda表达式() {
    //高阶函数（higher order function、算子、泛函数）
    //从名字上我们可以看到，高阶函数是一个很数学化的概念，很多数学概念都是高阶函数
    //简单的来说高阶函数就是输入值或输出值为函数的一种函数
    //函数式编程基于高阶函数（lambda表达式），而他们的理论基础是lambda演算
    //简单的来说lambda演算可以推到表示所有可计算的（函数），是图灵等价的
    //详细可参考计算机魔法师SICP(《计算机程序的构造和解释》)

    //kotlin 使用【 (输入参数类型) -> 输出参数的类型 】这样的结构表示函数类型(如何实例化后边再说):
    val onClick: (Int) -> Unit = { x -> print(x) }

    val 函数类型可空使用圆括号问号: ((Int) -> Unit)?

    val 函数类型是右结合的: (Int) -> (String) -> Int  //输入值为int，返回了一个 输入为string、返回值为int的函数

    val 函数类型可以使用括号结合函数类型: (Int) -> ((String) -> Int)   //和上面等价


    //函数类型可以有一个额外的接受者类型

    //挂起函数


    //---------------------实例化-----------------------
    val 实例化函数: (Int, Int) -> Int = { a, b -> a + b }

    //可以推断类型的话就不用写
    val 实例化匿名函数 = fun(s: String): String { return s + s }

    var 带接收者类型的函数: String.(Int) -> String = { a -> "$this, $a" }
//  使用的方法：   val res = "hello".带接收者类型的函数(1)

    val 等价于第一个参数是接受者: (String, Int) -> String = 带接收者类型的函数
    //反过来也可以（第一个参数被当做接收者）
    带接收者类型的函数 = 等价于第一个参数是接受者

    //---------------------lambda表达式-----------------------
    //lambda表达式在花括号内，参数声明在前（可选添类型标注），函数体在->后，返回值取函数体的最后一个表达式（如果返回类型是Unit就不取了）
    val 完整语法 = { x: Int, y: Int -> x + y }

    val 提前声明类型: (Int, Int) -> Unit = { a, b -> a + b }

    val 函数带lambda参数: (Int, (Int, Int) -> String) -> String = { a, lambda -> "$a + ${lambda(a, a)}" }
    //lambda为最后参数的时候可以写在外面
    val res = 函数带lambda参数(1) { a, b -> "lambda $a + $b" }
    //参数只有lambda的时候括号都可以不写
//    val res = 函数带lambda参数 { a, b -> "lambda $a + $b" }

    "只有一个参数的时候如果编译器可以识别签名，参数和->可以省略,隐士的声明为it".filter {
        val 结果也可以放最后一个位置隐式返回 = it == 'A'
        结果也可以放最后一个位置隐式返回
        //和下面等价
//        return@filter 结果也可以放最后一个位置隐式返回
    }

    arrayOf("这一约定连同", "在圆括号外传递 lambda 表达式", "一起支持 LINQ-风格 的代码")
            .filter { it.length == 5 }.sortedBy { it }.map { it.toUpperCase() }

    val `如果 lambda 表达式的参数未使用，那么可以用下划线取代其名称：` = 函数带lambda参数(1) { _, _ -> "这个lambda什么参数也没用" }

    //---------------------匿名函数-----------------------

    //匿名函数能指定返回值，在无法自动推断返回值得时候lambda表达式可能无法使用
    val 匿名函数 = fun(x:Int):String = "反正值:$x"

    //匿名函数和函数的区别（匿名函数没名不算区别的话）是匿名函数有时候有上下文，可以推断参数类型时不用声明类型
    //请注意，匿名函数参数总是在括号内传递
    val 自动推断匿名函数参数类型 = "hello".filter(fun(item) = item == 'A')

    //todo Lambda 表达式或者匿名函数（以及局部函数和对象表达式） 可以访问其 闭包 ，即在外部作用域中声明的变量。
    //todo 与 Java 不同的是可以修改闭包中捕获的变量

    //todo 带有接收者的函数字面值

    //todo 闭包
}
