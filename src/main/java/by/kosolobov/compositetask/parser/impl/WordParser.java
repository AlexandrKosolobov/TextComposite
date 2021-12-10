package by.kosolobov.compositetask.parser.impl;

import by.kosolobov.compositetask.entity.Component;
import by.kosolobov.compositetask.entity.SymbolComponent;
import by.kosolobov.compositetask.entity.TextComponent;
import by.kosolobov.compositetask.parser.ComponentParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.kosolobov.compositetask.type.impl.DefaultComponentType.WORD;
import static by.kosolobov.compositetask.type.SymbolComponentType.*;

public class WordParser implements ComponentParser {
    private static final Logger log = LogManager.getLogger(WordParser.class);
    private static final WordParser instance = new WordParser();
    private static final String SYMBOL_DELIMITER = "";
    private static final String REGEX_LETTER = "[a-zA-Z]";
    private static final String REGEX_PUNCTUATION = "[,]";
    private static final String REGEX_NUMBER = "[0-9]";
    private static final String REGEX_MARK = "[.!?]";

    private WordParser() {
    }

    public static WordParser getInstance() {
        return instance;
    }

    @Override
    public Component parse(String content) {
        String[] symbols = content.split(SYMBOL_DELIMITER);
        Component wordComponent = new TextComponent(WORD);
        for (String symbol : symbols) {
            SymbolComponent symbolComponent;
            if (symbol.matches(REGEX_LETTER)) {
                symbolComponent = new SymbolComponent(LETTER, symbol.charAt(0));
            } else if (symbol.matches(REGEX_PUNCTUATION)) {
                symbolComponent = new SymbolComponent(PUNCTUATION, symbol.charAt(0));
            } else if (symbol.matches(REGEX_NUMBER)) {
                symbolComponent = new SymbolComponent(NUMBER, symbol.charAt(0));
            } else if (symbol.matches(REGEX_MARK)) {
                symbolComponent = new SymbolComponent(MARK, symbol.charAt(0));
            } else {
                symbolComponent = new SymbolComponent(UNSUPPORTED, symbol.charAt(0));
            }
            wordComponent.add(symbolComponent);
        }
        log.log(Level.INFO, "Word parsed");
        return wordComponent;
    }
}
