package com.edw.helper;

import com.edw.bean.User;
import jakarta.annotation.PostConstruct;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * <pre>
 *     com.edw.helper.GenerateCacheHelper
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 06 Des 2023 09:12
 */
@Service
public class GenerateCacheHelper {

    @Autowired
    private RemoteCacheManager cacheManager;

    private List<String> cache01List = new ArrayList();
    private List<String> cache02List = new ArrayList();
    private List<String> cache03List = new ArrayList();

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    @PostConstruct
    public void generateInitialData() throws Exception {
        populateData("cache01.txt", cache01List);
        populateData("cache02.txt", cache02List);
        populateData("cache03.txt", cache03List);

    }

    private void populateData(String filename, List array) throws Exception {
        try(BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("classpath:"+filename).getInputStream(), StandardCharsets.UTF_8)
        )) {
            array.addAll(bufferedReader.lines().collect(Collectors.toList()));
        }
    }

    public void sendToCache() {
        System.out.println("cache01List has "+cache01List.size());
        System.out.println("cache02List has "+cache02List.size());
        System.out.println("cache03List has "+cache03List.size());

        executor.execute(() -> {
            for (String name : cache01List) {
                System.out.println("= procssesing "+name);
                cacheManager.getCache("user-cache").put(name, new User(name, 17, "Jakarta"));
            }
        });
        executor.execute(() -> {
            for (String name : cache02List) {
                System.out.println("= = procssesing "+name);
                cacheManager.getCache("user-cache").put(name, new User(name, 18, "Bandung"));
            }
        });
        executor.execute(() -> {
            for (String name : cache03List) {
                System.out.println("= = = procssesing "+name);
                cacheManager.getCache("user-cache").put(name, new User(name, 19, "Semarang"));
            }
        });
    }
}