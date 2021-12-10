package by.kosolobov.compositetask.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.kosolobov.compositetask.interpreter.impl.DefaultBitOperation.*;

public class BitOperationInterpreter {
    private static final String REGEX_EXPRESSION = "((\\d+(<<|>>|&|[|]|\\^){1,2}\\d+)|(~\\d+))";
    private static final String REGEX_LEFT = "\\d+[<]{2}\\d+";
    private static final String REGEX_RIGHT = "\\d+[>]{2}\\d+";
    private static final String REGEX_AND = "\\d+[&]\\d+";
    private static final String REGEX_NOT = "[~]\\d+";
    private static final String REGEX_OR = "\\d+[|]\\d+";
    private static final String REGEX_XOR = "\\d+[\\^]\\d+";
    private static final String REGEX_INT = "\\d+";

    public String convertFullText(String text) {
        Pattern pattern = Pattern.compile(REGEX_EXPRESSION);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            text = matcher.replaceFirst(getAnswer(matcher.group()));
            matcher = pattern.matcher(text);
        }

        return text;
    }

    private String getAnswer(String expr) {
        int answer;
        if (expr.matches(REGEX_LEFT)) {
            answer = LEFT.calc(getParams(expr));
        } else if (expr.matches(REGEX_RIGHT)) {
            answer = RIGHT.calc(getParams(expr));
        } else if (expr.matches(REGEX_AND)) {
            answer = AND.calc(getParams(expr));
        } else if (expr.matches(REGEX_NOT)) {
            answer = NOT.calc(getParams(expr));
        } else if (expr.matches(REGEX_OR)) {
            answer = OR.calc(getParams(expr));
        } else if (expr.matches(REGEX_XOR)) {
            answer = XOR.calc(getParams(expr));
        } else {
            return expr;
        }

        return String.valueOf(answer);
    }

    private int[] getParams(String expr) {
        List<Integer> paramsList = new ArrayList<>();

        Pattern pattern = Pattern.compile(REGEX_INT);
        Matcher matcher = pattern.matcher(expr);

        while (matcher.find()) {
            paramsList.add(Integer.valueOf(matcher.group()));
        }

        int[] params = new int[paramsList.size()];
        for (int i = 0; i < params.length; i++) {
            params[i] = paramsList.get(i);
        }

        return params;
    }
}
