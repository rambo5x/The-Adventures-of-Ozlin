package com.dvreiter.starassault.Menu;

import org.flixel.FlxButton;
import org.flixel.FlxG;
import org.flixel.FlxPoint;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.event.IFlxButton;
import org.flixel.*;
import org.flixel.ui.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Levels.*;
import com.dvreiter.starassault.*;
import org.flixel.ui.event.*;
import com.badlogic.gdx.utils.*;

public class WorldLevelSelect extends FlxState
{		
	private FlxText titleText, worldtitleText, cprogressText, lockedText,comingsoonText;
	private FlxTimer timer;
	private FlxButton backbtn, clearbtn;
	//private FlxNinePatchButton leftArrow, RightArrow;
	private FlxSprite grassWorldIcon, dungeonWorldIcon, hellWorldIcon;
	private FlxSprite leftArrow, rightArrow, worldlock;
	private FlxTileblock levelChapterOverlay, levelOverlay;
	private FlxTilemap level;
	private boolean pressedLeft, pressedRight, pressedGrass,pressedStone,pressedHell;
	private boolean dlocked, hlocked;
	private FlxSave gameSave;
	private int progressC;
	private int[] data = {
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,                                         
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,// changed this
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,           
		7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,
		7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,7,7,7,7,8,9,7,
		7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7};	
	private int[] data2 = {
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,                                         
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,// changed this
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,           
		2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,
		2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,
		2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2
		};	
	private int[] data3 = {
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,                                         
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,// changed this
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,           
		11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,   
		11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,   
		11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,   
		};	
	
