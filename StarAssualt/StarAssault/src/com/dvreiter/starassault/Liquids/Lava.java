package com.dvreiter.starassault.Liquids;
import org.flixel.*;
import org.flixel.event.*;

public class Lava extends FlxSprite {

	boolean isUpsideDown;
	FlxSprite spriteToDie;

    @Override
    public Lava(int x, int y) {
        super(x,y);
        loadGraphic("lava.png", true, true, 16, 16);      
		width = 16;
		height = 16;
		immovable = true;
		addAnimation("burn", new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44},4, true);
		play("burn");
    }
    @Override
    public void update() {
        super.update();
}

	@Override
	public void destroy()
	{
		// TODO: Implement this method
		super.destroy();
		
	}
	
	public void burnSprite(FlxSprite player){
		spriteToDie = player;
	}
}
