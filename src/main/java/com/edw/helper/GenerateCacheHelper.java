package com.edw.helper;

import com.edw.bean.User;
import jakarta.annotation.PostConstruct;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
        cache01List = Files.readAllLines(ResourceUtils.getFile("classpath:cache01.txt").toPath());
        cache02List = Files.readAllLines(ResourceUtils.getFile("classpath:cache02.txt").toPath());
        cache03List = Files.readAllLines(ResourceUtils.getFile("classpath:cache03.txt").toPath());
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