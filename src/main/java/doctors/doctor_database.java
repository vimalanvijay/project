package doctors;

import java.util.ArrayList;

public class doctor_database {

    private ArrayList<Doctor> doctorList =new ArrayList<>();//Array stores all doctors

    public void addDoctor(Doctor d){
        doctorList.add(d);
    }

    public void PrintDoctor(Doctor d){

        for(Doctor doc: doctorList){
            System.out.println("First: " +doc.getFirstname());
            System.out.println("Last: "+doc.getLastname());
            System.out.println("ID: " +doc.getId());
            System.out.println("Workload: " +doc.getWorkload());

        }
    }
}
