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

public class DungeonLevelsState extends FlxState
{		
	private FlxButton Level1,Level2,Level3,Level4,Level5,Level6;


	public FlxSave gameSave,jswitchSave;

	private FlxText title1,title2;

	private FlxGroup _bullets;

	private FlxEmitter _littleGibs;

	private FlxTilemap level;

	private FlxSprite leveldoor11,leveldoor12,leveldoor13,leveldoor14,leveldoor15,leveldoor6,leveldoor7,leveldoor8,leveldoor9,leveldoor10;

	private Player _player;
	
	private JoyStickPlayer jplayer;

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
	
	private FlxAnalog analog;
	
	private boolean switchS;

	@Override
	public void create()
	{			
		FlxG.setBgColor(FlxG.BLACK);

		int data [] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,11,11,11,11,11,11,0,0,0,0,0,0,0,0,0,
		2,2,0,0,0,0,0,0,0,0,11,0,0,0,0,11,0,0,0,0,0,0,0,2,2,
		0,0,0,0,0,0,0,0,0,0,11,0,0,0,0,11,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,11,0,0,0,0,11,0,0,0,0,0,0,0,0,0,
		2,2,0,0,0,0,0,0,0,0,11,0,0,0,0,11,0,0,0,0,0,0,0,2,2,
		0,0,0,0,0,0,11,11,11,11,11,11,11,11,11,11,11,11,11,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,
		2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,
		};
		level = new FlxTilemap() ;
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data),25), "tilemap.png" , 16, 16) ;
	//	level.setSolid(true) ;
		add(level);

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

		elevator = new FlxSprite(3*16,192);
		elevator.loadGraphic("elevator.png", true, true, 32, 10);
		elevator.immovable = true;
		destination = elevator.getMidpoint();
		destination.y -= 160;//122
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
	//		progress = 2;

		if(progress > 11)
		{
			leveldoor11 = new FlxSprite(0,16);
			leveldoor11.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor11.immovable = true;
			add(leveldoor11);
			add(new FlxText(5,22,12,"11"));
		}
		if(progress > 12)
		{
			leveldoor12 = new FlxSprite(0,4*16);
			leveldoor12.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor12.immovable = true;
			add(leveldoor12);
			add(new FlxText(5,70,12,"12"));
		}
		if(progress > 13)
		{
			leveldoor13 = new FlxSprite(0,7*16);
			leveldoor13.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor13.immovable = true;
			add(leveldoor13);
			add(new FlxText(5,117,12,"13"));
		}
		if(progress > 14)
		{
			leveldoor14 = new FlxSprite(0,10*16);
			leveldoor14.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor14.immovable = true;
			add(leveldoor14);
			add(new FlxText(5,165,12,"14"));
		}
		if(progress > 15)
		{
			leveldoor15 = new FlxSprite(0,13*16);//379
			leveldoor15.loadGraphic("leveldoor.png",true,true,16,16);
			leveldoor15.immovable = true;
			add(leveldoor15);
			add(new FlxText(5,214,12,"15"));//384
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


	/*	if(elevator.y > 32&& FlxG.collide(_player,elevator))
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
		/*	FlxG.collide(_bullets, option1,Option1);
		 FlxG.collide(_bullets, option2,Option2);
		 FlxG.collide(_bullets, option3,Option3);
		 FlxG.collide(_bullets, option4,Option4);
		 FlxG.collide(_bullets, option5,Option5);
		 FlxG.collide(_bullets, option6,Option6);
		 FlxG.collide(_bullets, option7,Option7);*/
		 if(switchS){
		FlxG.collide(jplayer,leveldoor11,lvl11);
		FlxG.collide(jplayer,leveldoor12,lvl12);
		FlxG.collide(jplayer,leveldoor13,lvl13);
		FlxG.collide(jplayer,leveldoor14,lvl14);
		FlxG.collide(jplayer,leveldoor15,lvl15);
		FlxG.collide(level,jplayer);
		FlxG.collide(elevator,jplayer);
		FlxG.collide(_bullets,level);
		FlxG.collide(_bullets,elevator);
		FlxG.collide(_bullets,elevator2);
		FlxG.collide(jplayer,elevator2);
		}else{
			FlxG.collide(_player,leveldoor11,lvl11);
			FlxG.collide(_player,leveldoor12,lvl12);
			FlxG.collide(_player,leveldoor13,lvl13);
			FlxG.collide(_player,leveldoor14,lvl14);
			FlxG.collide(_player,leveldoor15,lvl15);
			FlxG.collide(level,_player);
			FlxG.collide(elevator,_player);
			FlxG.collide(_bullets,level);
			FlxG.collide(_bullets,elevator);
			FlxG.collide(_bullets,elevator2);
			FlxG.collide(_player,elevator2);
		}
	}

	IFlxCollision lvl11 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel11();
		}
	};

	IFlxCollision lvl12 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel12();
		}
	};

	IFlxCollision lvl13 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel13();
		}
	};

	IFlxCollision lvl14 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel14();
		}
	};

	IFlxCollision lvl15 = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Player, FlxObject Leveldoor)
		{									
			onLevel15();
		}
	};

	/*IFlxCollision Option1 = new IFlxCollision()
	 {
	 @Override 
	 public void callback(FlxObject Enemy, FlxObject Bullet)
	 {									
	 onLevel1();
	 option1.hurt(1);
	 optionTxt1.kill();
	 }
	 };

	 IFlxCollision Option2 = new IFlxCollision()
	 {
	 @Override 
	 public void callback(FlxObject Enemy, FlxObject Bullet)
	 {									
	 onLevel2();
	 option2.hurt(1);
	 optionTxt2.kill();
	 }
	 };

	 IFlxCollision Option3 = new IFlxCollision()
	 {
	 @Override 
	 public void callback(FlxObject Enemy, FlxObject Bullet)
	 {									
	 onLevel3();
	 option3.hurt(1);
	 optionTxt3.kill();
	 }
	 };

	 IFlxCollision Option4 = new IFlxCollision()
	 {
	 @Override 
	 public void callback(FlxObject Enemy, FlxObject Bullet)
	 {									
	 onLevel4();
	 option4.hurt(1);
	 optionTxt4.kill();
	 }
	 };
	 IFlxCollision Option5 = new IFlxCollision()
	 {
	 @Override 
	 public void callback(FlxObject Enemy, FlxObject Bullet)
	 {									
	 onLevel5();
	 option5.hurt(1);
	 optionTxt5.kill();
	 }
	 };
	 IFlxCollision Option6 = new IFlxCollision()
	 {
	 @Override 
	 public void callback(FlxObject Enemy, FlxObject Bullet)
	 {									
	 onLevel6();
	 option6.hurt(1);
	 }
	 };
	 IFlxCollision Option7 = new IFlxCollision()
	 {
	 @Override 
	 public void callback(FlxObject Enemy, FlxObject Bullet)
	 {									
	 onLevel6();
	 option7.hurt(1);
	 }
	 };*/

	private void onLevel11(){
		FlxG.fade(0xff000000,1,onFade11);
		//	FlxG.switchState(new PlayState());
	}
	private void onLevel12(){
		FlxG.fade(0xff000000,1,onFade12);
	}

	private void onLevel3(){
		FlxG.fade(0xff000000,1,onFade3);
	}
	private void onLevel13(){
		FlxG.fade(0xff000000,1,onFade13);
	}
	private void onLevel14(){
		FlxG.fade(0xff000000,1,onFade14);
	}
	private void onLevel15(){
		FlxG.fade(0xff000000,1,onFade15);
	}
	private void onLevel6(){
		FlxG.fade(0xff000000,1,onFade6);
	}
	/*private void OnPress(){
	 FlxU.openURL("http://www.newgrounds.com/audio/listen/516336");
	 }*/

	public IFlxCamera onFade11 = new IFlxCamera()
	{
		@Override
		public void callback()
		{		
			FlxG.switchState(new PlayState11());
		}

	};

	public IFlxCamera onFade12 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlayState12());
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
	public IFlxCamera onFade13 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlayState13());
		}

	};
	
	public IFlxCamera onFade14 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlayState14());
		}

	};
	public IFlxCamera onFade15 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlayState15());
		}

	};
	public IFlxCamera onFade6 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new PlayState13());
		}

	};


}
