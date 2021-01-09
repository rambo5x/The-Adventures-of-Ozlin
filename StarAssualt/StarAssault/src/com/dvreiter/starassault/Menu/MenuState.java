package com.dvreiter.starassault.Menu;

import com.badlogic.gdx.*;
import org.flixel.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Player.*;
import org.flixel.ui.*;
import com.badlogic.gdx.utils.*;
import java.util.*;
import com.dvreiter.starassault.Mobs.*;
import org.flixel.ui.event.*;
import com.badlogic.gdx.graphics.*;
import org.flixel.system.*;
import android.location.*;
import com.dvreiter.starassault.Menu.Multiplayer.*;
import com.dvreiter.starassault.Levels.*;
import com.dvreiter.starassault.LevelEditor.*;
import com.dvreiter.starassault.Tools.*;

public class MenuState extends FlxState
{		
	private FlxButton OptionsButton;
    private FlxButton AboutButton;
	private FlxButton LE;
	private FlxNinePatchButton closebtn, lvlEditorclosebtn;
	private FlxText gameversion, soon;
	private FlxText  title1,title2,cprogressText, settingsTxt;
	private PlayerAuto player;
	private FlxVirtualPad pad;
	
	private FlxTileblock optionsOverlay, comingsoonOverlay;
	private FlxButton Musicbtn, Sfxbtn, Closebtn, Ratebtn;
	
	private FlxNinePatch optionsOverlay1;
	
	private FlxSwitch musicSwitch, sfxSwitch, clSwitch, jswitch;
	
	private FlxTilemap level;

	private FlxGroup _bullets;
	private FlxEmitter _littleGibs;
	private FlxEmitter _skelGibs;
	private Enemy enemy1;
	private Mage mage1;
	private Skeleton skeleton1;
	private FlxGroup skeletons;
	private FlxGroup enemies;
	private FlxGroup mages;

	private FlxSave guiMusic, guiMusicSwitch,guiSFXSwitch,gameSave,checkpt2Save,checkptSave,jswitchSave,jswitchSaveF;
	
	private int rightSideX = 0;
	private int rightSideY = 150;
	
	private int leftSideX = 24*16;
	private int leftSideY = 150;
	
	int randomnum;
	int start = 1;
	int end = 4;
	
	private int colorGen = ((int) (Math.random() * 0x1000000));
	
	private FlxTimer spawnCoolDown;
	private FlxTimer colorGenCoolDown;
	private FlxTimer timer;
	private boolean canSpawn = true, switchControls;
	private FlxButton SinglePlayButton;
	private ErrorReporter reporter;

	@Override
	public void create()
	{			
		FlxG.setBgColor(0xFF00CCFF);//Or FlxG.black
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
		level = new FlxTilemap();
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 25), "tilemap.png", 16, 16);
	//	level.setSolid(true);
		add(level);
	//	reporter = new ErrorReporter();
	//	try {
	/*	pad = new FlxVirtualPad(FlxVirtualPad.LEFT_RIGHT, FlxVirtualPad.A_B_X_Y);
		pad.setAlpha(0.5f);
		pad.visible = false;*/

		_bullets = new FlxGroup();

		_littleGibs = new FlxEmitter();
		_littleGibs.setXSpeed(-150,150);
		_littleGibs.setYSpeed(-200,0);
		_littleGibs.setRotation(-720,-720);
		_littleGibs.gravity = 350;
		_littleGibs.bounce = 0.5f;
		_littleGibs.makeParticles("gibs.png",100,10,true,0.5f);
		
		_skelGibs = new FlxEmitter();
		_skelGibs.setXSpeed(-150,150);
		_skelGibs.setYSpeed(-200,0);
		_skelGibs.setRotation(-720,-720);
		_skelGibs.gravity = 350;
		_skelGibs.bounce = 0.5f;
		_skelGibs.makeParticles("skelgibs.png",100,10,true,0.5f);
		
		player = new PlayerAuto(120,150,16,16,_bullets,_littleGibs);	
		add(player);
		
		add(_bullets);				
		add(_littleGibs);
		add(_skelGibs);
	//	add(pad);
		
		SinglePlayButton = new FlxButton(160,95,"Start Game");//95
		add(SinglePlayButton);	
		
		OptionsButton = new FlxButton(160, 125, "Settings");//120
		add(OptionsButton);	

		AboutButton = new FlxButton(160, 155, "About");//145
		add(AboutButton);
	/*	SettingsButton = new FlxButton(160, 170, "Settings", new IFlxButton(){@Override public void callback(){onSettings();}});
		add(SettingsButton);
	*/
		//LE = new FlxButton(160 , 170, "Level Editor");
		//add(LE);//y=195original
		
	/*	Ratebtn = new FlxButton(160, 195, "Rate");
		add(Ratebtn);*/
		/*	musicauthorbutton = new FlxButton(160, 18, "Music: Envy", new IFlxButton(){@Override public void callback(){OnPress();}});
		 add(musicauthorbutton);		*/

		/*	Title = new FlxText(100, 50, 200, "Bob Run");
		 Title.setSize(40);
		 add(Title);	
		 */

