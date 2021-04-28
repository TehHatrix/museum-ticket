package museum;

public class Museum {

    //  total visitor of visitor
    final int MAX_SIZE = 900;
    //    current session
    int current_size;
    boolean opened;


    public void close(){
        opened = false;
        System.out.println("Museum closing!");
    }
    public void open(){
        opened = true;
        System.out.println("Museum open!");
    }






}



