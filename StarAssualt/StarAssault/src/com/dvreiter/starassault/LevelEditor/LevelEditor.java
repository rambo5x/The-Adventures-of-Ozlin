package com.dvreiter.starassault.LevelEditor;


import android.os.*;
import com.badlogic.gdx.utils.*;
import com.dvreiter.starassault.Player.*;
import java.io.*;
import org.flixel.*;
import org.flixel.event.*;
import org.flixel.ui.*;
import com.dvreiter.starassault.Menu.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import android.widget.*;
import org.flixel.ui.event.*;
import com.dvreiter.starassault.Tools.*;

public class LevelEditor extends FlxState//use to edit level
{	

		private static final int TILE_WIDTH = 16;
		private static final int TILE_HEIGHT = 16;

		private FlxText coords;

		public Txtsaver pr;

		//new Level info
		public static boolean loadLevel;
		public static int levelWidth,levelHeight;
		public static String levelName;
		public static Boolean playerMode;
		private String color = "0xFF00CCF";
		private Boolean isPlayer = false;
		private Boolean isPortal = false;
		private boolean isPortalUnlock = false;
		private boolean isLockDoor = false;
		private boolean isLockDKey = false;
		private FlxTimer timer;

		public FlxButton pause;
		public EthTileMap level;
		public FlxTileblock block;
		private FlxButton closeMenu;
		private FlxButton Menu;
		private FlxButton typeBtn; 
		private FlxButton itemBtn;
		private FlxButton itemBackBtn;
		private FlxButton ItemDisplay;
		private FlxButton savebtn;
		private FlxButton BlockOptions;
		private FlxButton backbtn;
		private FlxButton BackCheckBtn,BackCheckBtnNo;
		private FlxButton switchPlayer;
		private int BlockOMode;
		private int blockX,blockY;

		private int lastPBlockX,lastPBlockY;

		private int Item, Type;
		private String []blockNames;
		private String ifSaved = " ";
		public static final String FntRobotoRegular = "Roboto-Regular.ttf";

		FlxVirtualPad pad;
		PlayerLE playerLE;

		ErrorReporter error;

		//Advance stuffd

		private FlxText ifSavedTxt;

		private String[] entityNames;

		private String[] entityFileNames;

		private FlxTileblock titleBlock1;

		private FlxTileblock titleBlock2;

		private tileBlockList list;

		private FlxRadioButtonGroup radioButtons;

		private String[] allnames;

		private FlxUIGroup maybebaby;

		private FlxSave guiAlpha;

