# This is a parkour game made by Vincent4486

In this game, you can play the builtin maps and get the fastest time, or you can create your own maps and play on it. To make maps, follow the instructions down below.

Making maps:
1) You have to make a file with the extension ".txt" and edit it in an application.
2) To make maps, type in the tile numbers in the file in the format you want it to display on the screen(e.g. 0 is typed at column 1 row 1 of the txt file and it will show rpthe tile of tilenumber 0 at screen column 1 screen row 1)and hava a space between every tilenumber, world column have to be 68 and row have to be 11.
3) Save this file and rename it whatever you want, then run the game at lest once and open the "ParkourWarrior.properties" file, use the format below to write the properties.

Format:

path=<path\to\file.txt>

map_type=custom    //This must be "custom"

is_default_map=false  //This must be "false"

hava_finished_map= **<** boolean **>**

record_time_minutes=**<** int time **>**

record_time_seconds=**<** int time **>**

record_time_miliseconds=**<** int time **>**



Tile Numbers:

0 = brick <solid>;

1 = cloud;

2 = flag <end point>;

3 = grass <solid>;

4 = sand <solid>;

5 = sky;

6 = water <die if touch>;

7 = sky <solid>

