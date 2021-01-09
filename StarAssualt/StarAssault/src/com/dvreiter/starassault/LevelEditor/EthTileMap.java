package com.dvreiter.starassault.LevelEditor;

import org.flixel.*;
import org.flixel.system.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.dvreiter.starassault.Mobs.*;
import java.util.*;
import java.io.*;
import com.dvreiter.starassault.Tools.*;

public class EthTileMap extends FlxTilemap implements Serializable
{		
//public String [][]tileArray;

		private String texturePackName = "tilemap.png";
		private boolean hasEntities = false;
		private EthSprite[][] Entities;
		public EthLevelData levelData;

		// make a list of all sprites being immovable and inactive to place them on the tilemap and havw a placejolder block
		
		public EthTileMap(int[] data, int width)
		{	
			super();
			
			loadMap(FlxTilemap.arrayToCSV(new IntArray(data), width),texturePackName , 16, 16);	
			levelData = new EthLevelData();
			levelData.TileArray = this.get1DTileArray();//.clone();
			levelData.WidthInTiles = this.widthInTiles;
		}
		
		public EthTileMap(){
			super();
		}
		
		public int[][] get2DArray()
		{ 
			int[][] ScreenData = new int[this.widthInTiles][this.heightInTiles];
			for (int h = 0;  h <= this.heightInTiles - 1;  h++)
			{
				for (int w = 0; w <= this.widthInTiles - 1; w++)
				{
						ScreenData[w][h] = this.getTile(w,h);
				}
			}
			return ScreenData;
		}

		public int[] get1DTileArray()
		{
				int[]ScreenData = new int[this.widthInTiles * this.heightInTiles];
				int round = 0;

				for (int h = 0;  h < this.heightInTiles;  h++)
				{
						for (int w = 0; w < this.widthInTiles; w++)
						{
								ScreenData[round] =  this.getTile(w , h);	
								round++;
						}
				}
				return ScreenData;	
		}	
		
//	public String[] get1DStringArray()
//	{
//		int count = 0;
//		String[] ScreenData = new String[(widthInTiles)*(heightInTiles)*variableSpots];
//
//		for(int h=0;h< this.heightInTiles;h++)
//		{
//			for(int w=0;w < this.widthInTiles;w++)
//			{
//				
//					//ScreenData[count] = tileArray[w][h];
//					count++;
//				
//			}
//
//		}
//		return ScreenData;
//	}
		
		public String getString1DTileArray(){
			String ScreenData = "{";
//			int round = 0;
//
//			for (int h = 0;  h < this.heightInTiles;  h++)
//			{
//				for (int w = 0; w < this.widthInTiles; w++)
//				{
//					ScreenData +=  this.getTile(w , h)  + ",";	
//					round++;
//				}
//			}
//			ErrorReporter.logData("Width" + widthInTiles + "Height" + heightInTiles);
//			//ScreenData = ScreenData.substring(0, ScreenData.length()-2);
//			ScreenData +="};";
	
//			ScreenData = "{";
//
//			for (int i=0; i < views.length ; i++)
//			{			
//				ScreenData += views[i];
//
//				if (i != views.length - 1){ScreenData += ",";
//
//				}else{ScreenData = "}";}
//			}
			
			int [] views = this.getData().toArray();
			for(int i = 0;i < views.length; i++)
			{
				ScreenData += views[i];
				if (i != views.length - 1){ScreenData += ",";

				}else{ScreenData += "}";}
			}
			ErrorReporter.logData("Width" + widthInTiles + "Height" + heightInTiles);
			//ScreenData = ScreenData.substring(0, ScreenData.length()-2);
			//ScreenData +="};";
			
			
			return ScreenData;	
			
		}
		
		
		
		
		public void PrepareSave(){
			levelData.TileArray = this.get1DTileArray();//.clone();
			levelData.WidthInTiles = this.widthInTiles;
		}
}



//Backup
//	package com.dvreiter.starassault.LevelEditor;
//
//	import org.flixel.*;
//	import org.flixel.system.*;
//	import com.badlogic.gdx.utils.*;
//	import com.badlogic.gdx.math.*;
//	import com.dvreiter.starassault.Menu.Tools.*;
//	import com.dvreiter.starassault.Mobs.*;
//	import java.util.*;
//	import java.io.*;
//
//	public class EthTileMap extends FlxTilemap implements Serializable
//	{		
////public String [][]tileArray;
//
//		private String texturePackName = "tilemap.png";
//		private boolean hasEntities = false;
//		private List<EthSprite> Entities = new ArrayList<EthSprite>();
//
//		public EthTileMap(int[] data)
//		{
//			super();
//			loadMap(FlxTilemap.arrayToCSV(new IntArray(data), 25),texturePackName , 16, 16);		
//		}
//
//		public EthTileMap(){
//			super();
//		}
//
//		public int[][] get2DArray()
//		{ 
//			int[][] ScreenData = new int[this.widthInTiles][this.heightInTiles];
//			for (int h = 0;  h <= this.heightInTiles - 1;  h++)
//			{
//				for (int w = 0; w <= this.widthInTiles - 1; w++)
//				{
//					ScreenData[w][h] = this.getTile(w,h);
//				}
//			}
//			return ScreenData;
//		}
//
//		public int[] get1DTileArray()
//		{
//			int[]ScreenData = new int[this.widthInTiles * this.heightInTiles];
//			int round = 0;
//
//			for (int h = 0;  h <= this.heightInTiles;  h++)
//			{
//				for (int w = 0; w <= this.widthInTiles; w++)
//				{
//					ScreenData[round] =  this.getTile(w , h);	
//					round++;
//				}
//			}
//			return ScreenData;	
//		}	
//	}
//
//
//
//
