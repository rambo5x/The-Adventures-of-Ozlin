 package com.dvreiter.starassault.Mobs;

import org.flixel.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Player.*;


public class Crusher extends FlxSprite{
	boolean isWatchingSprite;
    boolean isFalling,shouldFall;
    FlxSprite spriteToCrush;
	int startHeight,mode = 0,sdistance;
	Player _player;
	JoyStickPlayer jplayer;
	
	//boolean isUpsideDown;

	@Override
	public Crusher(){
		acceleration.y = 0;
        maxVelocity.y = 150;
		//       acceleration.y = 500;
        loadGraphic("crusher.png", true, true, 16, 16);      
		addAnimation("idle_normal", new int[]{0});
		addAnimation("idle_rage", new int[]{1});
		immovable = true;
		health = 3;
		shouldFall = true;
        isWatchingSprite = false;
		isFalling = false;
		play("idle_normal");
	}

    @Override
    public Crusher(int x, int y,int Mode) {
        super(x,y);
		startHeight = y;
		acceleration.y = 0;
        maxVelocity.y = 150;
		//       acceleration.y = 500;
		mode = Mode;
        loadGraphic("crusher.png", true, true, 16, 16);      
		addAnimation("idle_normal", new int[]{0});
		addAnimation("idle_rage", new int[]{1});
		health = 3;
        isWatchingSprite = false;
		isFalling = false;
		shouldFall = true;
		play("idle_normal");
     }
    @Override
    public void update() {
        acceleration.x = 0;
        super.update();
		if(mode == 1){
		if (isFalling){
			acceleration.y=200;
			play("idle_rage");
			if (isTouching(FlxObject.FLOOR)){
				isFalling = false;
				acceleration.y=0;
				FlxG.shake(0.035f,0.25f);
			}
		}else{
        	if(isWatchingSprite && spriteToCrush != null) {
          		if(spriteToCrush.x < x + 12 && spriteToCrush.x > x -12) {
					if(spriteToCrush.y <= sdistance + y && spriteToCrush.y > y && shouldFall)//Minimum value for normal size crushers.//default size 64
						//May vary depending on y value so another if will need to be added for specific crushers that will crush from longer distances.
					fall();
					
            	} else {
               		rise();
           		}
        	}
		}
		}else{
			if (isFalling){
				acceleration.y=200;
				play("idle_rage");
				if (isTouching(FlxObject.FLOOR)){
					isFalling = false;
					acceleration.y=0;
					FlxG.shake(0.035f,0.25f);
				}
			}else{
				if(_player.x != x) {
					if(_player.x < x + 12 && _player.x > x -12) {
						if(_player.y <= sdistance + y && _player.y > y && shouldFall)//Minimum value for normal size crushers.
						//May vary depending on y value so another if will need to be added for specific crushers that will crush from longer distances.
							fall();
					} else {
						rise();
					}
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
	
	public void setFallDistance(int distance){
		sdistance = distance;
	}

    public void WatchSprite(FlxSprite player) {
        spriteToCrush = player;
        isWatchingSprite = true;
    }
	
	public void WatchSprite(FlxPoint Location, Player player){
		_player = player;
		//isWatchingSprite = true;
		super.reset(Location.x-width/2,Location.y-height/2);
	}
	public void WatchJSprite(FlxPoint Location, JoyStickPlayer jplayer1){
		jplayer = jplayer1;
		//isWatchingSprite = true;
		super.reset(Location.x-width/2,Location.y-height/2);
	}
	
	
    public void fall() {
        isFalling = true;
		shouldFall = false;
    }
	
	public void rise(){
		if (y > startHeight){
			velocity.y = -20;
			play("idle_normal");
		}
		else{
			velocity.y = 0;
			shouldFall = true;
		}
	}
}
