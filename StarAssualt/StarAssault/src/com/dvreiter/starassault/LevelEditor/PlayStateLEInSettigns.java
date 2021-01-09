package com.dvreiter.starassault.LevelEditor;


import org.flixel.FlxButton;
import org.flixel.FlxG;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.event.IFlxButton;
import org.flixel.*;
import org.flixel.ui.*;
import org.flixel.event.*;
import org.flixel.ui.FlxTab;
import org.flixel.ui.FlxTabGroup;
import org.flixel.ui.FlxUIGroup;
import org.flixel.ui.event.IFlxUIListener;
import org.flixel.system.input.*;
import java.io.*;
import android.os.*;
import com.badlogic.gdx.*;
import com.dvreiter.starassault.Levels.*;
import com.dvreiter.starassault.Menu.*;
import com.dvreiter.starassault.Tools.*;


public class PlayStateLEInSettigns extends FlxState
{		 

		private int levelSlot = 0;
    private FlxButton backbtn;
		public FlxState currentState;
		public FlxState PlaystateTwo;
		private ErrorReporter error;

		

		
		public FlxTileblock titleBlock1;
		private FlxTileblock titleBlock2;

		private FlxTileblock levelSelectBlock1;
		private FlxTileblock levelSelectBlock2;
		private FlxButton levelLeft;
		private FlxButton levelDisplay;
		private FlxButton levelRight;

		private FlxTileblock levelInfoBlock1;
		private FlxTileblock levelInfoBlock2;
		private FlxTileblock levelWHBlock1;
		private FlxTileblock levelWHBlock2;

		private FlxText title;

		private FlxSave guiAlpha;

		private float Alpha;
		
		private Boolean Music;

		private FlxText AlphaSettingsTxt;

		@Override
		public void create()
		{			

				guiAlpha = new FlxSave();
				guiAlpha.bind("Options");//Test
				//Save
				if(guiAlpha.data.get("Alpha",float.class) == null)
				{
						guiAlpha.data.put("Alpha", .7);
						guiAlpha.flush();
				}
				
				//load
				@SuppressWarnings("unchecked")
				float  Alp = guiAlpha.data.get("Alpha", float.class);
				
				levelSlot = (int)Alp;
				Alpha = Alp/10;
				
				
				//Save
				if(guiAlpha.data.get("Music", Boolean.class) == null)
				{
						guiAlpha.data.put("Music", true);
						guiAlpha.flush();
				}

				//load
				@SuppressWarnings("unchecked")
				Boolean mus = guiAlpha.data.get("Music", Boolean.class);

				Music = mus;
		
				error = new ErrorReporter();

				//Title Stuff
				titleBlock1 = new FlxTileblock(115, 8, 200, 34);//780, 400
				titleBlock1.makeGraphic(200, 34, 0xff218fb8);// 390, 230
				add(titleBlock1);

				titleBlock2 = new FlxTileblock(120, 10, 190, 30);//780, 400
				titleBlock2.makeGraphic(190, 30, 0xff209ece);// 390, 230
				add(titleBlock2);

				title = new FlxText(117, 20, 300, "Level Editor Settings");
				title.setSize(15);							
				add(title);


				//LevelSelect
				
				
				levelSelectBlock1 = new FlxTileblock(5, 43, 254, 44);//780, 400
				levelSelectBlock1.makeGraphic(254, 44, 0xff218fb8);// 390, 230
				add(levelSelectBlock1);

				levelSelectBlock2 = new FlxTileblock(10, 45, 244, 40);//780, 400
				levelSelectBlock2.makeGraphic(244, 40, 0xff209ece);// 390, 230
				add(levelSelectBlock2);
				
				levelLeft = new FlxButton(12, 60, "<---", new IFlxButton(){@Override public void callback()
								{onLevelLeft();}});
				add(levelLeft);	

				levelDisplay = new FlxButton(levelLeft.x + levelLeft.width, levelLeft.y, "Level Design", new IFlxButton(){@Override public void callback()
								{onLevelDisplay();}});			
				add(levelDisplay);	

				levelRight = new FlxButton(levelDisplay.x + levelDisplay.width, levelLeft.y, "--->", new IFlxButton(){@Override public void callback()
								{onLevelRight();}});
				add(levelRight);	

				AlphaSettingsTxt = new FlxText((levelSelectBlock1.x + (levelSelectBlock1.width/2))-(levelDisplay.width/2),levelSelectBlock2.y+2,200,"Gui Alpha Selector");
				add(AlphaSettingsTxt);
				
				//LevelInfo

				levelInfoBlock1 = new FlxTileblock(265, 43, 128, 134);//780, 400
				levelInfoBlock1.makeGraphic(128, 134, 0xff218fb8);// 390, 230
				add(levelInfoBlock1);

				levelInfoBlock2 = new FlxTileblock(270, 45, 118, 130);//780, 400
				levelInfoBlock2.makeGraphic(118, 130, 0xff209ece);// 390, 230
				add(levelInfoBlock2);

				titleBlock1 = new FlxTileblock(5, 93, 254, 84);//780, 400
				titleBlock1.makeGraphic(254, 84, 0xff218fb8);// 390, 230
				add(titleBlock1);

				titleBlock2 = new FlxTileblock(10, 95, 244, 80);//780, 400
				titleBlock2.makeGraphic(244, 80, 0xff209ece);// 390, 230
				add(titleBlock2);
				
				//GoBack
				backbtn = new FlxButton(5, 215, "Back", new IFlxButton(){@Override public void callback()
								{onBack();}});
				add(backbtn);	
				
				updateTimeDisplay();
		}


    @Override
		public void update()
		{	
						//levelName		
						super.update();
		}

		private void updateTimeDisplay()
		{
				if (levelSlot < 2)
				{
						levelSlot = 2;
				}
				if (levelSlot > 10)
				{
						levelSlot = 10 ;
				}
				if (levelSlot == 2)
				{
						levelLeft.label.setText("|");
				}
				if (levelSlot == 3)
				{
						levelLeft.label.setText("<---");
				}
				if (levelSlot == 10)
				{
						levelRight.label.setText("|");
				}
				if (levelSlot == 10 - 1)
				{
						levelRight.label.setText("--->");
				}
				levelDisplay.label.setText(levelSlot+"0%");
				
				guiAlpha.data.put("Alpha", levelSlot);
				guiAlpha.flush();
				
				
		}	
		
	

		private void onLevelLeft()
		{
						levelSlot--;
						updateTimeDisplay();
		}
		
		public void onLevelRight()
		{			
				levelSlot++;
				updateTimeDisplay();
		}
		
		public void onLevelDisplay()
		{
				guiAlpha.data.put("Alpha", levelSlot/10);
				guiAlpha.flush();
		}

		//Back
		private void onBack()
		{
				updateTimeDisplay();
				FlxG.switchState(new PlayStateLESettings());
		}
}
