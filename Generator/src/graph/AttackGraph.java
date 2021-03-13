package graph;

import core.AttackStep;

import java.util.ArrayList;

/*
    TODO:
        * get relevant nodes by only regarding steps that can actually lead to the target
            if one path ends in a step that has no children and is not the target, all nodes in the path from the last node
            with only one child have to be omitted
        * add ttc to steps to guarantee that payoff can only increase or stay with each step
        * define F(x, a) correctly
*/

public class AttackGraph {
    private final AttackStep entryPoint;
    private final AttackStep target;

    private static final ArrayList<AttackStep> seenNodes = new ArrayList<>();

    private final ArrayList<Double> valueFunction = new ArrayList<>();

    public AttackGraph(AttackStep entryPoint, AttackStep target) {
        this.entryPoint = entryPoint;
        this.target = target;
    }

    // ################################################ GRAPH ##########################################################

    /**
     * Expands the attack graph with the visited parents
     * Seen nodes are not followed up in case of cycles in the graph
     *
     * @param node The node to be expanded. Must be target node for first function call.
     */
    public void expandGraph(AttackStep node) {
        if (seenNodes.contains(node) || node.equals(entryPoint)) {
            return;
        }

        seenNodes.add(node);

        for (AttackStep as : node.visitedParents) {
            as.addChild(node);
            expandGraph(as);
        }
    }

    /**
     * Starts the expanding process with the target node
     */
    public void expandGraph() {
        expandGraph(target);
    }

    // ############################################## VALUE ############################################################

    public void calculateValueFunction() {
    }

    // discount factor
    private final float beta = 0.8f;

    private double bellmanEquation(AttackStep x, int index) {
        double value = 0;
        double max = Double.MIN_VALUE;
        for (AttackStep as : x.getChildren()) {
            value = F(x, as) + beta * bellmanEquation(as, index + 1);
            if (value > max) {
                max = value;
            }
        }

        this.valueFunction.add(index, value);
        return value;
    }

    private double F(AttackStep x, AttackStep as) {
        return as.ttc == 0 ? 0 : 1.0 / as.ttc;
    }

    // ########################################### HELPERS #############################################################

    public void printGraph() {
        printGraph(entryPoint);
    }

    private static final ArrayList<AttackStep> seenNodes2 = new ArrayList<>();
    public void printGraph(AttackStep node) {
        if (seenNodes2.contains(node)) {
            return;
        }

        System.out.println(node + " " + node.fullName());
        seenNodes2.add(node);
        for (AttackStep as : node.getChildren()) {
            printGraph(as);
        }
    }
}
