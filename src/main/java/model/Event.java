package model;

public class Event {
    private String eventID;
    private String associatedUsername;
    private String personID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    public Event(String eventID, String username, String personID, float latitude, float longitude,
                 String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Get the Event ID
     * @return eventID
     */
    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    /**
     * Get the user's name
     * @return associatedUsername
     */
    public String getUsername() {
        return associatedUsername;
    }


    public void setUsername(String username) {
        this.associatedUsername = username;
    }

    /**
     * Get the Person's ID
     * @return personID
     */
    public String getPersonID() {
        return personID;
    }


    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Get the Latitude
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }


    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * Get the Longitude
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }


    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the Country
     * @return country
     */
    public String getCountry() {
        return country;
    }



    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get the City
     * @return city
     */
    public String getCity() {
        return city;
    }



    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get Event Type
     * @return eventType
     */
    public String getEventType() {
        return eventType;
    }


    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * Get the Year
     * @return year
     */
    public int getYear() {
        return year;
    }


    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Event) {
            Event oEvent = (Event) o;
            return oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getUsername().equals(getUsername()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    oEvent.getLatitude() == (getLatitude()) &&
                    oEvent.getLongitude() == (getLongitude()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear() == (getYear());
        } else {
            return false;
        }
    }
}
