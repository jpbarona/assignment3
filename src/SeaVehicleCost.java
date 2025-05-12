public class SeaVehicleCost {
    public static final String JETSKI = "jetski";
    public static final String PETROL = "petrol";
    public static final String DIESEL = "diesel";
    public static final String YACHT = "yacht";
    public static final String CARGO = "cargo ship";
    public static final String FRIGATE = "frigate";
    public static final String SCHOONER = "schooner";
    public static final String XEBEC = "xebec";

    public static final String CANVAS = "canvas";
    public static final String NYLON = "nylon";
    public static final String MYLAR = "mylar";
    public static final int CANVAS_COST = 500;
    public static final int NYLON_COST = 350;
    public static final int MYLAR_COST = 900;
    public static final int PETROL_COST = 1000;
    public static final int DIESEL_COST = 2000;
    public static final int FRIGATE_COST = 100000;
    public static final int XEBEC_COST = 5000;
    public static final int SCHOONER_COST = 10000;
    public static final int JETSKI_COST = 1000;
    public static final int YACHT_COST = 50000;
    public static final int CARGO_COST = 100000;

    public static int typeCost(SeaVehicle seaVehicle) {
        String[] typeList = seaVehicle.type;
        if (typeList == null) {return 0;}
        if (typeList.length < 2) {return 0;}
        String subType     = typeList[1];
        return switch(subType) {
            case FRIGATE -> FRIGATE_COST;
            case XEBEC -> XEBEC_COST;
            case SCHOONER -> SCHOONER_COST;
            case JETSKI -> JETSKI_COST;
            case YACHT -> YACHT_COST;
            case CARGO -> CARGO_COST;
            default -> 0;
        };
    }

    public static int engineCost(SeaVehicle seaVehicle) {
        String[] engineList = seaVehicle.engine;
        if (engineList == null) {return 0;}
        if (engineList.length < 2) {return 0;}
        String fuelType = engineList[1];
        return switch(fuelType) {
            case PETROL -> PETROL_COST;
            case DIESEL -> DIESEL_COST;
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
            case CANVAS -> CANVAS_COST;
            case NYLON -> NYLON_COST;
            case MYLAR -> MYLAR_COST;
            default -> 0;
        };
        return costPerSail * numberOfSails;
    }

    public static int computeCost(SeaVehicle seaVehicle) {
        int typeCost = typeCost(seaVehicle);
        int engineCost = engineCost(seaVehicle);
        int sailCost = sailCost(seaVehicle);
        return typeCost +  engineCost + sailCost;
    }
}

