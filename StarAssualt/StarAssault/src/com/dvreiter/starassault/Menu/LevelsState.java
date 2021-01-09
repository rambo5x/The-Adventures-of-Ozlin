package com.dvreiter.starassault.Menu;

import org.flixel.FlxButton;
import org.flixel.FlxG;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.event.IFlxButton;
import org.flixel.*;
import org.flixel.ui.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Levels.*;
import com.dvreiter.starassault.*;
import com.dvreiter.starassault.Player.*;
import com.badlogic.gdx.utils.*;
import com.dvreiter.starassault.Objects.*;
import com.dvreiter.starassault.Tools.*;

public class LevelsState extends FlxState
{		
	private FlxButton Level1,Level2,Level3,Level4,Level5,Level6;
	
	
	public FlxSave gameSave,jswitchSave;
	
	private FlxText title1,title2;

	private FlxGroup _bullets;

	private FlxEmitter _littleGibs;

	private FlxTilemap level;
	
	private FlxSprite leveldoor1,leveldoor2,leveldoor3,leveldoor4,leveldoor5,leveldoor6,leveldoor7,leveldoor8,leveldoor9,leveldoor10;
	
	private Player _player;

	private FlxVirtualPad pad;

	private FlxSprite elevator;

	private FlxPath path;

	private FlxPoint destination;

	private FlxSprite elevator2;

	private FlxPoint destination2;

	private FlxPath path2;

	private LevelOption option1;

	private LevelOption option2;

	private LevelOption option3;

	private LevelOption option4;

	private LevelOption option5;

	private LevelOption option6;

	private LevelOption option7;

	private FlxText optionTxt1;

	private FlxText optionTxt5;

	private FlxText optionTxt4;

	private FlxText optionTxt3;

	private FlxBasic optionTxt2;
	
	private JoyStickPlayer jplayer;
	
	private FlxAnalog analog;
	
	private boolean switchS;

	private ErrorReporter reporter;
	
	//private JoyStickPlayer player;
	

	@Override
	public void create()
	{			
		FlxG.setBgColor(0xFF00CCFF);
		FlxG.mouse.show();
		
int data [] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,
			1,1,0,0,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,0,0,0,0,1,1,
			0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,0,0,0,0,0,0,
			1,1,0,0,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,0,0,0,0,1,1,
			0,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
			};
		level = new FlxTilemap() ;
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data),25), "tilemap.png" , 16, 16) ;
	//	level.setSolid(true) ;
		add(level);
	//	reporter = new ErrorReporter();

		_littleGibs = new FlxEmitter() ;
		_littleGibs.setXSpeed(-150,150);
		_littleGibs.setYSpeed(-200,0) ;
		_littleGibs.setRotation(-720,-720) ;
		_littleGibs.gravity = 350;
		_littleGibs.bounce = 0.5f;
		_littleGibs.makeParticles("gibs.png",100,10,true,0.5f);
//Now for Entities
	/*	analog = new FlxAnalog(50, 200);
		analog.setAlpha(.75f);
		analog.setAll("scrollFactor", new FlxPoint(0, 0));*/
		
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

			jplayer = new JoyStickPlayer(208,208,16,16,_bullets,_littleGibs, pad,analog);
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

			_player = new Player(208,208,16,16,_bullets,_littleGibs, pad);
			_player.setHasGravityToggle(false);
			FlxG.camera.follow(_player, FlxCamera.STYLE_PLATFORMER);
			FlxG.camera.setBounds(0,0,400,240,true);// 1st 400,240  2nd 800,240, 3rd 1200,480//status = new FlxText(2,2,100, "SCORE: ";

			add(_bullets) ;
			add(_player) ;
			add(_littleGibs); 

		}
		
		
		
	//	add(analog);
		
		elevator = new FlxSprite(3*16,192);//13x16
		elevator.loadGraphic("elevator.png", true, true, 32, 10);
		elevator.immovable = true;
		destination = elevator.getMidpoint();
		destination.y -= 160;//122,176,168
		Array<FlxPoint> points = new Array<FlxPoint>();
		points.addAll(new FlxPoint[]{elevator.getMidpoint(),destination});
		path = new FlxPath(points);
		elevator.followPath(path,40,FlxObject.PATH_YOYO);
		add(elevator);

		elevator2 = new FlxSprite(20*16,192);
		elevator2.loadGraphic("elevator.png", true, true, 32, 10);
		elevator2.immovable = true;
		//elevator2.maxVelocity.y = 400;
		destination2 = elevator2.getMidpoint();
		destination2.y -= 160;//122
		Array<FlxPoint> points2 = new Array<FlxPoint>();
		points2.addAll(new FlxPoint[]{elevator2.getMidpoint(),destination2});
		path2 = new FlxPath(points2);
		elevator2.followPath(path2,40,FlxObject.PATH_YOYO);
		add(elevator2);
		
