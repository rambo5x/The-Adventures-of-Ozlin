package com.dvreiter.starassault.Menu.Multiplayer;

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
import com.dvreiter.starassault.Menu.*;
import com.dvreiter.starassault.LevelEditor.*;

public class TypeOfMultiplayer extends FlxState
{		
	private FlxText waitText;
	private FlxButton LButton, TButton;
	private FlxButton backbtn;

	@Override
	public void create()
	{			
		FlxG.setBgColor(0xFF00CCFF);
		
		LButton = new FlxButton(160, 110, "Level Editor", new IFlxButton(){@Override public void callback(){onLan();}});
		add(LButton);	

		TButton = new FlxButton(160, 135, "Keyboard/Touch Multiplayer", new IFlxButton(){@Override public void callback(){onKT();}});
		add(TButton);
		
		backbtn = new FlxButton(5, 215, "Back", new IFlxButton(){@Override public void callback(){onBack();}});
		add(backbtn);	

	}
    @Override
	public void update(){	

		super.update();
	}

	private void onLan(){
		FlxG.switchState(new PlayStateLESettings());
	 
	}
	private void onKT(){
		FlxG.switchState(new KTMultiplayerState());
	}
	private void onBack(){
		FlxG.switchState(new MenuState());
	}
} 
