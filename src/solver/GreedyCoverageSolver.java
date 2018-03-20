/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solver;

/**
 *
 * @author yanqiaoy
 */
import java.util.SortedSet;
import util.ElementSet;

public class GreedyCoverageSolver extends GreedySolver { //find least number of sets that covers all elements

    public GreedyCoverageSolver() {
        _name = "Coverage";

    }

    public ElementSet nextBestSet() {
        int size = 0;
        int count = 0;
        int tempcount = 0;
        for (ElementSet e : iter) {
            if (!usedEle.isEmpty()) {
                for (int i : e.getAllElements()) {
                    if (!usedEle.contains(i)) {
                        tempcount++;
                    }
                }

                if (tempcount > count) {
                    count = tempcount;
                    _bestSet = e;
                }
                tempcount = 0;
            } 
             else if (e.getAllElements().size() > size) {
                size = (int) e.getAllElements().size();
                _bestSet = e;
            }
        }
        return _bestSet;
    }
}
