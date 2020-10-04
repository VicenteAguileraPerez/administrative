package utility.helpers;

import java.util.ArrayList;

import org.json.JSONObject;

public class VerifiedInformationHelper 
{
	
	
	public JSONObject verifyData(JSONObject jsonObject)
	{
		
		JSONHelper jsonHelper = new JSONHelper();
		StringHelper stringHelper = new StringHelper();
		boolean boolsubject = false,boolname= false,boolcreation= false,boolcareer= false,boolresponsable= false
				,boolweb= false,boolcity= false,boolstate= false,boolsep= false;
		ArrayList<String> keys = jsonHelper.returnKey(jsonObject);
		
		//[sep-registry, career, responsable, notes, city, web-qr, name, creation-date, subject-number, state]
		System.out.println(keys);
		
		String subject_number = jsonObject.getString(keys.get(8));
		String name = jsonObject.getString(keys.get(6));
		String creation_date = jsonObject.getString(keys.get(7));
		String career = jsonObject.getString(keys.get(1));
		String responsable = jsonObject.getString(keys.get(2));
		String city = jsonObject.getString(keys.get(4));
		String state = jsonObject.getString(keys.get(9));
		String sep_registry = jsonObject.getString(keys.get(0));
		String web_qr = jsonObject.getString(keys.get(5));
		String notes = jsonObject.getString(keys.get(3));	
			
	
		if(stringHelper.justNumbersEqualToLimit(subject_number, 8))
		{
			boolsubject = true;
		}
		if(name.length()<=50)
		{
			name=  stringHelper.CapitalLetterEachWordStatic(name);
			System.out.println("name"+name);
			jsonObject.put("name",name);
			
			boolname=true;
		}
		if(stringHelper.date(creation_date))
		{
			
			boolcreation=true;
		}
		
		if(career.length()==3)
		{
			career =career.toUpperCase();
			System.out.println("carrera"+career);
			jsonObject.put("career", career);
			boolcareer=true;
		}
		if(responsable.length()<=200)
		{
			responsable=  stringHelper.CapitalLetterEachWordStatic(responsable);
			System.out.println("responsable"+responsable);
			jsonObject.put("responsable",responsable);
			boolresponsable=true;
		}
		if(stringHelper.justNumbersEqualToLimit(sep_registry, 12))
		{
			
			boolsep=true;
		}
		if(stringHelper.URL(web_qr))
		{
			
			boolweb=true;
		}
		
		if(!notes.isBlank())
		{
			notes = stringHelper.firstCapitalLetter(notes);
			jsonObject.put("notes", notes);
		}
		if(!city.isBlank())
		{
			city = stringHelper.firstCapitalLetter(city);
			
			jsonObject.put("city", city);
			boolcity=true;
		}
		if(!state.isBlank())
		{
			state = stringHelper.firstCapitalLetter(state);
			jsonObject.put("state", state);
			boolstate=true;
		}
		System.out.println(boolsubject+" "+boolname+" "+boolcreation+" "+boolcareer+" "+boolresponsable+" "+boolweb+" "+boolcity+" "+boolstate+" "+boolsep);
		if( boolsubject && boolname && boolcreation && boolcareer && boolresponsable && boolweb && boolcity && boolstate && boolsep) 
		{
			
			return jsonObject;
		}
		else {
			return null;
		}
		
	}

}
