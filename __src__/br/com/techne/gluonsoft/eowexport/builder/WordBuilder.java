package br.com.techne.gluonsoft.eowexport.builder;

/* ====================================================================
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==================================================================== */

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

/**
 * 
 * Classe de utilidade cria documento Office Word
 * lib usada Apache POI : https://poi.apache.org/
 * 
 * @author roberto.silva
 *
 */
public abstract class WordBuilder {
 
 /**
  * método cria bytes de documento Excel
  * @param titles
  * @param columnIndex
  * @param dataRows
  * @param locale
  * @return
  * @throws Exception
  */
 public static byte[] createStyledTable(
		 									String[] titles, 
		 									String [] columnIndex, 
		 									List<HashMap<String,Object>> dataRows, 
		 									Locale locale) throws Exception {
						
	 // Create a new document from scratch
     XWPFDocument doc = new XWPFDocument();
     byte[] outBytes;
     try {
         int nRows = dataRows.size()+1;
         int nCols = dataRows.get(0).keySet().size();
                  
         XWPFTable table = doc.createTable(nRows, nCols);

         // Set the table style. If the style is not defined, the table style
         // will become "Normal".
         CTTblPr tblPr = table.getCTTbl().getTblPr();
         CTString styleStr = tblPr.addNewTblStyle();
         styleStr.setVal("StyledTable");

         // Get a list of the rows in the table
         List<XWPFTableRow> rows = table.getRows();
        
         int rowCt = 1;
         
         addTitle(rows, titles);
        
         ValueCellUtil vcutil=new ValueCellUtil(locale);
         
         for(HashMap<String, Object> dataRow : dataRows){
        	 addRow(rows, dataRow, rowCt, vcutil , columnIndex);
        	 rowCt++;
         }
                
         // write for return byte[]
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         try {
             doc.write(out);
             outBytes = out.toByteArray();
         } finally {
             out.close();
         }
     } finally {
         doc.close();
     }
     
     return outBytes;
  }
 
 /**
  * adiciona linha á tabela
  * @param rows
  * @param data
  * @param indexRow
  * @param vcutil
  * @param columnIndex
  */
  private static void addRow(
		  						List<XWPFTableRow> rows, 
		  						HashMap<String,Object> data, 
		  						int indexRow, 
		  						ValueCellUtil vcutil, 
		  						String [] columnIndex){
	  //adicionando titulo
      XWPFTableRow row = rows.get(indexRow);
      // get table row properties (trPr)
      CTTrPr trPr = row.getCtRow().addNewTrPr();
      // set row height; units = twentieth of a point, 360 = 0.25"
      CTHeight ht = trPr.addNewTrHeight();
      ht.setVal(BigInteger.valueOf(360));
      // get the cells in this row
      List<XWPFTableCell> cells = row.getTableCells();
           
      List<String> keysAttribs = null;
      if(columnIndex.length == 0){
			keysAttribs = Arrays.asList(data.keySet().toArray(new String[0]));
			Collections.reverse(keysAttribs);
		}else{
			keysAttribs = Arrays.asList(columnIndex);
		}
      
      //List<String> keysAttribs = Arrays.asList(data.keySet().toArray(new String[0]));
	  //Collections.reverse(keysAttribs);
	  int colCt = 0;//counter cells
	  
      // add content to each cell
      for (XWPFTableCell cell : cells) {
          // get a table cell properties element (tcPr)
          CTTcPr tcpr = cell.getCTTc().addNewTcPr();
          // set vertical alignment to "center"
          CTVerticalJc va = tcpr.addNewVAlign();
          va.setVal(STVerticalJc.CENTER);

          // create cell color element
          CTShd ctshd = tcpr.addNewShd();
          ctshd.setColor("auto");
          ctshd.setVal(STShd.CLEAR);
          
          //zebrando tabela
         if (indexRow % 2 == 0) {
              // even row
              ctshd.setFill("D3DFEE");
          } else {
              // odd row
              ctshd.setFill("EDF2F8");
          }

          // get 1st paragraph in cell's paragraph list
          XWPFParagraph para = cell.getParagraphs().get(0);
          // create a run to contain the content
          XWPFRun rh = para.createRun();
          
         
          if(data.get(keysAttribs.get(colCt)) == null){
        	  data.put(keysAttribs.get(colCt), "");
          }else{
        	  // other rows
        	  rh.setText(vcutil.parseValue(data.get(keysAttribs.get(colCt))).toString());
        	  para.setAlignment(ParagraphAlignment.LEFT);
          }
        	  
          colCt++;
      } // for cell
  }
  
  /**
   * adiciona titulo
   * @param rows
   * @param titles
   */
  private static void addTitle(List<XWPFTableRow> rows, String[] titles){
	  //adicionando titulo
      XWPFTableRow row = rows.get(0);
      // get table row properties (trPr)
      CTTrPr trPr = row.getCtRow().addNewTrPr();
      // set row height; units = twentieth of a point, 360 = 0.25"
      CTHeight ht = trPr.addNewTrHeight();
      ht.setVal(BigInteger.valueOf(360));
      // get the cells in this row
      List<XWPFTableCell> cells = row.getTableCells(); 
      int colCt = 0;
     
      //CTTblWidth tblWidth = CTTblWidth.Factory.newInstance();
      //tblWidth.setW(BigInteger.valueOf(200));
      //tblWidth.setType(STTblWidth.DXA);
      
      // add content to each cell
      for (XWPFTableCell cell : cells) {
          // get a table cell properties element (tcPr)
          CTTcPr tcpr = cell.getCTTc().addNewTcPr();
          //tcpr.setTcW(tblWidth);
          
          // set vertical alignment to "center"
          CTVerticalJc va = tcpr.addNewVAlign();
          va.setVal(STVerticalJc.CENTER);
          
          // create cell color element
          CTShd ctshd = tcpr.addNewShd();
          ctshd.setColor("auto");
          ctshd.setVal(STShd.CLEAR);
          // header row
          ctshd.setFill("A7BFDE");
          // get 1st paragraph in cell's paragraph list
          XWPFParagraph para = cell.getParagraphs().get(0);
          // create a run to contain the content
          XWPFRun rh = para.createRun();
          
          // header row
          if((titles.length - 1) < colCt){
        	  rh.setText("");
          }else
        	  rh.setText(titles[colCt]);
          
          rh.setBold(true);
          para.setAlignment(ParagraphAlignment.CENTER);
          
          colCt++;
      } // for cell
  }
}