package com.dvreiter.starassault.Menu;

import org.flixel.FlxButton;
import org.flixel.FlxG;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.event.IFlxButton;
import org.flixel.*;
import org.flixel.ui.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Levels.*;
import com.dvreiter.starassault.*;
//import com.dvreiter.starassault.Menu.LE.*;

public class ShareLevels extends FlxState
{		
	private FlxText expText, titleText;
	private FlxButton backbtn, openGmailbtn;
	@Override
	public void create()
	{			
		FlxG.setBgColor(FlxG.BLUE);

		backbtn = new FlxButton(5, 215, "Back", new IFlxButton(){@Override public void callback(){onBack();}});
		add(backbtn);		
		
		openGmailbtn = new FlxButton(150, 200, "Open Gmail", new IFlxButton(){@Override public void callback(){onGmail();}});
		add(openGmailbtn);		

		titleText = new FlxText(110, 10, 350, "Sharing Levels");
		titleText.setSize(20);
		titleText.antialiasing = true;
		//titleText.setColor(FlxG.RED);
		add(titleText);

		expText = new FlxText(30,40,350,"Go to your files and open up to your directory path says \"storage/emulated/0.\" Then look for the folder named bobrun, you will open it and find the levels you've saved. Pick a level and when using gmail to compose an email to send, attach the level file you want. Then send the level to the company's gmail, ironleprechaungames@gmail.com");
		expText.setSize(12);
		expText.antialiasing = true;
		add(expText);
	}
    @Override
	public void update(){	

		super.update();
	}

	private void onBack(){
	//	FlxG.switchState(new PlayStateLESettings());
	}
	
	private void onGmail(){
		FlxU.openURL("https://mail.google.com/mail/mu/mp/834/#co");
	}
} 
