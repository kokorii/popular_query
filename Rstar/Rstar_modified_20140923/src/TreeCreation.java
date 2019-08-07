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
        	final BufferedReader input = new BufferedReader(new FileReader("dataset/lakes.txt"));
        	int index=0;
        	String temp;
			String entry;
			float deno = 25000;
			//CASE1 : 25000, 1, 190
			//CASE2 : 20000, 1, 224
			//CASE3 : 1, 23405, 4567130 NOT DRAW. ORIGIN DATA
			//CASE4 : 11710, 24000, 4567131
			//while((entry=input.readLine()) != null)
			
			for(int i=0; i <numRects; i++)
			{
				entry = input.readLine();
				//I Y X, Y X
				//0 1 2, 3 4
				//LX UX LY UY
				temp = entry.split(" ")[0];
				index = Integer.parseInt(temp);
				
				d = new Data(dimension, index);
				d.data = new float[dimension*2];
			
				
				temp = entry.split(" ")[2];
				d.data[0] = Float.parseFloat(temp);
				//d.data[0] = (d.data[0]*deno);
				
			
				temp = entry.split(" ")[4];
				d.data[1] = Float.parseFloat(temp);
				//d.data[1] = (d.data[1]*deno);
				
				
				temp = entry.split(" ")[1];
				d.data[2] = Float.parseFloat(temp);
				//d.data[2] = (d.data[2]*deno);
				
			
				temp = entry.split(" ")[3];
				d.data[3] = Float.parseFloat(temp);
				//d.data[3] = (d.data[3]*deno);
			
				
				
				//////////////////////////////////////////////////
				//HJ First MODIFIED SOURCE. CONFIRM 20140915 MON.
				/*
				temp = entry.split(" ")[0];
				index = Integer.parseInt(temp);
				
				d = new Data(dimension, index);
				d.data = new float[dimension*2];
			
				
				temp = entry.split(" ")[1];
				d.data[0] = Float.parseFloat(temp);
				d.data[0] = (int)(d.data[0]/deno);
				
				
				temp = entry.split(" ")[3];
				d.data[1] = Float.parseFloat(temp);
				d.data[1] = (int)(d.data[1]/deno);
				
				
				temp = entry.split(" ")[2];
				d.data[2] = Float.parseFloat(temp);
				d.data[2] = (int)(d.data[2]/deno);
				
			
				temp = entry.split(" ")[4];
				d.data[3] = Float.parseFloat(temp);
				d.data[3] = (int)(d.data[3]/deno);
				*/
				//////////////////////////////////////////////////
				
				
				System.out.println("TreeCreation Lower Bound X = " + d.data[0] + " " + "Upper Bound X = " + d.data[1] + " " + "Lower Bound Y = " + d.data[2] + " " + "Upper Bound Y = " + d.data[3]);
				
				rt.insert(d);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        //////////////////////////////////////////
        //HJ ORIGIN SOURCE CODE 20140912 
	        /*
	        for (int i=0; i<numRects; i++)
	        {
	            // create a new Data with dim=dimension
	            d = new Data(dimension, i);
	            // create a new rectangle
	            rectangle r = new rectangle(i);
	            // copy the rectangle's coords into d's data
	            d.data = new float[dimension*2];
	            d.data[0] = (float)r.LX;
	            d.data[1] = (float)r.UX;
	            d.data[2] = (float)r.LY;
	            d.data[3] = (float)r.UY;
	            //d.print();
	            rt.insert(d);
	        }
	        */
        //////////////////////////////////////////
        
        // Create the Query Result Window
        //qf = new QueryFrame(rt);
        //qf.show();
        //qf.move(400, 0);

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