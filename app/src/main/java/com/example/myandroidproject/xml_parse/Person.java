package com.example.myandroidproject.xml_parse;

public class Person {
    String name;
    String sex;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "[name =" + name + " age=" + age + " sex=" + sex + "]";
    }
}
