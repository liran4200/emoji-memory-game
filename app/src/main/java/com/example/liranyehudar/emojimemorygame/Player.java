package com.example.liranyehudar.emojimemorygame;
import java.io.Serializable;

public class Player implements Serializable {

    private int id;
    private String name;
    private int age;
    private int result;

    public Player(int id, String name, int age, int result) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.result = result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getResult() {
        return result;
    }

}
