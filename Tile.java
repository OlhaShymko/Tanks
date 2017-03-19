package com.thebyteguru.game.level;

import com.thebyteguru.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Chessmaster on 30.05.16.
 */
public class Tile {

    //статические объекты
    //изображение для тайла, которое будем рисовать
    private BufferedImage image;
    private TileType      type;

    protected Tile (BufferedImage image, int scale, TileType type){
        this.type = type;
        this.image = Utils.resize(image, image.getWidth() * scale, image.getHeight() * scale);
    }

    protected void render(Graphics2D g, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    protected TileType type(){
        return type;
    }


}
