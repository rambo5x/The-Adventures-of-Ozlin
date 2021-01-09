package com.dvreiter.starassault.LevelEditor;


import org.flixel.FlxButton;
import org.flixel.FlxG;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.event.IFlxButton;
import org.flixel.*;
import org.flixel.ui.*;
import org.flixel.event.*;
import org.flixel.ui.FlxTab;
import org.flixel.ui.FlxTabGroup;
import org.flixel.ui.FlxUIGroup;
import org.flixel.ui.event.IFlxUIListener;
import org.flixel.system.input.*;
import java.io.*;
import android.os.*;
import com.badlogic.gdx.*;
import com.dvreiter.starassault.Levels.*;
import com.dvreiter.starassault.Menu.*;
import com.badlogic.gdx.graphics.g3d.environment.*;
import com.dvreiter.starassault.Tools.*;



public class PlayStateLESettings extends FlxState
{		 

		private int levelSlot = 0;

    private FlxButton backbtn;
		
		private int Width = 25;
		private int Height= 15;

		public FlxState currentState;
		public FlxState PlaystateTwo;
		private FlxDialogBox inputHeight;

		private FlxButton delete;
		private File[] files; 
		private String[] Filelist;
		private long currentTime;
		private EthLevelData loadLevelData;

		private ErrorReporter error;

		private int levelWidth;
		private int levelHeight;
		private long levelTime;
		private long displayTime;
		private long levelHours;
		private long levelDays;
		private long levelMonths;
		private String timeDisplayInfo;
		private long levelMinutes;
		private String levelNameInfo = "";

		private FlxButton deleteYes;
		private FlxButton deleteNo;
		private FlxTileblock ifDeleteBlock;
		private FlxText ifDeleteFlxTxt,popText;

		public FlxTileblock titleBlock1;
		private FlxTileblock titleBlock2;

		private FlxTileblock levelSelectBlock1;
		private FlxTileblock levelSelectBlock2;
		private FlxButton levelLeft;
		private FlxButton levelDisplay;
		private FlxButton levelRight;

		private FlxTileblock levelInfoBlock1;
		private FlxTileblock levelInfoBlock2;
		private FlxText levelDisplayTime;
		private FlxText levelDisplayInfo;
		private FlxText levelDisplayLvl;
		private FlxText levelDisplayLvlName;

		private FlxButton widthUp;
		private FlxButton widthDown;
		private FlxButton heightUp;
		private FlxButton heightDown;
		private FlxText flxWidth;
		private FlxText flxHeight;
		private ButtonHoldIncrementer widthUpIncre;
		private ButtonHoldIncrementer widthDownIncre;
		private ButtonHoldIncrementer heightUpIncre;
		private ButtonHoldIncrementer heightDownIncre;
		private FlxTileblock levelWHBlock1;
		private FlxTileblock levelWHBlock2;

		private FlxTileblock ifDeleteBlock2;

		private FlxButton levelDisplayEnterLvl;

		private FlxButton simulate;

		private String editLevelName = "";

		private FlxButton playerMode;

		private boolean playerHasGravity = false;

		private FlxText title;

		private FlxTimer timer;

		private FlxButton levelEditorSettings;

		
		
