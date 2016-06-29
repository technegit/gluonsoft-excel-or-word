# gluonsoft-excel-or-word-export

This Gluonsoft  do export of word and excel in your data, for example in data the table or anywhere type of json.

The use will be via rest api, after add the button will be a javascript method that is responsible for calling api rest.
Just enter the data correctly to send to rest api.

```
<!-- 
		    eowtitles = title of table is array with alias
            eowdatasourcedata= datasource.data "json array"
            eowcolumnindex = index of column is array with keys
            eowfilename= file name 
    	 -->
		<button class="btn btn-success fa fa-file-excel-o"
		              ng-click="exportInExcel()"
		              eowtitles="{{['Enter your titles']}}"
		              eowdatasourcedata="{{datasource.data}}"
		              eowcolumnindex = ""
		              eowfilename="">
		              Excel
		</button>

´´´
