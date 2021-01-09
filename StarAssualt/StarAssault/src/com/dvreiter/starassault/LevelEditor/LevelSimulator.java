package com.dvreiter.starassault.LevelEditor;

import java.io.*;

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
import org.flixel.event.*;
import org.apache.http.message.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.dvreiter.starassault.Player.*;
import com.dvreiter.starassault.Mobs.*;
import com.dvreiter.starassault.Levels.*;
import com.dvreiter.starassault.*;
import org.flixel.ui.event.*;
import android.view.SurfaceHolder.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import android.os.*;
import android.text.*;
import android.widget.*;
import com.badlogic.gdx.utils.*;
import org.flixel.system.input.*;
import com.dvreiter.starassault.Objects.*;
import org.flixel.system.*;
import com.dvreiter.starassault.Menu.*;
import java.util.concurrent.*;
import java.lang.reflect.*;
import com.dvreiter.starassault.Tools.*;


public class LevelSimulator extends FlxState
{	

		ErrorReporter error;

		public static String filename = "";
		public static PrintWriter pr;

		//new Level info
		
		public static String fileName;
		public static int levelNumber = 7;

		public FlxButton pause;
		public EthTileMap level;
		public FlxTileblock block;
		private FlxButton backbtn;
		
		public static boolean openFromLe = false;

		
		public static final String FntRobotoRegular = "Roboto-Regular.ttf";

		protected FlxEmitter _littleGibs;

		FlxVirtualPad pad;
		PlayerLE playerLE;
		FlxGroup _bullets;

		//Advance stuff
		
		private FlxGroup enemies;

		private FlxGroup skeletons;

		private FlxGroup spikes;

		private FlxGroup coins;
		
		private FlxGroup mageBullets;
		private FlxGroup mages;

		private int playerMode;

		private String[][][] entityinfo;

	//	private Player allEntArr[0];

		private FlxGroup entitiesG;

		private FlxGroup entitiesS;

		private EthSprite[] allEntArr;
		
		private int entOn = 0;

		private FlxGroup players;
		
		private int levelWidth;
		private int levelHeight;

		@Override
		public void create()
		{		
				try{

						error = new ErrorReporter();
						FlxG.setBgColor(0xFFE0CCFF);
						FlxG.mouse.show();
						
					try {
						error.logData("reading data");
						File Directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bobrun/Worlds/" + filename);
						FileInputStream fis = new FileInputStream(Directory + "/LevelData.ser" );
						ObjectInputStream ois = new ObjectInputStream(fis);
						EthLevelData Ethdata = (EthLevelData) ois.readObject(); // 4
						//if something returns an object u can cast it
						ois.close();		
						level = new EthTileMap( Ethdata.TileArray , Ethdata.WidthInTiles);
						add(level);
						levelWidth = Ethdata.WidthInTiles;
						levelHeight = Ethdata.TileArray.length/Ethdata.WidthInTiles;

						error.logData(level.getString1DTileArray());

					} catch (Exception e) { e.printStackTrace(); 
						error.reportError(e);
						FlxG.switchState(new PlayStateLESettings());	
					}

						String[] one = {"player","coin","lockdoor","key","peppermintpu","portal","portalcoin","skeleton","spike","lever","mage","terminator"};
						int[] two = {    22,				23				,24,		25		,26,				  27					,28				,29				,30				,31,		32,		33};

						String[][] entities = new String[one.length][2];
						for(int pos1 = 0;pos1 < one.length;pos1++)
						{
								for(int pos2 = 0;pos2 < 2;pos2++)
								{
										if(pos2 == 0){
												entities[pos1][pos2] = one[pos1];
												//error.logData(one[pos1]);
										}
										if(pos2 == 1){
												entities[pos1][pos2] = String.valueOf(two[pos1]);
												//error.logData(String.valueOf(two[pos1]));
										}
								}		
						}

						_littleGibs = new FlxEmitter();
						_littleGibs.setXSpeed(-150,150);
						_littleGibs.setYSpeed(-200,0);
						_littleGibs.setRotation(-720,-720);
						_littleGibs.gravity = 350;
						_littleGibs.bounce = 0.5f;
						_littleGibs.gravity=0;
						_littleGibs.makeParticles("gibs.png",100,10,true,0.5f);
						
						
						
						
						//level.loadEntities(entities);
						//entityinfo = level.getEntityInfo();
					
						int Length = 0;
						for(int ent = 0;ent < entityinfo.length;ent++)
						{
						Length += entityinfo[ent].length;
						}
						allEntArr = new EthSprite[Length];
						
						backbtn = new FlxButton(310, 5, "Back", new IFlxButton(){@Override public void callback(){onBack();}});
						backbtn.scrollFactor.x = 0;
						backbtn.scrollFactor.y = 0;
						add(backbtn);	

						pad = new FlxVirtualPad(FlxVirtualPad.DPAD_FULL, FlxVirtualPad.A_B_X_Y);
						pad.setAlpha(0.5f);

						_bullets = new FlxGroup();

						FlxG.camera.follow(allEntArr[0], FlxCamera.STYLE_PLATFORMER);
						FlxG.camera.setBounds(0,0,levelWidth*16,levelHeight*16,true);// 1st 400,240  2nd 800,240, 3rd 1200,48

//				rightBorder = new FlxTileblock(levelWidth-1,0,1,levelHeight);
//				rightBorder.immovable = true;
//				add(rightBorder);
//
//				leftBorder = new FlxTileblock(0,0,1,levelHeight);
//				leftBorder.immovable = true;
//				add(leftBorder);

						mageBullets = new FlxGroup();
						enemies = new FlxGroup();
						mages = new FlxGroup();
						skeletons = new FlxGroup();
						spikes = new FlxGroup();
						coins = new FlxGroup();
						players = new FlxGroup();
						

						LevelSimulator levelm = new LevelSimulator();

						try{
								for(int ent = 0;ent < entityinfo.length;ent++){
										for(int entnum = 0;entnum < entityinfo[ent].length;entnum++){
												//error.logData(entityinfo[ent][entnum][7]);
												Method m = levelm.getClass().getMethod(entityinfo[ent][entnum][7],int.class,int.class,String.class,String.class,String.class,String.class);
												error.logData("name=" +m.getName());
												m.invoke(this,Integer.parseInt(entityinfo[ent][entnum][0]),Integer.parseInt(entityinfo[ent][entnum][1]),entityinfo[ent][entnum][3],entityinfo[ent][entnum][4],entityinfo[ent][entnum][5],entityinfo[ent][entnum][6]);	
												//x,y,info,info,info,info
												entOn++;
										}					
								}
						}catch(Exception e){error.reportError(e);}
						
						entitiesS = new FlxGroup();
					  	entitiesS.add(enemies);
						entitiesS.add(mages);
						entitiesS.add(skeletons);
						entitiesS.add(spikes);
						add(players);
						add(mageBullets);
						add(entitiesS);
						add(coins);




						add(_littleGibs);
						add(_bullets);
						add(pad);

				}catch(Exception e)
				{
						error.reportError(e);
						error.logData(e.getCause().toString());
				}				
		}
		public void player(int X,int Y,String info1,String info2,String info3,String info4)
		{
				try{
						
						//allEntArr[entOn] = new Ozlin(X*16,Y*16,_bullets,_littleGibs,pad,"Player");
						allEntArr[entOn].ID = Integer.parseInt(info4);
						players.add(allEntArr[entOn]);
				}catch(Exception e){error.reportError(e);}
		}


