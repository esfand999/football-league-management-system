import java.io.Serializable;
import java.util.ArrayList;

public class manageTeam implements Serializable {
    private String teamID;
    private ArrayList<player> players;

    public manageTeam(String teamID, ArrayList<player> players){
        this.teamID=teamID;
        this.players=players;
    };


    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public ArrayList<player> getPlayers() {
        this.players=players;
        return players;
    }

    public void setPlayers(ArrayList<player> players) {
        this.players = players;
    }


    public String toString() {
        String str = "\nTeam ID: " + getTeamID();
        for(int i =0; i< getPlayers().size(); i++) {
            str+="\n"+ getPlayers().get(i).toString();
        }
        str += "\n \t\t-----------------------------------------------------";
        return str;
    }
}

