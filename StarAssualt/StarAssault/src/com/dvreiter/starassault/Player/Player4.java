package com.dvreiter.starassault.Player;

import org.flixel.*;
import org.flixel.event.*;
import org.flixel.ui.*;
import com.dvreiter.starassault.*;
import com.dvreiter.starassault.Objects.*;

public class Player4 extends FlxSprite {   

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

    @Override
    public Player4(int x, int y, int width, int height, FlxGroup Bullets,FlxEmitter Gibs) {
        super(width, height);
		isUpsideDown = false;
		hasGravityToggle = false;
		isFlying = false;
		hasFlyingToggle = false;
        this.x = x;
        this.y = y;
        int runSpeed = 100;
        _jumpPower = 150;
        maxVelocity.y = _jumpPower;
        drag.x = runSpeed*4;
        maxVelocity.x = runSpeed;
        acceleration.y = 200;

        loadGraphic("budderking.png", true, true, 16, 16);    
		width = 14;
		height = 15;
//		offset.x = 1;
		//	offset.y = 1;

		addAnimation("stand", new int[]{0},0, false);
		addAnimation("walk", new int[]{0,1,2},4,true);
		addAnimation("jump", new int[]{6,7}, 1, true);
		addAnimation("Ustand", new int[]{3}, 0, false);
		addAnimation("Uwalk", new int[]{3,4,5}, 4, true);
		addAnimation("Ujump", new int[]{8,9}, 1, true);
		play("stand");
		
		health = 10;

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

	public void setHasGravityToggle(boolean hasGravityToggle) {
		this.hasGravityToggle = hasGravityToggle;
	}

	public void setHasFlyingToggle(boolean hasFlyingToggle){
		this.hasFlyingToggle = hasFlyingToggle;
	}

	public void toggle() {
		if(!hasGravityToggle)
			return;
		if(isUpsideDown) {
			isUpsideDown = false;
			//angle = 360;
			acceleration.y = 200;
			play("walk");
			return;
		} else {
			if(!isUpsideDown){
				isUpsideDown = true;
				//angle = 180;
				acceleration.y = -200;
				play("Uwalk");
				return;
			}
		}
	}

	public void fToggle(){
		if(!hasFlyingToggle)
			return;
		if(!isFlying)
			isFlying = true;
		velocity.y = - maxVelocity.y/2.7f;
		return;
	}

    @Override
    public void update() {      
	/*	if(!exists){
			_restart += FlxG.elapsed;
			if(_restart > 2){
				exists = true;
				visible = true;
				flicker(0.6f);
			}
			return;
		}
	*/
		if(!alive){
			_restart += FlxG.elapsed;
			if(_restart > 2){
				alive = true;
				visible = true;
				setSolid(true);
				health = 10;
				flicker(0.6f);
			}
			return;
		}
	
		acceleration.x = 0;

		if(FlxG.keys.LEFT)
		{
			if(isTouching(FlxObject.FLOOR))
				if(!isUpsideDown){
					play("walk");
				}
			if(isTouching(FlxObject.CEILING))
				if(isUpsideDown){
					play("Uwalk");
				}
			setFacing(LEFT);
			acceleration.x -= drag.x;
		}
		else if(FlxG.keys.RIGHT)
		{
			if(isTouching(FlxObject.FLOOR))
				if(!isUpsideDown){
					play("walk");}
			if(isTouching(FlxObject.CEILING))
				if(isUpsideDown){
					play("Uwalk");
				}
			setFacing(RIGHT);
			acceleration.x += drag.x;
		}else{
			if(isTouching(FlxObject.FLOOR))
				if(!isUpsideDown){play("stand");}
			if(isTouching(FlxObject.CEILING)) {
				if(isUpsideDown){play("Ustand");}
			}
		}

		if(FlxG.keys.UP)
		{
			if(!isUpsideDown) {
				play("jump");
			}
			else {
				play("Ujump");
			}
			if(!isUpsideDown) {
				if(isTouching(FlxObject.FLOOR)) {
					velocity.y = - maxVelocity.y/1.2f;
				}
			} else {
				if(isTouching(FlxObject.CEILING)) {
					velocity.y = + maxVelocity.y/1.2f;
				}
			}
		}

		_aim = getFacing();

		if(FlxG.keys.BACKSLASH) 
		{		
			if(!_justShoot)
			{
				if(getFlickering())
					flicker(1);
				else
				{
					getMidpoint(_point);
					((Bullet) _bullets.recycle(Bullet.class)).shoot(_point,_aim);
				}
				_justShoot = true;
			}	
		}
		else
			_justShoot = false;

		if(FlxG.keys.DOWN){
			toggle();
		}
		if(FlxG.keys.COMMA){
			fToggle();
		}
	}

	@Override public void hurt(float Damage)
	{
		flicker(0.6f);//0.2f
		FlxG.score += 10;
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
