package com.example.mathquiz.entity;

import java.math.BigDecimal;
import java.util.Random;

public class AlgebraExpression {

    public static final String EXPRESSION_SYMBOL= "E", OPERAND_PLUS = "+", OPERAND_MINUS = "-", OPERAND_MULTIPLY = "*", OPERAND_DIVIDE = "/";
    public static final String PLUS_EXPRESSION = String.format("(%s%s%s)", EXPRESSION_SYMBOL, OPERAND_PLUS, EXPRESSION_SYMBOL); //"(E+E)";
    public static final String MINUS_EXPRESSION = String.format("(%s%s%s)", EXPRESSION_SYMBOL, OPERAND_MINUS, EXPRESSION_SYMBOL);//"(E-E)";
    public static final String MULTIPLY_EXPRESSION = String.format("%s%s%s", EXPRESSION_SYMBOL, OPERAND_MULTIPLY, EXPRESSION_SYMBOL);//"E*E";
    public static final String DIVIDE_EXPRESSION = String.format("%s%s%s", EXPRESSION_SYMBOL, OPERAND_DIVIDE, EXPRESSION_SYMBOL);//"E/E";

    public static void createRandomAgebraExpression(StringBuffer algebraExp) {
        int placeE;

        if (algebraExp == null) {
            algebraExp = new StringBuffer(AlgebraExpression.EXPRESSION_SYMBOL);
        }
        int rand = 5 + (int)(Math.random() * 3);//range is 5-8
        do {
            placeE = algebraExp.indexOf(AlgebraExpression.EXPRESSION_SYMBOL);
            if (placeE < 0)
                break;
            switch (rand) {
                case 0:
                case 1:
                    algebraExp.replace(placeE, placeE + 1, String.format("%d", randomPositiveInt()));
                    break;
                case 2:
                    algebraExp.replace(placeE, placeE + 1, String.format("(-%d)", randomPositiveInt()));
                    break;
                case 3:
                    algebraExp.replace(placeE, placeE + 1,  String.format("%.2f", randomFloat(0, 100)));
                    break;
                case 4:
                    algebraExp.replace(placeE, placeE + 1, String.format("(-%.2f)", randomFloat(0, 100)));
                    break;
                case 5:
                    algebraExp.replace(placeE, placeE + 1, AlgebraExpression.PLUS_EXPRESSION);
                    break;
                case 6:
                    algebraExp.replace(placeE, placeE + 1,  AlgebraExpression.MINUS_EXPRESSION);
                    break;
                case 7:
                    algebraExp.replace(placeE, placeE + 1,  AlgebraExpression.MULTIPLY_EXPRESSION);
                    break;
                case 8:
                    algebraExp.replace(placeE, placeE + 1,  AlgebraExpression.DIVIDE_EXPRESSION);
                    break;
                }
                rand = (int)(Math.random() * 8);
        } while (algebraExp.indexOf(AlgebraExpression.EXPRESSION_SYMBOL) >= 0);

    }


