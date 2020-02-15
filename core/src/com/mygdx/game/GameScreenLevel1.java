/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patri
 */
public class GameScreenLevel1 implements Screen{
    MyGdxGame game;
    
    Stage stage;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    
    Ninja ninja;
    ZombieMale zombie;
    ZombieFemale zombie_fem;
    Coin coin;
    public List<Coin> coins;
    Munieco_Pasa_Level munieco;
    public List<Munieco_Mas_5> muniecos_peque;
    
    int score;    

    GameScreenLevel1(MyGdxGame game) {
        this.game=game;
    }

    public void show() {
        map = new TmxMapLoader().load("level1ninja.tmx");
        final float pixelsPerTile = 16;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();

        stage = new Stage();
        stage.getViewport().setCamera(camera);

        ninja = new Ninja();
        ninja.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        ninja.setPosition(10, 10);
        stage.addActor(ninja);
        
        zombie = new ZombieMale();
        zombie.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        zombie.setPosition(0, 10);
        stage.addActor(zombie);
        
        zombie_fem = new ZombieFemale();
        zombie_fem.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        zombie_fem.setPosition(154, 15);
        stage.addActor(zombie_fem);
        
	this.coins = new ArrayList<Coin>();
        this.loadCoin(0, 0);
        
        munieco = new Munieco_Pasa_Level();
        this.loadMunieco(0,0);
        
        muniecos_peque = new ArrayList();
        this.loadMuniecoPeque(0,0);
        
        score = 100;
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        camera.position.x = ninja.getX();
        if(ninja.getX()<=20){
            camera.position.x=20;
        }
        if(ninja.getX()>=189.6){
            camera.position.x=189.6f;
        }
        if(ninja.getX()<=0){
            ninja.setX(0);
        }
        if(ninja.getX()>=209){
            ninja.setX(209);
        }
        
        //MUERTE
        if(ninja.getY()<=0){
            this.muerte();
        }
        
        if(score <0){
            this.muerte();
        }
        
        camera.update();
        
        this.overlaps();
        
        renderer.setView(camera);
        renderer.render();
        
        this.movimientos(delta);

        stage.act(delta);
        stage.draw();
        
        game.batch.begin();
        game.font.draw(game.batch, "Score: " + score, 100, 480);
        game.batch.end();
    }

    public void dispose() {
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false,30* width / height, 20);
    }

    public void resume() {
    }
    
    private void loadCoin(float startX, float startY) {
        TiledMapTileLayer monedas = (TiledMapTileLayer) map.getLayers().get("monedas1");
        
        float endX = startX + monedas.getWidth();
        float endY = startY + monedas.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (monedas.getCell(x, y) != null) {
                    if (monedas.getProperties().get("visible", Boolean.class) == true) {
                        monedas.setCell(x, y, null);
                        this.spawMoneda(x,y);
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }
    
    private void loadMuniecoPeque(float startX, float startY) {
        TiledMapTileLayer munieco_peque = (TiledMapTileLayer) map.getLayers().get("munieco_peque");
        
        float endX = startX + munieco_peque.getWidth();
        float endY = startY + munieco_peque.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (munieco_peque.getCell(x, y) != null) {
                    if (munieco_peque.getProperties().get("visible", Boolean.class) == true) {
                        munieco_peque.setCell(x, y, null);
                        this.spawMuniecoPeque(x,y);
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }
    
    private void loadMunieco(float startX, float startY) {
        TiledMapTileLayer munieco_pasa_level = (TiledMapTileLayer) map.getLayers().get("estrella1");
        
        float endX = startX + munieco_pasa_level.getWidth();
        float endY = startY + munieco_pasa_level.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (munieco_pasa_level.getCell(x, y) != null) {
                    if (munieco_pasa_level.getProperties().get("visible", Boolean.class) == true) {
                        munieco_pasa_level.setCell(x, y, null);
                        munieco = new Munieco_Pasa_Level();                        
                        munieco.setPosition(x, y);
                        stage.addActor(munieco);                        
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }
    
    public void spawMoneda (float x, float y){
        Coin coin2 = new Coin();
        coin2.setPosition(x, y);
        stage.addActor(coin2);
        coins.add(coin2);
    }
    
    public void spawMuniecoPeque (float x, float y){
        Munieco_Mas_5 mun = new Munieco_Mas_5();
        mun.setPosition(x, y);
        stage.addActor(mun);
        muniecos_peque.add(mun);
    }
    
    public void overlapsCoin(){
        int i = 0;
        for (Coin coin : coins) {
            if(coin.getX()-1.5f < ninja.getX() &&coin.getX()+1.5f > ninja.getX() &&coin.getY()-1.5f < ninja.getY() && coin.getY()+1.5f > ninja.getY()){
                score+=15;
                coin.remove();
                coin.setY(100);
            }
            i++;
        }      
    }
    
    public void overlapsMuniecoPeque(){
        int i = 0;
        for (Munieco_Mas_5 muni : muniecos_peque) {
            if(muni.getX()-1.5f < ninja.getX() &&muni.getX()+1.5f > ninja.getX() &&muni.getY()-1.5f < ninja.getY() && muni.getY()+1.5f > ninja.getY()){
                //dropSound.play();
                score+=5;
                muni.remove();
                muni.setY(100);
            }
            i++;
        }      
    }
    
    public void overlapsMunieco(){
        if(munieco.getX()-1.5f < ninja.getX() &&munieco.getX()+1.5f > ninja.getX() &&munieco.getY()-1.5f < ninja.getY() && munieco.getY()+1.5f > ninja.getY()){
            game.setScreen(new GameScreenLevel2(game, this.score, this.ninja));                      
        }                  
    }
    
    public void overlapsZombieMale(){        
        if(zombie.getX()-1f < ninja.getX() &&zombie.getX()+1f > ninja.getX() &&zombie.getY()-1f < ninja.getY() && zombie.getY()+1f > ninja.getY()){
            score-=25;
            if(zombie.getX()>ninja.getX()){
                ninja.xVelocity -= 50;
            }else{
                ninja.xVelocity += 50;
            }            
        }      
    }
    
    public void overlapsZombieFemale(){        
        if(zombie_fem.getX()-1f < ninja.getX() &&zombie_fem.getX()+1f > ninja.getX() &&zombie_fem.getY()-1f < ninja.getY() && zombie_fem.getY()+1f > ninja.getY()){
            score-=40;
            ninja.setX(50);                       
        }      
    }
    
    public void muerte(){
        game.setScreen(new FinalScreen(game, this.score));
        dispose();
    }
    
    public void overlaps(){
        this.overlapsCoin();
        this.overlapsMunieco();
        this.overlapsZombieMale();
        this.overlapsZombieFemale();
        this.overlapsMuniecoPeque();
    }
    
    public void movimientos(float delta){
        this.zombie.movimiento_zombie(delta, ninja.getX());
        this.zombie_fem.movimiento_zombie(delta, ninja.getX());
    }
}