package com.thebyteguru.game.level;

import com.thebyteguru.game.Game;
import com.thebyteguru.graphics.TextureAtlas;
import com.thebyteguru.utils.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * Created by Chessmaster on 30.05.16.
 */
public class Level{

    //статические поля
    public static final int TILE_SCALE            = 8; //размер тайла
    public static final int TILE_IN_GAMW_SCALE    = 2; //на сколько больше размер тайла
    public static final int SCALED_TILE_SIZE      = TILE_SCALE*TILE_IN_GAMW_SCALE;
    //кол-во тайлов в ширину
    public static final int TILES_IN_WIDTH        = Game.WIDTH / SCALED_TILE_SIZE;
    //кол-во тайлов в длинну
    public static final int TILES_IN_HEIGHT       = Game.HEIGHT / SCALED_TILE_SIZE;
    //определяем кол-во тайлов в ширину и ввысоту
    private Integer[][] tileMap;
    //создаем карту
    private Map<TileType, Tile> tiles;
    //хранит координаты тайла - где должен находиться тайп
    private List<Point>grassCords;


    //конструктор
    public Level(TextureAtlas atlas){
        tileMap = new Integer[TILES_IN_WIDTH][TILES_IN_HEIGHT];
        tiles = new HashMap<TileType, Tile>();
        tiles.put(TileType.BRICK, new Tile(atlas.cut(32*TILE_SCALE,0*TILE_SCALE,TILE_SCALE,TILE_SCALE),
                TILE_IN_GAMW_SCALE,TileType.BRICK));
        tiles.put(TileType.METAL, new Tile(atlas.cut(32*TILE_SCALE,2*TILE_SCALE,TILE_SCALE,TILE_SCALE),
                TILE_IN_GAMW_SCALE,TileType.METAL));
        tiles.put(TileType.WATER, new Tile(atlas.cut(32*TILE_SCALE,4*TILE_SCALE,TILE_SCALE,TILE_SCALE),
                TILE_IN_GAMW_SCALE,TileType.WATER));
        tiles.put(TileType.GRASS, new Tile(atlas.cut(34*TILE_SCALE,4*TILE_SCALE,TILE_SCALE,TILE_SCALE),
                TILE_IN_GAMW_SCALE,TileType.GRASS));
        tiles.put(TileType.ICE, new Tile(atlas.cut(36*TILE_SCALE,4*TILE_SCALE,TILE_SCALE,TILE_SCALE),
                TILE_IN_GAMW_SCALE,TileType.ICE));
        tiles.put(TileType.EMPTY, new Tile(atlas.cut(36*TILE_SCALE,6*TILE_SCALE,TILE_SCALE,TILE_SCALE),
                TILE_IN_GAMW_SCALE,TileType.EMPTY));

        //карта левела
      //  tileMap = new int[TILES_IN_WIDTH][TILES_IN_HEIGHT];
        tileMap = Utils.levelParser("res/level.lvl");
        grassCords = new ArrayList<Point>();
        for (int i=0;i<tileMap.length;i++){
            for (int j=0;j<tileMap[i].length;j++){
                Tile tile = tiles.get(TileType.fromNumeric(tileMap[i][j]));
                //проверка
                if (tile.type() == TileType.GRASS)
                    grassCords.add(new Point (j*SCALED_TILE_SIZE,i*SCALED_TILE_SIZE));

            }
        }

    }
    public void update(){

    }
    public void render(Graphics2D g){
        for (int i=0; i<tileMap.length;i++){
            for(int j=0; j<tileMap[i].length;j++){
              Tile tile = tiles.get(TileType.fromNumeric(tileMap[i][j]));
                //проверка
                if (tile.type() != TileType.GRASS)
                tile.render(g,j*SCALED_TILE_SIZE,i*SCALED_TILE_SIZE);
            }
        }

    }
    public void renderGrass(Graphics2D g){
        for (Point p: grassCords){
            tiles.get(TileType.GRASS).render(g,p.x, p.y);
        }

    }
}
