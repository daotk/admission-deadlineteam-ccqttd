package deadlineteam.admission.quantritudien.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import deadlineteam.admission.quantritudien.util.AeSimpleMD5;











import deadlineteam.admission.quantritudien.bean.DictionaryBean;
import deadlineteam.admission.quantritudien.bean.UsersBean;
import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.DictionaryRestful;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;
import deadlineteam.admission.quantritudien.service.Dictionary.Dictionary_SERVICE;
import deadlineteam.admission.quantritudien.service.QuestionManagement.Questionmanagement_SERVICE;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;
import deadlineteam.admission.quantritudien.validator.ChangePassValidator;
import deadlineteam.admission.quantritudien.validator.DictionaryValidator;
import deadlineteam.admission.quantritudien.validator.LoginValidator;
import deadlineteam.admission.quantritudien.validator.RegisterValidator;
import deadlineteam.admission.quantritudien.util.*;
/**
 * Handles requests for the application home page.
 */
@Controller
public class DictionaryController {
	@Autowired
	private Users_SERVICE userService;

	@Autowired
	private Questionmanagement_SERVICE QuestionmanagementService;
	
	@Autowired
	private Dictionary_SERVICE DictionaryService;
	
	//url = http://localhost:8080/congcuquantri
	private static final String url = "http://10.11.27.12:8080/congcuhienthitudien";
	
	private int check = 47;
	private int get = 44;

	private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/editdictionary", method = RequestMethod.GET)
	public String editdictionary(
			@RequestParam(value = "topic", required = false, defaultValue= "0")int Id,
		Model model, HttpSession session) {
		if(session.getValue("login") == null){
			model.addAttribute("error", "notlogin");
			return "redirect:/";
		}else{
			session.setAttribute("Id", Id);
			Dictionary available = DictionaryService.loadquestion(Id);
			
			if(available.getBusyStatus() ==1){
				return "list-dictionary";
			}else{
				DictionaryService.busystatusupdate(Id);
			}
			model.addAttribute("createQaA", available);
		}
		
		return "edit-dictionary";
	}
	
