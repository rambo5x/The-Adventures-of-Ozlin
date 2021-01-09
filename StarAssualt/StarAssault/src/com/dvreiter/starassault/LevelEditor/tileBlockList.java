package com.dvreiter.starassault.LevelEditor;


import org.flixel.*;
import org.flixel.event.*;
import com.dvreiter.starassault.Tools.*;

public class tileBlockList extends FlxObject {
 		public static int listWidth = 15;
		public static int listHeight = 2;
		public static int listBlockSpace = 2;
		FlxTileblock block1;
		FlxTileblock block2;
		

    @Override
    public tileBlockList(int x, int y,FlxTileblock titleBlock1,FlxTileblock titleBlock2){
				super(x, y);
				block1 = titleBlock1;
				block2 = titleBlock2;
				
				int quickWidth = (listWidth*16) +(2*listBlockSpace) + ((listWidth-1)*listBlockSpace);
			
				titleBlock1.makeGraphic(quickWidth, FlxG.height, 0xff218fb8);// 390, 230		
				titleBlock1.scrollFactor.x = titleBlock1.scrollFactor.y = 0;
		
				titleBlock2.makeGraphic(quickWidth-4, FlxG.height-4, 0xff209ece);// 390, 230
				titleBlock2.scrollFactor.x = titleBlock2.scrollFactor.y = 0;
				
				ErrorReporter error=new ErrorReporter();
		}
		
    @Override
    public void update() {
        super.update();
		}

		public void updateDisplay()
		{
				int quickWidth = (listWidth*16) +(2*listBlockSpace) + ((listWidth-1)*listBlockSpace);

				block1.makeGraphic(quickWidth, FlxG.height, 0xff218fb8);// 390, 230		
				block2.makeGraphic(quickWidth-4, FlxG.height-4, 0xff209ece);// 390, 230
				block1.x = FlxG.width-quickWidth;
				block1.width = quickWidth;
				block2.x = FlxG.width-(quickWidth-2);
				block2.width = quickWidth-4;
		}
		
		@Override
		public void destroy()
		{
				// TODO: Implement this method
				super.destroy();
		}
}
