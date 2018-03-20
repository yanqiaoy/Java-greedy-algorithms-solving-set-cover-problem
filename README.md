# Java-greedy-algorithms-solving-set-cover-problem

In this project, you will implement the greedy algorithms in Section 2 to solve the wSCP integer programming
problem. You will compare the performance of the algorithms using the following three metrics:
 Final objective function value
 Feasibility of the final solution (element coverage)
 Computation time needed for the algorithm to run

model.SCPModel: Represents the weighted CSP problem; implemented as a sorted set of ElementSet.
 util.ElementSet: Represents the tuple (Set ID, Cost, Integer elements to cover). Must in-
herit from interface Comparable and override public int compareTo(Object o) so that it can be
used in a sorted set. 
 solver.GreedyCoverageSolver: Implementation of the greedy coverage heuristic outlined above; this
class should extend GreedySolver and only override nextBestSet().
 solver.GreedyCostSolver: Implementation of the greedy cost heuristic outlined above; this class
should extend GreedySolver and only override nextBestSet().
 solver.ChvatalSolver: Implementation of Chvatal's algorithm heuristic outlined above;
