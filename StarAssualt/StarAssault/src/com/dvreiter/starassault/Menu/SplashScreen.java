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
import org.flixel.system.*;

public class SplashScreen extends FlxState
{		
	private FlxText count;
	private boolean complete;
	private FlxTimer timer;

	@Override
	public void create()
	{			
	
		timer = new FlxTimer();
		timer.start(3,1,switchState);
		
		count = new FlxText(10, 10, 200, "Testing: ");
		add(count);
	}
    @Override
	public void update(){	

		super.update();
		if(!complete)
			 count.setText("Testing: " + timer.getTimeLeft());
	}
	
	IFlxTimer switchState = new IFlxTimer()
	{
		@Override
		public void callback(FlxTimer flxTimer)
		{
			count.setText("Testing: Complete");
			complete = true;
			FlxG.fade(0xff000000,1, onFade);
		}
	};

	public IFlxCamera onFade = new IFlxCamera()
	{
		@Override
		public void callback()
		{
			FlxG.switchState(new MenuState());
		}

	};


} 
