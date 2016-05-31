package opdracht2;

import org.apache.commons.lang3.builder.*;

public class Adres {
    
	private int adres_id;
    private String straatnaam;
    private String postcode;
    private String toevoeging;
    private int huisnummer;
    private String woonplaats;
    
    public Adres() {}
    
    public int getAdresID() {
    	return adres_id;
    }
    
    public void setAdresID(int adres_id) {
    	this.adres_id = adres_id;
    }
    
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
    			+ toevoeging + ", postcode: " + postcode + ", woonplaats: " + woonplaats 
    			+ ", adres_id: " + adres_id;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 45).append(straatnaam).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Adres))
            return false;
        if (obj == this)
            return true;

        Adres nieuwAdres = (Adres) obj;
        return new EqualsBuilder().
            append(straatnaam, nieuwAdres.getStraatnaam()).
            isEquals();
    }
}