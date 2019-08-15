package com.example.mathquiz.Model;

import java.util.Comparator;

public class ResultComparator implements Comparator<ResultUnit> {
    @Override
    public int compare(ResultUnit resultUnit, ResultUnit t1) {
        if (resultUnit.getAnswerResult() != t1.getAnswerResult())
            return (resultUnit.getAnswerResult() == AnswerResult.RIGHT) ? -1 : 1;
        return resultUnit.getUserAnswer().compareTo(t1.getUserAnswer());
    }
    public static class ResultComparatorDesc implements Comparator<ResultUnit> {
        @Override
        public int compare(ResultUnit resultUnit, ResultUnit t1) {
            if (resultUnit.getAnswerResult() != t1.getAnswerResult())
                return (resultUnit.getAnswerResult() == AnswerResult.RIGHT) ? 1 : -1;
            return t1.getUserAnswer().compareTo(resultUnit.getUserAnswer());
        }
    }
}