		public void terminator(int X, int Y,String info1,String info2,String info3,String info4){
				//Enemy enemy = new Enemy(X*16,Y*16,500,info4);
				//enemy.followSprite(allEntArr[0]);
				allEntArr[entOn].ID = Integer.parseInt(info4);
				//enemies.add(enemy);
		}
		public void mage(int X,int Y,String mode,String info2,String info3,String info4)
		{
				//allEntArr[entOn] = new Mage(X*16,Y*16,mageBullets,Integer.parseInt("3"),info4);
				allEntArr[entOn].followSprite(allEntArr[0]);
				allEntArr[entOn].watchLevel(level);
				mages.add(allEntArr[entOn]);
		}
		public void skeleton(int X, int Y,String info1,String info2,String info3,String info4){
				error.logData("info4 =" + info4);
				//allEntArr[entOn] = new Skeleton(X*16,Y*16,info4);
				allEntArr[entOn].watchLevel(level);
				skeletons.add(allEntArr[entOn]);
		}
		public void spike(int X, int Y,String Angle,String info2,String info3,String info4){
				Angle = "0";
				Spike spike = new Spike(X*16,Y*16,Integer.parseInt(Angle));
				spikes.add(spike);
		}
		public void coin(int X,int Y,String info1,String info2,String info3,String info4)
		{
				EthSprite coin = new EthSprite(X*16,Y*16,info4);
				coin.setTargetToActivate(info3);
				coin.loadGraphic("coin.png", true, true, 16, 16);	
				coin.addAnimation("rotating", new int[]{0, 1, 2}, 4, true);
				coin.offset.x = 3;
				coin.offset.y = 3;
				coin.centerOffsets();
				coin.play("rotating");
				coin.immovable = true;
				coins.add(coin);
		}	
		public void peppermintpu(int X, int Y,String info1,String info2,String info3,String info4){
				FlxSprite ppowerup = new FlxSprite(X*16,Y*16);
				ppowerup.loadGraphic("peppermintpowerup.png", true, true, 16, 16); 
				ppowerup.immovable = true;
				//speedUps.add(ppowerup);
		}		
//		public void portal(int X,int Y,String info1,String info2,String info3,String info4){
//				FlxSprite portal = new FlxSprite(X*16,Y*16);//40,50
//				portal.loadGraphic("portal.png", true, true, 16, 16);
//				portal.addAnimation("spinning", new int[]{0, 1, 2}, 6, true);
//				portal.play("spinning");
//				portal.immovable = true;
//				
//				add(portal);
//
//				portalcoin = new FlxSprite(420, 300);//first (170, 80) Second (170, 160)
//				portalcoin.loadGraphic("Portalcoin.png", true, true, 16, 16);
//				//	portalcoin.addAnimation("stand", new int[]{0}, 0, false);
//				portalcoin.addAnimation("rotate", new int[]{0, 1, 2}, 4, true);
//				portalcoin.play("rotate");
//				portalcoin.exists = false;
//				add(portalcoin);
//		}
    @Override
		public void update()
		{	
				
			
				if(mages.countLiving() > 0){
						FlxG.collide(_bullets,mages , Collision);
						FlxG.collide(mageBullets,allEntArr[0],Collision);
						FlxG.collide(mageBullets,level);
				}
				if(enemies.countLiving() > 0){
						FlxG.collide(_bullets,enemies , Collision);
						FlxG.collide(enemies,allEntArr[0],Collision);
				}
				if(skeletons.countLiving() > 0){
						FlxG.collide(_bullets,skeletons, Collision);
						FlxG.collide(skeletons,allEntArr[0],Collision);
				}
//				if(portals.countLiving() > 0){
//						FlxG.collide(portals,allEntArr[0],win);
//				}
//				if(portalcoins.countLiving() > 0)
//				{
//						FlxG.collide(portalcoins,allEntArr[0],winUnlock);
//				}
				if(coins.countLiving()>0)
				{
						FlxG.collide(coins,players,getCoin);
				}
				if(spikes.countLiving() > 0)
				{
						FlxG.collide(spikes,players,Collision);
						FlxG.collide(_bullets, spikes);
						
				}
//				if(speedUpAmount > 0){
//						FlxG.overlap(speedUps,allEntArr[0],getPP);
//				}
//				if(isLock && isKey){
//						FlxG.collide(enemies,lock);
//						FlxG.collide(skeletons,lock);
//						FlxG.collide(mages,lock);
//						FlxG.collide(mageBullets,lock);
//						FlxG.collide(_bullets,lock);
//						FlxG.collide(allEntArr[0],lock);
//						FlxG.overlap(key,allEntArr[0],openlock);
//				}
		
				FlxG.collide(entitiesS,level);
				FlxG.collide(entitiesS,entitiesS);
				FlxG.collide(players,level);
				FlxG.collide(_bullets,level);
				super.update();
				
		}
		public int FindEnt(float X,float Y){
				for(int ent = 0;ent < allEntArr.length;ent++)
				{
						if(allEntArr[ent] != null){
								if(allEntArr[ent].x == X && allEntArr[ent].y == Y )
								{
										return ent;
								}		 
						}
				}
				error.logData("ent not found");
				return 0;
		}
		
		
		public int[] FindEnt(String Id){
			int entsWithId = 0;
			for(int ent = 0;ent < allEntArr.length;ent++)
			{
					if(allEntArr[ent] != null){
							if(allEntArr[ent].id.equals(Id))
							 {
							 entsWithId++;
							 }		 
					 }
			}
			int[] sprites = new int[entsWithId];
			int count = 0;
				for(int ent = 0;ent < allEntArr.length;ent++)
				{
						if(allEntArr[ent] != null){
								if(allEntArr[ent].id.equals(Id))
								{
								sprites[count] = ent;
								count++;
								}		 
						}
				}
				return sprites;
		}

