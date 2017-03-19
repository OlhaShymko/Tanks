package com.thebyteguru.game;

import com.thebyteguru.IO.Input;
import com.thebyteguru.graphics.Sprite;
import com.thebyteguru.graphics.SpriteSheet;
import com.thebyteguru.graphics.TextureAtlas;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chessmaster on 22.05.16.
 */
public class Player extends Entity {


    public static final int SPRITE_SCALE = 16;
    public static final int SPRITE_PER_HEADING = 1;

    private enum Heading{
        //куда смотрит Player(танк)
        NORTH(0*SPRITE_SCALE,0*SPRITE_SCALE,1*SPRITE_SCALE, 1*SPRITE_SCALE),
        EAST(6*SPRITE_SCALE,0*SPRITE_SCALE,1*SPRITE_SCALE, 1*SPRITE_SCALE),
        SOUTH(4*SPRITE_SCALE,0*SPRITE_SCALE,1*SPRITE_SCALE, 1*SPRITE_SCALE),
        WEST(2*SPRITE_SCALE,0*SPRITE_SCALE,1*SPRITE_SCALE, 1*SPRITE_SCALE);


        //хранят координаты спрайта
        private int x,y,h,w;

        //конструктор
        Heading(int x, int y, int h, int w){
            this.x = x;
            this.y = y;
            this.h = h;
            this.w = w;
        }

        //метод - вырезаем изображения
        protected BufferedImage texture(TextureAtlas atlas){
            return atlas.cut(x,y,w,h);
        }
    }

    private Heading             heading;
    private Map<Heading,Sprite> spriteMap;
    private float               scale;
    private float               speed;

    public Player(float x, float y, float scale, float speed, TextureAtlas atlas){
        super(EntityType.Player, x, y);

        //создаем карту
        heading = Heading.NORTH;
        spriteMap = new HashMap<Heading,Sprite>();
        this.scale = scale;
        this.speed = speed;
        //связываем какие-то вещи между каким-то направлением и каким-то изображением
        for(Heading h:Heading.values()){
            //создаем индивидуальные спрайты
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas),SPRITE_PER_HEADING,SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet,scale);
            //связали каждое направление с каким-то спрайтом
            spriteMap.put(h,sprite);
        }

    }

    @Override
    public void update(Input input) {
        //перестраховатся не вылез ли танк за экран, н-р
        float newX = x;
        float newY = y;

        //проверка на нажатие кнопки вверх
        if(input.getKey(KeyEvent.VK_UP)){
            newY-=speed;
            //если нажата кнопка вверх нужно поменять направление
            heading = Heading.NORTH;
            //чтоб танк не двигался по диагонали
        } else if (input.getKey(KeyEvent.VK_RIGHT)){
            newX +=speed;
            heading = Heading.EAST;
        }
        else if (input.getKey(KeyEvent.VK_DOWN)){
            newY +=speed;
            heading = Heading.SOUTH;
        }else if (input.getKey(KeyEvent.VK_LEFT)){
            newX -=speed;
            heading = Heading.WEST;
        }
        //проверка на х
        if (newX < 0){
            newX = 0; //танк за экраном
        }else if(newX>=Game.WIDTH - SPRITE_SCALE*scale){
            newX = Game.WIDTH - SPRITE_SCALE*scale;
        }
        //проверка на y
        if (newY < 0){
            newY = 0; //танк за экраном
        }else if(newY>=Game.HEIGHT - SPRITE_SCALE*scale){
            newY = Game.HEIGHT - SPRITE_SCALE*scale;
        }
        x= newX;
        y = newY;
    }

    @Override
    public void render(Graphics2D g) {
        //проверем, куда смотрит наш танк
        //вытащить правильный спрайт в правильном месте
        spriteMap.get(heading).render(g, x, y);

    }

}
