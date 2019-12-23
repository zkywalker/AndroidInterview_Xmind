package org.zky.demo

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties

//kotlin集合框架、反射、泛型、注解

/**
 *  KAnnotatedElement(等于java的AnnotatedElement)
 *  KClass(= Class) KCallable(= AccessibleObject) KParameter(= Parameter 参数)
 *  KCallable子类KFunction(= Excutable)、KProperty(= Field)
 *  KFunction包括了java的Excutable的两个子类内容Constructor、Method，kotlin的get/set函数也是KFunction
 *  KProperty的子类KMutableProperty表示可修改的属性
 *
 *  kotlin通过Matadata注解存储额外的信息
 *
 *  kClass有很多诸如是否为半生对象、密封类等的kotlin特有属性
 */

val kClass: (KClass<Any>) -> Unit = { k ->
    k.constructors//构造函数s
    k.isAbstract //一堆is属性
    k.memberFunctions//获取函数列表
    k.memberProperties//获取属性列表
}

val kProperty: (KProperty<Any>) -> Unit = { p ->
    p.getter //只能拿到getter，为什么呢
    p.annotations//拿注解
    p.isLateinit
}

val kMutableProperty: (KMutableProperty<Any>) -> Unit = { p ->
    p.setter //因为mutable才有setter
}

val kFunction: (KFunction<Any>) -> Unit = { f ->
    f.isInline//是否内联
    f.annotations//获取注解
    f.typeParameters//获取类型参数（泛型）
    f.returnType//返回值
    f.call(Any())//对一个对象执行函数
}

//kotlin反射的一个例子：对象转map：
fun <A : Any> toMap(a: A): Map<String, Any?> {
    return a::class.memberProperties.map {
        it.name to it.call(a)
    }.toMap()
}

/**
 * kotlin注解参数只支持常量的：java对应基本类型、字符串、Class对象（kotlin、java）、其他注解、以上的数组
 * 支持除inherited外的四种元注解
 */
@MustBeDocumented //文档
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)//元注解：注解可以作用于什么位置
@Repeatable //注解可以多次在一个地方出现
@Retention(AnnotationRetention.RUNTIME)//用途：只在源码中、二进制文件中（无法反射拿到）、运行时
annotation class 注解(val 参数:String)

@注解("hello")
data class 注解测试(@field:注解("注解到字段上") val name: String,@property:注解("注解到属性上") val age:Int)

//运行期的注解我们可以通过反射操作

//todo 注解处理器