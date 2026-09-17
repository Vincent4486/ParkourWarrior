# Parkour Warrior

This is a parkour game made by Vincent4486.

In this game, you can play the builtin maps and get the fastest time, or you can
create your own maps and play on it.

## Making maps

1. Make a file with the extension ".txt" and write the tile numbers into it, one
   row per line and one space between every number, where the number at column 1
   row 1 of the file is the tile at column 1 row 1 of the map. A map is 11 tiles
   tall, and as long as its `mapLength`, which is 68 tiles by default. Anything
   past the length or past row 11 is ignored, and columns that the file does not
   have are solid.
2. Save the file under any name, put it in `~/.config/ParkourWarrior/maps/`, and
   add an entry for it to the maps file.
3. Run the game at least once, which creates the maps file when it is missing.

### Maps file

`MapManager` reads and writes `~/.config/ParkourWarrior/maps.json` with `Gson`,
and creates it with the three builtin maps when it is missing. It holds a `maps`
array of map objects:

```json
{
  "maps": [
    {
      "mapNumber": 1,
      "mapPath": "/map/map0.txt",
      "mapType": 1,
      "isDefaultMap": true,
      "haveFinishedMap": false,
      "recordTimeMinutes": 0,
      "recordTimeSeconds": 6,
      "recordTimeMiliseconds": 767,
      "endIndex": 2740,
      "mapLength": 3264,
      "playerInitX": 480,
      "playerInitY": 384
    }
  ]
}
```

| Field | Meaning |
| ----- | ------- |
| mapNumber | Number of the map, the game itself does not use it |
| mapPath | Path of the map file, relative to the maps folder for a custom map |
| mapType | `1` for a builtin map in the JAR, `2` for a custom map |
| isDefaultMap | Whether it is a default map, which the title screen uses |
| haveFinishedMap | Whether the map has been finished |
| recordTimeMinutes / Seconds / Miliseconds | Fastest time on the map |
| endIndex | World X the player has to pass to finish the map |
| mapLength | Length of the map in pixels, 0 for the default, at most 24000 |
| playerInitX / playerInitY | World position the player spawns at |

Every field that is left out keeps the default of the game, so only `mapPath`
and `mapType` have to be set. `endIndex` has to be inside `mapLength`, and a
length that is not a whole number of 48 pixel tiles is rounded down. The record
times and the finish state are written back when a map is finished, and a file
that cannot be read is kept as `maps.json.bak`.

## Tile numbers

| Tiles | Behaviour |
| ----- | --------- |
| 0 brick, 3 grass, 4 sand, 6 water, 9 dirt | solid |
| 7 brick, 8 sky | solid, border barrier |
| 1 cloud, 2 flag, 5 sky | not solid |

The flag marks the end of a map visually, the map is finished once the player
passes the `endIndex` X coordinate.
