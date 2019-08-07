class BranchList implements Sortable
{
    int entry_number;
    double mindist;
    double minmaxdist;
    boolean section;

	private double compute_erg(Sortable s, int sortCriterion)
    {
    	double erg = (float)0.0;
        switch(sortCriterion)
        {
            case Constants.SORT_MINDIST:
                erg = this.mindist - ((BranchList)s).mindist;
                break;
        }
        return erg;
    }
		    
    public boolean lessThan(Sortable s, int sortCriterion)
    {
        double erg = compute_erg(s, sortCriterion);
        if (erg < (float)0.0)    return true;
            return false;
    }
                
    public boolean greaterThan(Sortable s, int sortCriterion)
    {
        double erg = compute_erg(s, sortCriterion);
        if (erg > 0.0)
            return true;
        return false;
    }
}