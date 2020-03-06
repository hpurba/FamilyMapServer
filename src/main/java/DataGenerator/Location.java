package DataGenerator;

public class Location {
    private String country;
    private String city;
    private float latitude;
    private float longitude;

    public void setCountry(String country) { this.country = country; }
    public String getCountry() { return country; }

    public void setCity(String city) { this.city = city; }
    public String getCity() { return city; }

    public void setLatitude(float latitude) { this.latitude = latitude; }
    public float getLatitude() { return latitude; }

    public void setLongitude(float longitude) { this.longitude = longitude; }
    public float getLongitude() { return longitude; }
}
