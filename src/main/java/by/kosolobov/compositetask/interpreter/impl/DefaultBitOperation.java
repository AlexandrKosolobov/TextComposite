package by.kosolobov.compositetask.interpreter.impl;

import by.kosolobov.compositetask.interpreter.BitOperation;

public enum DefaultBitOperation implements BitOperation {
    LEFT {
        @Override
        public int calc(int[] param) {
            return param[0] << param[1];
        }
    },
    RIGHT {
        @Override
        public int calc(int[] param) {
            return param[0] >> param[1];
        }
    },
    AND {
        @Override
        public int calc(int[] param) {
            return param[0] & param[1];
        }
    },
    NOT {
        @Override
        public int calc(int[] param) {
            return ~param[0];
        }
    },
    OR {
        @Override
        public int calc(int[] param) {
            return param[0] | param[1];
        }
    },
    XOR {
        @Override
        public int calc(int[] param) {
            return param[0] ^ param[1];
        }
    }
}
