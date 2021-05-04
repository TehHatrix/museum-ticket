package museum;

import java.time.LocalTime;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketControl {
    static volatile AtomicInteger current_runningid = new AtomicInteger(0); //for thread safe (multiple thread can use same value)
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


    public static void checkMuseumTotalEnter() {
        if (Museum.total_enter >= 900) {
            purchase_ticket = false;
        }
    }

    //lupa nak tambah time bought bila (boleh je letak List / Hashmap *but kene fikir pasal thread safe collection)
    public static CopyOnWriteArrayList<Ticket> getTicketID(int numguests) {
        checkMuseumTotalEnter();
        if (purchase_ticket) {
            String first_word_id = "T";
            CopyOnWriteArrayList<Ticket> list_ticket = new CopyOnWriteArrayList<Ticket>();
            Ticket ticket;
            for (int i = 0; i < numguests; i++) {
                ticket = new Ticket(first_word_id.format("T%04d", current_runningid.get()), Test.Task.getCurrentTime());
                list_ticket.add(ticket);
                first_word_id = "T";
                current_runningid.incrementAndGet();
            }
            return list_ticket;
        }
        return null;
    }
}

