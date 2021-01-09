package com.dvreiter.starassault.Mobs;

import org.flixel.*;
import org.flixel.event.*;
import org.flixel.ui.*;
import com.dvreiter.starassault.*;
import com.dvreiter.starassault.Objects.*;
import com.dvreiter.starassault.Player.*;

public class Mage extends FlxSprite {   

	protected FlxGroup _balls;
	protected int _aim;
	protected float _restart;

	Player _player;
	JoyStickPlayer jplayer;
	
    private int _jumpPower;
	
	FlxTilemap levelToFollow;
	
	private FlxSprite spriteToFollow;
	private boolean isFollowingSprite;
	private boolean canShoot = true;
	private FlxTimer Shootcooldown;
	private int mode, smode = 0;
	private boolean hasStop = false;
	private boolean canShoot2 = true;
	
	@Override
	public Mage(){
		int runSpeed = 50;
        _jumpPower = 150;
        maxVelocity.y = _jumpPower;
        drag.x = runSpeed*4;
        maxVelocity.x = runSpeed;
        acceleration.y = 500;
		Shootcooldown = new FlxTimer();
		mode = 1;
        loadGraphic("mage.png", true, true, 16, 16);    
		width = 14;
		height = 15;
//		offset.x = 1;
		//	offset.y = 1;
		goLeft();
		addAnimation("stand", new int[]{0},0, false);
		addAnimation("walk", new int[]{9,10},2,true);
		addAnimation("shoot", new int[]{0,1,2,3,4,5,6,7,8},7,false);
		addAnimation("jump", new int[]{6,7}, 1, true);
		play("stand");

		health = 3;

	//	_bullets = Bullets;

		
	}
   
  	@Override
	public Mage(int x, int y, int width, int height, FlxGroup Balls,int Mode, int Smode) {
        super(width, height);
		this.x = x;
        this.y = y;
        int runSpeed = 20;
        _jumpPower = 150;
        maxVelocity.y = _jumpPower;
        drag.x = runSpeed*4;
        maxVelocity.x = runSpeed;
        acceleration.y = 500;
		mode = Mode;
		Shootcooldown = new FlxTimer();
		smode = Smode;
        loadGraphic("mage.png", true, true, 16, 16);    
		width = 14;
		height = 15;
//		offset.x = 1;
	//	offset.y = 1;
		goLeft();
		addAnimation("stand", new int[]{0},0, false);
		addAnimation("walk", new int[]{9,10},2,true);
		addAnimation("shoot", new int[]{0,1,2,3,4,5,6,7,8},7,false);
		addAnimation("jump", new int[]{6,7}, 1, true);
		play("stand");
	
		health = 3;
		
		_balls = Balls;
		
    }

	@Override
	public void destroy()
	{
		// TODO: Implement this method
		super.destroy();
		_balls = null;
	}
	
