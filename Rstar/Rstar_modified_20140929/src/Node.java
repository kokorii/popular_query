////////////////////////////////////////////////////////////////////////
// Node
////////////////////////////////////////////////////////////////////////

/**
* Node is a set of functions implemented in RTDirNode and RTDataNode
*/

import java.io.IOException;

public interface Node
{
  public abstract int get_num_of_data();  // returns number of data entries
                                        // behind that node
  public abstract Data get(int i);      // returns the i-th object in the 
                                        // tree lying behind that node
    
  public abstract void read_from_buffer(byte buffer[]) throws IOException; // reads data from buffer
  public abstract void write_to_buffer(byte buffer[]) throws IOException; // writes data to buffer

  public abstract boolean is_data_node();			// returns TRUE, if "this" is RTDataNode 

  //HJ 20140927 SAT. Type change float -> double
  public abstract double[] get_mbr();       // returns mbr enclosing whole page
  public abstract void print();                // prints rectangles
  
  /*
  void neighbours(LinList *sl,   
		    float eps,           // berechnet fuer alle Datas in
		    Result *rs,          // sl die Nachbarn, die in der eps-
		    norm_ptr norm);  // Umgebung liegen
	*/
  //HJ 20140927 SAT. Type change float -> double
  public abstract void NearestNeighborSearch(PPoint point, PPoint nearest, double nearest_distanz);

  void NearestNeighborSearch(PPoint point, SortedLinList res, float nearest_distanz);

  void point_query(PPoint p, SortedLinList res);
  
  void rangeQuery(double[] mbr, SortedLinList res);

  void rangeQuery(PPoint center, float radius, SortedLinList res); 
  
  void ringQuery(PPoint center, float radius1, float radius2, SortedLinList res);

  void range_nnQuery(double[] mbr, SortedLinList res, 
				PPoint center, double distanz,
				PPoint nearest, boolean success);

  public abstract void overlapping(double[] p, int nodes_t[]);
  public abstract void nodes(int nodes_a[]);

  //void writeinfo(FILE *f);
	
  public abstract int insert(Data d, RTNode sn[]); 
                                        // inserts d recursivly, if there
                                        // occurs a split, FALSE will be
                                        // returned and in sn a 
                                        // pointer to the new node
	//HJ 20140927 SAT. Type change float -> double
  public abstract void region(double[] mbr);
                                        // prints all entries sectioning
                                        // mbr
//HJ 20140927 SAT. Type change float -> double
  public abstract void point_query(double[] p); 
                                        // prints all entries equal to p
                                        
  public abstract void constraints_query(rectangle rect, double distance[], short direction, short MBRtopo, short topology, SortedLinList res);
  	                                      
  public abstract void delete();
public abstract void NearestNeighborSearch(PPoint queryPoint,
		SortedLinList res, double nearest_distanz);
}