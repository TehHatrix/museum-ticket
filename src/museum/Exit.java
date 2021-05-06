package museum;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Exit extends Museum implements Runnable{

    private String name;

    public Exit(String name) {
        this.name = name;
    }

    public void letVisitorExit() {
        lock.lock();
        try {
            if(isMuseumOpen()) {
                Iterator<ConcurrentHashMap.Entry<String, LocalTime>> itr = getMuseumVisitor().entrySet().iterator();
                while (itr.hasNext()) {
                    ConcurrentHashMap.Entry<String, LocalTime> entry = itr.next();
//                    System.out.println(entry.getKey() + "  " + entry.getValue());
                    if (entry.getValue().equals(getClock())) {
                        removeHashMap(entry.getKey(), entry.getValue());
                        System.out.println(getClock() + " Ticket " + entry.getKey() + " exited through exit " + name);
                        museumFull.signalAll();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {

        while(true) {
                letVisitorExit();

//            if (isMuseumOpen()) {
//                letVisitorExit();
//            }

        }

    }
}
