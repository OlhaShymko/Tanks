package com.thebyteguru.game;

import com.thebyteguru.IO.Input;
import com.thebyteguru.display.Display;
import com.thebyteguru.game.level.Level;
import com.thebyteguru.graphics.TextureAtlas;
import com.thebyteguru.utils.Time;

import java.awt.*;

/**
 * Created by Администратор on 20.05.2016.
 */
public class Game implements Runnable{

    //масштаб по отношению к видео


    // параметры, с помощью которых мы создаем наше окно (статические поля)
    public static final int    WIDTH           = 800;
    public static final int    HEIGHT          = 600;
    public static final String TITLE           = "Tanks";
    public static final int    CLEAR_COLOR     = 0xff000000;
    public static final int    NUM_BUFFERS     = 3;

    //значение, желаемое для абдейтов(сколько раз в секунду мы хотим считать нашу физику)
    public static final float  UPDATE_RATE     = 60.0f;

    //сколько времени должно проходить между каждым абдейтом
    public static final float  UPDATE_INTERVAL = Time.SECOND/UPDATE_RATE;

    //время, в которое мы ничего не делаем, - даем программе "подышать", в милисекундах
    public static final long   IDLE_TIME       = 1;

    //сделали статическое поле, которое держит имя нашей картинку
    public static final String ATLAS_FILE_NAME = "texture_atlas.png";


    //создаем поля самой игры
    //проверка бежит или не бежит игра
    private boolean            running;

    //процес, который дополнительно мы будем запускать
    private Thread             gameThread;

    //объект графики, который мы будем использовать, чтоб рисовать изменения в нашем окне
    private Graphics2D         graphics;

    //создаем объект из нашего класса Input
    private Input              input;

    private TextureAtlas       atlas;
 //   private SpriteSheet sheet;
 //   private Sprite sprite;
    private Player             player;
    private Level              lvl;

    //temp , добавляем круг
  //  float                      x              = 350;
   // float                      y              = 250;
  //  float                      delta          = 0;
  //  float                      radius         = 50;
  //  float                      speed          = 4;
    //temp end

    //создаем конструктор
    public Game()  {

        //наша игра еще не бежит
        running = false;

        //создаем окно для нашей игры
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);

        //функция, чтоб вытащить нашу графику, и с помощью нее рисовать изменения в нашем окне
        graphics = Display.getGraphics();
        //регистрируем наш класс Input, как компонент который находится внутри нашего дисплея
        input = new Input();
        Display.addInputListener(input);
        atlas = new TextureAtlas(ATLAS_FILE_NAME);
        //8(пиксели)-координат по х, 5(пиксели)-координат у, w-2-это два танка, h-16-один танк
        //параметры spriteCount-2 танка, scale-16-размер нашего танка
        //наше изображение-16 пикселей на каждое изображение
        // sheet = new SpriteSheet(atlas.cut(8*m(16),5*m(16),m(16)*2,m(16)),2,m(16)-2);

      //  sheet = new SpriteSheet(atlas.cut(1*m(16),9*m(16),m(16)*2,m(16)),2,m(16)-2);
        //индивидуальный спрайт
      //  sprite = new Sprite(sheet,1);
        player = new Player(300,300,2,3,atlas);
        lvl = new Level(atlas);
    }

    //способ запуска игры
    public synchronized void start(){
        //если игра уже была запущена
        if (running)
            return;
        //если игра не запущена(бежит)
        running=true;
        gameThread=new Thread(this);
        gameThread.start();
    }

    //остановка игры
    public synchronized void stop(){
        if (!running)
            return;
        //игра уже не бежит
        running=false;

        //прекратить все, и ждать пока наш процесс закончит свою работу
        try {
            gameThread.join();
        }catch (InterruptedException e){

            //где произошло исключение
            e.printStackTrace();
        }
        //вызываем функцию очищения после остановки игры
        cleanUp();
    }

    //метод, который будет считать всю физику, все матем.расчеты, движения...
    private void update(){
        player.update(input);
        lvl.update();
     //   delta+=0.02f;
        //обрабатываем движение нашего круга
        //if(input.getKey(KeyEvent.VK_UP))
          //  if(input.getKey(KeyEvent.VK_RIGHT))
       //     x+=speed;

    }

    //рисуем нашу следующую сцену, которую хотим показывать следующей
    private void render(){
        Display.clear();
       // graphics.setColor(Color.white);
        lvl.render(graphics);
        player.render(graphics);
        lvl.renderGrass(graphics);
      //  sprite.render(graphics,x,y);
        //вырезаем картинку по нашим координатам
      //  graphics.drawImage(atlas.cut(1,1,56,56),300,300,null);
        //рисуем наш круг
       // graphics.fillOval((int)(x + (Math.sin(delta)*200)),(int)(y),(int)(radius*2),(int)(radius*2));
        Display.swapBuffers();
    }

    //ядро для нашей игры, которая будет бежать с помощью "бесконечного" цикла
    //и вызывать функции update() и render() в нужное время
    public void run(){

        int fps = 0; // кол-во перерисовок, которое было
        int upd = 0; // кол-во update(обновлений)
        int updl = 0;// кол-во раз, сколько мы доганяли наш update

        long count = 0;

        float delta = 0;
        //держит в себе время прошлой итеррации цикла
        long lastTime = Time.get();
        //наш loop(цикл)
        while (running){
            long now = Time.get(); //данное (текущее) время

            //кол-во времени, которое прошло с прошлой итеррации нашего лупа
            long elapsedTime = now - lastTime;

            //прошлое время приравниваем к текущему времени, чтоб когда в след.раз
            //зайдем в наш loop у нас now поменяется на текущее время, а
            //lastTime будет держать в себе время прошлого цикла while()
            lastTime = now;

            //считается время, которое проходит по ходу нашей игры
            count+=elapsedTime;

            //если на экране не произошло изменений - нет смысла его перерисовывать
            boolean render = false;

            //в ней будем держать кол-во update-абдейтов-обновлений(раз, которые функция должна бежать),
            // которые хотим сделать// elapsedTime - кол-во времени,которое прошло с прошлой итеррации наш цикл
            //UPDATE_INTERVAL - кол-во времени, которое должно проходить между обновлениями
            delta += (elapsedTime/UPDATE_INTERVAL);

            //следит,чтоб кол-во наших абдейтов всегда было UPDATE_RATE = 60.0f;
            while (delta>1) {
                // Если пришло время сделать update-сделай его
                // если это время давно прошло , сделай delta меньше 1
                update();
                upd++;
                delta--;
                //было обновление, - нужно перересовать экран

                //делаем проверку
                if (render) {
                    updl++;
                } else {
                    render = true;
                }
            }
            //проверяем изменения, если нужно рисовать
            if (render){
                render();
                fps++;
            }else {
                //даем передых программе
                try {
                    //даем подышать одну милисекунду, и передать туда кол-во времени
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //проверка, как только count=1 секунда, - меняем имя нашего окна
            //и передаем параметры. после чего сбрасываем счетчик,и считаем заново
            if (count>=Time.SECOND){
                Display.setTitle(TITLE+" || Fps:"+ fps+"| Upd: " + upd + " | Updl "+ updl);
                upd=0;
                fps=0;
                updl=0;
                count=0;
            }

        }

    }
    private void cleanUp(){
        //уничтожаем окно, после того как закрыли нашу игру
        Display.destroy();

    }

    //делаем поправку на масштаб нашей картинки
    private int m(int i) {
        final double m=1.8125;
        i=Integer.parseInt(""+(Math.round(i*m)));
        return i;
    }

}
