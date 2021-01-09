package com.dvreiter.starassault.LevelEditor;
import org.json.*;
import com.dvreiter.starassault.Tools.*;

public class JsonParser
{
	public JsonParser(){
		
	}
	public void ParseData(){
		try{
		String str = "[{\"name\":\"name1\",\"url\":\"url1\"},{\"name\":\"name2\",\"url\":\"url2\"}]";

		JSONArray jsonarray = new JSONArray(str);


		for(int i=0; i<jsonarray.length(); i++){
			JSONObject obj = jsonarray.getJSONObject(i);

			String name = obj.getString("name");
			String url = obj.getString("url");

			ErrorReporter.logData(name);
			ErrorReporter.logData(url);
		}   
	}catch(Exception e){ErrorReporter.reportError(e);}
	}
	
}
