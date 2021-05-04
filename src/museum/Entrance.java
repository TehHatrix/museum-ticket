package museum;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Entrance {

    String name;
    int numTurnstile = 4;
    private Lock lock = new ReentrantLock();


    public Entrance(String name) {
        this.name = name;
    }

    public void enteringMuseum() {

    }


}
