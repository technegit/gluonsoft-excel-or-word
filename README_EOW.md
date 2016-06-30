#gluonsoft-excel-or-word-export

###Introduction
This gluonsoft is license of use APACHE 2.0, using library Apache POI for build this files word/excel and Jersey REST 2.2.1 for Web Service REST.

This Gluonsoft  do export of word and excel with data, for example in data the table or anywhere type of json.

###How to use
The use will be via rest api, after add the button will be a javascript method that is responsible for calling api rest.
Just enter the data correctly to send to rest api.

For execute this function is necessary attributes:
- eowtitles = title of table is array with alias;
- eowdatasourcedata = datasource.data "json array";

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

```

