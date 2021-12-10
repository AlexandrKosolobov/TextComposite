package by.kosolobov.compositetask.service;

import by.kosolobov.compositetask.entity.Component;
import by.kosolobov.compositetask.parser.ComponentParser;
import by.kosolobov.compositetask.parser.impl.TextParser;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MainServiceTest {
    private static final String CONTENT = """
    Hi there! I am Sasha. It's my text. So I will just copy-paste it as many times as I want.
    Does it matter what is the 1<<10 content of this file? No!
    Does it matter what is the 64>>2 content of this file? No!
    Does it matter what is the 123|321 content of this file? No!
    Does it matter what is the 123&321 content of this file? No!
    Does it matter what is the 123^321 content of this file? No!
    Or? May that be a part of a big game? Kind of.
    """;
    TextParser parser = TextParser.getInstance();
    Component text = parser.parse(CONTENT);
    MainService service = new MainService();

    @Test
    void sortParagraphBySentenceAmount() {
        final String EXPECTED = """
                Hi there! I am Sasha. It's my text. So I will just copy-paste it as many times as I want.\s
                Or? May that be a part of a big game? Kind of.\s
                Does it matter what is the 1<<10 content of this file? No!\s
                Does it matter what is the 64>>2 content of this file? No!\s
                Does it matter what is the 123|321 content of this file? No!\s
                Does it matter what is the 123&321 content of this file? No!\s
                Does it matter what is the 123^321 content of this file? No!\s
                """;
        Component result = service.sortParagraphBySentenceAmount(text);
        assertEquals(EXPECTED, result.toString());
    }

    @Test
    void findSentenceWithLongestWord() {
        final String EXPECTED = "[So I will just copy-paste it as many times as I want. ]";
        List<Component> result = service.findSentenceWithLongestWord(text);
        assertEquals(EXPECTED, result.toString());
    }

    @Test
    void deleteSentencesWithLessWordsThan() {
        final String EXPECTED = """
                So I will just copy-paste it as many times as I want.\s
                Does it matter what is the 1<<10 content of this file?\s
                Does it matter what is the 64>>2 content of this file?\s
                Does it matter what is the 123|321 content of this file?\s
                Does it matter what is the 123&321 content of this file?\s
                Does it matter what is the 123^321 content of this file?\s
                May that be a part of a big game?\s
                """;
        Component result = service.deleteSentencesWithLessWordsThan(text, 6);
        assertEquals(EXPECTED, result.toString());
    }

    @Test
    void countRepeatedWords() {
        final String EXPECTED = "{hi=1, no=5, big=1, game=1, 123|321=1, be=1, part=1, matter=5, content=5, that=1, times=1, file=5, does=5, of=7, text=1, 123^321=1, sasha=1, so=1, just=1, 1<<10=1, a=2, or=1, may=1, will=1, 64>>2=1, kind=1, want=1, this=5, i=3, is=6, it=7, am=1, my=1, many=1, the=5, copy-paste=1, as=2, what=5, there=1, 123&321=1}";
        Map<String, Integer> result = service.countRepeatedWords(text);
        assertEquals(EXPECTED, result.toString());
    }

    @Test
    void countVowelsAndConsonants() {
        final String EXPECTED = "{I am Sasha. =[vowels:4, consonants:4], It's my text. =[vowels:4, consonants:6], Does it matter what is the 1<<10 content of this file? =[vowels:14, consonants:24], Does it matter what is the 64>>2 content of this file? =[vowels:14, consonants:24], Does it matter what is the 123|321 content of this file? =[vowels:14, consonants:24], Does it matter what is the 123&321 content of this file? =[vowels:14, consonants:24], Does it matter what is the 123^321 content of this file? =[vowels:14, consonants:24], No! =[vowels:1, consonants:1], Or? =[vowels:1, consonants:1], So I will just copy-paste it as many times as I want. =[vowels:17, consonants:23], May that be a part of a big game? =[vowels:11, consonants:13], Hi there! =[vowels:3, consonants:4], Kind of. =[vowels:2, consonants:4]}";
        Map<Component, MainService.VowelsAndConsonants> result = service.countVowelsAndConsonants(text);
        assertEquals(EXPECTED, result.toString());
    }
}