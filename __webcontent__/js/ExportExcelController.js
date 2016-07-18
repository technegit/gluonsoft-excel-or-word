app.controller('ExportExcelController', 
    							['$scope', '$rootScope', '$timeout', '$translate', 'Notification', '$sce','$http',
                        function($scope, $rootScope, $timeout, $translate, Notification, $sce, $http) {
        
						       /**
						        * export json in excel file
						        */
						       $scope.exportInExcel = function(){
						    	   
						    	  var titles = JSON.parse(event.srcElement.attributes.eowtitles.value);
						    	  var data = JSON.parse(event.srcElement.attributes.eowdatasourcedata.value);
						    	  var fileName = event.srcElement.attributes.eowfilename.value;
						    	   
						    	  var columnIndex = null;
							       
							       if(event.srcElement.attributes.eowcolumnindex.value.length > 0)
							    	   columnIndex = JSON.parse(event.srcElement.attributes.eowcolumnindex.value);
						    	  
						    	   var json = JSON.stringify(
					    				   { "titles" : titles , "columnIndex": columnIndex , "data": data} , 
					    				   function( key, value ) {
								    		    if( key === "$$hashKey" ) {
								    		        return undefined;
								    		    }
								    		    return value;
						    		});
						    	   
						    	   if(fileName == undefined || fileName == null || fileName.length == 0){
						    		   fileName = 'dataExcel_'+Date.now()+'_.xlsx';
						    	   }else{
						    	   	fileName = fileName+'.xlsx';
						    	   } 
						    	   
						    	   var a = document.createElement("a");
						    	   document.body.appendChild(a);
						    	   a.style = "display: none";
						    	   
						    	   $http({
						    		    url: 'api/rest/gluonsoft/eowexport/excel',
						    		    method: "POST",
						    		    data: json, //this is your json data string
						    		    headers: {'Content-type': 'application/json'},
						    		    responseType: 'arraybuffer'
						    		}).success(function (data, status, headers, config) {
						    		    var blob = new Blob([data], {type: "octet/stream"});
						    		    var url = URL.createObjectURL(blob);
						    		    
						    		    a.href = url;
						    	        a.download = fileName;
						    	        a.click();
						    	        window.URL.revokeObjectURL(url);
						    		}).error(function (data, status, headers, config) {});
						           
						       }; 
    					}
	]);	

