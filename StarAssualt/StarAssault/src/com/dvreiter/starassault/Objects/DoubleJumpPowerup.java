package com.dvreiter.starassault.Objects;

import org.flixel.*;
import com.dvreiter.starassault.Objects.*;
import com.dvreiter.starassault.Player.*;

public class DoubleJumpPowerup extends FlxSprite
{
	
	public DoubleJumpPowerup(int x, int y)
	{
		super(x, y);
		loadGraphic("invinciblePow.png", true, true, 16, 16);
		addAnimation("doublePow",new int[]{0,1,2},4,true);
		immovable = true;
		play("doublePow");
	}
	@Override 
	public void destroy()
	{
		super.destroy();

	}

	@Override 
	public void update()
	{
		super.update();
		
		
	}
	
}
