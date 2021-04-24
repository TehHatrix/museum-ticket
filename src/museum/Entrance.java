package museum;

public class Entrance {

    String name;


    public Entrance(String name) {
        this.name = name;
    }

    public void Turnstile() {

        Runnable t1 = () -> {
            System.out.println("test");
        };

    }




}
