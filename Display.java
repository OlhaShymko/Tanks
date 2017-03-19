package com.thebyteguru.display;

import com.thebyteguru.IO.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;


/**
 * Created by Chessmaster on 19.05.16.
 */
public abstract class Display {

    //нужен способ чтоб следить создалось окно или нет
    private static boolean created = false;
    private static JFrame window;
    private static Canvas content;

    //рисуем пустое изображение(как мы рисовали лист), которое будем использовать как бафер
    private static BufferedImage buffer;

    //тут запишем всю информацию имедж, которую создали
    private static int[] bufferData;

    private static Graphics bufferGraphics;

    //тут будет цвет, которым будем очищать наш имедж, в котором будем создавать
    private static int clearColor;

    //с его помощью имплементируем наши баферы
    private static BufferStrategy bufferStrategy;

    //создаем рамку
    public static void create(int width, int height, String title, int _clearColor, int numBuffers) {
        if (created)
            return;

        window = new JFrame(title);

        //добавляем на окно функциональность
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //создаем лист с помощью функции типа Canvas()
        content = new Canvas();

        //задаем размер листу. для этого создаем объект класса Dimension
        Dimension size = new Dimension(width, height);
        content.setPreferredSize(size);

        //пользователь не сможет менять размер окна
        window.setResizable(false);

        //добавляем лист. getContentPane() возвращает нам только внутреннюю часть окна
        window.getContentPane().add(content);

        //изменит размер окна так, чтоб он подходил точно под размер нашего окна
        window.pack();

        //окно появится в центре экрана
        window.setLocationRelativeTo(null);

        //хотим видеть окно, которое мы создали
        window.setVisible(true);

        //ширина, высота изображения и тип (как мы хотим сохранять информацию - у нас один массив красный-зеленый-синий)
        //остается один неиспользованный байт - будем туда записывать на сколько значение(этот пиксель) прозрачный,
        //т.е. альфа. скалироваться будет от 0 до 255
        buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);

        //длинный массив интеджеров - служит информацией для нашего изображения
        //buffer - наш имедж. getRaster() - оболочка для информации, которая находится в имидже
        //getDataBuffer() - еще одна оболочка, вернет нам переменную из класса
        //getData() - возвращает нам самый длинный массив интеджер, который сидит внутри нашего имеджа, который исп-ся
        //для того, чтоб делать манипуляции с нашим изображением-мы исп-м только чтоб стереть изображение
        //с его помощью можно вытащить объект графики с нашего изображения
        bufferData = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();

        //получаем объект, который умеет рисовать разные фигуры, и делать другие операции с графикой
        bufferGraphics = buffer.getGraphics();

        //добавляем (включаем) функцию сглаживания
        ((Graphics2D)bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        //сохранили цвет
        clearColor = _clearColor;

        //создали стратегию буфферизации
        content.createBufferStrategy(numBuffers);

        //вытащили эту стратегию чтоб к ней был доступ, после того, как создали его
        bufferStrategy = content.getBufferStrategy();

        //чтоб изменить его после того, как создали наше окно
        created = true;
    }
    //метод для очистки изображения на тот цвет, который передан в наш конструктор
    public static void clear(){

        //принимает любой массив и заполняет его значениями(одинаковыми или разными)
        Arrays.fill(bufferData,clearColor);
    }

    //функция будет менять то, что мы видим внутри нашего конваса на то, что мы создали
    public static void swapBuffers(){

        //эта функция вернет нам графический объект того бафера на котором мы должны рисовать
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer,0,0,null);
        //показываем следующий по очереди бафер
        bufferStrategy.show();
    }

    //метод возвращает графический объект от нашей картинки, на которой мы все рисуем, чтоб использовать его
    //и снаружи можно было изменять нашу графику
    public static Graphics2D getGraphics(){
        return (Graphics2D) bufferGraphics;
    }

    //уничтожить наше окно
    public static void destroy(){
        //проверка
        if (!created)
            return; //если окно не создано

        //если окно создано
        window.dispose();
    }

    //функция, которая будет менять имя нашего окна
    public static void setTitle(String title){
        window.setTitle(title);
    }
    //функция - слушатель нажатия
    public static void addInputListener(Input inputListener){
        window.add(inputListener);
    }

}
