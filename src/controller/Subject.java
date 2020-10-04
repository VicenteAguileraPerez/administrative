package controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import utility.helpers.FilesHelper;
import utility.helpers.JSONHelper;
import utility.helpers.StaticHelper;
import utility.helpers.VerifiedInformationHelper;

@WebServlet("/subjects")
public class Subject extends HttpServlet
{

	private FilesHelper files = new FilesHelper(StaticHelper.DATABASENAME, "");
	private VerifiedInformationHelper  v = new VerifiedInformationHelper();
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String page = request.getParameter(StaticHelper.PAGE);
		String idSubject = request.getParameter(StaticHelper.ID);
		response.setContentType("text/json");
		// Get all the students
		if(page!=null && page.equals(StaticHelper.SUBJECTS) && idSubject==null)
		{
			
			
			
			try
			{
				JSONArray jsonArray = files.readDataJson();
				
				if(jsonArray.isEmpty()) 
				{
					response.sendError(HttpServletResponse.SC_NO_CONTENT);
				}
				else
				{
					response.getWriter().println(jsonArray);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("It has a mistake");
				files=null;
				response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			}
		}
		// Get a the students
		else if(page!=null && page.equals(StaticHelper.SUBJECT))
		{
			
			try
			{
				
				if(idSubject!=null && !idSubject.isBlank()) 
				{
					System.out.println("params"+page+" "+idSubject);
					JSONArray jsonArray = new JSONHelper().searchInformation(files.readDataJson(), idSubject, StaticHelper.ID);
					if(jsonArray.isEmpty()) 
					{
						response.sendError(HttpServletResponse.SC_NO_CONTENT);
					}
					else
					{
						System.out.println("Subject" + jsonArray);
						response.getWriter().println(jsonArray);
					}
					
				}
				else 
				{
					response.sendError(HttpServletResponse.SC_NO_CONTENT);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("It has a mistake");
				response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
				files=null;
			}
			
			
			
		}
		//Inform a mistake because the option is not avaliable
		else
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			
			System.out.println("Error");
		}
		
		
		
		
		

		
	}
	
	//Create a new Student
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
		//200 o 500
		
		try {
			String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			
			
			response.setContentType("text/json");
		
			JSONHelper jsonHelper = new JSONHelper();
			JSONObject jsonObject = jsonHelper.getNewJsonObject(data);
			
			
			jsonObject = v.verifyData(jsonObject);
			if(jsonObject!=null)
			{
				
				String id = files.saveData(jsonObject,  true);
				
				if(id!=null)
				{
					
					response.sendError(HttpServletResponse.SC_OK);
					
				}
				else 
				{
					System.out.println("files");
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					
				}
			}
			else
			{
				System.out.println("verificacion");
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);	
			}
			
		}
		catch(Exception e) 
		{
			
			System.out.println("It has a mistake");
	        e.printStackTrace();
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			
		}
		
	}
	
	//Update an existing student
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//No id wee need to send all the data and return id
		//200 o 500
		
		try {
			String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			
			System.out.println(data);
			response.setContentType("text/json");
		
		
			JSONHelper jsonHelper = new JSONHelper();
			JSONObject jsonObject = jsonHelper.getNewJsonObject(data);
			
			
			jsonObject = v.verifyData(jsonObject);
			if(jsonObject!=null)
			{
				jsonObject = v.verifyData(jsonObject);
				
				System.out.println(jsonObject);
				String id = files.saveData(jsonObject,StaticHelper.UPDATING);
				
				if(id!=null)
				{
					response.sendError(HttpServletResponse.SC_OK);	
				}
				else 
				{
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
			}
			else
			{
				System.out.println("verificacion");
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);	
			}
		}
		catch(Exception e) 
		{
			
			System.out.println("It has a mistake");
	        e.printStackTrace();
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			
		}
		
	}
	
	//Delete an existing student
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		//200 o 500
		
		try {
			String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			
			System.out.println(data);
			response.setContentType("text/json");
		
		
			JSONHelper jsonHelper = new JSONHelper();
			JSONObject jsonObject = jsonHelper.getNewJsonObject(data);
			System.out.println(jsonObject);
			String id = files.saveData(jsonObject,StaticHelper.DELETING);
			
			if(id!=null)
			{
				response.sendError(HttpServletResponse.SC_OK);	
			}
			else 
			{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}	
		}
		catch(Exception e) 
		{
			
			System.out.println("It has a mistake");
	        
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			
		}
		
	}

}

