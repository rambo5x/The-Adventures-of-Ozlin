package com.dvreiter.starassault.Mobs;

import org.flixel.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Objects.*;
import com.badlogic.gdx.graphics.g2d.*;

public class Silverfish extends FlxSprite {
    boolean isFollowingSprite;
	FlxSprite bulletToAvoid;
    FlxSprite spriteToFollow;
    int _jumpPower;
	boolean isUpsideDown;
	FlxTilemap levelToFollow;
	
	@Override
	public Silverfish(){
		acceleration.y = 500;
        int runSpeed = 100;
        _jumpPower = 50;
        maxVelocity.y = _jumpPower;
        drag.x = runSpeed*4;
        maxVelocity.x = runSpeed;
        loadGraphic("silverfish.png", true, true, 16, 8);     
		addAnimation("idle_stand", new int[]{0}, 0, false);
		addAnimation("idle_left", new int[]{7,6,5,4}, 4, true);
		addAnimation("idle_right", new int[]{0,1,2,3}, 4, true);
		health = 2;
		goRight(); 
	}

    @Override
    public Silverfish(int x, int y, int width, int height, int accel) {
        super(width, height);
        this.x = x;
        this.y = y;
		acceleration.y = accel;
        int runSpeed = 50;
        _jumpPower = 50;
        maxVelocity.y = _jumpPower;
        drag.x = runSpeed*4;
        maxVelocity.x = runSpeed;
        loadGraphic("silverfish.png", true, true, 16, 8);     
		addAnimation("idle_stand", new int[]{0}, 0, false);
		addAnimation("idle_left", new int[]{7,6,5,4}, 4, true);
		addAnimation("idle_right", new int[]{0,1,2,3}, 4, true);
		health = 2;
		goRight();
    }

    @Override
    public void update() {
        super.update();
		
	/*	int X = (Math.round(this.x))/16;
		int Y = (Math.round(this.y))/16;

		if(levelToFollow.getTile(X + 1,Y) != 0 )
			goLeft();

		if(levelToFollow.getTile(X - 1,Y) != 0 )
			goRight();*/
		
		
	/*	int X = (Math.round(this.x))/16;
		int Y = (Math.round(this.y))/16;

		if(levelToFollow.getTile(X + 4,Y) != 0){//|| levelToFollow.getTile(X + 1,Y + 1) != 0
			stop();
			goLeft();
		}
		if(levelToFollow.getTile(X - 4,Y) != 0){//|| levelToFollow.getTile(X - 1,Y - 1) != 0
			stop();
			goRight();
		}*/
		if(this.justTouched(FlxObject.RIGHT)){
			stop();
			goLeft();
		}
		if(this.justTouched(FlxObject.LEFT)){
			stop();
			goRight();
		}
		
	}

	@Override
	public void destroy()
	{
		// TODO: Implement this method
		super.destroy();

	}

	@Override public void hurt(float Damage)
	{
		flicker(0.6f);//0.2f
		FlxG.score += 10;
		super.hurt(Damage);
	}
    
    public void goLeft() {
        acceleration.x -= drag.x;
	//	setFacing(LEFT);
		play("idle_left");
    }
    public void goRight() {
        acceleration.x += drag.x;
	//	setFacing(RIGHT);
		play("idle_right");
    }
    public void stop() {
        acceleration.x = 0;
    }
	
	public void watchLevel(FlxTilemap level){
		levelToFollow = level;
	}
	
	public void watchLevel(FlxPoint Location, FlxTilemap level){
		levelToFollow = level;
		super.reset(Location.x-width/2,Location.y-height/2);
    }
	
    public void jump() {
        if(isTouching(FlxObject.FLOOR))
			velocity.y = - maxVelocity.y/1.2f;
		else if(isTouching(FlxObject.CEILING))
			velocity.y = + maxVelocity.y/1.2f;
    }
}