    //return root node of expression tree
    //E->Int
    //E->(Negative Int)
    //E->Float
    //#->(Negative Float)
    //E->(E+E)
    //E->(E-E)
    //E->E*E
    //E->E/E
    public static BinaryNode createRandomAgebraExpressionBinTree(BinaryNode parentNode) {
        int rand = (int)(Math.random() * 9);
        if (parentNode == null) {
            parentNode = new BinaryNode(EXPRESSION_SYMBOL, null, null);
           rand = 5 + (int)(Math.random() * 4);//range is 5-8
        }
        rand = (rand > 8) ? 8 : rand;

        switch (rand) {
            case 0:
            case 1://positive int
                parentNode.setNode(String.format("%d", randomPositiveInt()));
                break;
            case 2://negative int
                parentNode.setNode(String.format("-%d", randomPositiveInt()));
                break;
            case 3://positive float
                parentNode.setNode(String.format("%.2f", randomFloat(0, 100)));
                break;
            case 4://negative float
                parentNode.setNode(String.format("-%.2f", randomFloat(0, 100)));
                break;
            case 5://
                parentNode.createChildNodes(OPERAND_PLUS, new BinaryNode(), new BinaryNode());
                createRandomAgebraExpressionBinTree(parentNode.getLeft());
                createRandomAgebraExpressionBinTree(parentNode.getRight());
                break;
            case 6:
                parentNode.createChildNodes(OPERAND_MINUS, new BinaryNode(), new BinaryNode());
                createRandomAgebraExpressionBinTree(parentNode.getLeft());
                createRandomAgebraExpressionBinTree(parentNode.getRight());
                break;
            case 7:
                parentNode.createChildNodes(OPERAND_MULTIPLY, new BinaryNode(), new BinaryNode());
                createRandomAgebraExpressionBinTree(parentNode.getLeft());
                createRandomAgebraExpressionBinTree(parentNode.getRight());
                break;
            case 8:
                parentNode.createChildNodes(OPERAND_DIVIDE, new BinaryNode(), new BinaryNode());
                createRandomAgebraExpressionBinTree(parentNode.getLeft());
                createRandomAgebraExpressionBinTree(parentNode.getRight());
                break;
        }
        return parentNode;
    }
    public static String getAlgebraExp(BinaryNode node) {
        String strAlgebraExp = "";
        if (node == null || node.getNode() == null)
            return strAlgebraExp;
        if (node.getNode().equals("+") || node.getNode().equals("-"))
            strAlgebraExp += "(";
        if (node.getLeft() != null)
            strAlgebraExp += getAlgebraExp(node.getLeft());
        if ((node.getLeft() == null) && node.getNode().charAt(0) == '-') //negative int or float
            strAlgebraExp += "(" + node.getNode() + ")";
        else
            strAlgebraExp += node.getNode();
        if (node.getRight() != null)
            strAlgebraExp += getAlgebraExp(node.getRight());
        if (node.getNode().equals("+") || node.getNode().equals("-"))
            strAlgebraExp += ")";
        return strAlgebraExp;
    }

    public static boolean isNumeric(String s) {
        if (s == null || s.length() <= 0)
            return false;
        for (int i = 0; i < s.length(); i ++){
            if (s.charAt(i) == '.' && (i == 0 || i == s.length() - 1))//exclude .xx or xx.
                return false;
            if (s.charAt(i) == '-' && i != 0)//exclude xx-x
                return false;
            if (!Character.isDigit(s.charAt(i)))
                return false;

        }
        return true;
    }
    public static  float randomFloat(int min, int max) {
        float random = 0.0f;
        do {
            Random r = new Random();
            random = min + r.nextFloat() * (max - min);
        } while (Math.abs(random) < 0.001);
        return random;
    }
    public static int randomPositiveInt() {
        int i = 0;
        do {
            i = (int)(Math.random() * 100);
        } while (i == 0);
        return i;
    }

    public static BigDecimal doCalc(String operand, BigDecimal b1, BigDecimal b2) {
        switch (operand) {
            case OPERAND_PLUS:
                return b1.add(b2);
            case OPERAND_MINUS:
                return b1.subtract(b2);
            case OPERAND_MULTIPLY:
                return b1.multiply(b2);
            case OPERAND_DIVIDE:
                return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
            default:
                return new BigDecimal(Integer.MAX_VALUE);
        }
    }
    public static BigDecimal calculateExpression(BinaryNode node) {
        BigDecimal algebraExpressionResult = new BigDecimal(Integer.MAX_VALUE);
        if (node == null)
            return algebraExpressionResult;
        if ((node.getLeft().getLeft() == null) && (node.getRight().getLeft() == null))//all children are number
            return doCalc(node.getNode(), new BigDecimal(node.getLeft().getNode()), new BigDecimal(node.getRight().getNode()));
        else if ((node.getLeft().getLeft() == null) && (node.getRight().getLeft() != null)){//left node is number, right not
            BigDecimal right = calculateExpression(node.getRight());
            return doCalc(node.getNode(), new BigDecimal(node.getLeft().getNode()), right);
        }
        else if ((node.getLeft().getLeft() != null) && (node.getRight().getLeft() == null)) {//right node is number, left not
            BigDecimal left = calculateExpression(node.getLeft());
            return doCalc(node.getNode(), left, new BigDecimal(node.getRight().getNode()));
        }
        else if ((node.getLeft().getLeft() != null) && (node.getRight().getLeft() != null)) {//both child nodes are not numbers
            BigDecimal left = calculateExpression(node.getLeft());
            BigDecimal right = calculateExpression(node.getRight());
            return doCalc(node.getNode(), left, right);
        }
        return algebraExpressionResult;
    }
}
