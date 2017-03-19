package com.thebyteguru.utils;

/**
 * Created by Администратор on 20.05.2016.
 */
public class Time {
    //все манипуляции со временем будут расчитываться с помощью наносекунд
    //статическое поле, которое хранит кол-во наносекунд в секунде
    public static final long SECOND = 1000000000l;

    //возвращаем текущее время
    public static long get(){
        return System.nanoTime();
    }

    }
