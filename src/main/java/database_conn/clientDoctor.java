package database_conn;

import database_conn.connectDatabase;
import javafx.print.Collation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class clientDoctor {

    private connectDatabase conn = new connectDatabase();

    private ArrayList<String>doctorNames=new ArrayList<>();
    private ArrayList<String>doctorTimetable=new ArrayList<>();

    private HashMap<String, String> doctorAndTimetable = new HashMap<String,String>();


    public clientDoctor() throws IOException, SQLException {
        makepostRequest();
    }

    //so from my database it will send the name to website
    private void sendDrName() throws IOException, SQLException {

        Statement s = conn.createStatement();
        String sql = "SELECT firstname,lastname from doctors WHERE id>1 ORDER BY firstname; ";
        ResultSet rset = s.executeQuery(sql);

        while(rset.next()) {
            String name = rset.getString("firstname");

            byte[] body = name.getBytes(StandardCharsets.UTF_8);
            String webURL = "https://hospitalae.herokuapp.com/mypatients_page";

            URL myURL = new URL(webURL);
            HttpURLConnection connect = null;
            connect = (HttpURLConnection) myURL.openConnection();

            // Set up the header
            connect.setRequestMethod("POST");
            connect.setRequestProperty("Accept", "text/html");
            connect.setRequestProperty("charset", "utf-8");
            connect.setRequestProperty("Content-Length", Integer.toString(body.length));
            connect.setDoOutput(true);


            try (OutputStream outputStream = connect.getOutputStream()) {
                outputStream.write(body, 0, body.length);
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connect.getInputStream(), "utf-8"));
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {
                System.out.println(inputLine);
            }

        }
       // bufferedReader.close();
    }

    //this method will get from website
    public void makepostRequest() throws IOException {

        String message = "";
        byte[] body = message.getBytes(StandardCharsets.UTF_8);

        URL myURL = new URL("https://hospitalae.herokuapp.com/send_timetable");
        HttpURLConnection conn = null;
        conn = (HttpURLConnection) myURL.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "text/html");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(body.length));
        conn.setDoOutput(true);

        // Write the body of the request
        try (OutputStream outputStream = conn.getOutputStream()) {
            outputStream.write(body, 0, body.length);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        String inputLine;

        ArrayList<String> rawData=new ArrayList<>();

        // Read the body of the response
        while ((inputLine = bufferedReader.readLine()) != null){
            rawData.add(inputLine);
        }

        bufferedReader.close();

        for(int i=0;i<rawData.size();i++){
            if((i & 1) == 0 ){
                doctorNames.add(rawData.get(i));
            }else{
                doctorTimetable.add(rawData.get(i));
            }
        }
        setHash();

        doctorNames.removeAll(doctorNames);
        doctorTimetable.removeAll(doctorTimetable);
        rawData.removeAll(rawData);
    }

    private void setHash(){
        for(int i=0;i<doctorNames.size();i++){
            doctorAndTimetable.put(doctorNames.get(i),doctorTimetable.get(i));
        }
    }

    public HashMap<String,String> getDoctorAndTimetable(){
        return doctorAndTimetable;
    }

    public ArrayList<String> getDoctorNames(){
        return doctorNames;
    }

    public ArrayList<String> getDoctorTimetable(){
        return doctorTimetable;
    }


}


