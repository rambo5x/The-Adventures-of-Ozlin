package com.dvreiter.starassault.Objects;

import org.flixel.*;

public class MageBall extends FlxSprite
{

	public float speed;

	public MageBall()
	{
		super();
		loadGraphic("Mage Ball.png",true,true,12,8);
		
		
		addAnimation("left",new int[]{6,7,8,9,10,11});
		
		addAnimation("right",new int[]{0,1,2,3,4,5});

		speed = 360;//360
	}

	@Override
	public void update()
	{
		if(!alive)
		{
			if(finished)
				exists = false;
		}
		else if(touching > NONE)
			kill();
	}

	//public void shoot(FlxPoint Location, int Aim)
	public void shoot(FlxPoint Location, float Ex,float Ey,float Px,float Py)
	{
		super.reset(Location.x-width/2,Location.y-height/2);
		setSolid(true);
		play("left");
		velocity.x = ((-Ex/16) - (-Px/16))*50;
		velocity.y = ((-Ey/16) - (-Py/16))*50;
		
		
//		switch(Aim)
//		{
//		/*	case UP:
//				play("up");
//				velocity.y = -speed;
//				break;
//			case DOWN:
//				play("down");
//				velocity.y = speed;
//				break;*/
//			case LEFT:
//				play("left");
//				velocity.x = -speed;
//				break;
//			case RIGHT:
//				play("right");
//				velocity.x = speed;
//				break;
//			default:
//				break;
//		}
	}
}
