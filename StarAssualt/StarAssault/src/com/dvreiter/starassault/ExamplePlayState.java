/*Basic Flixel-gdx game tutorial
Made by MCPEGAMER*/

package com.dvreiter.starassault;

import org.flixel.FlxButton;
import org.flixel.FlxG;
import org.flixel.FlxObject;
import org.flixel.FlxSprite;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.FlxTileblock;
import org.flixel.FlxTilemap;
import org.flixel.event.IFlxCollision;
import org.flixel.*;
import com.badlogic.gdx.utils.IntArray;
import org.flixel.ui.*;
import com.dvreiter.starassault.Levels.*;

public class ExamplePlayState extends FlxState
{	//My public variables
	public FlxTilemap level; //A name for our first level or world which is declared as a FlxTilemap.
	public FlxVirtualPad pad; //Adding the game pad for buttons that comes already made. You can add custom ones.
	public FlxSprite player; //Our sprite named player.		
	public FlxSprite enemy1; //Our sprite named enemy1.
	public FlxSprite portal; //Our sprite named portal.
	public FlxGroup coins; //A group of coins for creating multiple coins in the level.
	public FlxSprite portalcoin; //Our sprite named portalcoin.
	public FlxGroup spikes; //A group of spikes for creating multiple spikes in pur level.
	public FlxText status; //A text for checking the player status during the game.
	public FlxTileblock leftboundary; //The left border of our world which is a block
	public FlxTileblock rightboundary; //The right border of our world which is a block
	public FlxTileblock upperboundary; //The upper border of our world which is a block

	@Override
	public void create()
	{						
		FlxG.setBgColor(0xFF00CCFF);//Setting background color.

int[] data = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, //Creating/Generating our worlds data.
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,1,1,1,0,1,1,1,2,0,1,1,1,0,0,1,1,1,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,
			1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,               
			10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
			10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
			10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
		};

		level = new FlxTilemap();
		level.loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 25), "tilemap.png", 16, 16); //loading our map to our data that we created and using textures from "tilemap.png" which all blocks are a 16x16.
		// 400x240						
		portal = new FlxSprite(50, 50);//coords for our portal to generate in our level.
		portal.loadGraphic("portal.png", true, true, 16, 16);//loading the image for our portal that is true and is a 16x16.
		portal.addAnimation("spinning", new int[]{0, 1, 2}, 6, true);//Adding the portal animation from our image in frames and setting our framerate.
		portal.play("spinning");//Playing our animation
		portal.immovable = true;//Cant move
		portal.exists = false;//Doesnt exist unless an action happens.
		add(portal);//Adding our portal. 

		portalcoin = new FlxSprite(240,60);//Just adding more objects in the game...
		portalcoin.loadGraphic("Portalcoin.png", true, true, 16, 16);
		portalcoin.addAnimation("rotate", new int[]{0, 1, 2}, 4, true);
		portalcoin.play("rotate");
		add(portalcoin);
				
		upperboundary = new FlxTileblock(0, -16, 400, 16);//Creating a block to make an upperboundary for our level.Sorry i meant worldboundary. There is another way but havent figured out yet.
		upperboundary.makeGraphic(400, 16, 0xFF000000);
		add(upperboundary);

		leftboundary = new FlxTileblock(-16, 0, 16, 240);//Same as above but for the left boundary.
		leftboundary.makeGraphic(16, 240, 0xFF000000);
		add(leftboundary);

		rightboundary = new FlxTileblock(400, 1, 16, 240);//Right boundary
		rightboundary.makeGraphic(16, 240, 0xFF000000);
		add(rightboundary);			

		player = new FlxSprite(20,150);
		player.loadGraphic("cube.png", true, true, 16, 16);
		player.addAnimation("stand", new int[]{0},0, false);
		player.addAnimation("left", new int[]{1}, 5, true);
		player.addAnimation("right", new int[]{2}, 1, true);
		player.addAnimation("jump", new int[]{3}, 0, true);
		player.maxVelocity.x = 80;//Setting our velocity for x
		player.maxVelocity.y = 200;//for y
		player.acceleration.y = 200;//our acceleration
		player.drag.x = player.maxVelocity.x*4;//The drag of the player.
		player.play("stand");//Playing the stand animation when spawned.									

		coins = new FlxGroup();//Creating a group of coins.
		//bottom coins	
		createCoin(75,150);//settings coords in our level.
		createCoin(125,150);
		createCoin(175,150);				
		createCoin(225,150);
		createCoin(275,150);
		add(coins);

		spikes = new FlxGroup();
		createSpike(50,160);
		createSpike(100,160);
		createSpike(150,160);
		createSpike(200,160);
		createSpike(250,160);
		add(spikes);

		status = new FlxText(2,2,100, "SCORE: "); //Creatings a text for our score. May not keep.
		status.setShadow(0xff000000);
		status.setText("SCORE: "+(coins.countDead()*100));
		status.scrollFactor.x = 0;//Making possible for our status text to be scrollable during the game.
		status.scrollFactor.y = 0;
		add(status);

		pad = new FlxVirtualPad(FlxVirtualPad.LEFT_RIGHT, FlxVirtualPad.A_B);//Creating our pad.
		add(player);		//Adding the rest of the objects etc.
		add(enemy1);		
		add(level);
