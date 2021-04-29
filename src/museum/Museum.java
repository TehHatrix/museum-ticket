package museum;

import java.time.LocalTime;

public class Museum {

    //  total visitor of visitor
    public static final int MAX_SIZE = 900;
    //    current session
    public static int current_size;
    public static LocalTime opentime;
    public static LocalTime closetime;
    Entrance south;
    Entrance north;
    public static boolean opened;

    public Museum(){
        this.current_size = 0;
        this.closetime = LocalTime.of(17,0);
        this.opentime = LocalTime.of(9,0);
        this.south = new Entrance("South Entrance",4);
        this.north = new Entrance("North Entrance",4);
    }

    public static LocalTime getClosetime() {
        return closetime;
    }

    public static LocalTime getOpentime() {
        return opentime;
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



