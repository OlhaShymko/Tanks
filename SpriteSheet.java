package com.thebyteguru.graphics;

import java.awt.image.BufferedImage;

/**
 * Created by Chessmaster on 22.05.16.
 */

//класс держит в себе более мелкие изображения,
// которые будут отвечать за одну(свою) анимацию
public class SpriteSheet {
    private BufferedImage sheet;
    //хранит в себе кол-во спрайтов(индивид.изображения)
    private int           spriteCount;
    //размер одного спрайта(танка) в пикселях
    private int           scale;
    //кол-во спрайтов, которые есть в ширину
    private int           spritesInWidth;

    //конструктор
    public SpriteSheet(BufferedImage sheet, int spriteCount, int scale){
        this.sheet = sheet;
        this.spriteCount = spriteCount;
        this.scale = scale;

        //обнуляет кол-во спрайтов (по циклу)
        //sheet.getWidth() - ширина картинки
        this.spritesInWidth = sheet.getWidth()/scale;
    }

    //пронумеровать все танки, чтоб вырезать нужный
    public BufferedImage getSprite(int index){
        index = index % spriteCount;
        //вырезаем картинку
        int x = index % spritesInWidth * scale;
        int y = index / spritesInWidth * scale;

        return sheet.getSubimage(x,y,scale,scale);

    }

}


