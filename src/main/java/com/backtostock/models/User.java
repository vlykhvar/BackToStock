package com.backtostock.models;

import java.util.Objects;

public class User {
    private String name;
    private boolean premium;
    private int age;

    public User() {
    }

    public User(String name, boolean premium, int age) {
        this.name = name;
        this.premium = premium;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return premium == user.premium && age == user.age && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, premium, age);
    }

    @Override
    public String toString() {
        return "User{"
                + "name='" + name + '\''
                + ", premium=" + premium
                + ", age=" + age + '}';
    }
}
