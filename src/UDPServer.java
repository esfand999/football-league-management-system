import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class UDPServer {
    DatagramSocket socket = null;



    public UDPServer() {
    }

    public void Listener() throws IOException, ClassNotFoundException {


        socket = new DatagramSocket(7777);
        System.out.println("The server is running...");
        byte[] incomingObjectData = new byte[1024];

        while (true) {

            //Object Packet
            DatagramPacket incomingObject = new DatagramPacket(incomingObjectData, incomingObjectData.length);
            socket.receive(incomingObject);
            byte[] objectByte = incomingObject.getData();
            ByteArrayInputStream BAIS = new ByteArrayInputStream(objectByte);
            ObjectInputStream OIS = new ObjectInputStream(BAIS);
            Interface in = (Interface) OIS.readObject();
            //Team functions
            if (in.getOperation().equals("addTeam")) {
                if (addTeam(in.getTeam())) {
                    System.out.println("\nTeam object is written to teamData.ser file");
                } else {
                    System.out.println("\nEntry already exists in the file");
                }
            } else if (in.getOperation().equals("viewAllTeams")) {
                viewAllTeams();
            } else if (in.getOperation().equals("searchTeam")) {
                searchTeam(in.getId());
            }//Player functions
            else if (in.getOperation().equals("addPlayer")) {
                if (addPlayer(in.getPlayer())) {
                    System.out.println("\nPlayer object is written to playerData.ser file");
                } else {
                    System.out.println("\nEntry already exists in the file");
                }

            } else if (in.getOperation().equals("viewAllPlayers")) {
                viewAllPlayers();
            } else if (in.getOperation().equals("searchPlayer")) {
                searchPlayer(in.getId());
            } else if (in.getOperation().equals("addToTeam")) {
                System.out.println("add to team");
                addToTeam(in.getId(), in.getPlayerList());
            } else if (in.getOperation().equals("viewAvailablePlayers")) {
                viewAvailablePlayers();
            } else if (in.getOperation().equals("viewTeamsMgmt")) {
                System.out.println("view team mgm");
                viewTeamsMgmt();
            } else if (in.getOperation().equals("searchMgmtTeam")) {
                System.out.println("searchingMgmtTeam");
                searchMgmtTeam(in.getId());
            }
        }
    }


//-------------------------------------------------- TEAM FUNCTIONS ---------------------------------------------------
    /**Function to add Teams*/
    public boolean addTeam(team t1) {
        int i;
        boolean check = true;
        ArrayList<team> array = readTeamFile();
        if (array.size() == 0) {
            writeObjectToTeamFile(t1);
            System.out.println("Object Written: " + t1.toString());
        } else {
            for (i = 0; i < array.size(); i++) {
                if (t1.getTeamID().equals(array.get(i).getTeamID())) {
                    check=false;
                    break;
                }
            }
            if(check){
                writeObjectToTeamFile(t1);
                System.out.println("Object Written: " + t1.toString());
            }
        }
        return check;
    }

    /**Function view All Teams*/
    public void viewAllTeams() throws IOException {
        ArrayList<team> teamList = readTeamFile();
        String dataSize = Integer.toString(teamList.size());
        //Sending size of data the client should expect to receive
        sendData(dataSize);
        //Sending Team data to Client
        for (int i = 0; i < teamList.size(); i++) {
            sendData(teamList.get(i).toString());
        }
    }

    /**Function to search for a team*/
    public void searchTeam(String id) throws IOException {
        int i;
        boolean check = false;
        ArrayList<team> array = readTeamFile();
        System.out.println("ID RECEIVED BY ME:  "+ id);
        for (i = 0; i < array.size(); i++) {
            if (array.get(i).getTeamID().equals(id)) {
                    check=true;
                    break;
                }
            }
            if(check){
                System.out.println("\nENTRY FOUND");
                team data= array.get(i);
                sendData(data.toString());
            }else{
                String data= "\n\t\t\n\t\tRESPONSE FROM SERVER ----- TEAM RECORD NOT FOUND\n\t\t\n";
                sendData(data);
            }
    }

//----------------------------------------------- TEAM MGMT FUNCTIONS -------------------------------------------------
public void addToTeam(String teamid, String playerid) throws IOException{
    ArrayList<player> allPlayers = readPlayerFile();
    ArrayList<team> allTeams = readTeamFile();
    ArrayList<manageTeam> mngallteams = readTeamMgmtFile();

    ArrayList<player> addedPlayers = new ArrayList<>();

    int i, j, k, r;
    boolean check = false;
    boolean exists = false;

    //Free + Exists
    for(i = 0; i < allPlayers.size(); i++){
        if (allPlayers.get(i).getStatus().equalsIgnoreCase("Free") && allPlayers.get(i).getPlayerID().equals(playerid)){
            check = true;
            break;
        }
    }

    //Team Exists or not
    for(j = 0; j < allTeams.size(); j++){
        if (!allTeams.get(j).getTeamID().equals(teamid)){
            check = false;
        }
        else{
            check = true;
            break;
        }
    }

    //Size of playerList less than 11
    for(k = 0; k < mngallteams.size(); k++){
        if (mngallteams.get(k).getTeamID().equals(teamid) && mngallteams.get(k).getPlayers().size() >= 11){
            check = false;
            break;
        }
    }


    if(check){
        allPlayers.get(i).setStatus("Recruited");
        addedPlayers.add(allPlayers.get(i));

        if(mngallteams.size() == 0){
            manageTeam team = new manageTeam(teamid, addedPlayers);
            writeObjectToTeamMgmtFile(team);
        } else {
            for(r = 0; r < mngallteams.size(); r++){
                if(mngallteams.get(r).getTeamID().equals(teamid)){
                    exists = true;
                    break;
                }
            }
            if(exists){
                mngallteams.get(r).getPlayers().add(allPlayers.get(i));

                ObjectOutputStream outputStream = null;
                try {
                    outputStream = new ObjectOutputStream(new FileOutputStream("teamMgmtData.ser"));
                    for (int i1 = 0; i1 < mngallteams.size() ; i1++) {
                        outputStream.writeObject(mngallteams.get(i1));
                    }
                }catch (IOException exp) {
                    System.out.println("IO Exception while opening file");
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException exp) {
                        System.out.println("IO Exception while closing file");
                    }
                }
            }
            else{
                manageTeam team = new manageTeam(teamid, addedPlayers);
                writeObjectToTeamMgmtFile(team);
            }
        }

        for(int q = 0; q < allPlayers.size(); q++) {
            if(allPlayers.get(q).getPlayerID().equals(playerid)){
//                allPlayers.get(q).setStatus("Recruited");
                ObjectOutputStream outputStream = null;
                try {
                    outputStream = new ObjectOutputStream(new FileOutputStream("playerData.ser"));
                    for (int i1 = 0; i1 <allPlayers.size() ; i1++) {
                        outputStream.writeObject(allPlayers.get(i1));
                    }
                }catch (IOException exp) {
                    System.out.println("IO Exception while opening file");
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException exp) {
                        System.out.println("IO Exception while closing file");
                    }
                }
                System.out.println(allPlayers.get(q).getStatus());
            }
        }
        }
    }


    public void viewAvailablePlayers() throws IOException {
        ArrayList<player> playerList = readPlayerFile();
        int dataSize = 0;

        System.out.println(dataSize);

        //Sending available player data to Client
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getStatus().equals("Free")) {
                dataSize++;
            }
        }
        sendData (Integer.toString(dataSize));

        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getStatus().equals("Free")) {
                sendData(playerList.get(i).toString());
            }
        }

    }


    public void viewTeamsMgmt() throws IOException{

        ArrayList<team> teamList = readTeamFile();
        ArrayList<manageTeam> manageTeamList = readTeamMgmtFile();
        System.out.println(manageTeamList.size()+ " Is my size.");
        String team = "";
        int dataSize = manageTeamList.size();
        sendData(Integer.toString(dataSize));

        for (int i = 0; i < manageTeamList.size(); i++) {
            for(int j = 0; j < teamList.size(); j++){
                if (manageTeamList.get(i).getTeamID().equals(teamList.get(j).getTeamID())){
                    team+= "\nTeam Name : " + teamList.get(j).getTeamName();
                    team += manageTeamList.get(i).toString();
                    sendData(team);
                    break;
                }
                team="";
            }
        }
    }



    public void searchMgmtTeam(String id) throws IOException {
        int i;
        boolean check = false;
        ArrayList<manageTeam> array = readTeamMgmtFile();
        System.out.println("ID RECEIVED BY ME:  "+ id);
        for (i = 0; i < array.size(); i++) {
            if (array.get(i).getTeamID().equals(id)) {
                check=true;
                break;
            }
        }
        if(check){
            System.out.println("\nENTRY FOUND");
            manageTeam data = array.get(i);
            sendData(data.toString());
        }else{
            String data= "\n\t\t\n\t\tRESPONSE FROM SERVER ----- TEAM RECORD NOT FOUND\n\t\t\n";
            sendData(data);
        }
    }

