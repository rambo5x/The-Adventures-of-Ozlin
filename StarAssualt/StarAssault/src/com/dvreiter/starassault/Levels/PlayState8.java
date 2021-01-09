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
import com.dvreiter.starassault.Liquids.*;
import com.dvreiter.starassault.Tools.*;

public class PlayState8 extends FlxState
{	

	private static final int TILE_WIDTH = 16;
	private static final int TILE_HEIGHT = 16;

	public FlxTilemap level;
	public FlxSprite portal;	
	public FlxGroup coins,pcoins;
	public FlxGroup walls;
	public FlxSprite portalcoin,portalcoin2;
	public FlxGroup spikes;	   
	public FlxText status;
	public FlxSprite coin,checkpt;
	public FlxSprite wallSwitch;
	public FlxText pausedTxt,popText,shieldTimeTxt;

	public FlxGroup enemies;
	public FlxGroup skeletons;

	private boolean coordsIsUpdated,isSignalGreen;

	Player _player;
	FlxVirtualPad pad;
	protected FlxGroup _bullets, Spowerupp;
	protected FlxEmitter _littleGibs;
	protected FlxEmitter _skelGibs;
	protected FlxGroup _tbullets;

	private FlxTimer timer, shieldtimer;
	private FlxNinePatchButton closebtn;

	private JoyStickPlayer jplayer;
	public FlxTileblock pauseblock;
	//private FlxSprite Pausebtn;
	private FlxSprite wallPortalIn,wallPortalOut;
	private FlxButton Exitbtn;
	private FlxButton restartbtn;
	private FlxButton Resumebtn;
	private FlxButton Pausebtn;
	private FlxSave gameSave,checkpt2Save,jswitchSave;
	private boolean complete;
	private Mage mage1;
	private Silverfish sFish;
	private Bat bat;

	private FlxGroup _balls, crushers, turrets, slimes, lavablocks,checkpts;
	private FlxAnalog analog;
	ErrorReporter err;
	private boolean switchS;

