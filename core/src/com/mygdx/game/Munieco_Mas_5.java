/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * @author patri
 */
public class Munieco_Mas_5 extends Image {
    TextureRegion stand;
    
    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = false;
    TiledMapTileLayer layer;

    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 5f;
    final float DAMPING = 0.87f;

    public Munieco_Mas_5() {        
        final float width = 20;
        final float height = 17; //120
        this.setSize(1, height / width);

        Texture munieco = new Texture("munieco4.png");
        TextureRegion[][] grid = TextureRegion.split(munieco, (int) width, (int) height);
        
        stand = grid[0][0];
    }

    public void movimiento_munieco2(float delta, float ninja_x) {
        time = time + delta;
    }
    
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;

        frame = stand; //STAND

        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }
}