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
import java.util.*;
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

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    public void sendToCache() {
        executor.execute(() -> {
            for (int i = 0 ; i < 100; i++) {
                Map<String, User> hashMap = new HashMap();
                for (int j = 0 ; j < 5000; j++) {
                    hashMap.put(UUID.randomUUID().toString(), new User(UUID.randomUUID().toString(), 17, "Jakarta"));
                }
                System.out.println(" = processing "+i);
                cacheManager.getCache("user-cache").putAll(hashMap);
            }
        });
        executor.execute(() -> {
            for (int i = 0 ; i < 100; i++) {
                Map<String, User> hashMap = new HashMap();
                for (int j = 0 ; j < 5000; j++) {
                    hashMap.put(UUID.randomUUID().toString(), new User(UUID.randomUUID().toString(), 19, "Tangerang"));
                }
                System.out.println(" = = processing "+i);
                cacheManager.getCache("user-cache").putAll(hashMap);
            }
        });
        executor.execute(() -> {
            for (int i = 0 ; i < 100; i++) {
                Map<String, User> hashMap = new HashMap();
                for (int j = 0 ; j < 5000; j++) {
                    hashMap.put(UUID.randomUUID().toString(), new User(UUID.randomUUID().toString(), 21, "Bandung"));
                }
                System.out.println(" = = = processing "+i);
                cacheManager.getCache("user-cache").putAll(hashMap);
            }
        });
    }
}