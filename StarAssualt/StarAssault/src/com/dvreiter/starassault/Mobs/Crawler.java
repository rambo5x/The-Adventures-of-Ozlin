package com.dvreiter.starassault.Mobs;

import org.flixel.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Player.*;

public class Crawler extends FlxSprite {
    boolean isWatchingSprite,jumped;
    FlxSprite spriteToBite;
    int _jumpPower, mode = 0;
	FlxTilemap levelToFollow;
	Player _player;
	JoyStickPlayer jplayer;
	
	@Override
	public Crawler(){
		acceleration.y = 500;
        int runSpeed = 40;
        _jumpPower = 400;
        maxVelocity.y = _jumpPower;
		drag.x = runSpeed*4;
        maxVelocity.x = runSpeed;
		//       acceleration.y = 500;
        loadGraphic("crawler.png", true, true, 16, 16);      
		addAnimation("idle_stand", new int[]{0}, 0, false);
		addAnimation("idle_lwalk", new int[]{7,6,5,4}, 4, true);
		addAnimation("idle_rwalk", new int[]{0,1,2,3}, 4, true);
		health = 3;
		play("idle_rwalk");
      //   isWatchingSprite = false;
	}

    @Override
    public Crawler(int x, int y, int width, int height, int accel,int Mode) {
        super(width, height);
        this.x = x;
        this.y = y;
		acceleration.y = accel;
		mode = Mode;
        int runSpeed = 40;
        _jumpPower = 400;
        maxVelocity.y = _jumpPower;
		drag.x = runSpeed*4;
        maxVelocity.x = runSpeed;
		//       acceleration.y = 500;
        loadGraphic("crawler.png", true, true, 16, 16);      
		addAnimation("idle_stand", new int[]{0}, 0, false);
		addAnimation("idle_lwalk", new int[]{7,6,5,4}, 4, true);
		addAnimation("idle_rwalk", new int[]{0,1,2,3}, 4, true);
		health = 3;
		play("idle_rwalk");
        isWatchingSprite = false;
    }
    @Override
    public void update() {

        super.update();
		if(mode == 1){
			int X = (Math.round(this.x))/16;
			int Y = (Math.round(this.y))/16;

			int proximety = Math.round(getProximety(this.x,this.y,spriteToBite.x,spriteToBite.y));

			if(proximety < 40){
				getMidpoint(_point);
				jumpTo(_point,this.x,this.y,spriteToBite.x,spriteToBite.y);
		//	getMidpoint(_point);
		//	JumpTo(_point,x,y,spriteToBite.x,spriteToBite.y);
			}else{
				
				if(this.justTouched(FlxObject.RIGHT)){
					stop();
					goLeft();
				}
				if(this.justTouched(FlxObject.LEFT)){
					stop();
					goRight();
				}
				
			}	
			}else{
				int X = (Math.round(this.x))/16;
				int Y = (Math.round(this.y))/16;

				int proximety = Math.round(getProximety(this.x,this.y,_player.x,_player.y));

				if(proximety < 40){
					getMidpoint(_point);
					jumpTo(_point,this.x,this.y,_player.x,_player.y);
					//	getMidpoint(_point);
					//	JumpTo(_point,x,y,spriteToBite.x,spriteToBite.y);
				}else{
					if(this.justTouched(FlxObject.RIGHT)){
						stop();
						goLeft();
					}
					if(this.justTouched(FlxObject.LEFT)){
						stop();
						goRight();
					}
					
					
				}	
				
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

    public void WatchSprite(FlxSprite player, FlxTilemap level) {
        spriteToBite = player;
		levelToFollow = level;
        isWatchingSprite = true;
    }
	
	public void WatchSprite(FlxPoint Location, Player player, FlxTilemap level) {
        _player = player;
		levelToFollow = level;
        super.reset(Location.x-width/2,Location.y-height/2);
    }
	
	public void WatchJSprite(FlxPoint Location, JoyStickPlayer jplayer1, FlxTilemap level) {
        jplayer = jplayer1;
		levelToFollow = level;
        super.reset(Location.x-width/2,Location.y-height/2);
    }
	
	
	public void goLeft() {
      	acceleration.x -= drag.x;
		//setFacing(LEFT);
		play("idle_lwalk");
		
    }
	
    public void goRight() {
		acceleration.x += drag.x;
		//setFacing(RIGHT);
		play("idle_rwalk");
		
    }
	
	public void jumpTo(FlxPoint Location, float Ex,float Ey,float Px,float Py)
	{
		super.reset(Location.x-width/2,Location.y-height/2);
		velocity.x = ((-Ex/16) - (-Px/16))*50;
		velocity.y = ((-Ey/16) - (-Py/16))*50;	
    }
  
	private float getProximety(float x1,float y1,float x2,float y2)
	{
		float X= Math.abs(x1)- Math.abs(x2);
		float Y= Math.abs(y1)- Math.abs(y2);
		return Math.abs((X+Y)/2);
	}
	
	public void stop() {
        acceleration.x = 0;
    }
}
/*
	int X = (Math.round(this.x))/16;
	int Y = (Math.round(this.y))/16; 

	 if(isWatchingSprite && spriteToBite != null) {
	 if(spriteToBite.x != x) {
	 /*        if(spriteToBite.x > x - 32) {
	 if(spriteToBite.y <= 32 - y || spriteToBite.y >= 32 + y)
	 jumpRight();
	 } else if(spriteToBite.x < x + 32) {
	 if(spriteToBite.y <= 32 - y || spriteToBite.y >= 32 + y)
	 jumpLeft();
	 }else{
	if(spriteToBite.x < x - 16 && spriteToBite.x > x + 16){
		jump();
	}

	if(levelToFollow.getTile(X + 4,Y) != 0){//|| levelToFollow.getTile(X + 1,Y + 1) != 0
		stop();
		goLeft();
		//		if(spriteToBite.x < x - 16) {
		//	if(spriteToBite.y <= 16 - y || spriteToBite.y >= 16 + y)
		//				jump();
		//		}
	}
	if(levelToFollow.getTile(X - 4,Y) != 0){//|| levelToFollow.getTile(X - 1,Y - 1) != 0
		stop();
		goRight();
		//	if(spriteToBite.x > x + 16) {
		//		if(spriteToBite.y <= 16 - y || spriteToBite.y >= 16 + y)
		//			jump();
		//		}
	}
	//	}
	}
}
*/
