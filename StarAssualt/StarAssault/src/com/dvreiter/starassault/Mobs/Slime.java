package com.dvreiter.starassault.Mobs;

import org.flixel.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Player.*;

public class Slime extends FlxSprite {
    boolean isFollowingSprite;
    FlxSprite spriteToFollow;
    int _jumpPower;
	int jumpTimer, mode = 0;
	boolean jumped;
	Player _player;
	JoyStickPlayer jplayer;
	
	@Override 
	public Slime(){
		acceleration.y = 500;
        int runSpeed = 40;
        _jumpPower = 125;
		jumpTimer = 0;
		jumped=false;
        maxVelocity.y = _jumpPower;
        maxVelocity.x = runSpeed;
		//       acceleration.y = 500;
        loadGraphic("slime.png", true, true, 16, 16);      
		addAnimation("idle_stand", new int[]{0}, 0, false);
		addAnimation("idle_jumpmove", new int[]{0,1,2,3,4}, 4, true);
		health = 3;
   //     isFollowingSprite = false;
	}
	
    @Override
    public Slime(int x, int y, int width, int height, int accel,int Mode) {
        super(width, height);
        this.x = x;
        this.y = y;
		acceleration.y = accel;
		mode = Mode;
        int runSpeed = 40;
        _jumpPower = 125;
		jumpTimer = 0;
		jumped=false;
        maxVelocity.y = _jumpPower;
        maxVelocity.x = runSpeed;
		//       acceleration.y = 500;
        loadGraphic("slime.png", true, true, 16, 16);      
		addAnimation("idle_stand", new int[]{0}, 0, false);
		addAnimation("idle_jumpmove", new int[]{0,1,2,3,4}, 4, true);
		health = 3;
        isFollowingSprite = false;
    }
    @Override
    public void update() {
        
        super.update();
		
		if(mode == 1){
		if(isTouching(FlxObject.FLOOR)){
			jumpTimer += 1;
			if (jumped){
				velocity.x = 0;
				jumped=false;
				play("idle_stand");
			}
		}
		
		if (jumpTimer > 20){
        	if(isFollowingSprite && spriteToFollow != null) {
            	if(spriteToFollow.x != x) {
                	if(spriteToFollow.x > x) {
                    	//if(isTouching(FlxObject.FLOOR))
                    	jumpRight();
                	} else if(spriteToFollow.x < x) {
                    	//if(isTouching(FlxObject.FLOOR))
                    	jumpLeft();
                	}
				}
        	}
			jumpTimer = 0;
		}
		}else{
			if(isTouching(FlxObject.FLOOR)){
				jumpTimer += 1;
				if (jumped){
					velocity.x = 0;
					jumped=false;
					play("idle_stand");
				}
			}

			if (jumpTimer > 20){
					if(_player.x != x) {
						if(_player.x > x) {
							//if(isTouching(FlxObject.FLOOR))
							jumpRight();
						} else if(_player.x < x) {
							//if(isTouching(FlxObject.FLOOR))
							jumpLeft();
						}
					}
				}
				jumpTimer = 0;
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

    public void followSprite(FlxSprite player) {
        spriteToFollow = player;
        isFollowingSprite = true;
    }
	
	public void followSprite(FlxPoint Location, Player player){
		_player = player;
		super.reset(Location.x-width/2,Location.y-height/2);
	}
	
	public void followJSprite(FlxPoint Location, JoyStickPlayer jplayer1){
		jplayer = jplayer1;
		super.reset(Location.x-width/2,Location.y-height/2);
	}
    public void jumpLeft() {
        if(isTouching(FlxObject.FLOOR)){
			jumped=true;
			play("idle_jumpmove");
			velocity.y = - maxVelocity.y/1.2f;
		    velocity.x = -40;
		}else if(isTouching(FlxObject.CEILING)){
			jumped=true;
			play("idle_jumpmove");
			velocity.y = + maxVelocity.y/1.2f;
	    	velocity.x = -40;
		}
    }
    public void jumpRight() {
        if(isTouching(FlxObject.FLOOR)){
			jumped=true;
			play("idle_jumpmove");
			velocity.y = - maxVelocity.y/1.2f;
	    	velocity.x = 40;
		}else if(isTouching(FlxObject.CEILING)){
			jumped=true;
			play("idle_jumpmove");
			velocity.y = + maxVelocity.y/1.2f;
	    	velocity.x = 40;
		}
    }
}
