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
import com.dvreiter.starassault.Tools.*;

public class JoystickState extends FlxState
{	

	private static final int TILE_WIDTH = 16;
	private static final int TILE_HEIGHT = 16;

	/*private FlxObject highlightbox;
	 private int action;
	 private static final int ACTION_IDLE = 0;*/

	/*public FlxButton pause;
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
	 public FlxText _fps, pausedTxt;
	 public FlxSprite elevator, pusher;

	 public FlxGroup crushers;
	 public FlxGroup enemies;
	 public FlxGroup sfishs;
	 Skeleton skeleton;
	 //Turret turret;*/
	public FlxTilemap level;
	public FlxTileblock pauseblock;
	public FlxText angleStat;
	TreeBoss tboss;
	Crawler crawler;
	Spawner espawner;
	Player player;
	private JoyStickPlayer _jplayer;
	Enemy enemy;
	FlxVirtualPad pad;
	ErrorReporter error;
	public  FlxGroup _bullets, _rocks;
	protected FlxEmitter _littleGibs, _rockGibs,_spitGibs;
	//protected FlxEmitter _skelGibs;
	public FlxGroup enemies, spawners;

	//	private FlxButton Pausebtn;
	private FlxSprite Pausebtn;
	private FlxButton Exitbtn;
	private FlxButton Settingsbtn;
	private FlxButton Resumebtn;
	private FlxAnalog analog;

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

	int[] data = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	
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

		level = new FlxTilemap();
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 25), "tilemap.png", TILE_WIDTH, TILE_HEIGHT);
		add(level);
		
		error = new ErrorReporter();

		_littleGibs = new FlxEmitter();
		_littleGibs.setXSpeed(-150,150);
		_littleGibs.setYSpeed(-200,0);
		_littleGibs.setRotation(-720,-720);
		_littleGibs.gravity = 350;
		_littleGibs.bounce = 0.5f;
		_littleGibs.makeParticles("playergibs.png",100,10,true,0.5f);

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
		
	//	add(new JoyStickPlayer(20,150,16,16,_bullets,_littleGibs,pad,analog));

	   // player = new Player(64,400,16,16,_bullets,_littleGibs, pad);
	//	player.setHasGravityToggle(true);
	//try{
		_jplayer = new JoyStickPlayer(20,150,16,16,_bullets,_littleGibs, pad,analog);
		//_jplayer.setHasGravityToggle(true);
//	}catch(Exception e){
	//	error.reportError(e);
	//}
	//	FlxG.camera.follow(_jplayer, FlxCamera.STYLE_PLATFORMER);
	//	FlxG.camera.setBounds(0,0,800,480,true);// 1st 400,240  2nd 800,240, 3rd 1200,48
	//	add(player);			
		angleStat = new FlxText(2,2,100, "Velocity: ");
		angleStat.setShadow(0xff000000);
	//	angleStat.setText("Joystick Angle: "+ analog.getAngle());
		angleStat.scrollFactor.x = angleStat.scrollFactor.y = 0;
		add(angleStat);
		
	    add(_jplayer);
		add(_bullets);
	//	add(pad);
	//add(analog);
	}

     @Override
	public void update(){	
	    angleStat.setText("Velocity: "+ (int)_jplayer.velocity.x);
		super.update();					  
		FlxG.collide(level, _bullets);
	//	FlxG.collide(level, player);
		FlxG.collide(level,_jplayer);
	}

	@Override
	public void destroy()
	{
		super.destroy();
		analog = null;
	}	



	private void onExit(){
		FlxG.switchState(new MenuState());
	}

	private void onSettings(){
		//			FlxG.switchState(new Options());
	}
}
