package museum;

public class Entrance {
    Museum museum;
    String name;
    int num_turnstiles;
    int current_turnstile_open;


    public Entrance(String name,int num_turnstiles) {
        this.name = name;
        this.num_turnstiles = num_turnstiles;
        this.current_turnstile_open = num_turnstiles;
    }

    public void DetectEnter(){

    }

    public void DetectExit(){

    }






}
