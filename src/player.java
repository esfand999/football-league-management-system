import java.io.*;

public class player implements Serializable {
    private String playerID;
    private String firstName;
    private String lastName;
    private String salary;
    private String position;
    private String country;
    private String status;
    public player(){};

    public player(String id, String firstName, String lastName, String position, String salary, String country, String status) {
        this.playerID = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.country = country;
        this.status = status;
    }

    public String getPlayerID() {
        return playerID;
    }
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }


    public String toString() {
        return "\n\nPlayerID : " + getPlayerID() + "\nName : " + getFirstName()+" "+ getLastName() + "\nPlayer's Salary: "
                + getSalary() + "\nCountry: " + getCountry()+ "\nStatus: " +getStatus();
    }
}