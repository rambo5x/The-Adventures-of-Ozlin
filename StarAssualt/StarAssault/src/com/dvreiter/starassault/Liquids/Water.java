package com.dvreiter.starassault.Liquids;
import org.flixel.*;
import org.flixel.event.*;

public class Water extends FlxSprite {
	
	boolean isWatchingSprite;
	FlxSprite spriteToSwim;
	//FlxObject p1;

    @Override
    public Water(int x, int y) {
        super(x,y);
        loadGraphic("lava.png", true, true, 16, 16);      
		width = 16;
		height = 16;
		offset.y = 8.0f;
		setSolid(false);
		immovable = true;
		velocity.y = 0;
		isWatchingSprite = false;
    }
    @Override
    public void update() {
        super.update();
		
	/*	FlxG.overlap(spriteToSwim, this, new IFlxCollision() {

				@Override
				public void callback(FlxObject p1, FlxObject p2)
				{
					p1.velocity.x -= 4;
					p1.velocity.y += 4;
				}
			});*/
	}
	
	@Override
	public void destroy()
	{
		// TODO: Implement this method
		super.destroy();
}
}
