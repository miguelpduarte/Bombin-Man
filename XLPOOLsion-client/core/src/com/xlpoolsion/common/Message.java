package com.xlpoolsion.common;

import java.io.Serializable;

public class Message implements Serializable {
    private int anInt;
    private float aFloat;
    private String string;

    public Message(int anInt, float aFloat, String string) {
        this.anInt = anInt;
        this.aFloat = aFloat;
        this.string = string;
    }

    public int getAnInt() {
        return anInt;
    }

    public float getaFloat() {
        return aFloat;
    }

    public String getString() {
        return string;
    }
}
