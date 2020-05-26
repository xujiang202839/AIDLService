package com.hans.constraint.bean.father;

/**
 * @创建者 xu
 * @创建时间 2020/5/25
 * @描述
 */
public class SubClass2 extends SuperClass{
   public SubClass2(){
        super(300);  // 调用父类中带有参数的构造器
        System.out.println("SubClass2");
    }

    public SubClass2(int n){ // 自动调用父类的无参数构造器
        System.out.println("SubClass2(int n):"+n);
    }
}
