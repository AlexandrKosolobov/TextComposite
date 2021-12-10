package by.kosolobov.compositetask.type.impl;

import by.kosolobov.compositetask.type.ComponentType;

public enum DefaultComponentType implements ComponentType {
    TEXT {
        @Override
        public int getLevel() {
            return 1;
        }
    },
    PARAGRAPH {
        @Override
        public int getLevel() {
            return 2;
        }
    },
    SENTENCE {
        @Override
        public int getLevel() {
            return 3;
        }
    },
    WORD {
        @Override
        public int getLevel() {
            return 4;
        }
    },
    SYMBOL {
        @Override
        public int getLevel() {
            return 5;
        }
    }
}
