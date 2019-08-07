package IntervalTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

public class IntervalTreeTests {
    @Test
    public void testIntervalTree() {
        {   // Interval tree

            java.util.List<IntervalTree.IntervalData<String>> intervals = new ArrayList<IntervalTree.IntervalData<String>>();
            intervals.add((new IntervalTree.IntervalData<String>((float)127.728836, (float)127.737266, "R0")));
            intervals.add((new IntervalTree.IntervalData<String>((float)127.728576, (float)127.732444, "R1")));
            intervals.add((new IntervalTree.IntervalData<String>((float)127.724266, (float)127.730003, "R2")));
            intervals.add((new IntervalTree.IntervalData<String>((float)127.730697, (float)127.733535, "R3")));
            intervals.add((new IntervalTree.IntervalData<String>((float)127.726303, (float)127.731056, "R4")));
            intervals.add((new IntervalTree.IntervalData<String>((float)127.720588, (float)37.873657, "R5")));
            intervals.add(new IntervalTree.IntervalData<String>((float)127.731452, (float)127.740158, "R6"));
            
            IntervalTree<String> tree = new IntervalTree<String>(intervals);

            IntervalTree.IntervalData<String> query = tree.query((float)127.731452); // Stabbing query
            System.out.println("returned=" + query);

            query = tree.query((float)127.740158); // Stabbing query
            System.out.println("returned=" + query);

        }
    }
}