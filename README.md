# Complex-Grid-Battle
This game was developed in Java following the MVC architecture. It includes unit tests and features an aggressive AI using A* pathfinding. A more defensive AI was also created but disabled due to its inefficiency—it often skipped turns, making AI-only games extremely long and uninteresting.<br>The game is highly customizable via the terminal: players can choose the number of participants, map size, player names, controllable entities, and their types, each with different stats. Players can move, set traps (bombs and mines—hidden from enemies), shoot vertically and horizontally, equip a shield for one-turn immunity, or skip their turn. The goal is to be the last player with remaining energy. Some pads on the map restore a bit of energy.<br>Several game variables can be configured through a .properties file. The game can be played on 4 predefined maps (for 2 to 8 players) or on randomly generated maps using a strategy pattern (chaotic or balanced layouts). It is fully playable both in the terminal and through a graphical interface built with AWT and Swing.<br>Although the project was intended for teams of about four students, I chose to tackle it solo. I’m very proud of this school project, which took me several months to complete.

# Instructions
$ ant [javadoc, runTests, run]

# Images
<table>
	<img src="Images/game_3_1.png" width="100%"/>
  <tr>
  	<td><img src="Images/game_3_2.png" width="300"/></td>
    <td><img src="Images/game_3_3.png" width="300"/></td>
    <td><img src="Images/game_3_4.png" width="300"/></td>
  </tr>
</table>
