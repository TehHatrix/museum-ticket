package museum;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketControl {
    static int current_runningid = 0;
    LocalTime time_bought;
    final int MAX_TICKET = 9999;
    static final LocalTime opentime = LocalTime.of(8, 0);
    static final LocalTime closetime = LocalTime.of(17, 0);
    static boolean opened;
    static boolean purchase_ticket;

    public TicketControl() {
    }

    public static LocalTime getClosetime() {
        return closetime;
    }

    public static LocalTime getOpentime() {
        return opentime;
    }

    public static void open() {
        opened = true;
        purchase_ticket = true;
        System.out.println("Ticket Counter Open!");
    }

    public static void close() {
        opened = false;
        purchase_ticket = false;
        System.out.println("Ticket Counter Closed!");
    }

//    public static void increment_runningid(int numguests) {
//        current_runningid += numguests;
//    }

    public static void checkMuseumTotalEnter() {
        if (Museum.total_enter >= 900) {
            purchase_ticket = false;
        }
    }

    public static List<String> getTicketID(int numguests) {
        List<String> ticket_list = new ArrayList<>();
        checkMuseumTotalEnter();
        if (purchase_ticket) {
            String first_word_id = "T";
            for (int i = 0; i < numguests; i++) {
                //T => T0 = T1 = T2
//                first_word_id += current_runningid;
                ticket_list.add(first_word_id.format("T%04d",current_runningid));
                first_word_id = "T";
                current_runningid++;
            }
            return ticket_list;
        }
        return ticket_list;
    }
}

