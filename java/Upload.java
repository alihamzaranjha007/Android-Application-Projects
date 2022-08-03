package com.example.classwork;

public class Upload {

    private String name;
    private String age;
    private String phone;
    private String height;


    public Upload(){

    }
    public Upload(String m_name, String m_age, String m_phone, String m_height){
        name=m_name;
        age=m_age;
        phone=m_phone;
        height=m_height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
