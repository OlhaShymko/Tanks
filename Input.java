package com.thebyteguru.IO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * Created by Chessmaster on 21.05.16.
 */

//1.InputMap-карта, которая держит в себе значения и ключи.
// Будет связывать кнопку с действием
//2.ActionMap-название нашего экшена и код для него(функцию)

public class Input extends JComponent {
    //сохраняем название кнопок в одном массиве. Это будет массив с ASCII значениями
    private boolean[] map;
    //конструктор
    public Input()  {
        //создаем массив
        map = new boolean[256];
        //добавляем значение на каждый индекс в нашем массиве
        //пробигаем по всем значениям
        for(int i=0; i<map.length;i++){
            final int KEY_CODE = i;
            //возвращает карту, в которую мы можем добавлять значения
            //JComponent.WHEN_IN_FOCUSED_WINDOW - нажатие кнопок нужно ловить только когда окно нашей игры в фокусе.
            //главное,чтоб внутри были выбраны компоненты,а как это будет работать-не важно
            //put-добавляем новое значение - 1.какую кнопку хотим мониторить. 2.какой-то объект, который мы будем потом
            //использовать чтоб написать функцию, когда эта кнопка будет нажата
            //KeyStroke.getKeyStroke(i,0,false) - нажатие кнопки по ASCII коду, которое = 0
            //мы хотим на данный момент обработать кнопку KeyStroke.getKeyStroke(i,0,false) когда она была нажата
            //что будет происходить, когда мы эту кнопку нажимаем
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i,0,false), i*2);
            getActionMap().put(i*2, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent argo) {
                    map[KEY_CODE] = true;

                }
            });

            //что будет происходить, когда мы эту кнопку отпускаем
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i,0,true), i*2+1);
            getActionMap().put(i*2+1, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent argo) {
                    map[KEY_CODE] = false;

                }
            });

        }

    }
    //возвращаем boolean[]
    public boolean[] getMap(){
        //делаем копию нашей карты, чтоб нельзя было ее изменить
        return Arrays.copyOf(map,map.length);
    }
    //нажата кнопка или нет
    public boolean getKey(int keyCode){
        return map[keyCode];
    }

}
