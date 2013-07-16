package digitalwallet;

import com.urbanairship.digitalwallet.client.Template;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;


/*
 */
public class TestData {


    public static Long createTemplate(String externalId, boolean checkExistence) {
        Template t = null;
        if (checkExistence) {
            try {
                t = Template.getTemplate(externalId);
                if (t.getId() != null) {
                    return t.getId();
                }
            } catch (RuntimeException e) {

            }
        }

        try {
            JSONParser p = new JSONParser();
            JSONObject json = (JSONObject) p.parse(templateString);
            Map<String, Object> headers = (Map<String, Object>) json.get("headers");
            Map<String, Object> fields = (Map<String, Object>) json.get("fields");
            return Template.createTemplate(externalId, "name_test", "desc_test", "boardingPass", headers, fields);
        } catch (Exception e) {
            return null;
        }

    }


    private static boolean isHeader(Object val) {
        if (val instanceof JSONObject) {
            Map<String, Object> fields = (Map<String, Object>) val;
            String fieldType = (String) fields.get("fieldType");
            if (StringUtils.isBlank(fieldType)) {
                return false;
            }
            if (fieldType.equals("barcode") || fieldType.equals("image") || fieldType.equals("topLevel") || fieldType.equals("passTop")) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    private static void parseHeadersAndFields(Map<String, Object> headers, Map<String, Object> fields, JSONObject json) {
        Map<String, Object> map = (Map<String, Object>) json;
        for (Map.Entry<String, Object> current : map.entrySet()) {
            Object val = current.getValue();
            HashMap<String, Object> v = (HashMap<String, Object>) current;
            if (isHeader(v)) {
                headers.putAll(v);
            } else {
                fields.putAll(v);
            }
        }
    }


    public static String getCreateTemplateData() {
        return templateString;
    }


    public static JSONObject getCreateTemplateHeaders() {
        if (createTemplateJson == null) {
            return null;
        }
        return (JSONObject) createTemplateJson.get("headers");

    }

    public static JSONObject getCreateTemplateFields() {
        if (createTemplateJson == null) {
            return null;
        }
        return (JSONObject) createTemplateJson.get("fields");
    }


    private static String templateString = "{\n" +
            "        \"headers\": {\n" +
            "            \"barcodeAltText\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"barcode\",\n" +
            "                \"value\": \"123456789\"\n" +
            "            },\n" +
            "            \"icon_image\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"topLevel\",\n" +
            "                \"value\": \"https://s3.amazonaws.com/passtools-localhost/1/images/3c0f1c994b46cfc032147893ed4a00ba75a0d428_logo@2x.png\"\n" +
            "            },\n" +
            "            \"transitType\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"passTop\",\n" +
            "                \"value\": \"transitTypeAir\"\n" +
            "            },\n" +
            "            \"footer_image\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"image\",\n" +
            "                \"value\": \"https://s3.amazonaws.com/passtools-localhost/1/images/default-pass-footer.png\"\n" +
            "            },\n" +
            "            \"logo_image\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"topLevel\",\n" +
            "                \"value\": \"https://s3.amazonaws.com/passtools-localhost/1/images/default-pass-logo.png\"\n" +
            "            },\n" +
            "            \"barcode_label\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"barcode\",\n" +
            "                \"value\": \"Member ID\"\n" +
            "            },\n" +
            "            \"logo_color\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"topLevel\",\n" +
            "                \"value\": \"rgb(24,86,148)\"\n" +
            "            },\n" +
            "            \"logo_text\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"topLevel\",\n" +
            "                \"value\": \"Atlantis Airlines11\"\n" +
            "            },\n" +
            "            \"barcode_value\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"barcode\",\n" +
            "                \"value\": \"123456789\"\n" +
            "            },\n" +
            "            \"barcode_encoding\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"barcode\",\n" +
            "                \"value\": \"iso-8859-1\"\n" +
            "            },\n" +
            "            \"barcode_type\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"barcode\",\n" +
            "                \"value\": \"PKBarcodeFormatPDF417\"\n" +
            "            },\n" +
            "            \"background_color\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"topLevel\",\n" +
            "                \"value\": \"rgb(0,147,201)\"\n" +
            "            },\n" +
            "            \"foreground_color\": {\n" +
            "                \"formatType\": 1,\n" +
            "                \"fieldType\": \"topLevel\",\n" +
            "                \"value\": \"rgb(255,255,255)\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"fields\": {\n" +
            "            \"Merchant Website\": {\n" +
            "                \"formatType\": \"URL\",\n" +
            "                \"changeMessage\": \"\",\n" +
            "                \"order\": 2,\n" +
            "                \"fieldType\": \"back\",\n" +
            "                \"value\": \"http://www.test.com\",\n" +
            "                \"label\": \"Merchant Website\",\n" +
            "                \"required\": false,\n" +
            "                \"hideEmpty\": false\n" +
            "            },\n" +
            "            \"Seat\": {\n" +
            "                \"formatType\": \"String\",\n" +
            "                \"changeMessage\": \"\",\n" +
            "                \"order\": 2,\n" +
            "                \"fieldType\": \"secondary\",\n" +
            "                \"textAlignment\": \"textAlignmentNatural\",\n" +
            "                \"value\": \"26A\",\n" +
            "                \"label\": \"Seat\",\n" +
            "                \"required\": false,\n" +
            "                \"hideEmpty\": false\n" +
            "            },\n" +
            "            \"Class\": {\n" +
            "                \"formatType\": \"String\",\n" +
            "                \"changeMessage\": \"\",\n" +
            "                \"order\": 3,\n" +
            "                \"fieldType\": \"auxiliary\",\n" +
            "                \"textAlignment\": \"textAlignmentNatural\",\n" +
            "                \"value\": \"Econ\",\n" +
            "                \"label\": \"Class\",\n" +
            "                \"required\": false,\n" +
            "                \"hideEmpty\": false\n" +
            "            },\n" +
            "            \"Terminal Gate\": {\n" +
            "                \"formatType\": \"Number\",\n" +
            "                \"changeMessage\": \"\",\n" +
            "                \"order\": 1,\n" +
            "                \"numberStyle\": \"numberStyleDecimal\",\n" +
            "                \"fieldType\": \"header\",\n" +
            "                \"textAlignment\": \"textAlignmentNatural\",\n" +
            "                \"value\": 11,\n" +
            "                \"label\": \"Terminal Gate\",\n" +
            "                \"required\": false,\n" +
            "                \"hideEmpty\": false\n" +
            "            },\n" +
            "            \"Arrival Airport\": {\n" +
            "                \"formatType\": \"String\",\n" +
            "                \"changeMessage\": \"\",\n" +
            "                \"order\": 2,\n" +
            "                \"fieldType\": \"primary\",\n" +
            "                \"textAlignment\": \"textAlignmentNatural\",\n" +
            "                \"value\": \"SFO\",\n" +
            "                \"label\": \"Arrival Airport\",\n" +
            "                \"required\": false,\n" +
            "                \"hideEmpty\": false\n" +
            "            },\n" +
            "            \"Ticket #\": {\n" +
            "                \"formatType\": \"Number\",\n" +
            "                \"changeMessage\": \"\",\n" +
            "                \"order\": 3,\n" +
            "                \"numberStyle\": \"numberStyleDecimal\",\n" +
            "                \"fieldType\": \"secondary\",\n" +
            "                \"textAlignment\": \"textAlignmentNatural\",\n" +
            "                \"value\": 384013,\n" +
            "                \"label\": \"Ticket #\",\n" +
            "                \"required\": false,\n" +
            "                \"hideEmpty\": false\n" +
            "            },\n" +
            "            \"Flight #\": {\n" +
            "                \"formatType\": \"String\",\n" +
            "                \"changeMessage\": \"\",\n" +
            "                \"order\": 2,\n" +
            "                \"fieldType\": \"auxiliary\",\n" +
            "                \"textAlignment\": \"textAlignmentNatural\",\n" +
            "                \"value\": \"G6A\",\n" +
            "                \"label\": \"Flight #\",\n" +
            "                \"required\": false,\n" +
            "                \"hideEmpty\": false\n" +
            "            },\n" +
            "            \"Passenger\": {\n" +
            "                \"formatType\": \"String\",\n" +
            "                \"changeMessage\": \"\",\n" +
            "                \"order\": 1,\n" +
            "                \"fieldType\": \"secondary\",\n" +
            "                \"textAlignment\": \"textAlignmentNatural\",\n" +
            "                \"value\": \"First Last\",\n" +
            "                \"label\": \"Passenger\",\n" +
            "                \"required\": false,\n" +
            "                \"hideEmpty\": false\n" +
            "            },\n" +
            "            \"Departure Airport\": {\n" +
            "                \"formatType\": \"String\",\n" +
            "                \"changeMessage\": \"\",\n" +
            "                \"order\": 1,\n" +
            "                \"fieldType\": \"primary\",\n" +
            "                \"textAlignment\": \"textAlignmentLeft\",\n" +
            "                \"value\": \"LAX\",\n" +
            "                \"label\": \"Departure Airport\",\n" +
            "                \"required\": true,\n" +
            "                \"hideEmpty\": false\n" +
            "            },\n" +
            "            \"Boarding Pass Details\": {\n" +
            "                \"formatType\": \"String\",\n" +
            "                \"changeMessage\": \"\",\n" +
            "                \"order\": 1,\n" +
            "                \"fieldType\": \"back\",\n" +
            "                \"value\": \"Some information about how this boarding pass works and how to use it.\\n\\nAdditional terms and support information.\",\n" +
            "                \"label\": \"Boarding Pass Details\",\n" +
            "                \"required\": false,\n" +
            "                \"hideEmpty\": false\n" +
            "            },\n" +
            "            \"Depart Time\": {\n" +
            "                \"formatType\": \"String\",\n" +
            "                \"changeMessage\": \"\",\n" +
            "                \"order\": 1,\n" +
            "                \"fieldType\": \"auxiliary\",\n" +
            "                \"textAlignment\": \"textAlignmentNatural\",\n" +
            "                \"value\": \"10:40 AM\",\n" +
            "                \"label\": \"Depart Time\",\n" +
            "                \"required\": false,\n" +
            "                \"hideEmpty\": false\n" +
            "            }\n" +
            "        }\n" +
            "        \"projectType\": \"boardingPass\",\n" +
            "        \"type\": \"Boarding Pass\",\n" +
            "        \"vendorId\": 1,\n" +
            "        \"description\": \"Test1 With ExternalId\",\n" +
            "        \"name\": \"Test with ExternalId\",\n" +
            "}";

    private static String boardingPassFields = "{\n" +
            "    \"fields\": {\n" +
            "        \"Passenger\": {\n" +
            "            \"formatType\": \"String\",\n" +
            "            \"changeMessage\": \"Issued today\",\n" +
            "            \"order\": 1,\n" +
            "            \"fieldType\": \"secondary\",\n" +
            "            \"textAlignment\": \"textAlignmentNatural\",\n" +
            "            \"value\": \"Smith Alex\",\n" +
            "            \"label\": \"Passenger\",\n" +
            "            \"required\": false,\n" +
            "            \"hideEmpty\": false\n" +
            "        }\n" +
            "    }\n" +
            "}";

    private static String boardingPassUpdateFields11 = "\"Terminal Gate\":{\n" +
            "         \"formatType\":\"Number\",\n" +
            "         \"changeMessage\":\"\",\n" +
            "         \"order\":1,\n" +
            "         \"numberStyle\":\"PKNumberStyleDecimal\",\n" +
            "         \"fieldType\":\"header\",\n" +
            "         \"textAlignment\":\"textAlignmentNatural\",\n" +
            "         \"value\":21.0,\n" +
            "         \"label\":\"Terminal Gate\",\n" +
            "         \"required\":false,\n" +
            "         \"hideEmpty\":false\n" +
            "      }";

    private static String boardingPassUpdateFields = "{\n" +
            "    \"fields\": {\n" +
            boardingPassUpdateFields11 +
            "    }\n" +
            "}";


    private static JSONObject createTemplateJson = parseCreateTemplateJson(templateString);
    private static Map<String, Object> createPassFields = parseCreatePassJson(boardingPassFields);
    private static Map<String, Object> updatePassFields = parseCreatePassJson(boardingPassUpdateFields);

    private static JSONObject parseCreateTemplateJson(String jsonString) {
        if (jsonString == null) {
            return null;
        }
        try {
            JSONParser p = new JSONParser();
            JSONObject json = (JSONObject) p.parse(templateString);
            return json;
        } catch (Exception e) {
            return null;
        }

    }

    private static Map<String, Object> parseCreatePassJson(String fields) {
        if (fields == null) {
            return null;
        }
        try {
            JSONParser p = new JSONParser();
            JSONObject json = (JSONObject) p.parse(fields);
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, Object> getCreatePassFields() {
        return createPassFields;
    }

    public static Map<String, Object> getUpdatePassFields() {
        return updatePassFields;
    }

}