		@Override
		public void create()
		{				
		
//		int[] hi = {1,2,4};
//			Car c = new Car(hi); // 2
//			try {
//				FileOutputStream fs = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bobrun/test.ser");
//				ObjectOutputStream os = new ObjectOutputStream(fs);
//				os.writeObject(c); // 3
//				os.close();
//			} catch (Exception e) { e.printStackTrace(); }
//		
		
		JsonParser pars = new JsonParser();
			pars.ParseData();
		
		
				error = new	ErrorReporter();
				error.logData("Test2");

				if (loadLevel == true)
				{
					
					
				try {
				error.logData("reading data");
				File Directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bobrun/Worlds/" + levelName);
				FileInputStream fis = new FileInputStream(Directory + "/LevelData.ser" );
				ObjectInputStream ois = new ObjectInputStream(fis);
				EthLevelData Ethdata = (EthLevelData) ois.readObject(); // 4
				//if something returns an object u can cast it
				ois.close();		
				levelWidth = Ethdata.WidthInTiles;
					level = new EthTileMap( Ethdata.TileArray , Ethdata.WidthInTiles);
					add(level);
				
					error.logData(level.getString1DTileArray());
					
					
				} catch (Exception e) { e.printStackTrace(); 
					error.reportError(e);
					FlxG.switchState(new PlayStateLESettings());	
				}
										
				}
				else
				{
					
						try{int data[] = new int[levelHeight * levelWidth];
						level = new EthTileMap(data , levelWidth);
						add(level);
						}catch(Exception e){error.reportError(e);}
				}
try{
				guiAlpha = new FlxSave();
				guiAlpha.bind("Options");//Test
				//Save
				if(guiAlpha.data.get("Alpha",float.class) == null)
				{
						guiAlpha.data.put("Alpha", 7);
						guiAlpha.flush();
				}

				//load
				@SuppressWarnings("unchecked")
				float  Alp = guiAlpha.data.get("Alpha", float.class);
				float AlphaLevel = Alp/10;

				FlxG.setBgColor(0xFFE0CCFF);
				FlxG.mouse.show();

				//level end

				String[] tempBlockNames = {"Eraser","Grass1","Bricks11","Bricks2","GrassLeft","GrassRight","GrassAlone","DirtStone1","GrassSolid","FullDirt","Stone1","NetherBlock", "Grass2","Invalid Block","Invalid Block","Invalid Block","Invalid Block","Invalid Block","DirtStone2","Invalid Block","Invalid Block","Stone2"};	
				blockNames = tempBlockNames;
				String[] tempEntityNames = {"Player","Coin","Locked Door", "Key", "PepperMint", "EndPortal","EndPortalUnlock","Skeleton","Spikes","Switch", "Mage" , "Terminater" ,"Turret" , "Slime" };
				entityNames = tempEntityNames;
				String[] tempEntityFileNames = {"budderking","coin","keylock", "lockkey" , "peppermintpowerup" , "portal" , "Portalcoin" , "skeletonminion", "spikes" , "switch" , "mage" , "terminator", "switch","switch" };
				entityFileNames = tempEntityFileNames;

				coords = new FlxText(10, 10, 90, "Coords:");	
				coords.setSize(10);
				coords.setColor(0x3a5c39);
				coords.scrollFactor.x = coords.scrollFactor.y = 0;
				add(coords);

				int xCoord = 316;
				ifSavedTxt = new FlxText(40, 60, 100, "");
				ifSavedTxt.setSize(16);
				add(ifSavedTxt);
				timer = new FlxTimer();


				int quickWidth = (tileBlockList.listWidth*16) +(2*tileBlockList.listBlockSpace) + ((tileBlockList.listWidth-1)*tileBlockList.listBlockSpace);
				titleBlock1 = new FlxTileblock(FlxG.width-quickWidth, 0, quickWidth, FlxG.height);//780, 400
				add(titleBlock1);

				titleBlock2 = new FlxTileblock(FlxG.width-(quickWidth-2), 2, quickWidth-4,FlxG.height-4);//780, 400
				add(titleBlock2);

				titleBlock1.setAlpha(AlphaLevel);
				titleBlock2.setAlpha(AlphaLevel);
				list= new tileBlockList(20,20,titleBlock1,titleBlock2);
				add(list);

				savebtn = new FlxButton(xCoord, FlxG.height-27, "Save", new IFlxButton(){@Override public void callback(){saveGame(levelName);}});
				savebtn.scrollFactor.x = savebtn.scrollFactor.y = 0;
				savebtn.setAlpha(AlphaLevel);
				savebtn.label.setAlpha(AlphaLevel);
				add(savebtn);	
				

				backbtn = new FlxButton(xCoord, 5, "Back", new IFlxButton(){@Override public void callback(){onBack();}});
				backbtn.scrollFactor.x = backbtn.scrollFactor.y = 0;
				backbtn.setAlpha(AlphaLevel);
				backbtn.label.setAlpha(AlphaLevel);
				add(backbtn);	

				Menu = new FlxButton(xCoord, 25, " ", new IFlxButton(){@Override public void callback(){onMenu();}});
				Menu.loadGraphic("LEShip.png");
				add(Menu);	
				Menu.visible = false;

				itemBtn = new FlxButton(xCoord, 55, "Grass", new IFlxButton(){@Override public void callback(){onItem();}});
				itemBtn.scrollFactor.x = itemBtn.scrollFactor.y = 0;
				itemBtn.setAlpha(AlphaLevel);
				itemBtn.label.setAlpha(AlphaLevel);
				add(itemBtn);

				ItemDisplay = new FlxButton(xCoord, 75);
				ItemDisplay.scrollFactor.x = ItemDisplay.scrollFactor.y = 0;
				ItemDisplay.height= 16;
				ItemDisplay.setAlpha(AlphaLevel);
				add(ItemDisplay);

				switchPlayer = new FlxButton(xCoord, 131, "SimulateLevel", new IFlxButton(){@Override public void callback(){onSimulateLevel();}});
				switchPlayer.scrollFactor.x = switchPlayer.scrollFactor.y = 0;
				switchPlayer.setAlpha(AlphaLevel);
				switchPlayer.label.setAlpha(AlphaLevel);
				add(switchPlayer);
				
				closeMenu = new FlxButton(xCoord, 111, "Close Menu", new IFlxButton(){@Override public void callback(){onCloseMenu();}});
				closeMenu.scrollFactor.x = closeMenu.scrollFactor.y = 0;
				closeMenu.setAlpha(AlphaLevel);
				closeMenu.label.setAlpha(AlphaLevel);
				add(closeMenu);	

				pad = new FlxVirtualPad(FlxVirtualPad.DPAD_FULL,0);pad.setAlpha(0.5f);


				playerLE = new PlayerLE(2 * 16, 0, pad);

				FlxG.camera.follow(playerLE, FlxCamera.STYLE_PLATFORMER);
				FlxG.camera.setBounds(0, 0, levelWidth * 16, levelHeight * 16, true);// 1st 400,240  2nd 800,240, 3rd 1200,48
				FlxUISkin skin = new FlxUISkin();

				skin.DISABLED = 3;
				skin.HIGHLIGHT_DISABLED = 4;
				skin.ACTIVE_NORMAL = 5;
				skin.ACTIVE_HIGHTLIGHT = 6;
				skin.ACTIVE_PRESSED = 7;
				skin.ACTIVE_DISABLED = 8;
				skin.ACTIVE_HIGHTLIGHT_DISABLED = 9;
				skin.labelPosition = FlxUISkin.LABEL_RIGHT;
				skin.setFormat(FntRobotoRegular, 18);				

				maybebaby = new FlxUIGroup(0,0, "");
				add(maybebaby);
				maybebaby.marginLeft = 0;
				maybebaby.marginTop = 0;
				


				//combine 2 String Arrays
				allnames = new String[tempBlockNames.length+ tempEntityFileNames.length];
				for(int i = 0;i < tempBlockNames.length;i++)
				{
						allnames[i] = tempBlockNames[i];
				}
				for(int i = 0;i < tempEntityFileNames.length;i++)
				{
						allnames[tempBlockNames.length + i] = tempEntityFileNames[i];
				}
				error.logData(String.valueOf(allnames.length));
				//done combining
				
				radioButtons = new FlxRadioButtonGroup();
				

				try{
						int count = 0;
						for (int i = 0;i < 9;i++)//height
						{
								for(int e = 0;e < 6;e++)//width
								{
										if(count < tempBlockNames.length)
										{
												skin.setImage("BlockTextures/" + allnames[count]+".png",16,16);			
												FlxRadioButton button = new FlxRadioButton(titleBlock2.x+(e*30)+2,titleBlock2.y+20+(i*30),String.valueOf(count),radioButtons,skin,"");
												button.setAlpha(AlphaLevel);
												maybebaby.add(button);
										}
										if((count >tempBlockNames.length-1) && (count < allnames.length))
										{
												skin.setImage( allnames[count]+".png",16,16);			
												FlxRadioButton button = new FlxRadioButton(titleBlock2.x+(e*30)+2,titleBlock2.y+20+(i*30),String.valueOf(count),radioButtons,skin,"");
												button.setAlpha(AlphaLevel);
												maybebaby.add(button);
										}
										count++;				
								}
						}		
						radioButtons.onChange = group;
				}catch(Exception e){error.reportError(e);}

				add(playerLE);					
				add(pad);

				BackCheckBtn = new FlxButton(160, 90, "Are You sure\n     Yes", new IFlxButton(){@Override public void callback(){onBackCheckBtn();}});
				BackCheckBtn.scrollFactor.x = BackCheckBtn.scrollFactor.y = 0;
				add(BackCheckBtn);

				BackCheckBtnNo = new FlxButton(160, 110, "No", new IFlxButton(){@Override public void callback(){onBackCheckBtnNo();}});
				BackCheckBtnNo.scrollFactor.x = BackCheckBtnNo.scrollFactor.y = 0;
				add(BackCheckBtnNo);
				
				BackCheckBtnNo.visible = false;
				BackCheckBtn.visible = false;
				
				onItem();
}catch(Exception e){error.reportError(e);}

				//pr = new Txtsaver();
//				try{
//						threadtest tesd = new threadtest(this);
//						Thread thred = new Thread(tesd);
//						thred.start();
//
//				}catch(Exception e){
//						error.logData(e.toString());
//				}
		}