	@Override
	public void create()
	{		
	//try{
		FlxG.setFramerate(60);
		FlxG.setFlashFramerate(60);

		FlxG.setBgColor(0xFF00CCFF);
		FlxG.mouse.show();

		int[] data={7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,
			7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,	
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,7,7,
			7,7,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,7,7,
			7,7,0,0,0,0,7,0,0,0,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,0,0,0,0,7,7,
			7,7,0,0,0,1,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,7,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,7,7,
			7,7,1,0,0,0,7,0,0,0,7,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,0,0,0,0,7,7,
			7,7,0,0,0,0,0,1,0,0,7,0,0,0,0,0,0,7,0,0,0,0,0,0,7,7,0,0,0,0,0,0,7,0,0,0,0,0,0,7,0,0,0,7,0,0,0,0,7,7,
			7,7,0,0,0,1,1,0,0,0,7,0,0,0,0,0,0,7,0,0,0,0,0,0,7,7,0,0,0,0,0,0,7,0,0,0,0,0,0,7,0,0,0,7,0,0,0,0,7,7,
			7,7,0,0,0,0,7,0,0,1,7,0,0,0,0,0,0,7,0,0,0,0,0,0,7,7,0,0,0,0,0,0,7,0,0,0,0,0,0,7,0,0,0,7,0,0,0,0,7,7,
			7,7,1,0,0,0,7,0,0,0,7,0,0,0,0,0,0,7,0,0,0,0,0,0,7,7,0,0,0,0,0,0,7,0,0,0,0,0,0,7,0,0,0,7,1,1,1,1,7,7,//Middle or half of y of the level map.
			7,7,0,0,0,0,0,1,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,7,0,0,0,0,7,7,			
			7,7,0,0,0,1,1,0,0,0,7,1,1,1,1,1,1,1,0,0,0,0,0,0,7,7,0,0,0,0,0,0,1,1,1,1,1,1,1,7,0,0,0,7,0,0,0,0,7,7,                                                  
			7,7,0,0,0,0,7,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7, 
			7,7,1,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,7,7,
			7,7,0,0,0,0,7,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,7,7,
			7,7,1,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,1,0,0,7,7,
			7,7,0,0,0,0,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,0,0,0,0,7,7,
			7,7,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,7,7,
			7,7,1,1,1,1,1,0,0,0,1,1,1,0,0,0,1,1,0,0,0,1,0,0,0,1,1,0,0,0,1,1,0,0,0,1,1,1,0,0,0,1,1,7,1,1,1,1,7,7,			
			7,7,7,8,9,7,7,0,0,0,7,8,9,0,0,0,7,7,0,0,0,7,0,0,0,7,7,0,0,0,7,7,0,0,0,7,8,9,0,0,0,7,7,7,7,8,9,7,7,7,	
			7,7,7,7,7,7,7,0,0,0,7,7,7,0,0,0,7,7,0,0,0,7,0,0,0,7,7,0,0,0,7,7,0,0,0,7,7,7,0,0,0,7,7,7,7,7,7,7,7,7,			
		};
		
		level = new FlxTilemap();
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 50), "tilemap.png", TILE_WIDTH, TILE_HEIGHT); 
		add(level);
		
		err = new ErrorReporter();
		
		lavablocks = new FlxGroup();
		
		createLava(112,448);
		createLava(112,464);
		createLava(128,448);
		createLava(128,464);
		createLava(144,448);
		createLava(144,464);
		
		createLava(208,448);
		createLava(208,464);
		createLava(224,448);
		createLava(224,464);
		createLava(240,448);
		createLava(240,464);
		
		createLava(288,448);
		createLava(288,464);
		createLava(304,448);
		createLava(304,464);
		createLava(320,448);
		createLava(320,464);
		
		createLava(352,448);
		createLava(352,464);
		createLava(368,448);
		createLava(368,464);
		createLava(384,448);
		createLava(384,464);
		
		createLava(432,448);
		createLava(432,464);
		createLava(448,448);
		createLava(448,464);
		createLava(464,448);
		createLava(464,464);
		
		createLava(512,448);
		createLava(512,464);
		createLava(528,448);
		createLava(528,464);
		createLava(544,448);
		createLava(544,464);
		
		createLava(608,448);
		createLava(608,464);
		createLava(624,448);
		createLava(624,464);
		createLava(640,448);
		createLava(640,464);
		
	/*	createLava(304,448);
		createLava(304,464);
		createLava(320,448);
		createLava(320,464);*/
		
		add(lavablocks);

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

		/*	FlxPath path, path2;
		 FlxPoint destination, destination2;

		 //Create the elevator and put it on a up and down path
		 elevator = new FlxSprite(400,200);
		 elevator.loadGraphic("elevator.png", true, true, 32, 16);
		 elevator.immovable = true;
		 destination = elevator.getMidpoint();
		 destination.y += 56;//122
		 Array<FlxPoint> points = new Array<FlxPoint>();
		 points.addAll(new FlxPoint[]{elevator.getMidpoint(),destination});
		 path = new FlxPath(points);
		 elevator.followPath(path,40,FlxObject.PATH_YOYO);
		 add(elevator);

		 //Create the side-to-side pusher object and put it on a different path
		 pusher = new FlxSprite(400, 380);
		 pusher.loadGraphic("pusher.png", true, true, 16, 32);
		 pusher.immovable = true;
		 destination2 = pusher.getMidpoint();
		 destination2.x += 28;//56
		 points = new Array<FlxPoint>();
		 points.addAll(new FlxPoint[]{pusher.getMidpoint(),destination2});
		 path2 = new FlxPath(points);
		 pusher.followPath(path2,40,FlxObject.PATH_YOYO);
		 add(pusher);  */
		 
	/*	checkpt2Save = new FlxSave();
		checkpt2Save.bind("checkpt2Coords");
		
		if(checkpt2Save.data.get("Coords",boolean.class) == null)
		{
			checkpt2Save.data.put("Coords", false);
			checkpt2Save.flush();
		}

		//load
		@SuppressWarnings("unchecked")
			boolean playercoordsCheck = checkpt2Save.data.get("Coords", boolean.class);

		if(playercoordsCheck == false){
			coordsIsUpdated = false;
		}else if (playercoordsCheck == true){
			coordsIsUpdated = true;
		}
		*/
		portalcoin = new FlxSprite(208, 208);//first (170, 80) Second (170, 160)
		portalcoin.loadGraphic("Portalcoin.png", true, true, 16, 16);
		portalcoin.addAnimation("rotate", new int[]{0, 1, 2}, 4, true);
		portalcoin.play("rotate");
		add(portalcoin);
		
		portalcoin2 = new FlxSprite(576, 208);//first (170, 80) Second (170, 160)
		portalcoin2.loadGraphic("Portalcoin.png", true, true, 16, 16);
		portalcoin2.addAnimation("rotate", new int[]{0, 1, 2}, 4, true);
		portalcoin2.play("rotate");
		add(portalcoin2);
		
		portal = new FlxSprite(384, 336);//50
		portal.loadGraphic("Grass_Portal.png", true, true, 16, 16);
		portal.addAnimation("spinning", new int[]{0, 1, 2}, 6, true);
		portal.play("spinning");
		portal.immovable = true;
		portal.exists = false;
		add(portal);

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

			jplayer = new JoyStickPlayer(50,400,16,16,_bullets,_littleGibs, pad,analog);
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

			_player = new Player(50,400,16,16,_bullets,_littleGibs, pad);
			_player.setHasGravityToggle(true);
			FlxG.camera.follow(_player, FlxCamera.STYLE_PLATFORMER);
			FlxG.camera.setBounds(0,0,800,480,true);// 1st 400,240  2nd 800,240, 3rd 1200,480//status = new FlxText(2,2,100, "SCORE: ";

			add(_bullets) ;
			add(_player) ;
			add(_littleGibs); 

		} 
	//	_balls = new FlxGroup();
	
		/*if(coordsIsUpdated){
			_player = new Player(220,240,16,16,_bullets,_littleGibs, pad);
			_player.setHasGravityToggle(true);
		}else{
			_player = new Player(50,400,16,16,_bullets,_littleGibs, pad);//50
			_player.setHasGravityToggle(true);

		}*/
		//_player.setHasFlyingToggle(false);
 
		enemies = new FlxGroup();
		createEnemy(600,384,-500);
	//	createEnemy(128,128,-500);
		createEnemy(208,280,500);
		createEnemy(500,300,500);
		//createEnemy(768,32,500);
		add(enemies);

	//	slimes = new FlxGroup();
	//	createSlime(600,200,200);
	//	createSlime(500,200,200);
	//	createSlime(400,200,200);
		//	createSlime(560,280,200);
	//	add(slimes);

		skeletons = new FlxGroup();
		createSkeleton(320,66,1);
		createSkeleton(180,128,2);
		createSkeleton(624,128,2);
	//	createSkeleton(624,400);
	//	createSkeleton(400,264);
		//	createSkeleton(768,32);
		add(skeletons);

		_tbullets = new FlxGroup();
		//x=648
		turrets = new FlxGroup();
		createTurret(800-112, 480-64);//208
		createTurret(96,480-176);
		createTurret(96,480-240);
		createTurret(96,480-304);
		createTurret(800-48,64);
		
		createTurret(112,128);
		
		createTurret(320,320);
		createTurret(464,320);
		//	createTurret(220,64,0);
	//	createTurret(480,64);
	//	createTurret(480,32);
		add(turrets);

	//	walls = new FlxGroup();
	//	createWall(240,224);
	//	createWall(256,224);
	//	add(walls);

		crushers = new FlxGroup();
		createCrusher(32,48,48);
		createCrusher(96,48,64);
		createCrusher(240,48,64);
		
		createCrusher(704,240,80);
		createCrusher(736,240,128);
		
		createCrusher(240,208,48);
		createCrusher(544,208,48);
		 
		add(crushers);
		
	/*	checkpt = new FlxSprite(208,240);
		checkpt.loadGraphic("flag.png", true, true, 16, 16);
		checkpt.addAnimation("signalGreen",new int[]{0,1,2},4,true);
		checkpt.addAnimation("signalRed",new int[]{3,4},4,true);
		checkpt.immovable = true;
		checkpt.play("signalRed");
		add(checkpt);*/
		

	//	Spowerupp = new FlxGroup();
	//	createSpowerup(600,140);
	//	add(Spowerupp);
	
		walls = new FlxGroup();
		createWall(304,352);
		createWall(480,352);
		add(walls);
		
		timer = new FlxTimer();
		//	shieldtimer = new FlxTimer();

		popText = new FlxText(pad.buttonX.x, pad.buttonX.y-20, 200, "X Button On!");
		popText.setSize(6);
		popText.scrollFactor.x = popText.scrollFactor.y = 0;
		popText.visible = true;
		timer.start(2,1,showPText);
		add(popText);
		

 // 		coins = new FlxGroup(); 			
		//bottom coins	
	//	createCoin(105, 300);//first (105, 115) second (210, 230)
		//	createCoin(140, 390);
	//	add(coins);

		spikes = new FlxGroup();
		
		createSpike(48,32,180,0);
		createSpike(64,32,180,0);
		createSpike(80,32,180,0);
		
		createSpike(192,80,0,0);
		createSpike(192,32,180,0);
		createSpike(288,80,0,0);
		createSpike(288,32,180,0);
		createSpike(496,80,0,0);
		createSpike(496,32,180,0);
		createSpike(592,80,0,0);
		createSpike(592,32,180,0);
	/*	createSpike(128,64,0,false);
		createSpike(160,32,180,true);
		createSpike(192,64,0,false);
		createSpike(224,32,180,true);
		createSpike(256,64,0,false);
		createSpike(288,32,180,true);
		createSpike(320,64,0,false);
		createSpike(352,32,180,true);
		createSpike(384,64,0,false);*/
		createSpike(704,208,0,0);
		createSpike(720,208,0,0);
		createSpike(736,208,0,0);
		createSpike(752,208,0,0);
		
		//createSpike(100,480-96,180,48);		
		createSpike(160,480-96,180,48);
		createSpike(192,480-96,180,48);
		createSpike(224+32,480-96,180,48);
		createSpike(240+32,480-96,180,48);
		createSpike(304+32,480-96,180,48);
		createSpike(368+32,480-96,180,48);
		createSpike(384+32,480-96,180,48);
		createSpike(448+32,480-96,180,48);
		createSpike(464+32,480-96,180,48);
		createSpike(528+32,480-96,180,48);
		createSpike(560+32,480-96,180,48);
		
		add(spikes);

	/*	timer = new FlxTimer();
		shieldtimer = new FlxTimer();

		popText = new FlxText(600, 140, 200, "Shield!");
		popText.setSize(6);
		popText.visible = false;
		add(popText);*/

	/*	status = new FlxText(2,2,100, "SCORE: ");
		status.setShadow(0xff000000);
		status.setText("SCORE: "+(pcoins.countDead()*100));
		status.scrollFactor.x = status.scrollFactor.y = 0;
		add(status);*/

	/*	shieldTimeTxt = new FlxText(200,2,100, "Shield: ");
		shieldTimeTxt.setShadow(0xff000000);
		//shieldTimeTxt.setText("SCORE: "+(coins.countDead()*100));
		shieldTimeTxt.scrollFactor.x = shieldTimeTxt.scrollFactor.y = 0;
		shieldTimeTxt.visible = false;
		add(shieldTimeTxt);*/
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
	//	add(_balls)
		add(_skelGibs);
	//	}catch(Exception e){
		//	err.reportError(e);
		//}
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
	
	public void createCheckpt(int X,int Y){
		FlxSprite checkpt = new FlxSprite(X,Y);
		checkpt.loadGraphic("flag.png", true, true, 16, 16);
		//	checkpt.addAnimation("signalGreen",new int[]{0,1,2},4,true);
		//	checkpt.addAnimation("signalRed",new int[]{3,4},4,true);
		checkpt.immovable = true;
		/*	if(isSignalGreen){
		 checkpt.play("signalGreen");
		 }else{
		 checkpt.play("signalRed");
		 }*/
		//	checkpt.play("signalRed");
		checkpts.add(checkpt);
	}
	
	

	public void createSpowerup(int X, int Y){
		FlxSprite Spowerup = new FlxSprite(X,Y);
		Spowerup.loadGraphic("shieldpowerup.png", true, true, 16, 16); 
		Spowerup.immovable = true;
		//	Spowerup.visible = false;
		Spowerupp.add(Spowerup);
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
	

	public void createWall(int X, int Y){
		FlxSprite wall = new FlxSprite(X,Y);
		wall.loadGraphic("grassWall.png", true, true, 16, 16);
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
	
	public void createLava(int X, int Y){
		Lava lava = new Lava(X,Y);
		if(switchS){
			lava.burnSprite(jplayer);
		}else{
			lava.burnSprite(_player);
		}
		lavablocks.add(lava);
	}

	public void createSkeleton(int X, int Y,int distance){
		Skeleton skeleton = new Skeleton(X,Y,16,16,_skelGibs);
		skeleton.setDistance(distance);
		skeleton.watchLevel(level);
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
			_tbullets.active = false;
			skeletons.active = false;
			_skelGibs.active = false;
			crushers.active = false;
			_bullets.active = false;
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
			turrets.active = true;
			_tbullets.active = true;
			_bullets.active = true;
			skeletons.active = true;
			_skelGibs.active = true;
			crushers.active = true;
			enemies.active=true;
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

		/*if(!complete)
			shieldTimeTxt.setText("Shield: " + (int)shieldtimer.getTimeLeft());*/ 
			
			if(!portalcoin.exists && !portalcoin2.exists){
				portal.exists = true;
				walls.kill();
			}
		

		super.update();					  
		/*ENEMY COLLISIONS*/
		if(switchS){
			FlxG.collide(enemies,spikes);
			FlxG.collide(enemies,turrets);
			FlxG.collide(enemies,level);
			FlxG.overlap(enemies,jplayer,doPlayerDamage);
			FlxG.collide(enemies, _bullets,doEnemyDamage);
			FlxG.collide(walls, enemies);

			FlxG.collide(crushers,_bullets,doCrusherDamage);

			FlxG.collide(turrets,level);
			FlxG.collide(jplayer,_tbullets,doTPlayerDamage);
			FlxG.collide(_tbullets,level);

			//	FlxG.collide(checkpt,_player,updatePcoords);
			FlxG.overlap(jplayer, portalcoin, getPCoin);
			FlxG.overlap(jplayer,portalcoin2,getP2Coin);
			FlxG.overlap(spikes,jplayer,doPlayerDamage);
			FlxG.overlap(portal,jplayer,win);
			FlxG.collide(_skelGibs,spikes);
			FlxG.collide(skeletons,spikes);
			FlxG.collide(skeletons,level);
			FlxG.overlap(skeletons,jplayer,doPlayerDamage);
			FlxG.collide(level,jplayer);
			FlxG.collide(jplayer,walls);
			FlxG.overlap(lavablocks,jplayer,doPlayerDamage);
			FlxG.collide(_bullets, skeletons, doSkeletonDamage);
			FlxG.collide(_skelGibs,jplayer,doPlayerDamage);
			FlxG.collide(level, _bullets);
			FlxG.collide(spikes, _bullets);
			FlxG.collide(level,crushers);
			FlxG.overlap(crushers,jplayer,doCrushPlayer);
			FlxG.collide(_bullets, turrets, doTurretDamage);
			FlxG.collide(enemies,enemies);		
			FlxG.collide(enemies,walls);
			FlxG.collide(_skelGibs,level);
		}else{
		FlxG.collide(enemies,spikes);
		FlxG.collide(enemies,turrets);
		FlxG.collide(enemies,level);
		FlxG.overlap(enemies,_player,doPlayerDamage);
		FlxG.collide(enemies, _bullets,doEnemyDamage);
		FlxG.collide(walls, enemies);

		FlxG.collide(crushers,_bullets,doCrusherDamage);

		FlxG.collide(turrets,level);
		FlxG.collide(_player,_tbullets,doTPlayerDamage);
		FlxG.collide(_tbullets,level);

	//	FlxG.collide(checkpt,_player,updatePcoords);
		FlxG.overlap(_player, portalcoin, getPCoin);
		FlxG.overlap(_player,portalcoin2,getP2Coin);
		FlxG.overlap(spikes,_player,doPlayerDamage);
		FlxG.overlap(portal,_player,win);
		FlxG.collide(skeletons,spikes);
		FlxG.collide(skeletons,level);
		FlxG.overlap(skeletons,_player,doPlayerDamage);
		FlxG.collide(level,_player);
		FlxG.collide(_player,walls);
		FlxG.overlap(lavablocks,_player,doPlayerDamage);
		FlxG.collide(_bullets, skeletons, doSkeletonDamage);
		FlxG.collide(_skelGibs,spikes);
		FlxG.collide(_skelGibs,_player,doPlayerDamage);
		FlxG.collide(level, _bullets);
		FlxG.collide(spikes, _bullets);
		FlxG.collide(level,crushers);
		FlxG.overlap(crushers,_player,doCrushPlayer);
		FlxG.collide(_bullets, turrets, doTurretDamage);
		FlxG.collide(enemies,enemies);		
		FlxG.collide(enemies,walls);
		FlxG.collide(_skelGibs,level);
		}
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

	IFlxTimer useShield = new IFlxTimer()
	{
		@Override
		public void callback(FlxTimer flxTimer)
		{
			if(switchS){
			jplayer.health = 1;
			}else{
			_player.health = 1;
			}
			complete = true;
			shieldTimeTxt.visible = false;
		}
	}; 

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
			jplayer.health = 100;
			}else{
			_player.health = 100;
			}
			shieldTimeTxt.visible = true;
			shieldtimer.start(10,1,useShield);
			//		shieldTimeTxt.setText("Shield: " + shieldtimer.getTimeLeft());
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
		public void callback(FlxObject Player, FlxObject Portalcoin)
		{						
	        Portalcoin.exists = false;	
			FlxG.play("Pcoin.mp3",1.0F,false);
		//	portal.exists = true;	
		//	status.setText("SCORE: "+(pcoins.countDead()*100));	  		
			//	_player.x = 220;
	    }
	};			
	
	IFlxCollision getP2Coin = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Player, FlxObject Portalcoin2)
		{						
	        Portalcoin2.exists = false;
			FlxG.play("Pcoin.mp3",1.0F,false);
			//	portal.exists = true;	
			//	status.setText("SCORE: "+(pcoins.countDead()*100));	  		
			//	_player.x = 220;
	    }
	};					
	
	IFlxCollision updatePcoords = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject CheckPt, FlxObject Player)
		{						
			//     checkpt.play("signalGreen");
			//	isSignalGreen = true;
			/*		checkptAnimation.data.put("greenAnimation",true);
			 checkptAnimation.flush();*/
			 walls.kill();
			 pcoins.kill();
			checkpt.play("signalGreen");
			checkpt.setSolid(false);
	//		pcoins.kill();
	//		walls.kill();
			coordsIsUpdated = true;
			checkpt2Save.data.put("Coords", true);
			checkpt2Save.flush();
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

			if(progress >= 10){
				FlxG.switchState(new PlayState9());
			}else{
				

			//Save

			gameSave.data.put("Progress", 10);
			gameSave.flush();

			FlxG.switchState(new PlayState9());
		}
		}
	}; 

	/*private void wrap(FlxObject obj)
	 {
	 obj.x = (obj.x + obj.width / 2 + 800) % 800 - obj.width / 2;
	 obj.y = (obj.y + obj.height / 2) % 480 - obj.height / 2;
	 }*/
}
