package test.br.com.techne.gluonsoft.eowexport;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

import org.junit.Test;

import br.com.techne.gluonsoft.eowexport.builder.WordBuilder;


/**
 * Teste de criação de arquivo word com tabela
 * @author roberto.silva
 *
 */
public class WordBuilderTest{
	
        
    /**
     * Rigourous Test :-)
     */
	@Test
    public void runTest(){
    	
    	try {
    		Locale locale=new Locale("pt", "BR");
     		byte [] bytes = WordBuilder.createStyledTable(DataBeanStatic.TITLES, new String[0], DataBeanStatic.getData(),locale);
     		File dir=new File("testsFiles/");
	 		dir.mkdir();
	 		File wordFile = new File(dir,"wordFileTest.docx");
	 	    FileOutputStream fos=new FileOutputStream(wordFile);
	 	    try{
	 	    	fos.write(bytes);
	 	    } finally {
				fos.close();
			}
	 		assertTrue(wordFile.isFile());
	 		wordFile.delete();
	 		assertTrue(dir.isDirectory());
	 		dir.delete();
     	}catch(Exception e) {
     		System.out.println("Error trying to create styled table.");
     		e.printStackTrace();
     		assertFalse(e.getMessage() , true);
     	}        
    }
}
