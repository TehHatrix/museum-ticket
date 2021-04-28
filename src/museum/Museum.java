package museum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Museum {

    //  total visitor of visitor
    final int MAX_SIZE = 900;
    //    current session
    int current_size;
//    LocalTime opentime = new LocalTime(10,);
    LocalDateTime closetime;
    Entrance south;
    Entrance north;
    boolean opened;

    public Museum(){
        this.south = new Entrance("South Entrance",4);
        this.south = new Entrance("North Entrance",4);

    }


    public void close(){
        opened = false;
        System.out.println("Museum closing!");
    }
    public void open(){
        opened = true;
        System.out.println("Museum open!");
    }




}



