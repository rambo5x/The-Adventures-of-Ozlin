package com.dvreiter.starassault.Menu;
import java.io.*;

public class SimulateLevel
{
	private char[] covertChar;
	private int[] data;
	private int levelWidth;
	private int levelHeight;
	private String filename;
	private long levelTime;
	
	public int[] getLevel()
	{
		return data;
	}
	
	public int getLevelWidth()
	{
		return levelWidth;
	}
	
	public int getLevelHeight()
	{
		return levelHeight;
	}
	public long getLevelTime()
	{
		return levelTime;
	}
	public void loadLevel(String lvlname){
		filename = lvlname;
		String lev = readFile();
		breakDown(lev);



	}
	private void breakDown(String Level)
	{
		covertChar = new char[Level.length()+ 100];
		covertChar = Level.toCharArray();

		int endCreateLevelArray = Level.length();
		int endCreateWH = Level.length();
		int endCreateTime = Level.length();
		int levelSlot = 0;
		String numberCache;
		String LevelW = "";
		String LevelH = "";
		boolean widthHeight = true;
		
		for(int e = 0; e < Level.length();e++)
		{
			if(covertChar[e] == '|')
			{
				for(int i=e+1;i < endCreateWH;i++)
				{

					if(covertChar[i] == ',')
					{
						widthHeight = !widthHeight;
					}
					if(Character.isDigit(covertChar[i]))				
					{	
						if(widthHeight){
							LevelW += Character.toString(covertChar[i]);
							//error += System.lineSeparator()+ LevelW;
						}else{
							LevelH += Character.toString(covertChar[i]);	
							//error += System.lineSeparator() + LevelH;
						}

					}
					if(covertChar[i] == '}')
					{
						levelWidth = Integer.parseInt(LevelW);//LevelW);
						levelHeight = Integer.parseInt(LevelH);
						endCreateWH= i;
						data = new int[levelWidth*levelHeight];
						
					}			
				}	
				
			}
			
			
			if(covertChar[e] == '~')
			{
				String LevelTime = "";
				for(int i=e+1;i < endCreateTime;i++)
				{

					if(Character.isDigit(covertChar[i])){
							LevelTime += Character.toString(covertChar[i]);	
}
					
					if(covertChar[i] == '}')
					{
						levelTime = Long.parseLong(LevelTime);//LevelW);
						endCreateTime = i;

					}			
				}	

			}
			
			
			
			if(covertChar[e] == '{')
			{
				numberCache ="";
				//error += "hi";
				for(int i=e+1;i < endCreateLevelArray;i++)
				{

					if(Character.isDigit(covertChar[i]))
					{	

						numberCache += Character.toString(covertChar[i]);
					}
					if(covertChar[i] == ',')
					{
						data[levelSlot] = Integer.parseInt(numberCache);
						levelSlot++;
						numberCache = "";
					}

					if(covertChar[i] == '}')
					{
						endCreateLevelArray = i;
					}
				}
			}//end of beggining level data		
		}	
	}

	private String readFile(){
		String aBuffer = "";
		try {
			File myFile = new File("/sdcard/Bobrun/" + filename);
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(
				new InputStreamReader(fIn));
			String aDataRow = "";
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}
			myReader.close();
		} catch (Exception e){

		}
		return aBuffer.toString();
	}
	
	
}
