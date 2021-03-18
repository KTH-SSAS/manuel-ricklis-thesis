package graph;

import core.AttackStep;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/*
    TODO:
        * get relevant nodes by only regarding steps that can actually lead to the target
            if one path ends in a step that has no children and is not the target, all nodes in the path from the last node
            with only one child have to be omitted
        * add ttc to steps to guarantee that payoff can only increase or stay with each step
        * define F(x, a) correctly
*/

/**
 * Attack graph represented by an entry point and target node.
 * Attack nodes know their parents and children, thus forming the edges.
 */
public class AttackGraph {
    private final AttackStep entryPointStep;
    private final AttackStep targetStep;

    private AttackNode entryPointNode;
    private final AttackNode targetNode;

    private static final ArrayList<String> seenNodes = new ArrayList<>();
    private static final ArrayList<AttackNode> allNodes = new ArrayList<>();


    private final ArrayList<Double> valueFunction = new ArrayList<>();

    public AttackGraph(AttackStep entryPointStep, AttackStep targetStep) {
        this.entryPointStep = entryPointStep;
        this.targetStep = targetStep;

        this.targetNode = new AttackNode(targetStep);
        allNodes.add(targetNode);
    }

    // ################################################ GRAPH ##########################################################

    /**
     * Expands the attack graph with the visited parents
     * Seen nodes are not followed up in case of cycles in the graph
     *
     * @param step The step to be expanded. Must be target attack step for first function call.
     * @param node The attack node corresponding to the attack step
     */
    public void expandGraph(AttackStep step, AttackNode node) {
        if (seenNodes.contains(step.fullName())) {
            return;
        } else if (step.equals(entryPointStep)) {
            entryPointNode = node;
            return;
        }

        seenNodes.add(step.fullName());

        for (AttackStep as : step.visitedParents) {
            if (as.visitedParents.isEmpty() && !as.equals(entryPointStep)) {
                continue;
            }

            AttackNode parentNode;
            if (seenNodes.contains(as.fullName())) {
                parentNode = getAttackNodeObj(as.fullName());
            } else {
                parentNode = new AttackNode(as);
            }
            allNodes.add(parentNode);
            node.addParent(parentNode);
            parentNode.addChild(node);
            expandGraph(as, parentNode);
        }
    }

    /**
     * Starts the expanding process with the target node
     */
    public void expandGraph() {
        expandGraph(targetStep, targetNode);
    }

    // ########################################### HELPERS #############################################################

    public void exportGraph(String fileName) {
        JSONObject obj = new JSONObject();

        for (AttackNode node : allNodes) {
            obj.put(node.getName(), node.getParentNames().toArray());
        }

        try (FileWriter file = new FileWriter("GraphFiles/" + System.currentTimeMillis() + "-" + fileName + ".json")) {
            file.write(obj.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printGraph() {
        printGraph(targetNode);
    }

    private static final ArrayList<AttackNode> seenNodesPrint = new ArrayList<>();

    public void printGraph(AttackNode node) {
        if (seenNodesPrint.contains(node)) {
            return;
        }

        System.out.println(node.getName() + " " + node.getParentNames());
        seenNodesPrint.add(node);
        for (AttackNode as : node.getParents()) {
            printGraph(as);
        }
    }

    public AttackNode getTargetNode() {
        return targetNode;
    }

    public AttackNode getEntryPointNode() {
        return entryPointNode;
    }

    public AttackNode getAttackNodeObj(String name) {
        for (AttackNode node : allNodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }

        return null;
    }

    // ############################################## VALUE ############################################################
//
//    public void calculateValueFunction() {
//    }
//
//    // discount factor
//    private final float beta = 0.8f;
//
//    private double bellmanEquation(AttackNode x, int index) {
//        double value = 0;
//        double max = Double.MIN_VALUE;
//        for (AttackNode as : x.getChildren()) {
//            value = F(x, as) + beta * bellmanEquation(as, index + 1);
//            if (value > max) {
//                max = value;
//            }
//        }
//
//        this.valueFunction.add(index, value);
//        return value;
//    }
//
//    private double F(AttackNode x, AttackNode as) {
//        return as.ttc == 0 ? 0 : 1.0 / as.ttc;
//    }

}
