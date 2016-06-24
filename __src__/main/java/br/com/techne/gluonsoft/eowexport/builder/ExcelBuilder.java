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


import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.awt.Color;
import java.io.ByteArrayOutputStream;

/**
 * 
 * Classe de utilidade cria documento Office Excel .xlsx
 * lib usada Apache POI : https://poi.apache.org/ 
 * 
 * @author roberto.silva
 *
 */
public abstract class ExcelBuilder {

	/**
	 * classe cria documento Excel e devolve retorno para criação download
	 * @param dataRows
	 * @param titles
	 * @throws Exception
	 */
	public static byte[] createExcelBytes(String[] titles, List<HashMap<String,Object>> dataRows) throws Exception {
		//Workbook wb = new HSSFWorkbook();
		XSSFWorkbook  wb = new XSSFWorkbook();
		byte [] outBytes;

		try{
			HashMap<String, CellStyle> styles = createStyles(wb);
			Sheet sheet = wb.createSheet("Tab 1");

			//turn off gridlines
			sheet.setDisplayGridlines(false);
			sheet.setPrintGridlines(false);
			sheet.setFitToPage(true);
			sheet.setHorizontallyCenter(true);
			PrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setLandscape(true);

			//the following three statements are required only for HSSF
			sheet.setAutobreaks(true);
			printSetup.setFitHeight((short)1);
			printSetup.setFitWidth((short)1);

			//the header row: centered text in 48pt font
			Row headerRow = sheet.createRow(0);
			headerRow.setHeightInPoints(12.75f);

			for (int indexColumn = 0; indexColumn < titles.length; indexColumn++) {
				Cell cell = headerRow.createCell(indexColumn);
				cell.setCellValue(titles[indexColumn]);
				cell.setCellStyle(styles.get("header"));
			}

			//freeze the first row
			sheet.createFreezePane(0, 1);

			Row row;
			Cell cell;
			int rownum = 1;//devido constar titulo, começa do indice 1
			
			for (int indexRow = 0; indexRow < dataRows.size(); indexRow++, rownum++) {

				row = sheet.createRow(rownum);
				HashMap<String, Object> dataRow = dataRows.get(indexRow);

				if(dataRow == null) continue;

				List<String> keysAttribs = Arrays.asList(dataRow.keySet().toArray(new String[0]));
				Collections.reverse(keysAttribs);

				int colCt = 0;

				for (String keyAttrib : keysAttribs) {

					cell = row.createCell(colCt);
					String styleName;
					cell.setCellValue(dataRow.get(keyAttrib).toString());

					//zebrando tabela
					if (indexRow % 2 == 0) {
						// even row
						styleName = "cell_normal_even";
					} else {
						// odd row
						styleName = "cell_normal_odd";
					}

					if(indexRow == 0){
						//setando auto ajuste
						sheet.autoSizeColumn(colCt);
					}

					cell.setCellStyle(styles.get(styleName));
					colCt++;
				}
			}

			sheet.setZoom(75); //75% scale

			// Write the output to a file
			// write for return byte[]
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				wb.write(out);
				outBytes = out.toByteArray();
			} finally {
				out.close();
			}
		} finally {
			wb.close();
		}

		return outBytes;
	}

	/**
	 * cria estilos de celulas
	 */
	private static HashMap<String, CellStyle> createStyles(XSSFWorkbook  wb){
		HashMap<String, CellStyle> styles = new HashMap<String, CellStyle>();

		XSSFCellStyle  style;
		Font headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(new XSSFColor(new Color(167, 191, 222)));
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("header", style);

		style = createBorderedStyle(wb);

		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(false);
		style.setFillForegroundColor(new XSSFColor(new Color(211, 223, 238)));
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styles.put("cell_normal_even", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(false);
		style.setFillForegroundColor(new XSSFColor(new Color(237, 242, 248)));
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styles.put("cell_normal_odd", style);

		return styles;
	}

	/**
	 * cria borda da tabela
	 * @param wb
	 * @return
	 */
	private static XSSFCellStyle  createBorderedStyle(XSSFWorkbook  wb){
		XSSFCellStyle  style = wb.createCellStyle();
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}
}
