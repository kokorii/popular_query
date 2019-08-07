/* Modified by Josephine Wong 24 November 1997 to incorporate User Interface.
   controller is now cast to type TreeCreation, and a rectangle is only drawn
   if its level is not less than the selected display level.
*/

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class RectArea extends Canvas {
	SortedLinList queryres=null;
    Rectangle currentRect=null;
    Object controller;
    String outp = "";
    
    // for displaying queries area
    boolean displayed = false;
    float[] range = null;
    PPoint p = null;
    float radius1 = 0.0f;
    float radius2 = 0.0f;

    public RectArea(Object controller) {
        super();
        this.controller = controller;
        //currentRect = new Rectangle(22, 32, 24, 43);
    }

    public void drawNode(RTNode node, Graphics g) throws IOException
    {		
    	if (((TreeCreation)controller).displaylevel == 200) return; // display only query results
        Dimension d = getSize();

        Color prev = g.getColor();
        
        switch(node.level)
        {
            case 0:
                g.setColor(Color.black);
                break;
            case 1:
                g.setColor(Color.blue);
                break;
            case 2:
                g.setColor(Color.green);
                break;
            case 3:
                g.setColor(Color.red);
                break;
            case 4:
                g.setColor(Color.magenta);
                break;
            case 5:
                g.setColor(Color.lightGray);
                break;
        }

        for (int i=0; i<node.get_num(); i++)
        {
        	
        	if (node instanceof RTDataNode)
            {
                RTDataNode datanode = (RTDataNode)node;

                
                //HJ modified 2D RECTANGLE
                Rectangle2D.Float r = new Rectangle2D.Float();
                r.setFrame(datanode.data[i].get_mbr()[0],
                                            datanode.data[i].get_mbr()[2],
                                            datanode.data[i].get_mbr()[1] - datanode.data[i].get_mbr()[0],
                                            datanode.data[i].get_mbr()[3] - datanode.data[i].get_mbr()[2]);
                outp+="Rect " + i +": " + r.x + " " + (r.x + r.width) + " "+ r.y + " "+ (r.y + r.height) + " "+"\n";
                Rectangle2D.Float box = getDrawableRect(r, d);
                
                if ((((TreeCreation)controller).displaylevel==199) || (node.level == ((TreeCreation)controller).displaylevel))
                {
                	//////HJ MODIFIED SOURCE CODE. 20140921. SUN
                	Graphics2D g2d = (Graphics2D)g;
                	g2d.draw(box);
                	
            		////////////////////////////////////////////////////////////
            		//HJ ORIGIN SOURCE CODE. 20140921. SUN
            		//g.drawRect(box.x, box.y, box.width - 1, box.height - 1);
            		//////////////////////////////////////////////////////////
     
                }
                	
            }
            else
            {
                RTDirNode dirnode = (RTDirNode)node;

                Node n = (Node)dirnode.entries[i].get_son();

                float[] mbr = n.get_mbr();

                //HJ modified 2D RECTANGLE
                Rectangle2D.Float r = new Rectangle.Float();
                r.setFrame(mbr[0], mbr[2], mbr[1] - mbr[0],mbr[3] - mbr[2]);

                outp+="Dirnode level " + node.level + " entry " + i + ": " + r.x + " " + (r.x + r.width) + " "+ r.y + " "+ (r.y + r.height) + " " + "\n";
                Rectangle2D.Float box = getDrawableRect(r, d);
                drawNode(dirnode.entries[i].get_son(), g);

                if ((((TreeCreation)controller).displaylevel==199) || (node.level == ((TreeCreation)controller).displaylevel))
                {
                	Graphics2D g2d = (Graphics2D)g;
                	g2d.draw(box);
                	//////////////////////////////////////////////////////////
                	//HJ ORIGIN SOURCE CODE. 20140921. SUN
                	//g.drawRect(box.x, box.y, box.width - 1, box.height - 1);
                	////////////////////////////////////////////////////////////
                }
            }
        	
			///////////////////////////////////////////////////////////////
			//HJ ORIGIN SOURCE CODE . 20140921. SUN
			/*
	            if (node instanceof RTDataNode)
	            {
	                RTDataNode datanode = (RTDataNode)node;
	
	                
	                Rectangle r = new Rectangle((int)datanode.data[i].get_mbr()[0],
	                                            (int)datanode.data[i].get_mbr()[2],
	                                            (int)datanode.data[i].get_mbr()[1] - (int)datanode.data[i].get_mbr()[0],
	                                            (int)datanode.data[i].get_mbr()[3] - (int)datanode.data[i].get_mbr()[2]);
	                outp+="Rect " + i +": " + r.x + " " + (r.x + r.width) + " "+ r.y + " "+ (r.y + r.height) + " "+"\n";
	                Rectangle box = getDrawableRect(r, d);
	                if ((((TreeCreation)controller).displaylevel==199) ||
	                	   (node.level == ((TreeCreation)controller).displaylevel))
	                			g.drawRect(box.x, box.y, box.width - 1, box.height - 1);
	            }
	            else
	            {
	                RTDirNode dirnode = (RTDirNode)node;
	
	                Node n = (Node)dirnode.entries[i].get_son();
	
	                float[] mbr = n.get_mbr();
	
	                Rectangle r = new Rectangle((int)mbr[0],
	                                            (int)mbr[2],
	                                            (int)mbr[1] - (int)mbr[0],
	                                            (int)mbr[3] - (int)mbr[2]);
	
	                outp+="Dirnode level " + node.level + " entry " + i + ": " + r.x + " " + (r.x + r.width) + " "+ r.y + " "+ (r.y + r.height) + " " + "\n";
	                Rectangle box = getDrawableRect(r, d);
	
	                drawNode(dirnode.entries[i].get_son(), g);
	
	                if ((((TreeCreation)controller).displaylevel==199) ||
	                	   (node.level == ((TreeCreation)controller).displaylevel))
	                			g.drawRect(box.x, box.y, box.width - 1, box.height - 1);
	            }
            */
          ////////////////////////////////////////////////////////////////////////////////
        }

        g.setColor(prev);
    }

    public void drawRange(float[] mbr)
    {
        this.p = null;
        this.range = mbr;
        repaint();
    }
    
    public void drawRing(PPoint p, float r1, float r2)
    {
        this.range = null;
        this.p = p;
        this.radius1 = r1;
        this.radius2 = r2;
        repaint();
    }
    
    public void paint(Graphics g) {
//        RTree rt = ((Test)controller).rt;

        RTree rt = ((TreeCreation)controller).rt;
        RTNode node = rt.root_ptr;
        outp = "";
        try {
			drawNode(node, g);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if ((((TreeCreation)controller).displaylevel == 200) && (queryres!=null))
        {
            Dimension d = getSize();
            
	        for (Object obj = queryres.get_first(); obj != null; obj = queryres.get_next())
	        {
	        	//HJ MODIFIED SORUCE CODE. 2D Rectangle
	        	float mbr[] = ((Data)obj).data;
	        	Rectangle2D.Float r = new Rectangle2D.Float();
	        	r.setFrame(mbr[0], mbr[2], mbr[1] - mbr[0], mbr[3] - mbr[2]);
            	Rectangle2D.Float box = getDrawableRect(r, d);
            	
            	Graphics2D g2d = (Graphics2D)g;
            	g2d.draw(box);
            	
            	
            	//HJ ORIGINE SORUCE CODE. 20140921. SUN
            	//g.drawRect(box.x, box.y, box.width - 1, box.height - 1);
            }
        }
        
        if (range!=null)
        {
            Dimension d = getSize();
            
            Color prev = g.getColor();
            g.setColor(Color.red);
            
            
            //HJ MODIFIED SOURCE CODE . 2D Rectangle
            
            Rectangle2D.Float r = new Rectangle2D.Float();
            r.setFrame(range[0], range[2], range[1] - range[0], range[3] - range[2]);
            Rectangle2D.Float box = getDrawableRect(r, d);
            
            Graphics2D g2d = (Graphics2D)g;
            g2d.draw(box);
            
            //HJ ORIGIN SOURCE CODE. 20140921. SUN
            //g.drawRect(box.x, box.y, box.width - 1, box.height - 1);
            
            g.setColor(prev);
        }
        
        if (p!=null)
        {
        	//HJ MODIFIED 2D GRAPHICS
        	Graphics2D g2d = (Graphics2D)g;
            Color prev = g2d.getColor();
            g2d.setColor(Color.red);
            
            g2d.drawOval((int)p.data[0]*3-(int)radius1*3, (int)p.data[1]*3-(int)radius1*3, (int)radius1*6, (int)radius1*6);
            g2d.drawOval((int)p.data[0]*3-(int)radius2*3, (int)p.data[1]*3-(int)radius2*3, (int)radius2*6, (int)radius2*6);
            g2d.setColor(prev);
            
            
            ////////////////////////////////////////////////////////////////
            //HJ ORIGIN SOURCE CODE 20140921. SUN
            /*
            g.drawOval((int)p.data[0]*3-(int)radius1*3, (int)p.data[1]*3-(int)radius1*3, (int)radius1*6, (int)radius1*6);
            g.drawOval((int)p.data[0]*3-(int)radius2*3, (int)p.data[1]*3-(int)radius2*3, (int)radius2*6, (int)radius2*6);
            g.setColor(prev);
            */
            /////////////////////////////////////////////////////////////////
        }
        
        if (!displayed)
        {
            displayinwin(outp,0,400);
            
            ///HJ ADD SOURCE 20140921. SUN. FILE OUTSTREAM
            ////
        	File file = new File("dataset/coordsData.txt");
    		OutputStream out;
            System.out.println(outp);
            try {
            	out = new FileOutputStream(file);
				out.write(outp.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ////
            displayed = true;
        }
      /*
      //If currentRect exists, paint a rectangle on top.
          if (currentRect != null) {
              Rectangle box = getDrawableRect(currentRect, d);
              //controller.rectChanged(box);

              //Draw the box outline.
              g.drawRect(box.x, box.y, box.width - 1, box.height - 1);
          }*/
    }

    Rectangle2D.Float getDrawableRect(Rectangle2D.Float originalRect, Dimension drawingArea) {
    	
        float x = originalRect.x;
        float y = originalRect.y;
        float width = originalRect.width;
        float height = originalRect.height;
        float temp = 250f;
        
        width *=temp;
        height *=temp;
        
        Rectangle2D.Float newRect = new Rectangle2D.Float();
        newRect.setFrame(x,y,width,height);
        
        return newRect;
        
        ////////////////////////////////////////////////////
        //HJ ORIGIN SOURCE 20140921 SUN
        //Make sure rectangle width and height are positive.
        /*
        int x = originalRect.x;
        int y = originalRect.y;
        int width = originalRect.width;
        int height = originalRect.height;
        
        if (width < 0) {
            width = 0 - width;
            x = x - width + 1;
            if (x < 0) {
                width += x;
                x = 0;
            }
        }
        if (height < 0) {
            height = 0 - height;
            y = y - height + 1;
            if (y < 0) {
                height += y;
                y = 0;
            }
        }

        //The rectangle shouldn't extend past the drawing area.
        if ((x + width) > drawingArea.width) {
            width = drawingArea.width - x;
        }
        if ((y + height) > drawingArea.height) {
            height = drawingArea.height - y;
        }
         
        return new Rectangle(x, y, width, height);
        */
        ////////////////////////////////////////////////
    }

    public Dimension getPreferredSize()
    {
        return getMinimumSize();
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(300,350);
    }

    /*
    public void drawRect(rectangle r)
    {
        currentRect = r.toRectangle();
        repaint();
    }*/

    public void displayinwin(String s,int x,int y)
    {
        //String qs="",a="";
        //int fl=0;

        Frame fq=new Frame("Rtree structure:");
        fq.resize(300,250);
        TextArea ta=new TextArea(s);
        fq.add("Center",ta);
        fq.show();
        fq.move(x,y);
    }
}