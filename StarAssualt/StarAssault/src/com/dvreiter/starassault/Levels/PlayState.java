package com.dvreiter.starassault.Levels;	

import org.flixel.FlxButton;
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
import com.dvreiter.starassault.Player.*;
import com.dvreiter.starassault.*;
import com.dvreiter.starassault.Mobs.*;
import com.dvreiter.starassault.Objects.*;
import com.badlogic.gdx.utils.*;
import com.dvreiter.starassault.Menu.*;
import com.dvreiter.starassault.LevelLoader.*;
import com.dvreiter.starassault.Tools.*;


public class PlayState extends FlxState
{	
	private ErrorReporter error;
	//public ActionResolver _actionResolver;
	public FlxButton pause;
	public FlxTilemap level;
	public FlxTileblock block;
	public FlxSprite portal;	
	public FlxSprite hearts;
	public FlxSprite Pausebtn;
	public FlxGroup coins;
	public FlxSprite portalcoin;
	public FlxGroup spikes;	   
	public FlxText status;
	public FlxText goal;
	public FlxText pausedTxt;
	//public FlxTileblock rightBorder, leftBorder;
	protected FlxEmitter _littleGibs;
	private FlxNinePatchButton closebtn;

	public FlxTileblock pauseblock;
	//private FlxButton Pausebtn;
	private FlxButton Exitbtn;
	//private FlxButton Settingsbtn;
	private FlxButton restartbtn;
	private FlxButton Resumebtn;
	private boolean hasSavedAlready,switchS;

	private FlxSave gameSave,guiMusic,levelSaveCheck,jswitchSave;
	
	private FlxVirtualPad pad;
	private Player player;
	private JoyStickPlayer jplayer;
	private Enemy enemy1;
	Mage mage1;
	Bat bat;
	//Silverfish sFish;
	
	FlxGroup _bullets;

	private FlxGroup _turretBullets;
	private FlxGroup _balls;

	private Turret turret;
    private FlxAnalog analog;
	//int playershealth;
	//int hurtwaiit
	
	/*public PlayState() {
	}
	
    public PlayState(ActionResolver actionResolver) {
		_actionResolver = actionResolver;
	}*/
	@Override
	public void create()
	{						
		FlxG.setBgColor(0xFF00CCFF);
		FlxG.mouse.show();
			int[] data = {
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,                                         
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,// changed this
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,           
			7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,
			7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,
			7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7};	
			
			/*
		 int[] data = {
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,                                         
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,4,1,1,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,0,4,1,1,5,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,4,8,8,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,// changed this
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 1,12,12,1,12,1,12,12,12,1,1,12,1,12,12,1,12,12,1,1,12,1,12,12,1,           
		 7,18,7,18,18,7,18,7,18,7,18,18,7,7,18,7,18,18,18,7,7,18,18,7,18,
		 10,10,21,21,10,21,21,10,21,10,21,21,10,21,21,10,21,21,10,21,10,21,10,21,21,
		 10,21,10,21,10,21,10,21,10,21,10,10,10,21,10,21,10,21,10,21,21,10,21,21,10};	
		 
			*/
		level = new FlxTilemap();
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 25), "tilemap.png", 16, 16);
		add(level);
	//	error = new ErrorReporter();
		// 400x240						
 	
	//	playershealth = 4;
	//	hurtwait = 0;
		
		_littleGibs = new FlxEmitter();
		_littleGibs.setXSpeed(-150,150);
		_littleGibs.setYSpeed(-200,0);
		_littleGibs.setRotation(-720,-720);
		_littleGibs.gravity = 350;
		_littleGibs.bounce = 0.5f;
		_littleGibs.makeParticles("playergibs.png",100,10,true,0.5f);
		
		portal = new FlxSprite(50, 50);//50
		portal.loadGraphic("Grass_Portal.png", true, true, 16, 16);
		portal.addAnimation("spinning", new int[]{0, 1, 2}, 6, true);
		portal.play("spinning");
		portal.immovable = true;
		portal.exists = false;
		add(portal);
		
		portalcoin = new FlxSprite(170, 80);//80
		portalcoin.loadGraphic("Portalcoin.png", true, true, 16, 16);
		portalcoin.addAnimation("rotate", new int[]{0, 1, 2}, 4, true);
		portalcoin.play("rotate");
		add(portalcoin);