//
		 
		spawnCoolDown = new FlxTimer();
		colorGenCoolDown = new FlxTimer();
		
	/*	title1 = new FlxText(FlxG.width + 12,60,90,"Bob");
		title1.setSize(32);	
		title1.antialiasing = true;
		title1.velocity.x = -FlxG.width;
		title1.angularAcceleration = 800;
		title1.angularVelocity = 400;
		title1.moves = true;
		add(title1);

		title2 = new FlxText(-70,title1.y,(int) title1.width,"Run");
		title2.setSize(32);
		title2.antialiasing = title1.antialiasing;
		title2.velocity.x = FlxG.width;
		title2.angularAcceleration = 800;
		title2.angularVelocity = 400;
		title2.moves = true;
		add(title2);
	*/	
	/*	title1 = new FlxText(170,FlxG.height + 12,90,"Ozlins");
		title1.setSize(14);	
		title1.antialiasing = true;
		title1.velocity.y = -FlxG.height;
	//	title1.angularAcceleration = 800;
	//	title1.angularVelocity = 400;
		title1.moves = true;
		add(title1);

		title2 = new FlxText(170,-24,(int) title1.width,"Adventures");
		title2.setSize(14);
		title2.antialiasing = title1.antialiasing;
		title2.velocity.y = 120;
	//	title2.angularAcceleration = 800;
	//	title2.angularVelocity = 400;
		title2.moves = true;
		add(title2);
	*/
	
		gameSave = new FlxSave();
		gameSave.bind("Test");

		if(gameSave.data.get("Progress",int.class) == null)
		{
			gameSave.data.put("Progress", 2);
			gameSave.flush();
		}

		@SuppressWarnings("unchecked")
			int progress = gameSave.data.get("Progress", int.class);
	//	if(progress > 8)
	//		progress = 2;
		
	/*	checkpt2Save = new FlxSave();
		checkpt2Save.bind("checkpt2Coords");

		if(checkpt2Save.data.get("Coords",boolean.class) == null)
		{
			checkpt2Save.data.put("Coords", false);
			checkpt2Save.flush();
		}

		//load
		@SuppressWarnings("unchecked")
			boolean playercoordsCheck = checkpt2Save.data.get("Coords", boolean.class);*/

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
		
		timer = new FlxTimer();

		cprogressText = new FlxText(135, 210, 200, "Progress Cleared!");
		cprogressText.setSize(12);
		cprogressText.visible = false;
		add(cprogressText);
		
		title1 = new FlxText(70,30,350,"The  Adventures  Of ");
		title1.setSize(20);	
		title1.antialiasing = true;
		title1.setAlpha(0.5f);
		add(title1);
		
		title2 = new FlxText(168,60,90,"Ozlin");
		title2.setSize(20);	
		title2.antialiasing = true;
		title2.setAlpha(0.5f);
		add(title2);

		FlxG.flash(colorGen,0.5f);
		FlxG.shake(0.035f,0.5f);
	/*	title2 = new FlxText(-70,title1.y,(int) title1.width,"Run");
		title2.setSize(32);
		title2.antialiasing = title1.antialiasing;
		title2.velocity.x = FlxG.width;
		title2.angularAcceleration = 800;
		title2.angularVelocity = 400;
		title2.moves = true;
		add(title2);
		*/
		
		Gdx.input.setCatchBackKey(true);

		 enemies = new FlxGroup();
		 add(enemies);
		 skeletons = new FlxGroup();
		 add(skeletons);
		 mages = new FlxGroup();
		 add(mages);
		
		 
		
		gameversion = new FlxText(2, 225, 100, "v0.1.4");
		add(gameversion);	
		
		FlxG.playMusic("prologue.mp3", 1f, true, true);
		
		comingsoonOverlay = new FlxTileblock(80, 50, 230, 150);//y=50original
		comingsoonOverlay.makeGraphic(240, 150, FlxG.BLACK);// 390, 230, 0x50FFFFFF
		comingsoonOverlay.setAlpha(0.5f);
		comingsoonOverlay.setSolid(true);
		comingsoonOverlay.immovable = true;
		comingsoonOverlay.scrollFactor.x = comingsoonOverlay.scrollFactor.y = 0;
		comingsoonOverlay.exists = true;
		comingsoonOverlay.visible = false;
		add(comingsoonOverlay);
		
		soon =  new FlxText(140,80,120,"Coming\nSoon");
		soon.setSize(26);//20
		soon.antialiasing = true;
		//	soon.setAlpha(0.5f);
		soon.visible = false;
		add(soon);
		
		optionsOverlay = new FlxTileblock(80, 50, 230, 150);//y=50original
		optionsOverlay.makeGraphic(240, 150, FlxG.BLACK);// 390, 230, 0x50FFFFFF
		optionsOverlay.setAlpha(0.5f);
		optionsOverlay.setSolid(true);
		optionsOverlay.immovable = true;
		optionsOverlay.scrollFactor.x = optionsOverlay.scrollFactor.y = 0;
		optionsOverlay.exists = true;
		optionsOverlay.visible = false;
		add(optionsOverlay);
		
		settingsTxt = new FlxText(155, 60, 90, "Settings");
		settingsTxt.setSize(16);
		settingsTxt.visible = false;
		add(settingsTxt);
		
		lvlEditorclosebtn = new FlxNinePatchButton(295,55,null,"X",20,20,null);
		lvlEditorclosebtn.scrollFactor.x = lvlEditorclosebtn.scrollFactor.y = 0;
		lvlEditorclosebtn.exists = true;
		lvlEditorclosebtn.visible = false;
		add(lvlEditorclosebtn);
		
		closebtn = new FlxNinePatchButton(295,55,null,"X",20,20,null);
		closebtn.scrollFactor.x = closebtn.scrollFactor.y = 0;
		closebtn.exists = true;
		closebtn.visible = false;
		add(closebtn);
	
	/*	optionsOverlay = new FlxNinePatchButton(100,50,null,"",210,150,null);
		optionsOverlay.exists = true;
		optionsOverlay.visible = false;
		add(optionsOverlay); */
	   // add(new FlxNinePatchButton(150, 10, null, "Extra\nline", 0, 0, null));
		
		 musicSwitch = new FlxSwitch(185, 80, "0", null, "Music");//y=60original
		 musicSwitch.exists = true;
		 musicSwitch.setActive(true);
		 musicSwitch.visible = false;
		 add(musicSwitch);
		 
		guiMusic = new FlxSave();
		guiMusic.bind("Options");//Test
		
		guiMusicSwitch = new FlxSave();
		guiMusicSwitch.bind("OptionsSwitch");
		
		musicSwitch.onUp = new IFlxUIListener()
		{
			@Override
			public void callback()
			{
				if(musicSwitch.getActivated() && FlxG.music.active)
				{
					FlxG.mute = false;
					guiMusic.data.put("Music", false);
					guiMusic.flush();
					musicSwitch.setActive(true);
					guiMusicSwitch.data.put("MusicS", true);
					guiMusicSwitch.flush();
				
				}
				else if(!musicSwitch.getActivated() && FlxG.music.active)
				{
					FlxG.mute = true;
					guiMusic.data.put("Music", true);
					guiMusic.flush();
					musicSwitch.setActive(false);
					guiMusicSwitch.data.put("MusicS", false);
					guiMusicSwitch.flush();
				}
			}
		};
		
		if(guiMusic.data.get("Music",boolean.class) == null)
		{
			guiMusic.data.put("Music", false);
			guiMusic.flush();
		}
	
		if(guiMusicSwitch.data.get("MusicS",boolean.class) == null)
		{
			guiMusicSwitch.data.put("MusicS", false);
			guiMusicSwitch.flush();
		}
		
	
		
		//load
		@SuppressWarnings("unchecked")
		boolean music = guiMusic.data.get("Music", boolean.class);
			
		if(music == false){
			FlxG.mute = false;
		}else if (music == true){
			FlxG.mute = true;
		}
		
		//load
		@SuppressWarnings("unchecked")
			boolean musicS = guiMusicSwitch.data.get("MusicS", boolean.class);

		if(musicS == false){
			musicSwitch.setActive(false);
		}else if (musicS == true){
			musicSwitch.setActive(true);
		}
		
		guiSFXSwitch = new FlxSave();
		guiSFXSwitch.bind("SFXSwitch");
		
		sfxSwitch = new FlxSwitch(185, 110, "0", null, "SFX");//90original
		sfxSwitch.setEnable(false);
		sfxSwitch.setActive(true);
		sfxSwitch.setColor(FlxG.RED);
		guiSFXSwitch.data.put("SFXS",true);
		sfxSwitch.exists = true;
		sfxSwitch.visible = false;
		add(sfxSwitch);
		
		if(guiSFXSwitch.data.get("SFXS",boolean.class) == null)
		{
			guiSFXSwitch.data.put("SFXS", false);
			guiSFXSwitch.flush();
		}
		
		//load
		@SuppressWarnings("unchecked")
			boolean sfxs = guiSFXSwitch.data.get("SFXS", boolean.class);

		if(sfxs == false){
			sfxSwitch.setActive(false);
		}else if (sfxs == true){
			sfxSwitch.setActive(true);
		}
		

	/*	sfxSwitch.onUp = new IFlxUIListener()
		{
			@Override
			public void callback()
			{
				if(sfxSwitch.getActivated())
				{
					
				}
				else if(!sfxSwitch.getActivated())
				{
					//FlxG.disposeSound("Bob_Shoot.mp3"); //Lags/Crashes cannot find another solution.
					
				}
			}
		};*/
		
		clSwitch = new FlxSwitch(185, 140, "0", null, "Clear Save");//90original
		clSwitch.exists = true;
		clSwitch.visible = false;
		add(clSwitch);
		
		clSwitch.onUp = new IFlxUIListener()
		{
			@Override
			public void callback()
			{
				if(clSwitch.getActivated())
				{
					gameSave.erase();
		//			checkpt2Save.erase();
					checkptSave.erase();
					cprogressText.visible = true;
					timer.start(3,1,showPText);
				}
			}
		};
		
		jswitch = new FlxSwitch(185, 170, "0", null, "Joystick");//y=60original,y=180
		jswitch.exists = true;
	//	jswitch.setActive(true);
		jswitch.visible = false;
		add(jswitch);

		jswitchSave = new FlxSave();
		jswitchSave.bind("joystick");//Test

		jswitchSaveF = new FlxSave();
		jswitchSaveF.bind("joystickS");

		jswitch.onUp = new IFlxUIListener()
		{
			@Override
			public void callback()
			{
				if(jswitch.getActivated())
				{
					switchControls = true;
					jswitchSave.data.put("jstick", true);
					jswitchSave.flush();
					jswitch.setActive(true);
					jswitchSaveF.data.put("jstickS", true);
					jswitchSaveF.flush();

				}
				else if(!jswitch.getActivated())
				{
					switchControls = false;
					jswitchSave.data.put("jstick", false);
					jswitchSave.flush();
					jswitch.setActive(false);
					jswitchSaveF.data.put("jstickS", false);
					jswitchSaveF.flush();
				}
			}
		};

		if(jswitchSave.data.get("jstick",boolean.class) == null)
		{
			jswitchSave.data.put("jstick", false);
			jswitchSave.flush();
		}

		if(jswitchSaveF.data.get("jstickS",boolean.class) == null)
		{
			jswitchSaveF.data.put("jstickS", false);
			jswitchSaveF.flush();
		}

		//load
		@SuppressWarnings("unchecked")
			boolean joystickS = jswitchSaveF.data.get("jstickS", boolean.class);

		if(joystickS == false){
			jswitch.setActive(false);
		}else if (joystickS == true){
			jswitch.setActive(true);
		}
		
		//} catch (Exception e) { e.printStackTrace(); 
	//		reporter.reportError(e);
		//}
		
	}
    @Override
	public void update(){	
		
		super.update();
	//	sfxSwitch.setActive(true); 
		if(FlxG.keys.justPressed("BACK"))
		{

			Gdx.app.exit();
		}
		
		if(SinglePlayButton.status == FlxButton.PRESSED && !optionsOverlay.visible && !comingsoonOverlay.visible){
			onSingle();
		}
	
		if(AboutButton.status == FlxButton.PRESSED && !optionsOverlay.visible && !comingsoonOverlay.visible){
			onAbout();
		}
		
		/*if(LE.status == FlxButton.PRESSED && !optionsOverlay.visible && !comingsoonOverlay.visible){
			onLESettings();
		//	comingsoonOverlay.visible = true;
		//	lvlEditorclosebtn.visible = true;
		//	soon.visible = true;
		}*/
		
		/*if(Ratebtn.status == FlxButton.PRESSED && !optionsOverlay.visible && !comingsoonOverlay.visible){
			onRate();
		}*/

		if(OptionsButton.status == FlxButton.PRESSED && !optionsOverlay.visible && !comingsoonOverlay.visible){
			optionsOverlay.visible = true;
			settingsTxt.visible = true;
			musicSwitch.visible = true;
			sfxSwitch.visible = true;
			clSwitch.visible = true;
			jswitch.visible = true;
			closebtn.visible = true;
		}
		
		/*if(lvlEditorclosebtn.status == FlxButton.PRESSED){
			comingsoonOverlay.visible = false;
			lvlEditorclosebtn.visible = false;
			soon.visible = false;
		}*/
		
		if(closebtn.status == FlxNinePatchButton.PRESSED){
			optionsOverlay.visible = false;
			settingsTxt.visible = false;
			musicSwitch.visible = false;
			sfxSwitch.visible = false;
			clSwitch.visible = false;
			jswitch.visible = false;
			closebtn.visible = false;
		}
		
		
		
		title1.setColor(colorGen);
		title2.setColor(colorGen);
		
		if(player.mode == 2) 
		{
			int random = random();
			switch(random){
				case 1:
					spawnEnemy(4);
				break;
				case 2:
					spawnMage(4);
				break;
				case 3:
					spawnEnemy(4);
				break;
				default:spawnEnemy(4);
				break;
			}
		}
		if(player.mode == 4)
		{
			int random = random();
			switch(random){
				case 1:
					spawnEnemy(2);
					break;
				case 2:
					spawnMage(2);
					break;
				case 3:
					
					
					spawnSkeleton(2);
					break;
				default:spawnEnemy(2);
					break;
			}
		}

		FlxG.collide(level,player);
		FlxG.collide(level,mages);
		FlxG.collide(level,skeletons);
		FlxG.collide(level,enemies);
		FlxG.collide(enemies, _bullets,doEnemyDamage);
		FlxG.collide(_bullets, skeletons, doSkeletonDamage);
		FlxG.collide(level,_skelGibs);
		FlxG.collide(_bullets, mages, doMageDamage);
		FlxG.collide(mages,level);
	}
	
	/*private Color randomColor(){
		Random gen = new Random();
		
		int r = gen.nextInt(255);
		int g = gen.nextInt(255);
		int b = gen.nextInt(255);
		
		
Color color = new Color(r,g,b);
		
		return color;
	}
*/
	private void spawnEnemy(int side)
	{
		if(canSpawn == true){	
			canSpawn = false;
			spawnCoolDown.start(1,1,Cooldown);	
		if(side == 2)
		{
		enemy1 = new Enemy(rightSideX,rightSideY,16,16,500,1); //y = 60		
		enemy1.followSprite(player);
		enemies.add(enemy1);
		}
		if(side == 4)
		{
		enemy1 = new Enemy(leftSideX,leftSideY,16,16,500,1); //y = 60		
		enemy1.followSprite(player);
		enemies.add(enemy1);
		}
		}
	}
	private void spawnMage(int side) 
	{
		
		if(canSpawn == true){	
			canSpawn = false;
			spawnCoolDown.start(1,1,Cooldown);
		if(side == 2)
		{
			mage1 = new Mage(rightSideX,rightSideY,16,16,_bullets,2,1);
			mage1.followSprite(level,player);
			mage1.goLeft();
			mages.add(mage1);
		}
		if(side == 4)
		{
			mage1 = new Mage(leftSideX,leftSideY,16,16,_bullets,2,1);
			mage1.followSprite(level,player);
			mages.add(mage1);
		}}
	}
	private void spawnSkeleton(int side)
	{
		if(canSpawn == true){	
		canSpawn = false;
		spawnCoolDown.start(1,1,Cooldown);
		if(side == 2 ){
			skeleton1 = new Skeleton(rightSideX,rightSideY,16,16,_skelGibs);
			skeleton1.watchLevel(level);
			skeleton1.goRight();
			skeletons.add(skeleton1);
		}
		if(side == 4)
		{
			skeleton1 = new Skeleton(leftSideX,leftSideY,16,16,_skelGibs);
			skeleton1.watchLevel(level);
			skeleton1.goLeft();
			skeletons.add(skeleton1);
		}
		}
	}
	
	IFlxTimer Cooldown = new IFlxTimer(){

		@Override
		public void callback(FlxTimer p1)
		{
			canSpawn = true;
		}	
	};
	
	private void onSingle(){
		FlxG.fade(0xff000000,1,onFade);
		//FlxG.switchState(new LevelsState());
	}
	
	private void onAbout(){
		FlxG.switchState(new LTPState() );
	}
