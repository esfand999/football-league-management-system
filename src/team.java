import java.io.*;

public class team implements Serializable {
    private String teamID;
    private String teamName;
    private String owner;
    private String teamCoach;

    public team(){};

    public team(String id, String name, String owner, String coach) {
        this.teamID = id;
        this.teamName = name;
        this.owner = owner;
        this.teamCoach = coach;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(int playerID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String firstName) {
        this.teamName = teamName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTeamCoach() {
        return teamCoach;
    }

    public void setTeamCoach(String club) {
        this.teamCoach = teamCoach;
    }


    public String toString() {
        return "\nTeam ID: " + getTeamID() + "\nTeam Name: " + getTeamName() + "\nTeam Manager: " + getOwner() + "\nCoach: " + getTeamCoach();
    }
}