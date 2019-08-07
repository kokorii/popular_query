public class PPoint
{
    int dimension;
    //HJ 20140927 SAT. Type change float -> double
    double data[];
    double distanz;

    public PPoint()
    {
        dimension = Constants.RTDataNode__dimension;
      //HJ 20140927 SAT. Type change float -> double
        data = new double[dimension];
        distanz = 0.0;
    }
    
    public PPoint(int dimension)
    {
        this.dimension = dimension;
      //HJ 20140927 SAT. Type change float -> double
        data = new double[dimension];
        distanz = 0.0f;
    }
}