    @Override
		public void update()
		{	
				if (FlxG.keys.pressed("BACK"))
				{onMenu();}//If back button is pressed

				if (playerLE.visible = true)
				{
						blockX = (int)FlxG.mouse.x / TILE_WIDTH;
						blockY = (int)FlxG.mouse.y / TILE_HEIGHT;
						//Get data map coordinate

						coords.setText("Coords:" + "\n X:" + blockX + "\n Y:" + blockY + "\n" + Item + "\n" + isPlayer + "\nTile\n" + level.getTile(blockX, blockY) + "\nmouseX/Y" + FlxG.mouse.screenX / 16 + "/" + FlxG.mouse.screenY / 16+ "\n" + levelName);
						ifSavedTxt.setText(ifSaved);

//						if (FlxG.mouse.pressed() && FlxG.mouse.screenX < itemBackBtn.x || FlxG.mouse.screenX > itemBackBtn.x + itemBackBtn .width)
//						{	
//								if (FlxG.mouse.screenX > 85 || FlxG.mouse.screenY  < 125)
//								{
//										if (FlxG.mouse.screenX < 100 || FlxG.mouse.screenY > 15)
//												onCloseMenu();
//								}
//						}
					if (FlxG.mouse.pressed() && itemBtn.visible == false)
						{
								placeItem(blockX, blockY);
								lastPBlockX = blockX;
								lastPBlockY = blockY;
						}
						Menu.x = playerLE.x;
						Menu.y = playerLE.y;
				}
				super.update();	
		}

