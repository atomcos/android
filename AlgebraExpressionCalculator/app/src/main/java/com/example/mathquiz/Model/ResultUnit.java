package com.example.mathquiz.Model;

import java.math.BigDecimal;


public class ResultUnit {
    private String algebraExp;
    private BigDecimal algebraExpResult;
    private BigDecimal userAnswer;
    private AnswerResult answerResult;

    public ResultUnit(String algebraExp, BigDecimal algebraExpResult, BigDecimal userAnswer, AnswerResult answerResult) {
        this.algebraExp = algebraExp;
        this.algebraExpResult = algebraExpResult;
        this.userAnswer = userAnswer;
        this.answerResult = answerResult;
    }

    @Override
    public String toString() {
        return String.format("%s = %.2f \nanswer %.2f %s", algebraExp, algebraExpResult, userAnswer, answerResult.toString());
    }

    public String getAlgebraExp() {
        return algebraExp;
    }

    public void setAlgebraExp(String algebraExp) {
        this.algebraExp = algebraExp;
    }

    public BigDecimal getAlgebraExpResult() {
        return algebraExpResult;
    }

    public void setAlgebraExpResult(BigDecimal algebraExpResult) {
        this.algebraExpResult = algebraExpResult;
    }

    public BigDecimal getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(BigDecimal userAnswer) {
        this.userAnswer = userAnswer;
    }

    public AnswerResult getAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(AnswerResult answerResult) {
        this.answerResult = answerResult;
    }
}
