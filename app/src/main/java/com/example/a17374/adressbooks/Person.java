package com.example.a17374.adressbooks;

public class Person {
    private  int  id;
    private  int number;
    private  char name;

    public Person(int id, int number, char name) {
        this.id = id;
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", number=" + number +
                ", name=" + name +
                '}';
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(char name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public char getName() {
        return name;
    }
}
