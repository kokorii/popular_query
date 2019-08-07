import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class main {

	public static void main(String[] args) throws IOException {
		//declaration variable.. Object variable
		JSONObject jObject = new JSONObject();
		JSONArray MBRcell = new JSONArray();
		
		//file object declaration
		BufferedReader input = new BufferedReader(new FileReader("resource/dong_MBR_WGS84.txt"));
		double LX, LY, UX, UY;
		int index;
		String tlx, tly, tux, tuy, tindex;
		
		//MBR DATA READ AND RESTORE AT ARRAY
		
		while(true){
			String coordData = input.readLine();
			JSONObject data = new JSONObject();
			
			if(coordData==null) break;
			
			tindex = coordData.split(" ")[0];
			index = Integer.parseInt(tindex);
			data.put("index", index);
			
			tlx = coordData.split(" ")[1];
			LX = Double.parseDouble(tlx);
			data.put("LX", LX);
			
			tux = coordData.split(" ")[2];
			UX = Double.parseDouble(tux);
			data.put("UX", UX);
			
			tly = coordData.split(" ")[3];
			LY = Double.parseDouble(tly);
			data.put("LY", LY);
			
			tuy = coordData.split(" ")[4];
			UY = Double.parseDouble(tuy);
			data.put("UY", UY);
			
			MBRcell.add(data);
			
		}
		
		input.close();
		
		jObject.put("list", MBRcell);
		
		//json file created
		FileWriter file = new FileWriter("MBRData_JSON.json");
		file.write(jObject.toJSONString());
		
		file.flush();
		file.close();
		
		System.out.println(jObject);
		
		
		
		

	}

}