//------------------------------------------------- PLAYER FUNCTIONS --------------------------------------------------
    /**Function to add Player*/
    public boolean addPlayer(player p1) {
        int i;
        boolean check = true;
        ArrayList<player> array = readPlayerFile();
        if (array.size() == 0) {
            writeObjectToPlayerFile(p1);
            System.out.println("Object Written" + p1.toString());
        } else {
            for (i = 0; i < array.size(); i++) {
                if (p1.getPlayerID().equalsIgnoreCase(array.get(i).getPlayerID())) {
                    check=false;
                    break;
                }
            }
            if(check){
                writeObjectToPlayerFile(p1);
                System.out.println("Object Written" + p1.toString());
            }
        }
        return check;
    }

    /**Function view All Players*/
    public void viewAllPlayers() throws IOException {
        ArrayList<player> playerList = readPlayerFile();
        String dataSize = Integer.toString(playerList.size());
        //Sending size of data the client should expect to receive
        sendData(dataSize);
        System.out.println(dataSize);

        //Sending Team data to Client
        for (int i = 0; i < playerList.size(); i++) {
            sendData(playerList.get(i).toString());
        }
    }

    /**Function to search for a player*/
    public void searchPlayer(String id) throws IOException {
        socket.getInetAddress();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        int i=0;
        boolean check = false;
        ArrayList<player> array = readPlayerFile();
        for (i = 0; i < array.size(); i++) {
            if (array.get(i).getPlayerID().equals(id)) {
                check=true;
                break;
            }
        }
        if(check){
            System.out.println("\nENTRY FOUND");
            player data= array.get(i);
            sendData(data.toString());
        }else{
            String data= "\n\t\t\n\t\t\tRESPONSE FROM SERVER ----- PLAYER RECORD NOT FOUND\n\t\t\n";
            sendData(data);
        }


    }

    public void sendData(Object obj) throws IOException {
        socket.getInetAddress();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        os = new ObjectOutputStream(outputStream);
        os.writeObject(obj);
        byte[] sendData = outputStream.toByteArray();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 6666);
        socket.send(sendPacket);
    }