/*
	private void onSettings(){
		FlxG.switchState(new Settings());
	}
*/
	/*private void onLESettings(){
		FlxG.switchState(new PlayStateLESettings());
		
	}*/
	
	//private void onRate(){
	//FlxU.openURL("");//Url of game in playstore. Will be added once its released.
	//}
	/*private void OnPress(){
	 FlxU.openURL("http://www.newgrounds.com/audio/listen/516336");
	 }*/
	
	public int random()
	{
		Random random = new Random();
		return showRandomInteger(start,end,random);
	}

	public static int showRandomInteger(int astart,int aend, Random arandom)
	{
		//get the range,casting to long to avojd overflow problrms
		long range = (long)aend - (long)astart + 1;
		//compute a fraction of the range,0 
		long fraction = (long)(range * arandom.nextDouble());
		int randomNumber =  (int)(fraction + astart);    
		return randomNumber;
	}
	 
	IFlxTimer showPText = new IFlxTimer()
	{
		@Override
		public void callback(FlxTimer flxTimer)
		{
			cprogressText.visible = false;
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
	
	IFlxCollision doMageDamage = new IFlxCollision()
	{
		@Override 
		public void callback(FlxObject Bullet, FlxObject Mage)
		{									
			//add(new FlxText(300,100 , 100, "Test: Enemy killed."));	
			Mage.hurt(1);
		}
	};	
	 
	public IFlxCamera onFade = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new WorldLevelSelect());//changed this
		}
	};
	
		public IFlxCamera onFade2 = new IFlxCamera()
		{
				@Override
				public void callback()
				{
						FlxG.switchState(new TypeOfMultiplayer());//changed this
				}
		};


}
