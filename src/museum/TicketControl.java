package museum;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TicketControl {
    static int current_runningid = 0;
    LocalTime time_bought;
    final int MAX_TICKET = 9999;
    static final LocalTime opentime = LocalTime.of(8,0);
    static final LocalTime closetime = LocalTime.of(17,0);
    static boolean opened;

    public TicketControl() {
    }

    public static LocalTime getClosetime() {
        return closetime;
    }

    public static LocalTime getOpentime() {
        return opentime;
    }

    public static void open(){
        opened = true;
        System.out.println("Ticket Counter Open!");
    }

    public static void close(){
        opened = false;
        System.out.println("Ticket Counter Closed!");
    }

    public static void increment_runningid(int numguests){
        current_runningid += numguests;
    }

    public static List<String> getTicketID(int numguests){
        List<String> ticket_list = new ArrayList<>();
        String first_word_id = String.valueOf('T');
        for(int i = current_runningid; i < numguests; i++){
            first_word_id += current_runningid;
            ticket_list.add(first_word_id);
        }
        return ticket_list;
    }


    }

