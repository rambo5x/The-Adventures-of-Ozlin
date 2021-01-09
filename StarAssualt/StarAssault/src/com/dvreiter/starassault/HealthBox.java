package com.dvreiter.starassault;

import org.flixel.FlxSprite;
import com.dvreiter.starassault.*;

public class HealthBox extends FlxSprite
{
	
	public HealthBox(int x, int y, int width, int height)
	{
		super(width, height);
		this.x = x;
		this.y = y;
		loadGraphic("untitled.png", true, true, 16, 16);
		addAnimation("4HP", new int[]{3}, 0, false);
		addAnimation("3HP", new int[]{2}, 0, false);
		addAnimation("2HP", new int[]{1}, 0, false);
		addAnimation("1HP", new int[]{0}, 0, false);		
		immovable = true;
		play("4HP");
		
		scrollFactor.x = scrollFactor.y = 0;
		y = 0;
		setSolid(false);
		moves = false;
	}
	
}
