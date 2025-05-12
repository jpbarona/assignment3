public class MotorVehicleValidator {
    private static final String[] VALID_COLORS = {"red", "green", "blue", "orange", "yellow", "purple", "pink", "black", "white", "silver", "gold"};
    private static final String[] VALID_MANUFACTURERS = {"ACME", "Consolidated Products", "Goliath Inc."};

    private static final String[] GENERAL_VEHICLE_TYPES = {"car", "truck", "motorcycle"};
    private static final String[] CAR_SUBTYPES = {"hatchback", "sedan", "convertible"};
    private static final String[] TRUCK_SUBTYPES = {"pickup", "eighteen wheeler"};
    private static final String[] VALID_FUELTYPES = {"diesel", "petrol", "electric"};
    private static final String[] MOTORCYCLE_SUBTYPES =  {"sport", "touring"};
    private static final String[] VALID_WHEEL_TYPES = {"winter", "summer", "all-weather"};
    public static final Integer[] PICKUP_NUMBER_OF_WHEELS = {4,6};

    private static final String CAR = "car";
    private static final String TRUCK = "truck";
    private static final String MOTORCYCLE = "motorcycle";
    private static final String ELECTRIC = "electric";
    private static final String PETROL = "petrol";
    public static final String PICKUP = "pickup";

    private static final int WHEELS_LIST_LENGTH = 3;
    private static final int ENGINE_LIST_LENGTH = 2;
    private static final int TYPE_LIST_LENGTH = 2;

    public static final String EIGHTEEN_WHEELER = "eighteen wheeler";
    public static final String ACME = "ACME";
    public static final String ALL_WEATHER = "all-weather";
    public static final String SEDAN = "sedan";
    public static final String PURPLE = "purple";
    public static final String BLACK = "black";

    public static boolean validVehicleId(MotorVehicle motorVehicle) {
        return motorVehicle.getVehicleId() > 0;
    }

    public static boolean validColor(MotorVehicle motorVehicle) {
        String[] colorList = motorVehicle.getColor();
        if (colorList.length != 1) {return false;}
        return (arrayContains(colorList[0].toLowerCase(), VALID_COLORS));
    }

    public static boolean validManufacturer(MotorVehicle motorVehicle) {
        String[] manufacturerList = motorVehicle.getManufacturer(); //Case sensitive, do not cleanse
        if (manufacturerList.length != 1) {return false;}
        return (arrayContains(manufacturerList[0], VALID_MANUFACTURERS));
    }

    public static boolean validType(MotorVehicle motorVehicle) {
        String[] typeList = motorVehicle.getType();

        if (typeList.length != TYPE_LIST_LENGTH) {return false;}

        String generalType = typeList[0];
        String subType = typeList[1];

        if (!arrayContains(generalType, GENERAL_VEHICLE_TYPES)) {return false;}
        if (generalType.equalsIgnoreCase(CAR)) {return arrayContains(subType, CAR_SUBTYPES);}
        if (generalType.equalsIgnoreCase(TRUCK)) {return arrayContains(subType, TRUCK_SUBTYPES);};
        if (generalType.equalsIgnoreCase(MOTORCYCLE)) {return arrayContains(subType, MOTORCYCLE_SUBTYPES);}

        return false;
    }

    public static boolean validEngine(MotorVehicle motorVehicle) {
        String[] engineList = motorVehicle.getEngine();

        if (engineList.length != ENGINE_LIST_LENGTH) {return false;}

        String engineManufacturer = engineList[0].strip();
        String fuelType = engineList[1].strip();
        String vehicleType = motorVehicle.getType()[0];

        if (!arrayContains(engineManufacturer, VALID_MANUFACTURERS)) {return false;}
        if (vehicleType.equals(TRUCK) && fuelType.equals(ELECTRIC)) {return false;}
        if (vehicleType.equals(MOTORCYCLE) && !fuelType.equals(PETROL)) {return false;}

        return arrayContains(fuelType, VALID_FUELTYPES);
    }

    public static boolean validWheels(MotorVehicle motorVehicle) {
        String[] wheelsList = motorVehicle.getWheels();

        if (wheelsList.length != WHEELS_LIST_LENGTH) {return false;}

        String wheelsManufacturer = wheelsList[0];
        String wheelType = wheelsList[1];

        if (!arrayContains(wheelsManufacturer, VALID_MANUFACTURERS)) {return false;}
        if (!arrayContains(wheelType, VALID_WHEEL_TYPES)) {return false;}

        String numberWheelsString = wheelsList[2].strip();
        int numberOfWheels;
        try {
            numberOfWheels = Integer.parseInt(numberWheelsString);
        } catch (Exception e) {
            return false;
        }

        String vehicleType = motorVehicle.getType()[0];
        String vehicleSubType = motorVehicle.getType()[1];
        if (vehicleType.equals(CAR) && numberOfWheels != 4) {return false;}
        if (vehicleSubType.equals(PICKUP) && !arrayContains(numberOfWheels, PICKUP_NUMBER_OF_WHEELS)) {return false;}
        if (vehicleSubType.equals(EIGHTEEN_WHEELER) && numberOfWheels != 18) {return false;}
        if (vehicleType.equals(MOTORCYCLE) && numberOfWheels != 2) {return false;}

        return true;
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


    public static boolean fieldsNotNull(MotorVehicle motorVehicle) {
        return motorVehicle.vehicleId        != MotorVehicle.DEFAULT_VEHICLE_ID
                && motorVehicle.color        != null
                && motorVehicle.engine       != null
                && motorVehicle.wheels       != null
                && motorVehicle.type         != null
                && motorVehicle.manufacturer != null;
    }

    public static boolean additionalRequirements(MotorVehicle motorVehicle) {
        String[] wheelsList = motorVehicle.wheels;
        String[] typeList = motorVehicle.type;
        String[] colorList = motorVehicle.color;
        String[] manufacturerList = motorVehicle.manufacturer;
        if (wheelsList == null) {return false;}
        if (wheelsList.length < 3) {return false;}
        if (typeList == null) {return false;}
        if (typeList.length < 2) {return false;}
        if (colorList == null) {return false;}
        if (colorList.length < 1) {return false;}
        if (manufacturerList == null) {return false;}
        if (manufacturerList.length < 1) {return false;}
        String wheelsManufacturer = wheelsList[0];
        String wheelsType = wheelsList[1];
        String generalType = typeList[0];
        String subType = typeList[1];
        String color = colorList[0];
        String manufacturer = manufacturerList[0];
        //Rule 1: Motorcycles cannot have wheels manufactured from ACME
        if (generalType.equalsIgnoreCase(MOTORCYCLE)) {return !wheelsManufacturer.equalsIgnoreCase(ACME);}
        //Rule 2: Eighteen wheelers must use all-weather tires
        if (subType.equalsIgnoreCase(EIGHTEEN_WHEELER)) {return wheelsType.equalsIgnoreCase(ALL_WEATHER);};
        //Rule 3: Sedans cannot be purple
        if (subType.equalsIgnoreCase(SEDAN)) {return !color.equalsIgnoreCase(PURPLE);}
        //Rule 4: Pickup trucks by ACME cannot be black
        if (subType.equalsIgnoreCase(PICKUP) && manufacturer.equalsIgnoreCase(ACME)) {return !color.equalsIgnoreCase(BLACK);}
        return true;
    }

    public static boolean isValid(MotorVehicle motorVehicle) {
        if (!fieldsNotNull(motorVehicle)) {
            return false;
        }

        boolean idOk          = validVehicleId(motorVehicle);
        boolean colorOk       = validColor(motorVehicle);
        boolean mfgOk         = validManufacturer(motorVehicle);
        boolean typeOk        = validType(motorVehicle);
        boolean engineOk      = validEngine(motorVehicle);
        boolean wheelsOk      = validWheels(motorVehicle);
        boolean fieldsNotNull = fieldsNotNull(motorVehicle);

        boolean allSemantics = idOk && colorOk && mfgOk && typeOk && engineOk && wheelsOk;

        boolean additionalRequirements = additionalRequirements(motorVehicle);

        return allSemantics && fieldsNotNull && additionalRequirements;
    }

    public static void main(String[] args) {
        MotorVehicle testMotorVehicle = new MotorVehicle();
        testMotorVehicle.vehicleId    = 1;
        testMotorVehicle.color        = new String[]{ "silver" };
        testMotorVehicle.manufacturer = new String[]{ "ACME" };
        testMotorVehicle.type         = new String[]{ "car", "sedan" };
        testMotorVehicle.engine       = new String[]{ "ACME", "diesel" };
        testMotorVehicle.wheels       = new String[]{ "ACME", "all-weather", "4" };

        boolean idOk       = MotorVehicleValidator.validVehicleId(testMotorVehicle);
        System.out.println("Id check:           " + idOk);

        boolean colorOk    = MotorVehicleValidator.validColor(testMotorVehicle);
        System.out.println("Color check:        " + colorOk);

        boolean mfgOk      = MotorVehicleValidator.validManufacturer(testMotorVehicle);
        System.out.println("Manufacturer check: " + mfgOk);

        boolean typeOk     = MotorVehicleValidator.validType(testMotorVehicle);
        System.out.println("Type check:         " + typeOk);

        boolean engineOk   = MotorVehicleValidator.validEngine(testMotorVehicle);
        System.out.println("Engine check:       " + engineOk);

        boolean wheelsOk   = MotorVehicleValidator.validWheels(testMotorVehicle);
        System.out.println("Wheels check:       " + wheelsOk);

        boolean allSemantics = idOk && colorOk && mfgOk && typeOk && engineOk && wheelsOk;
        System.out.println("Overall valid?      " + allSemantics);

        boolean valid = isValid(testMotorVehicle);
        System.out.println("Is valid:           " + valid);

    }
}
