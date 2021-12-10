package by.kosolobov.compositetask.parser.impl;

import by.kosolobov.compositetask.entity.Component;
import by.kosolobov.compositetask.entity.SymbolComponent;
import by.kosolobov.compositetask.entity.TextComponent;
import by.kosolobov.compositetask.parser.ComponentParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.kosolobov.compositetask.type.SymbolComponentType.NEW_LINE;
import static by.kosolobov.compositetask.type.impl.DefaultComponentType.TEXT;


public class TextParser implements ComponentParser {
    private static final Logger log = LogManager.getLogger(TextParser.class);
    private static final TextParser instance = new TextParser();
    private static final String PARAGRAPH_DELIMITER = "\\n";
    private final ComponentParser nextParser = ParagraphParser.getInstance();

    private TextParser() {
    }

    public static TextParser getInstance() {
        return instance;
    }

    @Override
    public Component parse(String content) {
        String[] paragraphs = content.split(PARAGRAPH_DELIMITER);
        Component textComponent = new TextComponent(TEXT);
        for (String paragraph : paragraphs) {
            Component paragraphComponent = nextParser.parse(paragraph);
            textComponent.add(paragraphComponent);
            Component delimiter = new SymbolComponent(NEW_LINE, '\n');
            textComponent.add(delimiter);
        }

        log.log(Level.INFO, "Text parsed.");
        return textComponent;
    }
}
