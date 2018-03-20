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
import java.util.TreeSet;
import util.ElementSet;

public class GreedyCostSolver extends GreedySolver {

    public GreedyCostSolver() {
        _name = "Cost";
    }

    public ElementSet nextBestSet() {  //overrides the absract method in the parent class
        double cost = 0;
        if(usedEle.containsAll(iter.first().getAllElements())){
            for (ElementSet e: iter){
                if (!usedEle.containsAll(e.getAllElements())){
                    cost = e.getCost();
                    _bestSet = e;
                    break;
                }
            }
        }
        else {
        cost = iter.first().getCost();
        _bestSet = iter.first();
        }
        
        for(ElementSet e:iter){
            if( (e.getCost() < cost)&&(!usedEle.containsAll(e.getAllElements()))){
                cost = e.getCost();
                _bestSet = e;
            }
        }
        
        return _bestSet;
    }
}
