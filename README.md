# football-league-management-system
 
<h2> Project Description: </h2>
Our project consists of a server-client application that manages players and teams of a football league. We have used the UDP protocol for communication between the server and the client. The client enters data regarding the players or teams and sends it to the server where the data is then stored as objects in serializable files. The server has 3 files; one for list of players, one for list of teams and one for a list of team rosters. 

<h2> Project Solution: </h2>
Adding/Viewing/Searching Team
An overloaded constructor by the name of interface is used to send objects and the operation to perform to the server. The team with all is attributes are added to teamData.ser file. To view the objects, the objects are first retrieved from the serializable file into an array list and then sent to the server. Teams are searched in a similar way by retrieving the array list from the file and then matched with the required ID attribute.

<h2> Adding/Viewing/Searching Player </h2>
An overloaded constructor by the name of interface is used to send objects and the operation to perform to the server. The player with all is attributes are added to playerData.ser file. To view the objects, the objects are first retrieved from the serializable file into an array list and then sent to the server. Players are searched in a similar way by retrieving the array list from the file and then matched with the required ID attribute.

<h2> Adding/Viewing/Searching Team Rosters </h2>
An overloaded constructor by the name of interface is used to send objects and the operation to perform to the server. The roster consists of team ID and an array list of players which is then added to teamMgmtData.ser file. We use several checks while adding players to the team. First, we check if the team exists, then we check if the player exists and has the status of a free agent. If the request complies with all the condition, the player is then added to the team. To view the objects, the objects are first retrieved from the serializable file into an array list and then sent to the server. Similarly, teams are searched by retrieving the array list from the file and then matched with the required ID attribute.
 
<h2> Entities: </h2>
Our project consists of two entities and a composition of the two in another:
<h3>•	Player </h3>
- playerID
-	firstName
-	lastName
-	salary
-	position
-	country
-	status

<h3>•	Team </h3>
-	teamID
-	teamName
-	owner
-	teamCoach

<h3>•	manageTeam </h3>
-	teamID
-	ArrayList<player>

A team can consist of 11 players.
The manageTeam class is composed of a team ID and consists of an array list of players assigned to the team.
