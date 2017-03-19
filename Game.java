package com.thebyteguru.game;

import com.thebyteguru.IO.Input;
import com.thebyteguru.display.Display;
import com.thebyteguru.game.level.Level;
import com.thebyteguru.graphics.TextureAtlas;
import com.thebyteguru.utils.Time;

import java.awt.*;

/**
 * Created by ������������� on 20.05.2016.
 */
public class Game implements Runnable{

    //������� �� ��������� � �����


    // ���������, � ������� ������� �� ������� ���� ���� (����������� ����)
    public static final int    WIDTH           = 800;
    public static final int    HEIGHT          = 600;
    public static final String TITLE           = "Tanks";
    public static final int    CLEAR_COLOR     = 0xff000000;
    public static final int    NUM_BUFFERS     = 3;

    //��������, �������� ��� ��������(������� ��� � ������� �� ����� ������� ���� ������)
    public static final float  UPDATE_RATE     = 60.0f;

    //������� ������� ������ ��������� ����� ������ ��������
    public static final float  UPDATE_INTERVAL = Time.SECOND/UPDATE_RATE;

    //�����, � ������� �� ������ �� ������, - ���� ��������� "��������", � ������������
    public static final long   IDLE_TIME       = 1;

    //������� ����������� ����, ������� ������ ��� ����� ��������
    public static final String ATLAS_FILE_NAME = "texture_atlas.png";


    //������� ���� ����� ����
    //�������� ����� ��� �� ����� ����
    private boolean            running;

    //������, ������� ������������� �� ����� ���������
    private Thread             gameThread;

    //������ �������, ������� �� ����� ������������, ���� �������� ��������� � ����� ����
    private Graphics2D         graphics;

    //������� ������ �� ������ ������ Input
    private Input              input;

    private TextureAtlas       atlas;
 //   private SpriteSheet sheet;
 //   private Sprite sprite;
    private Player             player;
    private Level              lvl;

    //temp , ��������� ����
  //  float                      x              = 350;
   // float                      y              = 250;
  //  float                      delta          = 0;
  //  float                      radius         = 50;
  //  float                      speed          = 4;
    //temp end

    //������� �����������
    public Game()  {

        //���� ���� ��� �� �����
        running = false;

        //������� ���� ��� ����� ����
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);

        //�������, ���� �������� ���� �������, � � ������� ��� �������� ��������� � ����� ����
        graphics = Display.getGraphics();
        //������������ ��� ����� Input, ��� ��������� ������� ��������� ������ ������ �������
        input = new Input();
        Display.addInputListener(input);
        atlas = new TextureAtlas(ATLAS_FILE_NAME);
        //8(�������)-��������� �� �, 5(�������)-��������� �, w-2-��� ��� �����, h-16-���� ����
        //��������� spriteCount-2 �����, scale-16-������ ������ �����
        //���� �����������-16 �������� �� ������ �����������
        // sheet = new SpriteSheet(atlas.cut(8*m(16),5*m(16),m(16)*2,m(16)),2,m(16)-2);

      //  sheet = new SpriteSheet(atlas.cut(1*m(16),9*m(16),m(16)*2,m(16)),2,m(16)-2);
        //�������������� ������
      //  sprite = new Sprite(sheet,1);
        player = new Player(300,300,2,3,atlas);
        lvl = new Level(atlas);
    }

    //������ ������� ����
    public synchronized void start(){
        //���� ���� ��� ���� ��������
        if (running)
            return;
        //���� ���� �� ��������(�����)
        running=true;
        gameThread=new Thread(this);
        gameThread.start();
    }

    //��������� ����
    public synchronized void stop(){
        if (!running)
            return;
        //���� ��� �� �����
        running=false;

        //���������� ���, � ����� ���� ��� ������� �������� ���� ������
        try {
            gameThread.join();
        }catch (InterruptedException e){

            //��� ��������� ����������
            e.printStackTrace();
        }
        //�������� ������� �������� ����� ��������� ����
        cleanUp();
    }

    //�����, ������� ����� ������� ��� ������, ��� �����.�������, ��������...
    private void update(){
        player.update(input);
        lvl.update();
     //   delta+=0.02f;
        //������������ �������� ������ �����
        //if(input.getKey(KeyEvent.VK_UP))
          //  if(input.getKey(KeyEvent.VK_RIGHT))
       //     x+=speed;

    }

    //������ ���� ��������� �����, ������� ����� ���������� ���������
    private void render(){
        Display.clear();
       // graphics.setColor(Color.white);
        lvl.render(graphics);
        player.render(graphics);
        lvl.renderGrass(graphics);
      //  sprite.render(graphics,x,y);
        //�������� �������� �� ����� �����������
      //  graphics.drawImage(atlas.cut(1,1,56,56),300,300,null);
        //������ ��� ����
       // graphics.fillOval((int)(x + (Math.sin(delta)*200)),(int)(y),(int)(radius*2),(int)(radius*2));
        Display.swapBuffers();
    }

    //���� ��� ����� ����, ������� ����� ������ � ������� "������������" �����
    //� �������� ������� update() � render() � ������ �����
    public void run(){

        int fps = 0; // ���-�� �����������, ������� ����
        int upd = 0; // ���-�� update(����������)
        int updl = 0;// ���-�� ���, ������� �� �������� ��� update

        long count = 0;

        float delta = 0;
        //������ � ���� ����� ������� ��������� �����
        long lastTime = Time.get();
        //��� loop(����)
        while (running){
            long now = Time.get(); //������ (�������) �����

            //���-�� �������, ������� ������ � ������� ��������� ������ ����
            long elapsedTime = now - lastTime;

            //������� ����� ������������ � �������� �������, ���� ����� � ����.���
            //������ � ��� loop � ��� now ���������� �� ������� �����, �
            //lastTime ����� ������� � ���� ����� �������� ����� while()
            lastTime = now;

            //��������� �����, ������� �������� �� ���� ����� ����
            count+=elapsedTime;

            //���� �� ������ �� ��������� ��������� - ��� ������ ��� ��������������
            boolean render = false;

            //� ��� ����� ������� ���-�� update-��������-����������(���, ������� ������� ������ ������),
            // ������� ����� �������// elapsedTime - ���-�� �������,������� ������ � ������� ��������� ��� ����
            //UPDATE_INTERVAL - ���-�� �������, ������� ������ ��������� ����� ������������
            delta += (elapsedTime/UPDATE_INTERVAL);

            //������,���� ���-�� ����� �������� ������ ���� UPDATE_RATE = 60.0f;
            while (delta>1) {
                // ���� ������ ����� ������� update-������ ���
                // ���� ��� ����� ����� ������ , ������ delta ������ 1
                update();
                upd++;
                delta--;
                //���� ����������, - ����� ������������ �����

                //������ ��������
                if (render) {
                    updl++;
                } else {
                    render = true;
                }
            }
            //��������� ���������, ���� ����� ��������
            if (render){
                render();
                fps++;
            }else {
                //���� ������� ���������
                try {
                    //���� �������� ���� �����������, � �������� ���� ���-�� �������
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //��������, ��� ������ count=1 �������, - ������ ��� ������ ����
            //� �������� ���������. ����� ���� ���������� �������,� ������� ������
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
        //���������� ����, ����� ���� ��� ������� ���� ����
        Display.destroy();

    }

    //������ �������� �� ������� ����� ��������
    private int m(int i) {
        final double m=1.8125;
        i=Integer.parseInt(""+(Math.round(i*m)));
        return i;
    }

}
