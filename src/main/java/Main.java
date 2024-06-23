import views.Welcome;

public class Main {
    public static void main(String[] args) throws Exception {
        Welcome w = new Welcome();
        do{
            w.welcomeScreen();
        }while(true);
    }
}
