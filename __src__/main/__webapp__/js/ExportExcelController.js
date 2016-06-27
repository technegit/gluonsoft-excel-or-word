app.controller('ExportExcelController', 
    							['$scope', '$rootScope', '$timeout', '$translate', 'Notification', '$sce','$http',
                        function($scope, $rootScope, $timeout, $translate, Notification, $sce, $http) {
        
						       /**
						        * export json in excel file
						        */
						       $scope.exportInExcel = function(titles, data, fileName){
						    	   var json = 
						    		   JSON.stringify(
						    				   { "titles" : titles , "data": data } , 
						    				   function( key, value ) {
									    		    if( key === "$$hashKey" ) {
									    		        return undefined;
									    		    }
									    		    else if(key == 'ativo'){
									    		    	return value ? 'Sim' : 'Não';
									    		    }
									    		    
									    		    
									    		    return value;
						    		});
						    	   
						    	   if(fileName == undefined || fileName == null){
						    		   fileName = 'dataExcel_'+Date.now()+'_.xlsx';
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

