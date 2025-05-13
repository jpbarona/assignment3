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
    public static final String ACME = "ACME";
    public static final int ACME_CARGO_SHIP_DISCOUNT = 5782;
    public static final int MYLAR_SCHOONER_DISCOUNT = 2149;
    public static final String[] JETSKI_COLORS_FOR_DISCOUNT = {"gold", "purple", "orange"};
    public static final int JETSKI_COLOR_DISCOUNT = 440;
    public static final String POWERED_VESSEL = "powered vessel";
    public static final int POWERED_VESSELS_PETROL_DISCOUNT = 792;

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
        int numberOfSails;
        try{
            numberOfSails = Integer.parseInt(sailsList[2]);
        } catch (Exception e) {
            return 0;
        }
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
        int additionalCost = additionalCostRules(seaVehicle);
        return typeCost +  engineCost + sailCost + additionalCost;
    }

    public static int additionalCostRules(SeaVehicle seaVehicle) {
        String[] typeList = seaVehicle.type;
        String[] colorList = seaVehicle.color;
        String[] manufacturerList = seaVehicle.manufacturer;
        if (typeList == null) {return 0;}
        if (typeList.length != 2) {return 0;}
        if (colorList == null) {return 0;}
        if (colorList.length != 1) {return 0;}
        if (manufacturerList == null) {return 0;}
        if (manufacturerList.length < 1) {return 0;}
        int discount = 0;
        String color = colorList[0];
        String generalType = typeList[0];
        String subType = typeList[1];
        String manufacturer = manufacturerList[0];
        //Rule 1: ACME Cargo ships receive a 5782 discount
        if (subType.equalsIgnoreCase(CARGO) && manufacturer.equalsIgnoreCase(ACME)) {discount+= -ACME_CARGO_SHIP_DISCOUNT;}
        //Rule 2: Schooners with mylar sails receive a 2149 discount
        if (subType.equalsIgnoreCase(SCHOONER)) {
            String[] sails = seaVehicle.sails;
            if (sails == null) {return 0;}
            if (sails.length < 3) {return 0;}
            String material = sails[1];
            if (material.equalsIgnoreCase(MYLAR)) {
                discount+= -MYLAR_SCHOONER_DISCOUNT;
            }
        }
        //Rule 3: Gold, purple and orange jet skis have a discount of 440
        if (subType.equalsIgnoreCase(JETSKI) && arrayContains(color, JETSKI_COLORS_FOR_DISCOUNT)) {discount+= -JETSKI_COLOR_DISCOUNT;}
        //Rule 4: Powered vessels with Petrol engines receive a 792 discount
        if (generalType.equalsIgnoreCase(POWERED_VESSEL)) {
            String[] engineList = seaVehicle.engine;
            if (engineList == null) {return 0;}
            String fuelType = engineList[1];
            if (fuelType.equalsIgnoreCase(PETROL)) {discount+= -POWERED_VESSELS_PETROL_DISCOUNT;}
        }
        return discount;
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


