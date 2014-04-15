package deadlineteam.admission.quantritudien.controller;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;
import deadlineteam.admission.quantritudien.service.Dictionary.Dictionary_SERVICE;
import deadlineteam.admission.quantritudien.service.QuestionManagement.Questionmanagement_SERVICE;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;
import deadlineteam.admission.quantritudien.util.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class QuestionaManagementController {
	@Autowired
	private Users_SERVICE userService;

	@Autowired
	private Questionmanagement_SERVICE QuestionmanagementService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	@Autowired
	private Dictionary_SERVICE DictionaryService;

	private static final Logger logger = LoggerFactory.getLogger(QuestionaManagementController.class);
	
	
	//Implement when home page load
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String HomeGetMethod(	 
			@RequestParam(value = "topic", required = false, defaultValue= "0")int Id, 
			@RequestParam(value = "page", required = false, defaultValue= "1")int page,
			Model model, HttpSession session) {
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		if(session.getValue("login") == null){
			model.addAttribute("error", "Bạn chưa đăng nhập");
			return "redirect:/";
		}else{
			if(Id==0){
				//User ID
				int UserId = Integer.parseInt(session.getAttribute("UserId").toString());
				
				//Set Session
				session.setAttribute("Record", "4");
				session.setAttribute("Id", "0");
				session.setAttribute("Page",page );
				
				//Get List Question
				List<Questionmanagement> ListQuestion= QuestionmanagementService.getQuestionmanagementbyPage( page-1,  UserID);;
				model.addAttribute("listquestionmanagement", ListQuestion);
				
				Setting setting = userService.getSetting(UserId);
				
				int numOfRecord = setting.getRecordNotRep();
				int numOfPagin = setting.getPaginDisplayNotRep();
				
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				for(int i=0;i < ListQuestion.size();i++){
					if(ListQuestion.get(i).getQuestion().length() >=50){
						String abc = ListQuestion.get(i).getQuestion().toString();
						ListQuestion.get(i).setQuestion(abc.substring(0, 45)+ "...");
					}
				}
				model.addAttribute("listquestionmanagement", ListQuestion);
				
				model.addAttribute("curentOfPage",page);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserId));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());

				model.addAttribute("curentOfPage",page);
				
			//	model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1));
				
				model.addAttribute("fullname",userService.getFullnameByID(UserId));
				
				model.addAttribute("questionmanagements", new Questionmanagement());
				model.addAttribute("message", "Không mục nào được chọn để xem.");
				
				//check is admin
				if(userService.checkIsAdmin(UserId)==true){
					model.addAttribute("isAdmin","admin");
				}		
				return "home";
			}else{
					//set Session
					session.setAttribute("Id", Id);
					session.setAttribute("Page",page );
					
					Questionmanagement questionmanagement = QuestionmanagementService.getQuestionmanagementbyID(Id);
					List<Questionmanagement> ListQuestion= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
					model.addAttribute("questionmanagements", questionmanagement);
					Setting setting = userService.getSetting(UserID);
					
					int numOfRecord = setting.getRecordNotRep();
					int numOfPagin = setting.getPaginDisplayNotRep();
					
					model.addAttribute("numOfRecord", ""+numOfRecord);
					model.addAttribute("numOfPagin", ""+numOfPagin);
					model.addAttribute("curentOfPage",page);
					model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
					model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
					model.addAttribute("listquestionmanagement", ListQuestion);
					model.addAttribute("fullname", userService.getFullnameByID(UserID));
					
					//check is admin
					if(userService.checkIsAdmin(UserID)==true){
						model.addAttribute("isAdmin","admin");
					}	
						
					session.setAttribute("email",questionmanagement.getQuestionEmail());
					session.setAttribute("title",questionmanagement.getTitle());
					return "home";
						
			}
		}
	}
	
	//Implement when submit home page
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String homepost( 	
			@RequestParam String actionsubmit ,
			@RequestParam(value = "checkboxdata", required = false, defaultValue= "0") String checkboxdata, 
			@RequestParam(value = "change-items", required = false, defaultValue= "0") String changeitems, 
			@RequestParam(value = "change-pagin", required = false, defaultValue= "0") String changepagin, 
			@ModelAttribute("questionmanagements") Questionmanagement questionmanagement,
			Model model,
			HttpSession session) {		
			int UserID = Integer.parseInt(session.getAttribute("login").toString());
			int page =Integer.parseInt(session.getAttribute("Page").toString());
			
			List<Questionmanagement> ListQuestion= QuestionmanagementService.getQuestionmanagementbyPage_setting(page-1, 5);
			
			model.addAttribute("fullname",userService.getFullnameByID(UserID));
			model.addAttribute("listquestionmanagement", ListQuestion);
			model.addAttribute("questionmanagements", new Questionmanagement());
			model.addAttribute("fullname", userService.getFullnameByID(UserID));
			//check is admin
			if(userService.checkIsAdmin(UserID)==true){
				model.addAttribute("isAdmin","admin");
			}	
			if(actionsubmit.equals("send")){
				//xu ly khi nhan gui
				int Id = Integer.parseInt(session.getAttribute("Id").toString());
				int login = Integer.parseInt(session.getAttribute("login").toString());
				if(session.getAttribute("Id") !="0"){
					//xu ly luu cau tra loi va gui mail
					int result = QuestionmanagementService.updateAnswerbyId(Id,questionmanagement.getAnswer());
					if(result>0){			
						String email = session.getAttribute("email").toString();
						String title = session.getAttribute("title").toString();
						String body = questionmanagement.getAnswer();
						
						MimeMessage mimeMessage = mailSender.createMimeMessage();
						
						try {
							
							 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
							 message.setTo(email);
							 message.setSubject(title);
							 message.setText(body, true);
							// sends the e-mail
							mailSender.send(mimeMessage);
							
							List<Questionmanagement> ListQuestion1= QuestionmanagementService.getQuestionmanagementbyPage_setting(page-1, 5);
							model.addAttribute("listquestionmanagement", ListQuestion1);
							QuestionmanagementService.UpdateAnwserBy(Id, login);
							model.addAttribute("message", "Bạn đã gửi mail thành công.");
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							model.addAttribute("message", "Bạn đã gủi mail thất bại.");
						}
						
						
						
						/*SendMail  send = new SendMail(email, title, body);
						//send.send();
						
						
						List<Questionmanagement> ListQuestion1= QuestionmanagementService.getQuestionmanagementbyPage_setting(page-1, 5);
						model.addAttribute("listquestionmanagement", ListQuestion1);
						QuestionmanagementService.UpdateAnwserBy(Id, login);
						model.addAttribute("message", "Bạn đã gửi mail thành công.");*/
					}
				}
			}else{
				if(actionsubmit.equals("save")){
					int Id = Integer.parseInt(session.getAttribute("Id").toString());
					int login = Integer.parseInt(session.getAttribute("login").toString());
					if(session.getAttribute("Id") !="0"){
						//xu ly luu cau tra loi
						if(checkboxdata!="0"){
						int result = QuestionmanagementService.SaveTemporaryAnswerbyId(Id,questionmanagement.getAnswer());
						if(result>0){			
							List<Questionmanagement> ListQuestion1= QuestionmanagementService.getQuestionmanagementbyPage_setting(page-1, 5);
							model.addAttribute("listquestionmanagement", ListQuestion1);
							QuestionmanagementService.UpdateAnwserBy(Id, login);
							model.addAttribute("message", "Bạn đã lưu thành công.");
						}
					}
					}	
				}else{
					if(actionsubmit.equals("delete")){
					//xu ly khi delete
						//xu ly khi nhan gui
						int Id = Integer.parseInt(session.getAttribute("Id").toString());
						int login = Integer.parseInt(session.getAttribute("login").toString());
						model.addAttribute("deletequestion",Id);
						if(session.getAttribute("Id") !="0"){
							//xu ly luu cau tra loi va gui mail
							int result = QuestionmanagementService.delete(Id);
							if(result>0){
								QuestionmanagementService.UpdateDelete(Id, login);
								model.addAttribute("message1", "Bạn đã xóa thành công.");
								
								
							}
						}
					}else{
						//xủ lý xóa tất cả
						int login = Integer.parseInt(session.getAttribute("login").toString());
						if(actionsubmit.equals("deleteall")){
							QuestionmanagementService.deleteall(checkboxdata,login);
							
							model.addAttribute("message", "Đã xóa.");
							List<Questionmanagement> ListQuestion1= QuestionmanagementService.getQuestionmanagementbyPage_setting(page-1, 5);
							model.addAttribute("listquestionmanagement", ListQuestion1);
						}else{
	
							if(actionsubmit.equals("change")){
								if(!changeitems.equals("0")){
									int numOfRecord = Integer.parseInt(changeitems);
									int numOfPagin = Integer.parseInt(changepagin);
									userService.UpdateSetting(UserID, numOfRecord, numOfPagin);
							
									model.addAttribute("numOfRecord",changeitems);
									model.addAttribute("numOfPagin",changepagin);
									
									Setting setting = userService.getSetting(UserID);
									model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
									model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
									
									List<Questionmanagement> ListQuestion1= QuestionmanagementService.getQuestionmanagementbyPage(page-1, UserID);
									model.addAttribute("listquestionmanagement", ListQuestion1);
									model.addAttribute("message","Thay đổi cấu hình thành công.");
			
								}
							}else{
							//xu ly tim kiem
							model.addAttribute("message",actionsubmit);
							List<Questionmanagement> list = QuestionmanagementService.searchIdex(actionsubmit,"1");
							model.addAttribute("listquestionmanagement", list);
							model.addAttribute("actionsubmit", actionsubmit);
							}

							
	
						}
					}
				}
			}
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
			return "home";
	}
	
	//Chưa xủ lý
	
	

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/dsluutam", method = RequestMethod.GET)
	public String dsluutam(@RequestParam(value = "topic", required = false, defaultValue= "0")int Id, 
			@RequestParam(value = "page", required = false, defaultValue= "1")int page,
			Model model, HttpSession session) {
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		if(session.getValue("login") == null){
			return "redirect:/";
		}else{
			if(Id==0){
				session.setAttribute("Id",0);
				session.setAttribute("Page",page );
				List<Questionmanagement> savelist= QuestionmanagementService.savelist(page-1, UserID);
				Setting setting = userService.getSetting(UserID);
				
				int numOfRecord = setting.getRecordTemp();
				int numOfPagin = setting.getPaginDisplayTemp();
				
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				
				
				model.addAttribute("fullname",userService.getFullnameByID(UserID));
				model.addAttribute("curentOfPage",page);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(2, UserID));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayTemp());
				model.addAttribute("savequestionlist", savelist);
				model.addAttribute("questionmanagements", new Questionmanagement());
				model.addAttribute("message", "Không mục nào được chọn để xem.");
				//check is admin
				if(userService.checkIsAdmin(UserID)==true){
					model.addAttribute("isAdmin","admin");
				}	
				return "list-saved";
				
			}else{
				session.setAttribute("Id", Id);
				session.setAttribute("Page",page );
				Questionmanagement save = QuestionmanagementService.savequestion(Id);
				List<Questionmanagement> savelist= QuestionmanagementService.savelist(page-1, UserID);
				Setting setting = userService.getSetting(UserID);
				
				int numOfRecord = setting.getRecordTemp();
				int numOfPagin = setting.getPaginDisplayTemp();
				
				model.addAttribute("fullname",userService.getFullnameByID(UserID));
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				model.addAttribute("curentOfPage",page);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(2, UserID));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayTemp());
				model.addAttribute("questionmanagements", save);
				model.addAttribute("savequestionlist", savelist);
				session.setAttribute("email",save.getQuestionEmail());
				session.setAttribute("title",save.getTitle());
				//check is admin
				if(userService.checkIsAdmin(UserID)==true){
					model.addAttribute("isAdmin","admin");
				}	
				return "list-saved";
			}
		}
		
	}
	
	
	@RequestMapping(value = "/dsluutam", method = RequestMethod.POST)
	public String dsluutampost( 	
			@RequestParam String actionsubmit , 
			@RequestParam(value = "change-items", required = false, defaultValue= "0") String changeitems, 
			@RequestParam(value = "change-pagin", required = false, defaultValue= "0") String changepagin, 
			@ModelAttribute("questionmanagements") Questionmanagement questionmanagement,
			@RequestParam(value = "checkboxdata", required = false, defaultValue= "0") String checkboxdata, 
			Model model,
			HttpSession session) {
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
			int page =Integer.parseInt(session.getAttribute("Page").toString());
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(2, UserID));
			
			List<Questionmanagement> savequestionlist= QuestionmanagementService.savelist(page-1, UserID);
			model.addAttribute("savequestionlist", savequestionlist);
			model.addAttribute("questionmanagements", new Questionmanagement());
			model.addAttribute("fullname",userService.getFullnameByID(UserID));
			//check is admin
			if(userService.checkIsAdmin(UserID)==true){
				model.addAttribute("isAdmin","admin");
			}
			if(actionsubmit.equals("send")){
				//xu ly khi nhan gui
				int Id = Integer.parseInt(session.getAttribute("Id").toString());
				int login = Integer.parseInt(session.getAttribute("login").toString());
				model.addAttribute("anwser",Id);
				if(session.getAttribute("Id") !="0"){
					//xu ly luu cau tra loi va gui mail
					int result = QuestionmanagementService.SendAnwser(Id,questionmanagement.getAnswer());
					if(result>0){
						List<Questionmanagement> savelist= QuestionmanagementService.savelist(page-1, UserID);
						model.addAttribute("savequestionlist", savelist);
						model.addAttribute("anwser",questionmanagement.getAnswer());
						String email = session.getAttribute("email").toString();
						String title = session.getAttribute("title").toString();
						String body = questionmanagement.getAnswer();
						SendMail  send = new SendMail(email, title, body);
						send.send();				
						model.addAttribute("message","Gủi mail thành công");
					}
				}
			}else{
				if(actionsubmit.equals("save")){
					int Id = Integer.parseInt(session.getAttribute("Id").toString());
					int login = Integer.parseInt(session.getAttribute("login").toString());
					model.addAttribute("anwser",Id);
					if(session.getAttribute("Id") !="0"){
						//xu ly luu cau tra loi va gui mail
						int result = QuestionmanagementService.SaveAnwser(Id,questionmanagement.getAnswer());
						if(result>0){
							model.addAttribute("message","Lưu thành công");
							QuestionmanagementService.UpdateAnwserBy(Id, login);
							List<Questionmanagement> savelist= QuestionmanagementService.savelist(page-1, UserID);
							model.addAttribute("savequestionlist", savelist);
						}
					}	
				}else{
					if(actionsubmit.equals("delete")){
					//xu ly khi delete
						//xu ly khi nhan gui
						int Id = Integer.parseInt(session.getAttribute("Id").toString());
						int login = Integer.parseInt(session.getAttribute("login").toString());
						model.addAttribute("deletequestion",Id);
						if(session.getAttribute("Id") !="0"){
							//xu ly luu cau tra loi va gui mail
							int result = QuestionmanagementService.deletesavequestion(Id);
							
							if(result>0){
								QuestionmanagementService.UpdateDelete(Id, login);
								model.addAttribute("message","Xóa thành công");
								List<Questionmanagement> savelist= QuestionmanagementService.savelist(page-1, UserID);
								model.addAttribute("savequestionlist", savelist);
							}
						}
					}else{
						//xủ lý xóa tất cả
						if(actionsubmit.equals("deleteall")){
							int login = Integer.parseInt(session.getAttribute("login").toString());
							QuestionmanagementService.deleteall(checkboxdata,login);
							model.addAttribute("message", "Đã xóa.");
							List<Questionmanagement> ListQuestion1= QuestionmanagementService.getQuestionmanagementbyPage(page-1, UserID);
							model.addAttribute("savequestionlist", ListQuestion1);
						}else{
							if(actionsubmit.equals("change")){
								if(!changeitems.equals("0")){
									int numOfRecord = Integer.parseInt(changeitems);
									int numOfPagin = Integer.parseInt(changepagin);
									userService.UpdateSettingSaved(UserID, numOfRecord, numOfPagin);
							
									model.addAttribute("numOfRecord",changeitems);
									model.addAttribute("numOfPagin",changepagin);
									
									Setting setting = userService.getSetting(UserID);
									model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(2, UserID));
									model.addAttribute("noOfDisplay", setting.getPaginDisplayTemp());
									
									List<Questionmanagement> savelist= QuestionmanagementService.savelist(page-1, UserID);
									model.addAttribute("savequestionlist", savelist);
									model.addAttribute("message","Thay đổi cấu hình thành công.");
			
								}
							}else{
							//xu ly tim kiem
							model.addAttribute("message",actionsubmit);
							List<Questionmanagement> list = QuestionmanagementService.searchIdex(actionsubmit,"1");
							model.addAttribute("listquestionmanagement", list);
							model.addAttribute("actionsubmit", actionsubmit);
							}
						}
					}
				}
			}
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(2, UserID));
			
			return "list-saved";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/dsdatraloi", method = RequestMethod.GET)
	public String dsdatraloi(
			@RequestParam(value = "topic", required = false, defaultValue= "0")int Id, 
			@RequestParam(value = "page", required = false, defaultValue= "1")int page,
			Model model, HttpSession session) {
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		if(session.getValue("login") == null){
			return "redirect:/";
		}else{
			if(Id==0){
				session.setAttribute("Id", "0");
				session.setAttribute("Page",page );
				List<Questionmanagement> Deletequestionlist= QuestionmanagementService.repliedList(page-1, UserID);
				model.addAttribute("replylust", Deletequestionlist);
				Setting setting = userService.getSetting(UserID);
				int numOfRecord = setting.getRecordRepied();
				int numOfPagin = setting.getPaginDisplayReplied();
				
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				
				
				model.addAttribute("fullname",userService.getFullnameByID(UserID));
				model.addAttribute("curentOfPage",page);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(3, UserID));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayReplied());
				model.addAttribute("questionmanagements", new Questionmanagement());
				model.addAttribute("message", "Không mục nào được chọn để xem.");
				//check is admin
				if(userService.checkIsAdmin(UserID)==true){
					model.addAttribute("isAdmin","admin");
				}	
				return "list-replied";
				
			}else{
				int login = Integer.parseInt(session.getAttribute("login").toString());
				session.setAttribute("Id", Id);
				session.setAttribute("Page",page );
				Questionmanagement delete = QuestionmanagementService.repliedquestion(Id);
				List<Questionmanagement> Deletequestionlist= QuestionmanagementService.repliedList(page-1, UserID);
				Users username = QuestionmanagementService.getusername(login);
				model.addAttribute("username",username.getFullName());
				
				Setting setting = userService.getSetting(UserID);
				int numOfRecord = setting.getRecordRepied();
				int numOfPagin = setting.getPaginDisplayReplied();
				
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				
				//check is admin
				if(userService.checkIsAdmin(UserID)==true){
					model.addAttribute("isAdmin","admin");
				}	
				
				model.addAttribute("curentOfPage",page);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(3, UserID));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayReplied());
				model.addAttribute("fullname",userService.getFullnameByID(UserID));
				model.addAttribute("questionmanagements", delete);
				model.addAttribute("replylust", Deletequestionlist);
				return "list-replied";
			}
		}
	}
	
	@RequestMapping(value = "/dsdatraloi", method = RequestMethod.POST)
	public String dsdatraloipost( 	
			@RequestParam String actionsubmit , 
			@RequestParam(value = "change-items", required = false, defaultValue= "0") String changeitems, 
			@RequestParam(value = "change-pagin", required = false, defaultValue= "0") String changepagin, 
			@ModelAttribute("questionmanagements") Questionmanagement questionmanagement,
			@RequestParam(value = "checkboxdata", required = false, defaultValue= "0") String checkboxdata, 
			Model model,
			HttpSession session) {
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
			//get page
			int page =Integer.parseInt(session.getAttribute("Page").toString());
			//Load deleted-question list of page that is selected
			//check is admin
			if(userService.checkIsAdmin(UserID)==true){
				model.addAttribute("isAdmin","admin");
			}	
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(3, UserID));
			model.addAttribute("fullname",userService.getFullnameByID(UserID));
			if(actionsubmit.equals("delete")){
				int Id = Integer.parseInt(session.getAttribute("Id").toString());// get ID
				int login = Integer.parseInt(session.getAttribute("login").toString());
				
				model.addAttribute("deletequestion",Id);
				if(session.getAttribute("Id") !="0"){
					// Processing restore question
					int result = QuestionmanagementService.deleterepliedquestion(Id);
					if(result>0){
						QuestionmanagementService.UpdateDelete(Id, login);
						model.addAttribute("message","Xóa thành công");
						List<Questionmanagement> Deletequestionlist= QuestionmanagementService.repliedList(page-1, UserID);
						model.addAttribute("replylust", Deletequestionlist);
					}
				}
									
				}else{
					if(actionsubmit.equals("dictionary")){
						int login =Integer.parseInt(session.getAttribute("login").toString());
						int Id =Integer.parseInt(session.getAttribute("Id").toString());
						model.addAttribute("mess",Id);
						Dictionary newdic = new Dictionary();
						Questionmanagement question = QuestionmanagementService.repliedquestion(Id);
						newdic.setTitle(question.getTitle());
						newdic.setAnwser(question.getAnswer());
						newdic.setQuestion(question.getQuestion());
						newdic.setCreateBy(login);
						Date now = new Date();
						newdic.setCreateDate(now);
						newdic.setAnwserBy(question.getAnswerBy());
						newdic.setStatus(1);
						newdic.setDeleteStatus(0);
						newdic.setBusyStatus(0);
						DictionaryService.AddDictionary(newdic);
						QuestionmanagementService.TransferToDictionary(Id, login);
						List<Questionmanagement> Deletequestionlist= QuestionmanagementService.repliedList(page-1, UserID);
						model.addAttribute("replylust", Deletequestionlist);
						model.addAttribute("message", "Đã đưa vào bộ từ điển thành công.");
					}else{
						if(actionsubmit.equals("deleteall")){
							int login = Integer.parseInt(session.getAttribute("login").toString());
							QuestionmanagementService.deleteall(checkboxdata,login);
							model.addAttribute("message", "Đã xóa.");
							List<Questionmanagement> Deletequestionlist= QuestionmanagementService.repliedList(page-1, UserID);
							model.addAttribute("replylust", Deletequestionlist);
						}else{
							if(actionsubmit.equals("change")){
								if(!changeitems.equals("0")){
									int numOfRecord = Integer.parseInt(changeitems);
									int numOfPagin = Integer.parseInt(changepagin);
									userService.UpdateSettingReplied(UserID, numOfRecord, numOfPagin);
							
									model.addAttribute("numOfRecord",changeitems);
									model.addAttribute("numOfPagin",changepagin);
									
									Setting setting = userService.getSetting(UserID);
									model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(3, UserID));
									model.addAttribute("noOfDisplay", setting.getPaginDisplayReplied());
									
									List<Questionmanagement> Deletequestionlist= QuestionmanagementService.repliedList(page-1, UserID);
									model.addAttribute("replylust", Deletequestionlist);
									model.addAttribute("message","Thay đổi cấu hình thành công.");
			
								}
							}else{
							//xu ly tim kiem
							model.addAttribute("message",actionsubmit);
							List<Questionmanagement> list = QuestionmanagementService.searchIdex(actionsubmit,"1");
							model.addAttribute("listquestionmanagement", list);
							model.addAttribute("actionsubmit", actionsubmit);
							}
							
						}
					}
				}
			List<Questionmanagement> ListQuestion= QuestionmanagementService.repliedList(page-1, UserID);
			model.addAttribute("listquestionmanagement", ListQuestion);
			model.addAttribute("questionmanagements", new Questionmanagement());
			return "list-replied";
	}
	
	

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/dsdaxoa", method = RequestMethod.GET)
	public String dsdaxoa(
			@RequestParam(value = "topic", required = false, defaultValue= "0")int Id, 
			@RequestParam(value = "page", required = false, defaultValue= "1")int page,
			Model model, HttpSession session) {
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		if(session.getValue("login") == null){
			return "redirect:/";
		}else{
			if(Id==0){
				session.setAttribute("Id", "0");
				session.setAttribute("Page",page );
				List<Questionmanagement> Deletequestionlist= QuestionmanagementService.deleteList(page-1, UserID);
				model.addAttribute("deletequestionlist", Deletequestionlist);
				Setting setting = userService.getSetting(UserID);
				
				int numOfRecord = setting.getRecordDelete();
				int numOfPagin = setting.getPaginDisplayDelete();
				
				//check is admin
				if(userService.checkIsAdmin(UserID)==true){
					model.addAttribute("isAdmin","admin");
				}	
				model.addAttribute("fullname",userService.getFullnameByID(UserID));
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				model.addAttribute("curentOfPage",page);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(4, UserID));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayDelete());
				model.addAttribute("deletequestion", new Questionmanagement());
				model.addAttribute("message", "Không mục nào được chọn để xem.");
				return "list-deleted";
				
			}else{
				session.setAttribute("Id", Id);
				session.setAttribute("Page",page );
				Questionmanagement delete = QuestionmanagementService.deletequestion(Id);
				List<Questionmanagement> Deletequestionlist= QuestionmanagementService.deleteList(page-1, UserID);
				Setting setting = userService.getSetting(UserID);
				
				int numOfRecord = setting.getRecordDelete();
				int numOfPagin = setting.getPaginDisplayDelete();
				
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				model.addAttribute("curentOfPage",page);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(4, UserID));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayDelete());
				model.addAttribute("fullname",userService.getFullnameByID(UserID));
				//check is admin
				if(userService.checkIsAdmin(UserID)==true){
					model.addAttribute("isAdmin","admin");
				}	
				model.addAttribute("deletequestion", delete);
				model.addAttribute("deletequestionlist", Deletequestionlist);
				return "list-deleted";
			}
		}
	}
	@RequestMapping(value = "/dsdaxoa", method = RequestMethod.POST)
	public String dsdaxoapost( 	
			@RequestParam String actionsubmit , 
			@RequestParam(value = "change-items", required = false, defaultValue= "0") String changeitems, 
			@RequestParam(value = "change-pagin", required = false, defaultValue= "0") String changepagin, 
			@ModelAttribute("deletequestion") Questionmanagement questionmanagement,
			@RequestParam(value = "checkboxdata", required = false, defaultValue= "0") String checkboxdata, 
			Model model,
			HttpSession session) {
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
			//get page
			int page =Integer.parseInt(session.getAttribute("Page").toString());
			//Load deleted-question list of page that is selected
			model.addAttribute("curentOfPage",page);
			model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagementDelete(1));
			List<Questionmanagement> ListQuestion= QuestionmanagementService.deleteList(page-1, UserID);
			model.addAttribute("deletequestionlist", ListQuestion);
			model.addAttribute("deletequestion", new Questionmanagement());
			model.addAttribute("fullname",userService.getFullnameByID(UserID));
			//check is admin
			if(userService.checkIsAdmin(UserID)==true){
				model.addAttribute("isAdmin","admin");
			}	
			if(actionsubmit.equals("delete")){
				// restore question
				int Id = Integer.parseInt(session.getAttribute("Id").toString());// get ID
			//	model.addAttribute("deletequestion",Id);
				if(session.getAttribute("Id") !="0"){
					// Processing restore question
					int result = QuestionmanagementService.restore(Id);
					if(result>0){
						model.addAttribute("message","Khôi phục thành công");
						List<Questionmanagement> Deletequestionlist= QuestionmanagementService.deleteList(page-1, UserID);
						model.addAttribute("deletequestionlist", Deletequestionlist);
					}
				}					
				
			}else{
				if(actionsubmit.equals("change")){
					if(!changeitems.equals("0")){
						int numOfRecord = Integer.parseInt(changeitems);
						int numOfPagin = Integer.parseInt(changepagin);
						userService.UpdateSettingDelete(UserID, numOfRecord, numOfPagin);
				
						model.addAttribute("numOfRecord",changeitems);
						model.addAttribute("numOfPagin",changepagin);
						
						Setting setting = userService.getSetting(UserID);
						model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(4, UserID));
						model.addAttribute("noOfDisplay", setting.getPaginDisplayDelete());
						
						List<Questionmanagement> Deletequestionlist= QuestionmanagementService.deleteList(page-1, UserID);
						model.addAttribute("deletequestionlist", Deletequestionlist);
						model.addAttribute("message","Thay đổi cấu hình thành công.");

					}
				}else{
				//xu ly tim kiem
				model.addAttribute("message",actionsubmit);
				List<Questionmanagement> list = QuestionmanagementService.searchIdex(actionsubmit,"1");
				model.addAttribute("listquestionmanagement", list);
				model.addAttribute("actionsubmit", actionsubmit);
				}
			}
			return "list-deleted";
	}
}
