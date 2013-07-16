package com.urbanairship.digitalwallet.client;


public class PassToolsSystem extends PassToolsClient {

    public static boolean isServiceUp() {

        try {

            String url = PassTools.API_BASE + "/system/status";
            PassToolsResponse response = _rawGet(url);

            if (response.getResponseCode()>=200 || response.getResponseCode()<300){
                return true;
            }

            return false;


        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
