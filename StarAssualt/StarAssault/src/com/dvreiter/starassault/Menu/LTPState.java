package com.dvreiter.starassault.Menu;

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
import org.flixel.ui.event.*;
import com.dvreiter.starassault.*;

public class LTPState extends FlxState
{		 
	private FlxText Title;
    private FlxButton backbtn;
	private FlxButton Linkbtn;
	
	@Override
	public void create()
	{			
	//	FlxG.setBgColor(0xFF00CCFF);
// 5,5
		backbtn = new FlxButton(5, 215, "Back", new IFlxButton(){@Override public void callback(){onBack();}});
		add(backbtn);		
		
		// Tab group that holds the tabs and set the tab alignment vertical.
		FlxTabGroup tabGroup = new FlxTabGroup(0, 0);
		tabGroup.align = FlxUIGroup.ALIGN_HORIZONTAL;
		add(tabGroup);

		// Create bunch of tabs.
		tabGroup.addTab(new FlxTab(null, "Controls"));
		tabGroup.addTab(new FlxTab(null, "Story"));
		tabGroup.addTab(new FlxTab(null, "Credits"));
	//	tabGroup.addTab(new FlxTab(null, "Buy"));
		tabGroup.loadDividerSkin();
		tabGroup.setDefault(0);

		// Add content
		tabGroup.addContent(0, new FlxText(80, 50, FlxG.width-80, "Press A To Jump.\n Press B To Shoot.\n Press Y To Dash.\n Press X to enable anti-gravity on some levels.\n Press Left or right button to move to the left or right.\n"));
	//	tabGroup.addContent(1, new FlxText(80, 50, FlxG.width-80, "Bob has been sent on a mission to recover the lost ancient artifact of The Lightning Sword.Bob must go through different parts of the world and fight enemys in his path."));
		tabGroup.addContent(1, new FlxText(80, 50, FlxG.width-80, "Once, there were 4 elemental wizards. Fire, wind, water, and earth. They ruled the world in peace. One day, they had sons and daughters, ready for their royal title to be passed on to the potential children. However, 3 of the element wizard's sons or daughters grew corrupt as the original wizards passed away. Many years later, the world is now under chaotic tyranny. They would do anything to make their villagers miserable. However, there was still hope, for 1 wizard wanted peace. On a long journey, that wizard has to take a stand, and overthrow their thrones. Will the world live in peace, or forever be tyranic?"));
		tabGroup.addContent(2, new FlxText(80, 50, FlxG.width-80, "MCPEGAMER - HEAD DEVELOPER\nETHAN Shelton-2ND PROGRAMER\nVadkline-Art/Icon Designer\nBayrain-Art/Icon Designer\nObeycap-Art\nBrandon\n "));
		//tabGroup.addContent(3, new FlxButton(80, 50, "Go To Info Screen", new IFlxButton(){@Override public void callback(){onInfo();}}));
		
		
	//	add(new FlxText(150, 30, 100, "How to play:\n Press A To Jump.\n Press Left button to move left.\n Press Right button to move right.\n Story: Bob has been sent on a mission to recover the lost ancient artifact of The Lightning Sword.Bob must go through different parts of the world and fight enemys in his path."));
	
	}
    @Override
	public void update(){	

		super.update();}

	private void onBack(){
		FlxG.switchState(new MenuState());
	}
	private void onInfo(){
		FlxG.switchState(new ComingSoonState());
	}
}
