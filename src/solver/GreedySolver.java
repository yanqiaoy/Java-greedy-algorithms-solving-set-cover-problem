package solver;

import java.util.SortedSet;
import java.util.TreeSet;
import model.SCPModel;
import util.ElementSet;

/**
 * This is the main method that all solvers inherit from. It is important to
 * note how this solver calls nextBestSet() polymorphically! Subclasses should
 * *not* override solver(), they need only override nextBestSet().
 *
 * We'll assume models are well-defined here and do not specify Exceptions to
 * throw.
 *
 * @author ssanner@mie.utoronto.ca
 *
 */
public abstract class GreedySolver {

    protected String _name;			  // name of algorithm type
    protected double _alpha;          // minimum required coverage level in range [0,1]
    protected SCPModel _model;        // the SCP model we're currently operating on
    protected double _objFn;          // objective function value (*total cost sum* of all sets used)
    protected double _coverage;       // actual coverage fraction achieved
    protected long _compTime;         // computation time (ms)
    protected TreeSet<ElementSet> _solnSets;
    protected ElementSet _bestSet;
    protected boolean _solved;
    protected SortedSet<Integer> usedEle;
    protected SortedSet<ElementSet> iter;

    // Basic setter (only one needed)
    public void setMinCoverage(double alpha) {
        _alpha = alpha;
    }

    public void setModel(SCPModel model) {
        _model = model;
    }

    // Basic getters
    public double getMinCoverage() {
        return _alpha;
    }

    public double getObjFn() {
        return _objFn;
    }

    public double getCoverage() {
        return _coverage;
    }

    public long getCompTime() {
        return _compTime;
    }

    public String getName() {
        return _name;
    }

    public GreedySolver() {
        _solnSets = new TreeSet<ElementSet>();
        _name = "undefined";//

    }

    /**
     * Run the simple greedy heuristic -- add the next best set until either (1)
     * The coverage level is reached, or (2) There is no set that can increase
     * the coverage.
     */
    public boolean isSolved() {
        return _solved;
    }

    public void solve() {

        // Reset the solver
        reset();
        iter = _model.getElementsetCopy();
        usedEle = new TreeSet();
        boolean allCovered = false;

		// TODO: Preliminary initializations
        // Begin the greedy selection loop
        long start = System.currentTimeMillis();
        System.out.println("Running '" + getName() + "'...");

		// TODO: Fill in the main loop, pseudocode given below
        //
        // while (set elements remaining not covered > max num that can be left uncovered
        //        AND all sets have not been selected)
        //
        //      Call nextBestSet() to get the next best ElementSet to add (if there is one)
        // 		Update solution and local members
        while ((1 - _coverage > 1 - _alpha) && (allCovered == false)) {

            _bestSet = nextBestSet();
            _solnSets.add(_bestSet); //add the bestset to the solution set

            usedEle.addAll(_bestSet.getAllElements());//add all elements in the nextbest to the usedele
            _objFn += _bestSet.getCost();//change the cost
            allCovered = _solnSets.size() == _model.getNumElementSets(); //check if all have been selected
            _coverage = (double) usedEle.size() / _model.getNumElements();//check coverage
            iter.remove(_bestSet);//get rid of the bestset for the next loop
            System.out.println("- Selected: " + _bestSet);
        }

		// Record final set coverage, compTime and print warning if applicable
        //_coverage = -1d; // TODO: Correct this, should be coverage of solution found
        _compTime = System.currentTimeMillis() - start;
        if (_coverage < _alpha) {
            System.out.format("\nWARNING: Impossible to reach %.2f%% coverage level.\n", 100 * _alpha);
        }
        System.out.println("Done.");
    }

    /**
     * Returns the next best set to add to the solution according to the
     * heuristic being used.
     *
     * NOTE 1: This is the **only** method to be implemented in child classes.
     *
     * NOTE 2: If no set can improve the solution, returns null to allow the
     * greedy algorithm to terminate.
     *
     * NOTE 3: This references an ElementSet class which is a tuple of (Set ID,
     * Cost, Integer elements to cover) which you must define.
     *
     * @return
     */
    public abstract ElementSet nextBestSet(); // Abstract b/c it must be implemented by subclasses

    /**
     * Print the solution
     *
     */
    public void print() {
        System.out.println("\n'" + getName() + "' results:");
        System.out.format("'" + getName() + "'   Time to solve: %dms\n", _compTime);
        System.out.format("'" + getName() + "'   Objective function value: %.2f\n", _objFn);
        System.out.format("'" + getName() + "'   Coverage level: %.2f%% (%.2f%% minimum)\n", 100 * _coverage, 100 * _alpha);
        System.out.format("'" + getName() + "'   Number of sets selected: %d\n", _solnSets.size());
        System.out.format("'" + getName() + "'   Sets selected: ");
        for (ElementSet s : _solnSets) {
            System.out.print(s.getId() + " ");
        }
        System.out.println("\n");
    }

    /**
     * Print the solution performance metrics as a row
     *
     */
    public void printRowMetrics() {
        System.out.format("%-25s%12d%15.4f%17.2f\n", getName(), _compTime, _objFn, 100 * _coverage);
    }

    private void reset() {  //set everything to 0;
        _solnSets = new TreeSet<ElementSet>();
        _objFn = 0.0;
        _coverage = 0.0;
        //_compTime = (long) 0.0;
    }

}
