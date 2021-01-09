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
import android.widget.*;
import com.dvreiter.starassault.Liquids.*;
import com.dvreiter.starassault.Tools.*;

public class PlayState10 extends FlxState
{	

	private static final int TILE_WIDTH = 16;
	private static final int TILE_HEIGHT = 16;

	/*private FlxObject highlightbox;
	 private int action;
	 private static final int ACTION_IDLE = 0;*/

	public FlxButton pause;
	public FlxTilemap level;
	public FlxTileblock pauseblock;
	public FlxSprite portal,checkpt;
	public FlxSprite hearts;
	public FlxGroup coins,pcoins;
	public FlxGroup walls;
	public FlxSprite portalcoin;
	public FlxGroup spikes;	   
	public FlxText status,popTextX;
	public FlxText goal;
	public FlxSprite wallSwitch;
	public FlxText _fps, pausedTxt;
	public FlxSprite elevator, pusher;

	public FlxGroup sfishs;
	private boolean complete,completeIP,coordsIsUpdated,isSignalGreen,musicStop;
	private int spikeX = 208,spikeX2 = 576;
	
	private FlxNinePatchButton closebtn;
	private FlxSave gameSave,checkptSave,bossmusicSave,guiMusic,jswitchSave;
	private JoyStickPlayer jplayer;
	TreeBoss tboss;
	Crawler crawler;
	Spawner espawner;
	Player player;
	Enemy enemy;
	Spike spike;
	FlxVirtualPad pad;
	public  FlxGroup _bullets, _rocks;
	protected FlxEmitter _littleGibs, _rockGibs,_spitGibs, _skelGibs;
	//protected FlxEmitter _skelGibs;
	private FlxGroup enemies, spawners, silverfishes, skeletons,slimes,_balls,turrets,lavablocks,crushers,Spowerupp,_tbullets,applebats,Ipowerupp,checkpts;

	private FlxText shieldTimeTxt,popText,popTextIP,freezeTimeTxt,bosshpTxt;
	private FlxTimer timer,shieldtimer,timerIP,freezeTimer,timerX;
	//	private FlxButton Pausebtn;
	//private FlxSprite Pausebtn;
	private FlxButton Exitbtn;
	private FlxButton restartbtn;
	private FlxButton Resumebtn;
	private FlxAnalog analog;
	private boolean switchS;
	private ErrorReporter error = new ErrorReporter();

	//private FlxSave gameSave;