		@Override
		public void create()
		{			
		
		
				timer = new FlxTimer();
				FlxG.setBgColor(0xFF00CCFF);
				loadLevelData = new EthLevelData();
				currentTime = System.currentTimeMillis();

				error = new ErrorReporter();

				//Title Stuff
				titleBlock1 = new FlxTileblock(115, 8, 200, 34);//780, 400
				titleBlock1.makeGraphic(200, 34, 0xff218fb8);// 390, 230
				add(titleBlock1);

				titleBlock2 = new FlxTileblock(120, 10, 190, 30);//780, 400
				titleBlock2.makeGraphic(190, 30, 0xff209ece);// 390, 230
				add(titleBlock2);

				title = new FlxText(130, 20, 300, "Level Editor Menu");
				title.setSize(15);
				add(title);


				//LevelSelect
				levelSelectBlock1 = new FlxTileblock(5, 43, 254, 34);//780, 400
				levelSelectBlock1.makeGraphic(254, 34, 0xff218fb8);// 390, 230
				add(levelSelectBlock1);

				levelSelectBlock2 = new FlxTileblock(10, 45, 244, 30);//780, 400
				levelSelectBlock2.makeGraphic(244, 30, 0xff209ece);// 390, 230
				add(levelSelectBlock2);

				levelLeft = new FlxButton(12, 50, "<---", new IFlxButton(){@Override public void callback()
								{onLevelLeft();}});
				add(levelLeft);	

				levelDisplay = new FlxButton(levelLeft.x + levelLeft.width, levelLeft.y, "Level Design", new IFlxButton(){@Override public void callback()
								{onLevelDisplay();}});			
				add(levelDisplay);	

				levelRight = new FlxButton(levelDisplay.x + levelDisplay.width, levelLeft.y, "--->", new IFlxButton(){@Override public void callback()
								{onLevelRight();}});
				add(levelRight);	


				//LevelInfo

				levelInfoBlock1 = new FlxTileblock(265, 43, 128, 134);//780, 400
				levelInfoBlock1.makeGraphic(128, 134, 0xff218fb8);// 390, 230
				add(levelInfoBlock1);

				levelInfoBlock2 = new FlxTileblock(270, 45, 118, 130);//780, 400
				levelInfoBlock2.makeGraphic(118, 130, 0xff209ece);// 390, 230
				add(levelInfoBlock2);
// 5,5
				levelDisplayLvl = new FlxText(272, 47, 100, "Level Info");
				levelDisplayLvl.setSize(15);
				add(levelDisplayLvl);

				levelDisplayLvlName = new FlxText(272, 65, 100);
				add(levelDisplayLvlName);

				levelDisplayInfo = new FlxText(272, 87, 100);
				add(levelDisplayInfo);

				levelDisplayTime = new FlxText(272, 107, 100);
				add(levelDisplayTime);

				levelDisplayEnterLvl = new FlxButton(274, 155, "Create New Level", new IFlxButton(){@Override public void callback()
								{onLevelDisplay();}});
				add(backbtn);	
				
			popText = new FlxText(100,180, 200, "Level Editor Demo and a W.I.P!");
			popText.setSize(12);
			popText.scrollFactor.x = popText.scrollFactor.y = 0;
			add(popText);


				//Width and Height adjust
				heightDownIncre = new ButtonHoldIncrementer();
				heightUpIncre = new ButtonHoldIncrementer();
				widthDownIncre = new ButtonHoldIncrementer();
				widthUpIncre = new ButtonHoldIncrementer();

				titleBlock1 = new FlxTileblock(5, 83, 254, 94);//780, 400
				titleBlock1.makeGraphic(254, 94, 0xff218fb8);// 390, 230
				add(titleBlock1);

				titleBlock2 = new FlxTileblock(10, 85, 244, 90);//780, 400
				titleBlock2.makeGraphic(244, 90, 0xff209ece);// 390, 230
				add(titleBlock2);


				widthUp = new FlxButton(12, 87, "   ^   ", new IFlxButton(){@Override public void callback()
								{onWidthUp();}

								private void onWidthUp()
								{
										Width++;// TODO: Implement this method
								}});
				add(widthUp);	

				flxWidth = new FlxText(widthUp.x, widthUp.y + widthUp.height, 100, " ");
				add(flxWidth);

				widthDown = new FlxButton(widthUp.x, flxWidth.y + flxWidth.height, "   v   ", new IFlxButton(){@Override public void callback()
								{onWidthDown();}

								private void onWidthDown()
								{
										if (Width != 25)
												Width--;
								}});
				add(widthDown);	

				heightUp = new FlxButton(widthUp.x + widthUp.width, widthUp.y, "   ^   ", new IFlxButton(){@Override public void callback()
								{onHeightUp();}

								private void onHeightUp()
								{
										Height++;// TODO: Implement this method
								}});
				add(heightUp);	

				flxHeight = new FlxText(heightUp.x, flxWidth.y, 100, "test");
				add(flxHeight);

				heightDown = new FlxButton(heightUp.x, widthDown.y, "   v   ", new IFlxButton(){@Override public void callback()
								{onHeightDown();}

								private void onHeightDown()
								{
										if (Height != 15)
												Height--;// TODO: Implement this method
								}});
				add(heightDown);	

				//GoBack
				backbtn = new FlxButton(5, 215, "Back", new IFlxButton(){@Override public void callback()
								{onBack();}});
				add(backbtn);	

				//DeleteInfo
				delete = new FlxButton(12, 87, "Delete", new IFlxButton(){@Override public void callback()
								{onDelete();}});
				delete.visible = false;
				add(delete);	

				ifDeleteBlock = new FlxTileblock(95, 85, 200, 72);//780, 400
				ifDeleteBlock.makeGraphic(200, 72, 0xff2667ae);// 390, 230
				ifDeleteBlock.visible = false;
				add(ifDeleteBlock);

				ifDeleteBlock2 = new FlxTileblock(97, 87, 196, 68);//780, 400
				ifDeleteBlock2.makeGraphic(196, 68, 0xff337fd2);// 390, 230
				ifDeleteBlock2.visible = false;
				add(ifDeleteBlock2);

				ifDeleteFlxTxt = new FlxText(105, 90, 250, "Are You Sure You Want To Delete");
				ifDeleteFlxTxt.visible = false;
				add(ifDeleteFlxTxt);

				deleteYes = new FlxButton(150, 112, "Yes", new IFlxButton(){@Override public void callback()
								{onDeleteYes();}});
				deleteYes.visible = false;
				add(deleteYes);	

				deleteNo = new FlxButton(150, 132, "No", new IFlxButton(){@Override public void callback()
								{onDeleteNo();}});
				deleteNo.visible = false;
				add(deleteNo);	
				//Simulate level
				simulate = new FlxButton(12, 107, "Simulate Level", new IFlxButton(){@Override public void callback()
								{onSimulateLevel();}});
				simulate.visible = false;
				add(simulate);	

				playerMode = new FlxButton(widthUp.x + widthUp.width+ heightUp.width, 87, "No Gravity Toggle", new IFlxButton(){@Override public void callback()
								{onPlayerMode();}});
				add(playerMode);	

				//Error Info		
				inputHeight = new FlxDialogBox(15, 160, null, "New Level Name", 232, 11);
				inputHeight.label.setColor(0xffffffff);
				inputHeight.setSize(0);
				add(inputHeight);
				
				levelEditorSettings = new FlxButton(FlxG.width - backbtn.width - 5, 215, "Level Editor Settings", new IFlxButton(){@Override public void callback()
								{onLESettings();}private void onLESettings(){		FlxG.switchState(new PlayStateLEInSettigns());}});
										
				add(levelEditorSettings);
				
				final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bobrun/Worlds");

				if (!dir.exists())
						dir.mkdirs(); 
						
				File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bobrun/Worlds");
				Filelist = new String[folder.list().length];
				Filelist = folder.list();
				
				
				try{
				updateLevelDisplay();
				playerModeBtnpdate();
				}catch(Exception e){error.reportError(e);}
				
		}


