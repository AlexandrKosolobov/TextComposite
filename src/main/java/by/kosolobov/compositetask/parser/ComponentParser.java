package by.kosolobov.compositetask.parser;

import by.kosolobov.compositetask.entity.Component;

public interface ComponentParser {
    Component parse(String content);
}