		private void placeItem(int mx, int my)
		{
				if (level.getTile(mx, my) == 22)
				{isPlayer = false;}
				if (level.getTile(mx, my) == 24)
				{isLockDoor = false;}
				if (level.getTile(mx, my) == 25)
				{isLockDKey = false;}
				if (level.getTile(mx, my) == 27)
				{isPortal = false;}
				if (level.getTile(mx, my) == 28)
				{isPortalUnlock = false;}


				if(!(allnames[Item] == "Invalid Block"))
				{
						level.setTile(mx, my, Item, true);
//						level.setTile(mx, my+1,Item,true);
//						level.setTile(mx,my-1,Item,false);
//						
//					level.setTile(mx-1, my, Item, true);
//					level.setTile(mx+1, my, Item, true);
//					level.setTile(mx-1, my-1, Item, true);
//					level.setTile(mx+1, my-1, Item, true);
//					level.setTile(mx-1, my+1, Item, true);
//					level.setTile(mx+1, my+1, Item, true);
					
				}

				switch (Item)
				{
						case 22:if(isPlayer == false)
								{
										level.setTile(mx, my, Item, true);
										isPlayer = true;
								}break;
						case 23:level.setTile(mx, my, Item, true);
								break;
						case 24:if (isLockDoor == false)
								{
										level.setTile(mx, my, Item, true);
										isLockDoor = true;
								}
								break;
						case 25:if (isLockDKey == false)
								{
										level.setTile(mx, my, Item, true);
										isLockDKey = true;
								}
								break;
						case 26:level.setTile(mx, my, Item, true);
								break;
						case 27:if (isPortal == false)
								{
										level.setTile(mx, my, Item, true);
										isPortal = true;
								}
								break;
						case 28:if (isPortalUnlock == false)
								{
										level.setTile(mx, my, Item, true);
										isPortalUnlock = true;
								}
								break;
				}				
		}

		public void updateItemDisplay()
		{
				try
				{
						if (Item < blockNames.length)
						{
								itemBtn.label.setText(blockNames[Item]);
								ItemDisplay.loadGraphic("BlockTextures/" + blockNames[Item] + ".png");				
						}
						if (Item > blockNames.length- 1)
						{
								itemBtn.label.setText(entityNames[Item-blockNames.length]);
								ItemDisplay.loadGraphic(entityFileNames[Item-blockNames.length] + ".png");			
						}
				}
				catch (Exception e)
				{
						error.reportError(e);
				}
		}	

