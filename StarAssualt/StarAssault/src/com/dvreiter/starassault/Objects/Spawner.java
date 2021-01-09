package com.dvreiter.starassault.Objects;

import org.flixel.*;
import com.dvreiter.starassault.Objects.*;
import com.dvreiter.starassault.Player.*;
import javax.xml.datatype.*;
import org.flixel.system.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Mobs.*;

public class Spawner extends FlxSprite
{
	private FlxGroup entities;
	private Player _player;
	private JoyStickPlayer jplayer;
	private FlxEmitter _gibs;
	private FlxGroup _mbullets, _tbullets;
	private FlxTilemap slevel;
	private boolean _canSpawn,switchS;
	FlxSprite spriteToSpawn,spriteToFollow;
	private FlxSave jswitchSave;
	
	private FlxTimer cooldown;

	private float cooldownTime;
	private int entityId;

	public Spawner(int x, int y, int EntityId, FlxGroup Entity,float time)
	{
		super(x, y);
		loadGraphic("spawner.png", true, true, 16, 16);
		addAnimation("stand",new int[]{0});
		addAnimation("spawn",new int[]{1,2},2,true);
	//	addAnimation("idle_upleft", new int[]{1},0,false);
	//	addAnimation("idle_upright", new int[]{2},0,false);
		width = 16;
		height = 16;
		entityId = EntityId;
		immovable = true;
		//	acceleration.y = 500;
		entities = Entity;
		cooldownTime = time;
		_canSpawn = true;
		cooldown = new FlxTimer();
		health = 5;
		jswitchSave = new FlxSave();
		jswitchSave.bind("joystick");

		if(jswitchSave.data.get("jstick",boolean.class) == null)
		{
			jswitchSave.data.put("jstick", false);
			jswitchSave.flush();
		}

		@SuppressWarnings("unchecked")
			boolean scontrols = jswitchSave.data.get("jstick", boolean.class);
		switchS = scontrols;
	}

	@Override 
	public void destroy()
	{
		super.destroy();

	}
	
	public void getPlayer(Player player){
		_player = player;
	}
	
	public void getJPlayer(JoyStickPlayer jplayer1){
		jplayer = jplayer1;
	}
	
	public void getLevel(FlxTilemap level){
		slevel = level;
	}
	
	public void getSkeletonGibs(FlxEmitter Gibs){
		_gibs = Gibs;
	}
	
	public void getMageBullets(FlxGroup Bullets){
		_mbullets = Bullets;
	}
	
	public void getTurretBullets(FlxGroup TBullets){
		_tbullets = TBullets;
	}

	@Override 
	public void update()
	{	
		if(alive)
		{
			if(_canSpawn)
			{
				cooldown.start(cooldownTime,1,cooldownset);
				_canSpawn = false;
		//		play("stand");
				spawn();
			}
		}
	}

	@Override public void hurt(float Damage)
	{
		flicker(0.6f);//0.2f
		FlxG.score += 10;
		super.hurt(Damage);
	}

	public void spawn(){ 
		play("spawn");
		getMidpoint(_point);
		if(switchS){
			if(entityId == 380){
				((Bat) entities.recycle(Bat.class)).followJSprite(_point,jplayer);
			}else if(entityId == 381){
				((Crawler) entities.recycle(Crawler.class)).WatchJSprite(_point,jplayer,slevel);
			}else if(entityId == 382){
				((Crusher) entities.recycle(Crusher.class)).WatchJSprite(_point,jplayer);
			}else if(entityId == 383){
				((Enemy) entities.recycle(Enemy.class)).followJSprite(_point,jplayer);
			}else if(entityId == 384){
				((Mage) entities.recycle(Mage.class)).followJSprite(_point,slevel,_mbullets,jplayer);
			}else if(entityId == 385){
				((Silverfish) entities.recycle(Silverfish.class)).watchLevel(_point,slevel);
			}else if(entityId == 386){
				((Skeleton) entities.recycle(Skeleton.class)).watchLevel(_point,slevel,_gibs);
			}else if(entityId == 387){
				((Slime) entities.recycle(Slime.class)).followJSprite(_point,jplayer);
			}else if(entityId == 388){
				((Turret) entities.recycle(Turret.class)).WatchJSprite(_point,_tbullets,jplayer);
			}else if(entityId == 389){
				((AppleBatMinion) entities.recycle(AppleBatMinion.class)).followJSprite(_point,jplayer);
			}
		}else{
		if(entityId == 380){
		((Bat) entities.recycle(Bat.class)).followSprite(_point,_player);
		}else if(entityId == 381){
		((Crawler) entities.recycle(Crawler.class)).WatchSprite(_point,_player,slevel);
		}else if(entityId == 382){
		((Crusher) entities.recycle(Crusher.class)).WatchSprite(_point,_player);
		}else if(entityId == 383){
		((Enemy) entities.recycle(Enemy.class)).followSprite(_point,_player);
		}else if(entityId == 384){
		((Mage) entities.recycle(Mage.class)).followSprite(_point,slevel,_mbullets,_player);
		}else if(entityId == 385){
		((Silverfish) entities.recycle(Silverfish.class)).watchLevel(_point,slevel);
		}else if(entityId == 386){
		((Skeleton) entities.recycle(Skeleton.class)).watchLevel(_point,slevel,_gibs);
		}else if(entityId == 387){
		((Slime) entities.recycle(Slime.class)).followSprite(_point,_player);
		}else if(entityId == 388){
		((Turret) entities.recycle(Turret.class)).WatchSprite(_point,_tbullets,_player);
		}else if(entityId == 389){
		((AppleBatMinion) entities.recycle(AppleBatMinion.class)).followSprite(_point,_player);
		}
		}
	//	((Enemy) entities.recycle(Enemy.class)).followSprite(_point,_player);
	}

	public IFlxTimer cooldownset = new IFlxTimer(){
		@Override
		public void callback(FlxTimer flxTimer)
		{
			_canSpawn = true;

		}
	};
}
