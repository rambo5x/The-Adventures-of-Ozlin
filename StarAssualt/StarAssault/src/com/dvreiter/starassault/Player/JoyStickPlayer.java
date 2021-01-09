package com.dvreiter.starassault.Player;

import org.flixel.*;
import org.flixel.event.*;
import org.flixel.ui.*;
import com.dvreiter.starassault.*;
import com.dvreiter.starassault.Objects.*;

public class JoyStickPlayer extends FlxSprite {   

//	DoubleJumpPowerup doubleJPow;
	FlxAnalog _joystick;
	private FlxText doublePowTime;
	protected FlxGroup _bullets;
	protected int _aim;
	protected FlxEmitter _gibs;
	protected float _restart;

	private FlxTimer doubleJPowTimer;
	private FlxSound sfxJump,sfxShoot,sfxDeath;

    private int _jumpPower;
	FlxVirtualPad pad;
	private boolean isUpsideDown, isSidewaysm, isSideways;
	private boolean hasGravityToggle;
	private boolean isFlying;
	private boolean hasFlyingToggle;
	private boolean _justShoot;
	private boolean _canDoubleJump, _complete;

	@Override
    public JoyStickPlayer(int x, int y, int width, int height, FlxGroup Bullets,FlxEmitter Gibs, FlxVirtualPad pad,FlxAnalog analog) {//FlxVirtualPad pad
        super(width, height);
		this.pad = pad;	
		isUpsideDown = false;
		isSidewaysm = false;
		isSideways=  false;
		hasGravityToggle = false;
		isFlying = false;
		hasFlyingToggle = false;
		_canDoubleJump = false;
		pad.buttonX.onDown = new IFlxButton(){@Override public void callback(){toggle();}};
		pad.buttonY.onDown = new IFlxButton(){@Override public void callback(){dtoggle();}};
        this.x = x;
        this.y = y;
        int runSpeed = 100;
        _jumpPower = 150;
        maxVelocity.y = _jumpPower;
        drag.x = runSpeed*4;
        maxVelocity.x = runSpeed;
        acceleration.y = 200;
		this._joystick = analog;
		_joystick.onPressed = move;
		_joystick.onUp = stop;
		_joystick.setAlpha(.75f);
		_joystick.setMaxSize(10);
		//	_joystick.setAlpha(.75f);
		//	_joystick = analog;

		/*		doubleJPowTimer = new FlxTimer();

		 doublePowTime = new FlxText(200,2,100,"DoubleJump: ");
		 doublePowTime.setShadow(0xff000000);
		 //shieldTimeTxt.setText("SCORE: "+(coins.countDead()*100));
		 doublePowTime.scrollFactor.x = doublePowTime.scrollFactor.y = 0;
		 doublePowTime.exists = false;
		 doublePowTime.visible = false;
		 */
        loadGraphic("budderking.png", true, true, 16, 16);    
		width = 14;
		height = 15;
		
//		offset.x = 1;
		//	offset.y = 1;

		addAnimation("stand", new int[]{0},0, false);
		addAnimation("lstand", new int[]{12},0, false);
		addAnimation("rstand", new int[]{15},0, false);

		addAnimation("walk", new int[]{0,1,2},4,true);
		addAnimation("lwalk", new int[]{12,11,10},4,true);
		addAnimation("rwalk", new int[]{13,14,15},4,true);

		addAnimation("jump", new int[]{6,7}, 1, true);
		addAnimation("ljump", new int[]{17,16}, 1, true);
		addAnimation("rjump", new int[]{18,19}, 1, true);

		addAnimation("Ustand", new int[]{3}, 0, false);
		addAnimation("Uwalk", new int[]{3,4,5}, 4, true);
		addAnimation("Ujump", new int[]{8,9}, 1, true);

		play("stand");

		_bullets = Bullets;
		_gibs = Gibs;

		sfxJump = new FlxSound().loadEmbedded("Bob_Jump.mp3",false,false,FlxSound.SFX);
		sfxShoot = new FlxSound().loadEmbedded("Bob_Shoot.mp3",false,false,FlxSound.SFX);
		sfxDeath = new FlxSound().loadEmbedded("Bob_Death.mp3",false,false,FlxSound.SFX);

		health = 1;
    }

