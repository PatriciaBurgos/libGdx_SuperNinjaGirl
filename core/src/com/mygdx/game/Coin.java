package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Coin extends Image {
    Animation walk;
    public static Texture items;
    
    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    TiledMapTileLayer layer;

    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;

    public Coin() {
        final float width = 0.5f;
        final float height = 0.8f;
        this.setSize(1, height / width);

        this.setY(20);
        this.setX(20);

        items = loadTexture("items.png");
        walk = new Animation(0.2f, new TextureRegion(items, 128, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32),
			new TextureRegion(items, 192, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32));
        walk.setPlayMode(Animation.PlayMode.LOOP);
    }
    public static Texture loadTexture (String file) {
            return new Texture(Gdx.files.internal(file));
    }

    public void act(float delta) {
        time = time + delta;       
        
    }

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;
        frame = (TextureRegion) walk.getKeyFrame(time);        
        batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());        
    }
    
}
