/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * @author patri
 */
public class Munieco_Pasa_Level extends Image {
    TextureRegion stand;
    
    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    TiledMapTileLayer layer;

    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;

    public Munieco_Pasa_Level() {
        final float width = 20;
        final float height = 18;
        this.setSize(1, height / width);

        this.setY(20);
        this.setX(20);

        Texture munieco = new Texture("munieco.png");
        TextureRegion[][] grid = TextureRegion.split(munieco, (int) width, (int) height);

        stand = grid[0][0];
    }
    public static Texture loadTexture (String file) {
            return new Texture(Gdx.files.internal(file));
    }

    public void act(float delta) {
        time = time + delta;       
        
    }

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;
        frame = stand;        
        batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());        
    }   
}