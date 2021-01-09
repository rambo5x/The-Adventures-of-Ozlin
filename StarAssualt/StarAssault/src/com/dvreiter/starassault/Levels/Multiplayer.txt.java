/*
package com.dvreiter.starassault.Menu;	

import org.flixel.FlxButton;
import org.flixel.event.IFlxButton;
import org.flixel.FlxG;
import org.flixel.FlxObject;
import org.flixel.FlxSprite; 
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.FlxTileblock;
import org.flixel.FlxTilemap;
import org.flixel.event.IFlxCollision;
import org.flixel.*;
import com.badlogic.gdx.utils.IntArray;
import org.flixel.ui.*;
import org.flixel.event.*;
import org.flixel.system.*;
import com.badlogic.gdx.utils.*;
import org.flixel.ui.event.*;
import com.dvreiter.starassault.Mobs.*;
import com.dvreiter.starassault.Player.*;
import com.dvreiter.starassault.Menu.*;
import com.dvreiter.starassault.Objects.*;

public class KTMultiplayerState extends FlxState
{	

	private static final int TILE_WIDTH = 16;
	private static final int TILE_HEIGHT = 16;

	public FlxButton pause;
	public FlxTilemap level;
	public FlxTileblock pauseblock;
	public FlxSprite portal;	
	public FlxSprite hearts;
	public FlxGroup coins;
	public FlxGroup walls;
	public FlxSprite portalcoin;
	public FlxGroup spikes;	   
	public FlxText status;
	public FlxText goal;	
	public FlxSprite coin;
	public FlxSprite wallSwitch;
	public FlxText _fps;
	public FlxSprite elevator, pusher;

	Player _player;
	Player2 _player2;
	Player3 _player3;
	
	public FlxGroup enemies;
	Skeleton skeleton;
	FlxVirtualPad pad;
	protected FlxGroup _bullets;
	protected FlxEmitter _littleGibs;

	private FlxButton Pausebtn;
	private FlxButton Exitbtn;
	private FlxButton Settingsbtn;
	private FlxButton Resumebtn;

	private FlxGroup crushers;

	private FlxSave gameSave;

	boolean isPaused = false;//added
	@Override
	public void create()
	{						
		FlxG.setFramerate(60);
		FlxG.setFlashFramerate(60);

		FlxG.setBgColor(0x4c4c4c);//6c767c
		FlxG.mouse.show();
		
		int[] data={2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,			
			2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,	
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,//Middle or half of y of the level map.
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,			
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,                                                  
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2, 
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
			2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
			2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
		};
		
		level = new FlxTilemap();
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 50), "tilemap.png", TILE_WIDTH, TILE_HEIGHT); 
		add(level);

		_littleGibs = new FlxEmitter();
		_littleGibs.setXSpeed(-150,150);
		_littleGibs.setYSpeed(-200,0);
		_littleGibs.setRotation(-720,-720);
		_littleGibs.gravity = 350;
		_littleGibs.bounce = 0.5f;
		_littleGibs.makeParticles("gibs.png",100,10,true,0.5f);

	/*	portal = new FlxSprite(40, 50);//50
		portal.loadGraphic("portal.png", true, true, 16, 16);
		portal.addAnimation("spinning", new int[]{0, 1, 2}, 6, true);
		portal.play("spinning");
		portal.immovable = true;
		portal.exists = false;
		add(portal);

		wallSwitch = new FlxSprite(700,400);//750,64
		wallSwitch.loadGraphic("switch.png", true, true, 16, 16);
		wallSwitch.immovable = true;
		add(wallSwitch);

		portalcoin = new FlxSprite(150, 140);//first (170, 80) Second (170, 160)
		portalcoin.loadGraphic("Portalcoin.png", true, true, 16, 16);
		//	portalcoin.addAnimation("stand", new int[]{0}, 0, false);
		portalcoin.addAnimation("rotate", new int[]{0, 1, 2}, 4, true);
		portalcoin.play("rotate");
		add(portalcoin);*/