		private void saveGame( String fileName)
		{
			try {
				
				File Directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bobrun/Worlds/" + fileName);
				if(Directory.exists() == false){
					Directory.mkdirs();
				}
				
				FileOutputStream fs = new FileOutputStream(Directory + "/LevelData.ser" );
				ObjectOutputStream os = new ObjectOutputStream(fs);
				level.PrepareSave();
				os.writeObject(level.levelData); // 3
				os.close();
				
				if (ifSaved == " ")
				{ifSaved = "Saved";}
				timer.start(3, 1, Tstop);
			} catch (Exception e) { 
				error.reportError(e);
				ifSaved = "Save Failed";
				timer.start(3, 1, Tstop);
			} 							
		}

		//Timer fix
		IFlxTimer Tstop = new IFlxTimer(){@Override
				public void callback(FlxTimer flxTimer)
				{ifSaved = " ";
				}
		};

		IFlxRadioButtonGroup group = new IFlxRadioButtonGroup(){
				@Override
				public void callback()
				{
						Item = Integer.parseInt(radioButtons.getSelected());
						updateItemDisplay();
				}		
		};


		//Buttons
		private void onItem()
		{
				Item++;
				updateItemDisplay();
		}

		private void onBack()
		{
				BackCheckBtnNo.visible = true;
				BackCheckBtn.visible = true;
		}

		private void onBackCheckBtn()
		{
				FlxG.switchState(new MenuState());
		}

		private void onBackCheckBtnNo()
		{
				BackCheckBtnNo.visible = false;
				BackCheckBtn.visible = false;
		}

		private void onMenu()
		{
				if (!FlxG.keys.pressed("BACK")){
					//level.setTile(lastPBlockX, lastPBlockY, 0);
					}
				savebtn.visible = true;
				itemBtn.visible = true;
				ItemDisplay.visible = true;
				closeMenu.visible = true;
		//		pad.active = true;
				pad.visible = true;
				backbtn.visible = true;
				switchPlayer.visible = true;
				titleBlock1.visible = true;
				titleBlock2.visible = true;
				Menu.visible = false;
				maybebaby.visible = true;
				maybebaby.active = true;
				closeMenu.visible = true;
		}

		private void onCloseMenu()
		{
				pad.visible = false;
		//		pad.active = false;
				savebtn.visible = false;
				itemBtn.visible = false;
				ItemDisplay.visible = false;
				backbtn.visible = false;
				switchPlayer.visible = false;
				titleBlock1.visible = false;
				titleBlock2.visible = false;
				Menu.visible = true;
				maybebaby.visible = false;
				maybebaby.active = false;
				closeMenu.visible = false;

		}


		public void onSimulateLevel()
		{
				saveGame(levelName);
				LevelSimulator.filename = levelName;
				LevelSimulator.openFromLe = true;
				FlxG.switchState(new LevelSimulator());
			
		}
		
		public void onTest(){
				
				ErrorReporter.logData("test worked" + levelName);
		}

		@Override
		public void draw(){super.draw();}

		@Override 
		public void destroy()
		{
				super.destroy();
				pause = null;
				level = null;
				block = null;
				playerLE = null;
				pad = null;
		}	
}

class threadtest implements Runnable
{
		private LevelEditor PSL;
		public threadtest(LevelEditor tst){
				PSL = tst;
		}
		@Override
		public void run()
		{
				ErrorReporter.logData("hi,how are you");
				PSL.onTest();
		}
}

//class ThreadA implements Runnable{
//		public void run(){
////do something
//		}
//		public void setSomething(){}
//}
//
//class ThreadB implements Runnable{
//		private ThreadA aref;
//		public ThreadB(ThreadA ref){aref=ref;}
//		public void run(){
//				aref.setSomething();//calling the setSomething() with this thread!! no thread a
//		}
//}
//
//class Foo{
//		public static void main(String...arg){
//				ThreadA a=new ThreadA();
//				new Thread(a).start();
//				ThreadB b=new ThreadB(b);
//				new Thread(b).start();
//		}
//}
