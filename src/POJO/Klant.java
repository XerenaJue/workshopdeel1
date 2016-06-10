package POJO;

import Annotations.*;

@Entity (className = "KLANT")
@Table (tableName = "KLANT")
public class Klant {
	
	@ID
	@Column
	private int klant_id;
	@Column
    private String voornaam, achternaam, tussenvoegsel, email;

    public int getKlantID() {
        return klant_id;
    }

    public void setKlantID(int klantID) {
        this.klant_id = klantID;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Klant{" + "klantID=" + klant_id + ", voornaam=" + voornaam + 
                ", achternaam=" + achternaam + ", tussenvoegsel=" + tussenvoegsel + ", email=" + email + '}';
    }
}
