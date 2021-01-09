package com.dvreiter.starassault.Objects;

import org.flixel.*;

public class TurretBullet extends FlxSprite
{

	public float speed;

	public TurretBullet()
	{
		super();
		loadGraphic("Turretbullet.png",true,true,12,8);
		offset.x = 1;
		offset.y = 1;

		addAnimation("left",new int[]{0});
		addAnimation("right",new int[]{1});

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
	
	public void shoot(FlxPoint Location, int Aim)
	{
		super.reset(Location.x-width/2,Location.y-height/2);
		setSolid(true);
		switch(Aim)
		{
				/*	case UP:
				 play("up");
				 velocity.y = -speed;
				 break;
				 case DOWN:
				 play("down");
				 velocity.y = speed;
				 break;*/
			case LEFT:
				play("left");
				velocity.x = -speed;
				break;
			case RIGHT:
				play("right");
				velocity.x = speed;
				break;
			default:
				break;
		}
		}
	}