	@RequestMapping(value = "/editdictionary", method = RequestMethod.POST)
	public String editdictionarypost( 	
			@RequestParam String actionsubmit , 
			@ModelAttribute("createQaA") Dictionary diction,
			Model model,
			HttpSession session) {
		int UserID =Integer.parseInt(session.getAttribute("login").toString());
		int page =Integer.parseInt(session.getAttribute("Page").toString());
		if(actionsubmit.equals("save")){
			int Id = Integer.parseInt(session.getAttribute("Id").toString());// get ID
			model.addAttribute("dictionary",Id);
			if(session.getAttribute("Id") !="0"){
				int result = DictionaryService.update(Id, diction.getAnwser(), diction.getQuestion());
				int restart = DictionaryService.busystatus(Id);
			
				List<Dictionary> Avaiable= DictionaryService.availablelist(page-1,UserID);
				model.addAttribute("Avaiable", Avaiable);
			}
		}
		List<Dictionary> Avaiable= DictionaryService.availablelist(page-1,UserID);
		for(int i=0;i < Avaiable.size();i++){
			if(Avaiable.get(i).getQuestion().length() >= check){
				String abc = Avaiable.get(i).getQuestion().toString();
				Avaiable.get(i).setQuestion(abc.substring(0, get)+ ".....");
			}
		}
		model.addAttribute("Avaiable", Avaiable);
		return "list-dictionary";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/editdictionary2", method = RequestMethod.GET)
	public String edit2(
			@RequestParam(value = "topic", required = false, defaultValue= "0")int Id,
		Model model, HttpSession session) {
		if(session.getValue("login") == null){
			model.addAttribute("error", "notlogin");
			return "redirect:/";
		}else{
			session.setAttribute("Id", Id);
			Dictionary available = DictionaryService.loadquestion(Id);
			
			if(available.getBusyStatus() ==1){
				return "list-dictionary";
			}else{
				DictionaryService.busystatusupdate(Id);
			}
			model.addAttribute("createQaA", available);
		}
		return "edit-dictionary";
	}
	
	@RequestMapping(value = "/editdictionary2", method = RequestMethod.POST)
	public String edit2post( 	
			@RequestParam String actionsubmit , 
			@ModelAttribute("createQaA") Dictionary diction,
			Model model,
			HttpSession session) {
		int UserID =Integer.parseInt(session.getAttribute("login").toString());
		int page =Integer.parseInt(session.getAttribute("Page").toString());
		if(actionsubmit.equals("save")){
			int Id = Integer.parseInt(session.getAttribute("Id").toString());// get ID
			model.addAttribute("dictionary",Id);
			if(session.getAttribute("Id") !="0"){
				int result = DictionaryService.update(Id, diction.getAnwser(), diction.getQuestion());
				int restart = DictionaryService.busystatus(Id);
				List<Dictionary> remove= DictionaryService.removelist(page-1, UserID);
				model.addAttribute("removelist", remove);
				
			}
		}
		List<Dictionary> remove= DictionaryService.removelist(page-1, UserID);
		for(int i=0;i < remove.size();i++){
			if(remove.get(i).getQuestion().length() >= check){
				String abc = remove.get(i).getQuestion().toString();
				remove.get(i).setQuestion(abc.substring(0, get)+ ".....");
			}
		}
		model.addAttribute("removelist", remove);
		return "list-dictionary-down";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/botudien", method = RequestMethod.GET)
	public String botudien(
		@RequestParam(value = "topic", required = false, defaultValue= "0")int Id, 
		@RequestParam(value = "page", required = false, defaultValue= "1")int page,
		Model model, HttpSession session) {
		
		
	if(session.getValue("login") == null){
		model.addAttribute("error", "notlogin");
		return "redirect:/";
	}else{
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		if(Id==0){		
			int UserId = Integer.parseInt(session.getAttribute("UserId").toString());
			session.setAttribute("Id", "0");
			session.setAttribute("Page",page );
			List<Dictionary> Avaiable= DictionaryService.availablelist(page-1,UserID);
			for(int i=0;i < Avaiable.size();i++){
				if(Avaiable.get(i).getQuestion().length() >= check){
					String abc = Avaiable.get(i).getQuestion().toString();
					Avaiable.get(i).setQuestion(abc.substring(0, get)+ ".....");
				}
			}
			model.addAttribute("Avaiable", Avaiable);
			model.addAttribute("diction", new Dictionary());
			
			Setting setting = userService.getSetting(UserId);
			
			int numOfRecord = setting.getRecordDictionary();
			int numOfPagin = setting.getPaginDisplayDictionary();
			
			model.addAttribute("numOfRecord", ""+numOfRecord);
			model.addAttribute("numOfPagin", ""+numOfPagin);
			
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(5, UserID));
			model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
			String user = userService.getFullnameByID(UserID);
			model.addAttribute("username", user);
			model.addAttribute("curentOfPage", page);
			return "list-dictionary";
			
		}else{
			session.setAttribute("Id", Id);
			session.setAttribute("Page",page );	
			
			List<Dictionary> Avaiable= DictionaryService.availablelist(page-1, UserID);			
			for(int i=0;i < Avaiable.size();i++){
				if(Avaiable.get(i).getQuestion().length() >= check){
					String abc = Avaiable.get(i).getQuestion().toString();
					Avaiable.get(i).setQuestion(abc.substring(0, get)+ ".....");
				}
			}
			model.addAttribute("Avaiable", Avaiable);				
			Dictionary available = DictionaryService.availablequestion(Id);
			Users newusername = DictionaryService.getusername(available.getAnwserBy());
			model.addAttribute("username", newusername.getFullName().toString());
			model.addAttribute("diction", available);
			
			Setting setting = userService.getSetting(UserID);
			
			int numOfRecord = setting.getRecordDictionary();
			int numOfPagin = setting.getPaginDisplayDictionary();
			
			model.addAttribute("numOfRecord", ""+numOfRecord);
			model.addAttribute("numOfPagin", ""+numOfPagin);

			String user = userService.getFullnameByID(UserID);
			model.addAttribute("username", user);
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(5, UserID));
			model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
			
			model.addAttribute("curentOfPage", page);
			if( available.getBusyStatus()== 1){
				model.addAttribute("busystatus",null);
			}else{
				model.addAttribute("busystatus", available.getBusyStatus().toString());
			}	
			return "list-dictionary";
		}
	}
}

