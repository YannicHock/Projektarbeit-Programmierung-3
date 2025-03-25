Listener

Attempt Player Creation Listener:

* Event triggered when an attempt is made to create a new player.
* Contains details such as the player's first name, last name, date of birth,
* jersey number, and positions.

Attempt Player Transfer Events Listener:
* Event triggered when an attempt is made to transfer a player to a new team.
* Contains details such as the player, new team ID, jersey number, and transfer amount.
* Extends the base Event class.

Attempt Player Update Listener:
* Event triggered when an attempt is made to update a player's details.
* Contains the player's old details, new details (if provided), and team ID.