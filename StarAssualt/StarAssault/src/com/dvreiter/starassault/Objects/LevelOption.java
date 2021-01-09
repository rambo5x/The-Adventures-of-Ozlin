package com.dvreiter.starassault.Objects;

import org.flixel.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Objects.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.*;

public class LevelOption extends FlxSprite{
	
    FlxSprite kills[];
    FlxSprite getsKilledBy[];

	private FlxText num;

	private FlxTileblock titleBlock2;

	private FlxTileblock titleBlock1;

	private boolean hasUpdated = false;

	private FlxTimer spawnCoolDown;
   
    @Override
    public LevelOption(int x, int y, int width, int height, int levelnum) {
        super(width, height);
		this.width=width;
		this.height=height;
        this.x = x;
        this.y = y;
		
        maxVelocity.y = 0;      
        maxVelocity.x = 0;
		
		num = new FlxText(x+width/4,y+height/4,100,"7"+ levelnum);
		
		num.setSize(6);
		
//		this.makeGraphic(width,height,0xff209ece);
//	
//		this.stamp(num);
		
//		this.stamp(makeGraphic(width,height,0xff209ece));
//		this.stamp(makeGraphic(width-8, height-8, 0xff218fb8),2,2);// 390, 230

		titleBlock1 = new FlxTileblock(0, 0, width, height);//780, 400
		titleBlock1.makeGraphic(width, height, 0xff218fb8);// 390, 230
		

		titleBlock2 = new FlxTileblock(2, 2,width-4 , height-4);//780, 400
		titleBlock2.makeGraphic(width-4, height-4, 0xff209ece);// 390, 230
		
		this.stamp(titleBlock1);
		this.stamp(titleBlock2,2,2);
		
		
		
		
		
		
        
		health = 2;
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

	@Override public void hurt(float Damage)
	{
		flicker(0.6f);//0.2f
		FlxG.score += 10;
		super.hurt(Damage);
	}
	/*package com.dvreiter.starassault.Objects;

import org.flixel.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Objects.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.*;

public class LevelOption extends FlxText{
	
    FlxSprite kills[];
    FlxSprite getsKilledBy[];

	private FlxText num;

	private FlxTileblock titleBlock2;

	private FlxTileblock titleBlock1;

	private boolean hasUpdated = false;
   
    @Override
    public LevelOption(int x, int y, int width, int height, int levelnum) {
        super(x,y,10,""+levelnum);
		this.setSolid(true);
        this.x = x;
        this.y = y;
		
        maxVelocity.y = 0;      
        maxVelocity.x = 0;
		
		
		
//		this.makeGraphic(width,height,0xff209ece);
//	
//		this.stamp(num);
		
//		this.stamp(makeGraphic(width,height,0xff209ece));
//		this.stamp(makeGraphic(width-8, height-8, 0xff218fb8),2,2);// 390, 230

		titleBlock1 = new FlxTileblock(0, 0, width, height);//780, 400
		titleBlock1.makeGraphic(width, height, 0xff218fb8);// 390, 230
		

		titleBlock2 = new FlxTileblock(2, 2,width-4 , height-4);//780, 400
		titleBlock2.makeGraphic(width-4, height-4, 0xff209ece);// 390, 230
		
		this.stamp(titleBlock1);
		this.stamp(titleBlock2,2,2);
		this.setPixels(titleBlock1.getPixels());
		
		
		
		
		
        
		health = 1;
      }
	  
    @Override
    public void update() {  
        super.update();
		this.stamp(titleBlock1);
		this.stamp(titleBlock2,2,2);
		
	}

	@Override
	public void destroy()
	{
		// TODO: Implement this method
		super.destroy();
	}

	@Override public void hurt(float Damage)
	{
		flicker(0.6f);//0.2f
		FlxG.score += 10;
		super.hurt(Damage);
	}
}*/
}
