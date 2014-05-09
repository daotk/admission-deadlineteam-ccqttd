package deadlineteam.admission.quantritudien.controller;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.service.QuestionManagement.Questionmanagement_SERVICE;


@Controller
@RequestMapping("api")
public class RESTfulController {
	@Autowired
	private Questionmanagement_SERVICE QuestionmanagementService;
	
	/**
	 * Saves new person. Spring automatically binds the name
	 * and age parameters in the request to the person argument
	 * @param person
	 * @return String indicating success or failure of save
	 */
	
	@RequestMapping(value="question", method=RequestMethod.POST)
	@ResponseBody
	public String savePerson(@RequestBody Questionmanagement question) {
		String message ="";
		if(checkquestion(question) == true ){
			if(checkemail(question.getQuestionEmail())){
				Questionmanagement createquestion = new Questionmanagement();
				createquestion.setQuestion(question.getQuestion());
				createquestion.setQuestionBy(question.getQuestionBy());
				createquestion.setQuestionEmail(question.getQuestionEmail());
				Date now = new Date();
				createquestion.setQuestionDate(now);
				createquestion.setStatus(1);
				createquestion.setDeleteStatus(0);	
				createquestion.setBusyStatus(0);
				QuestionmanagementService.addQuestionRESTful(createquestion);
				message = message+"Issuccess";
			}else{
				message = message+"Emailinvalid";
			}
			
		}else{
			message = message+"Inputenough";	
		}
		return  message;
	}
	private boolean checkquestion(Questionmanagement question){
		if( question.getQuestion() !="" &&
				question.getQuestionBy() != "" && question.getQuestionEmail() != ""){
			return true;
		}else{
			return false;	
		}	
	}
	private boolean checkemail(String email){
		Pattern pattern;
		Matcher matcher;
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (!(email != null && email.isEmpty())) {  
			   pattern = Pattern.compile(EMAIL_PATTERN);  
			   matcher = pattern.matcher(email);  
			   if (!matcher.matches()) {  
				   return false; 
			   }else{
				   return true;		   
			   }  
		}else{
			return false;
		}
		
		
	}
}
