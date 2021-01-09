package com.dvreiter.starassault.Tools;
import org.flixel.*;
import org.flixel.event.*;

public class ButtonHoldIncrementer
{
	private FlxTimer incrementCoolDown;
	private boolean canIncrement = true;;

	public int incrementAmount;
	public int incrementTimes;
	
	public ButtonHoldIncrementer()
	{
	incrementAmount = 0;
	incrementTimes = 0;
	incrementCoolDown = new FlxTimer();
	}

	
	
	
	
	public int update(FlxButton button)
	{
		if(incrementTimes == 1)
		{
			incrementAmount = 1;
		}
		if(incrementTimes == 2)
		{
			incrementAmount = 2;
		}
		if(incrementTimes == 6)
		{
			incrementAmount = 5;
		}
		if(incrementTimes == 10)
		{
			incrementAmount = 10;
		}
		if(incrementTimes == 20)
		{
			incrementAmount = 30;
		}
		
		
		if(button.status == 2)
		{
			if(canIncrement == true)
			{
				incrementCoolDown.start(.35f,1,Cooldown);
				canIncrement = false;
				incrementTimes++;
				return incrementAmount;
			}else
			{
				return 0;
			}
		}else
		{
		incrementAmount = 0;
		incrementTimes = 0;	
		return 0;
		}
	}
	
	IFlxTimer Cooldown = new IFlxTimer(){

		@Override
		public void callback(FlxTimer p1)
		{
			canIncrement= true;
		}	
	};

	
	
		
	
}
