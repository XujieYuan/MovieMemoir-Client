package com.m.moviememoir.Utils;

import com.m.moviememoir.Bean.Person;

public class Singleton {
    private static final Singleton single = new Singleton();
    private Person person;

    private Singleton() {
    }

    public static Singleton getInstance() {
        return single;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
