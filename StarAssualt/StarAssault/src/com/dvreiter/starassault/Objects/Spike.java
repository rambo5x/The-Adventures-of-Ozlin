package com.dvreiter.starassault.Objects;

import org.flixel.*;
import com.dvreiter.starassault.Objects.*;
import com.dvreiter.starassault.Player.*;

public class Spike extends FlxSprite
{
    boolean isFalling;
    FlxSprite spriteToPoke;
    FlxSprite kills[];
    FlxSprite getsKilledBy[];
	
	private int sdistance;
	private boolean isAbleToFall;
	private boolean isWatchingSprite;

	public Spike(int x, int y, int Angle)
	{
		super(x, y);
     //   this.x = x;
     //   this.y = y;
		//	acceleration.y = 500;
		loadGraphic("spikes.png", true, true, 16, 16);
		width = 10;//12
		height = 10;
	//	centerOffsets();
		
		
		angle = Angle;
		acceleration.y = 0;
        maxVelocity.y = 150;
		
		immovable = true;
		//moves = false;
		
		isWatchingSprite = false;
		isAbleToFall = false;

	}
	@Override 
	public void destroy()
	{
		super.destroy();

	}

	@Override 
	public void update()
	{
		super.update();
		if(angle == 180){
		if (isFalling){
			acceleration.y=200;
		}else{
        	if(isWatchingSprite && spriteToPoke != null) {
          		if(spriteToPoke.x < x + 12 && spriteToPoke.x > x - 12) {//4
					if(spriteToPoke.y <= sdistance + y && spriteToPoke.y > y)
		//			moves = true;
					fall();
            	} else {
      //         		moves = false;
	  				acceleration.y=0;
           		}
        	}
		}
		}
	}

	public void WatchSprite(FlxSprite player) {
        spriteToPoke = player;
        isWatchingSprite = true;
    }
	
	public void getDistance(int distance){
		sdistance = distance;
	}
    public void fall() {
        isFalling = true;
    }
}
