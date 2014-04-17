package deadlineteam.admission.hienthitudien.controller;

import java.text.DateFormat;

import deadlineteam.admission.hienthitudien.service.DictionaryService;
import deadlineteam.admission.hienthitudien.validate.SendquestionValidate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import deadlineteam.admission.hienthitudien.domain.Setting;
import deadlineteam.admission.hienthitudien.domain.Dictionary;
import deadlineteam.admission.hienthitudien.domain.Questionmanagement;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private DictionaryService DictionaryService;
	
	/*
	 * examble : http://localhost:8080/congcuquantri/
	 */
	private static final String url = "http://10.11.27.11:8080/congcuquantritudien/";
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home( 
			@RequestParam(value = "record", required = false, defaultValue= "10")int record,
			@RequestParam(value = "page", required = false, defaultValue= "1")int page,HttpSession session,Locale locale, Model model) {
		if(session.getValue("Record")==null){
			session.setAttribute("Record", record);
			session.setAttribute("CurrentRecord",record);
			model.addAttribute("testrecord", "");
			model.addAttribute("curentkeyword", "");
			List<Dictionary> list = DictionaryService.getalldictionary(page-1, record);
			model.addAttribute("curentrecord",record);
			for(int i = 0; i< list.size();i++){
				int number = (i+1) + ((page-1)*record) ;
				list.get(i).setID(number);
			}
			
			model.addAttribute("listdictionary", list);
			model.addAttribute("noOfPages", DictionaryService.totalPage(record));
			model.addAttribute("question", new Questionmanagement());
		}else{
			int newrecord = Integer.parseInt(session.getAttribute("CurrentRecord").toString());
			List<Dictionary> list = DictionaryService.getalldictionary(page-1, newrecord);
			model.addAttribute("testrecord",newrecord);
			for(int i = 0; i< list.size();i++){
				int number = (i+1) + ((page-1)*newrecord) ;
				list.get(i).setID(number);
			}
			
			model.addAttribute("listdictionary", list);
			model.addAttribute("noOfPages", DictionaryService.totalPage(newrecord));
			model.addAttribute("question", new Questionmanagement());
		}
		
		return "home";
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
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String homepost(@RequestParam(value = "page", required = false, defaultValue= "1")int page,
			@RequestParam String actionsubmit ,
			@RequestParam(value = "setting", required = false, defaultValue= "0") String setting,
			@ModelAttribute("question") Questionmanagement questionmanagement,
			@RequestParam(value = "checkboxdata", required = false, defaultValue= "0") String checkboxdata,
			Model model,HttpSession session, BindingResult bindingResult){
		
		int record = Integer.parseInt(session.getAttribute("Record").toString());
		if(actionsubmit.equals("register")){
			SendquestionValidate sendquestion = new SendquestionValidate();
			sendquestion.validate(questionmanagement, bindingResult);
			  if (bindingResult.hasErrors()){
					List<Dictionary> listDictionary = DictionaryService.getalldictionary(page, record);
					model.addAttribute("listdictionary",listDictionary);
					model.addAttribute("question",questionmanagement);
					List<Dictionary> list = DictionaryService.getalldictionary(page-1, record);
					for(int i = 0; i< list.size();i++){
						int number = (i+1) + ((page-1)*record) ;
						list.get(i).setID(number);
					}
					model.addAttribute("noOfPages", DictionaryService.totalPage(record));
					model.addAttribute("listdictionary", list);
					model.addAttribute("testrecord", record);
					
		        	 return "home";
		        }else {
						if(checkemail(questionmanagement.getQuestionEmail())){
							RestTemplate restTemplate = new RestTemplate();
							String result = restTemplate.postForObject(url+"api/question", questionmanagement, String.class);
							if(result.equals("Issuccess")){
								model.addAttribute("message","Câu hỏi đã được gủi thành công.");
								List<Dictionary> list = DictionaryService.getalldictionary(page-1, record);
								for(int i = 0; i< list.size();i++){
									int number = (i+1) + ((page-1)*record) ;
									list.get(i).setID(number);
								}
								model.addAttribute("noOfPages", DictionaryService.totalPage(record));
								model.addAttribute("listdictionary", list);
							}else{
								if(result.equals("Emailinvalid")){
									model.addAttribute("error","Email không hợp lệ");
								}else{
									if(result.equals("Inputenough")){
										model.addAttribute("error","Vui lòng nhập đầy đủ thông tin");
									}
								}
							}
						}else{
							model.addAttribute("error","Email không hợp lệ");
						}
					model.addAttribute("question", new Questionmanagement());	
					return "home";
		        }
		}else{
			if(actionsubmit.equals("settingrecord")){
				int intSetting = Integer.parseInt(setting);
				if(intSetting > 0){
					session.setAttribute("Record", Integer.parseInt(setting));
					session.setAttribute("CurrentRecord", Integer.parseInt(setting));
					int newrecord = Integer.parseInt(session.getAttribute("Record").toString());
					model.addAttribute("curentrecord",newrecord);
					List<Dictionary> list = DictionaryService.getalldictionary(0, newrecord);
					for(int i = 0; i< list.size();i++){
						int number = (i+1) + ((0)*newrecord) ;
						list.get(i).setID(number);
					}
					model.addAttribute("listdictionary", list);
					model.addAttribute("testrecord", newrecord);
					model.addAttribute("noOfPages", DictionaryService.totalPage(newrecord));
					model.addAttribute("currentPage", 1);
					
				}else{
					model.addAttribute("note", "Bạn phải nhập số lớn hơn 0");
					model.addAttribute("testrecord", intSetting);
				}
				model.addAttribute("question", new Questionmanagement());	
				return "home";
			}else{
				List<Dictionary> list = DictionaryService.searchIdex(actionsubmit);
				for(int i = 0; i< list.size();i++){
					int number = (i+1) + ((page-1)*record) ;
					list.get(i).setID(number);
				}
	
				model.addAttribute("curentkeyword", actionsubmit);
				model.addAttribute("listdictionary", list);
				model.addAttribute("question", new Questionmanagement());	
				return "home";
			}	
		}
	}
	
}
