package com.dvreiter.starassault.LevelLoader;

import org.flixel.FlxGame;
import org.flixel.FlxCamera;
import org.flixel.FlxG;
import com.dvreiter.starassault.*;
import com.dvreiter.starassault.Menu.*;
import com.dvreiter.starassault.Levels.*;
import org.flixel.system.*;
import java.util.*;
import com.dvreiter.starassault.AstroAce.*;

public class StarAssaultGame extends FlxGame
{

	public static String time = Calendar.getInstance().getTime().toString();
	public StarAssaultGame()
	{
		super(400,240,MenuState.class, 2, 50, 50);	//400,240 for Ozlin
	}
}
