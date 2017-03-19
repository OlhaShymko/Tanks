package com.thebyteguru.graphics;

import com.thebyteguru.utils.ResourceLoader;

import java.awt.image.BufferedImage;

/**
 * Created by Chessmaster on 22.05.16.
 */
//отвечает за большое изображение
public class TextureAtlas extends ResourceLoader{

    //изображение
    BufferedImage image;
//конструктор
    public TextureAtlas(String imageName){
        image = ResourceLoader.LoadImage(imageName);
    }
    //вырезать куски из картинки
    public BufferedImage cut(int x, int y, int w, int h){
        //вырежет размер картинки по нашим координатам
        return image.getSubimage(x,y,w,h);
    }

}


