package museum;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Museum {
    public final static LocalTime opentime = LocalTime.of(17,0);;
    public final static LocalTime closetime = LocalTime.of(9,0);;
    //  total visitor of visitor
    public static final int MAX_SIZE = 900;
    //    current session
    public static int current_size;
    public static int total_enter;

    Entrance south;
    Entrance north;
    Exit east;
    Exit west;
    public static List<Entrance> entrance_list = new ArrayList<>();
    public static List<Exit> exit_list = new ArrayList<>();
    public static boolean opened;

    public Museum(){
        this.current_size = 0;
        this.total_enter = 0;
        this.south = new Entrance("South Entrance",4);
        this.north = new Entrance("North Entrance",4);
        this.east = new Exit("East Exit",4);
        this.west = new Exit("West Exit", 4);
        entrance_list.add(south);
        entrance_list.add(north);
        exit_list.add(east);
        exit_list.add(west);
    }

    public static LocalTime getClosetime() {
        return closetime;
    }

    public static LocalTime getOpentime() {
        return opentime;
    }


    public static void incrementSize(int visitor_number){
        current_size += visitor_number;
    }

    public static void close(){
        opened = false;
        System.out.println("Museum closing!");
    }
    public static void open(){
        opened = true;
        System.out.println("Museum open!");
    }

}