    @Override
    public void update() {      
		
		super.update();
		if(smode == 1){
		if(mode == 1){
		int X = (Math.round(this.x))/16;
		int Y = (Math.round(this.y))/16;

		int proximety = Math.round(getProximety(this.x,this.y,spriteToFollow.x,spriteToFollow.y));
			
		if(this.getFrame() == 4 && canShoot2 == true)
		{
			
			Shoot();
		}
		
		if(proximety < 80)
		{
			if(hasStop == false)
			{
			hasStop = true;
			stop();
			}
			
				if(spriteToFollow.x > this.x)
				{
					setFacing(RIGHT);
					if(canShoot == true)
					{
					play("shoot");
					Shootcooldown.start(2,1,Cooldown);
					canShoot = false;
					//Shoot();
					}
		
				}
				if(spriteToFollow.x < this.x)
				{
					setFacing(LEFT);
					if(canShoot == true)
					{
					play("shoot");
						Shootcooldown.start(2,1,Cooldown);
						canShoot = false;
						//Shoot();
					}
				}
				
		}else if(hasStop == true && acceleration.x == 0){
			goLeft();
		}else{
			hasStop = false;
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

		if(mode == 2 && spriteToFollow != null) {
            if(spriteToFollow.x != x) {
                
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
        }
		_aim = getFacing();
		}else if(smode == 2){
			mode = 1;
			if(mode == 1){
				int X = (Math.round(this.x))/16;
				int Y = (Math.round(this.y))/16;

				int proximety = Math.round(getProximety(this.x,this.y,_player.x,_player.y));

				if(this.getFrame() == 4 && canShoot2 == true)
				{

					Shoot();
				}

				if(proximety < 80)
				{
					if(hasStop == false)
					{
						hasStop = true;
						stop();
					}

					if(_player.x > this.x)
					{
						setFacing(RIGHT);
						if(canShoot == true)
						{
							play("shoot");
							Shootcooldown.start(2,1,Cooldown);
							canShoot = false;
							//Shoot();
						}

					}
					if(_player.x < this.x)
					{
						setFacing(LEFT);
						if(canShoot == true)
						{
							play("shoot");
							Shootcooldown.start(2,1,Cooldown);
							canShoot = false;
							//Shoot();
						}
					}

				}else if(hasStop == true && acceleration.x == 0){
					goLeft();
				}else{
					hasStop = false;
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

			if(mode == 2) {
				if(_player.x != x) {

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
			_aim = getFacing();
		}else if (mode == 3){
			mode = 1;
			if(mode == 1){
				int X = (Math.round(this.x))/16;
				int Y = (Math.round(this.y))/16;

				int proximety = Math.round(getProximety(this.x,this.y,jplayer.x,jplayer.y));

				if(this.getFrame() == 4 && canShoot2 == true)
				{

					Shoot();
				}

				if(proximety < 80)
				{
					if(hasStop == false)
					{
						hasStop = true;
						stop();
					}

					if(jplayer.x > this.x)
					{
						setFacing(RIGHT);
						if(canShoot == true)
						{
							play("shoot");
							Shootcooldown.start(2,1,Cooldown);
							canShoot = false;
							//Shoot();
						}

					}
					if(jplayer.x < this.x)
					{
						setFacing(LEFT);
						if(canShoot == true)
						{
							play("shoot");
							Shootcooldown.start(2,1,Cooldown);
							canShoot = false;
							//Shoot();
						}
					}

				}else if(hasStop == true && acceleration.x == 0){
					goLeft();
				}else{
					hasStop = false;
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

			if(mode == 2) {
				if(jplayer.x != x) {

					if(jplayer.x > x ) {
						//if(isTouching(FlxObject.FLOOR))
						goRight();
					} else if(jplayer.x < x) {
						//if(isTouching(FlxObject.FLOOR))
						goLeft();
					}
				} else {
					stop();
				}
			}
			_aim = getFacing();
		}
	}
	
	private void Shoot()
	{
		canShoot2 = false;
		if(getFlickering())
			flicker(1);
		else
		{
			getMidpoint(_point);
			//((MageBall) _bullets.recycle(MageBall.class)).shoot(_point,_aim);
			if(smode == 1){
			((MageBall) _balls.recycle(MageBall.class)).shoot(_point,this.x,this.y,spriteToFollow.x,spriteToFollow.y);
			}else if(smode == 2){	
			((MageBall) _balls.recycle(MageBall.class)).shoot(_point,this.x,this.y,_player.x,_player.y);
			}else if(smode == 3){
			((MageBall) _balls.recycle(MageBall.class)).shoot(_point,this.x,this.y,jplayer.x,jplayer.y);
			}
		}
	}
	IFlxTimer Cooldown = new IFlxTimer(){

		

		@Override
		public void callback(FlxTimer p1)
		{
			canShoot2 = true;
			canShoot = true;
		}	
	};
	private float getProximety(float x1,float y1,float x2,float y2)
	{
		float X= Math.abs(x1)- Math.abs(x2);
		float Y= Math.abs(y1)- Math.abs(y2);
		return Math.abs((X+Y)/2);
	}
	
/*	public void watchLevel(FlxTilemap Level)
	{
		levelToFollow = Level;
	}*/
	
	public void goLeft() {
      	acceleration.x -= drag.x;

		setFacing(LEFT);
		play("walk");

    }
    public void goRight() {
		acceleration.x += drag.x;
		//  x += 4;
		setFacing(RIGHT);
		play("walk");
    }
    public void stop() {
        acceleration.x = 0;
	//	play("stand");
    }
	
	
	
	
	
	@Override
	public void hurt(float Damage)
	{
		
		
			
		flicker(1.3f);
		
		super.hurt(Damage);
	}
	
	public void followSprite(FlxTilemap level,FlxSprite player) {
		levelToFollow = level;
        spriteToFollow = player;
        isFollowingSprite = true;	
    }
	
	public void followSprite(FlxPoint Location, FlxTilemap level, FlxGroup Balls, Player player){
		levelToFollow = level;
		_player = player;
		smode = 2;
		_balls = Balls;
		super.reset(Location.x-width/2,Location.y-height/2);
	}
	
	public void followJSprite(FlxPoint Location, FlxTilemap level, FlxGroup Balls, JoyStickPlayer jplayer1){
		levelToFollow = level;
		smode = 3;
		jplayer = jplayer1;
		_balls = Balls;
		super.reset(Location.x-width/2,Location.y-height/2);
	}
	
	/*@Override 
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
	}*/


}	

