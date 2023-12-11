package com.edw.helper;

import com.edw.bean.User;
import org.infinispan.client.hotrod.MetadataValue;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    private Logger logger = LoggerFactory.getLogger(GenerateCacheHelper.class);

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public void sendToCache() {
        final RemoteCache cache = cacheManager.getCache("user-cache");
        executor.execute(() -> {
            for (int i = 0 ; i < 100; i++) {
                Map<String, User> hashMap = new HashMap();
                for (int j = 0 ; j < 1000; j++) {
                    hashMap.put(UUID.randomUUID().toString(), new User(UUID.randomUUID().toString(), 17, "Jakarta"));
                }
                logger.info(" = processing "+i);
                cache.putAll(hashMap);
            }
        });
        executor.execute(() -> {
            for (int i = 0 ; i < 100; i++) {
                Map<String, User> hashMap = new HashMap();
                for (int j = 0 ; j < 1000; j++) {
                    hashMap.put(UUID.randomUUID().toString(), new User(UUID.randomUUID().toString(), 19, "Tangerang"));
                }
                logger.info(" = = processing "+i);
                cache.putAll(hashMap);
            }
        });
        executor.execute(() -> {
            for (int i = 0 ; i < 100; i++) {
                Map<String, User> hashMap = new HashMap();
                for (int j = 0 ; j < 1000; j++) {
                    hashMap.put(UUID.randomUUID().toString(), new User(UUID.randomUUID().toString(), 21, "Bandung"));
                }
                logger.info(" = = = processing "+i);
                cache.putAll(hashMap);
            }
        });
    }

    public void sendToCacheWithVersion() {
        final RemoteCache cache = cacheManager.getCache("lele-cache");
        final List<String> uuidList = Arrays.asList(uuids.split(","));
        for(int i = 0 ; i < 3; i ++) {
            executor.execute(() -> {
                for (int j = 0 ; j < 300; j++) {
                    for(String uuid : uuidList) {
                        MetadataValue metadataValue = cache.getWithMetadata(uuid);
                        if(metadataValue == null) {
                            cache.put(uuid, UUID.randomUUID().toString());
                        }
                        else {
                            Long version = metadataValue.getVersion();
                            logger.info("=== processing {} before version {} ", uuid, version);
                            version = version +1;
                            cache.replaceWithVersion(uuid, UUID.randomUUID().toString(), version);
                            logger.info("=== processing {} after version {} ", uuid, version);
                        }
                    }
                    logger.info("== finish processing {} ", j);
                }
            });
        }
    }

    private String uuids = "e069baea-bc54-4443-8292-2e0391a8de0c,"+
            "06484ddd-d585-4b68-9abe-63b863921525,"+
            "c8ccc3c1-4377-400e-855b-f3a15f4899b2,"+
            "d724d65b-8c2c-49d3-a904-d0b800454502,"+
            "5c376260-9208-42b8-9502-2a7286e1194a,"+
            "9549e095-733f-4fc7-9acd-db1d71d53e81,"+
            "5c99043c-3cfd-4456-bae3-229900347ed8,"+
            "30da5b6f-878b-44a9-a6e3-4be5163eb839,"+
            "a2e83362-50b1-4f29-a79b-83583a1f78b4,"+
            "876eb869-8b3f-4743-b95d-6fa823e782de,"+
            "fc736e9a-8899-4c28-93d1-5e52a078ab21,"+
            "a4d8efa0-d0c4-4427-a225-fd86b0db5e3c,"+
            "91832888-ebef-4f51-92a3-2dfa4fa522d3,"+
            "6396fd46-edc0-41bb-88c2-a4c6ec6e421c,"+
            "771c87bb-ec9c-40f4-a8c1-b921f35c4ae5,"+
            "4874745c-64ea-4522-aab8-da6852fa22fa,"+
            "cd15dc0f-1b66-4d00-b37b-afb6511ecb29,"+
            "89a938cb-06bf-4ffd-96f2-38716c3961db,"+
            "52c6497a-9eab-4c21-85e1-6768877d6e62,"+
            "92381159-da14-435d-b570-c451ad9560fc,"+
            "cbfcd579-973e-4100-b221-7c24740c2d8a,"+
            "ce4cffae-6cc0-4c40-bd2b-f86212e86192,"+
            "c1ea582f-db56-4548-9364-11ea1f9beda5,"+
            "360c8402-4a30-48fa-9750-1fe9ad24e75b,"+
            "470bf358-4279-497c-9b1b-c550debfefb3,"+
            "2f6eaef2-8fb2-4223-86d6-1d1e8a2865cf,"+
            "7138b0ed-9eb5-4060-b0f4-27350bf3d4da,"+
            "7fac1084-1a62-450c-b85e-041c844ccab7,"+
            "8da70b7f-e243-47ec-9563-0427a517a45b,"+
            "90dd0f72-3293-4ebf-8ab7-9c89c6499385,"+
            "e57f31c9-65df-4701-a4a6-282c89b51a17,"+
            "9410faec-4210-4559-badd-58d6e2f61f7a,"+
            "da94e54e-cb73-4682-9664-ad73c81702c6,"+
            "1b208dcf-bc1c-4e6c-bcbc-2e57255dc798,"+
            "82f23b29-3567-4e27-852d-0700a2f38c44,"+
            "3003239f-1240-431a-91ee-727b9358c9d0,"+
            "7427098e-0121-40e7-87d2-0bd922400633,"+
            "d6f3ade1-c049-47d1-8a23-1ec65644251f,"+
            "98824a52-75e9-4e3e-b97f-255ecc12cd7c,"+
            "a43c14d5-6fbc-433d-a76d-81db55a146f1,"+
            "c3d4bafe-e3a8-47fe-89bb-4118a3a74139,"+
            "7ba73628-e6a9-4468-837d-1ed6019ac4c6,"+
            "ed8ac2d6-7918-4606-8a47-1bfcec077cbd,"+
            "fd27448e-f7f7-4335-8d6a-e58ef2f65cf6,"+
            "51d51d38-228c-4c1f-adae-27a6a55da811,"+
            "6d68bd88-0705-4834-a28d-47959eefff17,"+
            "f0016aaa-50b5-470e-a558-7461032f4dbc,"+
            "fb859a19-99f5-4efa-8e61-0f2f2752bff3,"+
            "fc44aac0-dab2-461a-85aa-c9df8b8fe5ce,"+
            "103dec6f-3d2d-41eb-afe7-a6105b0a104f,"+
            "4d6eae90-78cf-49cc-9ef3-ccf6a4d00164,"+
            "e53222e8-11d6-48ec-9e75-13b5c08a824a,"+
            "ef433eb7-cf25-4e42-9ab9-a153bd628836,"+
            "4c97e921-d0b4-4432-9b61-8a57ae26a294,"+
            "89fada95-73d2-437a-8701-9a0ed7c2e51a,"+
            "e153137a-248f-4401-9235-1c60b32a9d5e,"+
            "ce390715-7150-4e61-bb8d-0f795de39225,"+
            "f92785d8-06aa-4276-991e-398dbf2e96b3,"+
            "70dece11-fcba-450f-95d7-dca2d5978b49,"+
            "c5bc3751-4355-48eb-bede-5a44d07e66f7,"+
            "1f6369a3-22d9-4d21-a12c-11eb3a06c067,"+
            "e9629d75-3509-4ef8-b590-8e0c64334968,"+
            "f35571a7-7812-45e4-94a0-21ff20b3978e,"+
            "37bdb87c-f85e-4221-8a09-8b7e4600cb66,"+
            "54729336-91c7-4cfa-b457-ee14222e11d9,"+
            "e6752295-6751-4c89-94d9-272ec38c23ed,"+
            "0a2e85c2-cf7e-4e60-819f-fd9a77162994,"+
            "f1b341aa-7c00-459c-8692-420167df6b41,"+
            "fce6dfe1-20b7-45a4-9168-e750f65be7f6,"+
            "bd7d0931-7549-40ef-8328-a883d04b3539,"+
            "f666c3ef-2a87-40ac-a063-c8e6195d0522,"+
            "8fc9fcd5-f333-4fdd-ae62-a63d7c376ab5,"+
            "64f372cc-00e1-40ef-9d4a-31f4e03f0e8f,"+
            "57640714-6594-4a2a-bedd-7decadf20ec7,"+
            "5bf50505-2594-4125-8461-bc2fa4415aa4,"+
            "a81db381-07a3-4d97-a037-f8b2436b1e83,"+
            "77cee242-454e-4523-810e-c5bbc571eab6,"+
            "b6ee57cb-16fa-4d68-9f94-ee3bdf38269b,"+
            "cc8fae99-c779-4cad-813a-f324e7ebcd92,"+
            "8d5e7f31-c554-4e42-a395-5497b283bd85,"+
            "a5268b1e-9fb7-4176-9e11-6345c6281c4c,"+
            "430e3116-b3d1-46ec-9815-fb64a241466e,"+
            "2cd28213-4e69-4ac7-8252-785992a74959,"+
            "7dc4f07c-260e-4a26-899c-cb05e026bac2,"+
            "da698fb9-a471-481e-a03d-8d070cd44a4f,"+
            "fb3d4b65-f11c-4c20-b54e-56a010813114,"+
            "c50e3b78-e057-40d6-8a45-cd5781e48e40,"+
            "0b336e1c-a77f-4c66-8132-583aee5eb978,"+
            "01187973-0630-4fc7-9b3e-57bf8c694ed7,"+
            "6aee0073-6931-4a54-b9f7-aa2b0db5e2fc,"+
            "8a49fad7-e79d-43fd-85fe-a7e5397c64c9,"+
            "8d29b1fd-a0b6-4203-874d-02362411994a,"+
            "dd86fa26-425b-42f4-ac66-8ab09475e5b6,"+
            "a1d3526b-3e69-4ce5-b8d0-3bdf35ed9391,"+
            "f70655d3-8b15-45b0-963f-43f3e8a8df1b,"+
            "680af23a-73f7-4551-88d4-b40ad8c1f61e,"+
            "cf3bc097-b7c8-474e-b031-aacaca7340ca,"+
            "ac976c81-6cc1-4e29-b51d-8231b9af080c,"+
            "49ba531f-881c-44e6-8354-0cbec2184d83,"+
            "533a805c-41aa-4f6f-b195-513a9f8c48aa,"+
            "067798ca-51ac-4792-9090-8ba841485c69,"+
            "27c5c3ff-9999-4704-9423-57e47f0d31d3,"+
            "0ebb5890-3697-4880-a49d-72b328178849,"+
            "e6e652b4-568e-4a8f-916c-a84be0e55b7f,"+
            "69d5a5b6-3540-41ee-8be4-ee0eb04e4786,"+
            "9027177f-368c-4008-8161-7e6bf95b16c0,"+
            "3b54ca6b-0650-4c46-b1f2-2c7d6cfff486,"+
            "336a911e-9a36-4d07-ad31-ae7cb21ca6eb,"+
            "4dc3181a-21ba-4f9b-b9d1-d6d89eeb2e36,"+
            "f1f99022-e5fc-4143-b0e7-58dd515593e2,"+
            "ab935c46-6e3d-4ad4-8f2d-56e12e6875eb,"+
            "eb3b373f-db58-4874-b252-9617ad2a0584,"+
            "dffe0033-0062-4d14-8447-c62b7976d446,"+
            "f0207ba3-8a5e-401d-bd80-87fc3518610b,"+
            "61e61197-3ce3-4e02-bb1a-796c99b3a596,"+
            "ff65febe-0021-4f24-8964-2488d5e7cca8,"+
            "ffdafaaf-3b0c-45e6-bc9a-07b6099ee3a7,"+
            "6ade374e-9d01-41bd-85ad-44e6c37f6511,"+
            "5f0bd0b1-7627-4a0c-bb53-acc78740d39a,"+
            "fe79ba26-2017-4890-913e-64320bf5c9ed,"+
            "bd8c8a29-8f65-4d41-b461-467ceec1a33b,"+
            "1ab5637b-c367-41b1-996f-b08437cd1bd8,"+
            "e8552fb6-ae5d-4acf-979d-e2f35245b60c,"+
            "63bec0ed-92d2-4c92-b551-0107c059761e,"+
            "297e8d4a-1727-4c4b-9897-c41c01a0233f,"+
            "f5668cc7-e016-485a-bbba-bdba26323120,"+
            "c158659b-d694-4a00-bf27-31703a273d5d,"+
            "22c8e857-9688-4b5d-949d-32b6c596f8ab,"+
            "37ca257c-d518-42bb-9f2d-d381befad443,"+
            "8dd64876-1f13-4b87-90df-10adc4f52993,"+
            "4025b3e8-6618-4457-a488-764727290266,"+
            "0eadf705-c359-4f85-bb6e-2ee35c0ed52c,"+
            "673e0a9a-220a-4836-8c2e-12a10adcc825,"+
            "536364c1-9d17-4a65-99e7-924fc86e458f,"+
            "5d863b8e-f123-4d31-b298-cec439bed94d,"+
            "4c98a5d1-9e22-4f95-96fd-676a6ff16921,"+
            "c1f3e69a-dc60-4fe6-9479-ade43d6aa4bb,"+
            "744695a8-3ca4-498d-80b5-603276643b86,"+
            "de922b64-b058-46c1-9fc4-945e69400eaa,"+
            "f54f2f69-6638-41e7-8b31-e0ad75346ee5,"+
            "640c16aa-bb6e-4ec8-b118-3237696e6d40,"+
            "b04639f9-996d-49b9-94cb-89aa70513f55,"+
            "681d8d78-01ad-4ebc-86b0-e19a4bfb1a4d,"+
            "96e7869e-b267-4afe-b308-dda7d7aabb9e,"+
            "54b914de-3103-4712-9cfa-fe556bc34bba,"+
            "d0d06cdd-ff3b-41ed-b8b9-888f30738ec5,"+
            "67ef467f-4404-4440-ba7c-3131e101a983,"+
            "cca8b78e-341b-4ef5-b35e-890e8fef3247,"+
            "2b229a6c-c8e3-4f48-9b77-2d4eca7bcf8c,"+
            "afa3714c-9b31-44b8-ae65-b189bff44bd0,"+
            "61d71795-5f92-4a9b-85f5-5cafa4c10829,"+
            "09b2a632-774c-458a-8011-d793174823b7,"+
            "ab60edf1-e0f8-47b6-9694-e5bf7052b12a,"+
            "6268490c-378e-451f-8f9f-613c0ffcf8f0,"+
            "b48153d2-11e5-4483-ac36-623617540c4b,"+
            "c6e35bb6-a2ab-4fe7-a0e8-4e041cb5632e,"+
            "f149e281-083f-4e1b-91f9-d61e09b52fb2,"+
            "2534f412-bc85-4813-98cc-2baf7c2ed255,"+
            "d2344d49-14f5-4fca-b276-0b7be7493781,"+
            "c6c63435-01db-4179-8ac1-ea54f1a67acf,"+
            "93ed41e5-ca53-4fe3-923f-74ffb398dda2,"+
            "e41216d8-b348-42da-8320-488018cb64e2,"+
            "8b35a347-d062-4fcb-9669-bc967a82a730,"+
            "3211bd6f-8dae-47dd-b996-6a004d10637d,"+
            "becea7cb-91e9-42f4-86f1-b4306bce7cc1,"+
            "cf19dfe3-68fa-4ddb-9850-6728b6097b6e,"+
            "36fad817-4738-4261-9e2c-627da34d3a90,"+
            "c6f9da5c-5c60-44dd-8fcf-9dabf6d16b5f,"+
            "efa23ad9-e055-4f85-9ff8-21ba43c81fe4,"+
            "87d05929-9cda-4174-8e8d-ade4a1ad6c2d,"+
            "2b675e38-f9a5-4219-a373-1d0386b57197,"+
            "5f0f0637-03f6-48f0-bfbe-81d11e681c3f,"+
            "2080c7f4-35a6-4708-a47b-a0560214bf62,"+
            "1c2f8ba0-380e-4a70-992c-8399724fa0f6,"+
            "709598dc-d5c6-4170-91fc-22e36ec06948,"+
            "b808e674-ec4e-4be3-9310-4c704ffa5eda,"+
            "d06dee1c-dcc5-4f16-aa88-b3cc404bf84a,"+
            "0985b258-13ff-4093-8141-7471a277cf53,"+
            "ca7e4482-7a46-401a-a4b6-c0160aabd194,"+
            "84329f16-58f0-42cc-bfc1-55a413ba2179,"+
            "f745c785-fa92-430c-9843-3023b7eea357,"+
            "6a8888c1-7f75-414f-8b4b-3a62ac3fc1a7,"+
            "c2e4ad65-2b34-42b6-a4a4-5290353b6abf,"+
            "513a703a-cdcb-4402-9e29-1ec376956218,"+
            "ec573246-ce3f-468f-b2f2-98d3dd349d99,"+
            "18e47812-feef-4623-a541-d6a19cb314d0,"+
            "d666c915-3b63-4a57-8520-cf921fefd950,"+
            "178bacbc-6871-4841-ba2c-da5eaa2fcdac,"+
            "221d1271-f33d-46f3-a932-fe7188f395d5,"+
            "355c2221-f847-446d-b3d8-be182f847cdf,"+
            "8b5fc687-0e84-434d-a52b-09edbd383eb1,"+
            "2709851f-2574-41a3-a198-a36918091fb1,"+
            "ce8c76a3-7d65-45d0-9523-739ce1123735,"+
            "eca9b500-9e6a-4d33-bdd9-1bdf5cc794a1,"+
            "25df7a87-5e9b-4a24-bb48-e6f0f020dc79,"+
            "b92420b6-fbcf-4630-ba1d-b0f58e7e2539,"+
            "93baa526-2077-45b8-94f4-f59bb3acac82,"+
            "75e97e4c-6974-4aba-ba66-f76d213a0881,"+
            "7ca7b201-1aa2-48a2-b29c-656b3ea9c41d,"+
            "333fbfdf-cc0a-48a0-9832-db163f128aa2,"+
            "fee43f52-07a4-45c1-bd0c-4359374cef02,"+
            "ac1b271f-97ba-4514-8493-5d0e74406b06,"+
            "c68a2f75-998e-4ab2-8596-8bf65f5a253a,"+
            "cc9304d7-a473-438e-adeb-4e1241d776f2,"+
            "e9be7676-8ea2-4685-a838-86902563a683,"+
            "622d078e-6323-449c-88ef-8907208cc99d,"+
            "9a0ff918-2efb-4b8e-b775-45eabf49617e,"+
            "080342d6-13dc-48b2-8d13-19e275210773,"+
            "998c4b4a-7f96-40a6-a51c-c3e329a7432f,"+
            "5bf50051-d7a4-483f-afc7-980c9a554c4d,"+
            "c11b39ac-4579-4890-a6e0-1397ca2de8e2,"+
            "ae41c55f-7470-4b83-9061-7eabca0a6948,"+
            "748f172e-5647-4ffe-8d35-99db127da9f7,"+
            "dc458acb-2c1e-41bf-b0a6-60bfd3c7162f,"+
            "de6567d6-d7b5-4790-9d11-0d3be9cad278,"+
            "33eea77c-ee41-4049-b815-81bb397d7620,"+
            "1edf43ac-6cec-4e93-bcf7-a9113ee7dbb8,"+
            "d474428e-a0dd-4489-a192-93a9ef18ccfe,"+
            "65b43cfd-9d49-417c-8f57-59aae2993a75,"+
            "df78515e-76a5-4be3-b125-7a2155df1b5c,"+
            "41fc218c-f57d-4c82-84a3-86464f68e336,"+
            "65ca64d5-97ed-404d-adc5-a13c5e06b827,"+
            "d8887379-701f-4f0b-8ccc-9ab046640df4,"+
            "3da52e49-1030-426b-bf88-45ab00519018,"+
            "3b609fbc-7f2b-480b-b26c-05834db43d0b,"+
            "bbfd26c3-0e79-4fb6-968c-4677b633c7a1,"+
            "db9a194c-a3cf-40cb-88c2-828b9c8ef76a,"+
            "8cd19959-8b17-4c99-8b09-8aa52dfa804b,"+
            "f82389c5-8bc5-4433-b8b7-5de6dc781ce8,"+
            "0a4015ce-a8fe-4923-9644-42778e78ff44,"+
            "040cd159-0d6e-47ea-959a-457441ec485c,"+
            "c5510472-010a-4c0e-8f3a-186fd66b41b6,"+
            "126aed6d-aa00-43c9-a7c6-1740b5c38bd4,"+
            "22e7e476-a644-4aac-ae11-7481db4a97fe,"+
            "0e4dd283-541c-4e02-9bba-edc3aae9e9ed,"+
            "48e7bdf4-2cde-4a81-863f-6d0d24565adf,"+
            "bec2a480-d171-4ab4-afea-8f5439eba005,"+
            "75c9ad35-4e38-4f37-ab31-f1f24e5b3da3,"+
            "fe05f07b-6644-449b-9335-cbae1212ef01,"+
            "30d0e30d-cc41-4b5d-bd0f-30e45dc04394,"+
            "b00d321e-d7b3-466f-a0e2-ddc6e9a38346,"+
            "33f9cd04-8355-4912-8518-0651a79ed31c,"+
            "70780be7-c350-4fcc-afd7-8ae8ac2ed9aa,"+
            "62027a61-3c8c-4288-a134-587a45820a2c,"+
            "e94a2766-519e-4b83-8e86-12f02e96cbf3,"+
            "4807a5d1-8057-4cbd-81e6-0ce424ebdf71,"+
            "815261bf-ffbd-49d9-9e81-d1272131c7de,"+
            "ff24b374-b75d-4dac-a50d-b2c10e84e7f8,"+
            "3ca0b3bc-c4c0-4f29-8823-3ceaaf8ec349,"+
            "1c74b57a-2f4c-4b35-9162-97a96da3b057,"+
            "8396db65-227a-4205-96e9-2d97c66ebb4f,"+
            "c35109c5-32db-43d2-b522-80116b3dd589,"+
            "290761b2-62f7-4ac1-8239-2a9cb7368e73,"+
            "436a914d-f147-466d-843d-880a64cb9719,"+
            "7e9a9985-cb2a-4e32-88c1-3e22a9323a99,"+
            "f2ced1f9-d837-4e9f-86e4-ee6e71c4b11c,"+
            "745d5914-f2fe-462f-a676-1ef762790897,"+
            "e8a6bddd-a9da-43ef-b834-23b5d6c3f4c5,"+
            "fa9340ba-1df3-494f-ad3c-852daf340031,"+
            "2099bef4-a26a-478f-9074-379cbb44932e,"+
            "0c5862c5-01cc-42cc-83ae-8d1158b6f400,"+
            "e444d523-6cd6-4e15-98a7-63f7defd40d3,"+
            "cd587f36-7216-481b-8f63-ce1cab50b0bc,"+
            "ca76c7c3-6b4a-4452-bff9-e99a2f9e1abb,"+
            "3b6e92b4-e312-4c84-9429-2db5d3e844d1,"+
            "e6acee27-1d09-4bef-974f-f60288ae16b8,"+
            "6a59c1a2-dfc5-4469-a706-b76497b2f8f6,"+
            "7b034b13-461d-4cb0-a948-a35dce099504,"+
            "b75b2e02-33d6-450d-80b5-0ff4f9ef408a,"+
            "abeb9597-8961-4e36-bcf9-2ed1e5c871af,"+
            "da4f5dab-c950-429a-82e1-ad47650ec08b,"+
            "4e411733-7b54-4a23-bd02-8632ecdaf1bc,"+
            "199f2c42-dad9-4f92-a58e-1e8d8b1bab6d,"+
            "62ffdbf6-bb98-44a5-8f34-3378f426992a,"+
            "bbb0a058-e447-46b6-a808-d35efd5f9841,"+
            "2b506373-1c67-4bff-9118-3ef0e55e744b,"+
            "4389332e-8d51-4558-ac96-2681b8ef09ae,"+
            "cd5022f9-af57-41d5-b684-1e105f684bc4,"+
            "a6e7a940-9895-440c-9847-f19a8a796eca,"+
            "f3707ce5-8b78-4ed3-99dd-d1c376d425fc,"+
            "917ffebb-5c6f-4ffb-ac9b-8a80a4b577e1,"+
            "16035a45-6c01-4675-879a-2c55c7f20015,"+
            "6e70b551-c570-4662-bad0-4076f2ca5161,"+
            "086a04e2-3fd6-4b8a-bc6e-2b3532815261,"+
            "ac7212f1-9f5b-4691-b783-353752c5ccd0,"+
            "5b47e40a-afc8-4197-9a93-e133053fe436,"+
            "e66f9035-996c-40fe-9234-71101436fc15,"+
            "600d9a61-7ea8-4ff6-aa09-4216a13a64fd,"+
            "6ed887d2-23dd-4d06-a567-a5f262403458,"+
            "1f2322fa-ae18-4937-adce-9e44b5a45248,"+
            "d7fb1507-b712-4a79-867c-49984cab8a38,"+
            "c6bb74ff-692f-48e5-b0fe-a81ed42781e5,"+
            "0cc7ff51-dda0-4a15-b705-d2501df5d2b1,"+
            "b347b42c-2c10-4dc8-a584-e0dd1fb92ae2,"+
            "3b386337-f443-4599-a5b9-50003f58d4fc,"+
            "8f875dfa-9260-43a6-831a-6b7e7c03a41d,"+
            "9bef204b-b41f-443a-bcdc-b0c45d0c7523,"+
            "1315bdd1-7e44-4ca4-85ed-e7430f37241f,"+
            "8c3ac251-865f-492a-9b8b-1f6a2bd0d961,"+
            "190a386c-6b2d-4a8f-9138-d7d6f0bbcfd1,"+
            "b353d05e-4acb-44bb-ad6a-699add46e457,"+
            "548b5deb-a71b-481a-9eff-c5e9c8d70f7d,"+
            "4ad0d63a-7ef0-4425-bda6-0947265324bb,"+
            "a7ff0595-2e24-4edd-a00c-218f118fd927,"+
            "1f9d1efe-e508-43e1-9614-949d29f19ae2,"+
            "4add6615-379b-4a62-803e-b28d780b8dd3,"+
            "00a115c6-fed3-4287-85e6-ffb603a96287,"+
            "1a33047d-04a7-4b4f-8d39-a4ed2d2d8f63,"+
            "45dfbc43-e037-4914-8334-102051ea0044,"+
            "478aab9c-0835-480a-b97a-28c088bd4a5a,"+
            "2ecd8c0e-de1b-4894-850f-3436ed9ce5e5,"+
            "70eb9351-0e46-4806-bae1-c5f03eacdb92,"+
            "59f3a655-2b0e-46a2-850e-93d2ed8d5796,"+
            "c18cb927-a1ff-4c6f-94aa-4bdbe5f052ec,"+
            "9075fdab-bd5f-4293-b533-ea7b9ee64276,"+
            "1fd59a9f-9cd2-47aa-9b03-0b81ec6b4a0b,"+
            "e2274230-110d-4fb9-846d-9b5d8bf9e1e4,"+
            "a424645a-482b-4f21-b055-d1d9743572a8,"+
            "12dfd351-74e3-46a2-a44e-9b4db7f91fdb,"+
            "ec2fc033-61a7-4a80-a018-488c4db4174b,"+
            "9228ea93-4f49-43b3-a4a2-b93faeb7df3e,"+
            "7d40058e-5990-4c7e-b5d0-db6567ea75b3,"+
            "2f7a6a4c-2738-41f8-bc8a-8094128b88d0,"+
            "10d2006e-136a-4bce-aaec-33e565c920e5,"+
            "773d89cd-9b4c-4eb1-a390-1a76216a02fb,"+
            "cbb99bf6-a02e-4dfb-963f-f999486ec26f,"+
            "202e7758-fda9-4133-84d7-6f863acb1a4b,"+
            "cf232bb9-459d-407e-acb8-f8c29c905f0c,"+
            "23d32819-b709-4542-8b8f-fd2a24d642f5,"+
            "6c4cf2dc-900f-4587-8694-a040acedd759,"+
            "2d0c8c02-cf47-4c6a-b952-743d31b3b707,"+
            "a4d5b0c8-135c-4a3a-88b2-5fef4e180d05,"+
            "be8414c6-7096-4675-b832-faa94050c350,"+
            "99aeab87-de0b-49fa-a822-1c900eed6f24,"+
            "1c3980b8-0a0a-42b0-9be7-f89bb95e1c34,"+
            "9b5efd9c-9df7-4491-9c6c-20c152546f59,"+
            "b6bf5ce0-3dba-43dc-9d73-395838bd0214,"+
            "690a93d9-8f62-45c3-a022-daa3d9b6ed24,"+
            "9508dc5d-9b34-4fe8-bf12-69cdbdca01bd,"+
            "8492ce9e-31ed-4c17-bd9d-f7aa8372ad8c,"+
            "d9f2e852-571b-4d0c-a868-4fbd4c752033,"+
            "521c8f95-ae3c-44a2-9733-5d620a01ab1f,"+
            "edaa7fea-7cb9-4331-8ebc-d428757b2efc,"+
            "785de0ec-f230-45ca-8266-6a1418f6d07b,"+
            "2e27c683-3827-4c71-adef-83d64aa5cd65,"+
            "96815e9f-3fc8-40bd-8ff0-7481b5a45401,"+
            "17491645-0b17-436e-9860-905a9998acf7,"+
            "9e737deb-14e5-4037-92ec-645f955384fa,"+
            "2f23d954-5754-459c-82aa-7c9fae4852fd,"+
            "b214f300-5280-4f36-8cef-70a87e18388f,"+
            "337e88fc-a259-4cc1-aaf6-bed86c3ab8fc,"+
            "cc377d7a-6774-4027-bdc0-e795e12e4f04,"+
            "017e8148-83d1-4590-b32a-21eb34458c52,"+
            "870bc4a7-1d02-4bf1-8e8d-f33418134a4b,"+
            "38ed2c27-ae43-497f-a80c-1903c4166c07,"+
            "c1924ca1-08d5-4d45-92e3-c1475ade52ba,"+
            "b84cb7e6-a478-497a-8664-e066ab86522e,"+
            "73c4788a-40b4-460b-bcdb-b86e3e8ea53a,"+
            "0182e1af-d3da-4635-9753-c58043191cd0,"+
            "288f1551-3a9c-4ca8-97cd-8ffb95955787,"+
            "18152316-2cf7-4a30-99a6-d13b34f484e5,"+
            "dcfb94f1-651d-4806-8358-9ef5c85d7a31,"+
            "0faaa99f-deb0-43b9-8350-c940eb7869ba,"+
            "52c230c2-06f0-4810-b22b-36f8d30eca28,"+
            "9aac9506-389d-4347-99bd-7cad29ad610e,"+
            "5f88440b-38e3-4bfe-94eb-f2bc165f4aa2,"+
            "e51fba7a-f42b-40e7-97e2-159589c43102,"+
            "d778d92d-c627-4771-ae55-9b2bdd83f929,"+
            "4af6d268-4d8c-4b3f-b37f-9449eeae5b71,"+
            "e93d9302-f5f6-4089-a95b-834bf4ef0bc2,"+
            "115cb8b8-34e7-4824-a118-3dd6f9b26885,"+
            "0c78360d-37e9-492c-b5f9-3d4c9a371a87,"+
            "78451617-0b0a-4483-9f31-529ac0987284,"+
            "cbc31e7d-f99e-4ebc-88ad-ed931fcbedfd,"+
            "0a3a9f46-5ccc-4485-bfa7-2dd37310eff8,"+
            "aa15f2e7-4b65-4364-97d9-5a54b65c49a0,"+
            "f790cb70-3b1d-4823-974f-e3d9a7b5b7ab,"+
            "fae1428b-8f61-4876-8495-c373b40e6f49,"+
            "eac31ac8-1789-41c0-a4e4-d4186fec70c5,"+
            "527f1c57-82ef-492d-940a-a72a26c1b579,"+
            "77ce25d8-365f-4f11-8703-dbf11c9fdb98,"+
            "94dcb647-6e54-47a4-a608-5d5dd0773429,"+
            "a7531add-b3b5-4957-a337-77aafa799024,"+
            "3555942b-527d-4c34-bdc8-4a4986cfce8c,"+
            "855bb651-caf9-4b50-8ff7-dea5abb2185a,"+
            "93f31462-f1b7-4e02-82b8-8084210fb433,"+
            "6f2969d4-4e17-46a4-b0c9-88b26bc5a711,"+
            "7de9214a-0298-4af8-9b13-a233ba4dc8ca,"+
            "59b8ed58-5f9b-4beb-a683-c1321f57cf0d,"+
            "fc20513c-0bdd-4d2e-9d7e-9d8126f11a3e,"+
            "34b38aae-ef38-4a89-8f20-af6ac0775146,"+
            "a3225af5-1eca-48d8-98fc-325a3064d7ce,"+
            "6c5924d7-1c47-45c2-bd6d-4228ea79fbe0,"+
            "55d2f3ae-de70-4c57-a267-c147554e8a06,"+
            "1adacdf7-6760-4da8-bf48-9c548c12614a,"+
            "d12fe42b-5907-45ae-b697-4957d4a1dacb,"+
            "958e9e40-87fa-4848-ae75-8fbb0fb60031,"+
            "baa09475-4eaf-4174-8f21-e7b867dc2052,"+
            "57c16bbe-4f6d-419a-86c7-b152cbde6f0e,"+
            "67fa095d-12eb-4daf-bd12-61613e675ad3,"+
            "370650d1-f2f7-418a-8021-3f84bcb2bf24,"+
            "9b8b9597-176e-4097-a59b-bde542a39208,"+
            "d1c024f3-711f-4836-a481-e336e32ab121,"+
            "a027efdb-9d3a-485d-8465-c12b9da8e6cd,"+
            "6c6a879c-3e44-4345-9a9f-eb6d7bd2e854,"+
            "c7c5d84c-4b78-46b4-abf0-4fdb6e735127,"+
            "b71be094-052a-4c13-9816-ab3238ab4108,"+
            "73c68053-8847-4b55-8ac0-5c0c57b21181,"+
            "0695d6b3-47d5-48c6-98f7-a799ea5f3f89,"+
            "881fc656-00cc-40a8-aa1d-b4709f6c10ed,"+
            "803aaa08-2ba5-4a6b-952e-c264e2118ce7,"+
            "600079cd-a806-42a7-82df-05518adcd5f5,"+
            "e90b8eaa-29ac-46f8-8aa2-615af9344a5c,"+
            "a192370d-db60-44bc-8a4d-a009424b4c41,"+
            "0a0a0405-9e4d-4306-9d9e-199fb9d87373,"+
            "f36bb67c-77d4-4650-ba6f-b0b4c61baf43,"+
            "7c4d885f-1ef5-4b12-a671-d80fd147c2c0,"+
            "a212e657-89f1-40f4-81a1-f34752463bc8,"+
            "4f9d8f20-d929-4846-8903-224106635f8b,"+
            "80100c7a-f064-4ed2-8c4c-9fa8952814fa,"+
            "796da269-ef60-4356-b27b-6f4b64e0ee94,"+
            "dc0c3b26-9294-4b2c-8ef4-01249458fdac,"+
            "60769396-0d9c-44dc-8b23-f79a11089895,"+
            "edd90aad-2f16-4fb7-ab18-a3c0257b9337,"+
            "6b7d8777-fa98-41f1-b1c0-7f1218ac6ec1,"+
            "657d57ec-14af-4dfc-ac54-1f3d087307a4,"+
            "ff991ede-5f43-4ec3-b076-756d9f9dc8d6,"+
            "c5b153db-2f67-48f5-9c25-6b74e3ae70c5,"+
            "cb1e50d7-cd7f-49ea-82a1-49dbf9b51cc2,"+
            "c7d4614b-e6ac-4581-917d-c9e746d9d17f,"+
            "9a6b8208-61a8-4775-a170-1996885cc1ba,"+
            "e2d06089-3007-404f-a211-2cc3cb0318cb,"+
            "e131ced1-1ce7-4292-8b52-fe4caea49637,"+
            "e6e70856-be09-41ea-9061-86867c58d233,"+
            "f656e9c6-3e33-47e5-8bf5-7228a56aa3d8,"+
            "75464ce8-51a1-45b5-88dc-37e17a6f2b48,"+
            "d7b57194-5678-4658-839d-7e364b908ac8,"+
            "4d28fed7-7a11-4a31-817d-0ff81b8a7011,"+
            "e25809e2-0686-4596-901a-68b8fb5bbe4c,"+
            "7e4cd1f7-386a-4248-a97a-2de0226e99de,"+
            "f5338c9d-d16d-4bc3-857a-d68e51052085,"+
            "56b35158-294d-416e-a3c1-6ccf82f65b5e,"+
            "1d0bef67-d3e5-473f-9aad-c020e5c01322,"+
            "9743619a-cb90-4c8e-a20a-7c9dc3cafa6c,"+
            "ac478da8-be75-44b0-8fb9-b4a788bf518b,"+
            "f5168990-5dec-4bc7-a273-484fca583d50,"+
            "5b51e8a8-3636-4941-863f-84bef67fe761,"+
            "e5de5633-f402-4f9e-ba88-47b3dd703efd,"+
            "374b1aca-a443-4dfe-b79d-c5732d5a9731,"+
            "c64fe911-1c91-44dd-bd7c-720c524147c3,"+
            "b56e4f92-b523-49b6-97f1-6737aa3fe5da,"+
            "dfe495f7-5a45-48bf-8a78-01dd1ec7d01c,"+
            "d5ebdd21-27eb-4037-bc9b-b556ff7784be,"+
            "858032c4-147a-4678-8b8f-1fae0c17d35e,"+
            "681c9a20-aaca-4200-9a13-76081ccbd96c,"+
            "5184ab19-eb93-4ed9-afbe-24fc76c1b18e,"+
            "e3bfbd0d-2487-4e78-b1ed-db2e3b0c0e97,"+
            "2ea0ec68-e83d-46b0-aca9-117d746bb9d7,"+
            "4253d063-0a73-4274-ae75-b3ec2b062edd,"+
            "04ed29c3-fa4e-48c0-be48-edf65e964052,"+
            "e79c1994-d53d-4aa3-96ab-b04a057031f3,"+
            "573dd5a4-dc8b-417f-9caf-242664a528c5,"+
            "5517d32a-2795-46b2-81f5-618bf679f7d3,"+
            "4f6b08fc-ec7d-4ca1-9c01-52123bfb85ca,"+
            "004a2ac0-9928-443a-a2d1-8b7920a57bf5,"+
            "fa08cf85-0e76-4616-83b3-3d5434283707,"+
            "0f1efc5f-911a-4459-a4cf-7e95104fa05f,"+
            "8eb8fced-f616-425b-a787-03d9a1ea22c9,"+
            "86b31649-6dc8-413f-a7fc-3681745fed5d,"+
            "c621ab9a-acf9-4ec5-8254-76be60ae0535,"+
            "eb6e7f66-1d0c-484c-b439-11ffaba6977b,"+
            "887f890c-93b4-4aa3-a140-0ca08353aa9c,"+
            "6a613940-f07e-4a28-ae2b-94bfe80a2715,"+
            "2fe76845-17c7-41a9-89eb-34f5e271f9d0,"+
            "e092e7b3-6f38-499f-aed4-9dcec18e6ea2,"+
            "1fd64b12-06ea-4c31-8f91-4a5332cb750e,"+
            "df34356b-6015-4f81-8e4b-a7a3e4366908,"+
            "445fd307-4d60-488a-a4b0-81a309a91ac2,"+
            "d8882fa8-bd13-4c18-9713-9bba7dbddc9d,"+
            "aec74b21-d95c-4044-b630-00efeab9a23e,"+
            "fa291a96-3477-4441-a133-5b1c4cd619a2,"+
            "d06aa34f-0289-4719-8c51-e201884a0008,"+
            "cb11d9ca-3a7d-483f-bf6f-59e68fe45e69,"+
            "88292438-b64e-4fd4-b2c4-30d8431b808a,"+
            "ac3d9d32-89b2-4c20-a499-9ba8c982af55,"+
            "0ade093b-693a-41fa-be3f-09a943c4758b,"+
            "90ad9cbe-b4bb-43f4-97fd-f784ab32f58a,"+
            "b8dafdc2-b06c-45d9-9249-10d82a9df139,"+
            "4c01db4b-8a08-475a-a776-5161feb02d75,"+
            "b55dce47-ea30-48e2-a139-17b8969d3b27,"+
            "8884dafc-1f02-4e79-ae61-473bfcb0460e,"+
            "5a15919b-4b84-4458-85d0-78b0b667242b,"+
            "3c19d24c-07e5-48ea-8464-fae37775605e,"+
            "d99ba87f-8f49-4300-8085-43c7dfd5c6ba,"+
            "b8d7f8b6-aec6-43dc-bde8-df7243690354,"+
            "8e6faba8-d204-4e4c-a5bb-1c4e9caf4a11,"+
            "ef775732-1ddc-4ee7-8691-5e144ca46adb,"+
            "3d61b687-2bc1-4453-ae9a-71735c9c4bbf,"+
            "205c526b-f3d1-4981-86d3-68360b352ef2,"+
            "4d45c677-956f-4523-8a72-ed781c031996";
}