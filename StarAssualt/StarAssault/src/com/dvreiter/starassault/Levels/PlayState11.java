package com.dvreiter.starassault.Levels;	

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

public class PlayState11 extends FlxState
{	

	private static final int TILE_WIDTH = 16;
	private static final int TILE_HEIGHT = 16;

	/*private FlxObject highlightbox;
	 private int action;
	 private static final int ACTION_IDLE = 0;*/

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
	public FlxText goal,popText;
	public FlxSprite coin;
	public FlxSprite wallSwitch;
	public FlxText _fps, pausedTxt;
	public FlxSprite elevator, pusher;
	private FlxNinePatchButton closebtn;
	private int spikeX,spikeX2;

	public FlxGroup crushers,turrets,mages,skeletons,slimes,_balls,_tbullets;
	public FlxGroup enemies;
	public FlxGroup sfishs;
	Skeleton skeleton;
	//Turret turret;
	private JoyStickPlayer jplayer;
	Player _player;
	FlxVirtualPad pad;
	public  FlxGroup _bullets, bats;
	protected FlxEmitter _littleGibs;
	protected FlxEmitter _skelGibs;
	//public FlxGroup _tbullets

	//	private FlxButton Pausebtn;
	private FlxSprite Pausebtn;
	private FlxButton Exitbtn;
	private FlxButton Settingsbtn;
	private FlxButton Resumebtn,restartbtn;
	private FlxTimer timer;

	private FlxSave gameSave,guiMusic,jswitchSave;
	private FlxAnalog analog;
	private boolean switchS;

