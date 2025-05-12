public class SeaVehicleValidator {
    public static final String[] VALID_COLORS = {"red","green","blue","orange","yellow","purple","pink","black","white","silver","gold"};
    private static final String[] VALID_MANUFACTURERS = {"ACME", "Consolidated Products", "Goliath Inc."};
    public static final String[] GENERAL_VEHICLE_TYPES      = {"sailing vessel","powered vessel"};
    public static final String[] SAILING_VESSEL_SUBTYPES   = {"frigate","schooner","xebec"};
    public static final String[] POWERED_VESSEL_SUBTYPES   = {"jetski","yacht","cargo ship"};
    public static final String SAILING_VESSEL = "sailing vessel";
    public static final String POWERED_VESSEL = "powered vessel";
    public static final String JETSKI = "jetski";
    public static final String PETROL = "petrol";
    public static final String DIESEL = "diesel";
    public static final String YACHT = "yacht";
    public static final String CARGO = "cargo";
    public static final String FRIGATE = "frigate";
    public static final String SCHOONER = "schooner";
    public static final String XEBEC = "xebec";

    public static final int NUMBER_OF_FRIGATE_SAILS = 10;
    public static final int NUMBER_OF_SCHOONER_SAILS = 6;
    public static final int NUMBER_OF_XEBEC_SAILS = 3;


    public static boolean validColor(SeaVehicle seaVehicle) {
        String[] colorList = seaVehicle.color;
        if (colorList == null) {return false;}
        if (colorList.length != 1) {return false;}
        String color = colorList[0];
        return arrayContains(color, VALID_COLORS);
    }

    public static boolean validManufacturer(SeaVehicle seaVehicle) {
        String[] manufacturerList = seaVehicle.manufacturer;
        if (manufacturerList == null) {return false;}
        if (manufacturerList.length != 1) {return false;}
        String manufacturer = manufacturerList[0];
        return arrayContains(manufacturer, VALID_MANUFACTURERS);
    }

    public static boolean validType(SeaVehicle seaVehicle) {
        String[] typeList = seaVehicle.type;
        if (typeList == null) {return false;}
        if (typeList.length != 2) {return false;}
        String generalType = typeList[0];
        String subType = typeList[1];
        return switch (generalType) {
            case SAILING_VESSEL -> arrayContains(subType, SAILING_VESSEL_SUBTYPES);
            case POWERED_VESSEL -> arrayContains(subType, POWERED_VESSEL_SUBTYPES);
            default -> false;
        };
    }

    public static boolean validEngine(SeaVehicle seaVehicle) {
        String[] engineList = seaVehicle.engine;
        String[] typeList = seaVehicle.type;
        if (typeList == null) {return false;}
        String generalType = typeList[0];
        String subType = typeList[1];
        //Specific rules
        if (generalType.equals(SAILING_VESSEL) && engineList == null) {return true;}
        if (generalType.equals(SAILING_VESSEL) && engineList != null) {return false;}
        if (generalType.equals(POWERED_VESSEL) && engineList == null) {return false;}
        String fuelType = engineList[1];
        if (subType.equals(JETSKI)) {return fuelType.equals(PETROL);}
        if (subType.equals(YACHT)) {return (fuelType.equals(PETROL) || fuelType.equals(DIESEL));}
        if (subType.equals(CARGO)) {return fuelType.equals(DIESEL);}
        //Does not check engine manufacturer
        return true;
    }

    public static boolean validSails(SeaVehicle seaVehicle) {
        //Does not check sail manufacturer
        String[] sails = seaVehicle.sails;
        String[] typeList = seaVehicle.type;
        if (typeList == null) {return false;}
        if (typeList.length != 2) {return false;}
        if (sails.length != 3) {return false;}
        String generalType = typeList[0];
        String subType = typeList[1];
        String numberSailsString = sails[2].strip();
        int numberOfSails;
        try {
            numberOfSails = Integer.parseInt(numberSailsString);
        } catch (Exception e) {
            return false;
        }
        if (numberOfSails < 0) {return false;}
        if (generalType.equals(POWERED_VESSEL)) {return true;}
        return switch(subType) {
            case FRIGATE -> numberOfSails == NUMBER_OF_FRIGATE_SAILS;
            case SCHOONER -> numberOfSails == NUMBER_OF_SCHOONER_SAILS;
            case XEBEC -> numberOfSails == NUMBER_OF_XEBEC_SAILS;
            default -> false;
        };
    }

    public static boolean isValid(SeaVehicle seaVehicle) {
        boolean validSails = validSails(seaVehicle);
        boolean validColor = validColor(seaVehicle);
        boolean validManufacturer = validManufacturer(seaVehicle);
        boolean validEngine = validEngine(seaVehicle);
        boolean validType = validType(seaVehicle);
        return (validSails && validColor && validManufacturer && validEngine && validType);
    }

    private static boolean arrayContains(Object target, Object[] array) {
        boolean contains = false;
        for (Object x : array) {
            if (x.equals(target)) {
                contains = true;
                break;
            }
        }
        return contains;
    }
}

