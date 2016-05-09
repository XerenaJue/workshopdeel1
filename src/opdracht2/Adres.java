package opdracht2;

public class Adres {
    
    private String straatnaam;
    private String postcode;
    private String toevoeging;
    private int huisnummer;
    private String woonplaats;
    
    public Adres() {}
    
    public String getStraatnaam() {
        return straatnaam;        
    }
    
    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }
    
    public String getPostcode() {
        return postcode;
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    
    public String getToevoeging() {
        return toevoeging;
    }
    
    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }
    
    public int getHuisnummer() {
        return huisnummer;
    }
    
    public void setHuisnummer(int huisnummer) {
        this.huisnummer = huisnummer;
    }
    
    public String getWoonplaats() {
        return woonplaats;
    }
    
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }
    
    public String toString() {
    	return "Straatnaam: " + straatnaam + ", huisnummer: " + huisnummer + ", toevoeging: " 
    			+ toevoeging + ", postcode: " + postcode + ", woonplaats: " + woonplaats;
    }
}