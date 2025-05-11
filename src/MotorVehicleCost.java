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

    public static int typeCost(MotorVehicle motorVehicle) {
        String[] typeList = cleanseArrayStrings(motorVehicle.getType());
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
        int numberOfWheels = Integer.parseInt(wheelsList[2]);
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
        return typeCost + engineCost + wheelsCost;
     }

    private static String[] cleanseArrayStrings(String[] original) {
        if (original == null) {return new String[0];}
        String[] cleaned = new String[original.length];
        for (int i = 0; i < original.length; i++) {
            cleaned[i] = original[i].strip().toLowerCase();
        }
        return cleaned;
    }

}
