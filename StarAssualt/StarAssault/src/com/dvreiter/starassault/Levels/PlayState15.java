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

public class PlayState15 extends FlxState
{	

	private static final int TILE_WIDTH = 16;
	private static final int TILE_HEIGHT = 16;

	public FlxTilemap level;
	public FlxSprite portal;	
	public FlxGroup coins;
	public FlxGroup walls;
	public FlxSprite portalcoin;
	public FlxGroup spikes;	   
	public FlxText status,getZoom;
	public FlxSprite coin;
	public FlxSprite wallSwitch;
	public FlxText pausedTxt,popText,shieldTimeTxt,DestroySpawners,popSwitch;

	public FlxGroup enemies;
	public FlxGroup skeletons;

	private JoyStickPlayer jplayer;
	Player _player;
	FlxVirtualPad pad;
	protected FlxGroup _bullets, Spowerupp;
	protected FlxEmitter _littleGibs;
	protected FlxEmitter _skelGibs;
	protected FlxGroup _tbullets,spawners;
	private FlxNinePatchButton closebtn;
	private int lavaX,lavaX2,lavaX3,lavaY,lavaY2,lavaY3,spikeX,spikeX2,crushX,crushX2;

	private FlxTimer timer, shieldtimer,timerSpawner,timerS;

	public FlxTileblock pauseblock;
	//private FlxSprite Pausebtn;
	private FlxSprite wallPortalIn,wallPortalOut;
	private FlxButton Exitbtn;
	private FlxButton restartbtn;
	private FlxButton Resumebtn;
	private FlxButton Pausebtn;
	private FlxSave gameSave,guiMusic,jswitchSave;
	private boolean complete;
	private Mage mage1;
	private Silverfish sFish;
	private Bat bat;
	private FlxGroup _balls, crushers, turrets, slimes,mages,bats,lavablocks;
	private FlxAnalog analog;
	private float playerxU,playeryU;
	private boolean switchS;

