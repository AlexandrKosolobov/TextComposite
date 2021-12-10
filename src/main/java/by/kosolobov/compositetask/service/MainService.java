package by.kosolobov.compositetask.service;

import by.kosolobov.compositetask.entity.Component;
import by.kosolobov.compositetask.entity.SymbolComponent;
import by.kosolobov.compositetask.entity.TextComponent;
import by.kosolobov.compositetask.type.ComponentType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static by.kosolobov.compositetask.type.SymbolComponentType.NEW_LINE;
import static by.kosolobov.compositetask.type.SymbolComponentType.SPACE;
import static by.kosolobov.compositetask.type.impl.DefaultComponentType.*;


public class MainService {

    public MainService() {
        //default constructor
    }

    // ---------------------------- 1 function ------------------------------ //

    public Component sortParagraphBySentenceAmount(Component text) {
        List<Component> paragraphs = new ArrayList<>();
        fillArrayList(text, PARAGRAPH, paragraphs);
        Map<Component, Integer> paragraphSentenceCountMap = new HashMap<>();

        for (Component paragraph : paragraphs) {
            List<Component> sentences = paragraph.getChildren();
            Integer count = sentences.size();
            paragraphSentenceCountMap.put(paragraph, count);
        }

        Component result = new TextComponent();
        int startSize = paragraphs.size() * 2;
        while (result.getChildren().size() < startSize) {
            Integer sentCount = paragraphSentenceCountMap.get(paragraphs.get(0));
            Component toAdd = paragraphs.get(0);
            for (int i = 0; i < paragraphSentenceCountMap.size(); i++) {
                if (sentCount < paragraphSentenceCountMap.get(paragraphs.get(i))) {
                    sentCount = paragraphSentenceCountMap.get(paragraphs.get(i));
                    toAdd = paragraphs.get(i);
                }
            }
            result.add(toAdd);
            result.add(new SymbolComponent(SPACE, '\n'));
            paragraphSentenceCountMap.remove(toAdd);
            paragraphs.remove(toAdd);
        }

        return result;
    }

    // ---------------------------- 2 function ------------------------------ //

    public List<Component> findSentenceWithLongestWord(Component text) {
        List<Component> sentences = new ArrayList<>();
        fillArrayList(text, SENTENCE, sentences);
        List<Component> result = new ArrayList<>();

        int longestLength = 0;

        for (Component sentence : sentences) {
            for (Component word : sentence.getChildren()) {
                if (longestLength < word.getChildren().size()) {
                    longestLength = word.getChildren().size();
                }
            }
        }

        for (Component sentence : sentences) {
            boolean containLongestWord = false;
            for (Component word : sentence.getChildren()) {
                if (longestLength == word.getChildren().size()) {
                    containLongestWord = true;
                }
            }

            if (containLongestWord) {
                result.add(sentence);
            }
        }

        return result;
    }

    // ---------------------------- 3 function ------------------------------ //

    public Component deleteSentencesWithLessWordsThan(Component text, int min) {
        List<Component> sentences = new ArrayList<>();
        fillArrayList(text, SENTENCE, sentences);

        sentences.removeIf(sentence -> {
            int count = (int) sentence.getChildren()
                    .stream()
                    .filter(word -> word.getType().getLevel() == WORD.getLevel())
                    .count();
            return count < min;
        });

        Component result = new TextComponent();
        sentences.forEach(component -> {
            result.add(component);
            result.add(new SymbolComponent(NEW_LINE, '\n'));
        });

        return result;
    }

    // ---------------------------- 4 function ------------------------------ //

    public Map<String, Integer> countRepeatedWords(Component text) {
        List<Component> words = new ArrayList<>();
        fillArrayList(text, WORD, words);
        Map<String, Integer> wordsMap = new HashMap<>();

        words.forEach(word -> {
            String[] wordStrs = word.toString()
                    .toLowerCase()
                    .replace("'s", " is")
                    .split(" ");

            for (String str : wordStrs) {
                if (wordsMap.containsKey(str)) {
                    int old = wordsMap.get(str);
                    wordsMap.replace(str, old + 1);
                } else {
                    wordsMap.put(str, 1);
                }
            }
        });

        return wordsMap;
    }

    // ---------------------------- 5 function ------------------------------ //

    private static final String REGEX_VOWEL = "[eyuioa]";
    private static final String REGEX_CONSONANT = "[qwrtpsdfghjklzxcvbnm]";

    public class VowelsAndConsonants {
        public final int vowels;
        public final int consonants;

        public VowelsAndConsonants(int vowels, int consonants) {
            this.vowels = vowels;
            this.consonants = consonants;
        }

        @Override
        public String toString() {
            return String.format("[vowels:%d, consonants:%d]", vowels, consonants);
        }
    }

    public Map<Component, VowelsAndConsonants> countVowelsAndConsonants(Component text) {
        List<Component> sentences = new ArrayList<>();
        fillArrayList(text, SENTENCE, sentences);
        Map<Component, VowelsAndConsonants> map = new HashMap<>();

        sentences.forEach(sentence -> {
            List<Component> symbols = new ArrayList<>();
            fillArrayList(sentence, SYMBOL, symbols);

            AtomicInteger vowels = new AtomicInteger(0);
            AtomicInteger consonants = new AtomicInteger(0);

            symbols.forEach(s -> {
                String replaced = s.toString().toLowerCase().replace("'", "i");

                if (replaced.matches(REGEX_VOWEL)) {
                    vowels.getAndIncrement();
                } else if (replaced.matches(REGEX_CONSONANT)) {
                    consonants.getAndIncrement();
                }
            });

            map.put(sentence, new VowelsAndConsonants(vowels.get(), consonants.get()));
        });

        return map;
    }

    private void fillArrayList(Component component, ComponentType type, List<Component> list) {
        if (component.getType().getLevel() == type.getLevel()) {
            list.add(component);
        } else if (component.getType().getLevel() < type.getLevel()) {
            for (Component child : component.getChildren()) {
                fillArrayList(child, type, list);
            }
        }
    }
}
