import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[]args) throws Exception{

        Scanner sc = new Scanner(System.in);

        File file = null;
        FileInputStream fis = null;
        String fileName;
        while(file == null) {
            System.out.println("Enter filename with path : ");
            fileName = sc.nextLine();
            try {
                file = new File(fileName);
                fis = new FileInputStream(file);
            } catch (Exception e) {
                System.out.println("file not found");
                file = null;
            }
        }
        System.out.println("Enter port to use : ");
        int port = sc.nextInt();

        ServerSocket ss = new ServerSocket(port);

        Socket s = ss.accept();

        PrintStream ps = new PrintStream(s.getOutputStream());

//////////////////
        try{



            byte[] data = new byte[fis.available()];
            System.out.println(data.length);

            ps.println(file.getName());
            ps.println(data.length);

            fis.read(data);


            long startTime = System.nanoTime();
            System.out.println("started");
            for (int i=0;i< data.length;i++) {
                if(i==3436440){
                    break;
                }
                ps.println(data[i]);
                //System.out.println(i+"/"+ data.length);
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("Transmitted in : " + duration/1000000);

            System.out.println("Transmitted");
            fis.close();
        }
        catch (Exception e){
            System.out.println("error "+e.getMessage());
        }

////////////////////

        s.close();
        ss.close();
    }
}
