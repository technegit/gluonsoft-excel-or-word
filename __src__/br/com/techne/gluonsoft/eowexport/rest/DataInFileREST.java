package br.com.techne.gluonsoft.eowexport.rest;

import java.io.IOException;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.com.techne.gluonsoft.eowexport.builder.ExcelBuilder;
import br.com.techne.gluonsoft.eowexport.builder.WordBuilder;
 
/**
 * @brief classe rest responsavel pela criação de json em arquivos excel ou word
 * @author roberto.silva
 *
 */
@Path("/eowexport")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_OCTET_STREAM)
public class DataInFileREST{
	
	private SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	private JSONParser jsonParser = new JSONParser();
	
	@Context
	private HttpServletRequest request;
	/**
	 * @brief somente para teste
	 * @return
	 */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response echo(){
		return Response.ok("{\"/api/rest/gluonsoft/eowexport\":\"status ok\"}").build();
	}
	
	/**
	 * @brief serviço faz parse de json em arquivo excel
	 * json enviado deve conter:
	 * title  - array de string com titulos
	 * data   - Map para preencher a tabela com os dados
	 * 
	 * @param data
	 * @return
	 */
    @POST
    @Path("/excel")
    @SuppressWarnings("all")
    public Response dataToDownloadExcelFile(String json){
    	
    	JSONObject jsonObject;
		try {
			jsonObject = (JSONObject)jsonParser.parse(json);
		} catch (ParseException e1) {
			throw new RuntimeException(e1);
		}
    	verifyParamsOfData(jsonObject);
    	
    	String [] titles = (String[]) ((JSONArray) jsonObject.get("titles")).toArray(new String[0]);
    	List<HashMap<String, Object>> rows = ((JSONArray)jsonObject.get("data"));
    	    	
        StreamingOutput fileStream =  new StreamingOutput(){
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException{
                try{
                    byte[] data = ExcelBuilder.createExcelBytes(titles, rows, request.getLocale());
                    output.write(data);
                    output.flush();
                }catch (Exception e){
                    throw new WebApplicationException("File Not Found !!");
                }
            }
        };
        String fileName = "dataToExcel_"+sdf.format(new Date())+"_.xlsx";
        
        return Response
                .ok(fileStream)
                .header("content-disposition","attachment; filename = "+fileName)
                .build();
    }  
    
    /**
	 * @brief serviço faz parse de json em arquivo word
	 * json enviado deve conter:
	 * title  - array de string com titulos
	 * data   - Map para preencher a tabela com os dados
	 * 
	 * @param data
	 * @return
	 */
    @POST
    @Path("/word")
    @SuppressWarnings("all")
    public Response dataToDownloadWordFile(String json){
    	
    	JSONObject jsonObject;
		try {
			jsonObject = (JSONObject)jsonParser.parse(json);
		} catch (ParseException e1) {
			throw new RuntimeException(e1);
		}
    	verifyParamsOfData(jsonObject);
    	
    	String [] titles = (String[]) ((JSONArray) jsonObject.get("titles")).toArray(new String[0]);
    	List<HashMap<String, Object>> rows = ((JSONArray)jsonObject.get("data"));
    	
        StreamingOutput fileStream =  new StreamingOutput(){
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException{
                try{
                    byte[] data = WordBuilder.createStyledTable(titles, rows, request.getLocale());
                    output.write(data);
                    output.flush();
                }catch (Exception e){
                    throw new WebApplicationException("File Not Found !!");
                }
            }
        };
        String fileName = "dataToWord_"+sdf.format(new Date())+"_.docx";
        
        return Response
                .ok(fileStream)
                .header("content-disposition","attachment; filename = "+fileName)
                .build();
    }
    
    /**
     * @brief verifica parametro em comum nos serviços acima
     * data deve conter atributos verificados abaixo 
     * @param data
     */
    @SuppressWarnings("all")
    private void verifyParamsOfData(JSONObject data){
    	if(data.get("titles") == null || ((JSONArray)data.get("titles")).size() == 0)
    		throw new WebApplicationException(Response.status(Status.BAD_REQUEST.getStatusCode()).entity("Atributo de \"titles\" não foram encontrados!").build());
    	
    	if(data.get("data") == null || ((JSONArray)data.get("data")).size() == 0)
    		throw new WebApplicationException(Response.status(Status.BAD_REQUEST.getStatusCode()).entity("Atributo de \"data\" não foram encontrados!").build());
    }
}
