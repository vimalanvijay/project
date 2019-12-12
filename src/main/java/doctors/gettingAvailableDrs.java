package doctors;

import database_conn.clientDoctor;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import bed.assignBed;
import database_conn.connectDatabase;

public class gettingAvailableDrs {
    private clientDoctor dr =new clientDoctor();
    connectDatabase conn = new connectDatabase();

    private ArrayList<String> timetable=dr.getDoctorTimetable();

    //gets the dr and their timetable
    private HashMap<String,String>doctors=dr.getDoctorAndTimetable();

    private String timeDate;
    private String dayToday;
    private String timeNow;

    //DEBUG
    private String testTime="09";
    private String TESTDAY="Thurs";

    private ArrayList<String>availableDr=new ArrayList<>();
    private ArrayList<String>notAvail=new ArrayList<>();

    public gettingAvailableDrs() throws IOException, SQLException {
        decodeTime();
    }

    private void decodeTime() throws SQLException {
        setCurrentTime();

        for(Map.Entry<String, String> entry : doctors.entrySet()) {
            String tt = entry.getValue();
            String drname = entry.getKey();

            StringBuilder bld = new StringBuilder();

            //System.out.println(tt);
            String[] splitTimetable = tt.split(" ");

           // System.out.println(drname);

            //converts the coded timetable to Java standard date and time
            //this will allows to check if dr is on duty
            for (int i = 0; i < splitTimetable.length; i++) {

                switch (splitTimetable[i]) {
                    case "1a":
                        String time = "Mon 00";
                        splitTimetable[i] = time;
                        break;
                    case "1b":
                        String time2 = "Mon 08";
                        splitTimetable[i] = time2;
                        break;
                    case "1c":
                        String time3 = "Mon 16";
                        splitTimetable[i] = time3;
                        break;
                    case "2a":
                        String time4 = "Tues 00";
                        splitTimetable[i] = time4;
                        break;
                    case "2b":
                        String time5 = "Tues 08";
                        splitTimetable[i] = time5;

                        break;
                    case "2c":
                        String time6 = "Tues 16";
                        splitTimetable[i] = time6;

                        break;
                    case "3a":
                        String time7 = "Wed 00";
                        splitTimetable[i] = time7;

                        break;
                    case "3b":
                        String time21 = "Wed 08";
                        splitTimetable[i] = time21;

                        break;
                    case "3c":
                        String time8 = "Wed 16";
                        splitTimetable[i] = time8;
                        break;
                    case "4a":
                        String time9 = "Thu 00";
                        splitTimetable[i] = time9;
                        break;
                    case "4b":
                        String time10 = "Thu 08";
                        splitTimetable[i] = time10;
                        break;
                    case "4c":
                        String time11 = "Thu 16";
                        splitTimetable[i] = time11;
                        break;
                    case "5a":
                        String time12 = "Fri 00";
                        splitTimetable[i] = time12;
                        break;
                    case "5b":
                        String time13 = "Fri 08";
                        splitTimetable[i] = time13;
                        break;
                    case "5c":
                        String time14 = "Fri 16";
                        splitTimetable[i] = time14;
                        break;
                    case "6a":
                        String time15 = "Sat 00";
                        splitTimetable[i] = time15;
                        break;
                    case "6b":
                        String time16 = "Sat 08";
                        splitTimetable[i] = time16;
                        break;
                    case "6c":
                        String time17 = "Sat 16";
                        splitTimetable[i] = time17;
                        break;
                    case "7a":
                        String time18 = "Sun 00";
                        splitTimetable[i] = time18;
                        break;
                    case "7b":
                        String time19 = "Sun 08";
                        splitTimetable[i] = time19;
                        break;
                    case "7c":
                        String time20 = "Sun 16";
                        splitTimetable[i] = time20;
                        break;
                    default:
                        System.out.println("Invalid");
                }
                bld.append(splitTimetable[i]+",");
            }

            String timetable=bld.toString();
            //System.out.println();

            //replaces coded timetable to new one
            doctors.replace(drname,tt,timetable);
        }

        for(Map.Entry<String, String> entry : doctors.entrySet()){
            //send each dr time timetable
            //get if the dr is available

            //send dr name
            whichDrisAvailableNow(entry.getKey());
            System.out.println();
        }
    }

    private void setCurrentTime(){
        //get the current time from the system
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat timeFormat = new SimpleDateFormat("HH"); //get only the hours
        timeNow=timeFormat.format(date);

        dayToday=new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());
        StringBuilder bld= new StringBuilder();
        bld.append(dayToday+" "+timeNow);
        timeDate= bld.toString();
    }

    private void whichDrisAvailableNow(String name){

        //get the doctor's timetable
        String timeTable= doctors.get(name);

        //if the doctor's timetable has todays day we then check the shift-time
        if(timeTable.contains(dayToday)){

            String[] split=timeTable.split(",");

            for(int i=0;i<split.length;i++){

                String[] time=split[i].split(" ");
                String day=time[0];
                String times=time[1];
                int timeDr=Integer.parseInt(times);
                int timenow=Integer.parseInt(timeNow);

                //System.out.println(timeDr-timenow);

                //checks which shift NOW
                timenow=checkTimeShift(timenow);
               // System.out.println(timenow);

                //check which shift dr
                timeDr=checkTimeShift(timeDr);
               // System.out.println(timeDr);

                String[]splitName=name.split(" ");

                if( (day.equals(dayToday)) && timenow==timeDr ){
                    System.out.println("Dr "+name+" available now");
                    availableDr.add(name);
                }else{

                }
            }

        }else{
            notAvail.add(name);
            System.out.println("Dr "+name+" not available today");
        }
    }

    private int checkTimeShift(int time_in){
        //return 1 if shift 1
        //return 2 if shift 2
        //return 3 if shift 3

        if(time_in>=0&&time_in<8){
            return 1;
        }else if(time_in>=8&&time_in<16){
            return 2;
        }else
            return 3;
    }

    public ArrayList<String> getAvailableDr(){
        return availableDr;
    }

    public ArrayList<String> getNotAvail(){
        return notAvail;
    }



}
