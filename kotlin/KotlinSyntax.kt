package org.zky.demo

import java.io.File

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
    val nullableAge: Int ? = 123
    nullableAge!!.compareTo(12)//如果空了就抛出异常
    nullableAge?.compareTo(12)//如果空了就不管啦
    val res = nullableAge?:-1//如果空就返回一个值

    //类型判断
    var 类型判断:(Any) -> String = {obj -> if( obj is String) "是字符串哎" else "不是字符串哎"}
    var 类型判断完直接用可以:(Any) -> Unit = {obj -> if( obj is String && obj.length>0){ print("这是一个字符串并且长度大于1哎")}}

    var 区间的使用 = {for(i in 1..4) print(i)}
    //这里插播一条命名规则：``可以使不合法的字符当做变量名
    var `区间加步长(其实就是中缀表达式)` = {for(i in 1..10 step 2) print(i)}
    var 反向的  = {for(i in 10 downTo 1) print(i)}
    var 排除最后一个元素 = {for(i in 1 until 10) print(i)}//右开区间

    var when语句:(Any) -> Unit = {x -> when(x){
        1 -> print("1")
        2,3 -> print("23")
        in 4..10 -> print("4-10")
        !in arrayOf(11,12) -> print("! 11 ,12")
        else -> print("else")
    }}

    var intName = name.toInt()
    val file = File("KotlinDemo.iml")
    //File的扩展函数，kt自带的，扩展对象的调用函数。
    // 我的理解就是对类的对象动态的添加了一个方法，有点像 静态工具方法的对对象的操作
//    file.readText()

    vars(1, 2, 3, 4)

    val lambdaSum: (Int, Int) -> Int = { x, y -> x + y }
    //拉姆达表达式加if语句直接作为表达式输出结果
    val max: (Int, Int) -> Int = { a, b -> if (a > b) a else b }
    var 检测数值在某个区间 : (Any) -> Unit = {x -> if(x in 1..10) print("在这个区间")}

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
