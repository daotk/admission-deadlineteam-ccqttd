package deadlineteam.admission.quantritudien.webservice;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Produces;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Users;
import deadlineteam.admission.quantritudien.entities.QuestionmanagementEntity;
import deadlineteam.admission.quantritudien.entities.QuestionmanagementListEntity;
import deadlineteam.admission.quantritudien.service.QuestionManagement.Questionmanagement_SERVICE;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;
import deadlineteam.admission.quantritudien.util.AeSimpleMD5;


@Controller
@RequestMapping("android")
public class QuestionSaveListWS {
	
	@Autowired
	private Users_SERVICE userService;
	
	@Autowired
	private UserWS uWS;
	
	@Autowired
	private Questionmanagement_SERVICE quesSer;
	private QuestionmanagementListEntity quesList = new QuestionmanagementListEntity();
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	// This function return the list not reply to android
	@RequestMapping(value="questionsave/{username},{password},{page}", method=RequestMethod.GET)
	@Produces("application/xml")
	@ResponseBody
	public QuestionmanagementListEntity getDictionList(
			@PathVariable("username")String username,
			@PathVariable("password")String password,
			@PathVariable("page")String page){
		
		quesList.setQuestionList(new ArrayList<QuestionmanagementEntity>());
		String result = uWS.checkLoginPresent(username, password);
		if(result.equals("success")){
			int npage = Integer.parseInt(page);
			int idUser = userService.getIdbyUsername(username);
			List<Questionmanagement> quesTemp;

			if(userService.checkIsAdmin(idUser)==true){
				quesTemp= quesSer.getSaveListQuestionmanagementAndroid(npage);
			}else{
				quesTemp= quesSer.getSaveListForUserAndroid(npage, idUser);
			}	
			
			for(int i = 0; i < quesTemp.size(); i++){
				
				QuestionmanagementEntity us = new QuestionmanagementEntity();
				us.ID = quesTemp.get(i).getID();;
				us.Question = quesTemp.get(i).getQuestion();
				us.QuestionBy = quesTemp.get(i).getQuestionBy();
				us.QuestionEmail = quesTemp.get(i).getQuestionEmail();
				us.Answer = quesTemp.get(i).getAnswer().replace("&nbsp;", " ");
				quesList.getQuestionList().add(us);
			}
		}
		return quesList;
	}
	
	// Search for question page not reply
	@RequestMapping(value="questionsave/search/{username},{password},{page},{keyword}", method=RequestMethod.GET)
	@Produces("application/xml")
	@ResponseBody
	public QuestionmanagementListEntity getSearchQuestionList(
			@PathVariable("username")String username,
			@PathVariable("password")String password,
			@PathVariable("keyword")String keyword,
			@PathVariable("page")String page){
		quesList.setQuestionList(new ArrayList<QuestionmanagementEntity>());
		String result = uWS.checkLoginPresent(username, password);
		if(result.equals("success")){
			int npage = Integer.parseInt(page);
			int idUser = userService.getIdbyUsername(username);
			List<Questionmanagement> quesTemp;
			if(userService.checkIsAdmin(idUser)==true){
				quesTemp= quesSer.searchIdexAndroidSaveListAndroid(npage, keyword);
			}else{
				quesTemp= quesSer.searchIdexAndroidSaveListForUserAndroid(npage,keyword,idUser);
			}	
			
			for(int i = 0; i < quesTemp.size(); i++){
				
				QuestionmanagementEntity us = new QuestionmanagementEntity();
				us.ID = quesTemp.get(i).getID();;
				us.Question = quesTemp.get(i).getQuestion();
				us.QuestionBy = quesTemp.get(i).getQuestionBy();
				us.QuestionEmail = quesTemp.get(i).getQuestionEmail();
				us.Answer = quesTemp.get(i).getAnswer().replace("&nbsp;", " ");
				quesList.getQuestionList().add(us);
			}
		}
		return quesList;
	}
	
	
	
	// This function receive data from android and send mail to user
		@RequestMapping(value="questionsave/send/{username},{password},{IdQuestion},{email},{body}", method=RequestMethod.GET)
		@Produces("application/xml")
		@ResponseBody
		public String sendEmail(
				@PathVariable("username")String username,
				@PathVariable("password")String password,
				@PathVariable("email")String email,
				@PathVariable("body")String body,
				@PathVariable("IdQuestion")String IdQuestion){
			String result = "fail";
			String login = uWS.checkLoginPresent(username, password);
			
			if(login.equals("success")){
				// get id from username
				int idUser = userService.getIdbyUsername(username);
				int idQues = Integer.parseInt(IdQuestion);
				String title = "Trả lời câu hỏi tuyển sinh";
				body = AndroidUtil.restoreTags(body);
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				
				try {

					Questionmanagement question = quesSer.getQuestionmanagementbyID(idQues);
					if(question.getAnswerBy() != null){
						// Xu ly thao tac song song
						Users information = userService.getUserByUserID(idUser);
						int author = information.getAuthorization();
						if(idUser == question.getAnswerBy()){
							
							 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
							 message.setTo(email);
							 message.setSubject(title);
							 
							message.setText(body, true);
							// sends the e-mail
							mailSender.send(mimeMessage);
							
							result = "success";
							
							int execute = quesSer.SendAnwser(idQues,body);
							if(execute>0){
								quesSer.UpdateAnwserBy(idQues, idUser);	
							}	
						}else{
							if(author ==1){
								Users otheruser = userService.getUserByUserID(question.getAnswerBy());
								int otherauthor = otheruser.getAuthorization();
								if(otherauthor ==1){
									// null
									result = "confict,"+otheruser.getID();
								}else{
									//xu ly luu cau tra loi va gui mail
									
									 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
									 message.setTo(email);
									 message.setSubject(title);
									 
									 message.setText(body, true);
									// sends the e-mail
									mailSender.send(mimeMessage);
									
									
									result = "success";
									int execute = quesSer.SendAnwser(idQues,body);
									if(execute>0){
										quesSer.UpdateAnwserBy(idQues, idUser);	
									}	
								}
							}else{
								// null
								Users otheruser = userService.getUserByUserID(question.getAnswerBy());
								result = "confict,"+otheruser.getID();
							}
						}

					}else{
						//xu ly luu cau tra loi va gui mail
						
						 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
						 message.setTo(email);
						 message.setSubject(title);
						 
						 message.setText(body, true);
						// sends the e-mail
						mailSender.send(mimeMessage);
						
						result = "success";
						int execute = quesSer.SendAnwser(idQues,body);
						if(execute>0){
							quesSer.UpdateAnwserBy(idQues, idUser);	
						}	
					}
					
					
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = "fail";
				}
			}
			return result;
		}
		
