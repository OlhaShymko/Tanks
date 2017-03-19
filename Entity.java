package com.thebyteguru.game;

import com.thebyteguru.IO.Input;

import java.awt.*;

/**
 * Created by Chessmaster on 22.05.16.
 */
public abstract class Entity {
    public final EntityType type;
    //местонахождение наших вещей в игре
    protected float x;
    protected float y;

    //конструктор
    protected Entity(EntityType type, float x, float y){
        this.type = type;
        this.x = x;
        this.y = y;
    }
    //методы
    public abstract void update(Input input);

    public abstract void render(Graphics2D g);

}