		IFlxCollision Collision = new IFlxCollision()
		{
				@Override
				public void callback(FlxObject Key, FlxObject Player)
				{						
				    
				}
		};		

		

		IFlxCollision win = new IFlxCollision()
		{
				@Override
				public void callback(FlxObject Portal, FlxObject Player)
				{
					
						add(new FlxText(200,22 , 100, "You win!"));			//22
						allEntArr[0].exists = false;
						FlxG.play("Portal.mp3");
						FlxG.fade(0xff000000,1, onFade);
				}
		};

//		IFlxCollision winUnlock = new IFlxCollision()
//		{
//				@Override
//				public void callback(FlxObject Portalcoin, FlxObject Player)
//				{						
//						FlxG.play("Pcoin.mp3");
//						Portalcoin.kill();	
//						portal.exists = true;			        
//				}
//		};					

		IFlxCollision getCoin = new IFlxCollision()
		{
				@Override
				public void callback(FlxObject Coin, FlxObject Player)
				{		
										
						
						
						Coin.kill();
						FlxG.play("Coin.mp3");
						
						//status.setText("SCORE: "+(coins.countDead()*100));	  		
				}
		};					
		

		public IFlxCamera onFade = new IFlxCamera()
		{
				@Override
				public void callback()
				{onBack();}
		};


		private void onBack(){
				if(openFromLe)
				{
						FlxG.switchState(new LevelEditor());
				}else{
						FlxG.switchState(new MenuState());
				}
		}

		public boolean getPlayerMode()
		{
				Boolean result = false;
				if(playerMode == 1){result = true;}	
				return result;
		}

		@Override
		public void draw()
		{
				super.draw();
		}

		@Override
		public void destroy()
		{
				// TODO: Implement this method
				super.destroy();
				pause = null;
				level = null;
				block = null;
				playerLE = null;
				pad = null;

		}	
}


