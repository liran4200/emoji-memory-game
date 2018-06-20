package com.example.liranyehudar.emojimemorygame;
import java.io.Serializable;

public class Player implements Serializable {

    private int id;
    private String name;
    private int age;
    private int result;
    private double latitude;
    private double longitude;

    public Player(int id, String name, int age, int result) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.result = result;
    }

    public Player(int id, String name, int age, int result, double latitude, double longitude) {
        this(id,name,age,result);
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