	@Override
	public void create()
	{						
		FlxG.setFramerate(60);
		FlxG.setFlashFramerate(60);

		FlxG.setBgColor(0x4c4c4c);//6c767c
		FlxG.mouse.show();

		//	action = ACTION_IDLE;

		int[] data={2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,			
			2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,	
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,2,2,2,2,2,
			2,2,0,0,0,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,2,2,
			2,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,2,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,
			2,2,0,0,0,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,
			2,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,2,2,
			2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,2,2,//Middle or half of y of the level map.
			2,2,0,0,0,2,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,0,0,2,2,			
			2,2,0,0,0,2,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,2,                                                  
			2,2,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,2, 
			2,2,0,0,0,2,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,0,0,2,2,
			2,2,0,0,0,2,0,0,2,0,0,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,2,0,0,0,2,
			2,2,0,0,0,2,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,0,0,2,2,
			2,2,0,0,0,2,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,2,
			2,2,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,2,
			2,2,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,2,
			2,2,0,0,0,2,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,2,2,
			2,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,2,
			2,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,2,
			2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
			2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
			2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
		};

		level = new FlxTilemap();
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 50), "tilemap.png", TILE_WIDTH, TILE_HEIGHT);
		add(level);
		
		FlxG.playMusic("prologue.mp3", 1f, true, true);

		guiMusic = new FlxSave();
		guiMusic.bind("Options");//Test

		if(guiMusic.data.get("Music",boolean.class) == null)
		{
			guiMusic.data.put("Music", false);
			guiMusic.flush();
		}

		//load
		@SuppressWarnings("unchecked")
			boolean music = guiMusic.data.get("Music", boolean.class);

		if(music == false){
			FlxG.mute = false;
		}else if (music == true){
			FlxG.mute = true;
		}

		_littleGibs = new FlxEmitter();
		_littleGibs.setXSpeed(-150,150);
		_littleGibs.setYSpeed(-200,0);
		_littleGibs.setRotation(-720,-720);
		_littleGibs.gravity = 350;
		_littleGibs.bounce = 0.5f;
		_littleGibs.makeParticles("playergibs.png",100,10,true,0.5f);

		_skelGibs = new FlxEmitter();
		_skelGibs.setXSpeed(-150,150);
		_skelGibs.setYSpeed(-200,0);
		_skelGibs.setRotation(-720,-720);
		_skelGibs.gravity = 700;
		_skelGibs.bounce = 0.5f;
		_skelGibs.makeParticles("skelgibs.png",100,10,true,0.5f);

		portal = new FlxSprite(416,300);//40,50
		portal.loadGraphic("portal.png", true, true, 16, 16);
		portal.addAnimation("spinning", new int[]{0, 1, 2}, 6, true);
		portal.play("spinning");
		portal.immovable = true;
		portal.exists = false;
		add(portal);

		portalcoin = new FlxSprite(416, 360);//first (170, 80) Second (170, 160)
		portalcoin.loadGraphic("Portalcoin.png", true, true, 16, 16);
		//	portalcoin.addAnimation("stand", new int[]{0}, 0, false);
		portalcoin.addAnimation("rotate", new int[]{0, 1, 2}, 4, true);
		portalcoin.play("rotate");
		add(portalcoin);

		wallSwitch = new FlxSprite(736,80);//750,64
		wallSwitch.loadGraphic("switch.png", true, true, 16, 16);
		wallSwitch.addAnimation("off",new int[]{0});
		wallSwitch.addAnimation("on",new int[]{1});
		wallSwitch.play("off");
		wallSwitch.immovable = true;
		add(wallSwitch);

		/*		analog = new FlxAnalog(50, 200);
		 analog.setAlpha(.75f);
		 analog.setAll("scrollFactor", new FlxPoint(0, 0));
		 add(analog);*/
		 
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

		if(scontrols){
			analog = new FlxAnalog(50, 200);//x=50,y=190 (90,200)
			analog.setAlpha(.75f);
			analog.setAll("scrollFactor", new FlxPoint(0, 0));
			add(analog);

			pad = new FlxVirtualPad(0, FlxVirtualPad.A_B_X_Y);
			pad.setAlpha(0.5f);
			//pad.buttonX.setAlpha(0.15f);
			pad.buttonA.x = pad.buttonA.x - 10;
			pad.buttonB.x = pad.buttonB.x - 10;
			pad.buttonX.x = pad.buttonX.x - 10;
			pad.buttonY.x = pad.buttonY.x - 10;
			add(pad);

			_bullets = new FlxGroup();

			jplayer = new JoyStickPlayer(48,400,16,16,_bullets,_littleGibs, pad,analog);
			jplayer.setHasGravityToggle(true);
			FlxG.camera.follow(jplayer, FlxCamera.STYLE_PLATFORMER);
			FlxG.camera.setBounds(0,0,800,480,true);// 1st 400,240  2nd 800,240, 3rd 1200,480//status = new FlxText(2,2,100, "SCORE: ";
			add(_bullets);
			add(jplayer);
			add(_littleGibs);

		}else{

			pad = new FlxVirtualPad(FlxVirtualPad.LEFT_RIGHT, FlxVirtualPad.A_B_X_Y);
			pad.setAlpha(0.5f);
			//	pad.buttonX.setAlpha(0.15f);
			pad.buttonA.x = pad.buttonA.x - 10;
			pad.buttonB.x = pad.buttonB.x - 10;
			pad.buttonX.x = pad.buttonX.x - 10;
			pad.buttonY.x = pad.buttonY.x - 10;
			add(pad);

			_bullets = new FlxGroup();

			_player = new Player(48,400,16,16,_bullets,_littleGibs, pad);
			_player.setHasGravityToggle(true);
			FlxG.camera.follow(_player, FlxCamera.STYLE_PLATFORMER);
			FlxG.camera.setBounds(0,0,800,480,true);// 1st 400,240  2nd 800,240, 3rd 1200,480//status = new FlxText(2,2,100, "SCORE: ";

			add(_bullets) ;
			add(_player) ;
			add(_littleGibs); 

		} 

		_tbullets = new FlxGroup();

		//turret = new Turret(740,64,16,16,_tbullets);
		turrets = new FlxGroup();
		createTurret(144,128);
		createTurret(704,192);
		add(turrets);
		
		enemies = new FlxGroup();

		createEnemy(700,96,-500);
		createEnemy(700,80,500);
		createEnemy(400,400,500);

		//	createEnemy(200,132,-500);
		//	createEnemy(200,150,500);
		//	createEnemy(216,150,500);
		//		createEnemy(400,432,500);
		//		createEnemy(420,,500);


		add(enemies);

		/*	bats = new FlxGroup();
		 //	createBat(60,100);
		 createBat(200,160);
		 add(bats);*/

		crushers = new FlxGroup();
		createCrusher(384,288,32);
		createCrusher(448,288,32);
		add(crushers);

		skeletons = new FlxGroup();
		//		createSkeleton(670,432);
		createSkeleton(600,128);
		createSkeleton(600,192);

		createSkeleton(192,256);
		createSkeleton(608,256);
		add(skeletons);

		walls = new FlxGroup();
		createWall(704,96);
		createWall(688,96);

		/*createWall(80,432);
		 createWall(80,416);

		 createWall(0,96);
		 createWall(16,96);*/
		add(walls);

		spikes = new FlxGroup();
		createSpike(96,112,180,400);
		createSpike(112,112,180,400);

		createSpike(736,176,180,400);
		createSpike(752,176,180,400);

		/*Top Spikes*/
		createSpike(80,32,180,0);
		//	createSpike(112,32,180);
		createSpike(144,32,180,0);
		//	createSpike(176,32,180);
		createSpike(208,32,180,0);
		//	createSpike(240,32,180);
		createSpike(272,32,180,0);
		//	createSpike(304,32,180);
		createSpike(336,32,180,0);
		//	createSpike(368,32,180);
		createSpike(400,32,180,0);
		createSpike(464,32,180,0);
		createSpike(528,32,180,0);
		createSpike(592,32,180,0);
		createSpike(656,32,180,0);
		//		createSpike(721,32,180,0);

		/*Bottom Spikes*/
		createSpike(113,80,0,0);
		createSpike(177,80,0,0);
		createSpike(241,80,0,0);
		createSpike(305,80,0,0);
		createSpike(369,80,0,0);
		createSpike(433,80,0,0);
		createSpike(497,80,0,0);
		createSpike(561,80,0,0);
		createSpike(625,80,0,0);
		//	createSpike(721,80,0,0);

		/*Portal Spikes*/
		//	createSpike(300,380,0);
		add(spikes);

		sfishs = new FlxGroup();
		//for(int i = 0; i < 10; i++)
		createSFish(176,128);
		createSFish(192,128);
		createSFish(208,128);
		createSFish(224,128);
		createSFish(240,128);

		createSFish(176,192);
		createSFish(192,192);
		createSFish(208,192);
		createSFish(224,192);
		createSFish(240,192);

		add(sfishs);

		_balls = new FlxGroup();

		mages = new FlxGroup();
		createMage(192,352);
		createMage(608,352);
		add(mages);

		timer = new FlxTimer();
		//	shieldtimer = new FlxTimer();

		popText = new FlxText(pad.buttonX.x, pad.buttonX.y-20, 200, "X Button On!");
		popText.setSize(6);
		popText.scrollFactor.x = popText.scrollFactor.y = 0;
		popText.visible = true;
		timer.start(2,1,showPText);
		add(popText);

		//380,220 - original size.
		pauseblock = new FlxTileblock(90, 50, 210, 150);
		pauseblock.makeGraphic(210, 150, 0xff000000);
		pauseblock.setAlpha(0.5f);
		pauseblock.setSolid(false);
		pauseblock.immovable = true;
		pauseblock.scrollFactor.x = pauseblock.scrollFactor.y = 0;
		pauseblock.visible = false;
		add(pauseblock);

		pausedTxt = new FlxText(160,65,90,"PAUSED");
		pausedTxt.setSize(19);
		pausedTxt.antialiasing = true;
		pausedTxt.scrollFactor.x = 0;
		pausedTxt.scrollFactor.y = 0;
		pausedTxt.visible = false;
		add(pausedTxt);

		Resumebtn = new FlxButton(160, 100, "Resume");
		Resumebtn.setSolid(false);
		Resumebtn.immovable = true;
		Resumebtn.scrollFactor.x = Resumebtn.scrollFactor.y = 0;
		Resumebtn.exists = true;
		Resumebtn.visible = false;
		add(Resumebtn);

		restartbtn = new FlxButton(160, 130, "Restart", new IFlxButton(){@Override public void callback(){onReset();}});//x.190, x.180, x.170, y.130
		restartbtn.setSolid(false);//Coords 1: (400, 260), 
		restartbtn.immovable = true;
		restartbtn.scrollFactor.x = restartbtn.scrollFactor.y = 0;
		restartbtn.exists = true;
		restartbtn.visible = false;
		add(restartbtn);

		Exitbtn = new FlxButton(160, 160, "Quit Game", new IFlxButton(){@Override public void callback(){onExit();}});//x.190, x.180, x.170, y.150
		Exitbtn.setSolid(false);//Coords 1: (400, 280)
		Exitbtn.immovable = true;
		Exitbtn.scrollFactor.x = Exitbtn.scrollFactor.y = 0;
		Exitbtn.exists = true;
		Exitbtn.visible = false;
		add(Exitbtn);

		/*Pausebtn = new FlxSprite(375, 6);
		 Pausebtn.loadGraphic("pauseic.png",true,true,16,16);
		 Pausebtn.setSolid(false);
		 Pausebtn.immovable = true;
		 Pausebtn.exists = true;
		 Pausebtn.scrollFactor.x = Pausebtn.scrollFactor.y = 0;
		 add(Pausebtn);	*/
		closebtn = new FlxNinePatchButton(350,6,null,"||",20,20,null);
		closebtn.scrollFactor.x = closebtn.scrollFactor.y = 0;
		closebtn.exists = true;
		closebtn.visible = true;
		add(closebtn);

		add(_balls);
		add(_skelGibs);
		add(_tbullets);
	}
    public void createCoin(int X,int Y)
	{
		FlxSprite coin = new FlxSprite(X,Y);
		coin.loadGraphic("coinframes.png", true, true, 16, 16);
		coin.addAnimation("rotating", new int[]{0, 1, 2, 3}, 6, true);
		coin.offset.x = 3;
		coin.offset.y = 3;
		coin.centerOffsets();
		coin.immovable = true;
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

	public void createSpike(int X, int Y,int Angle,int distance){
		Spike spike = new Spike(X,Y,Angle);
		spike.getDistance(distance);
		if(switchS){
			spike.WatchSprite(jplayer);
		}else{
			spike.WatchSprite(_player);
		}
		spikes.add(spike);
	}
	
	public void createEnemy(int X, int Y,int Accel){
	    Enemy enemy = new Enemy(X,Y,16,16,Accel,1);
		if(switchS){
			enemy.followSprite(jplayer);
		}else{
			enemy.followSprite(_player);
		}
		enemies.add(enemy);
	}
	
	public void createBat(int X, int Y){
		Bat bat = new Bat(X,Y,16,16,1);
		bat.followSprite(_player);
		bats.add(bat);
	}


	public void createSFish(int X, int Y){
		Silverfish sfish = new Silverfish(X,Y,16,16,500);
		sfish.watchLevel(level);
		sfishs.add(sfish);
	}

	public void createCrusher(int X,int Y,int distance){
		Crusher crusher = new Crusher(X,Y,1);
		crusher.setFallDistance(distance);
		if(switchS){
			crusher.WatchSprite(jplayer);
		}else{
			crusher.WatchSprite(_player);
		}
		crushers.add(crusher);
	}
	

	public void createTurret(int X,int Y){
		Turret turret = new Turret(X,Y,_tbullets,2,1);
		if(switchS){
			turret.WatchSprite(jplayer);
		}else{
			turret.WatchSprite(_player);
		}
		turrets.add(turret);
	}
	

	public void createSkeleton(int X, int Y){
		Skeleton skeleton = new Skeleton(X,Y,16,16,_skelGibs);
		skeleton.watchLevel(level);
		skeleton.goRight();
		skeletons.add(skeleton);
	}

	public void createMage(int X, int Y){
		Mage mage1 = new Mage(X,Y,16,16,_balls,1,1);
		if(switchS){
			mage1.followSprite(level,jplayer);
		}else{
			mage1.followSprite(level,_player);
		}
		mages.add(mage1);
	}
	 
	public void createSlime(int X, int Y, int Accel){
		Slime slime = new Slime(X,Y,16,16,Accel,1);
		slime.followSprite(_player);
		slimes.add(slime);
	}


    @Override
	public void update(){					  
		if(closebtn.status == FlxNinePatchButton.PRESSED){
			crushers.active = false;
			skeletons.active = false;
			_skelGibs.active = false;
			sfishs.active = false;
			turrets.active = false;
			_bullets.active = false;
			_tbullets.active = false;
			mages.active = false;
			_balls.active = false;
			closebtn.visible = false;
			if(switchS){
			jplayer.exists = false;
			}else{
			_player.exists = false;
			}
			pauseblock.visible = true;
			pausedTxt.visible = true;

			Resumebtn.visible = true;
			restartbtn.visible = true;
			Exitbtn.visible = true;	
			enemies.active = false;
			FlxG.music.pause();
		}
		if(Resumebtn.status == Resumebtn.PRESSED || FlxG.keys.justPressed("BACK")){
			crushers.active = true;
			skeletons.active = true;
			_skelGibs.active = true;
			sfishs.active = true;
			turrets.active = true;
			_tbullets.active = true;
			mages.active = true;
			_balls.active = true;
			enemies.active=true;
			_bullets.active =true;
			closebtn.visible = true;
			if(switchS){
			jplayer.exists = true;
			}else{
			_player.exists = true;
			}
			Resumebtn.visible = false;
			restartbtn.visible = false;
			Exitbtn.visible = false;
			pauseblock.visible= false;
			pausedTxt.visible = false;
			FlxG.music.resume();
		}
		super.update();

		/*ENEMY COLLISIONS*/
		
		if(switchS){
			FlxG.collide(crushers,_bullets,doCrusherDamage);

			FlxG.collide(turrets,level);
			FlxG.collide(jplayer,_tbullets,doTPlayerDamage);
			FlxG.collide(_tbullets,level);

			FlxG.collide(mages,level);
			FlxG.collide(mages,spikes);
			FlxG.collide(_balls,spikes);
			FlxG.collide(_balls,level);
			FlxG.collide(jplayer,_balls,doPlayerDamage);
			FlxG.collide(_bullets,mages,doMageDamage);

			/*	FlxG.collide(slimes,level);
			 FlxG.collide(slimes,_player,doPlayerDamage);
			 FlxG.collide(slimes,_bullets, doSlimeDamage);*/

			//		FlxG.overlap(coins,_player,getCoin);
			FlxG.overlap(portalcoin, jplayer, getPCoin);
			//	FlxG.overlap(spikes,_player,doPlayerDamage);
			FlxG.overlap(portal,jplayer,win);
			//	FlxG.collide(skeletons,spikes);
			//	FlxG.collide(skeletons,slimes);
			FlxG.collide(skeletons,level);
			FlxG.overlap(skeletons,jplayer,doPlayerDamage);
			FlxG.collide(level,jplayer);
			FlxG.collide(_bullets, skeletons, doSkeletonDamage);
			FlxG.collide(_skelGibs,jplayer,doPlayerDamage);
			FlxG.collide(level, _bullets);
			FlxG.collide(spikes,_bullets);
			//	FlxG.collide(_bullets, spikes);
			//	FlxG.collide(spikes, _bullets);
			//		FlxG.overlap(lavablocks,_player,doPlayerDamage);
			FlxG.collide(level,crushers);
			FlxG.overlap(crushers,jplayer,doCrushPlayer);
			FlxG.collide(_bullets, turrets, doTurretDamage);		
			/*	FlxG.collide(slimes,slimes);
			 FlxG.collide(slimes, spikes);*/
			FlxG.collide(_skelGibs,level);
			/*	FlxG.collide(sFish,level);
			 FlxG.collide(sFish,_player,doPlayerDamage);
			 FlxG.collide(_bullets,sFish,doTurretDamage);
			 */			
			/*		FlxG.collide(bats,level);
			 FlxG.collide(bats,_player,doPlayerDamage);
			 FlxG.collide(bats,_bullets,doEnemyDamage);*/
			FlxG.collide(walls,jplayer);
			FlxG.overlap(spikes,jplayer,doPlayerDamage);
			FlxG.collide(wallSwitch,jplayer,switchOn);
			FlxG.collide(enemies,spikes);
			FlxG.collide(enemies,level);
			FlxG.collide(enemies,jplayer,doPlayerDamage);
			FlxG.collide(enemies,_bullets, doEnemyDamage);
			FlxG.collide(walls, enemies);

			FlxG.collide(sfishs,level);
			FlxG.collide(sfishs,sfishs);
			FlxG.collide(sfishs,jplayer,doPlayerDamage);
			FlxG.collide(sfishs,_bullets,doEnemyDamage);
			
		}else{
		FlxG.collide(crushers,_bullets,doCrusherDamage);

				FlxG.collide(turrets,level);
				FlxG.collide(_player,_tbullets,doTPlayerDamage);
				FlxG.collide(_tbullets,level);

		FlxG.collide(mages,level);
		FlxG.collide(mages,spikes);
		FlxG.collide(_balls,spikes);
		FlxG.collide(_balls,level);
		FlxG.collide(_player,_balls,doPlayerDamage);
		FlxG.collide(_bullets,mages,doMageDamage);

		/*	FlxG.collide(slimes,level);
		 FlxG.collide(slimes,_player,doPlayerDamage);
		 FlxG.collide(slimes,_bullets, doSlimeDamage);*/

		//		FlxG.overlap(coins,_player,getCoin);
		FlxG.overlap(portalcoin, _player, getPCoin);
		//	FlxG.overlap(spikes,_player,doPlayerDamage);
		FlxG.overlap(portal,_player,win);
		//	FlxG.collide(skeletons,spikes);
		//	FlxG.collide(skeletons,slimes);
		FlxG.collide(skeletons,level);
		FlxG.overlap(skeletons,_player,doPlayerDamage);
		FlxG.collide(level,_player);
		FlxG.collide(_bullets, skeletons, doSkeletonDamage);
		FlxG.collide(_skelGibs,_player,doPlayerDamage);
		FlxG.collide(level, _bullets);
		FlxG.collide(spikes,_bullets);
		//	FlxG.collide(_bullets, spikes);
		//	FlxG.collide(spikes, _bullets);
		//		FlxG.overlap(lavablocks,_player,doPlayerDamage);
		FlxG.collide(level,crushers);
		FlxG.overlap(crushers,_player,doCrushPlayer);
		FlxG.collide(_bullets, turrets, doTurretDamage);		
		/*	FlxG.collide(slimes,slimes);
		 FlxG.collide(slimes, spikes);*/
		FlxG.collide(_skelGibs,level);
		/*	FlxG.collide(sFish,level);
		 FlxG.collide(sFish,_player,doPlayerDamage);
		 FlxG.collide(_bullets,sFish,doTurretDamage);
		 */			
		/*		FlxG.collide(bats,level);
		 FlxG.collide(bats,_player,doPlayerDamage);
		 FlxG.collide(bats,_bullets,doEnemyDamage);*/
		FlxG.collide(walls,_player);
		FlxG.overlap(spikes,_player,doPlayerDamage);
		FlxG.collide(wallSwitch,_player,switchOn);
		FlxG.collide(enemies,spikes);
		FlxG.collide(enemies,level);
		FlxG.collide(enemies,_player,doPlayerDamage);
		FlxG.collide(enemies,_bullets, doEnemyDamage);
		FlxG.collide(walls, enemies);

		FlxG.collide(sfishs,level);
		FlxG.collide(sfishs,sfishs);
		FlxG.collide(sfishs,_player,doPlayerDamage);
		FlxG.collide(sfishs,_bullets,doEnemyDamage);
		
		}
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
		_littleGibs = null;
		hearts = null;
		enemies = null;
		pad = null;
	}	



	private void onExit(){
		FlxG.switchState(new MenuState());
	}

	private void onReset(){
		//			FlxG.switchState(new Options());
		FlxG.resetState();
		FlxG.music.resume();
	}

	IFlxTimer showPText = new IFlxTimer()
	{
		@Override
		public void callback(FlxTimer flxTimer)
		{
			popText.visible = false;
		}
	}; 


	IFlxCollision getCoin = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Coin, FlxObject Player)
		{						
			Coin.kill();
			FlxG.play("Coin.mp3",1.0F,false);
			status.setText("SCORE: "+(coins.countDead()*100));	  		
			portalcoin.exists = true;
		}
	};					

	IFlxCollision getPCoin = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Portalcoin, FlxObject Player)
		{						
			Portalcoin.kill();	
			FlxG.play("Pcoin.mp3",1.0F,false);
			portal.exists = true;			        
		}
	};					

	IFlxCollision switchOn = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Switch, FlxObject Player)
		{	
			wallSwitch.play("on");
			walls.kill();
		}
	};					


	IFlxCollision doSpikeDamage = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Spike, FlxObject Player)
		{			
			//add(new FlxText(200,22 , 100, "You Died!"));			
			if(switchS){
			jplayer.kill();
			}else{
			_player.kill();
			}
			//FlxG.music.kill();
		}
	};		

	IFlxCollision doBoneDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Skeleton, FlxObject Player)
		{									
			//add(new FlxText(400,240 , 400, "You Died!"));			
			if(switchS){
			jplayer.kill();
			}else{
			_player.kill();
			}
			//	FlxG.shake(0.05f,2);
		}
	};


	IFlxCollision doTPlayerDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject TurretBullet, FlxObject Player)
		{									
			if(switchS){
				if(jplayer.health > 1){
					jplayer.hurt(1);
				}else{
					jplayer.kill();
				}

			}else{
				if(_player.health > 1){
					_player.hurt(1);
				}else{
					_player.kill();
				}
			}
		}
	};	


    IFlxCollision doPlayerDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Enemy, FlxObject Player)
		{									
			/*add(new FlxText(400,240 , 400, "You Died!"));			*/
			if(switchS){
			jplayer.kill();
			}else{
			_player.kill();
			}
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
			Skeleton.hurt(1);
		}
	};	

	IFlxCollision doSlimeDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Slime, FlxObject Bullet)
		{									
			//		add(new FlxText(_player.x,_player.y , 100, "Test: Enemy killed."));	
			//		EnemyG.kill();
			Slime.hurt(1);
			//	error.reportError("Did slime damage");
		}
	};

	IFlxCollision doCrusherDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Crusher, FlxObject Bullet)
		{									
			//		add(new FlxText(_player.x,_player.y , 100, "Test: Enemy killed."));	
			//		EnemyG.kill();
			Crusher.hurt(1);
			Crusher.acceleration.x = 0;
			Crusher.velocity.x = 0;
			/*	if(Crusher.acceleration.y == 200){
			 Crusher.acceleration.y = 0;
			 Crusher.update();
			 //Crusher.acceleration.y = 200;
			 }else if(Crusher.velocity.y == -10){
			 Crusher.velocity.y = 0;
			 Crusher.update();
			 //Crusher.velocity.y = -10;
			 }*/

		}
	};

	IFlxCollision doTurretDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Bullet, FlxObject Turret)
		{									
			//add(new FlxText(300,100 , 100, "Test: Enemy killed."));	
			Turret.hurt(1);
		}
	};	

	IFlxCollision doMageDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Bullet, FlxObject Mage)
		{									
			//add(new FlxText(300,100 , 100, "Test: Enemy killed."));	
			Mage.hurt(1);
		}
	};	



	IFlxCollision doCrushPlayer = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Crusher, FlxObject Player)
		{									
			//		add(new FlxText(_player.x,_player.y , 100, "Test: Enemy killed."));	
			//		EnemyG.kill();
			if(switchS){
				if(jplayer.health > 1){
					jplayer.hurt(1);
				}else{
					jplayer.kill();
				}

			}else{
				if(_player.health > 1){
					_player.hurt(1);
				}else{
					_player.kill();
				}
			}
			Crusher.acceleration.x = 0;
			Crusher.velocity.x = 0;
			/*	if(Crusher.acceleration.y == 200){
			 Crusher.acceleration.y = 0;
			 Crusher.update();
			 //Crusher.acceleration.y = 200;
			 }else if(Crusher.velocity.y == -10){
			 Crusher.velocity.y = 0;
			 Crusher.update();
			 //Crusher.velocity.y = -10;
			 }*/

		}
	};

	IFlxCollision win = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Portal, FlxObject Player)
		{
			//	add(new FlxText(200,200, 100, "You win Level 5!"));			
			if(switchS){
			jplayer.exists = false;
			}else{
			_player.exists = false;
			}
			FlxG.play("Portal.mp3",1.0F,false);
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
			
			if(gameSave.data.get("Progress",int.class) == null)
			{
				gameSave.data.put("Progress", 2);
				gameSave.flush();
			}
			@SuppressWarnings("unchecked")
				int progress = gameSave.data.get("Progress", int.class);

			if(progress >= 13){
				FlxG.switchState(new PlayState12());
			}else{
				

			//Save

			gameSave.data.put("Progress", 13);
			gameSave.flush();

			FlxG.switchState(new PlayState12());
			}
		}
	}; 
}
