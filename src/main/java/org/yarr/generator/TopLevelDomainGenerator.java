package org.yarr.generator;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TopLevelDomainGenerator
{
    final private List<String> domains = new ArrayList<>();
    final private int totalNo;
    final private Random r = new Random();
    final private CharacterGenerator cg;
    private TopLevelDomainGenerator(CharacterGenerator cg) {
        // https://www.icann.org/resources/pages/tlds-2012-02-25-en here s the source of this txt file
        try (BufferedReader br = new BufferedReader(new InputStreamReader(TopLevelDomainGenerator.class.getResourceAsStream("tlds-alpha-by-domain.txt")))) {
            String line;
            while((line = br.readLine()) != null) {
                if(!line.startsWith("#"))
                    domains.add(line.toLowerCase());
            }
        } catch (Exception e) {

        }
        this.cg = cg;
        totalNo = domains.size();
    }

    public String get() {
        if(totalNo > 0)
            return domains.get(r.nextInt(totalNo));
        else
            return new String(cg.take(3));
    }

    public static TopLevelDomainGenerator i() {
        return Lazy.INSTANCE;
    }

    private static class Lazy {
        private static TopLevelDomainGenerator INSTANCE;
        static {
            INSTANCE = new TopLevelDomainGenerator(new CharacterGenerator());
        }
    }
}
