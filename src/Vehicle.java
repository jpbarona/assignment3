public abstract class Vehicle {
    public static final String VALUES_SEPARATOR = ",";
    public static final String SPACE = " ";
    public static final String KEY_VALUE_SEPARATOR = ":";

    public static final String VEHICLE_ID_STRING = "vehicle_id";

    public abstract void printVehicle();
    public abstract int getVehicleId();

    static String[] parseValue(String valueString) {
        String[] valuesRaw = valueString.split(VALUES_SEPARATOR);
        String[] valuesStripped = new String[valuesRaw.length];
        for (int i = 0; i < valuesRaw.length; i++) {
            String rawValue = valuesRaw[i];
            valuesStripped[i] = rawValue.strip();
        }
        return valuesStripped;
    }

    public static void printLineString(String KEY_STRING, String[] values) {
        String valuesString = constructValueString(values);
        String lineString = KEY_STRING + KEY_VALUE_SEPARATOR + SPACE + valuesString;
        System.out.println(lineString);
    }

    private static String constructValueString(String[] values) {
        String valuesString = "";
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            if (i == 0) {
                if (i == values.length-1) {
                    //This branch gets executed when there is only one value, hence no separator is required
                    valuesString += value;
                }
                else {
                    valuesString += value + VALUES_SEPARATOR;
                }
                continue;
            }
            if (i == values.length - 1) {
                valuesString += SPACE + value;
                continue;
            }
            else {
                valuesString += SPACE + value + VALUES_SEPARATOR;
            }

        }
        return valuesString;
    }
}
