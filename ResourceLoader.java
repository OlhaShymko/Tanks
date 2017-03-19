package com.thebyteguru.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Chessmaster on 22.05.16.
 */
//вспомогательный класс помогает загружать файлы

public class ResourceLoader {
    //добавляем статическое поле
    //PATH = содержит путь к нашей папке с графикой
    public static final String PATH = "res/";
    //функция, которая загружает изображения
    public static BufferedImage LoadImage(String fileName){
        BufferedImage image = null;
        //загружаем картинки с помощью класса IO
        try {
            image = ImageIO.read(new File(PATH+fileName));

        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