	@Override
	public void create()
	{			
		FlxG.setBgColor(0xFF00CCFF);//0xFF00CCFF or FlxG.black
		
		level = new FlxTilemap();
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 25), "tilemap.png", 16, 16);
		level.setSolid(true);
		add(level);
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
	//	if(progress > 8)
	//		progress = 2;
			
		 progressC = progress;
		 
		timer = new FlxTimer();

		lockedText = new FlxText(180, 180, 100, "LOCKED!");
		lockedText.visible = false;
		add(lockedText);
		
		comingsoonText = new FlxText(180, 180, 100, "Coming Soon!");
		comingsoonText.visible = false;
		add(comingsoonText);
			
	   	 backbtn = new FlxButton(5, 215, "Back", new IFlxButton(){@Override public void callback(){onBack();}});
		 add(backbtn);		
		  
		 levelChapterOverlay = new FlxTileblock(60,5,290,35);//y=50original
		 levelChapterOverlay.makeGraphic(290, 35, FlxG.BLACK);// 390, 230, 0x50FFFFFF,0xFF00CCFF or FlxG.black
		 levelChapterOverlay.setAlpha(0.5f);
		 levelChapterOverlay.setSolid(true);
		 levelChapterOverlay.immovable = true;
		 levelChapterOverlay.scrollFactor.x = levelChapterOverlay.scrollFactor.y = 0;
		 add(levelChapterOverlay);
	
		// clearbtn = new FlxButton(160, 155, "Clear Progress", new IFlxButton(){@Override public void callback(){onClear();}});
		// add(clearbtn);	
		 
		 titleText = new FlxText(70, 10, 300, "Level Chapter Select");//350
		 titleText.setSize(20);
		 titleText.antialiasing = true;
		 add(titleText);
		 
		 levelOverlay = new FlxTileblock(100, 60, 200, 120);//y=50original
		 levelOverlay.makeGraphic(200, 120, FlxG.BLACK);// 390, 230, 0x50FFFFFF
		 levelOverlay.setAlpha(0.5f);
		 levelOverlay.setSolid(true);
		 levelOverlay.immovable = true;
		 levelOverlay.scrollFactor.x = levelOverlay.scrollFactor.y = 0;
		 add(levelOverlay);
		 
		 worldtitleText = new FlxText(165, 65, 90, "World 1");//350
		 worldtitleText.setSize(16);
		 worldtitleText.antialiasing = true;
		 add(worldtitleText);
		 
		 leftArrow = new FlxSprite(100,110);
		 leftArrow.loadGraphic("leftarrow.png",true,true,16,15);
		 leftArrow.immovable = true;
		 add(leftArrow);
		 
		 pressedLeft = false;
		 
		 rightArrow = new FlxSprite(284,110);
		 rightArrow.loadGraphic("rightarrow.png",true,true,16,15);
		 rightArrow.immovable = true;
		 add(rightArrow);
		 
		 pressedRight = false;
		  
		hellWorldIcon = new FlxSprite(150,90);//840,70
		hellWorldIcon.loadGraphic("hellworldicon.png", true, true, 100, 60);
		hellWorldIcon.visible = false;
		add(hellWorldIcon);
		  
		hlocked = false;
		
		dungeonWorldIcon = new FlxSprite(150,90);//480,70
		dungeonWorldIcon.loadGraphic("dungeonworldicon.png", true, true, 100, 60);
		dungeonWorldIcon.visible = false;
		add(dungeonWorldIcon);

		dlocked = false;
		
		grassWorldIcon = new FlxSprite(150,90);//120,70
		grassWorldIcon.loadGraphic("grassworldicon.png", true, true, 100, 60);
		grassWorldIcon.visible = true;//visible.true; originally
		add(grassWorldIcon);
		
		worldlock = new FlxSprite(175,105);
		worldlock.loadGraphic("worldlock.png",true,true,50,30);
		worldlock.visible = false;
		add(worldlock);
	}
    @Override
	public void update(){	

		 if(leftArrow.overlapsPoint(new FlxPoint(FlxG.mouse.x, FlxG.mouse.y)) && FlxG.mouse.justPressed()){
		 pressedLeft = true;

		 if(pressedLeft = true){
		 if(!grassWorldIcon.visible && dungeonWorldIcon.visible && !hellWorldIcon.visible){// visible recently used
		 dungeonWorldIcon.visible = false;
		 FlxG.setBgColor(0xFF00CCFF);
		 level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 25), "tilemap.png", 16, 16);
		 worldtitleText.setText("World 1");
		 worldlock.visible = false;
		 grassWorldIcon.visible = true;
		 }else if(!grassWorldIcon.visible && !dungeonWorldIcon.visible && hellWorldIcon.visible){
		 hellWorldIcon.visible = false;
		 FlxG.setBgColor(FlxG.BLACK);
		 level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data2), 25), "tilemap.png", 16, 16);		 
		 worldtitleText.setText("World 2");
		 if(progressC > 11){
		 worldlock.visible = false;
		 dlocked = false;
		 }else{
		 worldlock.visible = true;
		 dlocked = true;
		 }
		 dungeonWorldIcon.visible = true;
		}
	}
}
		 if(rightArrow.overlapsPoint(new FlxPoint(FlxG.mouse.x, FlxG.mouse.y)) && FlxG.mouse.justPressed()){
		 pressedRight = true;

		 if(pressedRight = true){
		 if(grassWorldIcon.visible && !dungeonWorldIcon.visible && !hellWorldIcon.visible){
		 grassWorldIcon.visible = false;
		 FlxG.setBgColor(FlxG.BLACK);
		 level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data2), 25), "tilemap.png", 16, 16);
		 worldtitleText.setText("World 2");
		 if(progressC > 11){
		 worldlock.visible = false;
		 dlocked = false;
		 }else{
		 worldlock.visible = true;
		 dlocked = true;
		}
		 dungeonWorldIcon.visible = true;
		 }else if(!grassWorldIcon.visible && dungeonWorldIcon.visible && !hellWorldIcon.visible){
		 dungeonWorldIcon.visible = false;
	     FlxG.setBgColor(FlxG.RED);
		 level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data3), 25), "tilemap.png", 16, 16);
		 worldtitleText.setText("World 3");
			 if(progressC > 16){
				 worldlock.visible = false;
				 hlocked = false;
			 }else{
				 worldlock.visible = true;
				 hlocked = true;
			 }
		 worldlock.visible = true;
		 hellWorldIcon.visible = true;
		}
	}
}
		if(grassWorldIcon.overlapsPoint(new FlxPoint(FlxG.mouse.x, FlxG.mouse.y)) && FlxG.mouse.justPressed()){
			pressedGrass = true;
			
			if(pressedGrass = true){
				if(grassWorldIcon.visible && !dungeonWorldIcon.visible && !hellWorldIcon.visible)
				FlxG.fade(FlxG.GREEN,1,onFade);
			}
		}
		if(dungeonWorldIcon.overlapsPoint(new FlxPoint(FlxG.mouse.x, FlxG.mouse.y)) && FlxG.mouse.justPressed()){
			pressedStone = true;

			if(pressedStone = true){
				if(!grassWorldIcon.visible && dungeonWorldIcon.visible && !hellWorldIcon.visible)
					if(dlocked){
					lockedText.visible = true;
					timer.start(3,1,showLText);
					}else{
					FlxG.fade(0xff000000,1,onFade2);
				}
			}
		}
		if(hellWorldIcon.overlapsPoint(new FlxPoint(FlxG.mouse.x, FlxG.mouse.y)) && FlxG.mouse.justPressed()){
			pressedHell= true;

			if(pressedHell = true){
				if(!grassWorldIcon.visible && !dungeonWorldIcon.visible && hellWorldIcon.visible)
					if(hlocked){
						comingsoonText.visible = true;
						timer.start(3,1,showCText);
					}else{
					   FlxG.fade(FlxG.RED,1,onFade3);
					}
			}
		}
			
		super.update();
		
	}
	
	private void onBack(){
		FlxG.switchState(new MenuState());
	}
	
	/*private void onClear(){
		if(grassWorldIcon.visible && !dungeonWorldIcon.visible && !hellWorldIcon.visible){
			gameSave.erase();
			cprogressText.visible = true;
			timer.start(3,1,showPText);
			
		}
	}
	*/ 
	IFlxTimer showPText = new IFlxTimer()
	{
		@Override
		public void callback(FlxTimer flxTimer)
		{
			cprogressText.visible = false;
		}
	};
	
	IFlxTimer showLText = new IFlxTimer()
	{
		@Override
		public void callback(FlxTimer flxTimer)
		{
			lockedText.visible = false;
		}
	};
	
	IFlxTimer showCText = new IFlxTimer()
	{
		@Override
		public void callback(FlxTimer flxTimer)
		{
			comingsoonText.visible = false;
		}
	};
	
	public IFlxCamera onFade = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new LevelsState());//changed this
		}
	};
	
	public IFlxCamera onFade2 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new DungeonLevelsState());//changed this
		}
	};
	
	public IFlxCamera onFade3 = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new LTPState());//changed this
		}
	};
} 

