package bed;

public class Bed {

    private String PatientID;
    private String DoctorID;
    private String BedID;
    private boolean Attribute;

    public Bed(){

    }

    //setters
    public void setPatientID(String name){
        this.PatientID=name;
    }

    public void setAttribute(boolean bedStatus){
        this.Attribute=bedStatus;
    }

    public void setBedID(String bed_id){
        this.BedID=bed_id;
    }


    //getters
    public String getPatientID(){
        return PatientID;
    }

    public String getDoctorID(){
        return DoctorID;
    }

    public String getBedID() {
        return BedID;
    }

    public boolean getAttribute() {
        return Attribute;
    }

    public void checkinPatient(String PatientID) {
        this.PatientID = PatientID;
        Attribute=true;

    }

    public void checkoutPatient(String PatientID, String DoctorID, String BedID) {

        if (Attribute==true) {
            this.PatientID = "NA";
            this.DoctorID = "NA";
            this.BedID = BedID;
            Attribute = false;
        }

    }

    public void AssignDoctor(String PatientID, String DoctorID, boolean Attribute) {

        if (Attribute==true) {
            this.DoctorID = DoctorID;
        }
    }


}