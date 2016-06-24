package br.com.techne.gluonsoft.eowexport.tests;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
			"Nome:",
			"Data de Cadastro:",
			"Salário:"};
	
	public static List<HashMap<String,Object>> getData(){
		List<HashMap<String,Object>> rows=new ArrayList<HashMap<String,Object>>();

		for (int i = 0; i < 50; i++) {
			HashMap<String,Object> row = new HashMap<String, Object>();
			row.put("id", UUID.randomUUID().toString());
			row.put("name", "Fulano "+i);
			row.put("date", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
			row.put("mojo", NumberFormat.getCurrencyInstance().format(Math.random() * 4000));
			rows.add(row);
		} 

		return rows;
	}
	
}
