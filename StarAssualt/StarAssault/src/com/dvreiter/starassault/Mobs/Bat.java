package com.dvreiter.starassault.Mobs;

import org.flixel.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Objects.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.dvreiter.starassault.Player.*;

public class Bat extends FlxSprite {
    boolean isFollowingSprite;
    private Player _player;
	private JoyStickPlayer jplayer;
   private int _jumpPower,mode ;
	FlxSprite spriteToFollow;
	FlxTilemap levelToFollow;
	
	@Override
	public Bat(){
        int runSpeed = 50;
        _jumpPower = 50;
        maxVelocity.y = _jumpPower;
		//    drag.x = runSpeed*8;
        maxVelocity.x = runSpeed;
		//       acceleration.y = 500;
        loadGraphic("bat.png", true, true, 16, 16);      
		addAnimation("idle_stand", new int[]{0}, 0, false);
		addAnimation("idle_fly", new int[]{0,1,2,3}, 4, true);
		health = 2;
   //     isFollowingSprite = false;
	}
	
    @Override
    public Bat(int x, int y, int width, int height, int Mode) {
        super(width, height);
        this.x = x;
        this.y = y;
        int runSpeed = 50;
        _jumpPower = 50;
		mode = Mode;
        maxVelocity.y = _jumpPower;
    //    drag.x = runSpeed*8;
        maxVelocity.x = runSpeed;
		//       acceleration.y = 500;
        loadGraphic("bat.png", true, true, 16, 16);      
		addAnimation("idle_stand", new int[]{0}, 0, false);
		addAnimation("idle_fly", new int[]{0,1,2,3}, 4, true);
		health = 2;
        isFollowingSprite = false;
    }

    @Override
    public void update() {
        acceleration.x = 0;
        super.update();
		if(mode == 1){
		if(isFollowingSprite && spriteToFollow != null) {
            if(spriteToFollow.x != x) {
                if(spriteToFollow.x > x ) {
                    //if(isTouching(FlxObject.FLOOR))
					play("idle_fly");
                    flyRight();
                } else if(spriteToFollow.x < x) {
                    //if(isTouching(FlxObject.FLOOR))
					play("idle_fly");
                    flyLeft();
                }
            } else {
                stop();
            }
        }
		}else if(mode == 2){
			if(_player.x != x) {
                if(_player.x > x ) {
                    //if(isTouching(FlxObject.FLOOR))
					play("idle_fly");
                    flyRight();
                } else if(_player.x < x) {
                    //if(isTouching(FlxObject.FLOOR))
					play("idle_fly");
                    flyLeft();
                }
            } else {
                stop();
            }
		}else if(mode == 3){
			if(jplayer.x != x) {
                if(jplayer.x > x ) {
                    //if(isTouching(FlxObject.FLOOR))
					play("idle_fly");
                    flyRight();
                } else if(jplayer.x < x) {
                    //if(isTouching(FlxObject.FLOOR))
					play("idle_fly");
                    flyLeft();
                }
            } else {
                stop();
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

    public void followSprite(FlxSprite player) {
        spriteToFollow = player;
        isFollowingSprite = true;	
    }
	
	public void followSprite(FlxPoint Location,Player player) {
        _player = player;
		mode = 2;
     //   isFollowingSprite = true;	
		super.reset(Location.x-width/2,Location.y-height/2);
    }
	
	public void followJSprite(FlxPoint Location,JoyStickPlayer jplayer1) {
        jplayer = jplayer1;
		mode = 3;
		//   isFollowingSprite = true;	
		super.reset(Location.x-width/2,Location.y-height/2);
    }
	/*public void watchLevel(FlxTilemap level){
		levelToFollow = level;
	}*/
	
    public void flyLeft() {
		if(mode == 1){
		velocity.x = -40;
		if(spriteToFollow.y < y){
		velocity.y = - maxVelocity.y/1.2f;
		}else if(spriteToFollow.y > y){
		velocity.y = + maxVelocity.y/1.2f;
		}
		}else if(mode == 2){
			velocity.x = -40;
			if(_player.y < y){
				velocity.y = - maxVelocity.y/1.2f;
			}else if(_player.y > y){
				velocity.y = + maxVelocity.y/1.2f;
			}
		}else if(mode == 3){
			velocity.x = -40;
			if(jplayer.y < y){
				velocity.y = - maxVelocity.y/1.2f;
			}else if(jplayer.y > y){
				velocity.y = + maxVelocity.y/1.2f;
			}
		}
	/*	if(y <= levelToFollow.y + 8){
			y += 2;
		}else if(y >= levelToFollow.y - 8){
			y -= 2;
		}else if(x <= levelToFollow.x + 8){
			x += 2;
		}else if(x >= levelToFollow.x - 8){
			x -= 2;
		}
	/*	if(isTouching(FlxObject.FLOOR)){
			acceleration.y = 0;
			velocity.y = 0;
			y = y - 8;
		}else if(isTouching(FlxObject.LEFT)){
			acceleration.x = 0;
			velocity.x = 0;
			x = x + 8;
		}else if(isTouching(FlxObject.RIGHT)){
			acceleration.x = 0;
			velocity.x = 0;
			x = x - 8;
		}else if(isTouching(FlxObject.CEILING)){
			acceleration.y = 0;
			velocity.y = 0;
			y = y + 8;
		}*/
	}
    public void flyRight() {
		if(mode == 1){
	   velocity.x = 40;
	   if(spriteToFollow.y < y){
	   velocity.y = - maxVelocity.y/1.2f;
	   }else if(spriteToFollow.y > y){
	   velocity.y = + maxVelocity.y/1.2f;
	  }
		}else if(mode == 2){
			velocity.x = 40;
			if(_player.y < y){
				velocity.y = - maxVelocity.y/1.2f;
			}else if(_player.y > y){
				velocity.y = + maxVelocity.y/1.2f;
			}
		}else if(mode == 3){
			velocity.x = 40;
			if(jplayer.y < y){
				velocity.y = - maxVelocity.y/1.2f;
			}else if(jplayer.y > y){
				velocity.y = + maxVelocity.y/1.2f;
			}
		}
	}
	/*	if(isTouching(FlxObject.FLOOR)){
			acceleration.y = 0;
			velocity.y = 0;
			y = y - 8;
		}else if(isTouching(FlxObject.LEFT)){
			acceleration.x = 0;
			velocity.x =0;
			x = x + 8;
		}else if(isTouching(FlxObject.RIGHT)){
			acceleration.x = 0;
			velocity.x =0;
			x = x - 8;
		}else if(isTouching(FlxObject.CEILING)){
			acceleration.y = 0;
			velocity.y = 0;
			y = y + 8;
		}*/

    public void stop() {
        acceleration.x = 0;
		acceleration.y = 0;
    }
 
}
