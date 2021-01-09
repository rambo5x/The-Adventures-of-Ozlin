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
import com.dvreiter.starassault.Menu.*;

public class PlaystateTwo extends FlxState
{	
	public FlxButton pause;
	public FlxTilemap level;
	public FlxSprite portal;
	public FlxSprite hearts;
	public FlxGroup coins;
	public FlxSprite portalcoin;
	public FlxGroup spikes;	   
	public FlxText status;
	public FlxText goal;
	public FlxText pausedTxt;
	protected FlxEmitter _littleGibs;
	private FlxSave gameSave;
	private FlxNinePatchButton closebtn;

	private FlxVirtualPad pad;
	private Player player;
	private Enemy enemy1;
	FlxGroup _bullets;
	
	public FlxTileblock pauseblock, leftBorder,rightBorder;
	//private FlxButton Pausebtn;
	private FlxSprite Pausebtn;
	private FlxButton Exitbtn;
	private FlxButton restartbtn;
	private FlxButton Resumebtn;
	private FlxAnalog analog;	
	private JoyStickPlayer jplayer;
	private boolean switchS;
	private FlxSave jswitchSave;
	@Override
	public void create()
	{						
		FlxG.setBgColor(0xFF00CCFF);
		FlxG.mouse.show();

		int[] data = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,1,1,1,0,1,1,1,0,0,1,1,1,0,0,1,1,1,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,7,7,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,7,7,7,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,7,7,7,7,
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,7,7,7,           
			7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,
			7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,
			7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7};	

		level = new FlxTilemap();
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 25), "tilemap.png", 16, 16); 
		add(level);
		// 400x240						

		_littleGibs = new FlxEmitter();
		_littleGibs.setXSpeed(-150,150);
		_littleGibs.setYSpeed(-200,0);
		_littleGibs.setRotation(-720,-720);
		_littleGibs.gravity = 350;
		_littleGibs.bounce = 0.5f;
		_littleGibs.makeParticles("playergibs.png",100,10,true,0.5f);

		portal = new FlxSprite(50, 50);
		portal.loadGraphic("Grass_Portal.png", true, true, 16, 16);
		portal.addAnimation("spinning", new int[]{0, 1, 2}, 6, true);
		portal.play("spinning");
		portal.immovable = true;
		portal.exists = false;
		add(portal);	

		portalcoin = new FlxSprite(240,60);
		portalcoin.loadGraphic("Portalcoin.png", true, true, 16, 16);
		portalcoin.addAnimation("rotate", new int[]{0, 1, 2}, 4, true);
		portalcoin.play("rotate");
		add(portalcoin);		
		
		leftBorder = new FlxTileblock(-16,0,16,240);
		leftBorder.makeGraphic(16,240);
		add(leftBorder);
		
		rightBorder = new FlxTileblock(401,0,15,240);
		rightBorder.makeGraphic(15,240);
		add(rightBorder);
//300,75 for free
//380,160 for prison
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
			enemy1 = new Enemy(300,75,16,16,500,1); 		
			enemy1.followSprite(jplayer);		
		}else{
		enemy1 = new Enemy(300,75,16,16,500,1); 		
		enemy1.followSprite(player);		
		}

		coins = new FlxGroup(); 			
		//bottom coins	
		createCoin(75,150);
		createCoin(125,150);
		createCoin(175,150);				
		createCoin(225,150);
		createCoin(275,150);
		add(coins);

		spikes = new FlxGroup();
		createSpike(50,160,0);
		createSpike(100,160,0);
		createSpike(150,160,0);
		createSpike(200,160,0);
		createSpike(250,160,0);
		add(spikes);

		status = new FlxText(2,2,100, "SCORE: ");
		status.setShadow(0xff000000);
		status.setText("SCORE: "+(coins.countDead()*100));
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
		
		add(status);
		add(enemy1);		
		
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
	
	public void createSpike(int X, int Y,int Angle){
		Spike spike = new Spike(X,Y,Angle);
		spikes.add(spike);
	}

    @Override
	public void update()
	{		
		if(closebtn.status == FlxNinePatchButton.PRESSED){
			closebtn.visible = false;
			_bullets.active = false;
			if(switchS){
				jplayer.exists = false;
			}else{
				player.exists = false;
			}
			pauseblock.visible = true;
			pausedTxt.visible = true;

			Resumebtn.visible = true;
			restartbtn.visible = true;
			Exitbtn.visible = true;	
			enemy1.active = false;
			FlxG.music.pause();
		}
		if(Resumebtn.status == Resumebtn.PRESSED || FlxG.keys.justPressed("BACK")){
			enemy1.active=true;
			closebtn.visible = true;
			if(switchS){
				jplayer.exists = true;
			}else{
				player.exists = true;
			}
			_bullets.active = true;
			Resumebtn.visible = false;
			restartbtn.visible = false;
			Exitbtn.visible = false;
			pauseblock.visible= false;
			pausedTxt.visible = false;
			FlxG.music.resume();
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
		FlxG.overlap(spikes,jplayer,doSpikeDamage);
		FlxG.overlap(portalcoin,jplayer,getPCoin);
		FlxG.overlap(portal,jplayer,win);
		FlxG.collide(enemy1,level);
		FlxG.collide(enemy1,spikes);
		FlxG.collide(enemy1,jplayer,doPlayerDamage);
		FlxG.collide(level,jplayer);
		FlxG.collide(leftBorder,jplayer);
		FlxG.collide(_bullets, enemy1, doEnemyDamage);
		FlxG.collide(level, _bullets);
		FlxG.collide(spikes, _bullets);
		}else{
			FlxG.overlap(coins,player,getCoin);	
			FlxG.overlap(spikes,player,doSpikeDamage);
			FlxG.overlap(portalcoin,player,getPCoin);
			FlxG.overlap(portal,player,win);
			FlxG.collide(enemy1,level);
			FlxG.collide(enemy1,spikes);
			FlxG.collide(enemy1,player,doPlayerDamage);
			FlxG.collide(level,player);
			FlxG.collide(leftBorder,player);
			FlxG.collide(_bullets, enemy1, doEnemyDamage);
			FlxG.collide(level, _bullets);
			FlxG.collide(spikes, _bullets);
		}

		
	}

	@Override
	public void destroy()
	{
		// TODO: Implement this method
		super.destroy();
		pause = null;
		level = null;
		portal = null;
		hearts = null;
		coins = null;
		portalcoin = null;
		portal = null;
		spikes = null;
		status = null;
		goal = null;
		player = null;
		enemy1 = null;
		pad = null;
	}

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

	IFlxCollision getCoin = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Coin, FlxObject Player)
		{						
	        Coin.kill();
			FlxG.play("Coin.mp3",1.0F,false);
			status.setText("SCORE: "+(coins.countDead()*100));	  					        
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

	IFlxCollision doEnemyDamage = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Enemy1, FlxObject Bullet)
		{
			enemy1.hurt(1);
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

			if(progress >= 4){
				FlxG.switchState(new PlaystateThree());
			}else{
				

			//Save

			gameSave.data.put("Progress", 4);
			gameSave.flush();

			FlxG.switchState(new PlaystateThree());
		}
		} 
	};
}
       	
