package museum;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VisitorControl implements Runnable{
    private static List<String> visitor_ticket;
    public static LocalTime time_enter;
    public static String current_ticketid = ""; //This will work only one thread :(

    public VisitorControl() {
        this.visitor_ticket = new ArrayList<>();
    }


    public static void buyTicketandOverallFlow() throws InterruptedException {
        while(TicketControl.opened){
            Random random = new Random();
            int num_of_guests = random.nextInt(5) + 1;
            int purchase_delay = random.nextInt(5000) + 1000;
            Thread.sleep(purchase_delay);
            //get the ticket ID / buy the ticket
            visitor_ticket = TicketControl.getTicketID(num_of_guests);
            if(visitor_ticket == null){
                break;
            }
            System.out.println(Test.Task.getCurrentTime() + " " + visitor_ticket + " Sold");
            //ataupun kita keluarkan individual ticket kat sini
            for (String individual_ticket : visitor_ticket){
                entermuseum(individual_ticket);
                exitMuseum(individual_ticket);
            }

        }
    }

    //Enter museum through random entrances
    public static void entermuseum(String visitor_ticket) throws InterruptedException {
        Random random = new Random();
        //Choose Entrance Gate
        Entrance which_gate = Museum.entrance_list.get(random.nextInt(Museum.entrance_list.size()));
        //Enter the gate
        which_gate.EnterQueueList(visitor_ticket);
    }

    //Stay at the museum for random time
    public static void stayMuseum(String ticketid) throws InterruptedException {
        Random random = new Random();
        //Stay
        int staytime = random.nextInt(150) + 50;
        System.out.println(Test.Task.getCurrentTime() +" Ticket ID : " + ticketid + " staying for " + staytime + " minutes");
        Thread.sleep(staytime);

    }

    //Exit museum through random exits
    public static void exitMuseum(String visitor_ticket) throws InterruptedException {
        Random random = new Random();
        //Choose Exit Gate
        Exit which_exit = Museum.exit_list.get(random.nextInt(Museum.exit_list.size()));
        //Exit the gate
        which_exit.EnterQueueList(visitor_ticket);
    }



    public void run() {
        try {
            buyTicketandOverallFlow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