//		option6 = new LevelOption(0*16,1*16,16,16, 6);
//		add(option6);
//		
//		option7 = new LevelOption(0*16,1*16,16,16, 7);
//		add(option7);
		
		//saving stuff
		gameSave = new FlxSave();
		gameSave.bind("Test");//Test
	 
		//Save
		if(gameSave.data.get("Progress",int.class) == null)
		{
		gameSave.data.put("Progress", 2);
		gameSave.flush();
		}
	//	else
//		{
//		gameSave.data.put("Progress", 2);
//		gameSave.flush();
//		}
		
		//load
		@SuppressWarnings("unchecked")
		int progress = gameSave.data.get("Progress", int.class);
	//	if(progress > 8)
	//	progress = 2;
		
		//Other
		
	//	option1 = new LevelOption(0*16,1*16,32,32, 1);
	//	add(option1);
	 	leveldoor1 = new FlxSprite(0,16);
		leveldoor1.loadGraphic("leveldoor.png",true,true,16,16);
		leveldoor1.immovable = true;
		add(leveldoor1);
		add(new FlxText(5,22,12,"1"));
		if(progress > 2)
		{
			leveldoor2 = new FlxSprite(0,4*16);
			leveldoor2.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor2.immovable = true;
			add(leveldoor2);
			add(new FlxText(5,70,12,"2"));
		}
		if(progress > 3)
		{
			leveldoor3 = new FlxSprite(0,7*16);
			leveldoor3.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor3.immovable = true;
			add(leveldoor3);
			add(new FlxText(5,117,12,"3"));
		}
		if(progress > 4)
		{
			leveldoor4 = new FlxSprite(0,10*16);
			leveldoor4.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor4.immovable = true;
			add(leveldoor4);
			add(new FlxText(5,165,12,"4"));
		}
		if(progress > 5)
		{
			leveldoor5 = new FlxSprite(0,13*16);
			leveldoor5.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor5.immovable = true;
			add(leveldoor5);
			add(new FlxText(5,214,12,"5"));
		}
		if(progress > 6)
		{
			leveldoor6 = new FlxSprite(383,16);//379
			leveldoor6.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor6.immovable = true;
			add(leveldoor6);
			add(new FlxText(388,22,12,"6"));//384
		}
		if(progress > 7)
		{
			leveldoor7 = new FlxSprite(383,4*16);//379
			leveldoor7.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor7.immovable = true;
			add(leveldoor7);
			add(new FlxText(388,70,12,"7"));//384
		}
		if(progress > 8)
		{
			leveldoor8 = new FlxSprite(383,7*16);//379
			leveldoor8.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor8.immovable = true;
			add(leveldoor8);
			add(new FlxText(388,117,12,"8"));//384
		}
		if(progress > 9)
		{
			leveldoor9 = new FlxSprite(383,10*16);//379
			leveldoor9.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor9.immovable = true;
			add(leveldoor9);
			add(new FlxText(388,165,12,"9"));//384
		}
		if(progress > 10)
		{
			leveldoor10 = new FlxSprite(383,13*16);//379
			leveldoor10.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor10.immovable = true;
			add(leveldoor10);
			add(new FlxText(388,214,12,"10"));//384
		}
		
		//the letters "mo"
		title1 = new FlxText(FlxG.width + 12,FlxG.height / 20,120,"Level");
		title1.setSize(32);
		title1.antialiasing = true;
		title1.velocity.x = -FlxG.width + 64;
		title1.angularAcceleration = 800;
		title1.angularVelocity = 400;
		title1.moves = true;
		add(title1);

		//the letters "de"
		title2 = new FlxText(-55,title1.y,120,"Menu");
		title2.setSize(32);
		//	title2.setColor(title1.getColor());
		title2.antialiasing = title1.antialiasing;
		title2.velocity.x = FlxG.width - 64;
		title2.angularAcceleration = 800;
		title2.angularVelocity = 400;
		title2.moves = true;
		add(title2); 
	}
    @Override
	public void update(){	
				
		if(switchS){
		if(jplayer.x <= 0)
			jplayer.x = 0;
		if(jplayer.x >= 382)
			jplayer.x = 382;
		if(jplayer.y <= 0)
			jplayer.y = 0;
		}else{
			if(_player.x <= 0)
				_player.x = 0;
			if(_player.x >= 382)
				_player.x = 382;
			if(_player.y <= 0)
				_player.y = 0;
			
		}
		super.update();
		
	/*		if(elevator.y > 32&& FlxG.collide(_player,elevator))
			{	
					elevator.velocity.y = -40;
			}else if(elevator.y < (13*16)+16)
			{
					elevator.velocity.y = 40;
			}else
			{
					elevator.velocity.y = 0;
					elevator.y = 14 * 16;
			}
		
			if(elevator2.y > 32&& FlxG.collide(_player,elevator2))
			{	
					elevator2.velocity.y = -40;
			}else if(elevator2.y < (13*16)+16)
			{
					elevator2.velocity.y = 40;
			}else
			{
					elevator2.velocity.y = 0;
					elevator2.y = 14 * 16;
			}*/
			
		
		if(FlxG.keys.justPressed("BACK"))
		{
			FlxG.switchState( new MenuState());//Added
		}

		if(title2.x > title1.x + title1.width - 12)
		{
			title2.x = title1.x + title1.width - 12;
			title1.velocity.x = 0;
			title2.velocity.x = 0;
			title1.angularVelocity = 0;
			title1.angularAcceleration = 0;
			title2.angularAcceleration = 0;
			title2.angularVelocity = 0;
			title1.angle = 0;
			title2.angle = 0;

			FlxG.flash(0xffd8eba2,0.5f);
			FlxG.shake(0.035f,0.5f);
			title1.setColor(0xd8eba2);
			title2.setColor(0xd8eba2);
			
			
		} 
		if(switchS){
		FlxG.collide(jplayer,leveldoor1,lvl1);
		FlxG.collide(jplayer,leveldoor2,lvl2);
		FlxG.collide(jplayer,leveldoor3,lvl3);
		FlxG.collide(jplayer,leveldoor4,lvl4);
		FlxG.collide(jplayer,leveldoor5,lvl5);
		FlxG.collide(jplayer,leveldoor6,lvl6);
		FlxG.collide(jplayer,leveldoor7,lvl7);
		FlxG.collide(jplayer,leveldoor8,lvl8);
		FlxG.collide(jplayer,leveldoor9,lvl9);
		FlxG.collide(jplayer,leveldoor10,lvl10);
		FlxG.collide(level,jplayer);
		FlxG.collide(elevator,jplayer);
		FlxG.collide(_bullets,level);
		FlxG.collide(_bullets,elevator);
		FlxG.collide(_bullets,elevator2);
		FlxG.collide(jplayer,elevator2);
		}else{
			FlxG.collide(_player,leveldoor1,lvl1);
			FlxG.collide(_player,leveldoor2,lvl2);
			FlxG.collide(_player,leveldoor3,lvl3);
			FlxG.collide(_player,leveldoor4,lvl4);
			FlxG.collide(_player,leveldoor5,lvl5);
			FlxG.collide(_player,leveldoor6,lvl6);
			FlxG.collide(_player,leveldoor7,lvl7);
			FlxG.collide(_player,leveldoor8,lvl8);
			FlxG.collide(_player,leveldoor9,lvl9);
			FlxG.collide(_player,leveldoor10,lvl10);
			FlxG.collide(level,_player);
			FlxG.collide(elevator,_player);
			FlxG.collide(_bullets,level);
			FlxG.collide(_bullets,elevator);
			FlxG.collide(_bullets,elevator2);
			FlxG.collide(_player,elevator2);
			
		}
	}
	
	IFlxCollision lvl1 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel1();
		}
	};
	
	IFlxCollision lvl2 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel2();
		}
	};
	
	IFlxCollision lvl3 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel3();
		}
	};
	
	IFlxCollision lvl4 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel4();
		}
	};
	
	IFlxCollision lvl5 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel5();
		}
	};
	
	IFlxCollision lvl6 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel6();
		}
	};

	IFlxCollision lvl7 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel7();
		}
	};

	IFlxCollision lvl8 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel8();
		}
	};

	IFlxCollision lvl9 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel9();
		}
	};

	IFlxCollision lvl10 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel10();
		}
	};

	private void onLevel1(){
		FlxG.fade(0xff000000,1,onFade);
	//	FlxG.switchState(new PlayState());
	}
	private void onLevel2(){
		FlxG.fade(0xff000000,1,onFade2);
	}

	private void onLevel3(){
		FlxG.fade(0xff000000,1,onFade3);
	}
	private void onLevel4(){
		FlxG.fade(0xff000000,1,onFade4);
	}
	private void onLevel5(){
		FlxG.fade(0xff000000,1,onFade5);
	}
	private void onLevel6(){
		FlxG.fade(0xff000000,1,onFade6);
	}
	private void onLevel7(){
		FlxG.fade(0xff000000,1,onFade7);
	}
	private void onLevel8(){
		FlxG.fade(0xff000000,1,onFade8);
	}
	private void onLevel9(){
		FlxG.fade(0xff000000,1,onFade9);
	}
	private void onLevel10(){
		FlxG.fade(0xff000000,1,onFade10);
	}
	/*private void OnPress(){
	 FlxU.openURL("http://www.newgrounds.com/audio/listen/516336");
	 }*/

	public IFlxCamera onFade = new IFlxCamera()
	{
		@Override
		public void callback()
		{		
			FlxG.switchState(new PlayState());
		}

	};
	
	public IFlxCamera onFade2 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlaystateTwo());
		}

	};
	
	public IFlxCamera onFade3 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlaystateThree());
		}

	};
	public IFlxCamera onFade4 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlayState4());
		}

	};
	public IFlxCamera onFade5 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlayState5());
		}

	};
	public IFlxCamera onFade6 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlayState6());
		}

	};
	public IFlxCamera onFade7 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlayState7());
		}

	};
	public IFlxCamera onFade8 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlayState8());
		}

	};
	public IFlxCamera onFade9 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlayState9());
		}

	};
	public IFlxCamera onFade10 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlayState10());
		}

	};
}
