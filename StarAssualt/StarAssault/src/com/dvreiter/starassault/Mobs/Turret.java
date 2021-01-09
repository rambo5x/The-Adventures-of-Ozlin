package com.dvreiter.starassault.Mobs;

import org.flixel.*;
import com.dvreiter.starassault.Objects.*;
import com.dvreiter.starassault.Player.*;
import javax.xml.datatype.*;
import org.flixel.system.*;
import org.flixel.event.*;

public class Turret extends FlxSprite
{
    Player _player;
	JoyStickPlayer jplayer;
	private FlxGroup _tbullets;
	private int _aim, mode = 0;

	private boolean _canShoot,isWatchingSprite;
	FlxSprite spriteToFollow;

	private FlxTimer cooldown;

	private float cooldownTime;
	
	@Override
	public Turret(){
		loadGraphic("turret1.png", true, true, 16, 16);
		addAnimation("idle_upleft", new int[]{1},0,false);
		addAnimation("idle_upright", new int[]{2},0,false);
		width = 12;
		height = 12;
		immovable = true;
		//	acceleration.y = 500;
	//	_tbullets = TurretBullets;
		cooldownTime = 2f;
		_canShoot = true;
		cooldown = new FlxTimer();
		isWatchingSprite = false;	

		health = 3;
	}
	
	@Override
	public Turret(int x, int y,FlxGroup TurretBullets,float time,int Mode)
	{
		super(x, y);
		//	acceleration.y = 500;
		loadGraphic("turret1.png", true, true, 16, 16);
		addAnimation("idle_upleft", new int[]{1},0,false);
		addAnimation("idle_upright", new int[]{2},0,false);
		width = 12;
		height = 12;
		immovable = true;
	//	acceleration.y = 500;
		mode = Mode;
		_tbullets = TurretBullets;
		cooldownTime = time;
		_canShoot = true;
		cooldown = new FlxTimer();
		isWatchingSprite = false;	
		
		health = 2;
	}

	@Override 
	public void destroy()
	{
		super.destroy();
		_tbullets = null;

	}

	@Override 
	public void update()
	{	
	if(mode == 1){
		if(spriteToFollow.x < this.x)
		{
			setFacing(LEFT);
			if(_canShoot == true)
			{
				cooldown.start(cooldownTime,1,cooldownset);
				_canShoot = false;
				shoot();
			}
		}
	
		if(spriteToFollow.x > this.x)
		{
			setFacing(RIGHT);
			if(_canShoot == true)
			{
				cooldown.start(cooldownTime,1,cooldownset);
				_canShoot = false;
				shoot();
			}
		}
	  }else{
		  if(_player.x < this.x)
		  {
			  setFacing(LEFT);
			  if(_canShoot == true)
			  {
				  cooldown.start(cooldownTime,1,cooldownset);
				  _canShoot = false;
				  shoot();
			  }
		  }

		  if(_player.x > this.x)
		  {
			  setFacing(RIGHT);
			  if(_canShoot == true)
			  {
				  cooldown.start(cooldownTime,1,cooldownset);
				  _canShoot = false;
				  shoot();
			  }
		  }
	  }
	}

	@Override public void hurt(float Damage)
	{
		flicker(0.6f);//0.2f
		FlxG.score += 10;
		super.hurt(Damage);
	}
	
	public void shoot(){ 
		_aim = getFacing();
		getMidpoint(_point);
		((TurretBullet) _tbullets.recycle(TurretBullet.class)).shoot(_point,_aim);
	}

	public void WatchSprite(FlxSprite player) {
        spriteToFollow = player;
        isWatchingSprite = true;
    }
	
	public void WatchSprite(FlxPoint Location, FlxGroup TurretBullets, Player player) {
        _player = player;
		_tbullets = TurretBullets;
    	super.reset(Location.x-width/2,Location.y-height/2);
    }
	
	public void WatchJSprite(FlxPoint Location, FlxGroup TurretBullets, JoyStickPlayer jplayer1) {
        jplayer = jplayer1;
		_tbullets = TurretBullets;
    	super.reset(Location.x-width/2,Location.y-height/2);
    }
	

	public void shootNow(){
		_canShoot = true;
	}

	public IFlxTimer cooldownset = new IFlxTimer(){
		@Override
		public void callback(FlxTimer flxTimer)
		{
			_canShoot = true;

		}
	};
}
