package org.yarr.escart;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

public class Main
{
    public static void main(String... args) throws Exception
    {

        Node node = nodeBuilder().client(true).node();
        final Client client = node.client();

        Product p = new Product(
                UUID.randomUUID(),
                "Product #1",
                12343,
                Arrays.asList("Tag1", "Tag2"),
                new HashSet<String>(Arrays.asList(new String[]{"Tag1", "Tag2"}))
        );
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(p);
        System.out.println(json);

        for(int i = 0; i < 2_500_000; i++)
            client.prepareIndex("products", "product").setSource(gson.toJson(Product.random())).setId(String.valueOf(i)).execute().actionGet();

        ExecutorService es = Executors.newFixedThreadPool(200);

        for (int i = 0; i < 2000; i++)
            es.execute(new Query(client));

        es.shutdown();
        es.awaitTermination(15, TimeUnit.SECONDS);

        client.close();
        node.close();
    }

    private static class Query implements Runnable {

        final Client client;
        Query(Client client) {
            this.client = client;
        }
        @Override
        public void run()
        {
            GetResponse resp = client.prepareGet("products", "products", "123").get();
            String map = resp.getSourceAsString();
            System.out.println(".");
        }
    }

}
