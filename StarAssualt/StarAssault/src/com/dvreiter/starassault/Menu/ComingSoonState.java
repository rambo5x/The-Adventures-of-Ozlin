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

public class ComingSoonState extends FlxState
{		
	private FlxText expText, titleText;
	private FlxButton backbtn;

	@Override
	public void create()
	{			
		//FlxG.setBgColor(FlxG.BLUE);
		
		backbtn = new FlxButton(5, 215, "Back", new IFlxButton(){@Override public void callback(){onBack();}});
		add(backbtn);		

		/*titleText = new FlxText(30, 20, 350, "The Adventures Of Ozlin 2!");
		titleText.setSize(20);
		titleText.antialiasing = true;
		titleText.setColor(FlxG.RED);
		add(titleText);
		
		expText = new FlxText(30,60,330,"Buy the full and complete Adventures Of Ozlin 2 with many new features added.\n Some of which includes no ads, better graphics, level editor, more levels and multiplayer minigames! Tap/Click on the Chest icon below to open the playstore.");
		expText.setSize(12);
		expText.antialiasing = true;
		add(expText);*/
	}
    @Override
	public void update(){	

		super.update();
	}
	
	private void onBack(){
		FlxG.switchState(new MenuState());
	}
} 
