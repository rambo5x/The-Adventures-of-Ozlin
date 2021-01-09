package com.dvreiter.starassault.Mobs;

import java.util.Random;
import org.flixel.*;
import org.flixel.event.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;

public class Bird extends FlxSprite {
    FlxSprite kills[];
    FlxSprite getsKilledBy[];
	FlxSprite player;
    int _jumpPower;
	FlxPath path;
	FlxPoint destination, destination2;
	
	private FlxTimer timer;

	int randomnum;
	int start = 1;
	int end = 4;
	public int random()
	{
		Random random = new Random();
			return showRandomInteger(start,end,random);
	}
	
	public static int showRandomInteger(int astart,int aend, Random arandom)
	{
	//get the range,casting to long to avojd overflow problrms
	long range = (long)aend - (long)astart + 1;
	//compute a fraction of the range,0 
		long fraction = (long)(range * arandom.nextDouble());
		int randomNumber =  (int)(fraction + astart);    
		return randomNumber;
	}
	
	
    @Override
    public Bird(int x, int y, int width, int height) {
        super(width, height);
        this.x = x;
        this.y = y;

        int runSpeed =50;
        _jumpPower = 50;
        maxVelocity.y = _jumpPower;
        drag.x = runSpeed*2;
		drag.y = _jumpPower*2;
		
        maxVelocity.x = runSpeed;
        acceleration.y = 500;
        loadGraphic("skeletonminion.png", true, true, 16, 16); 
		addAnimation("idle_stand", new int[]{2}, 0, false);
		addAnimation("idle_walk", new int[]{2,1,0}, 4, true);
		addAnimation("idle_rwalk", new int[]{5,4,3}, 4, true);

		offset.x = 1;
		timer.start(500,100);
		play("idle_rwalk");
		//	x-=10;

		health=1;

		
    }
    @Override
    public void update() {
		acceleration.x = 0;
	    super.update();

		acceleration.x += 1;
		randomnum = random();
		//if(timer.getTimeLeft() == 0)
		{
		if(randomnum ==1)
		{	
			if(this.x <= 0)
			{goRight();
			}else
			{
			goLeft();
			
		}}
		if(randomnum ==2)
		{
            goRight();
		}
		if(randomnum ==3)
		{
			if(this.y > FlxG.height)
			{
			fall();
				//FlxG.music.kill();
			}else
			{
			jump();
		}}
		if(randomnum ==4)
		{
			fall();
		}
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
		//   x -= 4;
		//	setFacing(LEFT);
		play("idle_walk");
    }
    public void goRight() {
		acceleration.x += drag.x;
		//   x += 4;
		//setFacing(RIGHT);
		play("idle_rwalk");
    }
    public void stop() {
        acceleration.x = 0;
		play("idle_stand");
    }
	public void fall(){
		velocity.y =  maxVelocity.y/1.2f;
		
	}
    public void jump() {
//        if(isTouching(FlxObject.FLOOR))
			velocity.y = - maxVelocity.y/1.2f;
    }
}
