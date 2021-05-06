package museum;


import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Museum {

    public final LocalTime opentime = LocalTime.of(9,0);
    public final LocalTime closetime = LocalTime.of(18,0);
    public Random rand;
    private static boolean museumOpen;
    private static boolean counterOpen;
    private static volatile AtomicInteger totalVisitor = new AtomicInteger(0);
    private static volatile LocalTime buyTime = LocalTime.of(7, 45);

    private static volatile LocalTime clock;
    private static ConcurrentHashMap<String, LocalTime> museumVisitor = new ConcurrentHashMap<String, LocalTime>();
    private static ConcurrentLinkedQueue<Visitor> visitorQueue = new ConcurrentLinkedQueue<>();


    public static Lock lock;
    public static Condition museumFull;
    public static Condition turnstileFull;


    public Museum() {
        rand = new Random();
        clock = LocalTime.of(7, 45);
        lock = new ReentrantLock();
        museumFull = lock.newCondition();
        turnstileFull = lock.newCondition();
        counterOpen = false;
        museumOpen = false;
    }

    public LocalTime getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(LocalTime buyTime) {
        this.buyTime = buyTime;
    }

    public boolean isMuseumOpen() {
        return museumOpen;
    }

    public void setMuseumOpen(boolean museumOpen) {
        this.museumOpen = museumOpen;
    }

    public boolean isCounterOpen() {
        return counterOpen;
    }

    public void setCounterOpen(boolean counterOpen) {
        this.counterOpen = counterOpen;
    }

    public LocalTime getClock() {
        return clock;
    }

    public void setClock(LocalTime clock) {
        this.clock = clock;
    }

    public ConcurrentHashMap<String, LocalTime> getMuseumVisitor() {
        return museumVisitor;
    }

    public ConcurrentLinkedQueue<Visitor> getVisitorQueue() {
        return visitorQueue;
    }

    public AtomicInteger getTotalVisitor() {
        return totalVisitor;
    }

    public void setTotalVisitor(AtomicInteger totalVisitor) {
        this.totalVisitor = totalVisitor;
    }

    public void addHashMap(String v, LocalTime t) throws InterruptedException {
        if(museumVisitor.size() >= 100) {
            System.out.println("museum is full. waiting for visitor to left");
            museumFull.await();
        }
        museumVisitor.put(v, t);
    }

    public void removeHashMap(String v, LocalTime t) {
        museumVisitor.remove(v, t);
    }




}
