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
- firstName
- lastName
- salary
- position
- country
- status

<h3>•	Team </h3>
- teamID
- teamName
- owner
- teamCoach

<h3>•	manageTeam </h3>
- teamID
- ArrayList<player>

 
A team can consist of 11 players.
The manageTeam class is composed of a team ID and consists of an array list of players assigned to the team.

 
 <h1> Functions of Classes: </h1>
 <h2> 1.	UDPServer </h2>
This class holds the code for the server side of the application.

<h3> Functions: </h3>
•	addTeam(team)
•	searchTeam(String)
•	viewAllTeams()

•	addPlayer(player)
•	searchPlayer(String)
•	viewAllPlayers()

•	addToTeam(String teamid, String playerid)
•	viewAvailablePlayers()
•	viewTeamsMgmt
•	searchMgmtTeam()

•	readTeamFile()
•	readPlayerFile()
•	readTeamMgmtFile()

•	writeObjectToTeamFile(team)
•	writeObjectToPlayerFile(player)
•	writeObjectToTeamMgmtFile(manageTeam)

•	sendData(Object)



 <h2> 2.	UDPClient </h2>
This class holds the code for the client side of the application.

<h3> Functions: </h3>
•	addTeam(Interface)
•	searchTeam(Interface)
•	viewAllTeams(Interface)

•	addPlayer(Interface)
•	searchPlayer(Interface)
•	viewAllPlayers(Interface)

•	sendData(Object, port)



 <h2> 3.	Interface </h2>
The interface class contains an overloaded object consisting of an entity, a menu operation and an ID number of an entity that is sent to the client side to perform functions accordingly.
 <h3> Functions: </h3>
•	getTeam()
•	getPlayer()
•	getOperation()
•	getID()

•	setTeam(team)
•	setPlayer(player)
•	setOperation(String)
•	setID(String)

<h2> 4.	Player </h2?
The player class creates a player object.
 <h3> Functions: </h3>
•	getPlayerID()
•	getFirstName()
•	getLastName()
•	getSalary()
•	getCountry()
•	getStatus()

•	setPlayerID(String)
•	setFirstName(String)
•	setLastName(String)
•	setSalary(String)
•	setCountry(String)
•	setStatus(String)

 <h2> 5.	Team </h2>
The team class creates a team object.
 <h3> Functions: </h3>
•	getTeamID()
•	getTeamName()
•	getTeamOwner()
•	getTeamCoach()

•	setTeamID()
•	setTeamName()
•	setTeamOwner()
•	setTeamCoach()

 <h2> 6.	manageTeam </h2>
The manageTeam class is used to assign players to a team.
<h3> Functions: </h3>
•	getTeamID()
•	getPlayer()

•	setTeamID(team)
•	setPlayer(player)
