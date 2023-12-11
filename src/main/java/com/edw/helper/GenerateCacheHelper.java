package com.edw.helper;

import com.edw.bean.User;
import org.infinispan.client.hotrod.MetadataValue;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public void sendToCache() {
        final RemoteCache cache = cacheManager.getCache("user-cache");
        executor.execute(() -> {
            for (int i = 0 ; i < 100; i++) {
                Map<String, User> hashMap = new HashMap();
                for (int j = 0 ; j < 1000; j++) {
                    hashMap.put(UUID.randomUUID().toString(), new User(UUID.randomUUID().toString(), 17, "Jakarta"));
                }
                System.out.println(" = processing "+i);
                cache.putAll(hashMap);
            }
        });
        executor.execute(() -> {
            for (int i = 0 ; i < 100; i++) {
                Map<String, User> hashMap = new HashMap();
                for (int j = 0 ; j < 1000; j++) {
                    hashMap.put(UUID.randomUUID().toString(), new User(UUID.randomUUID().toString(), 19, "Tangerang"));
                }
                System.out.println(" = = processing "+i);
                cache.putAll(hashMap);
            }
        });
        executor.execute(() -> {
            for (int i = 0 ; i < 100; i++) {
                Map<String, User> hashMap = new HashMap();
                for (int j = 0 ; j < 1000; j++) {
                    hashMap.put(UUID.randomUUID().toString(), new User(UUID.randomUUID().toString(), 21, "Bandung"));
                }
                System.out.println(" = = = processing "+i);
                cache.putAll(hashMap);
            }
        });
    }

    public void sendToCacheWithVersion() {
        final RemoteCache cache = cacheManager.getCache("lele-cache");
        executor.execute(() -> {
            for (int i = 0 ; i < 100; i++) {
                Map<String, String> hashMap = new HashMap();
                for (int j = 0 ; j < 1000; j++) {
                    MetadataValue metadataValue = cache.getWithMetadata("1");
                    Long version = metadataValue.getVersion();
                    cache.replaceWithVersion("1", UUID.randomUUID().toString(), version++);
                }
                System.out.println(" = = = processing "+i);
            }
        });
    }

    private String listOfUUID;
}