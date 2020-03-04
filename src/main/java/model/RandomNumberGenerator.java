package model;

import java.util.UUID;

public class RandomNumberGenerator {
    public String generate() {
        long rand = UUID.randomUUID().getLeastSignificantBits();
        String id = String.valueOf(rand);
        return id.toString();
    }
}