	@Override
	public void destroy()
	{
		// TODO: Implement this method
		super.destroy();
		_bullets = null;
		_gibs = null;
		_joystick = null;
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
		if(isUpsideDown && alive) {
			isUpsideDown = false;
			//angle = 360;
			acceleration.y = 200;
			play("walk");
			return;
		} else {
			if(!isUpsideDown && alive){
				isUpsideDown = true;
				//angle = 180;
				acceleration.y = -200;
				play("Uwalk");
				return;
			}
		}
		/*	if(pad.buttonRight.status == FlxButton.PRESSED && isSidewaysm && alive) {
		 isSideways = true;
		 isSidewaysm = false;
		 //angle = 360;
		 acceleration.x = 200;
		 play("rwalk");
		 return;
		 }else if(pad.buttonLeft.status == FlxButton.PRESSED && !isSidewaysm && alive){
		 isSideways = true;
		 isSidewaysm = true;
		 //angle = 180;
		 acceleration.x = -200;
		 play("lwalk");
		 return;
		 }else{
		 isSideways = false;
		 }*/
	}

	public void fToggle(){
		if(!hasFlyingToggle)
			return;
		if(!isFlying)
			isFlying = true;
		velocity.y = - maxVelocity.y/2.7f;
		return;
	}

	public void dtoggle(){
		if(getFacing() == LEFT && alive && !isTouching(FlxObject.LEFT+16)){
			velocity.x -= 1500;//3000,2000,1500
		}else if(getFacing() == RIGHT && alive && !isTouching(FlxObject.RIGHT-16)){
			velocity.x += 1500;
		}
		//	x += 64;
	}
	
    @Override
    public void update() {      

		if(!alive)//alive
		{
			velocity.x = 0;
			acceleration.x = 0;
			_restart += FlxG.elapsed;
			if(_restart > 2)
				FlxG.resetState();
			return;
		}
		
		/*if(velocity.x == 0){
			play("stand");
		}*/

	//	acceleration.x = 0;

		/*	if(!_complete)
		 doublePowTime.setText("DoubleJump: " + doubleJPowTimer.getTimeLeft());
		 */

		/*	if(FlxG.keys.LEFT || pad.buttonLeft.status == FlxButton.PRESSED && alive)
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
		 else if(FlxG.keys.RIGHT || pad.buttonRight.status == FlxButton.PRESSED && alive)
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
		 }*/

		/*if(FlxG.keys.UP || pad.buttonUp.status == FlxButton.PRESSED && isSidewaysm && alive)
		 {
		 if(isTouching(FlxObject.LEFT))
		 if(!isSidewaysm){
		 play("lwalk");
		 }
		 if(isTouching(FlxObject.RIGHT))
		 if(isSidewaysm){
		 play("lwalk");
		 setFacing(RIGHT);
		 }
		 //		setFacing(LEFT);
		 acceleration.y -= drag.y;
		 }
		 else if(FlxG.keys.DOWN || pad.buttonDown.status == FlxButton.PRESSED && isSideways && alive)
		 {
		 if(isTouching(FlxObject.LEFT))
		 if(!isSidewaysm){
		 play("rwalk");}
		 if(isTouching(FlxObject.RIGHT))
		 if(isSidewaysm){
		 play("rwalk");
		 setFacing(LEFT);
		 }
		 //		setFacing(RIGHT);
		 acceleration.y += drag.y;
		 }else{
		 if(isTouching(FlxObject.LEFT))
		 if(!isSidewaysm){play("lstand");}
		 if(isTouching(FlxObject.RIGHT)) {
		 if(isSidewaysm){play("rstand");}
		 }
		 }*/

		if(FlxG.keys.SPACE || pad.buttonA.status == FlxButton.PRESSED && alive)
		{
			if(!isUpsideDown) {
				play("jump");
			}
			else {
				play("Ujump");
			}

			if(!isUpsideDown) {
				if(!_canDoubleJump){
					if(isTouching(FlxObject.FLOOR)) {
						velocity.y = - maxVelocity.y/1.2f;
						sfxJump.play(true);
					}
				}else{
					velocity.y = - maxVelocity.y/1.2f;
					sfxJump.play(true);
				}
			} else {
				if(!_canDoubleJump){
					if(isTouching(FlxObject.CEILING)) {			
						velocity.y = + maxVelocity.y/1.2f;
						sfxJump.play(true);
					}
				}else{
				    velocity.y = + maxVelocity.y/1.2f;
				    sfxJump.play(true);
				}
			}

			/*	if(isSideways) {
			 if(isTouching(FlxObject.LEFT)) 
			 if(!isSidewaysm){
			 play("ljump");
			 velocity.x = + maxVelocity.y/1.2f;
			 sfxJump.play(true);
			 }
			 if(isTouching(FlxObject.RIGHT)) 
			 if(isSidewaysm){
			 play("rjump");
			 velocity.x = - maxVelocity.y/1.2f;
			 sfxJump.play(true);
			 }
			 }		*/
		}

		/*	if(FlxG.keys.UP  || pad.buttonUp.status == FlxButton.PRESSED)
		 _aim = UP;
		 else if((FlxG.keys.DOWN || pad.buttonDown.status == FlxButton.PRESSED) && velocity.y != 0)
		 _aim = DOWN;
		 else*/
		_aim = getFacing();

		if((FlxG.keys.BACKSLASH || pad.buttonB.status == FlxButton.PRESSED)&& _bullets.countLiving() < 3 && alive)
		{		
			if(!_justShoot)
			{
				if(getFlickering())
					flicker(1);
				else
				{
					getMidpoint(_point);
					((Bullet) _bullets.recycle(Bullet.class)).shoot(_point,_aim);
					sfxShoot.play(true);
				}
				_justShoot = true;
			}	
		}
		else
			_justShoot = false;

		/*	if(FlxG.keys.B || pad.buttonY.status == FlxButton.PRESSED){
		 dtoggle();}*/
		//fToggle();
		/*	angle = 360;
		 angularVelocity = 520;
		 angularAcceleration = 520;
		 hasFlyingToggle = true;
		 */
		//	}
		if(FlxG.keys.M){
			toggle();
		}
		if(FlxG.keys.N){
			dtoggle();
		}
		if(FlxG.keys.N){
			fToggle();
		}
		if(this.x <= 0)
		{
			velocity.x = 0;
		}
		super.update();
		//	FlxG.overlap(doubleJPow, this, doublejump);
	}

