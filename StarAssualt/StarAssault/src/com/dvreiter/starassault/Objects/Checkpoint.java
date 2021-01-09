package com.dvreiter.starassault.Objects;

import org.flixel.*;
import com.dvreiter.starassault.Objects.*;
import com.dvreiter.starassault.Player.*;

public class Checkpoint extends FlxSprite
{
	@Override
	public Checkpoint(int x, int y){
		super(x,y);
		loadGraphic("checkpoint.png", true, true, 16, 16);
		width = 16;//12
		height = 16;
		immovable = true; 
	}

	@Override
	public void update()
	{
		// TODO: Implement this method
		super.update();
	}
}
