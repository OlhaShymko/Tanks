package com.thebyteguru.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chessmaster on 30.05.16.
 */
public class Utils {
    //функция изменяет размеры изображения
    public static BufferedImage resize(BufferedImage image, int width, int height){
        BufferedImage newImage = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        //размер оригинальной картинки вообще не интересует.
        //взяли старую картинку, изменили размер и передали графический объект новой картинки
        newImage.getGraphics().drawImage(image,0,0,width,height,null);
        return newImage;
    }

    public static Integer[][] levelParser(String filePath){
        Integer[][] result = null;

        //с java8 есть try-блок с ресурсами. создаем ресурс, который автоматически закроется
        try(BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {

            String line = null;
            List<Integer[]> lvlLines = new ArrayList<Integer[]>();
            while ((line = reader.readLine())!=null){
                //дилиметр - вытаскивает индвидуальные строки
               // String[] tokens = line.split(" ");
                lvlLines.add(str2int_arrays(line.split(" ")));
            }
            //[кол-во строчек] [кол-во эл-тов в каждой строчке]
            result = new Integer[lvlLines.size()][lvlLines.get(0).length];
            for (int i=0;i<lvlLines.size();i++){
                result[i]=lvlLines.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static final Integer[] str2int_arrays(String[] sArr){
        Integer[] result = new Integer[sArr.length];
        for (int i=0;i<sArr.length; i++){
            //конвертируем стринг в интеджер
            result[i] = Integer.parseInt(sArr[i]);

        }
        return result;
    }
}
