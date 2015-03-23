package org.yarr.generator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PersonGenerator
{
    final private List<String> firstNames = new ArrayList<>();
    final private List<String> lastNames = new ArrayList<>();
    final private int firstNo;
    final private int lastNo;
    final private CharacterGenerator cg;
    final private Random r = new Random();

    private PersonGenerator(CharacterGenerator cg) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(TopLevelDomainGenerator.class.getResourceAsStream("census-derived-all-first.txt")))) {
            String line;
            while((line = br.readLine()) != null)
                firstNames.add(
                        Character.toUpperCase(line.charAt(0))
                        + line.substring(1,line.indexOf(' ')).toLowerCase());
        } catch (Exception e) {
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(TopLevelDomainGenerator.class.getResourceAsStream("CSV_Database_of_Last_Names.csv")))) {
            String line;
            while((line = br.readLine()) != null)
                lastNames.add(line);
        } catch (Exception e) {
        }
        firstNo = firstNames.size();
        lastNo  = lastNames.size();
        this.cg = cg;
    }
    public String get() {
        return person().fullName();
    }

    public Person person() {
        String firstName = firstNames.get(r.nextInt(firstNo - 1));
        String lastName = lastNames.get(r.nextInt(lastNo -1));
        return new Person(firstName, lastName);
    }
    public static PersonGenerator i(){
        return Lazy.INSTANCE;
    }
    private static class Lazy {
        private static PersonGenerator INSTANCE;
        static {
            INSTANCE = new PersonGenerator(new CharacterGenerator());
        }
    }
    public static class Person {
        final private String firstName;
        final private String lastName;
        final private String nickname;

        Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.nickname = (firstName.substring(0, 1) + '.' + lastName).toLowerCase();
        }

        public String firstName(){
            return firstName;
        }
        public String lastName(){
            return lastName;
        }
        public String fullName(){
            return firstName  + " " + lastName;
        }
        public String nickname(){
            return nickname;
        }
    }

    public static void main(String... args) {
        for (int i = 0; i < 10; i++)
        {
            Person p = PersonGenerator.i().person();
            System.out.println(p.fullName() + " " + p.nickname() );
        }
    }
}
