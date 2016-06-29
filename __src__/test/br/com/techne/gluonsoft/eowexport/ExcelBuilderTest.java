package test.br.com.techne.gluonsoft.eowexport;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

import org.junit.Test;

import br.com.techne.gluonsoft.eowexport.builder.ExcelBuilder;

/**
 * Teste de criação de arquivo excel com tabela
 * @author roberto.silva
 *
 */
public class ExcelBuilderTest {
	
	/**
     * Rigourous Test :-)
     */
	@Test
    public void runTest(){
		try {	
			Locale locale=new Locale("pt", "BR");
	 		byte [] bytes =ExcelBuilder.createExcelBytes(DataBeanStatic.TITLES, DataBeanStatic.getData(), locale);
	 		File dir=new File("testsFiles/");
	 		dir.mkdir();
	 		File excelFile = new File(dir,"excelFileTest.xlsx");
	 	    FileOutputStream fos=new FileOutputStream(excelFile);
	 	    try{
	 	    	fos.write(bytes);
	 	    } finally {
				fos.close();
			}
	 		assertTrue(excelFile.isFile());
	 		excelFile.delete();
	 		assertTrue(dir.isDirectory());
	 		dir.delete();
	 	}catch(Exception e) {
	 		System.out.println("Error trying to create file excel.");
	 		e.printStackTrace();
	 		assertFalse(e.getMessage(), true);
	 	}      
	}	
}
