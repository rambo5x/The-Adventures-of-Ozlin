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
import com.badlogic.gdx.graphics.g3d.*;
import com.dvreiter.starassault.Tools.*;

public class PlayState6 extends FlxState
{	

	private static final int TILE_WIDTH = 16;
	private static final int TILE_HEIGHT = 16;

	public FlxTilemap level;
	public FlxSprite portal;	
	public FlxGroup coins;
	public FlxGroup walls;
	public FlxSprite portalcoin;
	public FlxGroup spikes;	   
	public FlxText status;
	public FlxSprite coin;
	public FlxSprite wallSwitch;
	public FlxText pausedTxt,popText;

	public FlxGroup enemies;
	public FlxGroup skeletons;
	public boolean canMove;
	Player _player;
	private JoyStickPlayer jplayer;
	FlxVirtualPad pad;
	protected FlxGroup _bullets, Spowerupp;
	protected FlxEmitter _littleGibs;
	protected FlxEmitter _skelGibs;
	protected FlxGroup _tbullets;

	private FlxTimer timer;

	public FlxTileblock pauseblock;
	//private FlxSprite Pausebtn;
	private FlxSprite wallPortalIn,wallPortalOut,elevator,elevator2;
	private FlxButton Exitbtn;
	private FlxButton restartbtn;
	private FlxButton Resumebtn;
	private FlxButton Pausebtn;
	private FlxSave gameSave,jswitchSave;
	private boolean switchS;
	
	FlxPath path, path2;
	FlxPoint destination, destination2;
	
	private FlxNinePatchButton closebtn;

	private Mage mage1;
	private Silverfish sFish;
	private Bat bat;
	private FlxGroup _balls, crushers, turrets, slimes;
	private FlxAnalog analog;
	private ErrorReporter reporter;

