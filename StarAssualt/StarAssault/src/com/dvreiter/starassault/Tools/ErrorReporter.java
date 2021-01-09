package com.dvreiter.starassault.Tools;
import java.io.*;
import android.os.*;
import java.util.*;
import com.dvreiter.starassault.LevelLoader.*;
import org.flixel.*;

public class ErrorReporter
{
	
	public void ErrorReporter()
	{
			
	}

	public static void logData(String error)
	{
		try
		{
			FlxG.log("New Data through error Reporter" , error);
			String info = readFile("Error" +StarAssaultGame.time + ".txt");
			final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bobrun/ErrorLogs");
			if (!dir.exists())
			{dir.mkdirs();}

				final File myFile = new File(dir, "Error"+ StarAssaultGame.time +  ".txt");

			if (!myFile.exists())
			{myFile.createNewFile();}

			PrintWriter pr = new PrintWriter(myFile);  
			pr.print(info);
			pr.print("\n\n New Data \n");
			pr.print("[" + 	Calendar.getInstance().getTime() + "]\n");
			pr.print(error);
			pr.close();
		}
		catch (Exception e)
		{

		}
	}
	
		public static void reportError(Exception e)
		{
				try
				{
						FlxG.log("New Error through error Reporter" , e.toString());
						StackTraceElement[] stacktrace = e.getStackTrace();
						String filename = stacktrace[1].getFileName();
						String fullClassName = stacktrace[1].getClassName();
						String classname = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
						String methodname = stacktrace[1].getMethodName();
						int linenumber = stacktrace[1].getLineNumber();
						String together = filename + "--\n" + fullClassName + "--" + classname + "--" + methodname + "--" + linenumber + "--\nError:" + e.toString();
						
						
						String info = readFile("Error" + StarAssaultGame.time + ".txt");
						final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bobrun/ErrorLogs");
						if (!dir.exists())
						{dir.mkdirs();}

						final File myFile = new File(dir, "Error"+ StarAssaultGame.time +  ".txt");

						if (!myFile.exists())
						{myFile.createNewFile();}

						PrintWriter pr = new PrintWriter(myFile);  
						pr.print(info);
						pr.print("\n\n New Error \n");
						pr.print("[" + 	Calendar.getInstance().getTime() + "]\n");
						pr.print(together);
						pr.close();
				}
				catch (Exception a)
				{

				}
		}
	

	private static String readFile(String filename)
	{
		String aBuffer = "";
		try
		{
			final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bobrun/ErrorLogs");
			File myFile = new File(dir, filename);
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(
			new InputStreamReader(fIn));
			String aDataRow = "";
			while ((aDataRow = myReader.readLine()) != null)
			{
				aBuffer += aDataRow + "\n";
			}
			myReader.close();
		}
		catch (Exception e)
		{}
		return aBuffer.toString();
	}
}