	@Override
	public void create()
	{						
		FlxG.setFramerate(60);
		FlxG.setFlashFramerate(60);

		FlxG.setBgColor(0x4c4c4c);//6c767c
		FlxG.mouse.show();

		int[] data={2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,			
			2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
			2,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,2,	
			2,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,2,
			2,2,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,2,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,2,0,0,0,0,0,0,2,2,2,
			2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,2,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,2,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,//Middle or half of y of the level map.
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,			
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,                                                  
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2, 
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,2,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,2,
			2,2,0,0,2,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,2,0,0,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,
			2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
			2,2,0,0,2,0,0,2,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,2,0,0,0,2,0,0,2,0,0,2,2,
			2,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,2,
			2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,	
			2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,	
			2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,	
		};


		level = new FlxTilemap();
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 50), "tilemap.png", TILE_WIDTH, TILE_HEIGHT); 
		add(level);

		//FlxG.playMusic("Arabesque.mp3", 1f, true, true);

		lavablocks = new FlxGroup();

		lavaX = 128;
		lavaY = 448;
		lavaX2 = 128;
		lavaY2 = 464;
		lavaX3 = 128;
		lavaY3 = 480;
		for(int i = 0; i < 34; i++){
			createLava(lavaX,lavaY);
			lavaX+=16;
			createLava(lavaX2,lavaY2);
			lavaX2+=16;
			createLava(lavaX3,lavaY3);
			lavaX3+=16;
		}

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

		portal = new FlxSprite(400, 368);//50
		portal.loadGraphic("portal.png", true, true, 16, 16);
		portal.addAnimation("spinning", new int[]{0, 1, 2}, 6, true);
		portal.play("spinning");
		portal.immovable = true;
		portal.exists = false;
		add(portal);

		/*	wallSwitch = new FlxSprite(160,384);//750,64
		 wallSwitch.loadGraphic("switch.png", true, true, 16, 16);
		 wallSwitch.addAnimation("off",new int[]{0});
		 wallSwitch.addAnimation("on",new int[]{1});
		 wallSwitch.play("off");
		 wallSwitch.immovable = true;
		 add(wallSwitch);*/

		portalcoin = new FlxSprite(400, 272);//first (170, 80) Second (170, 160)
		portalcoin.loadGraphic("Portalcoin.png", true, true, 16, 16);
		portalcoin.addAnimation("rotate", new int[]{0, 1, 2}, 4, true);
		portalcoin.play("rotate");
	// 	portalcoin.exists = false;
		add(portalcoin);

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

			jplayer = new JoyStickPlayer(400,240,16,16,_bullets,_littleGibs, pad,analog);
			//	jplayer.setHasGravityToggle(true);
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

			_player = new Player(400,240,16,16,_bullets,_littleGibs, pad);
			//		_player.setHasGravityToggle(true);
			FlxG.camera.follow(_player, FlxCamera.STYLE_PLATFORMER);
			FlxG.camera.setBounds(0,0,800,480,true);// 1st 400,240  2nd 800,240, 3rd 1200,480//status = new FlxText(2,2,100, "SCORE: ";

			add(_bullets) ;
			add(_player) ;
			add(_littleGibs); 

		}
		
		//_player.setHasGravityToggle(true);
		//	_player.setHasFlyingToggle(false);

		/*	enemies = new FlxGroup();
		 createEnemy(200,280,500);
		 //createEnemy(768,32,500);
		 add(enemies);*/
		 _balls = new FlxGroup();

		timerS = new FlxTimer();
		popSwitch = new FlxText(400, 230, 200, "Destroy Spawners!");
		popSwitch.setSize(6);
		//	popSwitch.scrollFactor.x = popSwitch.scrollFactor.y =0;
		popSwitch.visible = true;
		timerS.start(3,1,showDSText);
		add(popSwitch);
		

		mages = new FlxGroup();
		createMage(160,304);
		createMage(672,304);
		createMage(64,128);
		createMage(736,128);
		add(mages);

		bats = new FlxGroup();
		add(bats);

		spawners = new FlxGroup();
		createSpawner(64,400);
		createSpawner(720,400);
		createSpawner(64,64);
		createSpawner(720,64);
		add(spawners);


		/*	slimes = new FlxGroup();
		 createSlime(600,200,200);
		 createSlime(500,200,200);
		 createSlime(400,200,200);
		 //	createSlime(560,280,200);
		 add(slimes);*/

		skeletons = new FlxGroup();
		createSkeleton(400,304);
		createSkeleton(192,128);
		createSkeleton(608,128);
		//	createSkeleton(640,368);
		//createSkeleton(400,264);
		//	createSkeleton(768,32);
		add(skeletons);

		_tbullets = new FlxGroup();
		//x=648
		turrets = new FlxGroup();
		createTurret(32,128);//208
		//	createTurret(220,64,0);
		createTurret(752,128);
		// 	createTurret(576,368);
		//	createTurret(480,96);
		add(turrets);

		crushers = new FlxGroup();


		createCrusher(352,96,48);
		createCrusher(448,96,48);
		//	 createCrusher(432,208,32);

		/*	 crushX = 176;
		 for(int i = 0; i < 7; i++){
		 createCrusher(crushX,32,112);
		 crushX+=16;
		 }
		 crushX2 = 528;
		 for(int i = 0; i < 7; i++){
		 createCrusher(crushX2,32,112);
		 crushX2+=16;
		 }*/
		add(crushers);

		/*	Spowerupp = new FlxGroup();
		 createSpowerup(600,140);
		 add(Spowerupp);*/

		/*coins = new FlxGroup(); 			
		 //bottom coins	
		 createCoin(105, 300);//first (105, 115) second (210, 230)
		 //	createCoin(140, 390);
		 add(coins);*/

		spikes = new FlxGroup();
		createSpike(176,672,180,400);//first (80, 129) second (160, 250)
		createSpike(240,672,180,400);
		createSpike(624,672,180,400);
		createSpike(560,672,180,400);

		spikeX = 128;
		for(int i = 0; i < 10; i++){
			createSpike(spikeX,176,180,160);
			spikeX += 16;
		}

		spikeX2 = 528;
		for(int i = 0; i < 10; i++){
			createSpike(spikeX2,176,180,160);
			spikeX2+=16;
		}
		add(spikes);

		/*getZoom = new FlxText(300,2,100, "Zoom: ");
		 getZoom.setShadow(0xff000000);
		 getZoom.setText("Zoom: " + (int)FlxG.camera.getZoom());
		 getZoom.scrollFactor.x = getZoom.scrollFactor.y = 0;
		 add(getZoom);*/

		/*	status = new FlxText(2,2,100, "SCORE: ");
		 status.setShadow(0xff000000);
		 status.setText("SCORE: "+(coins.countDead()*100));
		 status.scrollFactor.x = status.scrollFactor.y = 0;
		 add(status);

		 shieldTimeTxt = new FlxText(200,2,100, "Shield: ");
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

		add(_tbullets);
		add(_balls);
		add(_skelGibs);
	}

	public void createSpawner(int X, int Y){
		Spawner spawner = new Spawner(X,Y,380,bats,8);
		if(switchS){
			spawner.getJPlayer(jplayer);
		}else{
			spawner.getPlayer(_player);
		}
		spawners.add(spawner);
		//	enemy.followSprite(_player);
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

	public void createLava(int X, int Y){
		Lava lava = new Lava(X,Y);
		if(switchS){
		lava.burnSprite(jplayer);
		}else{
		lava.burnSprite(_player);
		}
		lavablocks.add(lava);
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

	public void createBat(int X, int Y){
		Bat bat = new Bat(X,Y,16,16,1);
		if(switchS){
			bat.followSprite(jplayer);
		}else{
			bat.followSprite(_player);
		}
		bats.add(bat);
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
		enemy.followSprite(_player);
		enemies.add(enemy);
	}

	public void createSkeleton(int X, int Y){
		Skeleton skeleton = new Skeleton(X,Y,16,16,_skelGibs);
		skeleton.setDistance(2);
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
			mages.active = false;
			_balls.active = false;
			spawners.active = false;
			bats.active = false;
			_bullets.active = false;
			skeletons.active = false;
			_skelGibs.active = false;
			turrets.active = false;
			_tbullets.active = false;
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
			mages.active = true;
			_balls.active = true;
			_bullets.active = true;
			spawners.active = true;
			bats.active = true;
			skeletons.active = true;
			_skelGibs.active = true;
			turrets.active = true;
			_tbullets.active = true;
			crushers.active = true;
			//	enemies.active=true;
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

		if(spawners.countLiving() == 0){
			portalcoin.visible = true;
		}else{
			portalcoin.visible = false;
		}

		//	if(!complete)
		//		shieldTimeTxt.setText("Shield: " + (int)shieldtimer.getTimeLeft());

		super.update();					  
		/*ENEMY COLLISIONS*/
		/*	FlxG.collide(enemies,spikes);
		 FlxG.collide(enemies,level);
		 FlxG.overlap(enemies,_player,doPlayerDamage);
		 FlxG.collide(enemies, _bullets,doEnemyDamage);
		 FlxG.collide(walls, enemies);*/
		 if(switchS){
			 FlxG.collide(crushers,_bullets,doCrusherDamage);

			 FlxG.collide(mages,level);
			 //	FlxG.collide(mages,spikes);
			 FlxG.collide(_balls,spikes);
			 FlxG.collide(_balls,level);
			 FlxG.collide(jplayer,_balls,doPlayerDamage);
			 FlxG.collide(jplayer,mages,doPlayerDamage);
			 FlxG.collide(_bullets,mages,doMageDamage);

			 FlxG.collide(turrets,level);
			 FlxG.collide(jplayer,_tbullets,doTPlayerDamage);
			 FlxG.collide(_tbullets,level);

			 /*	FlxG.collide(slimes,level);
			  FlxG.collide(slimes,_player,doPlayerDamage);
			  FlxG.collide(slimes,_bullets, doSlimeDamage);

			  FlxG.overlap(Spowerupp,_player,getPP);*/

			 FlxG.collide(spawners,jplayer,doPlayerDamage);
			 FlxG.collide(spawners, _bullets,doEnemyDamage);
			 //	FlxG.overlap(coins,_player,getCoin);	
			 FlxG.overlap(portalcoin, jplayer, getPCoin);
			 //	FlxG.collide(walls,_player);
			 //	FlxG.collide(walls,bats);
			 //	FlxG.collide(wallSwitch, _player, switchOn);
			 FlxG.overlap(spikes,jplayer,doPlayerDamage);
			 FlxG.overlap(portal,jplayer,win);
			 //	FlxG.overlap(wallPortalIn,_player,teleport);
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
			 FlxG.overlap(lavablocks,jplayer,doPlayerDamage);
			 //	FlxG.collide(enemies,enemies);		
			 /*	FlxG.collide(slimes,slimes);
			  FlxG.collide(slimes, spikes);*/
			 FlxG.collide(_skelGibs,level);
			 FlxG.collide(bats,level);
			 FlxG.collide(bats,jplayer,doPlayerDamage);
			 FlxG.collide(_bullets,bats,doTurretDamage);
			 
		 }else{
		FlxG.collide(crushers,_bullets,doCrusherDamage);

		FlxG.collide(mages,level);
		//	FlxG.collide(mages,spikes);
		FlxG.collide(_balls,spikes);
		FlxG.collide(_balls,level);
		FlxG.collide(_player,_balls,doPlayerDamage);
		FlxG.collide(_player,mages,doPlayerDamage);
		FlxG.collide(_bullets,mages,doMageDamage);

		FlxG.collide(turrets,level);
		FlxG.collide(_player,_tbullets,doTPlayerDamage);
		FlxG.collide(_tbullets,level);

		/*	FlxG.collide(slimes,level);
		 FlxG.collide(slimes,_player,doPlayerDamage);
		 FlxG.collide(slimes,_bullets, doSlimeDamage);

		 FlxG.overlap(Spowerupp,_player,getPP);*/

		FlxG.collide(spawners,_player,doPlayerDamage);
		FlxG.collide(spawners, _bullets,doEnemyDamage);
		//	FlxG.overlap(coins,_player,getCoin);	
		FlxG.overlap(portalcoin, _player, getPCoin);
		//	FlxG.collide(walls,_player);
		//	FlxG.collide(walls,bats);
		//	FlxG.collide(wallSwitch, _player, switchOn);
		FlxG.overlap(spikes,_player,doPlayerDamage);
		FlxG.overlap(portal,_player,win);
		//	FlxG.overlap(wallPortalIn,_player,teleport);
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
		FlxG.overlap(lavablocks,_player,doPlayerDamage);
		//	FlxG.collide(enemies,enemies);		
		/*	FlxG.collide(slimes,slimes);
		 FlxG.collide(slimes, spikes);*/
		FlxG.collide(_skelGibs,level);
		FlxG.collide(bats,level);
		FlxG.collide(bats,_player,doPlayerDamage);
		FlxG.collide(_bullets,bats,doTurretDamage);
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

	IFlxTimer showDSText = new IFlxTimer()
	{
		@Override
		public void callback(FlxTimer flxTimer)
		{
			popSwitch.visible = false;
		}
	}; 

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
			if(switchS){
			jplayer.setHasGravityToggle(false);
			}else{
			_player.setHasGravityToggle(false);
			}
			pad.buttonX.setAlpha(0.15f);
			popText.setText("X Button Off!");
			popText.visible = true;
			timer.start(2,1,showPText);
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
		/*	//saving stuff
			gameSave = new FlxSave();
			gameSave.bind("Test");

			//Save

			gameSave.data.put("Progress", 13);
			gameSave.flush();*/

			FlxG.switchState(new MenuState());
		}

	}; 

	/*private void wrap(FlxObject obj)
	 {
	 obj.x = (obj.x + obj.width / 2 + 800) % 800 - obj.width / 2;
	 obj.y = (obj.y + obj.height / 2) % 480 - obj.height / 2;
	 }*/
	}
	/*
	 int[] data={2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,			
	 2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
	 2,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,	
	 2,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
	 2,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
	 2,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
	 2,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,0,0,0,0,2,0,0,0,2,2,
	 2,2,0,0,2,2,2,2,0,0,0,0,0,2,0,0,0,0,0,2,2,2,2,2,0,0,0,2,2,2,2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,0,0,2,2,
	 2,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,2,
	 2,2,0,0,0,0,0,2,0,0,0,0,0,2,2,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,2,
	 2,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,2,0,0,0,0,2,2,
	 2,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,2,
	 2,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,0,0,0,2,0,0,0,0,2,2,
	 2,2,2,2,2,0,0,2,0,0,0,0,2,2,2,2,0,0,0,2,2,2,2,2,0,0,0,2,2,2,2,2,2,0,0,2,2,2,2,2,2,2,2,2,0,0,2,2,2,2,
	 2,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,2,//Middle or half of y of the level map.
	 2,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,0,2,0,0,0,2,0,0,0,0,0,2,0,0,0,0,2,2,			
	 2,2,0,0,0,0,0,2,0,2,2,2,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,2,0,2,0,0,0,0,2,2,                                                  
	 2,2,0,0,0,0,0,2,0,0,0,0,0,2,2,2,0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,2,0,0,2,0,0,2,0,0,0,0,0,2,0,0,0,0,2,2, 
	 2,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,2,0,0,0,0,2,0,0,0,0,2,2,
	 2,2,0,0,2,2,2,2,2,0,0,0,0,2,0,0,0,2,2,2,2,2,2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,2,2,2,0,0,2,2,
	 2,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,2,2,
	 2,2,0,0,0,0,0,2,0,0,0,0,0,2,2,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,2,2,
	 2,2,0,0,0,0,0,2,0,2,2,2,0,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,2,0,0,2,0,0,2,0,0,0,0,2,2,
	 2,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,2,2,
	 2,2,2,2,2,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,2,0,0,0,0,2,2,0,0,0,0,0,2,0,0,0,0,2,2,
	 2,2,0,0,0,0,0,2,2,2,2,2,2,2,2,0,0,0,2,2,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,2,
	 2,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,2,2,
	 2,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,2,2,
	 2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
	 2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
	 2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,	
	 };

	 */











