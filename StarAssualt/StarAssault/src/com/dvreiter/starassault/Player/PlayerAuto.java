package com.dvreiter.starassault.Player;

import org.flixel.*;
import org.flixel.event.*;
import org.flixel.ui.*;
import com.dvreiter.starassault.*;
import com.dvreiter.starassault.Objects.*;

public class PlayerAuto extends FlxSprite {   

	protected FlxGroup _bullets;
	protected int _aim;
	protected FlxEmitter _gibs;
	protected float _restart;

	private FlxSound sfxShoot;
	
    private int _jumpPower;
	FlxVirtualPad pad;
	private boolean isUpsideDown;
	private boolean hasGravityToggle;
	private boolean isFlying;
	private boolean hasFlyingToggle;
	public int mode;
	private FlxTimer left;
	private FlxTimer leftShoot;
	private FlxTimer right;
	private FlxTimer rightShoot;
	private boolean shoot = false;
	private boolean canShoot = true;
	private FlxTimer Shootcooldown;

    @Override
    public PlayerAuto(int x, int y, int width, int height, FlxGroup Bullets,FlxEmitter Gibs) {
        super(width, height);
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

		addAnimation("stand", new int[]{0},0, false);
		addAnimation("walk", new int[]{0,1,2},4,true);
		addAnimation("jump", new int[]{6,7}, 1, true);
		addAnimation("Ustand", new int[]{3}, 0, false);
		addAnimation("Uwalk", new int[]{3,4,5}, 4, true);
		addAnimation("Ujump", new int[]{8,9}, 1, true);
		play("stand");

		_bullets = Bullets;
		_gibs = Gibs;
		Shootcooldown = new FlxTimer();
		
		left = new FlxTimer();
		leftShoot = new FlxTimer();
		right = new FlxTimer();
		rightShoot = new FlxTimer();
		right.start(2,1,rightCooldown);
		goRight();
		
		sfxShoot = new FlxSound().loadEmbedded("Bob_Shoot.mp3",false,false,FlxSound.SFX);
		/*
		mode1 = right
		mode2 = rightshoot
		mode3 = left
		mode4 = leftshoot
		*/
    }

	@Override
	public void destroy()
	{
		// TODO: Implement this method
		super.destroy();
		_bullets = null;
		_gibs = null;
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
		if(shoot == true){
			
			if(canShoot == true)
			{
				Shootcooldown.start(.2f,1,Cooldown);
				canShoot = false;
				shoot();
			}
			
			
		}
		_aim = getFacing();

		
		
	}
	
	
	private void shoot() 
	{		
		
		play("shoot");
		if(getFlickering())
			flicker(1);
		else
		{
	
		getMidpoint(_point);
		((Bullet) _bullets.recycle(Bullet.class)).shoot(_point,_aim);
		sfxShoot.play(true);
		}
		
	}
	
	
	
	private void jump()
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
	
	private void stand(){
		acceleration.x = 0;
		if(isTouching(FlxObject.FLOOR))
			if(!isUpsideDown){play("stand");}
		if(isTouching(FlxObject.CEILING)) {
			if(isUpsideDown){play("Ustand");}
		}
	}
	
	private void goRight()
	{
		mode = 1;
		if(isTouching(FlxObject.FLOOR))
			if(!isUpsideDown){
				play("walk");}
		if(isTouching(FlxObject.CEILING))
			if(isUpsideDown){
				play("Uwalk");
			}
		setFacing(RIGHT);
		acceleration.x = drag.x;
	}
	
	private void goLeft()
	{
		mode = 3;
		if(isTouching(FlxObject.FLOOR))
			if(!isUpsideDown){
				play("walk");
			}
		if(isTouching(FlxObject.CEILING))
			if(isUpsideDown){
				play("Uwalk");
			}
		setFacing(LEFT);
		acceleration.x = -drag.x;
	}
	IFlxTimer leftCooldown = new IFlxTimer(){

		

		@Override
		public void callback(FlxTimer p1)
		{
		mode = 4;
		stand();
		shoot = true;
		leftShoot.start(2,1,leftshootCooldown);
		}	
	};
	
	IFlxTimer leftshootCooldown = new IFlxTimer(){

		@Override
		public void callback(FlxTimer p1)
		{
			shoot = false;
			goRight();
			right.start(2,1,rightCooldown);
		}	
	};
	
	IFlxTimer rightCooldown = new IFlxTimer(){

		@Override
		public void callback(FlxTimer p1)
		{
			mode = 2;
			stand();
			shoot = true;
			rightShoot.start(2,1,rightshootCooldown);
		}	
	};
	
	IFlxTimer rightshootCooldown = new IFlxTimer(){

		@Override
		public void callback(FlxTimer p1)
		{
			shoot = false;
			goLeft();
			left.start(2,1,leftCooldown);
		}	
	};
	IFlxTimer Cooldown = new IFlxTimer(){

		@Override
		public void callback(FlxTimer p1)
		{
			canShoot = true;
		}	
	};
	
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

