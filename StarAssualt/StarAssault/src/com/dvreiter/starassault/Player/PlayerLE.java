package com.dvreiter.starassault.Player;

import org.flixel.*;
import org.flixel.event.*;
import org.flixel.ui.*;
import com.dvreiter.starassault.*;
import com.dvreiter.starassault.Objects.*;

public class PlayerLE extends FlxSprite {   

	protected FlxGroup _bullets;
	protected int _aim;
	protected FlxEmitter _gibs;
	protected float _restart;

    private int _jumpPower;
	FlxVirtualPad pad;
	private boolean isUpsideDown;
	private boolean hasGravityToggle;
	private boolean isFlying;
	private boolean hasFlyingToggle;
	private boolean _justShoot;
	private boolean hasPhysics;

    @Override
    public PlayerLE(int x, int y, int width, int height, FlxGroup Bullets,FlxEmitter Gibs, FlxVirtualPad pad) {
        super(width, height);
		this.pad = pad;	
		isUpsideDown = false;
		hasGravityToggle = false;
		hasPhysics = true;
		isFlying = false;
		hasFlyingToggle = false;
        this.x = x;
        this.y = y;
        int runSpeed = 100;
        _jumpPower = 150;
        maxVelocity.y = _jumpPower;
        drag.x = runSpeed*4;
		drag.y = drag.x;
        maxVelocity.x = runSpeed;
     

        loadGraphic("LEShip.png", true, true, 16, 16);    
		width = 14;
		height = 15;
//		offset.x = 1;
		//	offset.y = 1;

		

		_bullets = Bullets;
		_gibs = Gibs;
    }

	@Override
	public void destroy()
	{
		// TODO: Implement this method
		super.destroy();
		_bullets = null;
		_gibs = null;
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

	@Override 
	public void kill()
	{
		if(!alive)
			return;
		setSolid(false);
		super.kill();
		flicker(0);
		exists = true;
		visible = false;
		velocity.make();
		acceleration.make();
		FlxG.camera.shake(0.005f,0.35f);
		FlxG.camera.flash(0xffd8eba2,0.35f);
		if(_gibs != null)
		{
			_gibs.at(this);
			_gibs.start(true,5,0,50);
		}
	}


}	

