package com.dvreiter.starassault.LevelEditor;


import org.flixel.*;
import org.flixel.event.*;
import org.flixel.ui.*;
import com.dvreiter.starassault.*;
import com.dvreiter.starassault.Objects.*;

public class PlayerLE extends FlxSprite {   

	protected float _restart;

    private int _jumpPower;
	FlxVirtualPad pad;

    @Override
    public PlayerLE(int x, int y, FlxVirtualPad pad) {
        super(x,y);
		this.pad = pad;	
	  
        int runSpeed = 100;
        _jumpPower = 150;
        maxVelocity.y = _jumpPower;
        drag.x = runSpeed*4;
		drag.y = drag.x;
        maxVelocity.x = runSpeed;
     
        loadGraphic("LEShip.png", true, true, 16, 16);    
		width = 14;
		height = 15;
    }

	@Override
	public void destroy()
	{
		// TODO: Implement this method
		super.destroy();
	}

    @Override
    public void update(){
		acceleration.y = 0;
		if(!alive)
		{
			_restart += FlxG.elapsed;
			if(_restart > 2)
				FlxG.resetState();
			return;
		}
		
		acceleration.x = 0;
		if(FlxG.keys.LEFT || pad.buttonLeft.status == FlxButton.PRESSED)
		{
			acceleration.x -= drag.x;
		}
		else if(FlxG.keys.RIGHT || pad.buttonRight.status == FlxButton.PRESSED)
		{
			acceleration.x += drag.x;
		}

		if(pad.buttonUp.status == FlxButton.PRESSED || FlxG.keys.UP)
		{
			acceleration.y -= drag.y;
		}
		if(pad.buttonDown.status == FlxButton.PRESSED|| FlxG.keys.DOWN)
		{
			acceleration.y += drag.y;
		}
	}

	@Override
	public void hurt(float Damage)
	{
		Damage = 0;
		if(getFlickering())
			return;
		flicker(1.3f);
		super.hurt(Damage);
	}
}

