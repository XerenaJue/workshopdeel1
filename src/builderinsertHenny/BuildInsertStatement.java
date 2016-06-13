package builderinsertHenny;

import java.lang.reflect.Field;

import Annotations.*;
import POJO.Adres;
import POJO.Klant;

public class BuildInsertStatement {
	
	public String buildInsertString(Object o) {
		String query = "";
		int waardeToevoegen = 0;
		
		StringBuilder sb = new StringBuilder();
		StringBuilder waarde = new StringBuilder();
		waarde.append(" VALUES (");
		
		if (o.getClass().isAnnotationPresent(Table.class)) {
			String tableName = o.getClass().getAnnotation(Table.class).tableName();
			sb.append("INSERT INTO " + tableName + "(");
			System.out.println("annotatie gebruikt");
		} else {
			String className = o.getClass().getSimpleName();
			sb.append("INSERT INTO " + className + "(");
		};
		
		
		Field[] declaredFields = o.getClass().getDeclaredFields();
				
		for (int i = 0; i < declaredFields.length; i++) {
			declaredFields[i].setAccessible(true);
			
			try {
				if (declaredFields[i].get(o) != null && !isPrimitiveZero(declaredFields[i].get(o))) {
					waardeToevoegen++;
					if (waardeToevoegen > 1) {
						sb.append(", ");
						waarde.append(", ");
					}
					/*if (declaredFields[i].isAnnotationPresent(Column.class)) {
						String columnNaam = declaredFields[i].getAnnotation(Column.class).columnName();
						System.out.println(columnNaam);
						sb.append(columnNaam);
						System.out.println("anotatie gebruikt voor column naam");
					} else { */
						sb.append(declaredFields[i].getName());
					//}
					if (declaredFields[i].get(o) instanceof String) {
						waarde.append("\'" + declaredFields[i].get(o) + "\'");
					} else {
						waarde.append(declaredFields[i].get(o));
					}										
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		sb.append(") " + waarde + ") ");
		query = sb.toString();
		return query;
	}
	
	//methode om te controleren of er een waarde aan een field is gekoppeld
	private boolean isPrimitiveZero(Object object) {
		boolean isPrimitiveZero = false;
		if (object instanceof Long)	{
			if ((Long) object == 0)	{
				isPrimitiveZero = true;
			}
		}
		else if (object instanceof Integer)	{
			if ((Integer) object == 0) {
				isPrimitiveZero = true;
			}
		}
		else if (object instanceof Float) {
			if ((Float) object == 0.0) {
				isPrimitiveZero = true;
			}
		}
		else if (object instanceof Double) {
			if ((Double) object == 0.0) {
				isPrimitiveZero = true;
			}
		}
		return isPrimitiveZero;
	}
	
	public static void main(String[] args) { // tijdelijke main methode om werking van de code te bekijken
		BuildInsertStatement bis = new BuildInsertStatement();
		Klant klant1 = new Klant();
		klant1.setVoornaam("Bob");
		klant1.setAchternaam("Bouwer");
		klant1.setTussenvoegsel("de");
		//bis.buildInsertString(klant1);
		System.out.println(bis.buildInsertString(klant1));
		
		Adres adres1 = new Adres();
		adres1.setHuisnummer(12);
		adres1.setStraatnaam("hoofdstraat");
		System.out.println(bis.buildInsertString(adres1));
	}
}