/*Velocity movement shit code
	 //	if(grassWorldIcon.x == 120){
			//		grassWorldIcon.x = 120;}
				    if(grassWorldIcon.x == -480 && dungeonWorldIcon.x == 120 && hellWorldIcon.x == 480){
					hellWorldIcon.velocity.x = 100;
					dungeonWorldIcon.velocity.x = 100;
					grassWorldIcon.velocity.x = 100;		
					if(grassWorldIcon.x == 120 && dungeonWorldIcon.x == 480 && hellWorldIcon.x == 840){
					hellWorldIcon.velocity.x = 0;
					dungeonWorldIcon.velocity.x = 0;
					grassWorldIcon.velocity.x = 0;
				}
		}
					
				    if(grassWorldIcon.x == -840 && dungeonWorldIcon.x == -480 && hellWorldIcon.x == 120){
					hellWorldIcon.velocity.x = 100;
				 	dungeonWorldIcon.velocity.x = 100;
				 	grassWorldIcon.velocity.x = 100;
			 		if(grassWorldIcon.x == -480 && dungeonWorldIcon.x == 120 && hellWorldIcon.x == 480){
					hellWorldIcon.velocity.x = 0;
				 	dungeonWorldIcon.velocity.x = 0;
				 	grassWorldIcon.velocity.x = 0;
			 		}
			 }
*/
