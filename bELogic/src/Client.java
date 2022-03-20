import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[]args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String serverIP = sc.nextLine();
        int port = sc.nextInt();
        Socket s = new Socket(serverIP, port);

//10,48,576
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String fileName = br.readLine();
        int size = Integer.parseInt(br.readLine());
        byte[] data = new byte[size];

        for(int i=0;i<size;i++) {
            data[i] = Byte.parseByte(br.readLine());
           // System.out.println(data[i]);
        }


        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.close();
    }
}
