package museum;

import java.util.Timer;
import java.util.TimerTask;

class testTimer {

    Timer timer = new Timer();

    public void main(String[] args) {
        timer.schedule(new reminder(), 5000);
    }
}

class reminder extends TimerTask {

    public void run() {
        System.out.println("dsf");

    }
}