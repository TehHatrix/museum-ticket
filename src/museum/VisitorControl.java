package museum;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

public class VisitorControl implements Runnable{
    private static String ticketid;
    public static LocalTime time_enter;
    List<String> visitor_ticket;


    public VisitorControl() {
    }


    //Buy ticket (probably ni ticket thread)
    public static void buyTicketandOverallFlow() throws InterruptedException {
        while(TicketControl.opened){
            Random random = new Random();
            int num_of_guests = random.nextInt(5) + 1;
            int purchase_delay = random.nextInt(5000) + 1000;
            Thread.sleep(purchase_delay);
            //get the ticket ID / buy the ticket
            if(TicketControl.getTicketID(num_of_guests) == null){
                break;
            }
            List<String> visitor_ticket = TicketControl.getTicketID(num_of_guests);
            System.out.println(visitor_ticket);
            entermuseum(num_of_guests,visitor_ticket);
//            exitMuseum(num_of_guests,visitor_ticket);
        }
    }

    //Enter museum through random entrances
    public static void entermuseum(int numguest, List<String> visitor_ticket) throws InterruptedException {
        Random random = new Random();
        //Choose Entrance Gate
        Entrance which_gate = Museum.entrance_list.get(random.nextInt(Museum.entrance_list.size()));
        //Enter the gate
        which_gate.EnterQueueList(numguest,visitor_ticket);
    }

    //Stay at the museum for random time
    public static void stayMuseum() throws InterruptedException {
        Random random = new Random();
        //Stay
        int staytime = random.nextInt(150) + 50;
        Thread.sleep(staytime);
    }

    //Exit museum through random exits
    public static void exitMuseum(int numguest, List<String> visitor_ticket) throws InterruptedException {
        Random random = new Random();
        //Choose Exit Gate
        Exit which_exit = Museum.exit_list.get(random.nextInt(Museum.exit_list.size()));
        //Exit the gate
        which_exit.LetVisitorExit(numguest,visitor_ticket);
    }



    public void run() {
        try {
            buyTicketandOverallFlow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