	@Override
	public void create()
	{						
		FlxG.setFramerate(60);
		FlxG.setFlashFramerate(60);

		//FlxG.setBgColor(0x4c4c4c);//6c767c
		FlxG.setBgColor(0xFF00CCFF);
		FlxG.mouse.show();

		int[] data={7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,
			7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,	
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,7,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,1,1,1,1,1,1,1,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,1,1,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,1,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,1,1,1,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,//Middle or half of y of the level map.
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,1,1,1,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,			
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,7,7,                                                  
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,1,1,1,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7, 
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,1,1,1,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,1,1,1,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,1,1,1,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,7,7,
			7,7,1,1,1,1,1,1,1,1,1,1,7,1,1,1,1,1,7,1,1,1,1,1,1,1,1,1,1,1,1,1,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,1,1,			
			7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,
			7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7
			};
		
		level = new FlxTilemap();
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 50), "tilemap.png", TILE_WIDTH, TILE_HEIGHT); 
		add(level);
	//	reporter = new ErrorReporter();
	//	try{

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

		 elevator = new FlxSprite(232,416);//32
		 elevator.loadGraphic("elevator.png", true, true, 32, 10);
		 elevator.immovable = true;
		 destination = elevator.getMidpoint();
		 destination.y -= 336;//122
		 Array<FlxPoint> points = new Array<FlxPoint>();
		 points.addAll(new FlxPoint[]{elevator.getMidpoint(),destination});
		 path = new FlxPath(points);
		 elevator.followPath(path,40,FlxObject.PATH_YOYO);
		 add(elevator);
	
		elevator2 = new FlxSprite(560,432);
		elevator2.loadGraphic("elevator.png", true, true, 32, 10);
		elevator2.immovable = true;
		//elevator2.maxVelocity.y = 400;
		destination2 = elevator2.getMidpoint();
		destination2.y -= 352;//122
		Array<FlxPoint> points2 = new Array<FlxPoint>();
		points2.addAll(new FlxPoint[]{elevator2.getMidpoint(),destination2});
		path2 = new FlxPath(points2);
		elevator2.followPath(path2,40,FlxObject.PATH_YOYO);
		add(elevator2);
		
		portal = new FlxSprite(400, 112);//50
		portal.loadGraphic("Grass_Portal.png", true, true, 16, 16);
		portal.addAnimation("spinning", new int[]{0, 1, 2}, 6, true);
		portal.play("spinning");
		portal.immovable = true;
		portal.exists = false;
		add(portal);

	/*	wallSwitch = new FlxSprite(700,400);//750,64
		wallSwitch.loadGraphic("switch.png", true, true, 16, 16);
		wallSwitch.addAnimation("off",new int[]{0});
		wallSwitch.addAnimation("on",new int[]{1});
		wallSwitch.play("off");
		wallSwitch.immovable = true;
		add(wallSwitch);*/

		portalcoin = new FlxSprite(400, 48);//first (170, 80) Second (170, 160)
		portalcoin.loadGraphic("Portalcoin.png", true, true, 16, 16);
		portalcoin.addAnimation("rotate", new int[]{0, 1, 2}, 4, true);
		portalcoin.play("rotate");
		add(portalcoin);

	/*	analog = new FlxAnalog(50, 200);
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
			pad.buttonX.setAlpha(0.15f);
			pad.buttonA.x = pad.buttonA.x - 10;
			pad.buttonB.x = pad.buttonB.x - 10;
			pad.buttonX.x = pad.buttonX.x - 10;
			pad.buttonY.x = pad.buttonY.x - 10;
			add(pad);

			_bullets = new FlxGroup();

			jplayer = new JoyStickPlayer(240,400,16,16,_bullets,_littleGibs, pad,analog);
	//		jplayer.setHasGravityToggle(true);
			FlxG.camera.follow(jplayer, FlxCamera.STYLE_PLATFORMER);
			FlxG.camera.setBounds(0,0,800,480,true);// 1st 400,240  2nd 800,240, 3rd 1200,480//status = new FlxText(2,2,100, "SCORE: ";
			add(_bullets);
			add(jplayer);
			add(_littleGibs);

		}else{

			pad = new FlxVirtualPad(FlxVirtualPad.LEFT_RIGHT, FlxVirtualPad.A_B_X_Y);
			pad.setAlpha(0.5f);
			pad.buttonX.setAlpha(0.15f);
			pad.buttonA.x = pad.buttonA.x - 10;
			pad.buttonB.x = pad.buttonB.x - 10;
			pad.buttonX.x = pad.buttonX.x - 10;
			pad.buttonY.x = pad.buttonY.x - 10;
			add(pad);

			_bullets = new FlxGroup();

			_player = new Player(240,400,16,16,_bullets,_littleGibs, pad);
	//		_player.setHasGravityToggle(true);
			FlxG.camera.follow(_player, FlxCamera.STYLE_PLATFORMER);
			FlxG.camera.setBounds(0,0,800,480,true);// 1st 400,240  2nd 800,240, 3rd 1200,480//status = new FlxText(2,2,100, "SCORE: ";

			add(_bullets) ;
			add(_player) ;
			add(_littleGibs); 

		}

	//	_balls = new FlxGroup();

	/*	enemies = new FlxGroup();
		createEnemy(200,280,500);
		//createEnemy(768,32,500);
		add(enemies);*/

		/*mage1 = new Mage(700,140,16,16,_balls,1,1);
		mage1.followSprite(level,_player);
		add(mage1);

		bat = new Bat(400,240,16,16,1);
		bat.followSprite(_player);
		add(bat);*/

		slimes = new FlxGroup();
	//	createSlime(620,432,200);//200
		//createSlime(620,432,200);
		//createSlime(620,432,200);
		//createSlime(620,432,200);
		//add(slimes);

		skeletons = new FlxGroup();
		createSkeleton(420,64);
		createSkeleton(400,432);
		createSkeleton(700,432);
		//createSkeleton(400,264);
		//	createSkeleton(768,32);
		add(skeletons);

		_tbullets = new FlxGroup();
		//x=648
		turrets = new FlxGroup();
		createTurret(32,384);
		createTurret(32,288);
		createTurret(32,192);
		createTurret(32,96);
		
		createTurret(752,48);
		createTurret(752,144);
		createTurret(752,240);
		createTurret(752,336);
		
		createTurret(752,416);
		
	//	createTurret(304,416);
	//	createTurret(480,64);
	//	createTurret(480,32);
		add(turrets);

	/*	walls = new FlxGroup();
		createWall(240,224);
		createWall(256,224);
		add(walls);*/

		crushers = new FlxGroup();
		createCrusher(352,96);//48;
		createCrusher(448,96);
		
	/*	createCrusher(320,160);
		createCrusher(320,224);
		createCrusher(320,288);
		createCrusher(320,352);
		
		createCrusher(480,128);
		createCrusher(480,192);
		createCrusher(480,256);
		createCrusher(480,320);*/
	
	//	createCrusher(
		
		add(crushers);

	/*	Spowerupp = new FlxGroup();
		createSpowerup(600,140);
		add(Spowerupp);

		wallPortalIn = new FlxSprite(140,390);
		wallPortalIn.loadGraphic("wallportal.png",true,true,16,16);
		wallPortalIn.addAnimation("rotation", new int[]{0, 1, 2, 3}, 4, true);
		wallPortalIn.immovable = true;
		wallPortalIn.play("rotation");
		add(wallPortalIn);

		wallPortalOut = new FlxSprite(214,390);//164
		wallPortalOut.loadGraphic("wallportal.png",true,true,16,16);
		wallPortalOut.addAnimation("rotation", new int[]{0, 1, 2, 3}, 4, true);
		wallPortalOut.immovable = true;
		wallPortalOut.visible = false;
		wallPortalOut.play("rotation");
		add(wallPortalOut);*/

	/*	coins = new FlxGroup(); 			
		//bottom coins	
		createCoin(105, 300);//first (105, 115) second (210, 230)
		//	createCoin(140, 390);
		add(coins);*/

		spikes = new FlxGroup();
		createSpike(352,32,180);
	//	createSpike(400,32,180);
		createSpike(448,32,180);
		
		createSpike(304,192,0);
		createSpike(304,256,0);
		createSpike(304,320,0);
		createSpike(304,384,0);
		
		createSpike(496,160,0);
		createSpike(496,224,0);
		createSpike(496,288,0);
		createSpike(496,352,0);
		
	/*	createSpike(90,352,180);
		createSpike(160,16,180);
		createSpike(196,64,0);

		createSpike(580,208,0);
		createSpike(532,176,180);
		createSpike(484,208,0);
		createSpike(436,176,180);
		createSpike(388,208,0);*/
		add(spikes);
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

		closebtn = new FlxNinePatchButton(350,6,null,"||",20,20,null);
		closebtn.scrollFactor.x = closebtn.scrollFactor.y = 0;
		closebtn.exists = true;
		closebtn.visible = true;
		add(closebtn);

		
		add(_tbullets);
		//add(_balls);
		add(_skelGibs); 
	//	} catch (Exception e) { e.printStackTrace(); 
	//		reporter.reportError(e);
	//	}
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

	public void createSpowerup(int X, int Y){
		FlxSprite Spowerup = new FlxSprite(X,Y);
		Spowerup.loadGraphic("shieldpowerup.png", true, true, 16, 16); 
		Spowerup.immovable = true;
		//	Spowerup.visible = false;
		Spowerupp.add(Spowerup);
	}		

	public void createCrusher(int X,int Y){
		Crusher crusher = new Crusher(X,Y,1);
		crusher.setFallDistance(48);
		if(switchS){
			crusher.WatchSprite(jplayer);
		}else{
			crusher.WatchSprite(_player);
		}
		crushers.add(crusher);
	}

	public void createTurret(int X,int Y){
		Turret turret = new Turret(X,Y,_tbullets,3,1);
		if(switchS){
			turret.WatchSprite(jplayer);
		}else{
			turret.WatchSprite(_player);
		}
		turrets.add(turret);
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
	//	spike.getDistance(200);
		spikes.add(spike);
	}

	public void createEnemy(int X, int Y,int Accel){
		Enemy enemy = new Enemy(X,Y,16,16,Accel,1);
		enemy.followSprite(_player);
		enemies.add(enemy);
	}

	public void createSkeleton(int X, int Y){
		Skeleton skeleton = new Skeleton(X,Y,16,16,_skelGibs);
		skeleton.watchLevel(level);
		skeleton.goRight();
		skeletons.add(skeleton);
	}

	public void createSlime(int X, int Y, int Accel){
		Slime slime = new Slime(X,Y,16,16,Accel,1);
		slime.followSprite(_player);
		slimes.add(slime);
	}

    @Override
	public void update(){	
		//	if(Pausebtn.overlapsPoint(new FlxPoint(FlxG.mouse.x, FlxG.mouse.y)) && FlxG.mouse.justPressed() || FlxG.keys.justPressed("BACK")){
		if(closebtn.status == FlxNinePatchButton.PRESSED){
			turrets.active = false;
			_tbullets.active= false;
			skeletons.active= false;
			_skelGibs.active = false;
			_bullets.active = false;
			crushers.active = false;
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
	//		enemies.active = false;
			FlxG.music.pause();
		}
		if(Resumebtn.status == Resumebtn.PRESSED || FlxG.keys.justPressed("BACK")){
			turrets.active = true;
			_tbullets.active= true;
			skeletons.active= true;
			_skelGibs.active = true;
			crushers.active = true;
	//		enemies.active=true;
			_bullets.active = true;
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

		FlxG.collide(slimes,level);
		FlxG.collide(slimes,jplayer,doPlayerDamage);
		FlxG.collide(slimes,_bullets, doSlimeDamage);

		FlxG.overlap(portalcoin, jplayer, getPCoin);
		FlxG.overlap(spikes,jplayer,doPlayerDamage);
		FlxG.overlap(portal,jplayer,win);
		FlxG.collide(skeletons,spikes);
	//	FlxG.collide(skeletons,slimes);
		FlxG.collide(skeletons,level);
		FlxG.overlap(skeletons,jplayer,doPlayerDamage);
		FlxG.collide(level,jplayer);
		FlxG.collide(_bullets, skeletons, doSkeletonDamage);
		FlxG.collide(_skelGibs,jplayer,doPlayerDamage);
		FlxG.collide(level, _bullets);
		FlxG.collide(_bullets, spikes);
		FlxG.collide(spikes, _bullets);
		FlxG.collide(level,crushers);
		FlxG.overlap(crushers,jplayer,doCrushPlayer);
		FlxG.collide(_bullets, turrets, doTurretDamage);		
		FlxG.collide(slimes,slimes);
		FlxG.collide(slimes, spikes);
		FlxG.collide(_skelGibs,level);
		FlxG.collide(elevator,jplayer);
		FlxG.collide(elevator2,jplayer);
		}else{
			FlxG.collide(crushers,_bullets,doCrusherDamage);

			FlxG.collide(turrets,level);
			FlxG.collide(_player,_tbullets,doTPlayerDamage);
			FlxG.collide(_tbullets,level);

			FlxG.collide(slimes,level);
			FlxG.collide(slimes,_player,doPlayerDamage);
			FlxG.collide(slimes,_bullets, doSlimeDamage);

			FlxG.overlap(portalcoin, _player, getPCoin);
			FlxG.overlap(spikes,_player,doPlayerDamage);
			FlxG.overlap(portal,_player,win);
			FlxG.collide(skeletons,spikes);
			//	FlxG.collide(skeletons,slimes);
			FlxG.collide(skeletons,level);
			FlxG.overlap(skeletons,_player,doPlayerDamage);
			FlxG.collide(level,_player);
			FlxG.collide(_bullets, skeletons, doSkeletonDamage);
			FlxG.collide(_skelGibs,_player,doPlayerDamage);
			FlxG.collide(level, _bullets);
			FlxG.collide(_bullets, spikes);
			FlxG.collide(spikes, _bullets);
			FlxG.collide(level,crushers);
			FlxG.overlap(crushers,_player,doCrushPlayer);
			FlxG.collide(_bullets, turrets, doTurretDamage);		
			FlxG.collide(slimes,slimes);
			FlxG.collide(slimes, spikes);
			FlxG.collide(_skelGibs,level);
			FlxG.collide(elevator,_player);
			FlxG.collide(elevator2,_player);
			/*	FlxG.collide(sFish,level);
			 FlxG.collide(sFish,_player,doPlayerDamage);
			 FlxG.collide(_bullets,sFish,doTurretDamage);
			 */
		}
		/*	FlxG.collide(sFish,level);
		 FlxG.collide(sFish,_player,doPlayerDamage);
		 FlxG.collide(_bullets,sFish,doTurretDamage);
		 */
	}

	@Override
	public void destroy()
	{
		super.destroy();
		Pausebtn = null;
		restartbtn = null;
		Exitbtn = null;
		Resumebtn = null;
		pauseblock = null;
		skeletons = null;
		level = null;
		portal = null;
		coins = null;
		portalcoin = null;
		portal = null;
		spikes = null;
		_bullets = null;
		status = null;
		_player = null;
		enemies = null;
		pad = null;
	}	

	private void onExit(){
		FlxG.switchState(new MenuState());
	}

	private void onReset(){
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
			//Coin.acceleration.x = 0;
			//	Coin.velocity.x = 0;
			FlxG.play("Coin.mp3",1.0F,false);
			status.setText("SCORE: "+(coins.countDead()*100));	  		
			//	_player.x = 220;
	    }
	};		

	IFlxCollision getPP = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Ppowerupp, FlxObject Player)
		{						
	        Ppowerupp.kill();	     
			if(switchS){
			jplayer.health = 5;
			}else{
	        _player.health = 5;
			}
			popText.visible = true;
			timer.start(2,1,showPText);
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

	IFlxCollision teleport = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject wallPortal, FlxObject Player)
		{						
			//      Player.kill();
			//Coin.acceleration.x = 0;
			//	Coin.velocity.x = 0;
			wallPortalOut.visible = true;
			if(switchS){
			jplayer.x = 220;
			}else{
			_player.x = 220;
			}
	    }
	};					

	/*
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
	 };*/

    IFlxCollision doPlayerDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Enemy, FlxObject Player)
		{									
			/*add(new FlxText(400,240 , 400, "You Died!"));		*/	
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

	IFlxCollision doSkeletonDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Bullet, FlxObject Skeleton)
		{									
			//add(new FlxText(300,100 , 100, "Test: Enemy killed."));	
			Skeleton.hurt(1);
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


	IFlxCollision win = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Portal, FlxObject Player)
		{
			//	add(new FlxText(200,200, 100, "You win Level 4!"));			
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

			if(progress >= 8){
				FlxG.switchState(new PlayState7());
			}else{
				

			//Save

			gameSave.data.put("Progress", 8);
			gameSave.flush();

			FlxG.switchState(new PlayState7());
		}
		}
	}; 

	/*private void wrap(FlxObject obj)
	 {
	 obj.x = (obj.x + obj.width / 2 + 800) % 800 - obj.width / 2;
	 obj.y = (obj.y + obj.height / 2) % 480 - obj.height / 2;
	 }*/
}
