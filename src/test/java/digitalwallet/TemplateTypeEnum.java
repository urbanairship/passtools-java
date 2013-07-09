package digitalwallet;

import java.util.ArrayList;
import java.util.List;

public enum TemplateTypeEnum {
    BOARDING_PASS(1L, "boardingPass", "Boarding Pass", 1),
    COUPON(2L, "coupon", "Coupon", 1),
    EVENT_TICKET(3L, "eventTicket", "Event Ticket", 1),
    GENERIC(4L, "generic", "Generic", 1),
    STORE_CARD(5L, "storeCard", "Store Card", 1),
    LOYALTY_1(6L, "loyalty1", "Loyalty1", 2),
    LOYALTY_2(7L, "loyalty2", "Loyalty2", 2),
    OFFER_1(8L, "offer1", "Offer1", 2),
    GENERIC_1(9L, "generic1", "Generic1", 2),
    GENERIC_2(10L, "generic2", "Generic2", 2),
    GENERIC_3(11L, "generic3", "Generic3", 2);

    private final Long code;
    private final String jsonName;
    private final String displayName;
    private final int vendorId;

    private static List<TemplateTypeEnum> appleTemplateTypes = null;
    private static List<TemplateTypeEnum> googleTemplateTypes = null;

    private static final Object oSync = new Object();

    private TemplateTypeEnum(Long code, String jsonName, String displayName, int vendorId) {
        this.vendorId = vendorId;
        this.code = code;
        this.jsonName = jsonName;
        this.displayName = displayName;
    }

    public Long getCode() {
        return code;
    }

    public String getJsonName() {
        return jsonName;
    }

    public String getDisplayName() {
        return displayName;
    }


    public int getVendorId() {
        return vendorId;
    }

    public static String jsonKeyName(Long code) {
        for (TemplateTypeEnum current : TemplateTypeEnum.values()) {
            if (current.code.equals(code)) {
                return current.jsonName;
            }
        }

        throw new RuntimeException("invalid template type " + code);
    }

    public static String jsonKeyName(Long code, int vendorId) {
        for (TemplateTypeEnum current : TemplateTypeEnum.values()) {
            if (current.code.equals(code) && (vendorId == current.vendorId)) {
                return current.jsonName;
            }
        }

        switch (vendorId) {
            case 2:
                return LOYALTY_1.jsonName;
            case 1:
            default:
                return GENERIC.jsonName;
        }
    }

    public static String displayName(Long code) {
        for (TemplateTypeEnum current : TemplateTypeEnum.values()) {
            if (current.code.equals(code)) {
                return current.displayName;
            }
        }

        throw new RuntimeException("invalid template type " + code);
    }

    public static TemplateTypeEnum fromJsonKeyName(String json) {
        for (TemplateTypeEnum current : TemplateTypeEnum.values()) {
            if (current.jsonName.equals(json) || current.displayName.equals(json)) {
                return current;
            }
        }

        throw new RuntimeException("invalid template type " + json);
    }

    public static TemplateTypeEnum fromCode(Long code) {
        for (TemplateTypeEnum current : TemplateTypeEnum.values()) {
            if (current.code.equals(code)) {
                return current;
            }
        }

        throw new RuntimeException("invalid template code " + code);
    }

    public static TemplateTypeEnum fromCode(Long code, int vendorId) {
        for (TemplateTypeEnum current : TemplateTypeEnum.values()) {
            if (current.code.equals(code) && (current.vendorId == vendorId)) {
                return current;
            }
        }

        switch (vendorId) {
            case 2:
                return LOYALTY_1;
            case 1:
            default:
                return GENERIC;
        }
    }

    public static TemplateTypeEnum getRandomAppleTemplateType() {
        List<TemplateTypeEnum> types = getAppleTemplateTypes();
        int ndx = (int)(Math.random() * types.size());
        return types.get(ndx);

    }

    public static TemplateTypeEnum getRandomGoogleTemplateType() {
        List<TemplateTypeEnum> types = getGoogleTemplateTypes();
        int ndx = (int)(Math.random() * types.size());
        return types.get(ndx);
    }

    private static List<TemplateTypeEnum> getGoogleTemplateTypes() {
        synchronized (oSync) {
            if (googleTemplateTypes == null) {
                googleTemplateTypes = new ArrayList<TemplateTypeEnum>();

                for (TemplateTypeEnum current : TemplateTypeEnum.values()) {
                    if (current.getVendorId() == 2) {
                        googleTemplateTypes.add(current);
                    }
                }
            }

            return googleTemplateTypes;
        }
    }

    private static List<TemplateTypeEnum> getAppleTemplateTypes() {
        synchronized (oSync) {
            if (appleTemplateTypes == null) {
                appleTemplateTypes = new ArrayList<TemplateTypeEnum>();

                for (TemplateTypeEnum current : TemplateTypeEnum.values()) {
                    if (current.getVendorId() == 1) {
                        appleTemplateTypes.add(current);
                    }
                }
            }

            return appleTemplateTypes;
        }
    }
}
