package deadlineteam.admission.hienthitudien.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import deadlineteam.admission.hienthitudien.service.DictionaryService;
import deadlineteam.admission.hienthitudien.domain.Dictionary;

@Controller
@RequestMapping("api")
public class RESTful_Controller {
	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping(value="question", method=RequestMethod.POST)
	@ResponseBody
	public String savequestion(@RequestBody Dictionary dictionary) {
		String message = "";
		if(checkinput(dictionary) == true){
			Dictionary newquestion = new Dictionary();
			newquestion.setID(dictionary.getID());
			newquestion.setQuestion(dictionary.getQuestion());
			newquestion.setAnwser(dictionary.getAnwser());
			Date now = new Date();
			newquestion.setCreateDate(now);
			dictionaryService.updatequestion(newquestion);
			message = message+ "update question is success";
		}else{
			message = message +"update question is fail";	
		}
	
		return message;
	}
	private boolean checkinput(Dictionary dictionary){
		if(dictionary.getQuestion() != null 
				&& dictionary.getAnwser() != null
				&& dictionary.getID() != null){
			return true;
		}else{
			return false;
		}
		
	}
	@RequestMapping(value="romovequestion", method=RequestMethod.POST)
	@ResponseBody
	public String removequesstion(@RequestBody Dictionary dictionary) {
		String message = "";
		if(checkinput(dictionary) == true){
			dictionaryService.deleteUser(dictionary);
			message = message+ "remove is success";
		}else{
			message = message +"remove is fail";	
		}
	
		return message;
	}
}
