package com.thebyteguru.graphics;

import com.thebyteguru.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Chessmaster on 22.05.16.
 */
//хранит информацию о большом изображении, с помощью которой будем вытаскивать куски изображения
public class Sprite {

    private SpriteSheet   sheet;
    //на сколько большим мы хотим рисовать спрайт на экране
    private float         scale;
    private BufferedImage image;

    //должен принять в себя спрайт
    public Sprite(SpriteSheet sheet,float scale){
        this.sheet = sheet;
        this.scale = scale;
        image = sheet.getSprite(0);
        //изменяем размер картинки на тот, который нас интересует
        image = Utils.resize(image,(int)(image.getWidth() * scale),(int)(image.getHeight() * scale));
    }
    //
    public void render(Graphics2D g, float x, float y){
        //BufferedImage image = sheet.getSprite(0);
        g.drawImage(image,(int)(x),(int)(y),null);

    }

}


