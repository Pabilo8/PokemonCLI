# Pokemon CLI/GUI
This repository contains the code necessary to build and run Pokemon CLI/GUI, a Pokemon-inspired game written in Java.
It uses Lombok for boilerplate code reduction and JUnit for testing.
The game logic is the same in both games, only difference being the display technology (Lanterna in CLI, Swing in GUI
version).
## Building
The project is built using Gradle, so you can build it using the following command: `gradle build`
```shell
gradle build
```
## Authors
Paweł Godlewski - [@Pabilo8](https://github.com/Pabilo8)  
Oskar Łuniewski - [@KitsuneOkami](https://github.com/KitsuneOkami)
## Gameplay and Controls
Most UI is controlled using keys W,A,S,D for picking directions and SPACE for confirming choices.
The game is a simple turn-based RPG where you can move around the map, encounter wild Pokemon and enemy Trainers and
battle them.
Each pokemon has 4 moves, and you can choose which one to use in battle.
You can also pick up items from the ground and use them in battle.
The game also features a simple dialogue system and few NPCs that can be found inside of buildings scattered around the
map
## Pokemons
Currently the game includes following Pokemons:
- Bulbasaur
- Charmander
- Squirltle
- Caterpie
- Metapod
- Butterfree
- Weedle
- Kakuna
- Beedrill
- Pidgey
- Rattata
- Eevee
## Moves
Currently the game contains following moves:
- Covet
- Tackle
- Growl
- Tail Whip
