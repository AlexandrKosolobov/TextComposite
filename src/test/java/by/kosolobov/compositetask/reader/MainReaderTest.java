package by.kosolobov.compositetask.reader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainReaderTest {
    private static final String EXPECTED = """
    Hi there! I am Sasha. It's my text. So I will just copy-paste it as many times as I want.
    Does it matter what is the 1<<10 content of this file? No!
    Does it matter what is the 64>>2 content of this file? No!
    Does it matter what is the 123|321 content of this file? No!
    Does it matter what is the 123&321 content of this file? No!
    Does it matter what is the 123^321 content of this file? No!
    Does it matter what is the ~123 content of this file? No!
    Or? May that be a part of a big game? Kind of.
    """;

    @Test
    void read() {
        MainReader reader = new MainReader();
        String result = reader.read("files/TextInput.txt");
        assertEquals(EXPECTED, result);
    }
}