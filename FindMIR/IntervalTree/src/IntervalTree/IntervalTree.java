package IntervalTree;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * An interval tree is an ordered tree data structure to hold intervals.
 * Specifically, it allows one to efficiently find all intervals that overlap
 * with any given interval or point.
 *
 * http://en.wikipedia.org/wiki/Interval_tree
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class IntervalTree<O extends Object> {

    private Interval<O> root = null;

    private static final Comparator<IntervalData<?>> YlComparator = new Comparator<IntervalData<?>>() {

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(IntervalData<?> arg0, IntervalData<?> arg1) {
        	// Compare Yl first
            if (arg0.Yl < arg1.Yl)
                return -1;
            if (arg1.Yl < arg0.Yl)
                return 1;
            return 0;
        }
    };

    private static final Comparator<IntervalData<?>> YhComparator = new Comparator<IntervalData<?>>() {

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(IntervalData<?> arg0, IntervalData<?> arg1) {
        	// Compare Yh first
            if (arg0.Yh < arg1.Yh)
                return -1;
            if (arg1.Yh < arg0.Yh)
            	return 1;
            return 0;
        }
    };

    /**
     * Create interval tree from list of IntervalData objects;
     * 
     * @param intervals
     *            is a list of IntervalData objects
     */
    public IntervalTree(List<IntervalData<O>> intervals) {
        if (intervals.size() <= 0)
            return;

        root = createFromList(intervals);
    }

    protected static final <O extends Object> Interval<O> createFromList(List<IntervalData<O>> intervals) {
        Interval<O> newInterval = new Interval<O>();
        if (intervals.size()==1) {
        	IntervalData<O> middle = intervals.get(0);
        	newInterval.SV = ((middle.Yl + middle.Yh) / 2);
        	newInterval.add(middle);
        } else {
	        int half = intervals.size() / 2;
	        IntervalData<O> middle = intervals.get(half);
	        newInterval.SV = ((middle.Yl + middle.Yh) / 2);
	        List<IntervalData<O>> leftIntervals = new ArrayList<IntervalData<O>>();
	        List<IntervalData<O>> rightIntervals = new ArrayList<IntervalData<O>>();
	        for (IntervalData<O> interval : intervals) {
	        	if (interval.Yh < newInterval.SV) {
	                leftIntervals.add(interval);
	            } else if (interval.Yl > newInterval.SV) {
	                rightIntervals.add(interval);
	            } else {
	                newInterval.add(interval);
	            }
	        }
	        if (leftIntervals.size() > 0)
	            newInterval.left = createFromList(leftIntervals);
	        if (rightIntervals.size() > 0)
	            newInterval.right = createFromList(rightIntervals);
        }
        return newInterval;
    }

    /**
     * Stabbing query
     * 
     * @param w
     *            to query for.
     * @return data at w.
     */
    public IntervalData<O> query(float w) {
        return root.query(w);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(IntervalTreePrinter.getString(this));
        return builder.toString();
    }

    protected static class IntervalTreePrinter {

        public static <O extends Object> String getString(IntervalTree<O> tree) {
            if (tree.root == null)
                return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <O extends Object> String getString(Interval<O> interval, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "戌式式 " : "戍式式 ") + interval.toString() + "\n");
            List<Interval<O>> children = new ArrayList<Interval<O>>();
            if (interval.left != null)
                children.add(interval.left);
            if (interval.right != null)
                children.add(interval.right);
            if (children.size() > 0) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString(children.get(i), prefix + (isTail ? "    " : "弛   "), false));
                }
                if (children.size() > 0) {
                    builder.append(getString(children.get(children.size() - 1), prefix + (isTail ? "    " : "弛   "),
                            true));
                }
            }

            return builder.toString();
        }
    }

    public static final class Interval<O> {

        private float SV = Float.MIN_VALUE;
        private Interval<O> left = null;
        private Interval<O> right = null;
        private List<IntervalData<O>> overlap = new ArrayList<IntervalData<O>>(); // YlComparator

        private void add(IntervalData<O> data) {
        	overlap.add(data);
        	Collections.sort(overlap,YlComparator);
        }

        /**
         * Stabbing query
         * 
         * @param w
         *            to query for.
         * @return data at w.
         */
        public IntervalData<O> query(float w) {
            IntervalData<O> results = null;
            if (w < SV) {
                // overlap is sorted by Yl point
                for (IntervalData<O> data : overlap) {
                    if (data.Yl > w)
                        break;

                    IntervalData<O> temp = data.query(w);
                    if (results == null && temp != null)
                        results = temp;
                    else if (temp != null)
                        results.combined(temp);
                }
            } else if (w >= SV) {
                // overlapYh is sorted by Yh point
                List<IntervalData<O>> overlapYh = new ArrayList<IntervalData<O>>();
                overlapYh.addAll(overlap);
                Collections.sort(overlapYh,YhComparator);
                for (IntervalData<O> data : overlapYh) {
                    if (data.Yh < w)
                        break;

                    IntervalData<O> temp = data.query(w);
                    if (results == null && temp != null)
                        results = temp;
                    else if (temp != null)
                        results.combined(temp);
                }
            }
            if (w < SV) {
                if (left != null) {
                    IntervalData<O> temp = left.query(w);
                    if (results == null && temp != null)
                        results = temp;
                    else if (temp != null)
                        results.combined(temp);
                }
            } else if (w >= SV) {
                if (right != null) {
                    IntervalData<O> temp = right.query(w);
                    if (results == null && temp != null)
                        results = temp;
                    else if (temp != null)
                        results.combined(temp);
                }
            }
            return results;
        }

       

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("SV=").append(SV);
            builder.append(" Set=").append(overlap);
            return builder.toString();
        }
    }

    /**
     * Data structure representing an interval.
     */
    public static final class IntervalData<O> implements Comparable<IntervalData<O>> {

        private float Yl = Float.MIN_VALUE;
        private float Yh = Float.MAX_VALUE;
        private Set<O> set = new TreeSet<O>(); // Sorted

        /**
         * Interval data using O as it's unique identifier
         * 
         * @param object
         *            Object which defines the interval data
         */
        public IntervalData(float w, O object) {
            this.Yl = w;
            this.Yh = w;
            this.set.add(object);
        }

        /**
         * Interval data using O as it's unique identifier
         * 
         * @param object
         *            Object which defines the interval data
         */
        public IntervalData(float Yl, float Yh, O object) {
            this.Yl = Yl;
            this.Yh = Yh;
            this.set.add(object);
        }

        /**
         * Interval data list which should all be unique
         * 
         * @param list
         *            of interval data objects
         */
        public IntervalData(float Yl, float Yh, Set<O> set) {
            this.Yl = Yl;
            this.Yh = Yh;
            this.set = set;

            // Make sure they are unique
            Iterator<O> iter = set.iterator();
            while (iter.hasNext()) {
                O obj1 = iter.next();
                O obj2 = null;
                if (iter.hasNext())
                    obj2 = iter.next();
                if (obj1.equals(obj2))
                    throw new InvalidParameterException("Each interval data in the list must be unique.");
            }
        }

        /**
         * Get the Yl of this interval
         * 
         * @return Yl of interval
         */
        public float getYl() {
            return Yl;
        }

        /**
         * Get the Yh of this interval
         * 
         * @return Yh of interval
         */
        public float getYh() {
            return Yh;
        }

        /**
         * Get the data set in this interval
         * 
         * @return Unmodifiable collection of data objects
         */
        public Collection<O> getData() {
            return Collections.unmodifiableCollection(this.set);
        }

        /**
         * Clear the indices.
         */
        public void clear() {
            this.Yl = Float.MIN_VALUE;
            this.Yh = Float.MAX_VALUE;
            this.set.clear();
        }

        /**
         * Combined this IntervalData with data.
         * 
         * @param data
         *            to combined with.
         * @return Data which represents the combination.
         */
        public IntervalData<O> combined(IntervalData<O> data) {
            if (data.Yl < this.Yl)
                this.Yl = data.Yl;
            if (data.Yh > this.Yh)
                this.Yh = data.Yh;
            this.set.addAll(data.set);
            return this;
        }

        /**
         * Deep copy of data.
         * 
         * @return deep copy.
         */
        public IntervalData<O> copy() {
            Set<O> listCopy = new TreeSet<O>();
            listCopy.addAll(set);
            return new IntervalData<O>(Yl, Yh, listCopy);
        }

        /**
         * Query inside this data object.
         * 
         * @param Yl
         *            of range to query for.
         * @param Yh
         *            of range to query for.
         * @return Data queried for or NULL if it doesn't match the query.
         */
        public IntervalData<O> query(float w) {
            if (w < this.Yl || w > this.Yh) {
                // Ignore
            } else {
                return copy();
            }
            return null;
        }

        /**
         * Query inside this data object.
         * 
         * @param Yl
         *            of range to query for.
         * @param Yh
         *            of range to query for.
         * @return Data queried for or NULL if it doesn't match the query.
         */
        public IntervalData<O> query(float Yl, float Yh) {
            if (Yh < this.Yl || Yl > this.Yh) {
                // Ignore
            } else {
                return copy();
            }
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return 31 * ((int)(this.Yl + this.Yh)) + this.set.size();
        }
 
        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof IntervalData))
                return false;
            IntervalData<O> data = (IntervalData<O>) obj;
            if (this.Yl == data.Yl && this.Yh == data.Yh) {
                if (this.set.size() != data.set.size())
                    return false;
                for (O o : set) {
                    if (!data.set.contains(o))
                        return false;
                }
                return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(IntervalData<O> d) {
            if (this.Yh < d.Yh)
                return -1;
            if (d.Yh < this.Yh)
                return 1;
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(Yl).append("->").append(Yh);
            builder.append(" set=").append(set);
            return builder.toString();
        }
    }
}