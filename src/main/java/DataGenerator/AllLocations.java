package DataGenerator;

import java.util.ArrayList;
import java.util.Random;

public class AllLocations {

    ArrayList<Location> locationArrayList = new ArrayList<>();

    public Location getRandomLocation() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(locationArrayList.size());
        return locationArrayList.get(index);
    }
}
