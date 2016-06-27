package test.br.com.techne.gluonsoft.eowexport;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * fornece objeco
 * @author roberto.silva
 *
 */
public class DataBeanStatic {

	public final static String [] TITLES = {
			"ID:",
			"Name:",
			"Registration Date:",
			"Salary:",
			"Active:",
			"Birth Date:"};
	
	public static List<HashMap<String,Object>> getData(){
		List<HashMap<String,Object>> rows=new ArrayList<HashMap<String,Object>>();

		for (int i = 0; i < 50; i++) {
			LinkedHashMap<String,Object> row = new LinkedHashMap<String, Object>();
			row.put("id", UUID.randomUUID().toString());
			row.put("name", "Fulano "+i);
			row.put("registration", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			row.put("mojo", NumberFormat.getCurrencyInstance().format(Math.random() * 4000));
			row.put("active", true);
			row.put("birth", "1983-08-29");
			rows.add(row);
		} 
		
		return rows;
	}
	
}