/*
		pad = new FlxVirtualPad(FlxVirtualPad.LEFT_RIGHT, FlxVirtualPad.A_B_X_Y);
		pad.setAlpha(0.5f);

		_bullets = new FlxGroup();

	   _player = new Player(50,400,16,16,_bullets,_littleGibs,pad);
	   _player.setHasGravityToggle(true);
	   _player.setHasFlyingToggle(false);
	
	//	_player2 = new Player2(82,400,16,16,_bullets,_littleGibs);
	//	_player2.setHasGravityToggle(true);
	//	_player2.setHasFlyingToggle(false);
		
	//	_player3 = new Player3(114,400,16,16,_bullets,_littleGibs);
	//	_player3.setHasGravityToggle(true);
	//	_player3.setHasFlyingToggle(false);

		enemies = new FlxGroup();
		//createEnemy(200,280,500);
		//createEnemy(768,32,500);
		//add(enemies);

		crushers = new FlxGroup();
		//createCrusher(50,364);//48;
		//add(crushers);

		//skeleton = new Skeleton(624,400,16,16);//x=648

		FlxG.camera.follow(_player, FlxCamera.STYLE_PLATFORMER);
	//	FlxG.camera.follow(_player2, FlxCamera.STYLE_PLATFORMER);
	//	FlxG.camera.follow(_player3, FlxCamera.STYLE_PLATFORMER);
	    FlxG.camera.setBounds(0,0,800,480,true);// 1st 400,240  2nd 800,240, 3rd 1200,480

		walls = new FlxGroup();
		//createWall(240,224);
		//createWall(256,224);
		//add(walls);

		coins = new FlxGroup(); 			
		//bottom coins	
		//createCoin(105, 300);//first (105, 115) second (210, 230)
		//createCoin(90, 390);
		//add(coins);

		spikes = new FlxGroup();
		//createSpike(160,400,0);//first (80, 129) second (160, 250)
		//add(spikes);

		status = new FlxText(2,2,100, "SCORE: ");
		status.setShadow(0xff000000);
		status.setText("SCORE: "+(coins.countDead()*100));
		status.scrollFactor.x = status.scrollFactor.y = 0;
		add(status);
		//380,220 - original size.
		pauseblock = new FlxTileblock(10, 10, 340, 180);//780, 400
		pauseblock.makeGraphic(340, 180, 0xff000000);// 390, 230
		pauseblock.setAlpha(0.5f);
		pauseblock.setSolid(false);
		pauseblock.immovable = true;
		pauseblock.scrollFactor.x = pauseblock.scrollFactor.y = 0;
		pauseblock.visible = false;;
		add(pauseblock);

		Resumebtn = new FlxButton(160, 100, "Resume");//x.190, x.180, x.170, y.110
		Resumebtn.setSolid(false);//Coords 1: (400, 240),
		Resumebtn.immovable = true;
		Resumebtn.scrollFactor.x = Resumebtn.scrollFactor.y = 0;
		Resumebtn.visible = false;
		add(Resumebtn);

		Settingsbtn = new FlxButton(160, 130, "Settings", new IFlxButton(){@Override public void callback(){onSettings();}});//x.190, x.180, x.170, y.130
		Settingsbtn.setSolid(false);//Coords 1: (400, 260), 
		Settingsbtn.immovable = true;
		Settingsbtn.scrollFactor.x = Settingsbtn.scrollFactor.y = 0;
		Settingsbtn.visible = false;
		add(Settingsbtn);

		Exitbtn = new FlxButton(160, 160, "Quit Game", new IFlxButton(){@Override public void callback(){onExit();}});//x.190, x.180, x.170, y.150
		Exitbtn.setSolid(false);//Coords 1: (400, 280)
		Exitbtn.immovable = true;
		Exitbtn.scrollFactor.x = Exitbtn.scrollFactor.y = 0;
		Exitbtn.visible = false;
		add(Exitbtn);

		Pausebtn =  new FlxButton(300, 6, "||");
		Pausebtn.setSolid(false);
		Pausebtn.immovable = true;
		Pausebtn.visible = true;
		Pausebtn.scrollFactor.x = Pausebtn.scrollFactor.y = 0;
		add(Pausebtn);	

		//add(skeleton);
		add(_bullets);
		add(_player);				
		//add(_player2);
	//	add(_player3);
		add(_littleGibs);
		add(pad);
	}
    public void createCoin(int X,int Y)
	{
		FlxSprite coin = new FlxSprite(X,Y);
		coin.loadGraphic("coin.png", true, true, 16, 16);	
		coin.addAnimation("rotating", new int[]{0, 1, 2}, 4, true);
		coin.offset.x = 3;
		coin.offset.y = 3;
		coin.centerOffsets();
		coin.play("rotating");
		coins.add(coin);
	}	

	public void createWall(int X, int Y){
		FlxSprite wall = new FlxSprite(X,Y);
		wall.loadGraphic("Wall.png", true, true, 16, 16);
		wall.centerOffsets();
		wall.immovable = true;
		walls.add(wall);

	}

	public void createSpike(int X, int Y,int Angle){
		Spike spike = new Spike(X,Y,Angle);
		spikes.add(spike);
	}

	public void createEnemy(int X, int Y,int Accel){
		Enemy enemy = new Enemy(X,Y,16,16,Accel);
		enemy.followSprite(_player);
		enemies.add(enemy);
	}

	public void createCrusher(int X,int Y){
		Crusher crusher = new Crusher(X,Y);
		crusher.WatchSprite(_player);
		crushers.add(crusher);
	}

    @Override
	public void update(){	
//		if(FlxG.keys.justPressed("BACK"))
//		{
//			if(_player.exists == true)
//				{
//				Pausebtn.kill();
//				_player.exists = false;
//				pauseblock.exists = true;
//				Resumebtn.exists = true;
//				Settingsbtn.exists = true;
//				Exitbtn.exists = true;			
//				}
//			if(_player.exists == false)
//				{
//					Pausebtn.exists = true;
//					_player.exists = true;
//					Resumebtn.kill();
//					Settingsbtn.kill();
//					Exitbtn.kill();
//					pauseblock.kill();
//				}		
		//	}
		if(Pausebtn.status == Pausebtn.PRESSED){
			//FlxG.paused = true;
			enemies.active = false;
			Pausebtn.visible = false;
		//	_player.exists = false;
		//	_player2.exists = false;
		//	_player3.exists = false;
			pauseblock.visible = true;
			Resumebtn.visible = true;
			Settingsbtn.visible = true;
			Exitbtn.visible = true;
		}
		if(Resumebtn.status == Resumebtn.PRESSED){
			Pausebtn.visible = true;
		//	_player.exists = true;
		//	_player2.exists = true;
		//	_player3.exists = true;
			Resumebtn.visible = false;
			Settingsbtn.visible = false;
			Exitbtn.visible = false;
			pauseblock.visible = false;
			enemies.active = true;
		}

		super.update();					  
		
		/*PLAYERS COLLISIONS
		FlxG.collide(level,_player2);
		FlxG.collide(level,_player3);
		FlxG.collide(_player,_player2);
		FlxG.collide(_player2,_player3);
		FlxG.collide(_player3,_player);
		
		FlxG.collide(_player, _bullets, doPlayerDamage);
		FlxG.collide(_player2, _bullets, doPlayer2Damage);
		FlxG.collide(_player3, _bullets, doPlayer3Damage);
		
		/*ENEMY COLLISIONS 
		FlxG.collide(enemies,spikes);
		FlxG.collide(enemies,level);
		FlxG.overlap(enemies,_player,doPlayerDamage);
		FlxG.collide(enemies, _bullets,doEnemyDamage);
		FlxG.collide(walls, enemies);
		
		FlxG.collide(crushers,level);
		FlxG.collide(crushers,_player,doPlayerDamage);

		FlxG.overlap(coins,_player,getCoin);	
		FlxG.overlap(portalcoin, _player, getPCoin);
		FlxG.collide(walls,_player);
		FlxG.collide(wallSwitch, _player, switchOn);
		FlxG.overlap(spikes,_player,doSpikeDamage);
		FlxG.overlap(portal,_player,win);
		FlxG.collide(skeleton,spikes);
		FlxG.collide(skeleton,level);
		FlxG.overlap(skeleton,_player,doBoneDamage);
		FlxG.collide(level,_player);
		FlxG.collide(elevator, level);
		FlxG.collide(pusher, level);
		FlxG.collide(elevator, _player);
		FlxG.collide(pusher, _player);
		FlxG.collide(_bullets, skeleton, doSkeletonDamage);
		FlxG.collide(level, _bullets);
		FlxG.collide(_bullets, spikes);
		FlxG.collide(spikes, _bullets);
		FlxG.collide(enemies,enemies);

	}

	@Override
	public void destroy()
	{
		super.destroy();
		Pausebtn = null;
		Settingsbtn = null;
		Exitbtn = null;
		Resumebtn = null;
		pauseblock = null;
		elevator = null;
		skeleton = null;
		pusher = null;
		level = null;
		portal = null;
		hearts = null;
		coins = null;
		portalcoin = null;
		portal = null;
		spikes = null;
		_bullets = null;
		status = null;
		goal = null;
		_player = null;
		_player2 = null;
		_player3 = null;
		hearts = null;
		enemies = null;
		pad = null;
	}	

	private void onExit(){
		FlxG.switchState(new MenuState());
	}

	private void onSettings(){
		FlxG.switchState(new Settings());
	}

	/*	private void onResume(){
	 FlxG.paused = false;
	 Pausebtn.exists = true;
	 _player.exists = true;
	 Resumebtn.exists = false;
	 Settingsbtn.exists = false;
	 Exitbtn.exists = false;
	 pauseblock.exists = false;
	 }
	 
	IFlxCollision getCoin = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Coin, FlxObject Player)
		{						
	        Coin.kill();
			status.setText("SCORE: "+(coins.countDead()*100));	  		
			_player.x = 220;
	    }
	};					

	IFlxCollision switchOn = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject wallSwitch, FlxObject Player)
		{	
			wallSwitch.kill();
	        walls.kill();
	    }
	};					

	IFlxCollision getPCoin = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Portalcoin, FlxObject Player)
		{						
	        Portalcoin.kill();	
			portal.exists = true;			        
	    }
	};					


	IFlxCollision doSpikeDamage = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Spike, FlxObject Player)
		{			
			//add(new FlxText(200,22 , 100, "You Died!"));			
			_player.kill();
			//FlxG.music.kill();
	    }
	};		

	IFlxCollision doBoneDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Skeleton, FlxObject Player)
		{									
			//add(new FlxText(400,240 , 400, "You Died!"));			
			_player.kill();
			//	FlxG.shake(0.05f,2);
		}
	};
	
	IFlxCollision doPlayer1Damage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Bullet)
		{									
			/*add(new FlxText(400,240 , 400, "You Died!"));		
			_player.kill();
			//	FlxG.shake(0.05f,2);
		}
	};	
	
	IFlxCollision doPlayer2Damage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player2, FlxObject Bullet)
		{									
			/*add(new FlxText(400,240 , 400, "You Died!"));			
			_player2.kill();
			//	FlxG.shake(0.05f,2);
		}
	};	
	
	IFlxCollision doPlayer3Damage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player3, FlxObject Bullet)
		{									
			/*add(new FlxText(400,240 , 400, "You Died!"));		
			_player3.kill();
			//	FlxG.shake(0.05f,2);
		}
	};	

    IFlxCollision doPlayerDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Enemy, FlxObject Player)
		{									
			/*add(new FlxText(400,240 , 400, "You Died!"));	
			_player.kill();
			//	FlxG.shake(0.05f,2);
		}
	};	

	IFlxCollision doEnemyDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Enemy, FlxObject Bullet)
		{									
			//		add(new FlxText(_player.x,_player.y , 100, "Test: Enemy killed."));	
			//		EnemyG.kill();
			Enemy.hurt(1);
		}
	};

	IFlxCollision doSkeletonDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Bullet, FlxObject Skeleton)
		{									
			//add(new FlxText(300,100 , 100, "Test: Enemy killed."));	
			skeleton.hurt(1);
		}
	};	

	IFlxCollision win = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Portal, FlxObject Player)
		{
			add(new FlxText(200,200, 100, "You win Level 4!"));			
			_player.exists = false;
			FlxG.fade(0xff000000,1, onFade);
			//FlxG.switchState(new PlayState4());
		}
	};

	public IFlxCamera onFade = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			//saving stuff
			gameSave = new FlxSave();
			gameSave.bind("Test");

			//Save

			gameSave.data.put("Progress", 6);
			gameSave.flush();

			//FlxG.switchState(new PlayState5());
		}

	}; 

	/*private void wrap(FlxObject obj)
	 {
	 obj.x = (obj.x + obj.width / 2 + 800) % 800 - obj.width / 2;
	 obj.y = (obj.y + obj.height / 2) % 480 - obj.height / 2;
	 }*//*
}
*/