    @Override
		public void update()
		{	try{
				//levelName
				if (levelSlot == 0)
				{
						levelNameInfo = inputHeight.getText().toString();
						//if(inputHeight.getText().toString() != "")
								
						levelWidth = Width;
						levelHeight = Height;
				}
				if ((levelNameInfo.length() > 4 && levelSlot == 0)||(editLevelName.length() > 9 && levelSlot > 0))
				{
						levelDisplayInfo.y = 87;
						levelDisplayTime.y = 107;//Moves the info and time if the name is long enough to go onto the next line
				}
				else
				{
						levelDisplayInfo.y = 77;
						levelDisplayTime.y = 97;
				}
		
				//levelWH
				levelDisplayInfo.setText("Width:" + levelWidth + "\nHeight:" + levelHeight);		
				levelDisplayLvlName.setText("LevelName:" + levelNameInfo);

				Height += heightUpIncre.update(heightUp);
				Height -= heightDownIncre.update(heightDown);
				Width += widthUpIncre.update(widthUp);
				Width -= widthDownIncre.update(widthDown);
	
				flxWidth.setText("W:" + Width + "\n");
				flxHeight.setText("H:" + Height);
				LevelEditor.levelHeight = Height;
				LevelEditor.levelWidth = Width;
				super.update();
		}catch(Exception e){error.reportError(e);}


		}
		
