/* Modified from Test.java created by Nikos
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TreeCreation {
    TreeCreation (String filename, int numRects, int dimension, int blockLength, int cacheSize) throws IOException {
        this.numRects = numRects;
        this.dimension = dimension;
        this.blockLength = blockLength;
        this.cacheSize = cacheSize;
        
        // initialize tree
        rt = new RTree(filename, blockLength, cacheSize, dimension);

        // insert random data into the tree
        Data d;
        
		
        //////////////////////////////////////////
        //HJ MODIFIED SOURCE CODE 20140912
        try {
        	
			//FileReader input = new FileReader("rivers.txt");
        	final BufferedReader input = new BufferedReader(new FileReader("dataset/dong_MBR_WGS84.txt"));
        	int index=0;
        	String temp;
			String entry;
			double LX, LY, UX, UY;
			double deno = 1;
			//CASE1 : 25000, 1, 190
			//CASE2 : 20000, 1, 224
			//CASE3 : 1, 23405, 4567130 NOT DRAW. ORIGIN DATA
			//CASE4 : 11710, 24000, 4567131
			//while((entry=input.readLine()) != null)
			
			for(int i=0; i <numRects; i++)
			{
				entry = input.readLine();
				
				temp = entry.split(" ")[0];
				index = Integer.parseInt(temp);
				
				d = new Data(dimension, index);
				d.data = new double[dimension*2];
				
				//HJ 20140927 SAT. change data type. float -> double 
				//d.data = new float[dimension*2];
			
				//HJ 20140927 SAT. change data type. float -> double 
				temp = entry.split(" ")[1];
				LX = Double.parseDouble(temp);
				d.data[0] = LX;
				float test = (float)LX;
				System.out.println("test: " + test);
				
				temp = entry.split(" ")[2];
				UX = Double.parseDouble(temp);
				d.data[1] = UX;
				
				
				temp = entry.split(" ")[3];
				LY = Double.parseDouble(temp);
				d.data[2] = LY;
			
				temp = entry.split(" ")[4];
				UY = Double.parseDouble(temp);
				d.data[3] = UY;
				
				
				System.out.println("TreeCreation Lower Bound X = " + d.data[0] + " " + "Upper Bound X = " + d.data[1] + " " + 
												"Lower Bound Y = " + d.data[2] + " " + "Upper Bound Y = " + d.data[3]);
				
				rt.insert(d);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        // Create the Rectangle Display Window
        f = new RectFrame(this);
        f.pack();
        f.show();

    }
    
    TreeCreation (String filename, int cacheSize) {
        //this.numRects = numRects;
        //this.dimension = dimension;
        //this.blockLength = blockLength;
        this.cacheSize = cacheSize;
        
        // initialize tree
        rt = new RTree(filename, cacheSize);

        // Create the Rectangle Display Window
        f = new RectFrame(this);
        f.pack();
        f.show();

    }
    
    public void exit(int exitcode)
    {
        if ((rt != null) && (exitcode == 0))
            rt.delete();
        System.exit(0);
    }

    public RTree rt;
    public RectFrame f;
    //public QueryFrame qf;
    public int displaylevel = 199;
    private int numRects, dimension, blockLength, cacheSize;
}