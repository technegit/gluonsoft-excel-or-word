package br.com.techne.gluonsoft.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

import br.com.techne.gluonsoft.builder.ExcelBuilder;

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
	 		byte [] bytes =ExcelBuilder.createExcelBytes(DataBeanStatic.TITLES, DataBeanStatic.getData());
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
