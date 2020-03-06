package model;

public class Person {
    private String personID;            // Unique identifier for this person (non-empty string)
    private String associatedUsername;  // User (UserName) to which this person belongs
    private String firstName;           // model.Person’s first name (non-empty string)
    private String lastName;            // model.Person’s last name (non-empty string)
    private String gender;              // model.Person’s gender (string: f or m)
    private String fatherID;            // model.Person ID of person’s father (possibly null)
    private String motherID;            // model.Person ID of person’s mother (possibly null)
    private String spouseID;            // model.Person ID of person’s spouse (possibly null)

    public Person(String personID, String associatedUsername, String firstName, String lastName,
                  String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }


    // Functions    (setters and getters)

    // personID
    public void setPersonID(String personID) {
        this.personID = personID;
    }
    public String getPersonID(){ return personID; }

    // associatedUserName
    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }
    public String getAssociatedUsername() { return associatedUsername; }

    // firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() { return firstName; }

    // lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }

    // gender
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return gender;
    }

    // fatherID
    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }
    public String getFatherID() {
        return fatherID;
    }

    // motherID
    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }
    public String getMotherID() {
        return motherID;
    }

    // spouseID
    public void setSpouseID(String spouseID) { this.spouseID = spouseID; }
    public String getSpouseID() { return spouseID; }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Person) {
            Person oPerson = (Person) o;
            return oPerson.getPersonID().equals(getPersonID()) &&
                    oPerson.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getGender().equals(getGender()) &&
                    oPerson.getFatherID().equals(getFatherID()) &&
                    oPerson.getMotherID().equals(getMotherID()) &&
                    oPerson.getSpouseID().equals(getSpouseID());
        } else {
            return false;
        }
    }
}