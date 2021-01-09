package com.dvreiter.starassault.Objects;

import org.flixel.*;

public class TreeRock extends FlxSprite
{

	public float speed;

	public TreeRock()
	{
		super();
		loadGraphic("treerock.png",true,true,16,16);

		speed = 360;//360
		angularAcceleration = 250;
		angularVelocity = 500;
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
		velocity.x = ((-Ex/16) - (-Px/16))*20;
		velocity.y = ((-Ey/16) - (-Py/16))*20;


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
