package by.kosolobov.compositetask.parser.impl;

import by.kosolobov.compositetask.entity.Component;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextParserTest {
    private static final String CONTENT = """
    Hi there! I am Sasha. It's my text. So I will just copy-paste it as many times as I want.
    Does it matter what is the 1<<10 content of this file? No!
    Does it matter what is the 64>>2 content of this file? No!
    Does it matter what is the 123|321 content of this file? No!
    Does it matter what is the 123&321 content of this file? No!
    Does it matter what is the 123^321 content of this file? No!
    Or? May that be a part of a big game? Kind of.
    """;

    @Test
    void parse() {
        TextParser parser = TextParser.getInstance();
        Component result = parser.parse(CONTENT);
        assertFalse(result.getChildren().isEmpty());
    }
}