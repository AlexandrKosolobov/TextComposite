package by.kosolobov.compositetask.parser.impl;

import by.kosolobov.compositetask.entity.Component;
import by.kosolobov.compositetask.entity.TextComponent;
import by.kosolobov.compositetask.parser.ComponentParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.kosolobov.compositetask.type.impl.DefaultComponentType.PARAGRAPH;


public class ParagraphParser implements ComponentParser {
    private static final Logger log = LogManager.getLogger(ParagraphParser.class);
    private static final ParagraphParser instance = new ParagraphParser();
    private static final String SENTENCE_DELIMITER = "(?<=((\\.)|(!)|(\\?)))\\s+(?=[A-Z])";
    private final ComponentParser nextParser = SentenceParser.getInstance();

    private ParagraphParser() {
    }

    public static ParagraphParser getInstance() {
        return instance;
    }

    @Override
    public Component parse(String content) {
        String[] sentences = content.split(SENTENCE_DELIMITER);
        Component paragraphComponent = new TextComponent(PARAGRAPH);
        for (String sentence : sentences) {
            Component sentenceComponent = nextParser.parse(sentence);
            paragraphComponent.add(sentenceComponent);
        }
        log.log(Level.INFO, "Paragraph parsed");
        return paragraphComponent;
    }
}