		private void updateTimeDisplay()
		{
				

				if (levelSlot < 0)
				{
						levelSlot = 0;
				}
				if (levelSlot > Filelist.length)
				{
						levelSlot = Filelist.length ;
				}
				if (levelSlot == 0)
				{
						levelLeft.label.setText("|");
				}
				if (levelSlot == 1)
				{
						levelLeft.label.setText("<---");
				}
				if (levelSlot == Filelist.length)
				{
						levelRight.label.setText("|");
				}
				if (levelSlot == Filelist.length - 1)
				{
						levelRight.label.setText("--->");
				}

				if (levelSlot > 0)
				{
//						loadLevelData.loadLevel(Filelist[levelSlot - 1] + "/leveldata.txt");
//						levelWidth = loadLevelData.getLevelWidth();
//						levelHeight = loadLevelData.getLevelHeight();
//						levelTime = loadLevelData.getLevelTime();
//						
//						playerHasGravity = loadLevelData.getPlayerMode();
						playerModeBtnpdate();
						
						displayTime = currentTime - levelTime;	
						displayTime = ((displayTime / 1000) / 60);

						levelMinutes = displayTime;
						displayTime = displayTime / 60;//hours
						levelHours = displayTime;
						displayTime = displayTime / 24;
						levelDays = displayTime;
						displayTime = displayTime / 31;
						levelMonths = displayTime;

						levelDays -= (levelMonths * 31);
						levelHours -= (levelDays * 24);
						levelMinutes -= (levelHours * 60);

						timeDisplayInfo = "Last Edited:";


						if (levelMonths > 0)
						{
								timeDisplayInfo += levelMonths + "Months,\n";
						}
						if (levelDays > 0)
						{
								timeDisplayInfo += levelDays + "Days\n";
						}
						if (levelHours > 0 && levelMonths == 0)
						{
								timeDisplayInfo += levelHours + "Hours\n";
						}
						if (levelMinutes > 0 && levelDays == 0)
						{	
								timeDisplayInfo += levelMinutes + "Minutes\n";
						}
						timeDisplayInfo += "Ago";
						if (levelMonths > 3)
						{
								timeDisplayInfo = "A Long Time Ago";
						}
						if (levelMonths + levelHours + levelDays + levelMinutes == 0)
						{
								timeDisplayInfo = "Just now";
						}
						levelDisplayTime.setText(timeDisplayInfo);
						levelNameInfo = Filelist[levelSlot - 1];
						}
		}	
		
