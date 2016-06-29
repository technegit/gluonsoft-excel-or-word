package br.com.techne.gluonsoft.eowexport.builder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * classe faz o tratamento do dado valor contido em um java.util.Map
 * @author roberto.silva
 *
 */
public class ValueCellUtil {
	
	/**
	 * construtor default
	 */
	public ValueCellUtil() {
		this(Locale.getDefault());
	}
	
	/**
	 * construtor com locale para setar i18n dos formatos
	 * @param locale
	 */
	public ValueCellUtil(Locale locale) {
		this.locale = locale;
		this.dateTimeFmt = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
		this.dateFmt = DateFormat.getDateInstance(DateFormat.DEFAULT , this.locale);
		this.moneyFmt = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	}
	
	private Locale locale;
	private DateFormat dateTimeFmt;
	private DateFormat dateFmt;
	private NumberFormat moneyFmt;
	
	/**
	 * devolve o valor legivel a ser impresso na célula
	 * @param o
	 * @return
	 */
	public Object parseValue(Object o){
		   
		   if (o.getClass().getSimpleName().toUpperCase().equals("STRING"))
		      return o;
		    
		    else if (o.getClass().getSimpleName().toUpperCase().equals("TIMESTAMP"))
		      return dateTimeFmt.format((Date) o);
		    
		    else if (o.getClass().getSimpleName().toUpperCase().equals("BIGDECIMAL")){
		      BigDecimal b = (BigDecimal) o;
		      return (b.doubleValue());
		    }  
		    
		    else if (o.getClass().getSimpleName().toUpperCase().equals("DOUBLE"))
		      return moneyFmt.format((Double) o);
		
		    else if (o.getClass().getSimpleName().toUpperCase().equals("INTEGER"))
		      return (Integer) o;
		
		    else if (o.getClass().getSimpleName().toUpperCase().equals("BIGINTEGER")){
		      BigInteger i = (BigInteger) o;
		      Integer.valueOf(i.toString());
		    }
		    
		    else if (o.getClass().getSimpleName().toUpperCase().equals("LONG"))
		      return dateFmt.format(new Date((Long) o));
		
		    else if (o.getClass().getSimpleName().toUpperCase().equals("FLOAT"))
		      return (Float) o;
		   
		    else
		      return "";
		
		return o;
	}
}
