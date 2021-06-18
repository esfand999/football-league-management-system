import java.io.*;
import java.net.*;
import java.util.Scanner;

@SuppressWarnings("ALL")
public class UDPClient {
    DatagramSocket Socket = new DatagramSocket(6666);

    public UDPClient() throws SocketException {}

//------------------------------------------------ TEAM MENU FUNCTIONS ------------------------------------------------
    public void addTeam(Interface obj) throws IOException {
        /**Sending Menu Option Packet*/
        sendData(obj, 7777);
    }

    public void viewAllTeams(Interface obj) throws IOException, ClassNotFoundException {
        /**Sending Menu Option Packet*/
        sendData(obj, 7777);

        try {
            //Receiving the data size from server
            byte[] incomingdataSizeByte = new byte[1024];
            DatagramPacket incomingdataSize = new DatagramPacket(incomingdataSizeByte, incomingdataSizeByte.length);
            Socket.receive(incomingdataSize);
            byte[] dataSizeObjectByte = incomingdataSize.getData();
            ByteArrayInputStream dataSizeBAIS = new ByteArrayInputStream(dataSizeObjectByte);
            ObjectInputStream dataSizeOIS = new ObjectInputStream(dataSizeBAIS);
            String sizeString = (String) dataSizeOIS.readObject();
            int size = Integer.parseInt(sizeString);

            if(size==0){
                System.out.println("\n\t\t\t\t\t\t**********NO RECORDS STORED**********\n");
            } else {

                //Receving Team record from server
                for (int i = 0; i < size; i++) {
                    byte[] incomingObjectData = new byte[1024];
                    DatagramPacket incomingPacketObject = new DatagramPacket(incomingObjectData, incomingObjectData.length);
                    Socket.receive(incomingPacketObject);
                    byte[] teamDataByte = incomingPacketObject.getData();
                    ByteArrayInputStream in = new ByteArrayInputStream(teamDataByte);
                    ObjectInputStream is = new ObjectInputStream(in);
                    String s = (String) is.readObject();
                    System.out.println(s);
                }
            }

            System.out.println("\n===================================END OF LIST===================================");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void searchTeam(Interface obj) throws IOException, ClassNotFoundException {
        /**Send Menu Option Packet*/
        sendData(obj, 7777);

        //Receive response from Server
        byte[] incomingObjectData = new byte[1024];
        DatagramPacket incomingResponse = new DatagramPacket(incomingObjectData, incomingObjectData.length);
        Socket.receive(incomingResponse);
        byte[] objByte = incomingResponse.getData();
        ByteArrayInputStream responseBAIS = new ByteArrayInputStream(objByte);
        ObjectInputStream responseOIS = new ObjectInputStream(responseBAIS);
        String responseString = (String) responseOIS.readObject();
        System.out.println("===============================================================");
        System.out.println("\t\t\t" + responseString);
        System.out.println("===============================================================");
    }

//----------------------------------------------- PLAYER MENU FUNCTIONS -----------------------------------------------
    public void addPlayer(Interface obj) throws IOException {
        /**Sending Object Packet*/
        sendData(obj, 7777);
    }

    public void viewAllPlayers(Interface obj) throws IOException, ClassNotFoundException {
        //Sending object
        sendData(obj, 7777);

            //Receiving the data size from server
            byte[] incomingdataSizeByte = new byte[1024];
            DatagramPacket incomingdataSize = new DatagramPacket(incomingdataSizeByte, incomingdataSizeByte.length);
            Socket.receive(incomingdataSize);
            byte[] dataSizeObjectByte = incomingdataSize.getData();
            ByteArrayInputStream dataSizeBAIS = new ByteArrayInputStream(dataSizeObjectByte);
            ObjectInputStream dataSizeOIS = new ObjectInputStream(dataSizeBAIS);
            String sizeString = (String) dataSizeOIS.readObject();
            int size = Integer.parseInt(sizeString);


            if(size==0){
                System.out.println("\n\t\t\t\t\t\t**********NO RECORDS STORED**********\n");
            } else {
                //Receving Player record from server
                for (int i = 0; i < size; i++) {
                    byte[] incomingObjectData = new byte[1024];
                    DatagramPacket incomingPacketObject = new DatagramPacket(incomingObjectData, incomingObjectData.length);
                    Socket.receive(incomingPacketObject);
                    byte[] playerDataByte = incomingPacketObject.getData();
                    ByteArrayInputStream in = new ByteArrayInputStream(playerDataByte);
                    ObjectInputStream is = new ObjectInputStream(in);
                    String p = (String) is.readObject();
                    System.out.println(p.toString());
                }
            }

            System.out.println("\n===================================END OF LIST===================================");
        }

    public void searchPlayer(Interface obj) throws IOException, ClassNotFoundException {
        /**Send Object Packet*/
        sendData(obj, 7777);

        //Receive response from Server
        byte[] incomingObjectData = new byte[1024];
        DatagramPacket incomingResponse = new DatagramPacket(incomingObjectData, incomingObjectData.length);
        Socket.receive(incomingResponse);
        byte[] objByte = incomingResponse.getData();
        ByteArrayInputStream responseBAIS = new ByteArrayInputStream(objByte);
        ObjectInputStream responseOIS = new ObjectInputStream(responseBAIS);
        String responseString = (String) responseOIS.readObject();
        System.out.println("======================================================================");
        System.out.println("\t\t\t" + responseString);
        System.out.println("======================================================================");
    }

//--------------------------------------------- TEAM MANAGEMENT FUNCTIONS ---------------------------------------------

    public void addToTeam(Interface obj) throws IOException, ClassNotFoundException{
        sendData(obj, 7777);
    }

//------------------------------------------------------- MAIN --------------------------------------------------------

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        UDPClient client = new UDPClient();
        int managementMenuOption = 0;
        int playerMenuOption = 0;
        int teamMenuOption = 0;
        int mainMenuOption = 0;
        String regex = "\\d+";


        while (mainMenuOption !=4){
            System.out.println("********* MAIN MENU *********");
            System.out.println(" 1. Access Team Menu");
            System.out.println(" 2. Access Player Menu");
            System.out.println(" 3. Access Team Management Menu");
            System.out.println(" 4. Exit");
            System.out.println("\n Select an option: ");
            mainMenuOption = input.nextInt();

            switch (mainMenuOption) {
                case 1:
                    teamMenuOption=0;
                    while (teamMenuOption !=4){
                        System.out.println("********* Team Menu *********");
                        System.out.println(" 1. Add a team");
                        System.out.println(" 2. View all teams");
                        System.out.println(" 3. Search a team");
                        System.out.println(" 4. Exit");
                        System.out.println("\n Select an option: ");
                        teamMenuOption = input.nextInt();

                        switch (teamMenuOption) {
                            case 1:
                                System.out.println("Enter team ID: ");
                                input.nextLine();
                                String teamID = input.nextLine();
                                while(!teamID.matches(regex)){
                                    System.out.println("ID must be a number input.");
                                    teamID = input.nextLine();
                                }

                                System.out.println("Enter team name: ");
                                String teamName = input.nextLine();

                                System.out.println("Enter team manager: ");
                                String manager = input.nextLine();

                                System.out.println("Enter team coach: ");
                                String coachName = input.nextLine();

                                team Team = new team(teamID, teamName, manager, coachName);
                                Interface add = new Interface(Team,"addTeam");
                                client.addTeam(add);
                                System.out.println("\n");
                                break;

                            //TODO Add Function and complete other cases

                            case 2:
                                Interface view = new Interface("viewAllTeams");
                                System.out.println("============================== DISPLAYING ALL TEAMS ============================");
                                client.viewAllTeams(view);
                                System.out.println("\n");
                                break;

                            case 3:
                                System.out.println("\nEnter team's id to Search:");
                                input.nextLine();
                                String id = input.nextLine();
                                Interface search = new Interface(id, "searchTeam");
                                client.searchTeam(search);
                                System.out.println("\n");
                                break;

                            case 4:
                                break;

                            default:
                                System.out.println("Invalid Choice.");
                                System.out.println("\n");
                                break;
                        }
                    }
                    break;
                case 2:
                    playerMenuOption=0;
                    while (playerMenuOption != 4) {
                        System.out.println("********* Players Menu *********");
                        System.out.println(" 1. Add a player");
                        System.out.println(" 2. View all players");
                        System.out.println(" 3. Search a player");
                        System.out.println(" 4. Exit");
                        System.out.println("\nSelect an option: ");
                        playerMenuOption = input.nextInt();

                        switch (playerMenuOption) {
                            case 1:
                                System.out.println("Enter player's id: ");
                                input.nextLine();
                                String playerid = input.nextLine();
                                while(!playerid.matches(regex)){
                                    System.out.println("ID must be a number input.");
                                    playerid = input.nextLine();
                                }

                                System.out.println("Enter player's first name: ");
                                String fname = input.nextLine();

                                System.out.println("Enter player's last name: ");
                                String lname = input.nextLine();

                                System.out.println("Enter player's position: ");
                                String pos = input.nextLine();


                                System.out.println("Enter player's salary: $");
                                String salary = input.nextLine();
                                while(!salary.matches(regex)){
                                    System.out.println("Salary must be a number input.");
                                    salary = input.nextLine();
                                }

                                System.out.println("Enter player's country: ");
                                String country = input.nextLine();

                                String status = "Free";

                                player Player = new player(playerid, fname, lname, pos, salary, country, status);
                                Interface add = new Interface(Player, "addPlayer");
                                client.addPlayer(add);
                                System.out.println("\n");
                                break;

                            case 2:
                                Interface view = new Interface("viewAllPlayers");
                                System.out.println("============================ DISPLAYING ALL PLAYERS ===========================");

                                client.viewAllPlayers(view);
                                System.out.println("\n");
                                break;

                            case 3:
                                System.out.println("\nEnter player's id to Search:");
                                input.nextLine();
                                String id = input.nextLine();
                                Interface search = new Interface(id, "searchPlayer");
                                client.searchPlayer(search);
                                System.out.println("\n");
                                break;

                            case 4:
                                break;

                            default:
                                System.out.println("Invalid Choice.");
                                System.out.println("\n");
                                break;
                        }
                    }
                    break;

                case 3:
                    managementMenuOption = 0;
                    while(managementMenuOption !=5){
                        System.out.println("********* Team Management Menu *********");
                        System.out.println(" 1. Add player to team");
                        System.out.println(" 2. View Available Players");
                        System.out.println(" 3. View all team rosters");
                        System.out.println(" 4. Search team");
                        System.out.println(" 5. Exit");
                        System.out.println("\nSelect an option: ");
                        managementMenuOption = input.nextInt();

                        switch (managementMenuOption){
                            case 1:
                                System.out.println("\nEnter ID of the team you would like to manage: ");
                                input.nextLine();
                                String team = input.nextLine();
                                System.out.println("\nEnter player ID: ");
                                String playerList = input.nextLine();
                                Interface mgmt = new Interface(team, playerList, "addToTeam");
                                client.addToTeam(mgmt);
                                break;

                            case 2:
                                Interface viewAvailable = new Interface("viewAvailablePlayers");
                                System.out.println("========================== DISPLAYING ALL AVAILABLE PLAYERS ========================");
                                client.viewAllPlayers(viewAvailable);
                                System.out.println("\n");
                                break;

                            case 3:
                                Interface viewTeamsMgmt = new Interface("viewTeamsMgmt");
                                System.out.println("========================== DISPLAYING ALL TEAM ROSTERS =============================");
                                client.viewAllTeams(viewTeamsMgmt);
                                System.out.println("\n");
                                break;

                            case 4:
                                System.out.println("\nEnter team's id to Search:");
                                input.nextLine();
                                String mgmid = input.nextLine();
                                Interface mgmsearch = new Interface(mgmid, "searchMgmtTeam");
                                client.searchTeam(mgmsearch);
                                System.out.println("\n");
                                break;

                            case 5:
                                break;

                            default:
                                System.out.println("Invalid Choice.");
                                System.out.println("\n");
                                break;
                        }

                    }
                    break;
                case 4:
                    System.out.println("Goodbye :)");
                    break;
                default:
                    System.out.println("Invalid Choice.");
                    System.out.println("\n");
                    break;
            }
        }
    }

//------------------------------------------------- HELPER FUNCTIONS --------------------------------------------------

    public void sendData(Object obj, int port) throws IOException {
        InetAddress IPAddress = InetAddress.getByName("localhost");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        os = new ObjectOutputStream(outputStream);
        os.writeObject(obj);
        byte[] sendData = outputStream.toByteArray();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        Socket.send(sendPacket);
    }

}
