package com.kodo.codewars.scraper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kodo.utils.FileUtils;
import com.kodo.utils.TaskTracker;

public class CodewarsScraper {

    private AtomicInteger counter = new AtomicInteger(0);
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private KataInformation kataInfo;
    private File file = new File("Kodo/katas.json");
    private TaskTracker tracker;

    // Create a thread pool with the specified number of threads
    private ExecutorService executor = Executors.newFixedThreadPool(1);


    public void scrapeCodewars() {

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Get JSON
        String json = FileUtils.readFile(file);

        // Create KataInformation from file using Gson, if it cannot make the kata info, then simply create a new instance of KataInformation
        this.kataInfo = this.gson.fromJson(json, KataInformation.class);

        if (this.kataInfo == null) {
            this.kataInfo = new KataInformation();
        }

        // Update kata info
        Set<String> cachedKata = retrieveCachedKata();
        kataInfo.setCachedKata(cachedKata);

        System.out.println("Cached Kata: " + cachedKata.size() + " Kata cached: " + kataInfo.getKatas().keySet().size());

        int totalTasks = cachedKata.size() - this.kataInfo.getKatas().keySet().size();

        this.tracker = new TaskTracker(totalTasks);

        retrieveKataInfo(kataInfo, 1);

        // Save to file using Gson pretty printing
        this.save();

        
    }

    private synchronized void save(){
        // Save to file using Gson pretty printing
        synchronized(kataInfo){
            String jsonPretty = gson.toJson(kataInfo);
            FileUtils.writeFile(file, jsonPretty);
            System.out.println("Saved contents to file:  " + this.kataInfo.getKatas().keySet().size() + " katas");
        }
    }

    private void processId(KataInformation kataInfo, String id) {

        try{
            System.out.println(
                    "Processing id: " + id + " " + counter.incrementAndGet() + "/" + this.tracker.getTotalTasks()
                            + " by " + Thread.currentThread().getName()
                            + " " + (this.tracker.getPercentageComplete()*100) + "% complete " + this.tracker.getEstimatedTimeRemainingAsPrettyString() + " remaining");

            String url = "https://www.codewars.com/api/v1/code-challenges/" + id;

            String json = readWebPage(url);

            CodewarsKata kata = new Gson().fromJson(json, CodewarsKata.class);

            if (kata == null) {
                System.out.println("Kata is null");
                return;
            }

            kataInfo.addKata(id, kata);
            this.tracker.incrementCompletedTasks();

        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    private void retrieveKataInfo(KataInformation kataInfo, int numThreads) {
        Set<String> katas = kataInfo.getCachedKata();

        List<String> ids = new ArrayList<>(katas);
        int numIds = ids.size();

        // Create a CountDownLatch to wait for all tasks to complete
        CountDownLatch latch = new CountDownLatch(numIds);

        for (int i = 0; i < numIds; i += numThreads) {
            final int startIndex = i;

            // Submit a task to process the ids in parallel
            executor.submit(() -> {
                for (int j = startIndex; j < Math.min(startIndex + numThreads, numIds); j++) {
                    String id = ids.get(j);
                    if (!kataInfo.containsKata(id)) {
                        processId(kataInfo, id);
                    }
                }
                latch.countDown();


                if((counter.get() + 1) % 20 == 0){
                    this.save();
                }

            });

        }

        // Wait for all tasks to complete
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shutdown the executor
        executor.shutdown();
    }

    private Set<String> retrieveCachedKata() {
        File file = new File("Kodo/html4.html");

        String[] data = FileUtils.readFile(file).split("class=\"w-full md:w-9/12 md:pl-4 pr-0 space-y-4\">")[1]
                .split("<div id=\"collection-modal-view\" v-scope v-cloak>")[0].split("data-id=\"");

        Set<String> content = new HashSet<>();

        for (int i = 0; i < data.length; i++) {
            String line = data[i].split("\"")[0];
            if (line.contains(" ")) {
                continue;
            }
            content.add(line);
        }

        return content;
    }

    private String readWebPage(String url) {
        String html = "";

        try (CloseableHttpClient httpClient = HttpClients.custom().setUserAgent("Mozilla/5.0")
                .disableCookieManagement().build()) {

            HttpGet httpGet = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    html = EntityUtils.toString(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html;
    }

    public static void main(String[] args) {
        CodewarsScraper scraper = new CodewarsScraper();
        scraper.scrapeCodewars();
    }
}
