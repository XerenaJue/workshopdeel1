package builderinsertHenny;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Annotations.*;

public class BuildInsertStatement {
	private final static Logger LOGGER = LoggerFactory.getLogger(BuildInsertStatement.class);
	
	public String buildInsertString(Object o) {
		String query = "";
		int waardeToevoegen = 0;
		
		StringBuilder sb = new StringBuilder();
		StringBuilder waarde = new StringBuilder();
		waarde.append(" VALUES (");
		
		if (o.getClass().isAnnotationPresent(Table.class)) {
			String tableName = o.getClass().getAnnotation(Table.class).tableName();
			sb.append("INSERT INTO " + tableName + "(");
			LOGGER.info("annotatie gebruikt");
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
						LOGGER.info("anotatie gebruikt voor column naam");
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
				LOGGER.info("Oeps gaat mis " + e);				
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
}
