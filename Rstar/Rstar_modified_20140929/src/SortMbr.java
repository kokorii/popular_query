/* class SortMbr is used to store mbrs to be sorted. Used in RTNode.split()
 * and RTDataNode.insert().
 */

class SortMbr implements Sortable
{
    int dimension; 	// the dimension of the MBR according to which the sorting is done
    //HJ 20140927 Type change float -> double
    double[] mbr; 	// the data of the MBR
    double center[];	// the MBR's centroid
    int index;		// the MBR's index inside an array of mbrs
    
    //HJ 209140927 Return type change float -> double
    private double compute_erg(Sortable s, int sortCriterion)
    {
    	//HJ 20140927 Type change float -> double
        double erg = 0.0;
        switch(sortCriterion)
        {
            case Constants.SORT_LOWER_MBR:
                erg = this.mbr[2*dimension] - ((SortMbr)s).mbr[2*dimension];
                break;
            case Constants.SORT_UPPER_MBR:
                erg = this.mbr[2*dimension+1] - ((SortMbr)s).mbr[2*dimension+1];
                break;
            case Constants.SORT_CENTER_MBR:
                //HJ 20140927 Type change float -> double
            	double d, e1, e2;
                e1 = e2 = 0.0;
                for (int i = 0; i < dimension; i++)
                {
                    d = ((this.mbr[2*i] + this.mbr[2*i+1]) / (float)2.0) - this.center[i];
                    e1 += d*d;
                    d = ((((SortMbr)s).mbr[2*i] + ((SortMbr)s).mbr[2*i+1]) / (float)2.0) - ((SortMbr)s).center[i];
                    e2 += d*d;
                }
                
                erg = e1 - e2;
                break;
        }
        return erg;
    }
    
    //HJ 20140927 SAT. Type change float -> double
    public boolean lessThan(Sortable s, int sortCriterion)
    {
        double erg = compute_erg(s, sortCriterion);
        if (erg < (float)0.0)
            return true;
        return false;
    }

  //HJ 20140927 SAT. Type change float -> double        
    public boolean greaterThan(Sortable s, int sortCriterion)
    {
        double erg = compute_erg(s, sortCriterion);
        if (erg > (float)0.0)
            return true;
        return false;
    }
}