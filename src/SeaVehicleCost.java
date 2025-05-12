public class SeaVehicleCost {
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
    public static final String CARGO = "cargo ship";
    public static final String FRIGATE = "frigate";
    public static final String SCHOONER = "schooner";
    public static final String XEBEC = "xebec";

    public static final int NUMBER_OF_FRIGATE_SAILS = 10;
    public static final int NUMBER_OF_SCHOONER_SAILS = 6;
    public static final int NUMBER_OF_XEBEC_SAILS = 3;

    public static int typeCost(SeaVehicle seaVehicle) {
        String[] typeList = seaVehicle.type;
        if (typeList == null) {return 0;}
        if (typeList.length < 2) {return 0;}
        String subType     = typeList[1];
        return switch(subType) {
            case FRIGATE -> 100000;
            case XEBEC -> 5000;
            case SCHOONER -> 10000;
            case JETSKI -> 1000;
            case YACHT -> 50000;
            case CARGO -> 100000;
            default -> 0;
        };
    }

    public static int engineCost(SeaVehicle seaVehicle) {
        String[] engineList = seaVehicle.engine;
        if (engineList == null) {return 0;}
        if (engineList.length < 2) {return 0;}
        String fuelType = engineList[1];
        return switch(fuelType) {
            case PETROL -> 1000;
            case DIESEL -> 2000;
            default -> 0;
        };
    }

    public static int sailCost(SeaVehicle seaVehicle) {
        String[] sailsList = seaVehicle.sails;
        if (sailsList == null) {return 0;}
        if (sailsList.length < 3) {return 0;}
        String sailMaterial = sailsList[1];
        int numberOfSails = Integer.parseInt(sailsList[2]);
        int costPerSail = switch (sailMaterial) {
            case "canvas" -> 500;
            case "nylon" -> 350;
            case "mylar" -> 900;
            default -> 0;
        };
        return costPerSail * numberOfSails;
    }

    public static int computeCost(SeaVehicle seaVehicle) {
        int typeCost = typeCost(seaVehicle);
        int engineCost = engineCost(seaVehicle);
        int sailCost = sailCost(seaVehicle);
        int totalCost = typeCost +  engineCost + sailCost;
        return totalCost;
    }
}