/*
		hearts = new FlxSprite(170,5);
		hearts.loadGraphic("untitled.png", true, true, 36, 8);
		hearts.addAnimation("4HP", new int[]{3}, 0, false);
		hearts.addAnimation("3HP", new int[]{2}, 0, false);
		hearts.addAnimation("2HP", new int[]{1}, 0, false);
		hearts.addAnimation("1HP", new int[]{0}, 0, false);		
		hearts.setSolid(false);
		hearts.immovable = true;
		hearts.scrollFactor.x = hearts.scrollFactor.y = 0;
		hearts.play("4HP");
*/
 	/*	analog = new FlxAnalog(50, 200);
		analog.setAlpha(.75f);
		analog.setAll("scrollFactor", new FlxPoint(0, 0));
		add(analog);*/
	
	//	error.logData("Joystick Test: ");
		
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

			jplayer = new JoyStickPlayer(20,150,16,16,_bullets,_littleGibs, pad,analog);
			FlxG.camera.follow(jplayer, FlxCamera.STYLE_PLATFORMER);
			FlxG.camera.setBounds(0,0,400,240,true);// 1st 400,240  2nd 800,240, 3rd 1200,480//status = new FlxText(2,2,100, "SCORE: ";
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

			player = new Player(20,	150,16,16,_bullets,_littleGibs, pad);
			player.setHasGravityToggle(false);
			FlxG.camera.follow(player, FlxCamera.STYLE_PLATFORMER);
			FlxG.camera.setBounds(0,0,400,240,true);// 1st 400,240  2nd 800,240, 3rd 1200,480//status = new FlxText(2,2,100, "SCORE: ";

			add(_bullets) ;
			add(player) ;
			add(_littleGibs); 

		}
		
		if(switchS){
			enemy1 = new Enemy(300,60,16,16,500,1); //y = 60		
			enemy1.followSprite(jplayer);
		}else{
		enemy1 = new Enemy(300,60,16,16,500,1); //y = 60		
		enemy1.followSprite(player);
		}
		
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
		
	//	bat = new Bat(300,100,16,16);
		//bat.followSprite(player);
	//	add(bat);
		
