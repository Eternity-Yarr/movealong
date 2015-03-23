package org.yarr.generator;

import java.util.Random;

public class CharacterGenerator
{
    final char[] punctuation = new char[]{',',',',',','-',':'};
    final char[] eols = new char[]{'.','.','.','!','?'};
    final private Random r = new Random();
    public char take() {
        return (char)(r.nextInt(26) + 'a');
    }

    public char[] take(int n){
        char[] cs = new char[n];
        for(int i = 0; i < n; i++)
            cs[i] = take();
        return cs;
    }

    public char takePunctuation() {
        return punctuation[r.nextInt(punctuation.length)];
    }

    public char takeEol() {
        return eols[r.nextInt(eols.length)];
    }

    public char[] takeBounded(int atMost) {
        return takeBounded(1, atMost);
    }

    public char[] takeBounded(int atLeast, int atMost) {
        return take(atLeast + r.nextInt(atMost - atLeast));
    }
}
