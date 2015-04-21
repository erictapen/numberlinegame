# Tasks #

## Web interface for accessing log data ##

  * Overview of relevant tables in browser and CSV download option (generated CSV files will be removed from server once they have been downloaded)
  * Allow for formulating SQL queries and generating SQL queries from user input
  * Create a new tab in the Drupal CMS for authorized users; the web interface can be embedded there
  * Consider that user information is stored in the Drupal database ("drupal\_wiscam") whereas all logging related data is located in the database "logging"; is it possible to run SQL queries on each database and then run a query on the results?

## ELO rating ##

Goals:

  * Implement ELO rating for registered users
  * Create NPCs with different skill and ELO number (for NumberLineGame)
  * Adjust game settings to ELO rating (pointer width (for NumberLineGame),
> > reaction delay, NPC skill level)

Details:

  * Create classes of NPC with different ELO numbers
  * Do not adjust the ELO number of NPC depending on the outcome
  * The ELO number of each player in multiplayer games can be updated by comparison with the best player or the average value of all other players
  * Use 1000 as an initial ELO number. Inflation and deflation phenomena are not relevant for now. The maximal ELO number is 2500.

## Handicap calculation and evaluation ##

Goals:
  * Calculate handicap for user when opening new game
  * Handicap should be based on log entries
  * Adjust game settings to user handicap (i.e., increased level of difficulty for skilled users)

Considerations:
  * Generic interface for all games; each game will must implement a method for calculating the handicap and for adjusting the game settings
  * Normalized handicap value for all games (e.g. a value between 0.0 and 1.0 where 0.0 is the best possible and 1.0 the worst possible value)

Open questions:
  * Rely on order of log IDs to determine which log entries belong to one game? Answer: Create new action which is triggered at the end of each game and contains information about handicap values for all players.
  * Only use results for single player games (user vs. NPC)? Answer: No, use results from all game no matter how many players took part.
  * Default handicap value? Answer: In the case of the numberline game, there are three possibilities - use the worst, best possible value or the value in between those two values.
  * For the numberline game:
    * How does a suitable mapping of handicap value to pointer width look like? Answer: Use a linear mapping.
    * What are reasonable limits for pointer width?

## Logging ##

Consider new table game\_properties. A log entry will refer to this table via a foreign key constraint. This would allow for determining which users took part in one specific game.

## Outlook ##

  * Web interface for finding opponents who will match the user's skill
  * Team games where the ELO number of each team is the average of the ELO numbers of its players