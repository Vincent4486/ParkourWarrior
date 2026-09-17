# Parkour Warrior

This is a parkour game made by Vincent4486.

In this game, you can play the builtin maps and get the fastest time, or you can
create your own maps and play on it. To make maps, follow the instructions down
below.

## Making maps

1. You have to make a file with the extension ".txt" and edit it in an
   application.
2. To make maps, type in the tile numbers in the file in the format you want it
   to display on the screen (e.g. 0 is typed at column 1 row 1 of the txt file
   and it will show the tile of tile number 0 at screen column 1 screen row 1)
   and have a space between every tile number, the world column has to be 68 and
   the row has to be 11. Anything past the 68th column or the 11th row is
   ignored.
3. Save this file and rename it whatever you want, then run the game at least
   once so it creates its properties file, and add the map to it in the format
   below.

### Map properties

`MapManager` reads and writes the game properties at
`~/.config/ParkourWarrior/maps.properties`, and creates the file with the three
builtin maps when it does not exist yet. Every entry is a numbered key, counting
up from 0 without gaps, and a value of eleven space separated fields:

```properties
0=1 /map/map0.txt 1 true false 0 6 767 2740 480 384
1=2 /map/map1.txt 1 true false 0 10 585 3017 480 384
2=3 /map/map2.txt 1 true false 0 9 604 3100 480 384
```

| Field | Meaning |
| ----- | ------- |
| map_number | Number of the map |
| map_path | Path of the map file, the builtin maps use `/map/map0.txt` |
| map_type | `1` for a builtin map in the JAR, `2` for a custom map |
| is_default_map | `true` or `false`, whether the map is a default map |
| have_finished_map | `true` or `false`, whether the map has been finished |
| record_time_minutes | Fastest time in minutes, shown on the title screen |
| record_time_seconds | Fastest time in seconds, shown on the title screen |
| record_time_milis | Fastest time in milliseconds, shown on the title screen |
| end_position | World X the player has to pass to finish the map |
| player_init_x | World X the player spawns at, in world pixels |
| player_init_y | World Y the player spawns at, in world pixels |

The fields after the key are positional, so they always have to be written in
this order.

`is_default_map` only affects the title screen, where the first map marked
`true` is the one selected when the game starts.

A `map_type` of `2` loads the map from
`~/.config/ParkourWarrior/maps/<map_path>`, so custom maps belong in that folder
with an entry like `3=4 myMap.txt 2 false false 0 0 0 2000 480 384`.

## Tile numbers

| Number | Tile  | Behaviour |
| ------ | ----- | --------- |
| 0      | brick | solid |
| 1      | cloud | not solid |
| 2      | flag  | not solid |
| 3      | grass | solid |
| 4      | sand  | solid |
| 5      | sky   | not solid |
| 6      | water | solid |
| 7      | brick | solid, border barrier |
| 8      | sky   | solid, border barrier |
