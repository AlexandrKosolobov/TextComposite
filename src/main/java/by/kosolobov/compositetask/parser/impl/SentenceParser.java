package by.kosolobov.compositetask.parser.impl;

import by.kosolobov.compositetask.entity.Component;
import by.kosolobov.compositetask.entity.SymbolComponent;
import by.kosolobov.compositetask.entity.TextComponent;
import by.kosolobov.compositetask.parser.ComponentParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.kosolobov.compositetask.type.impl.DefaultComponentType.SENTENCE;
import static by.kosolobov.compositetask.type.SymbolComponentType.*;


public class SentenceParser implements ComponentParser {
    private static final Logger log = LogManager.getLogger(SentenceParser.class);
    private static final SentenceParser instance = new SentenceParser();
    private static final String WORD_DELIMITER = "\\s+";
    private final ComponentParser nextParser = WordParser.getInstance();

    private SentenceParser() {
    }

    public static SentenceParser getInstance() {
        return instance;
    }

    @Override
    public Component parse(String content) {
        String[] words = content.split(WORD_DELIMITER);
        Component sentenceComponent = new TextComponent(SENTENCE);
        for (String word : words) {
            if (word.equals("")) {
                continue;
            }

            if (word.contains(".")) {
                word = word.replace(".", "");
                Component wordComponent = nextParser.parse(word);
                sentenceComponent.add(wordComponent);
                Component mark = new SymbolComponent(MARK, '.');
                sentenceComponent.add(mark);
            } else if (word.contains("!")) {
                word = word.replace("!", "");
                Component wordComponent = nextParser.parse(word);
                sentenceComponent.add(wordComponent);
                Component mark = new SymbolComponent(MARK, '!');
                sentenceComponent.add(mark);
            } else if (word.contains("?")) {
                word = word.replace("?", "");
                Component wordComponent = nextParser.parse(word);
                sentenceComponent.add(wordComponent);
                Component mark = new SymbolComponent(MARK, '?');
                sentenceComponent.add(mark);
            } else {
                Component wordComponent = nextParser.parse(word);
                sentenceComponent.add(wordComponent);
            }

            Component space = new SymbolComponent(SPACE, ' ');
            sentenceComponent.add(space);
        }

        log.log(Level.INFO, "Sentence parsed");
        return sentenceComponent;
    }
}
