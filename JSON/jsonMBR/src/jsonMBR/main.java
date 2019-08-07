package jsonMBR;

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
		BufferedReader input = new BufferedReader(new FileReader("resource/R_star_mbr.txt"));
		double LX, LY, UX, UY;
		int level, entry;
		String tlx, tly, tux, tuy, tentry, tlevel;
		
		level = entry = 0;
		LX = LY = UX = UY = 0;
		//MBR DATA READ AND RESTORE AT ARRAY
		
		while(true){
		//for(int i=0; i<10; i++){
			String coordData = input.readLine();
			JSONObject data = new JSONObject();
			Node newNode = new Node();
			
			if(coordData==null) break;
			String[] dataform = coordData.split(" ");
			
			if(dataform[0].equals("level")){
				tlevel = coordData.split(" ")[1];
				level = Integer.parseInt(tlevel);
				
				
				if(dataform[2].equals("entry")){
					tentry = coordData.split(" ")[3];
					entry = Integer.parseInt(tentry);
				}
				
				tlx = coordData.split(" ")[4];
				LX = Double.parseDouble(tlx);
				
				tux = coordData.split(" ")[5];
				UX = Double.parseDouble(tux);
				
				tly = coordData.split(" ")[6];
				LY = Double.parseDouble(tly);
				
				tuy = coordData.split(" ")[7];
				UY = Double.parseDouble(tuy);
			}
			else if(dataform[0].equals("Rect")){
				level = 0;
				
				tentry = coordData.split(" ")[1];
				entry = Integer.parseInt(tentry);
				
				tlx = coordData.split(" ")[2];
				LX = Double.parseDouble(tlx);
				
				tux = coordData.split(" ")[3];
				UX = Double.parseDouble(tux);
				
				tly = coordData.split(" ")[4];
				LY = Double.parseDouble(tly);
				
				tuy = coordData.split(" ")[5];
				UY = Double.parseDouble(tuy);
			}
			else{
				//no action
			}
			newNode.setLevel(level);
			newNode.setEntry(entry);
			newNode.setLx(LX);
			newNode.setLy(LY);
			newNode.setUx(UX);
			newNode.setUy(UY);
			
			data.put("level", newNode.getLevel());
			data.put("entry", newNode.getEntry());
			data.put("LX", newNode.getLx());
			data.put("LY", newNode.getLy());
			data.put("UX", newNode.getUx());
			data.put("UY", newNode.getUy());
			
			MBRcell.add(data);
		}
		
		input.close();
		
		jObject.put("list", MBRcell);
		
		//json file created
		FileWriter file = new FileWriter("resource/TREE_MBR_json.json");
		file.write(jObject.toJSONString());
		
		file.flush();
		file.close();
		
		System.out.println(jObject);
	}

}
