package org.yarr.escart;

import org.yarr.generator.WordsGenerator;

import java.util.*;

public class Product
{
    static Random random = new Random();
    UUID id;
    String name;
    long price;
    List<String> tags;
    Set<String> categories;

    @Override
    public String toString()
    {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", tags=" + tags +
                ", categories=" + categories +
                '}';
    }

    public Product(UUID id, String name, long price, List<String> tags, Set<String> categories)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.tags = tags;
        this.categories = categories;
    }

    public static Product random() {
        long price = random.nextInt(30) * 50 + 500;
        String name = WordsGenerator.i().getCapitalized();
        Set<String> cats = new HashSet<>();
        cats.addAll(WordsGenerator.i().getWords(5));
        List<String> tags = WordsGenerator.i().getWords(3);
        return new Product(UUID.randomUUID(), name, price, tags, cats);
    }
}