	@Override
	public void create()
	{						
		FlxG.setFramerate(60);
		FlxG.setFlashFramerate(60);

	//	FlxG.setBgColor(0x4c4c4c);//6c767c
		FlxG.setBgColor(0xFF00CCFF);
		FlxG.mouse.show();

		//	action = ACTION_IDLE;

		int[] data={7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,
			7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,	
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,7,7,
			7,7,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,1,1,1,1,1,7,7,0,0,7,7,1,1,1,1,1,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,7,7,
			7,7,1,1,0,0,1,1,1,1,1,1,0,0,1,1,1,7,0,0,0,0,7,7,0,0,7,7,0,0,0,0,7,1,1,1,0,0,1,1,1,1,1,1,0,0,1,1,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,7,7,0,0,7,7,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,7,7,0,0,7,7,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,0,0,7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,0,0,7,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,
			7,7,0,0,0,0,0,0,7,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,7,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,1,1,0,0,1,1,1,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,1,1,1,0,0,1,1,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,7,
			7,7,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,7,7,
			7,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,			
			7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,
			7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7
			};

		level = new FlxTilemap();
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 50), "tilemap.png", TILE_WIDTH, TILE_HEIGHT);
		add(level);
	/*	bossmusicSave = new FlxSave();
		bossmusicSave.bind("bmusicSave");
		
		if(bossmusicSave.data.get("bmusic",boolean.class) == null)
		{
			bossmusicSave.data.put("bmusic", false);
			bossmusicSave.flush();
		}

		//load
		@SuppressWarnings("unchecked")
			boolean bossmusicCheckStop = bossmusicSave.data.get("bmusic", boolean.class);

		if(bossmusicCheckStop == false){
			
		}else if (bossmusicCheckStop == true){
			FlxG.mute = false;
		}*/
		
		FlxG.playMusic("HeroicDemise.mp3", 1f, true, true);
		
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
		
		_rockGibs = new FlxEmitter();
	//	_rockGibs.setXSpeed(-150,150);
		_rockGibs.setYSpeed(-50,50);//-200,0
	//	_rockGibs.setRotation(-720,-720);
		_rockGibs.gravity = 700;
		_rockGibs.bounce = 0.6f;
		_rockGibs.makeParticles("treegibs.png",100,10,true,0.5f);
		
		_spitGibs = new FlxEmitter();
	//	_spitGibs.setXSpeed(100,200);
		_spitGibs.setYSpeed(-50,50);
	//	_spitGibs.setRotation(-720,-720);
		_spitGibs.gravity = 300;//200 too op,250 current,350?
		_spitGibs.bounce = .7f;//.8f too op also current,.6?
		_spitGibs.makeParticles("treegibs.png",100,10,true,0.5f);

	/*	analog = new FlxAnalog(50, 200);
		analog.setAlpha(.75f);
		analog.setAll("scrollFactor", new FlxPoint(0, 0));
		add(analog);*/
		
		 
	/*	checkptAnimation = new FlxSave();
		checkptAnimation.bind("checkptGreen");*/
		checkptSave = new FlxSave();
		checkptSave.bind("checkptCoords");
		

		
		
		if(checkptSave.data.get("Coords",boolean.class) == null)
		{
			checkptSave.data.put("Coords", false);
			checkptSave.flush();
		}
		
		//load
		@SuppressWarnings("unchecked")
			boolean playercoordsCheck = checkptSave.data.get("Coords", boolean.class);

		if(playercoordsCheck == false){
			coordsIsUpdated = false;
		}else if (playercoordsCheck == true){
			coordsIsUpdated = true;
		}
	
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

			if(coordsIsUpdated){
				jplayer = new JoyStickPlayer(480,128,16,16,_bullets,_littleGibs, pad,analog);
				jplayer.setHasGravityToggle(true);
			}else{
				jplayer = new JoyStickPlayer(32,64,16,16,_bullets,_littleGibs, pad,analog);
				jplayer.setHasGravityToggle(true);

			}
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

			if(coordsIsUpdated){
				player = new Player(480,128,16,16,_bullets,_littleGibs, pad);
				player.setHasGravityToggle(true);
			}else{
				player = new Player(32,64,16,16,_bullets,_littleGibs, pad);
				player.setHasGravityToggle(true);

			}
			FlxG.camera.follow(player, FlxCamera.STYLE_PLATFORMER);
			FlxG.camera.setBounds(0,0,800,480,true);// 1st 400,240  2nd 800,240, 3rd 1200,480//status = new FlxText(2,2,100, "SCORE: ";

			add(_bullets) ;
			add(player) ;
			add(_littleGibs); 

		} 
	
		_rocks = new FlxGroup();
		
		tboss = new TreeBoss(400,350,37,64,_rocks,_rockGibs,_spitGibs);
		tboss.WatchLevel(level);
		if(switchS){
		tboss.followSprite(jplayer);
		}else{
		tboss.followSprite(player);
		}
		add(tboss);
		
		portal = new FlxSprite(tboss.x, tboss.y);//50
		portal.loadGraphic("portal.png", true, true, 16, 16);
		portal.addAnimation("spinning", new int[]{0, 1, 2}, 6, true);
		portal.play("spinning");
		portal.immovable = true;
		portal.exists = false;
		add(portal);
		
		walls = new FlxGroup();
		createWall(368,112);
		createWall(368,128);
		createWall(416,112);
		createWall(416,128);
		add(walls);

		enemies = new FlxGroup();
	//	silverfishes = new FlxGroup();
	//	skeletons = new FlxGroup();
	//	slimes = new FlxGroup();
		//spawners = new FlxGroup();
		//createSpawner(500,350);
		//add(spawners);
	//	add(slimes);
		createEnemy(800-96,48,-500);
		createEnemy(800-112,64,500);
	 	add(enemies);
	//	add(silverfishes);
	//	add(skeletons);
	//	add(_skelGibs);
		_tbullets = new FlxGroup();
		turrets = new FlxGroup();
		createTurret(32,480-160);
		createTurret(800-48,480-160);
		
		createTurret(144,128);
		createTurret(800-160,128);
		
		createTurret(144,176);
		createTurret(800-160,176);
		add(turrets);
		
		pcoins = new FlxGroup();
		createPCoin(320,176);
		createPCoin(464,176);
		add(pcoins);
		
		skeletons = new FlxGroup();
		createSkeleton(64,208,2);
		createSkeleton(800-64,208,2);
		
		createSkeleton(130,80,1);
		createSkeleton(258,80,1);
		createSkeleton(388,80,1);
		createSkeleton(515,80,8);
		add(skeletons);
		
		applebats = new FlxGroup();
		add(applebats);
		
		Ipowerupp = new FlxGroup();
		createPpowerup(64,480-130);
		add(Ipowerupp);
		
		timerX = new FlxTimer();
		//	shieldtimer = new FlxTimer();

		popTextX = new FlxText(pad.buttonX.x, pad.buttonX.y-20, 200, "X Button On!");
		popTextX.setSize(6);
		popTextX.scrollFactor.x = popTextX.scrollFactor.y = 0;
		popTextX.visible = true;
		timerX.start(2,1,showPXText);
		add(popTextX);
		

		timerIP = new FlxTimer();

		popTextIP = new FlxText(80, 80, 200, "Freeze!");
		popTextIP.setSize(6);
		popTextIP.visible = false;
		add(popTextIP);
		
		checkpt = new FlxSprite(464,128);
		checkpt.loadGraphic("flag.png", true, true, 16, 16);
		checkpt.addAnimation("signalGreen",new int[]{0,1,2},4,true);
		checkpt.addAnimation("signalRed",new int[]{3,4},4,true);
		checkpt.immovable = true;
		checkpt.play("signalRed");
		add(checkpt);
		
		Spowerupp = new FlxGroup();
		createSpowerup(600,480-130);
		add(Spowerupp);

		//crushers = new FlxGroup();
		//	createCrusher(696,400);
		//	add(crushers);*/ 

	/*	walls = new FlxGroup();
		createWall(704,96);
		createWall(688,96);

		createWall(80,432);
		createWall(80,416);

		createWall(0,96);
		createWall(16,96);
		add(walls);*/

	/*	coins = new FlxGroup(); 			
		//bottom coins	
		createCoin(105, 300);//first (105, 115) second (210, 230)
		//	createCoin(90, 390);
		add(coins);*/
		
		spawners = new FlxGroup();
		createSpawner(160,480-224);
		createSpawner(624,480-224);
		add(spawners);

		spikes = new FlxGroup();
	
	/*	createSpike(144,32,180,false);
		createSpike(208,32,180,false);
		createSpike(272,32,180,false);
		createSpike(336,32,180,false);
		createSpike(400,32,180,false);
		createSpike(464,32,180,false);
		createSpike(528,32,180,false);
		createSpike(592,32,180,false);
		createSpike(656,32,180,false);*/

		createSpike(113,80,0,false);
		createSpike(177,32,180,false);
		createSpike(241,80,0,false);
		createSpike(305,32,180,false);
		createSpike(369,80,0,false);
		createSpike(433,32,180,false);
		createSpike(497,80,0,false);
		createSpike(561,32,180,false);
		createSpike(624,80,0,false);
	
		createSpike(704,32,180,true);
		createSpike(720,32,180,true);
		
		for(int i = 0; i < 11; i++){
			createSpike(spikeX,240,180,false);
			spikeX+=16;
			createSpike(spikeX2,240,180,false);
			spikeX2-=16;
		}
		add(spikes);

	/*	status = new FlxText(2,2,100, "SCORE: ");
		status.setShadow(0xff000000);
		status.setText("SCORE: "+(coins.countDead()*100));
		status.scrollFactor.x = status.scrollFactor.y = 0;
		add(status);*/
		
		bosshpTxt = new FlxText(2,2,100, "BOSS HP: ");
		bosshpTxt.setShadow(0xff000000);
		bosshpTxt.setColor(FlxG.RED);
		bosshpTxt.setText("BOSS HP: " + (int)tboss.health);
		bosshpTxt.scrollFactor.x = bosshpTxt.scrollFactor.y = 0;
		add(bosshpTxt);
		
		timer = new FlxTimer();
		shieldtimer = new FlxTimer();

		popText = new FlxText(600, 140, 200, "Shield!");
		popText.setSize(6);
		popText.visible = false;
		add(popText);

		shieldTimeTxt = new FlxText(300,2,100, "Shield: ");
		shieldTimeTxt.setShadow(0xff000000);
		//shieldTimeTxt.setText("SCORE: "+(coins.countDead()*100));
		shieldTimeTxt.scrollFactor.x = shieldTimeTxt.scrollFactor.y = 0;
		shieldTimeTxt.visible = false;
		add(shieldTimeTxt);
		
		freezeTimer = new FlxTimer();
		freezeTimeTxt = new FlxText(200,2,100, "Freeze: ");
		freezeTimeTxt.setShadow(0xff000000);
		//shieldTimeTxt.setText("SCORE: "+(coins.countDead()*100));
		freezeTimeTxt.scrollFactor.x = freezeTimeTxt.scrollFactor.y = 0;
		freezeTimeTxt.visible = false;
		add(freezeTimeTxt);

		pauseblock = new FlxTileblock(90, 50, 210, 150);//780, 400
		pauseblock.makeGraphic(210, 150, 0xff000000);// 390, 230
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

		Resumebtn = new FlxButton(160, 100, "Resume");//x.190, x.180, x.170, y.110
		Resumebtn.setSolid(false);//Coords 1: (400, 240),
		Resumebtn.immovable = true;
		Resumebtn.scrollFactor.x = Resumebtn.scrollFactor.y = 0;
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
		Exitbtn.visible = false;
		add(Exitbtn);

		closebtn = new FlxNinePatchButton(350,6,null,"||",20,20,null);
		closebtn.scrollFactor.x = closebtn.scrollFactor.y = 0;
		closebtn.exists = true;
		closebtn.visible = true;
		add(closebtn);

		//add(skeleton);
		add(_rockGibs);
		add(_spitGibs);
		add(_tbullets);
		add(_skelGibs);
	 	add(_rocks);
		
	}
	
	public void createSpawner(int X, int Y){
		Spawner spawner = new Spawner(X,Y,389,applebats,8);
		if(switchS){
		spawner.getJPlayer(jplayer);
		}else{
		spawner.getPlayer(player);
		}
		spawners.add(spawner);
	//	enemy.followSprite(_player);
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
	
	public void createPCoin(int X, int Y){
		FlxSprite portalcoin = new FlxSprite(X, Y);//first (170, 80) Second (170, 160)
		portalcoin.loadGraphic("Portalcoin.png", true, true, 16, 16);
		portalcoin.addAnimation("rotate", new int[]{0, 1, 2}, 4, true);
		portalcoin.play("rotate");
		pcoins.add(portalcoin);
	}
	
	public void createPpowerup(int X, int Y){
		FlxSprite Ipowerup = new FlxSprite(X,Y);
		Ipowerup.loadGraphic("freezePow.png", true, true, 16, 16); 
		Ipowerup.addAnimation("freeze",new int[]{0,1},4,true);
		Ipowerup.play("freeze");
		Ipowerup.immovable = true;
		Ipowerupp.add(Ipowerup);
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
			crusher.WatchSprite(player);
		}
		crushers.add(crusher);
	}


	public void createTurret(int X,int Y){
		Turret turret = new Turret(X,Y,_tbullets,2,1);
		if(switchS){
			turret.WatchSprite(jplayer);
		}else{
			turret.WatchSprite(player);
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

	public void createSpike(int X, int Y,int Angle,boolean shouldFall){
		spike = new Spike(X,Y,Angle);
		spike.getDistance(64);
		if(shouldFall){
			if(switchS){
			spike.WatchSprite(jplayer);
			}else{
			spike.WatchSprite(player);
			}
		}
		spikes.add(spike);
	}

	public void createEnemy(int X, int Y,int Accel){
		Enemy enemy = new Enemy(X,Y,16,16,Accel,1);
		if(switchS){
		enemy.followSprite(jplayer);
		}else{
		enemy.followSprite(player);
		}
		enemies.add(enemy);
	}

	public void createBat(int X,int Y){
		Bat bat = new Bat(X,Y,16,16,2);
		if(switchS){
		bat.followSprite(jplayer);
		}else{
		bat.followSprite(player);
		}
		applebats.add(bat);
	}

	public void createLava(int X, int Y){
		Lava lava = new Lava(X,Y);
		if(switchS){
			lava.burnSprite(jplayer);
		}else{
			lava.burnSprite(player);
		}
		lavablocks.add(lava);
	}
	

	public void createSkeleton(int X, int Y,int distance){
		Skeleton skeleton = new Skeleton(X,Y,16,16,_skelGibs);
		skeleton.setDistance(distance);
		skeleton.setSpriteToMoveFrom(spike);
		skeleton.watchLevel(level);
		skeletons.add(skeleton);
	}

	public void createSlime(int X, int Y, int Accel){
		Slime slime = new Slime(X,Y,16,16,Accel,1);
		if(switchS){
		slime.followSprite(jplayer);
		}else{
		slime.followSprite(player);
		}
		slimes.add(slime);
	}
	
	public void createSFish(int X, int Y){
		Silverfish sfish = new Silverfish(X,Y,16,16,500);
		sfish.watchLevel(level);
		sfish.goRight();
		sfishs.add(sfish);
	
	}
    @Override
	public void update(){						 
		try{
		if(closebtn.status == FlxNinePatchButton.PRESSED){
			spawners.active = false;
			applebats.active = false;
			skeletons.active = false;
			_skelGibs.active = false;
			turrets.active = false;
			_tbullets.active = false;
			closebtn.visible = false;
			if(switchS){
			jplayer.exists = false;
			}else{
			player.exists = false;
			}
			_rocks.active = false;
			_spitGibs.active = false;
			_bullets.active = false;
			tboss.active = false;
			pauseblock.visible = true;
			pausedTxt.visible = true;

			Resumebtn.visible = true;
			restartbtn.visible = true;
			Exitbtn.visible = true;	
			enemies.active = false;
			FlxG.music.pause();
		}
		if(Resumebtn.status == Resumebtn.PRESSED || FlxG.keys.justPressed("BACK")){
			spawners.active = true;
			applebats.active = true;
			skeletons.active = true;
			_skelGibs.active = true;
			_bullets.active = true;
			turrets.active = true;
			_rocks.active = true;
			_spitGibs.active = true;
			_tbullets.active = true;
			enemies.active=true;
			closebtn.visible = true;
			if(switchS){
			jplayer.exists = true;
			}else{
			player.exists = true;
			}
			tboss.active = true;
			Resumebtn.visible = false;
			restartbtn.visible = false;
			Exitbtn.visible = false;
			pauseblock.visible= false;
			pausedTxt.visible = false;
			FlxG.music.resume();
		}
		
		portal.x = tboss.x;
		portal.y = tboss.y;
		
		if(pcoins.countLiving() == 0){
			walls.kill();
		}
		
		if(!tboss.exists){
			portal.exists = true;
		}
		
		if(!complete)
			shieldTimeTxt.setText("Shield: " + (int)shieldtimer.getTimeLeft());
			
		if(!completeIP)
			freezeTimeTxt.setText("Freeze: " + (int)freezeTimer.getTimeLeft());
		super.update();
		/*ENEMY COLLISIONS*/
		if(switchS){
			FlxG.collide(enemies,spikes);
			FlxG.collide(enemies,level);
			FlxG.overlap(enemies,jplayer,doPlayerDamage);
			FlxG.collide(enemies, _bullets,doEnemyDamage);
			FlxG.collide(enemies,walls);

			FlxG.collide(checkpt,jplayer,updatePcoords);

			FlxG.collide(level,jplayer);
			FlxG.collide(_bullets,spikes);

			FlxG.collide(tboss,level);
			FlxG.collide(tboss,jplayer,doPlayerDamage);
			FlxG.collide(tboss,_bullets,doTbossDamage);

			FlxG.collide(applebats,level);
			FlxG.collide(applebats,_bullets,doEnemyDamage);
			FlxG.collide(jplayer,applebats,doPlayerDamage);
			/*OTHER COLLISIONS ASIDE FROM ENEMY*/
			//	FlxG.collide(walls,_player);
			//	FlxG.overlap(coins,_player,getCoin);	
			FlxG.overlap(pcoins, jplayer, getPCoin);
			//	FlxG.collide(wallSwitch,_player,switchOn);
			FlxG.overlap(spikes,jplayer,doPlayerDamage);
			FlxG.overlap(portal,jplayer,win);
			FlxG.collide(skeletons,spikes);
			FlxG.collide(skeletons,level);
			FlxG.overlap(skeletons,jplayer,doPlayerDamage);
			FlxG.collide(level, _skelGibs);
			FlxG.collide(_skelGibs,jplayer,doPlayerDamage);
			FlxG.collide(skeletons,_bullets,doEnemyDamage);
			FlxG.collide(level,_tbullets);
			FlxG.collide(_tbullets, jplayer, doPlayerDamage);
			FlxG.collide(turrets,_bullets,doEnemyDamage);
			FlxG.collide(level, _bullets);
			FlxG.collide(level,_rocks);
			FlxG.collide(_rocks,_bullets,doRockDamage);

			FlxG.collide(jplayer,walls);

			FlxG.overlap(Ipowerupp,jplayer,doOverPower);
			FlxG.collide(level,_spitGibs);
			FlxG.collide(jplayer,_spitGibs,doPlayerDamage);
			FlxG.overlap(Spowerupp,jplayer,getPP);
			FlxG.collide(walls,_bullets);
			FlxG.collide(level,enemies);
			FlxG.collide(spawners,jplayer,doPlayerDamage);
			FlxG.collide(spawners, _bullets,doEnemyDamage);
			FlxG.collide(_rocks,jplayer,doPlayerDamage);
			FlxG.collide(level,_rockGibs);
			FlxG.collide(_rockGibs,jplayer,doPlayerDamage);
			FlxG.collide(enemies,enemies);
		}else{

		FlxG.collide(enemies,spikes);
		FlxG.collide(enemies,level);
		FlxG.overlap(enemies,player,doPlayerDamage);
		FlxG.collide(enemies, _bullets,doEnemyDamage);
		FlxG.collide(enemies,walls);
		
		FlxG.collide(checkpt,player,updatePcoords);
	
		FlxG.collide(level,player);
		FlxG.collide(_bullets,spikes);
		
		FlxG.collide(tboss,level);
		FlxG.collide(tboss,player,doPlayerDamage);
		FlxG.collide(tboss,_bullets,doTbossDamage);

		FlxG.collide(applebats,level);
		FlxG.collide(applebats,_bullets,doEnemyDamage);
		FlxG.collide(player,applebats,doPlayerDamage);
		/*OTHER COLLISIONS ASIDE FROM ENEMY*/
		//	FlxG.collide(walls,_player);
	//	FlxG.overlap(coins,_player,getCoin);	
		FlxG.overlap(pcoins, player, getPCoin);
	//	FlxG.collide(wallSwitch,_player,switchOn);
		FlxG.overlap(spikes,player,doPlayerDamage);
		FlxG.overlap(portal,player,win);
		FlxG.collide(skeletons,spikes);
		FlxG.collide(skeletons,level);
		FlxG.overlap(skeletons,player,doPlayerDamage);
		FlxG.collide(level, _skelGibs);
		FlxG.collide(_skelGibs,player,doPlayerDamage);
		FlxG.collide(skeletons,_bullets,doEnemyDamage);
		FlxG.collide(level,_tbullets);
		FlxG.collide(_tbullets, player, doPlayerDamage);
		FlxG.collide(turrets,_bullets,doEnemyDamage);
		FlxG.collide(level, _bullets);
		FlxG.collide(level,_rocks);
		FlxG.collide(_rocks,_bullets,doRockDamage);
		
		FlxG.collide(player,walls);
	
		FlxG.overlap(Ipowerupp,player,doOverPower);
		FlxG.collide(level,_spitGibs);
		FlxG.collide(player,_spitGibs,doPlayerDamage);
		FlxG.overlap(Spowerupp,player,getPP);
		FlxG.collide(walls,_bullets);
		FlxG.collide(level,enemies);
		FlxG.collide(spawners,player,doPlayerDamage);
		FlxG.collide(spawners, _bullets,doEnemyDamage);
		FlxG.collide(_rocks,player,doPlayerDamage);
		FlxG.collide(level,_rockGibs);
		FlxG.collide(_rockGibs,player,doPlayerDamage);
		FlxG.collide(enemies,enemies);
	/*	FlxG.collide(sfishs,level);
		FlxG.collide(sfishs,sfishs);
		FlxG.collide(sfishs,_player,doPlayerDamage);
		FlxG.collide(sfishs,_bullets,doEnemyDamage);*/
		}
		}catch (Exception e){
			e.printStackTrace();
			error.reportError(e);
		}
	}

	@Override
	public void destroy()
	{
		super.destroy();
	/*	Pausebtn = null;
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
		pad = null;*/
		
	}	



	private void onExit(){
		FlxG.switchState(new MenuState());
	}

	private void onReset(){
		FlxG.resetState();
		FlxG.music.resume();
	}

	IFlxCollision doRockDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Rock, FlxObject Bullet)
		{									
			//		add(new FlxText(_player.x,_player.y , 100, "Test: Enemy killed."));	
			//		EnemyG.kill();
			Rock.kill();
		}
	};
	
	IFlxTimer showPXText = new IFlxTimer()
	{
		@Override
		public void callback(FlxTimer flxTimer)
		{
			popTextX.visible = false;
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
	
	IFlxTimer showIPText = new IFlxTimer()
	{
		@Override
		public void callback(FlxTimer flxTimer)
		{
			popTextIP.visible = false;
		}
	}; 
	
	IFlxCollision doOverPower = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Ipowerup, FlxObject Player1)
		{						
			//    add(new FlxText(200,22 , 100, "You Died!"));			
			Ipowerup.kill();
			//enemies.active = false;
			spawners.active = false;
			applebats.active = false;
			turrets.active = false;
			_tbullets.active = false;
			tboss.active = false;
			freezeTimeTxt.visible = true;
			freezeTimer.start(10,1,useFreeze);
		//	skeletons.active = false;
			popTextIP.visible = true;
			timerIP.start(2,1,showIPText);
	    }
	};
	
	IFlxTimer useFreeze = new IFlxTimer()
	{
		@Override
		public void callback(FlxTimer flxTimer)
		{
			spawners.active = true;
			applebats.active = true;
			turrets.active = true;
			_tbullets.active = true;
			tboss.active = true;
			completeIP = true;
			freezeTimeTxt.visible = false;
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
			player.health = 100;
			}
			shieldTimeTxt.visible = true;
			shieldtimer.start(10,1,useShield);
			//		shieldTimeTxt.setText("Shield: " + shieldtimer.getTimeLeft());
			popText.visible = true;
			timer.start(2,1,showPText);
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
			player.health = 1;
			}
			complete = true;
			shieldTimeTxt.visible = false;
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
			checkpt.play("signalGreen");
			checkpt.setSolid(false);
			pcoins.kill();
			walls.kill();
			coordsIsUpdated = true;
			checkptSave.data.put("Coords", true);
			checkptSave.flush();
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
	//		portal.exists = true;	
	//		status.setText("SCORE: "+(pcoins.countDead()*100));	  		
			//	_player.x = 220;
	    }
	};								

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
				if(player.health > 1){
					player.hurt(1);
				}else{
					player.kill();
				}
			}
		/*		FlxG.mute = true;
				bossmusicSave.data.put("bmusic", true);
				bossmusicSave.flush();*/
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
				if(player.health > 1){
					player.hurt(1);
				}else{
					player.kill();
				}
			}
	/*			FlxG.mute = true;
				bossmusicSave.data.put("bmusic", true);
				bossmusicSave.flush();*/
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
	
	IFlxCollision doTbossDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Enemy, FlxObject Bullet)
		{									
			//		add(new FlxText(_player.x,_player.y , 100, "Test: Enemy killed."));	
			//		EnemyG.kill();
			Enemy.hurt(1);
			bosshpTxt.setText("BOSS HP: " + (int)tboss.health);
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
				if(player.health > 1){
					player.hurt(1);
				}else{
					player.kill();
				}
			}
	/*			FlxG.mute = true;
				bossmusicSave.data.put("bmusic", true);
				bossmusicSave.flush();*/
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
			//	add(new FlxText(200,200, 100, "You win Level 5!"));		
			if(switchS){
			jplayer.exists = false;
			}else{
			player.exists = false;
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

			if(progress >= 12){
				FlxG.switchState(new PlayState11());
			}else{
				

			//Save

			gameSave.data.put("Progress", 12);
			gameSave.flush();
			FlxG.switchState(new PlayState11());
			}
		}
	}; 
}
