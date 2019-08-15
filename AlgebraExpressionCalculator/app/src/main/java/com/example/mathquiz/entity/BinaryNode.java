package com.example.mathquiz.entity;

public class BinaryNode {
    private String node;
    private BinaryNode left, right;

    public BinaryNode() {
        node = AlgebraExpression.EXPRESSION_SYMBOL;
        left = null;
        right = null;
    }

    public BinaryNode(String node, BinaryNode left, BinaryNode right) {
        this.node = node;
        this.left = left;
        this.right = right;
    }
    public void createChildNodes(String node, BinaryNode left, BinaryNode right) {
        this.node = node;
        this.left = left;
        this.right = right;
    }


    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public BinaryNode getLeft() {
        return left;
    }

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public BinaryNode getRight() {
        return right;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }
}
