import java.io.Serializable;

public class Interface implements Serializable {
    player Player;
    team Team;
    String operation;
    String id;
    String playerList;

    public Interface(String operation) {
        this.operation = operation;
    }

    public Interface(player p, String operation) {
        this.Player = p;
        this.operation = operation;
    }

    public Interface(team t, String operation) {
        this.Team = t;
        this.operation = operation;
    }

    public Interface(String id, String operation) {
        this.operation = operation;
        this.id = id;
    }

    public Interface(String id, String playerList, String operation){
        this.playerList = playerList;
        this.operation = operation;
        this.id = id;
    }


    public player getPlayer() {
        return Player;
    }

    public void setPlayer(player player) {
        Player = player;
    }

    public team getTeam() {
        return Team;
    }

    public void setTeam(team team) {
        Team = team;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerList() {
        return playerList;
    }

    public void setPlayerList(String playerList) {
        this.playerList = playerList;
    }
}