		private void updateLevelDisplay()
		{
				try{
				
				updateTimeDisplay();
				}catch(Exception e){error.reportError(e);
				}
				if (levelSlot ==  0)
				{
						levelDisplayEnterLvl.label.setText("Create New Level");
						LevelEditor.levelName = inputHeight.getText().toString();
						inputHeight.visible = true;
						levelDisplay.label.setText("New Level");
						LevelEditor.loadLevel = false;
						widthUp.visible = true;
						widthDown.visible = true;
						heightUp.visible = true;
						heightDown.visible = true;
						flxHeight.visible = true;
						flxWidth.visible = true;
						delete.visible = false;
						simulate.visible = false;
						playerMode.x = widthUp.x + widthUp.width+ heightUp.width;
				}
				else
				{
						playerMode.x = widthUp.x + widthUp.width;
						simulate.visible = true;
						levelDisplayEnterLvl.label.setText("Edit Current Lvl");
						delete.visible = true;
						inputHeight.visible = false;
						levelDisplay.label.setText(Filelist[levelSlot - 1]);
						editLevelName = Filelist[levelSlot - 1];
						LevelEditor.levelName = Filelist[levelSlot - 1];
						LevelSimulator.filename = Filelist[levelSlot - 1];
						LevelEditor.loadLevel = true;
						widthUp.visible = false;
						widthDown.visible = false;
						heightUp.visible = false;
						heightDown.visible = false;
						flxHeight.visible = false;
						flxWidth.visible = false;
				}
		}

		private void onDeleteYes()
		{
				try
				{
						File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bobrun/Worlds" + Filelist[levelSlot - 1]);
						f.delete();
				}
				catch (Exception e)
				{
						error.reportError(e);
				}
				levelSlot = 0;
				updateLevelDisplay();
				onDeleteNo();
		}

		private void onDeleteNo()
		{
				deleteYes.visible = false;
				deleteNo.visible = false;
				ifDeleteBlock.visible = false;
				ifDeleteBlock2.visible = false;
				ifDeleteFlxTxt.visible = false;
		}

		private void onDelete()
		{

				deleteYes.visible = true;
				deleteNo.visible = true;
				ifDeleteBlock.visible = true;
				ifDeleteBlock2.visible = true;
				ifDeleteFlxTxt.visible = true;
				ifDeleteFlxTxt.setText("Are You Sure You Want To Delete\n" + '"' + Filelist[levelSlot - 1] + '"');
		}

		//LevelPick
		private void onLevelRight()
		{		
						levelSlot++;	
						updateLevelDisplay();
						playerModeBtnpdate();
		}
		
		private void onLevelDisplay()
		{
				LevelEditor.playerMode = playerHasGravity;
				if (levelSlot == 0)
				{
						
						if(inputHeight.getText().toString() != "")
						{
						LevelEditor.loadLevel = false;
						LevelEditor.levelName = inputHeight.getText().toString();
						error.logData("Change State To" + LevelEditor.levelName);
						FlxG.switchState(new LevelEditor());
						}else{
						title.setText("Please Enter Lvl Name");
						title.setSize(10);
						timer.start(3,1,Tstop);
						}
						
				}else			
				{
						LevelEditor.loadLevel = true;
						LevelEditor.levelName = levelNameInfo;
						FlxG.switchState(new LevelEditor());
				}
				
		}
		
		IFlxTimer Tstop = new IFlxTimer(){@Override
				public void callback(FlxTimer flxTimer)
				{title.setText("Level Editor Menu");
				title.setSize(15);
				}
		};
		
		private void onSimulateLevel()
		{
				LevelSimulator.fileName = levelNameInfo;
				LevelSimulator.openFromLe = false;
				FlxG.switchState(new LevelSimulator());
		}
		
		private void onLevelLeft()
		{
				try
				{
						levelSlot--;
						updateLevelDisplay();
						playerModeBtnpdate();
				}
				catch (Exception e)
				{
						error.reportError(e);
				}
		}

		//Back
		private void onBack()
		{
				FlxG.switchState(new MenuState());
		}

		private void onPlayerMode()
		{
				playerHasGravity = !playerHasGravity;
				LevelEditor.playerMode = playerHasGravity;
				playerModeBtnpdate();
		}
		private void playerModeBtnpdate(){
				if(playerHasGravity){playerMode.label.setText("Gravity Tog");}
				else{playerMode.label.setText("No Graivty Tog");}
		}
}
