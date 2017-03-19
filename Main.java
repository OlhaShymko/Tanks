package com.thebyteguru.main;

import com.thebyteguru.game.Game;

import java.io.FileNotFoundException;

/**
 * Created by Chessmaster on 19.05.16.
 */
public class Main {
    public static void main (String[] args) throws FileNotFoundException {
        Game tanks = new Game();
        tanks.start();

    }
}

//0123456789abcdef - гексодецемальная система, 16-тиричная. удобна для компьютеров
