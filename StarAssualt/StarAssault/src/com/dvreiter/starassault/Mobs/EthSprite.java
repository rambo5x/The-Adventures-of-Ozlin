package com.dvreiter.starassault.Mobs;
import org.flixel.*;

public class EthSprite extends FlxSprite
{
		public String id = null;
		public String target = null;

		public FlxTilemap levelToFollow;
		public FlxSprite spriteToFollow;
		public boolean isFollowingSprite = false;
	@Override
	public EthSprite(int x,int y,String Id) 
	{
			super(x,y);
			id = Id;
	}
	
		public void watchLevel(FlxTilemap Level)
		{
				levelToFollow = Level;
		}
		public void followSprite(FlxSprite player)
		{
        spriteToFollow = player;
        isFollowingSprite = true;	
    }
		public void setTargetToActivate(String Target)
		{
				target=Target;
		}
	
		
	
		
}
