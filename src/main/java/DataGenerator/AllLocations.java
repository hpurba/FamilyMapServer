package DataGenerator;

import java.util.ArrayList;
import java.util.Random;

public class AllLocations {

    ArrayList<Location> data = new ArrayList<>();

    public Location getRandomLocation() {
        Random random = new Random();
        int numLocations = data.size();
        int index = random.nextInt(data.size());
        return data.get(index);
    }
}
