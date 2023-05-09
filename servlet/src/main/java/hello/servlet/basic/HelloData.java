package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter// 롬복 이용하여 저 코드들 다 필요 없어짐 (예전에 공부했음)
public class HelloData {
    private String username;
    private int age;
/*
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }*/
}