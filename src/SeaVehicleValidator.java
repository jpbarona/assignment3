public class SeaVehicleValidator {
    public static final String[] VALID_COLORS = {"red","green","blue","orange","yellow","purple","pink","black","white","silver","gold"};
    public static final String SILVER = "silver";

    private static final String[] VALID_MANUFACTURERS = {"ACME", "Consolidated Products", "Goliath Inc."};
    public static final String[] GENERAL_VEHICLE_TYPES      = {"sailing vessel","powered vessel"};
    public static final String[] SAILING_VESSEL_SUBTYPES   = {"frigate","schooner","xebec"};
    public static final String[] POWERED_VESSEL_SUBTYPES   = {"jetski","yacht","cargo ship"};
    public static final String[] ALLOWED_MATERIALS   = {"canvas","nylon","mylar"};
    public static final String SAILING_VESSEL = "sailing vessel";
    public static final String POWERED_VESSEL = "powered vessel";
    public static final String JETSKI = "jetski";
    public static final String PETROL = "petrol";
    public static final String DIESEL = "diesel";
    public static final String YACHT = "yacht";
    public static final String CARGO = "cargo ship";
    public static final String FRIGATE = "frigate";
    public static final String SCHOONER = "schooner";
    public static final String XEBEC = "xebec";

    public static final int NUMBER_OF_FRIGATE_SAILS = 10;
    public static final int NUMBER_OF_SCHOONER_SAILS = 6;
    public static final int NUMBER_OF_XEBEC_SAILS = 3;
    public static final String ACME = "ACME";
    public static final String PINK = "pink";
    public static final String CARGO_SHIP = "cargo ship";


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
        if (generalType.equalsIgnoreCase(SAILING_VESSEL) && engineList == null) {return true;}
        if (generalType.equalsIgnoreCase(SAILING_VESSEL) && engineList != null) {return false;}
        if (generalType.equalsIgnoreCase(POWERED_VESSEL) && engineList == null) {return false;}
        if (engineList.length < 2) {return false;}
        String fuelType = engineList[1];
        if (subType.equalsIgnoreCase(JETSKI)) {return fuelType.equalsIgnoreCase(PETROL);}
        if (subType.equalsIgnoreCase(YACHT)) {return (fuelType.equalsIgnoreCase(PETROL) || fuelType.equalsIgnoreCase(DIESEL));}
        if (subType.equalsIgnoreCase(CARGO)) {return fuelType.equalsIgnoreCase(DIESEL);}
        //Does not check engine manufacturer
        return true;
    }

    public static boolean validSails(SeaVehicle seaVehicle) {
        //Does not check sail manufacturer
        String[] sails = seaVehicle.sails;
        String[] typeList = seaVehicle.type;
        if (typeList == null) {return false;}
        if (typeList.length != 2) {return false;}
        String generalType = typeList[0];
        if (generalType.equalsIgnoreCase(POWERED_VESSEL)) {return sails == null;}
        if (sails.length != 3) {return false;}
        String subType = typeList[1];
        String numberSailsString = sails[2].strip();
        int numberOfSails;
        try {
            numberOfSails = Integer.parseInt(numberSailsString);
        } catch (Exception e) {
            return false;
        }
        String material = sails[1];
        if (!arrayContains(material, ALLOWED_MATERIALS)) {return false;}
        if (numberOfSails < 1) {return false;}
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
        boolean additionalRules = additionalValidationRules(seaVehicle);
        return (validSails && validColor && validManufacturer && validEngine && validType && additionalRules);
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

    public static boolean additionalValidationRules(SeaVehicle seaVehicle) {
        String[] typeList = seaVehicle.type;
        String[] sailsList = seaVehicle.sails;
        String[] colorList = seaVehicle.color;
        String[] manufacturerList = seaVehicle.manufacturer;
        if (typeList == null) {return false;}
        if (typeList.length != 2) {return false;}
        if (colorList == null) {return false;}
        if (colorList.length != 1) {return false;}
        if (manufacturerList == null) {return false;}
        if (manufacturerList.length < 1) {return false;}
        String color = colorList[0];
        String generalType = typeList[0];
        String subType = typeList[1];
        String manufacturer = manufacturerList[0];
        //Rule 1: Schooners cannot be silver
        if (subType.equalsIgnoreCase(SCHOONER)) {return !color.equalsIgnoreCase(SILVER);}
        //Rule 2: Xebecs must be made by ACME
        if (subType.equalsIgnoreCase(XEBEC)) {return manufacturer.equalsIgnoreCase(ACME);}
        //Rule 3: Jet Skis must be pink
        if (subType.equalsIgnoreCase(JETSKI)) {return color.equalsIgnoreCase(PINK);}
        //Rule 4: Cargo ship
        if (subType.equalsIgnoreCase(CARGO_SHIP)) {return manufacturer.equalsIgnoreCase(ACME);}
        return true;
    }
}

