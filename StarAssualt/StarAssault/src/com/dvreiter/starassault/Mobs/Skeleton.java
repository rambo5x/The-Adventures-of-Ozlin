package com.dvreiter.starassault.Mobs;

import org.flixel.*;
import org.flixel.event.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;

public class Skeleton extends FlxSprite {
    FlxSprite kills[];
    FlxSprite getsKilledBy[];
	FlxSprite obj;
	FlxObject getSpike;
	boolean isNearSprite,isTouchingSpike;
    int _jumpPower, sdistance;
	FlxPath path;
	FlxEmitter _gibs;
	FlxPoint destination, destination2;
	FlxTilemap levelToFollow;
	
	@Override
	public Skeleton(){
		int runSpeed = 50;
        _jumpPower = 200;
        maxVelocity.y = _jumpPower;
        drag.x = runSpeed*4;
        maxVelocity.x = runSpeed;
        acceleration.y = 500;
        loadGraphic("skeletonminion.png", true, true, 16, 16); 
		addAnimation("idle_stand", new int[]{2}, 0, false);
		addAnimation("idle_walk", new int[]{2,1,0}, 4, true);
		addAnimation("idle_rwalk", new int[]{5,4,3}, 4, true);
	//	_gibs = Gibs;
		//offset.x = 1;
		width = 10;

		play("idle_rwalk");
		//	x-=10;

		health=3;
		goRight();
		/*	destination = getMidpoint();
		 destination.x += 130;
		 Array<FlxPoint> points = new Array<FlxPoint>();
		 points.addAll(new FlxPoint[]{getMidpoint(),destination});
		 path = new FlxPath(points);
		 followPath(path,80,FlxObject.PATH_YOYO);
		 */
	}

    @Override
    public Skeleton(int x, int y, int width, int height, FlxEmitter Gibs) {
        super(width, height);
        this.x = x;
        this.y = y;
		isNearSprite = false;
        int runSpeed = 50;
        _jumpPower = 200;
        maxVelocity.y = _jumpPower;
        drag.x = runSpeed*4;
        maxVelocity.x = runSpeed;
        acceleration.y = 500;
        loadGraphic("skeletonminion.png", true, true, 16, 16); 
		addAnimation("idle_stand", new int[]{2}, 0, false);
		addAnimation("idle_walk", new int[]{2,1,0}, 4, true);
		addAnimation("idle_rwalk", new int[]{5,4,3}, 4, true);
		_gibs = Gibs;
		offset.x = 1;
		
		play("idle_rwalk");
	//	x-=10;
	
	health=3;
		goRight();

    }
    @Override
    public void update() {
       
	    super.update();
		int X = (Math.round(this.x))/16;
		int Y = (Math.round(this.y))/16;

	/*	if(levelToFollow.getTile(X + sdistance,Y) != 0 ){//x+1,y
			stop();
			goLeft();
			
		}
		if(levelToFollow.getTile(X - sdistance,Y) != 0 ){
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
	
	@Override public void hurt(float Damage)
	{
		flicker(0.6f);//0.2f
		FlxG.score += 10;
		super.hurt(Damage);
	}

    public void goLeft() {
      	acceleration.x -= drag.x;
		//setFacing(LEFT);
		play("idle_walk");
		
    }
    public void goRight() {
       acceleration.x += drag.x;
		//setFacing(RIGHT);
		play("idle_rwalk");
    }
    public void stop() {
        acceleration.x = 0;
	//	play("idle_stand");
    }
	
	public void setDistance(int distance){
		sdistance = distance;
	}
	
	public void getSpikeObject(FlxSprite sprite){
		getSpike = sprite;
		isTouchingSpike = false;
	}
	
	public void setSpriteToMoveFrom(FlxSprite sprite){
		obj = sprite;
		isNearSprite = true;
	}
	public void watchLevel(FlxTilemap Level)
	{
		levelToFollow = Level;
	}
	
	public void watchLevel(FlxPoint Location, FlxTilemap Level, FlxEmitter gibs)
	{
		levelToFollow = Level;
		_gibs = gibs;
		super.reset(Location.x-width/2,Location.y-height/2);
	}
	
    public void jump() {
        if(isTouching(FlxObject.FLOOR))
			velocity.y = - maxVelocity.y/1.2f;
    }
	
	@Override 
	public void kill()
	{
		if(!alive)
			return;
		setSolid(false);
		super.kill();
		flicker(0);
		exists = true;
		visible = false;
		velocity.make();
		acceleration.make();
		if(_gibs != null)
		{
			_gibs.at(this);
			_gibs.start(true,5,0,50);
		}
	}
	
}
