package com.dvreiter.starassault.Mobs;

import org.flixel.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Objects.*;
import com.badlogic.gdx.graphics.g2d.*;

public class TreeBoss extends FlxSprite {
    boolean isFollowingSprite;
	FlxSprite bulletToAvoid, spriteToFollow;
	protected FlxEmitter _gibs, _rgibs;
    FlxTilemap levelToFollow;
    int _jumpPower, _aim, time,jumptime;
	double timeR;
	boolean isUpsideDown, canJump,jumped, _canShoot = true,_canSpit = true;
	FlxTimer timer, shootcooldown, spitRockTimer;
	FlxGroup _rocks;

    @Override
    public TreeBoss(int x, int y, int width, int height, FlxGroup Rocks,FlxEmitter Gibs, FlxEmitter rGibs) {
        super(width, height);
        this.x = x;
        this.y = y;
        int runSpeed = 60;
        _jumpPower = 300;//200
        maxVelocity.y = _jumpPower;
        drag.x = runSpeed*4;
        maxVelocity.x = runSpeed;
        acceleration.y = 200;
		timer = new FlxTimer();
		shootcooldown = new FlxTimer();
		spitRockTimer = new FlxTimer();
        loadGraphic("treeboss.png", true, true, 37, 64);      
		//addAnimation("idle_stand", new int[]{0}, 0, false);
		addAnimation("idle_move", new int[]{0,1,2}, 4, true);
	//	addAnimation("idle_rage", new int[]{4}, 0, false);
		addAnimation("idle_rageattack", new int[]{4,5,6}, 4, true);
		health = 100;
        isFollowingSprite = false;
		canJump = false;
		_rocks = Rocks;
		_gibs = Gibs;
		_rgibs = rGibs;
		time = 8;
		jumptime = 5;
    }

    @Override
    public void update() {
        acceleration.x = 0;
        super.update();
	
		if(isFollowingSprite && spriteToFollow != null) {
            if(spriteToFollow.x != x) {
				if(isTouching(FlxObject.FLOOR) && timer.finished || timer.getTimeLeft() <= 0){
					canJump = true;
					jump();
				if(canJump){
					timer.start(jumptime,1,cooldownset);
					canJump = false;
			//		jumped = false;
				}
			}
			//	if(isTouching(FlxObject.FLOOR))//	if(isTouching(FlxObject.FLOOR) && timer.finished || timer.getTimeLeft() == 0f)
			//		canJump = true;
                if(spriteToFollow.x > x ) {
                    //if(isTouching(FlxObject.FLOOR))
                    goRight();
                } else if(spriteToFollow.x < x) {
                    //if(isTouching(FlxObject.FLOOR))
                    goLeft();
                }
            } else {
                stop();
            }
        }

	}

	@Override
	public void destroy()
	{
		// TODO: Implement this method
		super.destroy();
		_rocks = null;

	}

	@Override public void hurt(float Damage)
	{
		flicker(0.6f);//0.2f
		FlxG.score += 10;
		super.hurt(Damage);
	}

    public void followSprite(FlxSprite player) {
        spriteToFollow = player;
        isFollowingSprite = true;	
    }
	
	public void WatchLevel(FlxTilemap level) {
        levelToFollow = level;
    }
	
    public void goLeft() {
		play("idle_move");
		Enraged();
        acceleration.x -= drag.x;
		if(_canShoot)
		{
			shootcooldown.start(time,1,cooldownset2);
			_canShoot = false;
			shoot();
		}
		/*if(isTouching(FlxObject.FLOOR))
		canJump = true;
		if(canJump){
			timer.start(10,1,cooldownset);
			canJump = false;
			jump();
		}*/
    }
    public void goRight() {
		play("idle_move");
		Enraged();
        acceleration.x += drag.x;
		if(_canShoot)
		{
			shootcooldown.start(time,1,cooldownset2);
			_canShoot = false;
			shoot();
		}
	/*	if(isTouching(FlxObject.FLOOR))
		canJump = true;
		if(canJump){
		timer.start(10,1,cooldownset);
		canJump = false;
		jump();
		}*/
    }
	
	public void Enraged(){
		if(health <= 50){
		play("idle_rageattack");
		drag.x = 150*4;
		time = 4;
		jumptime = 3;
	//	_canSpit = true;
		if(_canSpit){
		spitRockTimer.start(6,1,spitcooldown);
		_canSpit = false;
		spit();
		}
	  }
	}
	
    public void stop() {
        acceleration.x = 0;
    }
    public void jump() {
		int X = (Math.round(this.x))/16;
		int Y = (Math.round(this.y))/16;
        if(canJump){
			velocity.y = - maxVelocity.y/1.2f;
			if(!isTouching((FlxObject.FLOOR))){
			acceleration.y = 400;
			}
		/*	if(levelToFollow.getTile(X,Y + 2) != 0){
			FlxG.shake(0.035f,0.25f); 
			}
			}*/
	//		if(justTouched(FlxObject.FLOOR)){
	//		FlxG.shake(0.035f,0.25f); 
	//		if(spriteToFollow.isTouching(FlxObject.FLOOR))
	//		spriteToFollow.kill();
	//		}
		}
	}
	private void shoot()
	{
		_aim = getFacing();
		getMidpoint(_point);
		//((MageBall) _bullets.recycle(MageBall.class)).shoot(_point,_aim);
		((TreeRock) _rocks.recycle(TreeRock.class)).shoot(_point,this.x,this.y,spriteToFollow.x,spriteToFollow.y);
	}
	
	private void spit(){
		if(spriteToFollow.x > x){
		  _rgibs.setXSpeed(100,200);
		if(_rgibs != null)
		{
			_rgibs.at(this);
			_rgibs.start(true,4,.01f);//5sec
			FlxG.shake(0.035f,0.25f); 
		}
		}else if(spriteToFollow.x < x){
			_rgibs.setXSpeed(-100,-200);
		if(_rgibs != null)
		{
			_rgibs.at(this);
			_rgibs.start(true,4,.01f);
			FlxG.shake(0.035f,0.25f); 
		}
		}
	}
	
	@Override 
	public void kill()
	{
		if(!alive)
			return;
		setSolid(false);
		super.kill();
		flicker(0);
		exists = false;
		visible = false;
		velocity.make();
		acceleration.make();
		if(_gibs != null)
		{
			_gibs.at(this);
			_gibs.start(true,5,0,50);
		}
	}
	
	public IFlxTimer cooldownset = new IFlxTimer(){
		@Override
		public void callback(FlxTimer flxTimer)
		{
			canJump = true;

		}
	};
	
	public IFlxTimer cooldownset2 = new IFlxTimer(){
		@Override
		public void callback(FlxTimer flxTimer)
		{
			_canShoot = true;

		}
	};
	
	public IFlxTimer spitcooldown = new IFlxTimer(){
		@Override
		public void callback(FlxTimer flxTimer)
		{
			_canSpit = true;

		}
	};
	
}
/*if (isFalling){
			acceleration.y=200;
			play("idle_rage");
			if (isTouching(FlxObject.FLOOR)){
				isFalling = false;
				acceleration.y=0;
				FlxG.shake(0.035f,0.25f);
			}*/
