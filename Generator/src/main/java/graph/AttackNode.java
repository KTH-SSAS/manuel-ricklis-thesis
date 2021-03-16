package graph;

import core.AttackStep;

import java.util.ArrayList;

public class AttackNode {
    private final ArrayList<AttackNode> children = new ArrayList<>();
    private final ArrayList<AttackNode> parents = new ArrayList<>();
    private final String name;

    public AttackNode(AttackStep attackStep) {
        this.name = attackStep.fullName();
    }

    public ArrayList<AttackNode> getChildren() {
        return this.children;
    }

    public ArrayList<String> getChildrenNames() {
        ArrayList<String> names = new ArrayList<>();
        for (AttackNode child : children) {
            names.add(child.getName());
        }

        return names;
    }

    public void addChild(AttackNode child) {
        children.add(child);
    }

    public ArrayList<AttackNode> getParents() {
        return this.parents;
    }

    public void addParent(AttackNode parent) {
        parents.add(parent);
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getParentNames() {
        ArrayList<String> names = new ArrayList<>();
        for (AttackNode parent : parents) {
            names.add(parent.getName());
        }

        return names;
    }
}
