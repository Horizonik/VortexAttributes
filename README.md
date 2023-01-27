# What is it exactly?
**VortexAttributes** is a Minecraft 1.19.2 plugin coded by Ofek Buchnik, it attributes for every player such as strength, armor and health (similar to the leveling systems like Hypixel Skyblock).

The stats are displayed on the player's hotbar (above the on-screen inventory slots).
There are currently 3 stats (with the option to easily add new skills):
- Health (Increases the vanilla health count)
- Armor (Resistance to damage)
- Strength (Damage to opponents)

#### Why I am making this public
This was a passion side project, due to a lack of time I don't think I'll ever fully finish it and I want the code to be available and see what other people do with it :)

## Code
#### File Storage
Currently each new player that logs into your server is assigned a stats file that is stored under Plugins/VortexAttributes. 
The stat file is then loaded when the player's stats need to be updated. Stat files only work with UUIDs, to prevent players from losing their skills on every name change.
The class for managing player files is under Stats/StatsManager.java.

#### Tasks
Due to a limitation of Minecraft, we cannot set permanent hotbar text, this means each player's hotbar needs to be updated every few seconds in order to prevent it from fading.
The ActionBarTask.java class handles that and is called every tick (you can change the timing in the OnEnable file at the VortexAttributes class) *TODO add this option in the config.

#### Attribute leveling
I wanted the attributes to be vanilla based, meaning a player can level up without running any commands, just by playing.
For this purpose I have implemented an EventListener under Events/StatsEvents.java, thanks to my custom methods, it is very easy to implement new ways to level up skills.
Currently the only implementations of vanilla leveling is for the Strength skill, just due to a lack of time on my end :)


## Commands
The plugins has two commands available:
/attrSet [player_name] [attribute_name] [attribute_level] - Set the level of a player's skill. (Requires permission "setAttr.use_command")
/sendLevels player_name health/strength/armor levels_amount - Send some of your hard-earned levels to another player. (Requires permission "sendLevels.use_command")
/attrStatus - Shows your levels, how much xp is needed for a level up and your general attribute stats. (Requires permission "attrStatus.use_command")

## Current Limitations
- Currently, the only level that players can level up is Strength (by killing other players). 
The health and armor attribute implementations don't have a vanilla way of leveling up just yet.

- The plugin was only tested in a server with up to 3 players concurrently and did not cause any extra lag. 
Due to the aforementioned hotbar limitation, the plugin iterates through all online players every few seconds and updates the text on their hotbar, which could cause some lag.
