package com.passtools.client;


import com.passtools.client.data.LocationInfo;
import com.passtools.client.exception.InternalServerException;
import com.passtools.client.exception.InvalidParameterException;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/* manages the address book of a user */
public class Location extends PassToolsClient {

    public static Long create(LocationInfo locationInfo) {
        try {

            LocationInfo.validate(locationInfo);


            String url = PassTools.API_BASE + "/location";


            Map formFields = new HashMap<String, Object>();
            formFields.put("json", locationInfo.toJSON());

            PassToolsResponse response = post(url, formFields);


            JSONObject jsonObjResponse = response.getBodyAsJSONObject();

            LocationInfo createdLocationInfo = LocationInfo.fromJSON(jsonObjResponse);


            if (createdLocationInfo != null) {
                return createdLocationInfo.id;
            } else {
                throw new InternalServerException("please check response info! ");
            }

        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