	@RequestMapping(value = "/botudien", method = RequestMethod.POST)
	public String botudienpost( 	
			@RequestParam String actionsubmit , 
			@RequestParam(value = "checkboxdata", required = false, defaultValue= "0") String checkboxdata, 
			@RequestParam(value = "change-items", required = false, defaultValue= "0") String changeitems, 
			@RequestParam(value = "change-pagin", required = false, defaultValue= "0") String changepagin, 
			@ModelAttribute("dictionary") Dictionary diction,
			Model model,
			HttpSession session) {
			//get page
			int UserID = Integer.parseInt(session.getAttribute("login").toString());
			int page =Integer.parseInt(session.getAttribute("Page").toString());
			//Load deleted-question list of page that is selected			
			List<Dictionary> Avaiable= DictionaryService.availablelist(page-1, UserID);
			model.addAttribute("Avaiable", Avaiable);	
			model.addAttribute("dictionary", new Dictionary());
			model.addAttribute("curentOfPage", page);	
			
			if(actionsubmit.equals("upload")){
				// restore question
				int Id = Integer.parseInt(session.getAttribute("Id").toString());// get ID
				model.addAttribute("dictionary",Id);
				if(session.getAttribute("Id") !="0"){
					// Processing restore question
					int result = DictionaryService.upload(Id);	
					int update = DictionaryService.updateby(Id, UserID);
					if(result > 0 && update >0){
						String user = userService.getFullnameByID(UserID);
						model.addAttribute("username", user);
						List<Dictionary> Avaiable1= DictionaryService.availablelist(page-1, UserID);			
						for(int i=0;i < Avaiable1.size();i++){
							if(Avaiable1.get(i).getQuestion().length() >= check){
								String abc = Avaiable1.get(i).getQuestion().toString();
								Avaiable1.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						model.addAttribute("Avaiable", Avaiable1);
					}
					Dictionary newdictionary = DictionaryService.getinformation(Id);
					
					DictionaryRestful dicrestful = new DictionaryRestful();
					dicrestful.setID(Id);
					dicrestful.setAnwser(newdictionary.getAnwser());
					dicrestful.setQuestion(newdictionary.getQuestion());
					
					try{
						RestTemplate restTemplate = new RestTemplate();
						String result1 = restTemplate.postForObject(url+"/api/question", dicrestful, String.class);
						if(result1.equals("success")){
							model.addAttribute("message","Đăng câu hỏi thành công");
						}else{
							if(result1.equals("fail")){
								model.addAttribute("message","Đăng câu hỏi không  thành công");
							}
						}
						
					}catch(Exception e){
						model.addAttribute("error","Đăng câu hỏi không thành công");
					}
					
				}									
			}else{
				if(actionsubmit.equals("delete")){
					// restore question
					int Id = Integer.parseInt(session.getAttribute("Id").toString());// get ID
					int userID = Integer.parseInt(session.getAttribute("login").toString());// get ID
					model.addAttribute("dictionary",Id);
					if(session.getAttribute("Id") !="0"){
						// Processing restore question
						int result = DictionaryService.delete(Id);
						DictionaryService.updatedelete(Id, userID);
						List<Dictionary> Avaiable1= DictionaryService.availablelist(page-1, UserID);
						for(int i=0;i < Avaiable1.size();i++){
							if(Avaiable1.get(i).getQuestion().length() >= check){
								String abc = Avaiable1.get(i).getQuestion().toString();
								Avaiable1.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						String user = userService.getFullnameByID(UserID);
						model.addAttribute("username", user);
						model.addAttribute("Avaiable", Avaiable1);
						model.addAttribute("message","Câu hỏi đã được xóa");
					}					
				}else{
					if(actionsubmit.equals("change")){
						if(!changeitems.equals("0")){
							int numOfRecord = Integer.parseInt(changeitems);
							int numOfPagin = Integer.parseInt(changepagin);
							userService.UpdateSettingDictionary(UserID, numOfRecord, numOfPagin);
					
							model.addAttribute("numOfRecord",changeitems);
							model.addAttribute("numOfPagin",changepagin);
							Setting setting = userService.getSetting(UserID);
							model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(5, UserID));
							model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
							
							List<Dictionary> avaiable= DictionaryService.availablelist(0, UserID);
							for(int i=0;i < avaiable.size();i++){
								if(avaiable.get(i).getQuestion().length() >= check){
									String abc = avaiable.get(i).getQuestion().toString();
									avaiable.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
							String user = userService.getFullnameByID(UserID);
							model.addAttribute("username", user);
							model.addAttribute("savequestionlist", avaiable);
							model.addAttribute("message","Thay đổi cấu hình thành công.");
	
						}
					}else{
					// Tim kiem
						List<Dictionary> avaiable= DictionaryService.searchIdex(actionsubmit, "1", UserID);
						for(int i=0;i < avaiable.size();i++){
							if(avaiable.get(i).getQuestion().length() >= check){
								String abc = avaiable.get(i).getQuestion().toString();
								avaiable.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						model.addAttribute("savequestionlist", avaiable);
					}
				}
			}			
			List<Dictionary> Avaiable1= DictionaryService.availablelist(page-1, UserID);
			for(int i=0;i < Avaiable1.size();i++){
				if(Avaiable1.get(i).getQuestion().length() >= check){
					String abc = Avaiable1.get(i).getQuestion().toString();
					Avaiable1.get(i).setQuestion(abc.substring(0, get)+ ".....");
				}
			}
			String user = userService.getFullnameByID(UserID);
			model.addAttribute("username", user);
			model.addAttribute("Avaiable", Avaiable1);	
			model.addAttribute("dictionary", new Dictionary());
			model.addAttribute("curentOfPage", page);		
			return "list-dictionary";
	}

	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/botudiendaxoa", method = RequestMethod.GET)
	public String botudiendaxoa(
		@RequestParam(value = "topic", required = false, defaultValue= "0")int Id, 
		@RequestParam(value = "page", required = false, defaultValue= "1")int page,
		Model model, HttpSession session) {
		
	if(session.getValue("login") == null){
		model.addAttribute("error", "notlogin");
		return "redirect:/";
	}else{
		int userID = Integer.parseInt(session.getAttribute("login").toString());
		if(Id==0){
			session.setAttribute("Id", "0");
			session.setAttribute("Page",page );
			List<Dictionary> delete= DictionaryService.deletelist(page-1, userID);
			for(int i=0;i < delete.size();i++){
				if(delete.get(i).getQuestion().length() >= check){
					String abc = delete.get(i).getQuestion().toString();
					delete.get(i).setQuestion(abc.substring(0, get)+ ".....");
				}
			}
			Setting setting = userService.getSetting(userID);
			
			int numOfRecord = setting.getRecordDictionary();
			int numOfPagin = setting.getPaginDisplayDictionary();
			
			model.addAttribute("numOfRecord", ""+numOfRecord);
			model.addAttribute("numOfPagin", ""+numOfPagin);
			
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(8, userID));
			model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
			
			String user = userService.getFullnameByID(userID);
			model.addAttribute("username", user);
			model.addAttribute("deletelist", delete);
			model.addAttribute("diction", new Dictionary());
			model.addAttribute("curentOfPage", page);
			return "list-dictionary-delete";
			
		}else{
			session.setAttribute("Id", Id);
			session.setAttribute("Page",page );	
			List<Dictionary> deletelist= DictionaryService.deletelist(page-1, userID);	
			for(int i=0;i < deletelist.size();i++){
				if(deletelist.get(i).getQuestion().length() >= check){
					String abc = deletelist.get(i).getQuestion().toString();
					deletelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
				}
			}
			model.addAttribute("deletelist", deletelist);
			Setting setting = userService.getSetting(userID);
			
			int numOfRecord = setting.getRecordDictionary();
			int numOfPagin = setting.getPaginDisplayDictionary();
			
			model.addAttribute("numOfRecord", ""+numOfRecord);
			model.addAttribute("numOfPagin", ""+numOfPagin);
			
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(8, userID));
			model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
			Dictionary delete = DictionaryService.question(Id);
			model.addAttribute("diction", delete);
			String user = userService.getFullnameByID(userID);
			model.addAttribute("username", user);
			Users newusername = DictionaryService.getusername(delete.getAnwserBy());
			Users deleteuser = DictionaryService.getusername(delete.getDeleteBy());
			model.addAttribute("username", newusername.getFullName().toString());
			model.addAttribute("deleteuser", deleteuser.getFullName().toString());
			model.addAttribute("curentOfPage", page);
			return "list-dictionary-delete";
		}
	}
}

	@RequestMapping(value = "/botudiendaxoa", method = RequestMethod.POST)
	public String botudiendaxoapost( 	
			@RequestParam String actionsubmit , 
			@RequestParam(value = "change-items", required = false, defaultValue= "0") String changeitems, 
			@RequestParam(value = "change-pagin", required = false, defaultValue= "0") String changepagin, 
			@ModelAttribute("dictionary") Dictionary diction,
			Model model,
			HttpSession session) {
			int userID = Integer.parseInt(session.getAttribute("login").toString());
			//get page
			int page =Integer.parseInt(session.getAttribute("Page").toString());
			//Load deleted-question list of page that is selected
			
			List<Dictionary> deletelist= DictionaryService.deletelist(page, userID);
			for(int i=0;i < deletelist.size();i++){
				if(deletelist.get(i).getQuestion().length() >= check){
					String abc = deletelist.get(i).getQuestion().toString();
					deletelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
				}
			}
			model.addAttribute("deletelist", deletelist);
			
			
			model.addAttribute("dictionary", new Dictionary());
			model.addAttribute("curentOfPage", page);
			
			if(actionsubmit.equals("restore")){
				// restore question
				int Id = Integer.parseInt(session.getAttribute("Id").toString());// get ID
				model.addAttribute("dictionary",Id);
				if(session.getAttribute("Id") !="0"){
					// Processing restore question
					int result = DictionaryService.restore(Id);
					List<Dictionary> dele= DictionaryService.deletelist(page-1,userID);
					for(int i=0;i < dele.size();i++){
						if(dele.get(i).getQuestion().length() >= check){
							String abc = dele.get(i).getQuestion().toString();
							dele.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
					Setting setting = userService.getSetting(userID);
					
					int numOfRecord = setting.getRecordDictionary();
					int numOfPagin = setting.getPaginDisplayDictionary();
					
					model.addAttribute("numOfRecord", ""+numOfRecord);
					model.addAttribute("numOfPagin", ""+numOfPagin);
					
					model.addAttribute("curentOfPage",page);
					model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(8, userID));
					model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
					String user = userService.getFullnameByID(userID);
					model.addAttribute("username", user);
					model.addAttribute("deletelist", dele);
					model.addAttribute("message", "Câu hỏi đã được khôi phục");
				}						
			}else{
				if(actionsubmit.equals("change")){
					if(!changeitems.equals("0")){
						int numOfRecord = Integer.parseInt(changeitems);
						int numOfPagin = Integer.parseInt(changepagin);
						userService.UpdateSettingDictionary(userID, numOfRecord, numOfPagin);
				
						model.addAttribute("numOfRecord",changeitems);
						model.addAttribute("numOfPagin",changepagin);
						Setting setting = userService.getSetting(userID);
						model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(8, userID));
						model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
						
						List<Dictionary> dele= DictionaryService.deletelist(0,userID);
						for(int i=0;i < dele.size();i++){
							if(dele.get(i).getQuestion().length() >= check){
								String abc = dele.get(i).getQuestion().toString();
								dele.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						model.addAttribute("deletelist", dele);
						String user = userService.getFullnameByID(userID);
						model.addAttribute("username", user);
						
						model.addAttribute("message","Thay đổi cấu hình thành công.");

					}
				}else{
				// Tim kiem
					List<Dictionary> dele= DictionaryService.searchIdex(actionsubmit, "4", userID);
					for(int i=0;i < dele.size();i++){
						if(dele.get(i).getQuestion().length() >= check){
							String abc = dele.get(i).getQuestion().toString();
							dele.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
					model.addAttribute("deletelist", dele);
				}
			}
			return "list-dictionary-delete";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/botudiendaha", method = RequestMethod.GET)
	public String botudiendaha(
		@RequestParam(value = "topic", required = false, defaultValue= "0")int Id, 
		@RequestParam(value = "page", required = false, defaultValue= "1")int page,
		Model model, HttpSession session) {
		
	if(session.getValue("login") == null){
		model.addAttribute("error", "notlogin");
		return "redirect:/";
	}else{
		int UserID =Integer.parseInt(session.getAttribute("login").toString());
		if(Id==0){
			session.setAttribute("Id", "0");
			session.setAttribute("Page",page );
			List<Dictionary> remove= DictionaryService.removelist(page-1, UserID);
			for(int i=0;i < remove.size();i++){
				if(remove.get(i).getQuestion().length() >= check){
					String abc = remove.get(i).getQuestion().toString();
					remove.get(i).setQuestion(abc.substring(0, get)+ ".....");
				}
			}
			model.addAttribute("removelist", remove);
			//------------
			Setting setting = userService.getSetting(UserID);
			
			int numOfRecord = setting.getRecordDictionary();
			int numOfPagin = setting.getPaginDisplayDictionary();
			
			model.addAttribute("numOfRecord", ""+numOfRecord);
			model.addAttribute("numOfPagin", ""+numOfPagin);
			
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(7, UserID));
			model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
			String user = userService.getFullnameByID(UserID);
			model.addAttribute("username", user);
			//-----------
			model.addAttribute("diction", new Dictionary());
			model.addAttribute("curentOfPage", page);
			return "list-dictionary-down";
			
		}else{
			session.setAttribute("Id", Id);
			session.setAttribute("Page",page );	
			List<Dictionary> remove= DictionaryService.removelist(page-1, UserID);	
			for(int i=0;i < remove.size();i++){
				if(remove.get(i).getQuestion().length() >= check){
					String abc = remove.get(i).getQuestion().toString();
					remove.get(i).setQuestion(abc.substring(0, get)+ ".....");
				}
			}
			model.addAttribute("removelist", remove);
			//------------------
			Setting setting = userService.getSetting(UserID);
			
			int numOfRecord = setting.getRecordDictionary();
			int numOfPagin = setting.getPaginDisplayDictionary();
			
			model.addAttribute("numOfRecord", ""+numOfRecord);
			model.addAttribute("numOfPagin", ""+numOfPagin);
			
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(7, UserID));
			model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
			String user = userService.getFullnameByID(UserID);
			model.addAttribute("username", user);
			Dictionary removequestion = DictionaryService.removequestion(Id);
			//-----------------------
			model.addAttribute("diction", removequestion);
			if( removequestion.getBusyStatus()== 1){
				model.addAttribute("busystatus",null);
			}else{
				model.addAttribute("busystatus", removequestion.getBusyStatus().toString());
			}
			model.addAttribute("curentOfPage", page);
			Users newusername = DictionaryService.getusername(removequestion.getAnwserBy());
			model.addAttribute("username", newusername.getFullName().toString());
			return "list-dictionary-down";
		}
	}
}

	@RequestMapping(value = "/botudiendaha", method = RequestMethod.POST)
	public String botudiendahapost( 	
			@RequestParam String actionsubmit , 
			@RequestParam(value = "change-items", required = false, defaultValue= "0") String changeitems, 
			@RequestParam(value = "change-pagin", required = false, defaultValue= "0") String changepagin, 
			@ModelAttribute("dictionary") Dictionary diction,
			Model model,
			HttpSession session) {
		
			//get page
			int UserID =Integer.parseInt(session.getAttribute("login").toString());
			int page =Integer.parseInt(session.getAttribute("Page").toString());
			//Load deleted-question list of page that is selected
			
			
			
			model.addAttribute("dictionary", new Dictionary());
			model.addAttribute("curentOfPage", page);
			
			
			if(actionsubmit.equals("upload")){
				// restore question
				int Id = Integer.parseInt(session.getAttribute("Id").toString());// get ID
				model.addAttribute("dictionary",Id);
				if(session.getAttribute("Id") !="0"){
					// Processing restore question
					int result = DictionaryService.upload(Id);
					int update = DictionaryService.updateby(Id, UserID);
					if(result >0 && update >0){
						List<Dictionary> remove1= DictionaryService.removelist(page-1,UserID);
						for(int i=0;i < remove1.size();i++){
							if(remove1.get(i).getQuestion().length() >= check){
								String abc = remove1.get(i).getQuestion().toString();
								remove1.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						model.addAttribute("removelist", remove1);
						
					}
					//--------------
					Setting setting = userService.getSetting(UserID);
					
					int numOfRecord = setting.getRecordDictionary();
					int numOfPagin = setting.getPaginDisplayDictionary();
					
					model.addAttribute("numOfRecord", ""+numOfRecord);
					model.addAttribute("numOfPagin", ""+numOfPagin);
					
					model.addAttribute("curentOfPage",page);
					model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(7, UserID));
					model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
					String user = userService.getFullnameByID(UserID);
					model.addAttribute("username", user);
					//--------------
					Dictionary newdictionary = DictionaryService.getinformation(Id);
					DictionaryRestful dicrestful = new DictionaryRestful();
					dicrestful.setID(Id);
					dicrestful.setAnwser(newdictionary.getAnwser());
					dicrestful.setQuestion(newdictionary.getQuestion());
					
					try{
						RestTemplate restTemplate = new RestTemplate();
						String result1 = restTemplate.postForObject(url+"/api/question", dicrestful, String.class);
						if(result1.equals("success")){
							model.addAttribute("message","Đăng câu hỏi thành công");
						}else{
							if(result1.equals("fail")){
								model.addAttribute("message","Đăng câu hỏi không  thành công");
							}
						}
					}catch(Exception e){
						model.addAttribute("message","error");
					}
				}
									
			}else{
				if(actionsubmit.equals("delete")){
					// restore question
					int Id = Integer.parseInt(session.getAttribute("Id").toString());// get ID
					int userID = Integer.parseInt(session.getAttribute("login").toString());
					model.addAttribute("dictionary",Id);
					if(session.getAttribute("Id") !="0"){
						// Processing restore question
						int result = DictionaryService.delete(Id);
						DictionaryService.updatedelete(Id, userID);
						List<Dictionary> remove1= DictionaryService.removelist(page-1, UserID);
						for(int i=0;i < remove1.size();i++){
							if(remove1.get(i).getQuestion().length() >= check){
								String abc = remove1.get(i).getQuestion().toString();
								remove1.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						model.addAttribute("removelist", remove1);
						Setting setting = userService.getSetting(UserID);
						
						int numOfRecord = setting.getRecordDictionary();
						int numOfPagin = setting.getPaginDisplayDictionary();
						
						model.addAttribute("numOfRecord", ""+numOfRecord);
						model.addAttribute("numOfPagin", ""+numOfPagin);
						
						model.addAttribute("curentOfPage",page);
						model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(7, UserID));
						model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
						String user = userService.getFullnameByID(UserID);
						model.addAttribute("username", user);
						model.addAttribute("message","Câu hỏi đã được xóa");
					}				
				}else{
					if(actionsubmit.equals("update")){
						// restore question
						int Id = Integer.parseInt(session.getAttribute("Id").toString());// get ID
						model.addAttribute("dictionary",Id);
						if(session.getAttribute("Id") !="0"){
							// Processing restore question
							int result = DictionaryService.update(Id, diction.getAnwser(), diction.getQuestion());
							List<Dictionary> remove2= DictionaryService.removelist(page-1, UserID);
							for(int i=0;i < remove2.size();i++){
								if(remove2.get(i).getQuestion().length() >= check){
									String abc = remove2.get(i).getQuestion().toString();
									remove2.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
							model.addAttribute("removelist", remove2);
							Setting setting = userService.getSetting(UserID);
							
							int numOfRecord = setting.getRecordDictionary();
							int numOfPagin = setting.getPaginDisplayDictionary();
							
							model.addAttribute("numOfRecord", ""+numOfRecord);
							model.addAttribute("numOfPagin", ""+numOfPagin);
							
							model.addAttribute("curentOfPage",page);
							model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(7, UserID));
							model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
							String user = userService.getFullnameByID(UserID);
							model.addAttribute("username", user);
							model.addAttribute("message","Đăng câu hỏi thành công");
						}	
					}else{
						if(actionsubmit.equals("change")){
							if(!changeitems.equals("0")){
								int numOfRecord = Integer.parseInt(changeitems);
								int numOfPagin = Integer.parseInt(changepagin);
								userService.UpdateSettingDictionary(UserID, numOfRecord, numOfPagin);
						
								model.addAttribute("numOfRecord",changeitems);
								model.addAttribute("numOfPagin",changepagin);
								Setting setting = userService.getSetting(UserID);
								model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(7, UserID));
								model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
								
								List<Dictionary> remove2= DictionaryService.removelist(0, UserID);
								for(int i=0;i < remove2.size();i++){
									if(remove2.get(i).getQuestion().length() >= check){
										String abc = remove2.get(i).getQuestion().toString();
										remove2.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
								model.addAttribute("removelist", remove2);
								String user = userService.getFullnameByID(UserID);
								model.addAttribute("username", user);
								
								model.addAttribute("message","Thay đổi cấu hình thành công.");
		
							}
						}else{
						// Tim kiem
							List<Dictionary> remove2= DictionaryService.searchIdex(actionsubmit,"3", UserID);
							for(int i=0;i < remove2.size();i++){
								if(remove2.get(i).getQuestion().length() >= check){
									String abc = remove2.get(i).getQuestion().toString();
									remove2.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
							model.addAttribute("removelist", remove2);
						}
					}
					
				}
			}
			int page1 =Integer.parseInt(session.getAttribute("Page").toString());
			//Load deleted-question list of page that is selected
			
			
			return "list-dictionary-down";
	}	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/botudienhientai", method = RequestMethod.GET)
	public String botudienhientai(
		@RequestParam(value = "topic", required = false, defaultValue= "0")int Id, 
		@RequestParam(value = "page", required = false, defaultValue= "1")int page,
		Model model, HttpSession session) {
		
	if(session.getValue("login") == null){
		model.addAttribute("error", "notlogin");
		return "redirect:/";
	}else{
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		if(Id==0){
			session.setAttribute("Id", "0");
			session.setAttribute("Page",page );
		
			List<Dictionary> Recent= DictionaryService.recentlist(page-1, UserID);
			for(int i=0;i < Recent.size();i++){
				if(Recent.get(i).getQuestion().length() >= check){
					String abc = Recent.get(i).getQuestion().toString();
					Recent.get(i).setQuestion(abc.substring(0, get)+ ".....");
				}
			}
			model.addAttribute("Recentlist", Recent);
			Setting setting = userService.getSetting(UserID);
			
			int numOfRecord = setting.getRecordDictionary();
			int numOfPagin = setting.getPaginDisplayDictionary();
			
			model.addAttribute("numOfRecord", ""+numOfRecord);
			model.addAttribute("numOfPagin", ""+numOfPagin);
			
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(6, UserID));
			model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
			model.addAttribute("diction", new Dictionary());
			String user = userService.getFullnameByID(UserID);
			model.addAttribute("username", user);
			model.addAttribute("curentOfPage", page);
			return "list-dictionary-recent";
			
		}else{
			session.setAttribute("Id", Id);
			session.setAttribute("Page",page );	

			List<Dictionary> recentlist= DictionaryService.recentlist(page-1,  UserID);	
			for(int i=0;i < recentlist.size();i++){
				if(recentlist.get(i).getQuestion().length() >= check){
					String abc = recentlist.get(i).getQuestion().toString();
					recentlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
				}
			}
			model.addAttribute("Recentlist", recentlist);
			
			Dictionary recent = DictionaryService.recentquestion(Id);
			
			model.addAttribute("diction", recent);
			Setting setting = userService.getSetting(UserID);
			
			int numOfRecord = setting.getRecordDictionary();
			int numOfPagin = setting.getPaginDisplayDictionary();
			
			model.addAttribute("numOfRecord", ""+numOfRecord);
			model.addAttribute("numOfPagin", ""+numOfPagin);
			
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(6, UserID));
			model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
			
			model.addAttribute("diction", recent);
			
			String user = userService.getFullnameByID(UserID);
			model.addAttribute("username", user);
			
			model.addAttribute("curentOfPage", page);
			Users newusername = DictionaryService.getusername(recent.getAnwserBy());
			model.addAttribute("username", newusername.getFullName().toString());
			return "list-dictionary-recent";
		}
	}
}

	@RequestMapping(value = "/botudienhientai", method = RequestMethod.POST)
	public String botudienhientaipost( 	
			@RequestParam String actionsubmit ,
			@RequestParam(value = "change-items", required = false, defaultValue= "0") String changeitems, 
			@RequestParam(value = "change-pagin", required = false, defaultValue= "0") String changepagin, 
			@ModelAttribute("dictionary") Dictionary diction,
			Model model,
			HttpSession session) {
			int UserID = Integer.parseInt(session.getAttribute("login").toString());
			//get page
			int page =Integer.parseInt(session.getAttribute("Page").toString());
			//Load deleted-question list of page that is selected

			List<Dictionary> recentlist= DictionaryService.recentlist(page-1, UserID);
			for(int i=0;i < recentlist.size();i++){
				if(recentlist.get(i).getQuestion().length() >= check){
					String abc = recentlist.get(i).getQuestion().toString();
					recentlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
				}
			}
			model.addAttribute("Recentlist", recentlist);			
			model.addAttribute("dictionary", new Dictionary());
			model.addAttribute("curentOfPage", page);
			if(actionsubmit.equals("remove")){
				// restore question
				int Id = Integer.parseInt(session.getAttribute("Id").toString());// get ID
				int userID = Integer.parseInt(session.getAttribute("login").toString());// get ID
				model.addAttribute("dictionary",Id);
				if(session.getAttribute("Id") !="0"){
					// Processing restore question
					int result = DictionaryService.remove(Id);
					int update = DictionaryService.updateby(Id, UserID);
					List<Dictionary> rece= DictionaryService.recentlist(page-1, UserID);
					for(int i=0;i < rece.size();i++){
						if(rece.get(i).getQuestion().length() >= check){
							String abc = rece.get(i).getQuestion().toString();
							rece.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
					model.addAttribute("Recentlist", rece);
					Setting setting = userService.getSetting(UserID);
					
					int numOfRecord = setting.getRecordDictionary();
					int numOfPagin = setting.getPaginDisplayDictionary();
					
					model.addAttribute("numOfRecord", ""+numOfRecord);
					model.addAttribute("numOfPagin", ""+numOfPagin);
					
					model.addAttribute("curentOfPage",page);
					model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(6, UserID));
					model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
					model.addAttribute("diction", new Dictionary());
					String user = userService.getFullnameByID(UserID);
					model.addAttribute("username", user);
					Dictionary newdictionary = DictionaryService.getinformation(Id);
					
					DictionaryRestful dicrestful = new DictionaryRestful();
					dicrestful.setID(Id);
					dicrestful.setAnwser(newdictionary.getAnwser());
					dicrestful.setQuestion(newdictionary.getQuestion());
					try{
						RestTemplate restTemplate = new RestTemplate();
						String result1 = restTemplate.postForObject(url+"/api/romovequestion", dicrestful, String.class);
						
						if(result1.equals("remove is success")){
							model.addAttribute("message", "Hạ câu hỏi thành công");
						}else{
							if(result1.equals("remove is fail")){
								model.addAttribute("message", "Hạ câu hỏi không thành công");
							}
						}
					}catch(Exception e){
						model.addAttribute("message","error");
					}
				}
				
									
			}else{
				if(actionsubmit.equals("change")){
					if(!changeitems.equals("0")){
						int numOfRecord = Integer.parseInt(changeitems);
						int numOfPagin = Integer.parseInt(changepagin);
						userService.UpdateSettingDictionary(UserID, numOfRecord, numOfPagin);
				
						model.addAttribute("numOfRecord",changeitems);
						model.addAttribute("numOfPagin",changepagin);
						Setting setting = userService.getSetting(UserID);
						model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(6, UserID));
						model.addAttribute("noOfDisplay", setting.getPaginDisplayDictionary());
						
						List<Dictionary> rece= DictionaryService.recentlist(0, UserID);
						for(int i=0;i < rece.size();i++){
							if(rece.get(i).getQuestion().length() >= check){
								String abc = rece.get(i).getQuestion().toString();
								rece.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						model.addAttribute("Recentlist", rece);
						String user = userService.getFullnameByID(UserID);
						model.addAttribute("username", user);
						
						model.addAttribute("message","Thay đổi cấu hình thành công.");

					}
				}else{
				// Tim kiem
					List<Dictionary> rece1= DictionaryService.searchIdex(actionsubmit, "2", UserID);
					for(int i=0;i < rece1.size();i++){
						if(rece1.get(i).getQuestion().length() >= check){
							String abc = rece1.get(i).getQuestion().toString();
							rece1.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
					model.addAttribute("Recentlist", rece1);
				}
			}
			return "list-dictionary-recent";
	}
	

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/taocauhoi", method = RequestMethod.GET)
	public String taocauhoi(Locale locale, Model model, HttpSession session) {
		if(session.getValue("login") == null){
			model.addAttribute("error", "notlogin");
			return "redirect:/";
		}else{
			model.addAttribute("createQaA", new Dictionary());
			model.addAttribute("message", "");
			return "create-dictionary";
		}
	}
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/taocauhoi", method = RequestMethod.POST)
	public String taocauhoipost( 	
			@RequestParam String actionsubmit , 
			@ModelAttribute("createQaA") Dictionary dictionary,
			Model model,
			HttpSession session, BindingResult result) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		int userID = Integer.parseInt(session.getAttribute("login").toString());// get ID	
		DictionaryValidator validator = new DictionaryValidator();
		validator.validate(dictionary, result);	     
        if (result.hasErrors()){
        	model.addAttribute("createQaA",dictionary);
        	 return "create-dictionary";
        }else {
        	if(actionsubmit.equals("save")){
							
				dictionary.setCreateBy(userID);
				dictionary.setAnwserBy(userID);
				Date now = new Date();
				dictionary.setCreateDate(now);
				dictionary.setStatus(1);
				dictionary.setDeleteStatus(0);
				dictionary.setBusyStatus(0);
			
				DictionaryService.AddDictionary(dictionary);
				
				model.addAttribute("message", "Tạo câu hỏi thành công!");
				
			}
        	return "create-dictionary";
        }		
	}
}