//----------------------------------------------- TEAM HELPER FUNCTIONS -----------------------------------------------

    /**Function to read Objects from file*/
    public static ArrayList<team> readTeamFile() {

        ArrayList<team> teamList = new ArrayList<team>(0);

        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream("teamData.ser"));
            boolean EOF = false;

            while (!EOF) {
                try {

                    team myObj = (team) inputStream.readObject();
                    teamList.add(myObj);
                } catch (ClassNotFoundException e) {
                } catch (EOFException end) {
                    EOF = true;
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                System.out.println("IO Exception while closing file");
            }
        }
        return teamList;
    }


    /**Function to write Objects to file*/
    public void writeObjectToTeamFile(team t) {
        ObjectOutputStream outputStream = null;

        try {
            ArrayList<team> teamList = readTeamFile();
            teamList.add(t);
            outputStream = new ObjectOutputStream(new FileOutputStream("teamData.ser"));

            for (int i = 0; i < teamList.size(); i++) {
                outputStream.writeObject(teamList.get(i));
            }

        } catch (IOException exp) {
            System.out.println("IO Exception while opening file");
        } finally {

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException exp) {
                System.out.println("IO Exception while closing file");
            }
        }
    }

//----------------------------------------------- TEAM HELPER FUNCTIONS -----------------------------------------------

    /**Function to read Objects from file*/
    public static ArrayList<manageTeam> readTeamMgmtFile() {

        ArrayList<manageTeam> teamList = new ArrayList<manageTeam>(0);

        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream("teamMgmtData.ser"));
            boolean EOF = false;

            while (!EOF) {
                try {
                    manageTeam myObj = (manageTeam) inputStream.readObject();
                    teamList.add(myObj);
                } catch (ClassNotFoundException e) {
                } catch (EOFException end) {
                    EOF = true;
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                System.out.println("IO Exception while closing file");
            }
        }
        return teamList;
    }


    /**Function to write Objects to file*/
    public void writeObjectToTeamMgmtFile(manageTeam t) {
        ObjectOutputStream outputStream = null;

        try {
            ArrayList<manageTeam> teamList = readTeamMgmtFile();
            teamList.add(t);
            outputStream = new ObjectOutputStream(new FileOutputStream("teamMgmtData.ser"));

            for (int i = 0; i < teamList.size(); i++) {
                outputStream.writeObject(teamList.get(i));
            }

        } catch (IOException exp) {
            System.out.println("IO Exception while opening file");
        } finally {

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException exp) {
                System.out.println("IO Exception while closing file");
            }
        }
    }

//---------------------------------------------- PLAYER HELPER FUNCTIONS ----------------------------------------------

  /**Function to read Objects from file*/

    public static ArrayList<player> readPlayerFile() {

        ArrayList<player> playerList = new ArrayList<player>(0);

        ObjectInputStream inputStream = null;
        try {

            inputStream = new ObjectInputStream(new FileInputStream("playerData.ser"));

            boolean EOF = false;

            while (!EOF) {
                try {

                    player myObj = (player) inputStream.readObject();
                    playerList.add(myObj);
                } catch (ClassNotFoundException e) {
                } catch (EOFException end) {
                    EOF = true;
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                System.out.println("IO Exception while closing file");
            }
        }
        return playerList;
    }

    /**Function to write an object to file*/

    public void writeObjectToPlayerFile(player p) {

        ObjectOutputStream outputStream = null;

        try {

            ArrayList<player> playerList = readPlayerFile();
            playerList.add(p);
            outputStream = new ObjectOutputStream(new FileOutputStream("playerData.ser"));

            for (int i = 0; i < playerList.size(); i++) {
                outputStream.writeObject(playerList.get(i));
            }

        } catch (IOException exp) {
            System.out.println("IO Exception while opening file");
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException exp) {
                System.out.println("IO Exception while closing file");
            }
        }
    }

//------------------------------------------------------- MAIN -------------------------------------------------------
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        UDPServer server = new UDPServer();
        server.Listener();
    }
}