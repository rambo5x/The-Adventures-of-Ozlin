package com.dvreiter.starassault.Tools;
import java.io.*;
import android.os.*;

public class Txtsaver
{
		private String fileName;
		private String error;
		private String text = "";

		private String directory;

		private ErrorReporter Error;
		public Txtsaver()
		{
				Error = new ErrorReporter();
		}
		
		public void print(String newText)
		{
				this.text += newText;
		}
		public void printl(String newText)
		{
				this.text += newText + "\n";
		}
		
		public void print(int newText)
		{
				this.text += newText;
		}
		public void printl(int newText)
		{
				this.text += newText + "\n";
		}
		public void print(boolean newText)
		{
				this.text += newText;
		}
		public void printl(boolean newText)
		{
				this.text += newText + "\n";
		}
		public void setDir(String dir)
		{
				this.directory = dir;
		}
		public void setFilename(String Name)
		{
				this.fileName = Name;
		}
		
		public void textClear()
		{
				this.text = "";
		}
		
		public String getText()
		{
				return this.text;
		}
		
		public boolean save()
		{
				if(fileName == null)
				{error = "Need a fileName";
				}else
				{try{
							final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bobrun"+ directory);

							if (!dir.exists())
							dir.mkdirs(); 

							final File myFile = new File(dir, fileName);


							if (!myFile.exists()) 
							myFile.createNewFile();

							PrintWriter pr = new PrintWriter(myFile);    
								
							pr.print(this.text);
							
							pr.close();
							
							}catch(Exception e){
									Error.logData(directory);
									Error.reportError(e);
									return false;
							}
					}
					return true;
				}}
