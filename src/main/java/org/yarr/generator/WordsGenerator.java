package org.yarr.generator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordsGenerator {
    final private List<String> words = new ArrayList<>();
    final private int totalNo;
    final private Random r = new Random();
    final private CharacterGenerator cg;
    private WordsGenerator(CharacterGenerator cg) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(TopLevelDomainGenerator.class.getResourceAsStream("wordsEn.txt")))) {
            String line;
            while((line = br.readLine()) != null)
                words.add(line.toLowerCase());
        } catch (Exception ignored) { }
        this.cg = cg;
        totalNo = words.size();
    }

    public String get() {
        if(totalNo > 0)
            return words.get(r.nextInt(totalNo));
        else
            return new String(cg.take(3));
    }

    public String getSentence(int words) {
        StringBuilder sb = new StringBuilder();
        sb.append(getCapitalized()).append(" ");
        boolean punctuated = false;
        for (int i = 0; i < words - 1; i++) {
            sb.append(get());
            if(r.nextInt(100) < 95) {
                punctuated = false;
                sb.append(" ");
            }
            else {
                sb.append(cg.takePunctuation())
                        .append(" ");
                punctuated = true;
            }
        }
        sb.delete(sb.length() - (punctuated ? 2 : 1), sb.length());
        sb.append(cg.takeEol());

        return sb.toString();
    }

    public String getCapitalized() {
        String w = null;
        while(w == null || w.length() < 2)
            w = get();

        return Character.toUpperCase(w.charAt(0)) + w.substring(1);
    }

    public List<String> getWords(int n) {
        List<String> words = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            words.add(get());
        }
        return words;
    }

    public static WordsGenerator i() {
        return Lazy.INSTANCE;
    }

    private static class Lazy {
        private static WordsGenerator INSTANCE;
        static {
            INSTANCE = new WordsGenerator(new CharacterGenerator());
        }
    }

    public static void main(String... args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(WordsGenerator.i().getSentence(10));
        }
    }
}
