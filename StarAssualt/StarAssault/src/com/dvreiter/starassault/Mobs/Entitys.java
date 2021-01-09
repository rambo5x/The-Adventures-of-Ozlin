package com.dvreiter.starassault.Mobs;

import org.flixel.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Objects.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.dvreiter.starassault.Player.*;

public class Entitys extends FlxSprite {
    boolean isFollowingSprite;
	FlxSprite bulletToAvoid;
    FlxSprite spriteToFollow;
    FlxSprite kills[];
    FlxSprite getsKilledBy[];
    float _jumpPower;
	float _runSpeed;
	Player _player;
	boolean isUpsideDown;

    @Override
    public Entitys(float x, float y) {
        super(x, y);
        
        //loadGraphic("terminator.png", true, true, 16, 16);      
		health = 3;
		isFollowingSprite = false;
    }

	/*@Override
	 public Enemy(int x, int y, int width, int height, int accel,int Mode) {
	 super(width, height);
	 this.x = x;
	 this.y = y;
	 acceleration.y = accel;
	 int runSpeed = 80;
	 _jumpPower = 200;
	 maxVelocity.y = _jumpPower;
	 drag.x = runSpeed*8;
	 mode = Mode;
	 maxVelocity.x = runSpeed;
	 //       acceleration.y = 500;
	 loadGraphic("terminator.png", true, true, 16, 16);      
	 health = 3;
	 //       isFollowingSprite = false;
	 }*/

    @Override
    public void update() {
        acceleration.x = 0;
        super.update();
		//if(mode == 1){
			if(isFollowingSprite && spriteToFollow != null) {
				if(spriteToFollow.x != x) {
					if(isTouching(FlxObject.WALL))jump();
					if(spriteToFollow.x > x ) {
						//if(isTouching(FlxObject.FLOOR))
						goRight();
					} else if(spriteToFollow.x < x) {
						//if(isTouching(FlxObject.FLOOR))
						goLeft();
					}
				} else {
					stop();
				}
			//}
		}else{
			if(_player.x != x) {
				if(isTouching(FlxObject.WALL))jump();
				if(_player.x > x ) {
					//if(isTouching(FlxObject.FLOOR))
					goRight();
				} else if(_player.x < x) {
					//if(isTouching(FlxObject.FLOOR))
					goLeft();
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

	public void init(float getX,float getY,int getWidth,int getHeight,int getAccel,FlxSprite player){
		this.x = getX;
		this.y = getY;
		this.width = getWidth;
		this.height = getHeight;
		this.acceleration.y = getAccel;
		this.spriteToFollow = player;
	}

	public void followSprite(FlxPoint Location,Player player) {
		_player = player;
		super.reset(Location.x-width/2,Location.y-height/2);
    }

	public void followSprite(FlxSprite player) {
        spriteToFollow = player;
        isFollowingSprite = true;	
    }
    public void goLeft() {
        acceleration.x -= drag.x;
    }
    public void goRight() {
        acceleration.x += drag.x;
    }
    public void stop() {
        acceleration.x = 0;
    }
    public void jump() {
        if(isTouching(FlxObject.FLOOR))
			velocity.y = - maxVelocity.y/1.2f;
		else if(isTouching(FlxObject.CEILING))
			velocity.y = + maxVelocity.y/1.2f;
    }
	
	public void setSpeed(float MaxSpeed, float JumpPower){
		_jumpPower = JumpPower;
		_runSpeed = MaxSpeed;
		maxVelocity.y = _jumpPower;
        drag.x = _runSpeed*8;
        maxVelocity.x = _runSpeed;
	}
	
	public void teleport(FlxSprite sprite1,FlxSprite sprite2)
	{
		sprite1.x = sprite2.x;
		sprite1.y = sprite2.y;
	}
	
	public void teleport(FlxSprite otherSprite){
		this.x = otherSprite.x;
		this.y = otherSprite.y;
	}
	
	public void teleport(FlxSprite spriteToTeleport,float Xposition , float Yposition , Boolean isRelative)
	{		
		if(isRelative){
		spriteToTeleport.x += Xposition;
		spriteToTeleport.y += Yposition;
		}else{
		spriteToTeleport.x = Xposition;
		spriteToTeleport.y = Yposition;
		}
	}
	
	public void onCollision(){
		
		
		
	}
	
}
