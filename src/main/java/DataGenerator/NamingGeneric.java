package DataGenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 * This will pick a random String in an ArrayList of names
 */
public class NamingGeneric {
    ArrayList<String> data = new ArrayList<>(); // data is given to us for female names, male names, and surnames

    public String pickRandomName() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(data.size());
        return data.get(index);
    }
}
