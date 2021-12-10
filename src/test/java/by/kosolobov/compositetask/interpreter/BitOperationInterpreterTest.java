package by.kosolobov.compositetask.interpreter;

import by.kosolobov.compositetask.entity.Component;
import by.kosolobov.compositetask.parser.impl.TextParser;
import by.kosolobov.compositetask.service.MainService;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class BitOperationInterpreterTest {
    private static final String CONTENT = """
    Hi there! I am Sasha. It's my text. So I will just copy-paste it as many times as I want.
    Does it matter what is the 1<<10 content of this file? No!
    Does it matter what is the 64>>2 content of this file? No!
    Does it matter what is the 123|321 content of this file? No!
    Does it matter what is the 123&321 content of this file? No!
    Does it matter what is the 123^321 content of this file? No!
    Does it matter what is the ~123 content of this file? No!
    Or? May that be a part of a big game? Kind of.
    """;
    private static final String EXPECTED = """
    Hi there! I am Sasha. It's my text. So I will just copy-paste it as many times as I want.\s
    Does it matter what is the 1024 content of this file? No!\s
    Does it matter what is the 16 content of this file? No!\s
    Does it matter what is the 379 content of this file? No!\s
    Does it matter what is the 65 content of this file? No!\s
    Does it matter what is the 314 content of this file? No!\s
    Does it matter what is the -124 content of this file? No!\s
    Or? May that be a part of a big game? Kind of.\s
    """;
    TextParser parser = TextParser.getInstance();
    Component text = parser.parse(CONTENT);

    @Test
    void convertExpression() {
        BitOperationInterpreter bit = new BitOperationInterpreter();
        String newText = bit.convertFullText(text.toString());
        assertEquals(EXPECTED, newText);
    }
}