		// This function recieve save request from android application
		@RequestMapping(value="questionsave/save/{username},{password},{IdQuestion},{body}", method=RequestMethod.GET)
		@Produces("application/xml")
		@ResponseBody
		public String saveQuestion(
				@PathVariable("username")String username,
				@PathVariable("password")String password,
				@PathVariable("body")String body,
				@PathVariable("IdQuestion")String IdQuestion){
			
			String result = "fail";
			String login = uWS.checkLoginPresent(username, password);
			
			if(login.equals("success")){
				int idUser = userService.getIdbyUsername(username);
				int idQues = Integer.parseInt(IdQuestion);
				
				
				body = AndroidUtil.restoreTags(body);
				
				//xu ly luu cau tra loi va gui mail

				Questionmanagement question = quesSer.getQuestionmanagementbyID(idQues);
				if(question.getAnswerBy() != null){
					// Xu ly thao tac song song
					Users information = userService.getUserByUserID(idUser);
					int author = information.getAuthorization();
					if(idUser == question.getAnswerBy()){
						int execute = quesSer.SaveTemporaryAnswerbyId(idQues,body);
						if(execute>0){
							result = "success";
							quesSer.UpdateAnwserBy(idQues, idUser);
						}
					
					}else{
						if(author ==1){
							Users otheruser = userService.getUserByUserID(question.getAnswerBy());
							int otherauthor = otheruser.getAuthorization();
							if(otherauthor ==1){
								// null
								result = "confict,"+otheruser.getID();
							}else{
								int execute = quesSer.SaveTemporaryAnswerbyId(idQues,body);
								if(execute>0){
									result = "success";
									quesSer.UpdateAnwserBy(idQues, idUser);
								}
							}
						}else{
							Users otheruser = userService.getUserByUserID(question.getAnswerBy());
							result = "confict,"+otheruser.getID();
						}
					}
					
				}else{
					int execute = quesSer.SaveTemporaryAnswerbyId(idQues,body);
					if(execute>0){
						result = "success";
						quesSer.UpdateAnwserBy(idQues, idUser);
					}
				}

				
			}
			return result;
		}
		// This function recieve save request from android application
				@RequestMapping(value="questionsave/delete/{username},{password},{IdQuestion}", method=RequestMethod.GET)
				@Produces("application/xml")
				@ResponseBody
				public String deleteQuestion(
						@PathVariable("username")String username,
						@PathVariable("password")String password,
						@PathVariable("IdQuestion")String IdQuestion){
					
					String result = "fail";
					String login = uWS.checkLoginPresent(username, password);
					
					if(login.equals("success")){
						int idUser = userService.getIdbyUsername(username);
						int idQues = Integer.parseInt(IdQuestion);
						
					
							Questionmanagement question = quesSer.getQuestionmanagementbyID(idQues);
							if(question.getDeleteBy() != null){
								// Xu ly thao tac song song
								Users information = userService.getUserByUserID(idUser);
								int author = information.getAuthorization();
								if(idUser == question.getDeleteBy()){
									int execute = quesSer.delete(idQues);
									
									if(execute>0){
										result = "success";
										quesSer.UpdateDelete(idQues, idUser);
									}
								}else{
									if(author ==1){
										Users otheruser = userService.getUserByUserID(question.getDeleteBy());
										int otherauthor = otheruser.getAuthorization();
										if(otherauthor ==1){
											// null
											result = "confict,"+otheruser.getID();
										}else{
											int execute = quesSer.delete(idQues);
											
											if(execute>0){
												result = "success";
												quesSer.UpdateDelete(idQues, idUser);
												
											}
										}
									}else{
										Users otheruser = userService.getUserByUserID(question.getDeleteBy());
										result = "confict,"+otheruser.getID();
									}
								}
							}else{
								int execute = quesSer.delete(idQues);
								
								if(execute>0){
									result = "success";
									quesSer.UpdateDelete(idQues, idUser);
								}
							}	
						}
					
					return result;
				}
}