	IFlxAnalog move = new IFlxAnalog()
	{		
		@Override
		public void callback()
		{
			acceleration.x = 0;
			
		//	velocity.x = 3 * _joystick.acceleration.x;
			if(_joystick.acceleration.x < 0 || FlxG.keys.LEFT){
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
			}else if(_joystick.acceleration.x > 0 || FlxG.keys.RIGHT){
				if(isTouching(FlxObject.FLOOR))
					if(!isUpsideDown){
						play("walk");
					}
				if(isTouching(FlxObject.CEILING))
					if(isUpsideDown){
						play("Uwalk");
					}
				setFacing(RIGHT);
				acceleration.x += drag.x;
			}else if(_joystick.acceleration.x == 0){
				if(isTouching(FlxObject.FLOOR))
					if(!isUpsideDown){play("stand");}
				if(isTouching(FlxObject.CEILING)) {
					if(isUpsideDown){play("Ustand");}
				}
			}
		/*	if(velocity.x == 0){
				play("stand");
			}*/
	/*		if(_joystick.getAngle() < -90 && _joystick.getAngle() > 90 && alive){
				/*if(isTouching(FlxObject.FLOOR))
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
			if(_joystick.getAngle() > -90 && _joystick.getAngle() < 90 && alive){
			/*	if(isTouching(FlxObject.FLOOR))
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
			}*/
		}
	};

	IFlxAnalog stop = new IFlxAnalog()
	{		
		@Override
		public void callback()
		{
			acceleration.x = 0;
		}
	};

	IFlxCollision doublejump = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject p1, FlxObject p2)
		{						
	        p1.kill();
			_canDoubleJump = true;
			doublePowTime.exists = true;
			doublePowTime.visible = true;
			_complete = false;
			doubleJPowTimer.start(10,1,doubleJump);
	    }
	};				


	public IFlxTimer doubleJump = new IFlxTimer(){
		@Override
		public void callback(FlxTimer flxTimer)
		{
			_canDoubleJump = false;
			_complete = true;
			doublePowTime.exists = false;
			doublePowTime.visible = false;

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
		if(!alive)//alive
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
		sfxDeath.play(true);
	}


}	

