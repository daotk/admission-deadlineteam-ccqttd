package deadlineteam.admission.quantritudien.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.jws.soap.SOAPBinding.Use;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	private int check = 47;
	private int get = 44;

	private static final Logger logger = LoggerFactory.getLogger(QuestionaManagementController.class);
	
	private MessageSource msgSrc;
	 @Autowired
	  public void AccountsController(MessageSource msgSrc) {
	     this.msgSrc = msgSrc;
	  }

	
	//Implement when home page load
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String HomeGetMethod(	 
			@RequestParam(value = "topic", required = false, defaultValue= "0")int Id, 
			@RequestParam(value = "page", required = false, defaultValue= "1")int page,
			Model model, HttpSession session,RedirectAttributes attributes, Locale locale) {	
		if(checkLogin(session, locale)==false){			
			attributes.addFlashAttribute("users", new Users());
			attributes.addFlashAttribute("error", "Bạn chưa đăng nhập!.");
			session.setAttribute("redirectto", "home");
			return "redirect:/";
		}else{
			int UserID = Integer.parseInt(session.getAttribute("login").toString());
			checkBusyStatus(Id, UserID, session);
			if(Id==0){
				//User ID
				int UserId = Integer.parseInt(session.getAttribute("UserId").toString());
		
				//Set Session
				session.setAttribute("Record", "4");
				session.setAttribute("Id", "0");
				session.setAttribute("Page",page );

				//Get List Question
				//check is admin
				List<Questionmanagement> ListQuestion;
				if(userService.checkIsAdmin(UserId)==true){
					ListQuestion= QuestionmanagementService.getQuestionmanagementbyPageForAdmin(page-1,  UserID);
				}else{
					ListQuestion= QuestionmanagementService.getQuestionmanagementbyPage( page-1,  UserID);;
				}
				for(int i=0;i < ListQuestion.size();i++){
					if(ListQuestion.get(i).getQuestion().length() >= check){
						String abc = ListQuestion.get(i).getQuestion().toString();
						ListQuestion.get(i).setQuestion(abc.substring(0, get)+ ".....");
					}
				}
				model.addAttribute("listquestionmanagement", ListQuestion);		
				Setting setting = userService.getSetting(UserId);
				int numOfRecord = setting.getRecordNotRep();
				int numOfPagin = setting.getPaginDisplayNotRep();
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				model.addAttribute("curentOfPage",page);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserId));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());				
				model.addAttribute("fullname",userService.getFullnameByID(UserId));
				model.addAttribute("questionmanagements", new Questionmanagement());
				Users users = userService.getUserByUserID(UserID);
				logger.info("Tài khoản "+users.getUserName() +" đã vào danh sách chưa trả lời");
				return "home";
			}else{
				if(QuestionmanagementService.checkIdQuestionNotReply(Id)==true){
				if(QuestionmanagementService.checkQuestionIsBusy(Id,UserID)==true){
					model.addAttribute("warning","Hiện tại tài khoản "+userService.getFullnameByID(QuestionmanagementService.geUserIDByIdQuestion(Id))+ " đang làm việc với câu hỏi này.");
					//check is admin
					List<Questionmanagement> ListQuestion;
					if(userService.checkIsAdmin(UserID)==true){
						ListQuestion= QuestionmanagementService.getQuestionmanagementbyPageForAdmin(page-1,  UserID);
					}else{
						ListQuestion= QuestionmanagementService.getQuestionmanagementbyPage( page-1,  UserID);;
					}
					for(int i=0;i < ListQuestion.size();i++){
						if(ListQuestion.get(i).getQuestion().length() >= check){
							String abc = ListQuestion.get(i).getQuestion().toString();
							ListQuestion.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
					Setting setting = userService.getSetting(UserID);
					int numOfRecord = setting.getRecordNotRep();
					int numOfPagin = setting.getPaginDisplayNotRep();
					model.addAttribute("numOfRecord", ""+numOfRecord);
					model.addAttribute("numOfPagin", ""+numOfPagin);
					model.addAttribute("curentOfPage",page);
					model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
					model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
					model.addAttribute("listquestionmanagement", ListQuestion);
					return "home";
				}else{
					//set Session
					session.setAttribute("Id", Id);
					session.setAttribute("Page",page );
					model.addAttribute("abc", Id);
					QuestionmanagementService.updateBusyStatusAfter(Id,UserID);
					Questionmanagement questionmanagement = QuestionmanagementService.getQuestionmanagementbyID(Id);
					
					//check is admin
					List<Questionmanagement> ListQuestion;
					if(userService.checkIsAdmin(UserID)==true){
						ListQuestion= QuestionmanagementService.getQuestionmanagementbyPageForAdmin(page-1,  UserID);
					}else{
						ListQuestion= QuestionmanagementService.getQuestionmanagementbyPage( page-1,  UserID);;
					}
					for(int i=0;i < ListQuestion.size();i++){
						if(ListQuestion.get(i).getQuestion().length() >= check){
							String abc = ListQuestion.get(i).getQuestion().toString();
							ListQuestion.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
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
					
					session.setAttribute("email",questionmanagement.getQuestionEmail());
					
					//update busy status when user click question
					QuestionmanagementService.updateBusyStatus(Id,UserID);
					session.setAttribute("BusyStatus",UserID);
					return "home";
					}
				}else {
					return "redirect:/notalow";
				}
				
			}
		}
		
	}
	
	public void checkBusyStatus(int Id,int UserID, HttpSession session){
		if(session.getValue("BusyStatus") != null){
			QuestionmanagementService.updateBusyStatusAfter(Id,UserID); 
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
			Model model, HttpSession session, Locale locale) {		
			int UserID = Integer.parseInt(session.getAttribute("login").toString());
			int page =Integer.parseInt(session.getAttribute("Page").toString());
			
		//	List<Questionmanagement> ListQuestion= QuestionmanagementService.getQuestionmanagementbyPage_setting(page-1, 5);
			
			model.addAttribute("fullname",userService.getFullnameByID(UserID));
//			model.addAttribute("listquestionmanagement", ListQuestion);
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
					if(questionmanagement.getAnswer() ==""){
						model.addAttribute("error", "Bạn chưa nhập câu trả lời.");
						List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
						for(int i=0;i < ListQuestion1.size();i++){
							if(ListQuestion1.get(i).getQuestion().length() >= check){
								String abc = ListQuestion1.get(i).getQuestion().toString();
								ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						model.addAttribute("listquestionmanagement", ListQuestion1);
					}else{
						String email = session.getAttribute("email").toString();
						String title = "Trả lời câu hỏi tuyển sinh";
						String body = questionmanagement.getAnswer();
						MimeMessage mimeMessage = mailSender.createMimeMessage();
						try {
							
							Questionmanagement question = QuestionmanagementService.getQuestionmanagementbyID(Id);
							if(question.getAnswerBy() != null){
								// Xu ly thao tac song song
								Users information = userService.getUserByUserID(UserID);
								int author = information.getAuthorization();
								if(UserID == question.getAnswerBy()){
									
									 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
									 message.setTo(email);
									 message.setSubject(title);
									 
									 message.setText(body, true);
									// sends the e-mail
									mailSender.send(mimeMessage);
									
									Users users = userService.getUserByUserID(UserID);
									Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
									
									logger.info("Tài khoản "+users.getUserName()+" đã gửi trả lời cho "+ userquestion.getQuestionBy());
									
									model.addAttribute("message", msgSrc.getMessage("message.sendmail.success", null,locale));
									int result = QuestionmanagementService.updateAnswerbyId(Id,questionmanagement.getAnswer());
									if(result>0){
										
										List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
										for(int i=0;i < ListQuestion1.size();i++){
											if(ListQuestion1.get(i).getQuestion().length() >= check){
												String abc = ListQuestion1.get(i).getQuestion().toString();
												ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
											}
										}
										QuestionmanagementService.UpdateAnwserBy(Id, login);
										Setting setting = userService.getSetting(UserID);
										int numOfRecord = setting.getRecordNotRep();
										int numOfPagin = setting.getPaginDisplayNotRep();
										model.addAttribute("numOfRecord", ""+numOfRecord);
										model.addAttribute("numOfPagin", ""+numOfPagin);
										model.addAttribute("curentOfPage",page);
										model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
										model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
										model.addAttribute("listquestionmanagement", ListQuestion1);														
									}
								}else{
									if(author ==1){
										Users otheruser = userService.getUserByUserID(question.getAnswerBy());
										int otherauthor = otheruser.getAuthorization();
										if(otherauthor ==1){
											// null
											
											model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" trả lời");
										}else{
											
											 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
											 message.setTo(email);
											 message.setSubject(title);
											 
											 message.setText(body, true);
											// sends the e-mail
											mailSender.send(mimeMessage);
											
											Users users = userService.getUserByUserID(UserID);
											Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
											
											logger.info("Tài khoản "+users.getUserName()+" đã gửi trả lời cho "+ userquestion.getQuestionBy());
											model.addAttribute("message", msgSrc.getMessage("message.sendmail.success", null,locale));
											int result = QuestionmanagementService.updateAnswerbyId(Id,questionmanagement.getAnswer());
											if(result>0){
												
												List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
												for(int i=0;i < ListQuestion1.size();i++){
													if(ListQuestion1.get(i).getQuestion().length() >= check){
														String abc = ListQuestion1.get(i).getQuestion().toString();
														ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
													}
												}
												QuestionmanagementService.UpdateAnwserBy(Id, login);
												Setting setting = userService.getSetting(UserID);
												int numOfRecord = setting.getRecordNotRep();
												int numOfPagin = setting.getPaginDisplayNotRep();
												model.addAttribute("numOfRecord", ""+numOfRecord);
												model.addAttribute("numOfPagin", ""+numOfPagin);
												model.addAttribute("curentOfPage",page);
												model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
												model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
												model.addAttribute("listquestionmanagement", ListQuestion1);														
											}
										}
									}else{
										// null
										Users otheruser = userService.getUserByUserID(question.getAnswerBy());
										model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" trả lời");
									}
									// ket thuc xu ly thao tac song song
								}
								
							}else{
								
								 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
								 message.setTo(email);
								 message.setSubject(title);
								 
								 message.setText(body, true);
								// sends the e-mail
								mailSender.send(mimeMessage);
								
								Users users = userService.getUserByUserID(UserID);
								Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
								
								logger.info("Tài khoản "+users.getUserName()+" đã gửi trả lời cho "+ userquestion.getQuestionBy());
								
								model.addAttribute("message",  msgSrc.getMessage("message.sendmail.success", null,locale));
								int result = QuestionmanagementService.updateAnswerbyId(Id,questionmanagement.getAnswer());
								if(result>0){
									
									List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
									for(int i=0;i < ListQuestion1.size();i++){
										if(ListQuestion1.get(i).getQuestion().length() >= check){
											String abc = ListQuestion1.get(i).getQuestion().toString();
											ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
										}
									}
									QuestionmanagementService.UpdateAnwserBy(Id, login);
									Setting setting = userService.getSetting(UserID);
									int numOfRecord = setting.getRecordNotRep();
									int numOfPagin = setting.getPaginDisplayNotRep();
									model.addAttribute("numOfRecord", ""+numOfRecord);
									model.addAttribute("numOfPagin", ""+numOfPagin);
									model.addAttribute("curentOfPage",page);
									model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
									model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
									model.addAttribute("listquestionmanagement", ListQuestion1);														
								}
							}
							
							
																						
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
							model.addAttribute("error",  msgSrc.getMessage("message.sendmail.fail", null,locale));
						}						
					}
				}
				List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
				for(int i=0;i < ListQuestion1.size();i++){
					if(ListQuestion1.get(i).getQuestion().length() >= check){
						String abc = ListQuestion1.get(i).getQuestion().toString();
						ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
					}
				}
				QuestionmanagementService.UpdateAnwserBy(Id, login);
				Setting setting = userService.getSetting(UserID);
				int numOfRecord = setting.getRecordNotRep();
				int numOfPagin = setting.getPaginDisplayNotRep();
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				model.addAttribute("curentOfPage",page);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
			}else{
				if(actionsubmit.equals("save")){
					int Id = Integer.parseInt(session.getAttribute("Id").toString());
					int login = Integer.parseInt(session.getAttribute("login").toString());
					if(session.getAttribute("Id") !="0"){
						//xu ly luu cau tra loi
						if(checkboxdata!="0"){
							
						
							Questionmanagement question = QuestionmanagementService.getQuestionmanagementbyID(Id);
							if(question.getAnswerBy() != null){
								// Xu ly thao tac song song
								Users information = userService.getUserByUserID(UserID);
								int author = information.getAuthorization();
								if(author ==1){
									Users otheruser = userService.getUserByUserID(question.getAnswerBy());
									int otherauthor = otheruser.getAuthorization();
									if(otherauthor ==1){
										// null
										
										model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" trả lời");
									}else{
										int result = QuestionmanagementService.SaveTemporaryAnswerbyId(Id,questionmanagement.getAnswer());
										if(result>0){			
											List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
											for(int i=0;i < ListQuestion1.size();i++){
												if(ListQuestion1.get(i).getQuestion().length() >= check){
													String abc = ListQuestion1.get(i).getQuestion().toString();
													ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
												}
											}
											model.addAttribute("listquestionmanagement", ListQuestion1);
											Setting setting = userService.getSetting(UserID);
											int numOfRecord = setting.getRecordNotRep();
											int numOfPagin = setting.getPaginDisplayNotRep();
											model.addAttribute("numOfRecord", ""+numOfRecord);
											model.addAttribute("numOfPagin", ""+numOfPagin);
											model.addAttribute("curentOfPage",page);
											model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
											model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
											QuestionmanagementService.UpdateAnwserBy(Id, login);
											Users users = userService.getUserByUserID(UserID);
											Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
											
											logger.info("Tài khoản "+users.getUserName()+" đã lưu câu hỏi của người hỏi "+ userquestion.getQuestionBy());
											model.addAttribute("message",  msgSrc.getMessage("message.save.success", null,locale));
										}
									}
								}else{
									Users otheruser = userService.getUserByUserID(question.getAnswerBy());
									model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" trả lời");
								}
							}else{
								int result = QuestionmanagementService.SaveTemporaryAnswerbyId(Id,questionmanagement.getAnswer());
								if(result>0){			
									List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
									for(int i=0;i < ListQuestion1.size();i++){
										if(ListQuestion1.get(i).getQuestion().length() >= check){
											String abc = ListQuestion1.get(i).getQuestion().toString();
											ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
										}
									}
									model.addAttribute("listquestionmanagement", ListQuestion1);
									Setting setting = userService.getSetting(UserID);
									int numOfRecord = setting.getRecordNotRep();
									int numOfPagin = setting.getPaginDisplayNotRep();
									model.addAttribute("numOfRecord", ""+numOfRecord);
									model.addAttribute("numOfPagin", ""+numOfPagin);
									model.addAttribute("curentOfPage",page);
									model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
									model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
									QuestionmanagementService.UpdateAnwserBy(Id, login);
									model.addAttribute("message",  msgSrc.getMessage("message.save.success", null,locale));
									Users users = userService.getUserByUserID(UserID);
									Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
									
									logger.info("Tài khoản "+users.getUserName()+" đã lưu câu hỏi của người hỏi "+ userquestion.getQuestionBy());
								}
							}
						}
					}	
					List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
					for(int i=0;i < ListQuestion1.size();i++){
						if(ListQuestion1.get(i).getQuestion().length() >= check){
							String abc = ListQuestion1.get(i).getQuestion().toString();
							ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
					model.addAttribute("listquestionmanagement", ListQuestion1);
					Setting setting = userService.getSetting(UserID);
					int numOfRecord = setting.getRecordNotRep();
					int numOfPagin = setting.getPaginDisplayNotRep();
					model.addAttribute("numOfRecord", ""+numOfRecord);
					model.addAttribute("numOfPagin", ""+numOfPagin);
					model.addAttribute("curentOfPage",page);
					model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
					model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
				}else{
					if(actionsubmit.equals("delete")){
					//xu ly khi delete
						//xu ly khi nhan gui
						
						int Id = Integer.parseInt(session.getAttribute("Id").toString());
						int login = Integer.parseInt(session.getAttribute("login").toString());
						model.addAttribute("deletequestion",Id);
						if(session.getAttribute("Id") !="0"){
							
							//xu ly luu cau tra loi va gui mail
							Questionmanagement question = QuestionmanagementService.getQuestionmanagementbyID(Id);
							if(question.getDeleteBy() != null){
								// Xu ly thao tac song song
								Users information = userService.getUserByUserID(UserID);
								int author = information.getAuthorization();
								if(author ==1){
									Users otheruser = userService.getUserByUserID(question.getDeleteBy());
									int otherauthor = otheruser.getAuthorization();
									if(otherauthor ==1){
										// null
										
										model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" xóa");
									}else{
										int result = QuestionmanagementService.delete(Id);
										if(result>0){
											QuestionmanagementService.UpdateDelete(Id, login);
											Users users = userService.getUserByUserID(UserID);
											Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
											
											logger.info("Tài khoản "+users.getUserName()+" đã xóa câu hỏi của người hỏi "+ userquestion.getQuestionBy());
											model.addAttribute("message",  msgSrc.getMessage("message.delete.success", null,locale));
											List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
											for(int i=0;i < ListQuestion1.size();i++){
												if(ListQuestion1.get(i).getQuestion().length() >= check){
													String abc = ListQuestion1.get(i).getQuestion().toString();
													ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
												}
											}
											model.addAttribute("listquestionmanagement", ListQuestion1);
											Setting setting = userService.getSetting(UserID);
											int numOfRecord = setting.getRecordNotRep();
											int numOfPagin = setting.getPaginDisplayNotRep();
											model.addAttribute("numOfRecord", ""+numOfRecord);
											model.addAttribute("numOfPagin", ""+numOfPagin);
											model.addAttribute("curentOfPage",page);
											model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
											model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
											
										}
									}
								}else{
									Users otheruser = userService.getUserByUserID(question.getDeleteBy());
									model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" xóa");
								}
							}else{
								int result = QuestionmanagementService.delete(Id);
								if(result>0){
									QuestionmanagementService.UpdateDelete(Id, login);
									Users users = userService.getUserByUserID(UserID);
									Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
									
									logger.info("Tài khoản "+users.getUserName()+" đã xóa câu hỏi của người hỏi "+ userquestion.getQuestionBy());
									model.addAttribute("message", msgSrc.getMessage("message.delete.success", null,locale));
									List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
									for(int i=0;i < ListQuestion1.size();i++){
										if(ListQuestion1.get(i).getQuestion().length() >= check){
											String abc = ListQuestion1.get(i).getQuestion().toString();
											ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
										}
									}
									model.addAttribute("listquestionmanagement", ListQuestion1);
									
								}
							}
							//
							
						}
						List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
						for(int i=0;i < ListQuestion1.size();i++){
							if(ListQuestion1.get(i).getQuestion().length() >= check){
								String abc = ListQuestion1.get(i).getQuestion().toString();
								ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						model.addAttribute("listquestionmanagement", ListQuestion1);
						Setting setting = userService.getSetting(UserID);
						int numOfRecord = setting.getRecordNotRep();
						int numOfPagin = setting.getPaginDisplayNotRep();
						model.addAttribute("numOfRecord", ""+numOfRecord);
						model.addAttribute("numOfPagin", ""+numOfPagin);
						model.addAttribute("curentOfPage",page);
						model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
						model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
					}else{
						//xủ lý xóa tất cả
						int login = Integer.parseInt(session.getAttribute("login").toString());
						if(actionsubmit.equals("deleteall")){
							List<Questionmanagement> returnlist = QuestionmanagementService.deleteall(checkboxdata,login);
							
							model.addAttribute("message",msgSrc.getMessage("message.deletemulti.success", null,locale));
							List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( page-1,  UserID);
							for(int i=0;i < ListQuestion1.size();i++){
								if(ListQuestion1.get(i).getQuestion().length() >= check){
									String abc = ListQuestion1.get(i).getQuestion().toString();
									ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
							if(returnlist !=null){
								for(int i =0; i< returnlist.size();i++){
									Users users = userService.getUserByUserID(UserID);
									
									String question = returnlist.get(i).getQuestion();
									if(question.length() > 50){
										question.substring(0, 45);
										question = question + "...";
									}
									logger.info("Tài khoản "+users.getUserName()+" xóa câu hỏi " + question);
								}
							}
							model.addAttribute("listquestionmanagement", ListQuestion1);
							Setting setting = userService.getSetting(UserID);
							int numOfRecord = setting.getRecordNotRep();
							int numOfPagin = setting.getPaginDisplayNotRep();
							model.addAttribute("numOfRecord", ""+numOfRecord);
							model.addAttribute("numOfPagin", ""+numOfPagin);
							model.addAttribute("curentOfPage",page);
							model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
							model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
						}else{
	
							if(actionsubmit.equals("change")){
								if(!changeitems.equals("0")){
									int numOfRecord = Integer.parseInt(changeitems);
									int numOfPagin = Integer.parseInt(changepagin);
									userService.updateSetting(UserID, numOfRecord, numOfPagin);
							
									model.addAttribute("numOfRecord",changeitems);
									model.addAttribute("numOfPagin",changepagin);
									
									Setting setting = userService.getSetting(UserID);
									model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
									model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
									
									List<Questionmanagement> ListQuestion1= QuestionmanagementService. getQuestionmanagementbyPage( 0,  UserID);
									for(int i=0;i < ListQuestion1.size();i++){
										if(ListQuestion1.get(i).getQuestion().length() >= check){
											String abc = ListQuestion1.get(i).getQuestion().toString();
											ListQuestion1.get(i).setQuestion(abc.substring(0, get)+ ".....");
										}
									}
									model.addAttribute("listquestionmanagement", ListQuestion1);
									Users users = userService.getUserByUserID(UserID);
									
									logger.info("Tài khoản "+users.getUserName()+" thay đổi cấu hình phân trang");
									model.addAttribute("message",msgSrc.getMessage("message.changconfig.paging.success", null,locale));
			
								}
							}else{
							//xu ly tim kiem
							List<Questionmanagement> list = QuestionmanagementService.searchIdex(actionsubmit,"1");
							for(int i=0;i < list.size();i++){
								if(list.get(i).getQuestion().length() >= check){
									String abc = list.get(i).getQuestion().toString();
									list.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
							Users users = userService.getUserByUserID(UserID);
							Setting setting = userService.getSetting(UserID);
							int numOfRecord = setting.getRecordNotRep();
							int numOfPagin = setting.getPaginDisplayNotRep();
							model.addAttribute("numOfRecord", ""+numOfRecord);
							model.addAttribute("numOfPagin", ""+numOfPagin);
							model.addAttribute("curentOfPage",page);
							model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
							model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
							logger.info("Tài khoản "+users.getUserName()+" tìm kiếm "+ actionsubmit);
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

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/dsluutam", method = RequestMethod.GET)
	public String dsluutam(@RequestParam(value = "topic", required = false, defaultValue= "0")int Id, 
			@RequestParam(value = "page", required = false, defaultValue= "1")int page,
			Model model, HttpSession session, RedirectAttributes attributes, Locale locale) {
		if(checkLogin(session, locale)==false){			
			attributes.addFlashAttribute("users", new Users());
			attributes.addFlashAttribute("error", "Bạn chưa đăng nhập!.");
			session.setAttribute("redirectto", "dsluutam");
			return "redirect:/";
		}else{
			int UserID = Integer.parseInt(session.getAttribute("login").toString());
			checkBusyStatus(Id, UserID, session);
			if(Id==0){
				session.setAttribute("Id",0);
				session.setAttribute("Page",page );
				List<Questionmanagement> savelist;
				if(session.getValue("Admin")==null){	
					//user nomal
					savelist= QuestionmanagementService.savelist(page-1, UserID);
					for(int i=0;i < savelist.size();i++){
						if(savelist.get(i).getQuestion().length() >= check){
							String abc = savelist.get(i).getQuestion().toString();
							savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}else{
					//admin
					savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
					for(int i=0;i < savelist.size();i++){
						if(savelist.get(i).getQuestion().length() >= check){
							String abc = savelist.get(i).getQuestion().toString();
							savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
					
				}
				
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
				//check is admin
				if(userService.checkIsAdmin(UserID)==true){
					model.addAttribute("isAdmin","admin");
				}	
				Users users = userService.getUserByUserID(UserID);
				logger.info("Tài khoản "+users.getUserName()+" đã vào danh sách lưu tạm");
				return "list-saved";
				
			}else{
				if(QuestionmanagementService.checkIdQuestionSave(Id)==true){
				if(QuestionmanagementService.checkSavaListByUserId(UserID , Id)==true){
				session.setAttribute("Id", Id);
				session.setAttribute("Page",page );
				Questionmanagement save = QuestionmanagementService.savequestion(Id);
				List<Questionmanagement> savelist;
				if(session.getValue("Admin")==null){	
					//user nomal
					savelist= QuestionmanagementService.savelist(page-1, UserID);
					for(int i=0;i < savelist.size();i++){
						if(savelist.get(i).getQuestion().length() >= check){
							String abc = savelist.get(i).getQuestion().toString();
							savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}else{
					//admin
					savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
					for(int i=0;i < savelist.size();i++){
						if(savelist.get(i).getQuestion().length() >= check){
							String abc = savelist.get(i).getQuestion().toString();
							savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
					
				}
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
				
				//check is admin
				if(userService.checkIsAdmin(UserID)==true){
					model.addAttribute("isAdmin","admin");
				}	
				return "list-saved";
			}else{
				return "redirect:/notalow";
			}
			}else{
				return "redirect:/notalow";
			}
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
			HttpSession session, Locale locale) {
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
					if(questionmanagement.getAnswer() ==""){
						model.addAttribute("error", "Bạn chưa nhập câu trả lời.");
						List<Questionmanagement> savelist;
						if(session.getValue("Admin")==null){	
							//user nomal
							savelist= QuestionmanagementService.savelist(page-1, UserID);
							for(int i=0;i < savelist.size();i++){
								if(savelist.get(i).getQuestion().length() >= check){
									String abc = savelist.get(i).getQuestion().toString();
									savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
						}else{
							//admin
							savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
							for(int i=0;i < savelist.size();i++){
								if(savelist.get(i).getQuestion().length() >= check){
									String abc = savelist.get(i).getQuestion().toString();
									savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
							
						}
						model.addAttribute("savequestionlist", savelist);
						
					}else{
						model.addAttribute("anwser",questionmanagement.getAnswer());
						String email = session.getAttribute("email").toString();
						String title = "Trả lời câu hỏi tuyển sinh";
						String body = questionmanagement.getAnswer();
						
						MimeMessage mimeMessage = mailSender.createMimeMessage();
						
						try {
			
							Questionmanagement question = QuestionmanagementService.getQuestionmanagementbyID(Id);
							if(question.getAnswerBy() != null){
								// Xu ly thao tac song song
								Users information = userService.getUserByUserID(UserID);
								int author = information.getAuthorization();
								if(UserID == question.getAnswerBy()){
									
									 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
									 message.setTo(email);
									 message.setSubject(title);
									 
									 message.setText(body, true);
									// sends the e-mail
									mailSender.send(mimeMessage);
									Users users = userService.getUserByUserID(UserID);
									Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
									logger.info("Tài khoản "+users.getUserName()+" đã gửi trả lời cho người hỏi "+userquestion.getQuestionBy());
									
									model.addAttribute("message",msgSrc.getMessage("message.sendmail.success", null,locale));
									int result = QuestionmanagementService.SendAnwser(Id,questionmanagement.getAnswer());
									if(result>0){
										List<Questionmanagement> savelist;
										if(session.getValue("Admin")==null){	
											//user nomal
											savelist= QuestionmanagementService.savelist(page-1, UserID);
											for(int i=0;i < savelist.size();i++){
												if(savelist.get(i).getQuestion().length() >= check){
													String abc = savelist.get(i).getQuestion().toString();
													savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
												}
											}
											QuestionmanagementService.UpdateAnwserBy(Id, login);
										}else{
											//admin
											savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
											for(int i=0;i < savelist.size();i++){
												if(savelist.get(i).getQuestion().length() >= check){
													String abc = savelist.get(i).getQuestion().toString();
													savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
												}
											}
											QuestionmanagementService.UpdateAnwserBy(Id, login);
										}
										Setting setting = userService.getSetting(UserID);
										int numOfRecord = setting.getRecordNotRep();
										int numOfPagin = setting.getPaginDisplayNotRep();
										model.addAttribute("numOfRecord", ""+numOfRecord);
										model.addAttribute("numOfPagin", ""+numOfPagin);
										model.addAttribute("curentOfPage",page);
										model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
										model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
										model.addAttribute("savequestionlist", savelist);
										
										
								}	
								}else{
									if(author ==1){
										Users otheruser = userService.getUserByUserID(question.getAnswerBy());
										int otherauthor = otheruser.getAuthorization();
										if(otherauthor ==1){
											// null
											
											model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" trả lời");
										}else{
											//xu ly luu cau tra loi va gui mail
											
											 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
											 message.setTo(email);
											 message.setSubject(title);
											 
											 message.setText(body, true);
											// sends the e-mail
											mailSender.send(mimeMessage);
											Users users = userService.getUserByUserID(UserID);
											Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
											logger.info("Tài khoản "+users.getUserName()+" đã gửi trả lời cho người hỏi "+userquestion.getQuestionBy());
											
											model.addAttribute("message",msgSrc.getMessage("message.sendmail.success", null,locale));
											int result = QuestionmanagementService.SendAnwser(Id,questionmanagement.getAnswer());
											if(result>0){
												List<Questionmanagement> savelist;
												if(session.getValue("Admin")==null){	
													//user nomal
													savelist= QuestionmanagementService.savelist(page-1, UserID);
													for(int i=0;i < savelist.size();i++){
														if(savelist.get(i).getQuestion().length() >= check){
															String abc = savelist.get(i).getQuestion().toString();
															savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
														}
													}
													QuestionmanagementService.UpdateAnwserBy(Id, login);
												}else{
													//admin
													savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
													for(int i=0;i < savelist.size();i++){
														if(savelist.get(i).getQuestion().length() >= check){
															String abc = savelist.get(i).getQuestion().toString();
															savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
														}
													}
													QuestionmanagementService.UpdateAnwserBy(Id, login);
												}
												
												model.addAttribute("savequestionlist", savelist);
												
												
										}	
										}
									}else{
										// null
										Users otheruser = userService.getUserByUserID(question.getAnswerBy());
										model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" trả lời");
									}
								}
								
								// ket thuc xu ly thao tac song song
							}else{
								//xu ly luu cau tra loi va gui mail
								
								 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
								 message.setTo(email);
								 message.setSubject(title);
								 
								 message.setText(body, true);
								// sends the e-mail
								mailSender.send(mimeMessage);
								Users users = userService.getUserByUserID(UserID);
								Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
								logger.info("Tài khoản "+users.getUserName()+" đã gửi trả lời cho người hỏi "+userquestion.getQuestionBy());
								
								model.addAttribute("message",msgSrc.getMessage("message.sendmail.success", null,locale));
								int result = QuestionmanagementService.SendAnwser(Id,questionmanagement.getAnswer());
								if(result>0){
									List<Questionmanagement> savelist;
									if(session.getValue("Admin")==null){	
										//user nomal
										savelist= QuestionmanagementService.savelist(page-1, UserID);
										for(int i=0;i < savelist.size();i++){
											if(savelist.get(i).getQuestion().length() >= check){
												String abc = savelist.get(i).getQuestion().toString();
												savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
											}
										}
										QuestionmanagementService.UpdateAnwserBy(Id, login);
									}else{
										//admin
										savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
										for(int i=0;i < savelist.size();i++){
											if(savelist.get(i).getQuestion().length() >= check){
												String abc = savelist.get(i).getQuestion().toString();
												savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
											}
										}
										QuestionmanagementService.UpdateAnwserBy(Id, login);
										
									}
									
									model.addAttribute("savequestionlist", savelist);
									
									
							}	
							}
							
							
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
							model.addAttribute("error", msgSrc.getMessage("message.sendmail.fail", null,locale));
						}
						
										
					}
					List<Questionmanagement> savelist;
					if(session.getValue("Admin")==null){	
						//user nomal
						savelist= QuestionmanagementService.savelist(page-1, UserID);
						for(int i=0;i < savelist.size();i++){
							if(savelist.get(i).getQuestion().length() >= check){
								String abc = savelist.get(i).getQuestion().toString();
								savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						QuestionmanagementService.UpdateAnwserBy(Id, login);
					}else{
						//admin
						savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
						for(int i=0;i < savelist.size();i++){
							if(savelist.get(i).getQuestion().length() >= check){
								String abc = savelist.get(i).getQuestion().toString();
								savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						QuestionmanagementService.UpdateAnwserBy(Id, login);
					}
					Setting setting = userService.getSetting(UserID);
					int numOfRecord = setting.getRecordNotRep();
					int numOfPagin = setting.getPaginDisplayNotRep();
					model.addAttribute("numOfRecord", ""+numOfRecord);
					model.addAttribute("numOfPagin", ""+numOfPagin);
					model.addAttribute("curentOfPage",page);
					model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
					model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
					model.addAttribute("savequestionlist", savelist);
				}
			}else{
				if(actionsubmit.equals("save")){
					int Id = Integer.parseInt(session.getAttribute("Id").toString());
					int login = Integer.parseInt(session.getAttribute("login").toString());
					model.addAttribute("anwser",Id);
					if(session.getAttribute("Id") !="0"){
						//xu ly luu cau tra loi va gui mail

						Questionmanagement question = QuestionmanagementService.getQuestionmanagementbyID(Id);
						if(question.getAnswerBy() != null){
							// Xu ly thao tac song song
							Users information = userService.getUserByUserID(UserID);
							int author = information.getAuthorization();
							if(UserID == question.getAnswerBy()){
								int result = QuestionmanagementService.SaveAnwser(Id,questionmanagement.getAnswer());
								if(result>0){
									Users users = userService.getUserByUserID(UserID);
									Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
									
									logger.info("Tài khoản "+users.getUserName()+" đã lưu câu hỏi của người hỏi "+ userquestion.getQuestionBy());
									model.addAttribute("message",msgSrc.getMessage("message.save.success", null,locale));
									QuestionmanagementService.UpdateAnwserBy(Id, login);
									List<Questionmanagement> savelist;
									if(session.getValue("Admin")==null){	
										//user nomal
										savelist= QuestionmanagementService.savelist(page-1, UserID);
										for(int i=0;i < savelist.size();i++){
											if(savelist.get(i).getQuestion().length() >= check){
												String abc = savelist.get(i).getQuestion().toString();
												savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
											}
										}
										QuestionmanagementService.UpdateAnwserBy(Id, login);
									}else{
										//admin
										savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
										for(int i=0;i < savelist.size();i++){
											if(savelist.get(i).getQuestion().length() >= check){
												String abc = savelist.get(i).getQuestion().toString();
												savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
											}
										}
										QuestionmanagementService.UpdateAnwserBy(Id, login);
										
									}
									
									model.addAttribute("savequestionlist", savelist);
								}
							
							}else{
								if(author ==1){
									Users otheruser = userService.getUserByUserID(question.getAnswerBy());
									int otherauthor = otheruser.getAuthorization();
									if(otherauthor ==1){
										// null
										
										model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" trả lời");
									}else{
										int result = QuestionmanagementService.SaveAnwser(Id,questionmanagement.getAnswer());
										if(result>0){
											Users users = userService.getUserByUserID(UserID);
											Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
											
											logger.info("Tài khoản "+users.getUserName()+" đã lưu câu hỏi của người hỏi "+ userquestion.getQuestionBy());
											model.addAttribute("message",msgSrc.getMessage("message.save.success", null,locale));
											QuestionmanagementService.UpdateAnwserBy(Id, login);
											List<Questionmanagement> savelist;
											if(session.getValue("Admin")==null){	
												//user nomal
												savelist= QuestionmanagementService.savelist(page-1, UserID);
												for(int i=0;i < savelist.size();i++){
													if(savelist.get(i).getQuestion().length() >= check){
														String abc = savelist.get(i).getQuestion().toString();
														savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
													}
												}
												
												QuestionmanagementService.UpdateAnwserBy(Id, login);
											}else{
												//admin
												savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
												for(int i=0;i < savelist.size();i++){
													if(savelist.get(i).getQuestion().length() >= check){
														String abc = savelist.get(i).getQuestion().toString();
														savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
													}
												}
												QuestionmanagementService.UpdateAnwserBy(Id, login);
												
											}
											Setting setting = userService.getSetting(UserID);
											int numOfRecord = setting.getRecordNotRep();
											int numOfPagin = setting.getPaginDisplayNotRep();
											model.addAttribute("numOfRecord", ""+numOfRecord);
											model.addAttribute("numOfPagin", ""+numOfPagin);
											model.addAttribute("curentOfPage",page);
											model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
											model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
											model.addAttribute("savequestionlist", savelist);
										}
									}
								}else{
									Users otheruser = userService.getUserByUserID(question.getAnswerBy());
									model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" trả lời");
								}
							}
							
						}else{
							int result = QuestionmanagementService.SaveAnwser(Id,questionmanagement.getAnswer());
							if(result>0){
								Users users = userService.getUserByUserID(UserID);
								Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
								
								logger.info("Tài khoản "+users.getUserName()+" đã lưu câu hỏi của người hỏi "+ userquestion.getQuestionBy());
								model.addAttribute("message","Lưu thành công");
								QuestionmanagementService.UpdateAnwserBy(Id, login);
								List<Questionmanagement> savelist;
								if(session.getValue("Admin")==null){	
									//user nomal
									savelist= QuestionmanagementService.savelist(page-1, UserID);
									for(int i=0;i < savelist.size();i++){
										if(savelist.get(i).getQuestion().length() >= check){
											String abc = savelist.get(i).getQuestion().toString();
											savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
										}
									}
									QuestionmanagementService.UpdateAnwserBy(Id, login);
								}else{
									//admin
									savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
									for(int i=0;i < savelist.size();i++){
										if(savelist.get(i).getQuestion().length() >= check){
											String abc = savelist.get(i).getQuestion().toString();
											savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
										}
									}
									QuestionmanagementService.UpdateAnwserBy(Id, login);
									
								}
								
								model.addAttribute("savequestionlist", savelist);
							}
						}
						//
						
						
					}	
					List<Questionmanagement> savelist;
					if(session.getValue("Admin")==null){	
						//user nomal
						savelist= QuestionmanagementService.savelist(page-1, UserID);
						for(int i=0;i < savelist.size();i++){
							if(savelist.get(i).getQuestion().length() >= check){
								String abc = savelist.get(i).getQuestion().toString();
								savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						
						QuestionmanagementService.UpdateAnwserBy(Id, login);
					}else{
						//admin
						savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
						for(int i=0;i < savelist.size();i++){
							if(savelist.get(i).getQuestion().length() >= check){
								String abc = savelist.get(i).getQuestion().toString();
								savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						QuestionmanagementService.UpdateAnwserBy(Id, login);
						
					}
					Setting setting = userService.getSetting(UserID);
					int numOfRecord = setting.getRecordNotRep();
					int numOfPagin = setting.getPaginDisplayNotRep();
					model.addAttribute("numOfRecord", ""+numOfRecord);
					model.addAttribute("numOfPagin", ""+numOfPagin);
					model.addAttribute("curentOfPage",page);
					model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
					model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
					model.addAttribute("savequestionlist", savelist);
				}else{
					if(actionsubmit.equals("delete")){
					//xu ly khi delete
						//xu ly khi nhan gui
						int Id = Integer.parseInt(session.getAttribute("Id").toString());
						int login = Integer.parseInt(session.getAttribute("login").toString());
						model.addAttribute("deletequestion",Id);
						if(session.getAttribute("Id") !="0"){
							//xu ly luu cau tra loi va gui mail
							//xu ly luu cau tra loi va gui mail
							Questionmanagement question = QuestionmanagementService.getQuestionmanagementbyID(Id);
							if(question.getDeleteBy() != null){
								// Xu ly thao tac song song
								Users information = userService.getUserByUserID(UserID);
								int author = information.getAuthorization();
								if(UserID == question.getDeleteBy()){
									int result = QuestionmanagementService.deletesavequestion(Id);
									
									if(result>0){
										Users users = userService.getUserByUserID(UserID);
										Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
										
										logger.info("Tài khoản "+users.getUserName()+" đã xóa câu hỏi của người hỏi "+ userquestion.getQuestionBy());
										QuestionmanagementService.UpdateDelete(Id, login);
										model.addAttribute("message", msgSrc.getMessage("message.delete.success", null,locale));
										List<Questionmanagement> savelist;
										if(session.getValue("Admin")==null){	
											//user nomal
											savelist= QuestionmanagementService.savelist(page-1, UserID);
											for(int i=0;i < savelist.size();i++){
												if(savelist.get(i).getQuestion().length() >= check){
													String abc = savelist.get(i).getQuestion().toString();
													savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
												}
											}
										}else{
											//admin
											savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
											for(int i=0;i < savelist.size();i++){
												if(savelist.get(i).getQuestion().length() >= check){
													String abc = savelist.get(i).getQuestion().toString();
													savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
												}
											}
											
										}
										Setting setting = userService.getSetting(UserID);
										int numOfRecord = setting.getRecordNotRep();
										int numOfPagin = setting.getPaginDisplayNotRep();
										model.addAttribute("numOfRecord", ""+numOfRecord);
										model.addAttribute("numOfPagin", ""+numOfPagin);
										model.addAttribute("curentOfPage",page);
										model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
										model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
										model.addAttribute("savequestionlist", savelist);
									}
								}else{
									if(author ==1){
										Users otheruser = userService.getUserByUserID(question.getDeleteBy());
										int otherauthor = otheruser.getAuthorization();
										if(otherauthor ==1){
											// null
											
											model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" xóa");
										}else{
											int result = QuestionmanagementService.deletesavequestion(Id);
											
											if(result>0){
												QuestionmanagementService.UpdateDelete(Id, login);
												model.addAttribute("message", msgSrc.getMessage("message.delete.success", null,locale));
												List<Questionmanagement> savelist;
												if(session.getValue("Admin")==null){	
													//user nomal
													savelist= QuestionmanagementService.savelist(page-1, UserID);
													for(int i=0;i < savelist.size();i++){
														if(savelist.get(i).getQuestion().length() >= check){
															String abc = savelist.get(i).getQuestion().toString();
															savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
														}
													}
												}else{
													//admin
													savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
													for(int i=0;i < savelist.size();i++){
														if(savelist.get(i).getQuestion().length() >= check){
															String abc = savelist.get(i).getQuestion().toString();
															savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
														}
													}
													
												}
												model.addAttribute("savequestionlist", savelist);
											}
										}
									}else{
										Users otheruser = userService.getUserByUserID(question.getDeleteBy());
										model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" xóa");
									}
								}
								
							}else{
								int result = QuestionmanagementService.deletesavequestion(Id);
								
								if(result>0){
									QuestionmanagementService.UpdateDelete(Id, login);
									Users users = userService.getUserByUserID(UserID);
									Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
									
									logger.info("Tài khoản "+users.getUserName()+" đã xóa câu hỏi của người hỏi "+ userquestion.getQuestionBy());
									model.addAttribute("message", msgSrc.getMessage("message.delete.success", null,locale));
									List<Questionmanagement> savelist;
									if(session.getValue("Admin")==null){	
										//user nomal
										savelist= QuestionmanagementService.savelist(page-1, UserID);
										for(int i=0;i < savelist.size();i++){
											if(savelist.get(i).getQuestion().length() >= check){
												String abc = savelist.get(i).getQuestion().toString();
												savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
											}
										}
									}else{
										//admin
										savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
										for(int i=0;i < savelist.size();i++){
											if(savelist.get(i).getQuestion().length() >= check){
												String abc = savelist.get(i).getQuestion().toString();
												savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
											}
										}
										
									}
									model.addAttribute("savequestionlist", savelist);
								}
							}
							
							//
							
						}
						List<Questionmanagement> savelist;
						if(session.getValue("Admin")==null){	
							//user nomal
							savelist= QuestionmanagementService.savelist(page-1, UserID);
							for(int i=0;i < savelist.size();i++){
								if(savelist.get(i).getQuestion().length() >= check){
									String abc = savelist.get(i).getQuestion().toString();
									savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
						}else{
							//admin
							savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
							for(int i=0;i < savelist.size();i++){
								if(savelist.get(i).getQuestion().length() >= check){
									String abc = savelist.get(i).getQuestion().toString();
									savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
							
						}
						Setting setting = userService.getSetting(UserID);
						int numOfRecord = setting.getRecordNotRep();
						int numOfPagin = setting.getPaginDisplayNotRep();
						model.addAttribute("numOfRecord", ""+numOfRecord);
						model.addAttribute("numOfPagin", ""+numOfPagin);
						model.addAttribute("curentOfPage",page);
						model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
						model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
						model.addAttribute("savequestionlist", savelist);
					}else{
						//xủ lý xóa tất cả
						if(actionsubmit.equals("deleteall")){
							int login = Integer.parseInt(session.getAttribute("login").toString());
							List<Questionmanagement> returnlist =QuestionmanagementService.deleteall(checkboxdata,login);
							model.addAttribute("message", msgSrc.getMessage("message.deletemulti.success", null,locale));
							List<Questionmanagement> savelist;
							if(session.getValue("Admin")==null){	
								//user nomal
								savelist= QuestionmanagementService.savelist(page-1, UserID);
								for(int i=0;i < savelist.size();i++){
									if(savelist.get(i).getQuestion().length() >= check){
										String abc = savelist.get(i).getQuestion().toString();
										savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}else{
								//admin
								savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
								for(int i=0;i < savelist.size();i++){
									if(savelist.get(i).getQuestion().length() >= check){
										String abc = savelist.get(i).getQuestion().toString();
										savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
								
							}
							if(returnlist !=null){
								for(int i =0; i< returnlist.size();i++){
									Users users = userService.getUserByUserID(UserID);
									
									String question = returnlist.get(i).getQuestion();
									if(question.length() > 50){
										question.substring(0, 45);
										question = question + "...";
									}
									logger.info("Tài khoản "+users.getUserName()+" xóa câu hỏi " + question);
								}
							}
							Setting setting = userService.getSetting(UserID);
							int numOfRecord = setting.getRecordNotRep();
							int numOfPagin = setting.getPaginDisplayNotRep();
							model.addAttribute("numOfRecord", ""+numOfRecord);
							model.addAttribute("numOfPagin", ""+numOfPagin);
							model.addAttribute("curentOfPage",page);
							model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
							model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
							model.addAttribute("savequestionlist", savelist);
						}else{
							if(actionsubmit.equals("change")){
								if(!changeitems.equals("0")){
									int numOfRecord = Integer.parseInt(changeitems);
									int numOfPagin = Integer.parseInt(changepagin);
									userService.updateSettingSaved(UserID, numOfRecord, numOfPagin);
							
									model.addAttribute("numOfRecord",changeitems);
									model.addAttribute("numOfPagin",changepagin);
									
									Setting setting = userService.getSetting(UserID);
									model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(2, UserID));
									model.addAttribute("noOfDisplay", setting.getPaginDisplayTemp());
									
									List<Questionmanagement> savelist;
									if(session.getValue("Admin")==null){	
										//user nomal
										savelist= QuestionmanagementService.savelist(page-1, UserID);
										for(int i=0;i < savelist.size();i++){
											if(savelist.get(i).getQuestion().length() >= check){
												String abc = savelist.get(i).getQuestion().toString();
												savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
											}
										}
									}else{
										//admin
										savelist= QuestionmanagementService.getSaveListForAdmin(page-1,UserID);
										for(int i=0;i < savelist.size();i++){
											if(savelist.get(i).getQuestion().length() >= check){
												String abc = savelist.get(i).getQuestion().toString();
												savelist.get(i).setQuestion(abc.substring(0, get)+ ".....");
											}
										}
										
									}
									
									model.addAttribute("savequestionlist", savelist);
									Users users = userService.getUserByUserID(UserID);
								
									
									logger.info("Tài khoản "+users.getUserName()+" thay đổi cấu hình phân trang");
									model.addAttribute("message",msgSrc.getMessage("message.changconfig.paging.success", null,locale));
			
								}
							}else{
							//xu ly tim kiem							
							List<Questionmanagement> list;
							if(session.getValue("Admin")!=null){
								list = QuestionmanagementService.searchIdex(actionsubmit,"2");
								for(int i=0;i < list.size();i++){
									if(list.get(i).getQuestion().length() >= check){
										String abc = list.get(i).getQuestion().toString();
										list.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}else{
								list = QuestionmanagementService.searchIdexForAdmin(actionsubmit,"2",UserID);
								for(int i=0;i < list.size();i++){
									if(list.get(i).getQuestion().length() >= check){
										String abc = list.get(i).getQuestion().toString();
										list.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}
							Setting setting = userService.getSetting(UserID);
							int numOfRecord = setting.getRecordNotRep();
							int numOfPagin = setting.getPaginDisplayNotRep();
							model.addAttribute("numOfRecord", ""+numOfRecord);
							model.addAttribute("numOfPagin", ""+numOfPagin);
							model.addAttribute("curentOfPage",page);
							model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
							model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());
							model.addAttribute("savequestionlist", list);
							Users users = userService.getUserByUserID(UserID);
							
							
							logger.info("Tài khoản "+users.getUserName()+" tìm kiếm "+actionsubmit);
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
			Model model, HttpSession session, RedirectAttributes attributes, Locale locale) {
		
		if(checkLogin(session, locale)==false){			
			attributes.addFlashAttribute("users", new Users());
			attributes.addFlashAttribute("error", "Bạn chưa đăng nhập!.");
			session.setAttribute("redirectto", "dsdatraloi");
			return "redirect:/";
		}else{
			int UserID = Integer.parseInt(session.getAttribute("login").toString());
			checkBusyStatus(Id, UserID, session);
			if(Id==0){
				session.setAttribute("Id", "0");
				session.setAttribute("Page",page );
				
				
				List<Questionmanagement> Deletequestionlist;
				if(session.getValue("Admin")==null){	
					Deletequestionlist = QuestionmanagementService.repliedList(page-1, UserID);
					for(int i=0;i < Deletequestionlist.size();i++){
						if(Deletequestionlist.get(i).getQuestion().length() >= check){
							String abc = Deletequestionlist.get(i).getQuestion().toString();
							Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}else{
					Deletequestionlist = QuestionmanagementService.getRepliedListForAdmin(page-1, UserID);
					for(int i=0;i < Deletequestionlist.size();i++){
						if(Deletequestionlist.get(i).getQuestion().length() >= check){
							String abc = Deletequestionlist.get(i).getQuestion().toString();
							Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}
								
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
				//check is admin
				if(userService.checkIsAdmin(UserID)==true){
					model.addAttribute("isAdmin","admin");
				}	
				Users users = userService.getUserByUserID(UserID);				
				logger.info("Tài khoản "+users.getUserName()+" vào danh sách câu hỏi đã trả lời");
				return "list-replied";
				
			}else{
				if(QuestionmanagementService.checkIdQuestionReplied(Id)==true){
				if(QuestionmanagementService.checkSavaListByUserId(UserID , Id)==true){
				
				
				int login = Integer.parseInt(session.getAttribute("login").toString());
				session.setAttribute("Id", Id);
				session.setAttribute("Page",page );
				Questionmanagement delete = QuestionmanagementService.repliedquestion(Id);
				List<Questionmanagement> Deletequestionlist;
				if(session.getValue("Admin")==null){	
					Deletequestionlist = QuestionmanagementService.repliedList(page-1, UserID);
					for(int i=0;i < Deletequestionlist.size();i++){
						if(Deletequestionlist.get(i).getQuestion().length() >= check){
							String abc = Deletequestionlist.get(i).getQuestion().toString();
							Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}else{
					Deletequestionlist = QuestionmanagementService.getRepliedListForAdmin(page-1, UserID);
					for(int i=0;i < Deletequestionlist.size();i++){
						if(Deletequestionlist.get(i).getQuestion().length() >= check){
							String abc = Deletequestionlist.get(i).getQuestion().toString();
							Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}
				Users username = QuestionmanagementService.getusername(login);
				model.addAttribute("username",username.getFullName());
				
				Setting setting = userService.getSetting(UserID);
				int numOfRecord = setting.getRecordRepied();
				int numOfPagin = setting.getPaginDisplayReplied();
				
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				model.addAttribute("curentOfPage",page);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(3, UserID));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayReplied());
				//check is admin
				if(userService.checkIsAdmin(UserID)==true){
					model.addAttribute("isAdmin","admin");
				}	
				
				
				model.addAttribute("fullname",userService.getFullnameByID(UserID));
				model.addAttribute("questionmanagements", delete);
				model.addAttribute("replylust", Deletequestionlist);
				return "list-replied";
			}else{
				return "redirect:/notalow";
			}
			}else{
				return "redirect:/notalow";
			}
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
			HttpSession session,Locale locale) {
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
					
					//xu ly luu cau tra loi va gui mail
					Questionmanagement question = QuestionmanagementService.getQuestionmanagementbyID(Id);
					if(question.getDeleteBy() != null){
						// Xu ly thao tac song song
						Users information = userService.getUserByUserID(UserID);
						int author = information.getAuthorization();
						if(UserID == question.getDeleteBy()){
							int result = QuestionmanagementService.deleterepliedquestion(Id);
							if(result>0){
								QuestionmanagementService.UpdateDelete(Id, login);
								Users users = userService.getUserByUserID(UserID);
								Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
								
								logger.info("Tài khoản "+users.getUserName()+" đã xóa câu hỏi của người hỏi "+ userquestion.getQuestionBy());
								model.addAttribute("message",msgSrc.getMessage("message.delete.success", null, locale));
								List<Questionmanagement> Deletequestionlist;
								if(session.getValue("Admin")==null){	
									Deletequestionlist = QuestionmanagementService.repliedList(page-1, UserID);
									for(int i=0;i < Deletequestionlist.size();i++){
										if(Deletequestionlist.get(i).getQuestion().length() >= check){
											String abc = Deletequestionlist.get(i).getQuestion().toString();
											Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
										}
									}
								}else{
									Deletequestionlist = QuestionmanagementService.getRepliedListForAdmin(page-1, UserID);
									for(int i=0;i < Deletequestionlist.size();i++){
										if(Deletequestionlist.get(i).getQuestion().length() >= check){
											String abc = Deletequestionlist.get(i).getQuestion().toString();
											Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
										}
									}
								}
								Setting setting = userService.getSetting(UserID);
								int numOfRecord = setting.getRecordRepied();
								int numOfPagin = setting.getPaginDisplayReplied();
								
								model.addAttribute("numOfRecord", ""+numOfRecord);
								model.addAttribute("numOfPagin", ""+numOfPagin);
								model.addAttribute("curentOfPage",page);
								model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(3, UserID));
								model.addAttribute("noOfDisplay", setting.getPaginDisplayReplied());
								model.addAttribute("replylust", Deletequestionlist);
							}
						}else{
							if(author ==1){
								Users otheruser = userService.getUserByUserID(question.getDeleteBy());
								int otherauthor = otheruser.getAuthorization();
								if(otherauthor ==1){
									// null
									
									model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" xóa");
								}else{
									// Processing restore question
									int result = QuestionmanagementService.deleterepliedquestion(Id);
									if(result>0){
										QuestionmanagementService.UpdateDelete(Id, login);
										Users users = userService.getUserByUserID(UserID);
										Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
										
										logger.info("Tài khoản "+users.getUserName()+" đã xóa câu hỏi của người hỏi "+ userquestion.getQuestionBy());
										model.addAttribute("message",msgSrc.getMessage("message.delete.success", null,locale));
										List<Questionmanagement> Deletequestionlist;
										if(session.getValue("Admin")==null){	
											Deletequestionlist = QuestionmanagementService.repliedList(page-1, UserID);
											for(int i=0;i < Deletequestionlist.size();i++){
												if(Deletequestionlist.get(i).getQuestion().length() >= check){
													String abc = Deletequestionlist.get(i).getQuestion().toString();
													Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
												}
											}
										}else{
											Deletequestionlist = QuestionmanagementService.getRepliedListForAdmin(page-1, UserID);
											for(int i=0;i < Deletequestionlist.size();i++){
												if(Deletequestionlist.get(i).getQuestion().length() >= check){
													String abc = Deletequestionlist.get(i).getQuestion().toString();
													Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
												}
											}
										}
						
										model.addAttribute("replylust", Deletequestionlist);
									}
								}
							}else{
								Users otheruser = userService.getUserByUserID(question.getDeleteBy());
								model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" xóa");
							}
						}
						
					}else{
						// Processing restore question
						int result = QuestionmanagementService.deleterepliedquestion(Id);
						if(result>0){
							QuestionmanagementService.UpdateDelete(Id, login);
							Users users = userService.getUserByUserID(UserID);
							Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
							
							logger.info("Tài khoản "+users.getUserName()+" đã xóa câu hỏi của người hỏi "+ userquestion.getQuestionBy());
							model.addAttribute("message","Xóa thành công");
							List<Questionmanagement> Deletequestionlist;
							if(session.getValue("Admin")==null){	
								Deletequestionlist = QuestionmanagementService.repliedList(page-1, UserID);
								for(int i=0;i < Deletequestionlist.size();i++){
									if(Deletequestionlist.get(i).getQuestion().length() >= check){
										String abc = Deletequestionlist.get(i).getQuestion().toString();
										Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}else{
								Deletequestionlist = QuestionmanagementService.getRepliedListForAdmin(page-1, UserID);
								for(int i=0;i < Deletequestionlist.size();i++){
									if(Deletequestionlist.get(i).getQuestion().length() >= check){
										String abc = Deletequestionlist.get(i).getQuestion().toString();
										Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}
			
							model.addAttribute("replylust", Deletequestionlist);
						}
					}
					//
					
				}
				List<Questionmanagement> Deletequestionlist;
				if(session.getValue("Admin")==null){	
					Deletequestionlist = QuestionmanagementService.repliedList(page-1, UserID);
					for(int i=0;i < Deletequestionlist.size();i++){
						if(Deletequestionlist.get(i).getQuestion().length() >= check){
							String abc = Deletequestionlist.get(i).getQuestion().toString();
							Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}else{
					Deletequestionlist = QuestionmanagementService.getRepliedListForAdmin(page-1, UserID);
					for(int i=0;i < Deletequestionlist.size();i++){
						if(Deletequestionlist.get(i).getQuestion().length() >= check){
							String abc = Deletequestionlist.get(i).getQuestion().toString();
							Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}
				Setting setting = userService.getSetting(UserID);
				int numOfRecord = setting.getRecordRepied();
				int numOfPagin = setting.getPaginDisplayReplied();
				
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				model.addAttribute("curentOfPage",page);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(3, UserID));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayReplied());
				model.addAttribute("replylust", Deletequestionlist);				
				}else{
					if(actionsubmit.equals("dictionary")){
						int login =Integer.parseInt(session.getAttribute("login").toString());
						int Id =Integer.parseInt(session.getAttribute("Id").toString());
						
						//
						Questionmanagement question = QuestionmanagementService.getQuestionmanagementbyID(Id);
						if(question.getUpdateBy() != null){
							// Xu ly thao tac song song
							Users information = userService.getUserByUserID(UserID);
							int author = information.getAuthorization();
							if(UserID == question.getUpdateBy()){
								//
								model.addAttribute("mess",Id);
								Dictionary newdic = new Dictionary();
								Questionmanagement question1 = QuestionmanagementService.repliedquestion(Id);
							
									newdic.setAnwser(question1.getAnswer());
									newdic.setQuestion(question1.getQuestion());
									newdic.setCreateBy(login);
									Date now = new Date();
									newdic.setCreateDate(now);
									newdic.setAnwserBy(question1.getAnswerBy());
									newdic.setStatus(1);
									newdic.setDeleteStatus(0);
									newdic.setBusyStatus(0);
									DictionaryService.AddDictionary(newdic);
									QuestionmanagementService.TransferToDictionary(Id, login);
								
								List<Questionmanagement> Deletequestionlist;
								if(session.getValue("Admin")==null){	
									Deletequestionlist = QuestionmanagementService.repliedList(page-1, UserID);
									for(int i=0;i < Deletequestionlist.size();i++){
										if(Deletequestionlist.get(i).getQuestion().length() >= check){
											String abc = Deletequestionlist.get(i).getQuestion().toString();
											Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
										}
									}
								}else{
									Deletequestionlist = QuestionmanagementService.getRepliedListForAdmin(page-1, UserID);
									for(int i=0;i < Deletequestionlist.size();i++){
										if(Deletequestionlist.get(i).getQuestion().length() >= check){
											String abc = Deletequestionlist.get(i).getQuestion().toString();
											Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
										}
									}
								}
								Users users = userService.getUserByUserID(UserID);
								Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
								
								logger.info("Tài khoản "+users.getUserName()+" đã đưa câu hỏi của người hỏi "+ userquestion.getQuestionBy() +" vào bộ từ điển");
								model.addAttribute("message", msgSrc.getMessage("message.inserttodictionary.success", null,locale));
								model.addAttribute("replylust", Deletequestionlist);
							}else{
								if(author ==1){
									Users otheruser = userService.getUserByUserID(question.getUpdateBy());
									int otherauthor = otheruser.getAuthorization();
									if(otherauthor ==1){
										// null
										
										model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" đưa vào bộ từ điển");
									}else{
										//
										model.addAttribute("mess",Id);
										Dictionary newdic = new Dictionary();
										Questionmanagement question1 = QuestionmanagementService.repliedquestion(Id);
									
											newdic.setAnwser(question1.getAnswer());
											newdic.setQuestion(question1.getQuestion());
											newdic.setCreateBy(login);
											Date now = new Date();
											newdic.setCreateDate(now);
											newdic.setAnwserBy(question1.getAnswerBy());
											newdic.setStatus(1);
											newdic.setDeleteStatus(0);
											newdic.setBusyStatus(0);
											DictionaryService.AddDictionary(newdic);
											QuestionmanagementService.TransferToDictionary(Id, login);
										
										List<Questionmanagement> Deletequestionlist;
										if(session.getValue("Admin")==null){	
											Deletequestionlist = QuestionmanagementService.repliedList(page-1, UserID);
											for(int i=0;i < Deletequestionlist.size();i++){
												if(Deletequestionlist.get(i).getQuestion().length() >= check){
													String abc = Deletequestionlist.get(i).getQuestion().toString();
													Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
												}
											}
										}else{
											Deletequestionlist = QuestionmanagementService.getRepliedListForAdmin(page-1, UserID);
											for(int i=0;i < Deletequestionlist.size();i++){
												if(Deletequestionlist.get(i).getQuestion().length() >= check){
													String abc = Deletequestionlist.get(i).getQuestion().toString();
													Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
												}
											}
										}
										Users users = userService.getUserByUserID(UserID);
										Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
										
										logger.info("Tài khoản "+users.getUserName()+" đã đưa câu hỏi của người hỏi "+ userquestion.getQuestionBy() +" vào bộ từ điển");
										model.addAttribute("message",msgSrc.getMessage("message.inserttodictionary.success", null,locale));
										model.addAttribute("replylust", Deletequestionlist);
									}
								}else{
									// null
									Users otheruser = userService.getUserByUserID(question.getUpdateBy());
									model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" đưa vào bộ từ điển");
								}
							}
							
							// ket thuc xu ly thao tac song song
						}else{
							//
							model.addAttribute("mess",Id);
							Dictionary newdic = new Dictionary();
							Questionmanagement question1 = QuestionmanagementService.repliedquestion(Id);
						
								newdic.setAnwser(question1.getAnswer());
								newdic.setQuestion(question1.getQuestion());
								newdic.setCreateBy(login);
								Date now = new Date();
								newdic.setCreateDate(now);
								newdic.setAnwserBy(question1.getAnswerBy());
								newdic.setStatus(1);
								newdic.setDeleteStatus(0);
								newdic.setBusyStatus(0);
								DictionaryService.AddDictionary(newdic);
								QuestionmanagementService.TransferToDictionary(Id, login);
							
							List<Questionmanagement> Deletequestionlist;
							if(session.getValue("Admin")==null){	
								Deletequestionlist = QuestionmanagementService.repliedList(page-1, UserID);
								for(int i=0;i < Deletequestionlist.size();i++){
									if(Deletequestionlist.get(i).getQuestion().length() >= check){
										String abc = Deletequestionlist.get(i).getQuestion().toString();
										Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}else{
								Deletequestionlist = QuestionmanagementService.getRepliedListForAdmin(page-1, UserID);
								for(int i=0;i < Deletequestionlist.size();i++){
									if(Deletequestionlist.get(i).getQuestion().length() >= check){
										String abc = Deletequestionlist.get(i).getQuestion().toString();
										Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}
							Users users = userService.getUserByUserID(UserID);
							Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
							
							logger.info("Tài khoản "+users.getUserName()+" đã đưa câu hỏi của người hỏi "+ userquestion.getQuestionBy() +" vào bộ từ điển");
							model.addAttribute("message", msgSrc.getMessage("message.inserttodictionary.success", null,locale));
							model.addAttribute("replylust", Deletequestionlist);
						}
						
						List<Questionmanagement> Deletequestionlist;
						if(session.getValue("Admin")==null){	
							Deletequestionlist = QuestionmanagementService.repliedList(page-1, UserID);
							for(int i=0;i < Deletequestionlist.size();i++){
								if(Deletequestionlist.get(i).getQuestion().length() >= check){
									String abc = Deletequestionlist.get(i).getQuestion().toString();
									Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
						}else{
							Deletequestionlist = QuestionmanagementService.getRepliedListForAdmin(page-1, UserID);
							for(int i=0;i < Deletequestionlist.size();i++){
								if(Deletequestionlist.get(i).getQuestion().length() >= check){
									String abc = Deletequestionlist.get(i).getQuestion().toString();
									Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
						}
						Setting setting = userService.getSetting(UserID);
						int numOfRecord = setting.getRecordRepied();
						int numOfPagin = setting.getPaginDisplayReplied();
						
						model.addAttribute("numOfRecord", ""+numOfRecord);
						model.addAttribute("numOfPagin", ""+numOfPagin);
						model.addAttribute("curentOfPage",page);
						model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(3, UserID));
						model.addAttribute("noOfDisplay", setting.getPaginDisplayReplied());
						model.addAttribute("replylust", Deletequestionlist);	
					}else{
						if(actionsubmit.equals("deleteall")){
							int login = Integer.parseInt(session.getAttribute("login").toString());
							List<Questionmanagement> returenlist = QuestionmanagementService.deleteall(checkboxdata,login);
							model.addAttribute("message", msgSrc.getMessage("message.deletemulti.success", null,locale));
							List<Questionmanagement> Deletequestionlist;
							if(session.getValue("Admin")==null){	
								Deletequestionlist = QuestionmanagementService.repliedList(page-1, UserID);
								for(int i=0;i < Deletequestionlist.size();i++){
									if(Deletequestionlist.get(i).getQuestion().length() >= check){
										String abc = Deletequestionlist.get(i).getQuestion().toString();
										Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}else{
								Deletequestionlist = QuestionmanagementService.getRepliedListForAdmin(page-1, UserID);
								for(int i=0;i < Deletequestionlist.size();i++){
									if(Deletequestionlist.get(i).getQuestion().length() >= check){
										String abc = Deletequestionlist.get(i).getQuestion().toString();
										Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}
							Setting setting = userService.getSetting(UserID);
							int numOfRecord = setting.getRecordRepied();
							int numOfPagin = setting.getPaginDisplayReplied();
							
							model.addAttribute("numOfRecord", ""+numOfRecord);
							model.addAttribute("numOfPagin", ""+numOfPagin);
							model.addAttribute("curentOfPage",page);
							model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(3, UserID));
							model.addAttribute("noOfDisplay", setting.getPaginDisplayReplied());
							if(returenlist !=null){
								for(int i =0; i< returenlist.size();i++){
									Users users = userService.getUserByUserID(UserID);
									
									String question = returenlist.get(i).getQuestion();
									if(question.length() > 50){
										question.substring(0, 45);
										question = question + "...";
									}
									logger.info("Tài khoản "+users.getUserName()+" xóa câu hỏi " + question);
								}
							}
							model.addAttribute("replylust", Deletequestionlist);
						}else{
							if(actionsubmit.equals("change")){
								if(!changeitems.equals("0")){
									int numOfRecord = Integer.parseInt(changeitems);
									int numOfPagin = Integer.parseInt(changepagin);
									userService.updateSettingReplied(UserID, numOfRecord, numOfPagin);
							
									model.addAttribute("numOfRecord",changeitems);
									model.addAttribute("numOfPagin",changepagin);
									
									Setting setting = userService.getSetting(UserID);
									model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(3, UserID));
									model.addAttribute("noOfDisplay", setting.getPaginDisplayReplied());
									
									List<Questionmanagement> Deletequestionlist;
									if(session.getValue("Admin")==null){	
										Deletequestionlist = QuestionmanagementService.repliedList(page-1, UserID);
										for(int i=0;i < Deletequestionlist.size();i++){
											if(Deletequestionlist.get(i).getQuestion().length() >= check){
												String abc = Deletequestionlist.get(i).getQuestion().toString();
												Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
											}
										}
									}else{
										Deletequestionlist = QuestionmanagementService.getRepliedListForAdmin(page-1, UserID);
										for(int i=0;i < Deletequestionlist.size();i++){
											if(Deletequestionlist.get(i).getQuestion().length() >= check){
												String abc = Deletequestionlist.get(i).getQuestion().toString();
												Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
											}
										}
									}
									;
									model.addAttribute("replylust", Deletequestionlist);
									Users users = userService.getUserByUserID(UserID);
									logger.info("Tài khoản "+users.getUserName()+" thay đổi cấu hình phân trang");
									model.addAttribute("message",msgSrc.getMessage("message.changconfig.paging.success", null,locale));
			
								}
							}else{
							//xu ly tim kiem
							List<Questionmanagement> list;
							if(session.getValue("Admin")!=null){
								list = QuestionmanagementService.searchIdex(actionsubmit,"3");
								for(int i=0;i < list.size();i++){
									if(list.get(i).getQuestion().length() >= check){
										String abc = list.get(i).getQuestion().toString();
										list.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}else{
								list = QuestionmanagementService.searchIdexForAdmin(actionsubmit,"3",UserID);
								for(int i=0;i < list.size();i++){
									if(list.get(i).getQuestion().length() >= check){
										String abc = list.get(i).getQuestion().toString();
										list.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}
							Setting setting = userService.getSetting(UserID);
							int numOfRecord = setting.getRecordRepied();
							int numOfPagin = setting.getPaginDisplayReplied();
							
							model.addAttribute("numOfRecord", ""+numOfRecord);
							model.addAttribute("numOfPagin", ""+numOfPagin);
							model.addAttribute("curentOfPage",page);
							model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(3, UserID));
							model.addAttribute("noOfDisplay", setting.getPaginDisplayReplied());
							Users users = userService.getUserByUserID(UserID);
							logger.info("Tài khoản "+users.getUserName()+" tìm kiếm "+ actionsubmit);
							model.addAttribute("replylust", list);
							model.addAttribute("actionsubmit", actionsubmit);
							}
							
						}
					}
				}

			return "list-replied";
	}
	
	

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/dsdaxoa", method = RequestMethod.GET)
	public String dsdaxoa(
			@RequestParam(value = "topic", required = false, defaultValue= "0")int Id, 
			@RequestParam(value = "page", required = false, defaultValue= "1")int page,
			Model model, HttpSession session, RedirectAttributes attributes, Locale locale) {
		
		if(checkLogin(session, locale)==false){			
			attributes.addFlashAttribute("users", new Users());
			attributes.addFlashAttribute("error", "Bạn chưa đăng nhập!.");
			session.setAttribute("redirectto", "dsdaxoa");
			return "redirect:/";
		}else{
			int UserID = Integer.parseInt(session.getAttribute("login").toString());
			checkBusyStatus(Id, UserID, session);
			if(Id==0){
				session.setAttribute("Id", "0");
				session.setAttribute("Page",page );
				List<Questionmanagement> Deletequestionlist;
				if(session.getValue("Admin")==null){
					Deletequestionlist= QuestionmanagementService.deleteList(page-1, UserID);
					for(int i=0;i < Deletequestionlist.size();i++){
						if(Deletequestionlist.get(i).getQuestion().length() >= check){
							String abc = Deletequestionlist.get(i).getQuestion().toString();
							Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}else{
					Deletequestionlist= QuestionmanagementService.getDeleteListForAdmin(page-1, UserID);
					for(int i=0;i < Deletequestionlist.size();i++){
						if(Deletequestionlist.get(i).getQuestion().length() >= check){
							String abc = Deletequestionlist.get(i).getQuestion().toString();
							Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}
				
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
				Users users = userService.getUserByUserID(UserID);				
				logger.info("Tài khoản "+users.getUserName()+" vào danh sách câu hỏi đã xóa");
				return "list-deleted";
				
			}else{
				if(QuestionmanagementService.checkIdQuestionDeleted(Id)==true){
				if(QuestionmanagementService.checkDeleteListByUserId(UserID , Id)==true){
				
				
				session.setAttribute("Id", Id);
				session.setAttribute("Page",page );
				Questionmanagement delete = QuestionmanagementService.deletequestion(Id);
				List<Questionmanagement> Deletequestionlist;
				if(session.getValue("Admin")==null){
					Deletequestionlist= QuestionmanagementService.deleteList(page-1, UserID);
					for(int i=0;i < Deletequestionlist.size();i++){
						if(Deletequestionlist.get(i).getQuestion().length() >= check){
							String abc = Deletequestionlist.get(i).getQuestion().toString();
							Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}else{
					Deletequestionlist= QuestionmanagementService.getDeleteListForAdmin(page-1, UserID);
					for(int i=0;i < Deletequestionlist.size();i++){
						if(Deletequestionlist.get(i).getQuestion().length() >= check){
							String abc = Deletequestionlist.get(i).getQuestion().toString();
							Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}
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
				}else {
					return "redirect:/notalow";
				}
				}else{
					return "redirect:/notalow";
				}
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
			HttpSession session, Locale locale) {
		int Id = Integer.parseInt(session.getAttribute("Id").toString());// get ID
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
				
			//	model.addAttribute("deletequestion",Id);
				if(session.getAttribute("Id") !="0"){
					int login = Integer.parseInt(session.getAttribute("login").toString());
					//xu ly luu cau tra loi va gui mail
					Questionmanagement question = QuestionmanagementService.getQuestionmanagementbyID(Id);
					if(question.getDeleteBy() != null){
						// Xu ly thao tac song song
						Users information = userService.getUserByUserID(UserID);
						int author = information.getAuthorization();
						if(UserID == question.getDeleteBy()){
							// Processing restore question
							int result = QuestionmanagementService.restore(Id);
							if(result>0){
							
								QuestionmanagementService.UpdateDelete(Id, login);
								Users users = userService.getUserByUserID(UserID);
								Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
								
								logger.info("Tài khoản "+users.getUserName()+" đã khôi phục câu hỏi của người hỏi "+ userquestion.getQuestionBy());
								QuestionmanagementService.updatedelete(Id);
								if(userquestion.getStatus() == 1){
									
									model.addAttribute("message",msgSrc.getMessage("message.restoretonotreplylist.success", null,locale));
								}else{
									if(userquestion.getStatus() == 2){
										model.addAttribute("message",msgSrc.getMessage("message.restoretosavelist.success", null,locale));
									}else{
										model.addAttribute("message",msgSrc.getMessage("message.restoretoreplylist.success", null,locale));
									}
								}
								
								List<Questionmanagement> Deletequestionlist;
								if(session.getValue("Admin")==null){
									Deletequestionlist= QuestionmanagementService.deleteList(page-1, UserID);
									for(int i=0;i < Deletequestionlist.size();i++){
										if(Deletequestionlist.get(i).getQuestion().length() >= check){
											String abc = Deletequestionlist.get(i).getQuestion().toString();
											Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
										}
									}
								}else{
									Deletequestionlist= QuestionmanagementService.getDeleteListForAdmin(page-1, UserID);
									for(int i=0;i < Deletequestionlist.size();i++){
										if(Deletequestionlist.get(i).getQuestion().length() >= check){
											String abc = Deletequestionlist.get(i).getQuestion().toString();
											Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
										}
									}
								}
								Setting setting = userService.getSetting(UserID);
								
								int numOfRecord = setting.getRecordDelete();
								int numOfPagin = setting.getPaginDisplayDelete();
								
								model.addAttribute("numOfRecord", ""+numOfRecord);
								model.addAttribute("numOfPagin", ""+numOfPagin);
								model.addAttribute("curentOfPage",page);
								model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(4, UserID));
								model.addAttribute("noOfDisplay", setting.getPaginDisplayDelete());
								model.addAttribute("deletequestionlist", Deletequestionlist);
							}
						}else{
							if(author ==1){
								Users otheruser = userService.getUserByUserID(question.getDeleteBy());
								int otherauthor = otheruser.getAuthorization();
								if(otherauthor ==1){
									// null
									
									model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" khôi phục");
								}else{
									// Processing restore question
									int result = QuestionmanagementService.restore(Id);
									if(result>0){
										
										QuestionmanagementService.UpdateDelete(Id, login);
										Users users = userService.getUserByUserID(UserID);
										Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
										
										logger.info("Tài khoản "+users.getUserName()+" đã khôi phục câu hỏi của người hỏi "+ userquestion.getQuestionBy());
										model.addAttribute("message",msgSrc.getMessage("message.restore.success", null,locale));
										List<Questionmanagement> Deletequestionlist;
										if(session.getValue("Admin")==null){
											Deletequestionlist= QuestionmanagementService.deleteList(page-1, UserID);
											for(int i=0;i < Deletequestionlist.size();i++){
												if(Deletequestionlist.get(i).getQuestion().length() >= check){
													String abc = Deletequestionlist.get(i).getQuestion().toString();
													Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
												}
											}
										}else{
											Deletequestionlist= QuestionmanagementService.getDeleteListForAdmin(page-1, UserID);
											for(int i=0;i < Deletequestionlist.size();i++){
												if(Deletequestionlist.get(i).getQuestion().length() >= check){
													String abc = Deletequestionlist.get(i).getQuestion().toString();
													Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
												}
											}
										}
										model.addAttribute("deletequestionlist", Deletequestionlist);
									}
								}
							}else{
								Users otheruser = userService.getUserByUserID(question.getDeleteBy());
								model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" khôi phục");
							}
						}
						
					}else{
						// Processing restore question
						int result = QuestionmanagementService.restore(Id);
						if(result>0){
							
							QuestionmanagementService.UpdateDelete(Id, login);
							Users users = userService.getUserByUserID(UserID);
							Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
							
							logger.info("Tài khoản "+users.getUserName()+" đã khôi phục câu hỏi của người hỏi "+ userquestion.getQuestionBy());
							model.addAttribute("message",msgSrc.getMessage("message.restore.success", null,locale));
							List<Questionmanagement> Deletequestionlist;
							if(session.getValue("Admin")==null){
								Deletequestionlist= QuestionmanagementService.deleteList(page-1, UserID);
								for(int i=0;i < Deletequestionlist.size();i++){
									if(Deletequestionlist.get(i).getQuestion().length() >= check){
										String abc = Deletequestionlist.get(i).getQuestion().toString();
										Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}else{
								Deletequestionlist= QuestionmanagementService.getDeleteListForAdmin(page-1, UserID);
								for(int i=0;i < Deletequestionlist.size();i++){
									if(Deletequestionlist.get(i).getQuestion().length() >= check){
										String abc = Deletequestionlist.get(i).getQuestion().toString();
										Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
									}
								}
							}
							model.addAttribute("deletequestionlist", Deletequestionlist);
						}
					}
					
				}					
				List<Questionmanagement> Deletequestionlist;
				if(session.getValue("Admin")==null){
					Deletequestionlist= QuestionmanagementService.deleteList(page-1, UserID);
					for(int i=0;i < Deletequestionlist.size();i++){
						if(Deletequestionlist.get(i).getQuestion().length() >= check){
							String abc = Deletequestionlist.get(i).getQuestion().toString();
							Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}else{
					Deletequestionlist= QuestionmanagementService.getDeleteListForAdmin(page-1, UserID);
					for(int i=0;i < Deletequestionlist.size();i++){
						if(Deletequestionlist.get(i).getQuestion().length() >= check){
							String abc = Deletequestionlist.get(i).getQuestion().toString();
							Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
						}
					}
				}
				Setting setting = userService.getSetting(UserID);
				
				int numOfRecord = setting.getRecordDelete();
				int numOfPagin = setting.getPaginDisplayDelete();
				
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				model.addAttribute("curentOfPage",page);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(4, UserID));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayDelete());
				model.addAttribute("deletequestionlist", Deletequestionlist);
			}else{
				if(actionsubmit.equals("change")){
					if(!changeitems.equals("0")){
						int numOfRecord = Integer.parseInt(changeitems);
						int numOfPagin = Integer.parseInt(changepagin);
						userService.updateSettingDelete(UserID, numOfRecord, numOfPagin);
				
						model.addAttribute("numOfRecord",changeitems);
						model.addAttribute("numOfPagin",changepagin);
						
						Setting setting = userService.getSetting(UserID);
						model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(4, UserID));
						model.addAttribute("noOfDisplay", setting.getPaginDisplayDelete());
						
						List<Questionmanagement> Deletequestionlist;
						if(session.getValue("Admin")==null){
							Deletequestionlist= QuestionmanagementService.deleteList(page-1, UserID);
							for(int i=0;i < Deletequestionlist.size();i++){
								if(Deletequestionlist.get(i).getQuestion().length() >= check){
									String abc = Deletequestionlist.get(i).getQuestion().toString();
									Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
						}else{
							Deletequestionlist= QuestionmanagementService.getDeleteListForAdmin(page-1, UserID);
							for(int i=0;i < Deletequestionlist.size();i++){
								if(Deletequestionlist.get(i).getQuestion().length() >= check){
									String abc = Deletequestionlist.get(i).getQuestion().toString();
									Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
						}
						
						model.addAttribute("deletequestionlist", Deletequestionlist);
						Users users = userService.getUserByUserID(UserID);
						logger.info("Tài khoản "+users.getUserName()+" thay đổi cấu hình phân trang");
						model.addAttribute("message",msgSrc.getMessage("message.changconfig.paging.success", null,locale));

					}
				}else{
					if(actionsubmit.equals("restoreall")){
						//xử lý khôi phục tất cả
						List<Questionmanagement> returnlist = QuestionmanagementService.restoreall(checkboxdata, UserID);
						List<Questionmanagement> Deletequestionlist;
						if(session.getValue("Admin")==null){
							Deletequestionlist= QuestionmanagementService.deleteList(page-1, UserID);
							for(int i=0;i < Deletequestionlist.size();i++){
								if(Deletequestionlist.get(i).getQuestion().length() >= check){
									String abc = Deletequestionlist.get(i).getQuestion().toString();
									Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
						}else{
							Deletequestionlist= QuestionmanagementService.getDeleteListForAdmin(page-1, UserID);
							for(int i=0;i < Deletequestionlist.size();i++){
								if(Deletequestionlist.get(i).getQuestion().length() >= check){
									String abc = Deletequestionlist.get(i).getQuestion().toString();
									Deletequestionlist.get(i).setQuestion(abc.substring(0, get)+ ".....");
								}
							}
						}
						Setting setting = userService.getSetting(UserID);
						
						int numOfRecord = setting.getRecordDelete();
						int numOfPagin = setting.getPaginDisplayDelete();
						
						model.addAttribute("numOfRecord", ""+numOfRecord);
						model.addAttribute("numOfPagin", ""+numOfPagin);
						model.addAttribute("curentOfPage",page);
						model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(4, UserID));
						model.addAttribute("noOfDisplay", setting.getPaginDisplayDelete());
						if(returnlist !=null){
							for(int i =0; i< returnlist.size();i++){
								Users users = userService.getUserByUserID(UserID);
								
								String question = returnlist.get(i).getQuestion();
								if(question.length() > 50){
									question.substring(0, 45);
									question = question + "...";
								}
								logger.info("Tài khoản "+users.getUserName()+" khôi phục câu hỏi " + question);
							}
						}
						model.addAttribute("deletequestionlist", Deletequestionlist);
						model.addAttribute("message","Khôi phục tất cả.");
					}else{
					
					//xu ly tim kiem
					List<Questionmanagement> list;
					if(session.getValue("Admin")!=null){
						list= QuestionmanagementService.searchIdex(actionsubmit,"4");
						for(int i=0;i < list.size();i++){
							if(list.get(i).getQuestion().length() >= check){
								String abc = list.get(i).getQuestion().toString();
								list.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
					}else{
						list= QuestionmanagementService.searchIdexDeleteListForAdmin(actionsubmit,"4",UserID);
						for(int i=0;i < list.size();i++){
							if(list.get(i).getQuestion().length() >= check){
								String abc = list.get(i).getQuestion().toString();
								list.get(i).setQuestion(abc.substring(0, get)+ ".....");
							}
						}
						
					}
					Users users = userService.getUserByUserID(UserID);
					logger.info("Tài khoản "+users.getUserName()+" tìm kiếm "+actionsubmit);
					model.addAttribute("deletequestionlist", list);
					model.addAttribute("actionsubmit", actionsubmit);
					}
					}
			}
		return "list-deleted";
	}
	
	public boolean checkLogin(HttpSession session, Locale locale){
		if(session.getValue("login") == null){
			return false;
		}else{
			return true;
		}
	}
}
