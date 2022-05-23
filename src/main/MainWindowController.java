package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainWindowController {
    public Pane mainPane;
    public Button CP_goBackBtn;
    public ProgressBar CP_Progressbar;
    public Label CP_ProgressLabel;
    public TextField RP_IPAtReciver;
    public TextField RP_passwordAtReciver;
    public Button fSP_ileChooserBtn;
    public Button RP_getFileBtn;
    public Button SP_sendFileBtn;

    public Label SP_passwordLabel;
    public Label SP_IPLAbel;
    public Label sharerTitle;
    @FXML private Pane titlePane;
    @FXML private ImageView btnMinimize, btnClose;
    @FXML private Label lblResult;


    @FXML private Button SP_fileChooserBtn;
   public Label SP_selectedFileLabel;
    public File SelectedFile = null;
    public FileInputStream fis = null;


    private double x, y;
    private double num1 = 0;
    private String operator = "+";



    public void init(Stage stage) {
        titlePane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        titlePane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-x);
            stage.setY(mouseEvent.getScreenY()-y);
        });

        SP_fileChooserBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                SelectedFile = fileChooser.showOpenDialog(stage);
                SP_selectedFileLabel.setText(SelectedFile.getName());

                try {
                    fis = new FileInputStream(SelectedFile);
                } catch (Exception e) {
                    System.out.println("file not found");
                    SelectedFile = null;
                }
            }
        });

        btnClose.setOnMouseClicked(mouseEvent -> stage.close());
        btnMinimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));



    }


    BackgroundFill background_fill = new BackgroundFill(Color.PINK,
            CornerRadii.EMPTY, Insets.EMPTY);
    Background background = new Background(background_fill);
    Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));


    @FXML private Pane MP_sendBtn;
    @FXML private Pane MP_reciveBtn;
    @FXML private Pane senderPane;
    @FXML private Pane reciverPane;
    @FXML private Pane commonPane;


    InetAddress addr = null;

    public void MP_sendBtnClicked(MouseEvent mouseEvent) throws UnknownHostException {
        System.out.println("MF send Button Clicked");
        mainPane.setVisible(false);
        senderPane.setVisible(true);
        commonPane.setVisible(true);






        int port = 4747;
        SP_passwordLabel.setText(String.valueOf(port));

        //ServerSocket ss = new ServerSocket(port);
        String thisIP = null;
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            thisIP = socket.getLocalAddress().getHostAddress();
        }
        catch(Exception e){
            System.out.println("error");
        }

        addr = InetAddress.getByName(thisIP);
        SP_IPLAbel.setText(String.valueOf(addr));

    }

    public void MP_mouseOntoSendBtn(MouseEvent mouseEvent) {
        MP_sendBtn.setStyle("-fx-background-color: black");
    }

    public void MP_mouseOutofSendBtn(MouseEvent mouseEvent) {
        MP_sendBtn.setStyle("opacity:1");
    }

    public void MP_reciveBtnClicked(MouseEvent mouseEvent) throws UnknownHostException {
        System.out.println("MF Recive Button Clicked");
        mainPane.setVisible(false);
        reciverPane.setVisible(true);
        commonPane.setVisible(true);



  }

    public void MP_mouseOntoReciveBtn(MouseEvent mouseEvent) {
        //reciveBtn.setBackground(background);
        //reciveBtn.setBorder(border);
        MP_reciveBtn.setStyle("-fx-background-color: black");
    }

    public void MP_mouseOutofReciveBtn(MouseEvent mouseEvent) {
        MP_reciveBtn.setStyle("opacity:1");
    }


    ////////////////////////////////////////////^^^^^^^^^^^^^^^^^Main panel complete^^^^^^^^^^^^^^^^^/////////////

    ///// COMMAN PANE  ///////////////

    public void CP_goBackBtnClicked(MouseEvent mouseEvent) {
        System.out.println("go back");
        mainPane.setVisible(true);
        senderPane.setVisible(false);
        reciverPane.setVisible(false);
        commonPane.setVisible(false);

    }


    /////SEND PANEL ////////



    public void SP_SendFilseBTnClicked(MouseEvent mouseEvent) throws IOException {
        System.out.println("send file btn clicked");














        int port = 4747;
// and now you can pass it to your socket-constructor
        ServerSocket ss = new ServerSocket(port, 50, addr);

        Socket s = ss.accept();

        PrintStream ps = new PrintStream(s.getOutputStream());
        Map<Byte,Integer> map = new HashMap<>();
//////////////////
        try{



            byte[] data = new byte[fis.available()];
            System.out.println(data.length);

            ps.println(SelectedFile.getName());
            ps.println(data.length);

            fis.read(data);
            long startTime = System.nanoTime();
            System.out.println("started");
            int id = 0;
            for (int i=0;i< data.length;i++) {
                if(i==3436440){
                    break;
                }
                if(map.containsKey(data[i])){
                    ps.println("id="+map.get(data[i]));
                    //System.out.println("id="+map.get(data[i])+" : "+data[i]);
                }else{
                    id++;
                    map.put(data[i],id);
                    //System.out.println("id="+map.get(data[i])+" : "+data[i]);
                    ps.println(data[i]);
                }
                //wait = sc.nextLine();
                //System.out.println(i+"/"+ data.length);
            }
            System.out.println("ended");
            long endTime = System.nanoTime();

            long duration = (endTime - startTime);

            System.out.println("Transmitted in : " + duration/1000000);
            fis.close();
        }
        catch (Exception e){
            System.out.println("error "+e.getMessage());
        }

////////////////////

        s.close();
        ss.close();












    }


    ////// RECIVER PANE    //////////////////
@FXML private TextField RP_IPInput;
@FXML private TextField RP_passwordInput;
    public void RP_getFileBtnClicked() throws IOException {

        System.out.println("getting file");



        System.setProperty("java.net.preferIPv4Stack","true");
        Scanner sc = new Scanner(System.in);
        String serverIP = RP_IPInput.getText();
        int port = Integer.parseInt(RP_passwordInput.getText());
        Socket s = new Socket(serverIP, port);

//10,48,576
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String fileName = br.readLine();
        int size = Integer.parseInt(br.readLine());
        byte[] data = new byte[size];
        String str;
        HashMap<Integer,Byte> map = new HashMap<>();

        int id =0;
        for(int i=0;i<size;i++) {
            str = br.readLine();
            //System.out.println("reciver string = "+str);
            if(str.startsWith("id=")){

                try{
                    data[i] = map.get(Integer.parseInt(str.substring(3)));}
                catch (Exception e){
                    //System.out.println(str.substring(3)+"not found in map");
                }
            }else{
                id++;
                byte b = Byte.parseByte(str);
                //System.out.println("new byte recived = "+b+" put onto id= "+id);
                map.put(id,b);
                data[i] = b;
            }
//            data[i] = Byte.parseByte(br.readLine());
            // System.out.println(data[i]);
        }
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.close();










    }
}