/*		_balls = new FlxGroup();
		
		mage1 = new Mage(250,80,16,16,_balls,1);
		mage1.watchLevel(level);
		mage1.followSprite(player);
		add(mage1);*/

	/*	turret = new Turret(300,145,_turretBullets,3.0f);//.01f
		turret.WatchSprite(player);
		add(turret);
		add(_turretBullets);*/
		
	/*	leftBorder = new FlxTileblock(-16,0,16,240);
		leftBorder.makeGraphic(16,240);
		leftBorder.setSolid(true);
		leftBorder.immovable = true;
		add(leftBorder);

		rightBorder = new FlxTileblock(401,0,15,240);
		rightBorder.makeGraphic(15,240);
		add(rightBorder);*/
		
		coins = new FlxGroup(); 			
		//bottom coins	
		createCoin(105,115);//115

		add(coins);

		spikes = new FlxGroup();
		createSpike(80,128,0);//129
		createSpike(96,128,0);

		add(spikes);

		status = new FlxText(2,2,100, "SCORE: ");
		status.setShadow(0xff000000);
		status.setText("SCORE: "+(coins.countDead()*100));
		status.scrollFactor.x = 0;
		status.scrollFactor.y = 0;
		add(status);
	
		pauseblock = new FlxTileblock(90, 50, 210, 150);//780, 400
		pauseblock.makeGraphic(210, 150, 0xff000000);// 390, 230
		pauseblock.setAlpha(0.5f);
		pauseblock.setSolid(false);
		pauseblock.immovable = true;
		pauseblock.scrollFactor.x = pauseblock.scrollFactor.y = 0;
		pauseblock.visible = false;
		
		pausedTxt = new FlxText(160,65,90,"PAUSED");
		pausedTxt.setSize(19);
		pausedTxt.antialiasing = true;
		pausedTxt.scrollFactor.x = 0;
		pausedTxt.scrollFactor.y = 0;
		pausedTxt.visible = false;

		Resumebtn = new FlxButton(160, 100, "Resume");//x.190, x.180, x.170, y.110
		Resumebtn.setSolid(false);//Coords 1: (400, 240),
		Resumebtn.immovable = true;
		Resumebtn.scrollFactor.x = Resumebtn.scrollFactor.y = 0;
		Resumebtn.exists = true;
		Resumebtn.visible = false;
		
		restartbtn = new FlxButton(160, 130, "Restart", new IFlxButton(){@Override public void callback(){onReset();}});//x.190, x.180, x.170, y.130
		restartbtn.setSolid(false);//Coords 1: (400, 260), 
		restartbtn.immovable = true;
		restartbtn.scrollFactor.x = restartbtn.scrollFactor.y = 0;
		restartbtn.exists = true;
		restartbtn.visible = false;
		
		Exitbtn = new FlxButton(160, 160, "Quit Game", new IFlxButton(){@Override public void callback(){onExit();}});//x.190, x.180, x.170, y.150
		Exitbtn.setSolid(false);//Coords 1: (400, 280)
		Exitbtn.immovable = true;
		Exitbtn.scrollFactor.x = Exitbtn.scrollFactor.y = 0;
		Exitbtn.exists = true;
		Exitbtn.visible = false;
		
		closebtn = new FlxNinePatchButton(350,6,null,"||",20,20,null);
		closebtn.scrollFactor.x = closebtn.scrollFactor.y = 0;
		closebtn.exists = true;
		closebtn.visible = true;
		add(closebtn);

		/*Pausebtn =  new FlxSprite(350, 6);
		Pausebtn.loadGraphic("pauseic.png",true,true,16,16);
		Pausebtn.setSolid(false);
		Pausebtn.immovable = true;
		Pausebtn.exists = true;
		Pausebtn.scrollFactor.x = Pausebtn.scrollFactor.y = 0;*/
	
	//	add(_balls);
		add(enemy1);
	//	add(hearts);
		
		add(pauseblock);
		add(pausedTxt);
		add(restartbtn);
		add(Resumebtn);
		add(Exitbtn);
		add(Pausebtn);
		
	}
    public void createCoin(int X,int Y)
	{
		FlxSprite coin = new FlxSprite(X,Y);
		coin.loadGraphic("coinframes.png", true, true, 16, 16);
		coin.addAnimation("rotating", new int[]{0, 1, 2, 3}, 6, true);
		coin.play("rotating");
		coins.add(coin);
	}	

	public void createSpike(int X, int Y, int Angle){
		Spike spike = new Spike(X,Y,Angle);
		spikes.add(spike);
	}
	/*public void hurtplayer(){ 
		hurtwait = 240;
		if(playershealth == 4){
			FlxG.flash(0xFFFFFFFF, 2);
			hearts.play("3HP");
			playershealth = 3;
		}
		else if(playershealth == 3){
			FlxG.flash(0xFFFFFFFF, 2);
			hearts.play("2HP");
			playershealth = 2;
		}
		else if(playershealth == 2){
			FlxG.flash(0xFFFFFFFF, 2);
			hearts.play("1HP");		
			playershealth = 1;
		}
		else if(playershealth == 1){
			FlxG.flash(0xFFFFFFFF, 2);
			player.kill();
			hearts.kill();
			
			FlxG.resetState();
		}
	}
*/
	
	
    @Override
	public void update(){	
	//	try{
		if(closebtn.status == FlxNinePatchButton.PRESSED){
			closebtn.visible = false;
			pauseblock.visible = true;
			pausedTxt.visible = true;
			_bullets.active = false;
			if(switchS){
				jplayer.exists = false;
			}else{
				player.exists = false;
			}
			
			FlxG.music.pause();
			//FlxG.playMusic("Pneumatic-Tok.mp3", 1f, true, true);

			Resumebtn.visible = true;
			restartbtn.visible = true;
			Exitbtn.visible = true;	
			enemy1.active = false;
		}
		if(Resumebtn.status == Resumebtn.PRESSED || FlxG.keys.justPressed("BACK")){
			enemy1.active=true;
			_bullets.active = true;
			closebtn.visible = true;
			Resumebtn.visible = false;
			restartbtn.visible = false;
			if(switchS){
				jplayer.exists = true;
			}else{
				player.exists = true;
			}
			
			FlxG.music.resume();
		
			Exitbtn.visible = false;
			pauseblock.visible= false;
			pausedTxt.visible = false;
		}			
		
		if(switchS){
			if(jplayer.x <= 0)
				jplayer.x = 0;
			if(jplayer.x >= 382)
				jplayer.x = 382;
			if(jplayer.y <= 0)
				jplayer.y = 0;
		}else{
		if(player.x <= 0)
			player.x = 0;
		if(player.x >= 382)
			player.x = 382;
		if(player.y <= 0)
			player.y = 0;
			}
		
		if(enemy1.x <= 0)
			enemy1.x = 0;
		if(enemy1.x >= 382)
			enemy1.x = 382;
		
		
		super.update();				
		if(switchS){
			FlxG.overlap(coins,jplayer,getCoin);	
			FlxG.overlap(portalcoin,jplayer,getPCoin);
			FlxG.overlap(spikes,jplayer,doSpikeDamage);
			FlxG.overlap(portal,jplayer,win);
			FlxG.collide(enemy1,spikes);
			FlxG.collide(enemy1,level);
			FlxG.collide(enemy1,jplayer,doPlayerDamage);
			FlxG.collide(level,jplayer);
			FlxG.collide(_bullets, enemy1, doEnemyDamage);
			FlxG.collide(level, _bullets);
			FlxG.collide(spikes, _bullets);
			
		}else{
		FlxG.overlap(coins,player,getCoin);	
		FlxG.overlap(portalcoin,player,getPCoin);
		FlxG.overlap(spikes,player,doSpikeDamage);
		FlxG.overlap(portal,player,win);
		FlxG.collide(enemy1,spikes);
		FlxG.collide(enemy1,level);
		FlxG.collide(enemy1,player,doPlayerDamage);
		FlxG.collide(level,player);
		FlxG.collide(_bullets, enemy1, doEnemyDamage);
		FlxG.collide(level, _bullets);
		FlxG.collide(spikes, _bullets);
		}
	//	FlxG.collide(enemy1,leftBorder);
	//	FlxG.collide(enemy1,rightBorder);
		//		FlxG.collide(mage1,level);
		
		//FlxG.collide(rightBorder,player);
		//FlxG.collide(leftBorder,player);
	
	/*	if(player.y > FlxG.height)
		{
			FlxG.resetState();
		}*/

//		if(player.y > FlxG.height)
//		{
//			FlxG.resetState();
//			//FlxG.music.kill();
//			}
			
		
		
	/*	if(hurtwait != 0){
			hurtwait -= 1;
		}*/
	//	}catch (Exception e){
	//		e.printStackTrace();
		//	error.reportError(e);
	//	}
	}

	protected void overlapped(FlxObject object1)
	{
		if((object1 instanceof Bullet))
		object1.hurt(1);
	}

	
	@Override
	public void destroy()
	{
		// TODO: Implement this method
		super.destroy();
		pause = null;
		level = null;
		block = null;
		portal = null;
		hearts = null;
		coins = null;
		portalcoin = null;
		portal = null;
		spikes = null;
		status = null;
		goal = null;
		player = null;
		_littleGibs = null; 
		_bullets = null;
		hearts = null;
		enemy1 = null;
		pad = null;
}

	IFlxCollision getCoin = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Coin, FlxObject Player)
		{						
	        Coin.kill();
			FlxG.play("Coin.mp3",1.0F, false);
			status.setText("SCORE: "+(coins.countDead()*100));	  		
	    }
	};					

	IFlxCollision getPCoin = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Portalcoin, FlxObject Player)
		{						
	        Portalcoin.kill();	
			FlxG.play("Pcoin.mp3",1.0F, false);
			portal.exists = true;			        
	    }
	};					
	
	IFlxCollision doSpikeDamage = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Spike, FlxObject Player)
		{			
//			add(new FlxText(200,22 , 100, "You Died!"));			
			if(switchS){
			jplayer.kill();
			}else{
			player.kill();
			}
			//FlxG.music.kill();
	    }
	};		
		
    IFlxCollision doPlayerDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Enemy1, FlxObject Player)
		{									
			add(new FlxText(200,22 , 100, "You Died!"));			
			if(switchS){
			jplayer.kill();
			}else{
			player.kill();
			}
			FlxG.music.kill();
			//Enemy enemyOne = (Enemy) Enemy1;
			//FlxSprite playerone = (FlxSprite) Player;
			
			//enemyOne.teleport( playerone , enemyOne);
			
	    }
	};		
	
	IFlxCollision win = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Portal, FlxObject Player)
		{
		//	add(new FlxText(200,22 , 100, "You win Level 1!"));			//22
		if(switchS){

			jplayer.exists = false;
		}else{
				
			player.exists = false;
		}
			FlxG.play("Portal.mp3",1.0F, false);
			FlxG.fade(0xff000000,1, onFade);
		//	FlxG.switchState(new PlaystateTwo());
		}
	};
	
	IFlxCollision doSFishDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Bullet, FlxObject SFish)
		{									
			//		add(new FlxText(200,22 , 100, "You Died!"));			
//			player.kill();
	        SFish.hurt(1);
			//FlxG.music.kill();
	    }
	};		
	
	IFlxCollision doEnemyDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Enemy1, FlxObject Bullet)
		{									
			//		add(new FlxText(200,22 , 100, "You Died!"));			
//			player.kill();
	        enemy1.hurt(1);
			//FlxG.music.kill();
	    }
	};		
	private void onExit(){
		FlxG.switchState(new MenuState());
	}

	private void onReset(){
	FlxG.resetState();
	FlxG.music.resume();
	}
	public IFlxCamera onFade = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			gameSave = new FlxSave();
			gameSave.bind("Test");
			
			if(gameSave.data.get("Progress",int.class) == null)
			{
				gameSave.data.put("Progress", 2);
				gameSave.flush();
			}
			@SuppressWarnings("unchecked")
				int progress = gameSave.data.get("Progress", int.class);
	
				if(progress >= 3){
				FlxG.switchState(new PlaystateTwo());
				}else{
					
				
			/*saving stuff
			gameSave = new FlxSave();
			gameSave.bind("Test");*/

			//Save
			gameSave.data.put("Progress", 3);
			gameSave.flush();
	//		hasSavedAlready = true;
			
		//	_actionResolver.showOrLoadInterstital();
			
			FlxG.switchState(new PlaystateTwo());
			}
		}
	};
}

