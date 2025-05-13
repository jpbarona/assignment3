public class MotorVehicleCost {

    public static final String CAR = "car";
    public static final String HATCHBACK = "hatchback";
    public static final int HATCHBACK_COST = 8_000;
    public static final String SEDAN = "sedan";
    public static final int SEDAN_COST = 12_000;
    public static final String CONVERTIBLE = "convertible";
    public static final int CONVERTIBLE_COST = 20_000;
    public static final String PICKUP = "pickup";
    public static final int PICKUP_COST = 20_000;
    public static final String EIGHTEEN_WHEELER = "eighteen wheeler";
    public static final int EIGHTEEN_WHEELER_COST = 35_000;
    public static final String SPORT = "sport";
    public static final int SPORT_COST = 16_000;
    public static final String TOURING = "touring";
    public static final int TOURING_COST = 9_000;
    public static final String TRUCK = "truck";
    public static final String MOTORCYCLE = "motorcycle";
    public static final String PETROL = "petrol";
    public static final int PETROL_COST = 1000;
    public static final String DIESEL = "diesel";
    public static final int DIESEL_COST = 2000;
    public static final String ELECTRIC = "electric";
    public static final int ELECTRIC_COST = 5000;
    public static final int SUMMER_WHEELS_COST = 100;
    public static final int WINTER_WHEELS_COST = 120;
    public static final int ALL_WEATHER_WHEELS_COST = 150;
    public static final String SUMMER = "summer";
    public static final String WINTER = "winter";
    public static final String ALL_WEATHER = "all-weather";
    public static final String CONSOLIDATED_PRODUCTS = "Consolidated Products";
    public static final int CONSOLIDATED_PRODUCTS_WHEELS_VEHICLE_DISCOUNT = 573;
    public static final int ELECTRIC_VEHICLE_DISCOUNT = 1116;
    public static final String GOLIATH_INC = "Goliath Inc.";
    public static final int GOLIATH_SUMMER_TIRES_SURCHARGE = 587;
    public static final int GOLIATH_ENGINE_CAR_DISCOUNT = 554;

    public static int typeCost(MotorVehicle motorVehicle) {
        String[] typeList = cleanseArrayStrings(motorVehicle.getType());
        if (typeList == null) {return 0;}
        if (typeList.length<2) {return 0;}
        String generalType = typeList[0].toLowerCase();
        String subType     = typeList[1].toLowerCase();
        if (generalType.equals(CAR)) {
            return switch (subType) {
                case HATCHBACK -> HATCHBACK_COST;
                case SEDAN -> SEDAN_COST;
                case CONVERTIBLE -> CONVERTIBLE_COST;
                default -> 0;
            };
        }
        if (generalType.equals(TRUCK)) {
            return switch (subType) {
                case PICKUP -> PICKUP_COST;
                case EIGHTEEN_WHEELER -> EIGHTEEN_WHEELER_COST;
                default -> 0;
            };
        }
        if (generalType.equals(MOTORCYCLE)) {
            return switch(subType) {
                case SPORT -> SPORT_COST;
                case TOURING -> TOURING_COST;
                default -> 0;
            };
        }
        return 0;
    }

    public static int engineCost(MotorVehicle motorVehicle) {
        String[] engineList = cleanseArrayStrings(motorVehicle.getEngine());
        if (engineList == null) {return 0;}
        if (engineList.length < 2) {return 0;}
        String fuelType = engineList[1];
        return switch (fuelType) {
            case PETROL -> PETROL_COST;
            case DIESEL -> DIESEL_COST;
            case ELECTRIC -> ELECTRIC_COST;
            default -> 0;
        };
    }

     public static int wheelsCost(MotorVehicle motorVehicle) {
        String[] wheelsList = motorVehicle.getWheels();
        int numberOfWheels;
        try {
        numberOfWheels = Integer.parseInt(wheelsList[2]);
        } catch (Exception e) {
            return 0;
        }
        if (wheelsList == null) {return 0;}
        if (wheelsList.length < 2) {return 0;}
        String wheelType = wheelsList[1];
        return switch(wheelType) {
            case SUMMER -> SUMMER_WHEELS_COST * numberOfWheels;
            case WINTER -> WINTER_WHEELS_COST * numberOfWheels;
            case ALL_WEATHER -> ALL_WEATHER_WHEELS_COST * numberOfWheels;
            default -> 0;
        };
     }

     public static int computeVehicleCost(MotorVehicle motorVehicle) {
        int typeCost = typeCost(motorVehicle);
        int engineCost = engineCost(motorVehicle);
        int wheelsCost = wheelsCost(motorVehicle);
        int additionalRequirements = additionalCostRequirements(motorVehicle);
        return typeCost + engineCost + wheelsCost + additionalRequirements;
     }

    private static String[] cleanseArrayStrings(String[] original) {
        if (original == null) {return new String[0];}
        String[] cleaned = new String[original.length];
        for (int i = 0; i < original.length; i++) {
            cleaned[i] = original[i].strip().toLowerCase();
        }
        return cleaned;
    }

    public static int additionalCostRequirements(MotorVehicle motorVehicle) {
        String[] wheelsList = motorVehicle.wheels;
        String[] typeList = motorVehicle.type;
        String[] colorList = motorVehicle.color;
        String[] manufacturerList = motorVehicle.manufacturer;
        String[] engineList = motorVehicle.getEngine();
        if (wheelsList == null) {return 0;}
        if (wheelsList.length < 3) {return 0;}
        if (typeList == null) {return 0;}
        if (typeList.length < 2) {return 0;}
        if (colorList == null) {return 0;}
        if (colorList.length < 1) {return 0;}
        if (manufacturerList == null) {return 0;}
        if (manufacturerList.length < 1) {return 0;}
        if (engineList == null) {return 0;}
        if (engineList.length < 1) {return 0;]
        int discount = 0;
        String wheelsManufacturer = wheelsList[0];
        String wheelsType = wheelsList[1];
        String generalType = typeList[0];
        String subType = typeList[1];
        String color = colorList[0];
        String manufacturer = manufacturerList[0];
        String engineManufacturer = engineList[0];
        //Rule 1: Cars manufactured by consolidated products with wheels from Consolidated products get a discount of 573
        if (generalType.equalsIgnoreCase(CAR)
                && manufacturer.equalsIgnoreCase(CONSOLIDATED_PRODUCTS)
                && wheelsManufacturer.equalsIgnoreCase(CONSOLIDATED_PRODUCTS))
            {discount += -CONSOLIDATED_PRODUCTS_WHEELS_VEHICLE_DISCOUNT;}
        //Rule 2: Electric cars get a discount of 1116
        if (subType.equalsIgnoreCase(ELECTRIC)) {discount += -ELECTRIC_VEHICLE_DISCOUNT;}
        //Rule 3: Vehicles by Goliath Inc. with summer tires incur a surcharge of 587
        if (manufacturer.equalsIgnoreCase(GOLIATH_INC) && wheelsType.equalsIgnoreCase(SUMMER))
            {discount += GOLIATH_SUMMER_TIRES_SURCHARGE;}
        //Rule 4: Cars manufactured by Goliath Inc. with an engine from the same manufacturer gets a discount of 554
        if (generalType.equalsIgnoreCase(CAR) &&
            manufacturer.equalsIgnoreCase(GOLIATH_INC) &&
            engineManufacturer.equalsIgnoreCase(GOLIATH_INC))
            {discount += -GOLIATH_ENGINE_CAR_DISCOUNT;}

        return discount;
    }

}