//		add(hearts);
		add(pad);
	}
    public void createCoin(int X,int Y)//Creating our coin
	{
		FlxSprite coin = new FlxSprite(X,Y);
		coin.loadGraphic("coin.png", true, true, 16, 16);
		coin.addAnimation("rotating", new int[]{0, 1, 2}, 4, true);
		coin.play("rotating");
		coins.add(coin);
	}	

	public void createSpike(int X, int Y){//creating our spike
		FlxSprite spike = new FlxSprite(X,Y);
		spike.loadGraphic("spikes.png", true, true, 16, 16); 
		spike.immovable = true;
		spikes.add(spike);
	}
	
    @Override
	public void update()//Update method for our game
	{		
		if(pad.buttonLeft.status == FlxButton.PRESSED)//If our left button is pressed.
		{
			player.acceleration.x = - player.maxVelocity.x*4;//Our acceleration and maxvelocity.
		    if(player.isTouching(FlxObject.FLOOR)) {//If its touching the floor of our level.
				player.play("left");//Play the animation "left".
		    }
		}
		if(pad.buttonRight.status == FlxButton.PRESSED)
		{
			player.acceleration.x = player.maxVelocity.x*4;
			if(player.isTouching(FlxObject.FLOOR)) {			
				player.play("right");
			}
		}
		if(pad.buttonA.status == FlxButton.PRESSED && player.isTouching(FlxObject.FLOOR))
		{
			player.velocity.y = - player.maxVelocity.y/1.7f;//Adding jump to our player using a.
			player.play("jump");
		}		
		if(pad.buttonB.status == FlxButton.PRESSED)//If pressing B reset our level. Will change.
		{					
			FlxG.resetState();
		}       
		super.update();//Adding pur collisions and overlaps after super.update method.
        player.acceleration.x = 0;		
		FlxG.overlap(coins,player,getCoin);	
		FlxG.overlap(spikes,player,doSpikeDamage);
		FlxG.overlap(portalcoin,player,getPCoin);
		FlxG.overlap(portal,player,win);
		FlxG.collide(enemy1,level);
		FlxG.collide(upperboundary,player);
		FlxG.collide(leftboundary,player);
		FlxG.collide(rightboundary,player);
		FlxG.collide(enemy1,upperboundary);
		FlxG.collide(enemy1,leftboundary);
		FlxG.collide(enemy1,rightboundary);		
		FlxG.collide(enemy1,spikes);
		FlxG.collide(enemy1,player,doPlayerDamage);
		FlxG.collide(level,player);

		if(player.y > FlxG.height)//If players height is greater than the world height, reset level.
		{
			FlxG.resetState();
		}				
	}

	IFlxCollision getPCoin = new IFlxCollision()//Our callback for our collisions. 
	{
		@Override
		public void callback(FlxObject Portalcoin, FlxObject Player)//pretty much what happens after collision.
		{						
	        Portalcoin.kill();
			portal.exists = true;			        
	    }
	};					

	IFlxCollision getCoin = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Coin, FlxObject Player)
		{						
	        Coin.kill();
			status.setText("SCORE: "+(coins.countDead()*100));	  					        
	    }
	};					

	IFlxCollision doSpikeDamage = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Spike, FlxObject Player)
		{						
	        add(new FlxText(200,22 , 100, "You Died!"));			
			player.kill();
	    }
	};

	IFlxCollision doPlayerDamage = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Enemy1, FlxObject Player)
		{						
	        add(new FlxText(200,22 , 100, "You Died!"));			
			player.kill();
	    }
	};


	IFlxCollision win = new IFlxCollision()
	{
		@Override
		public void callback(FlxObject Portal, FlxObject Player)
		{
			add(new FlxText(200,22 , 100, "You win Level 2!"));			
			player.kill();
			FlxG.switchState(new PlaystateThree());//Switching to another state/level/world.
		}
	};
}
       	
