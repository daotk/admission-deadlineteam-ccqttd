package deadlineteam.admission.quantritudien.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.soap.SAAJResult;

import org.hibernate.internal.util.config.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import deadlineteam.admission.quantritudien.util.AeSimpleMD5;
import deadlineteam.admission.quantritudien.bean.UsersBean;
import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;
import deadlineteam.admission.quantritudien.service.Dictionary.Dictionary_SERVICE;
import deadlineteam.admission.quantritudien.service.Question.Questionmanagement_SERVICE;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;
import deadlineteam.admission.quantritudien.validator.ChangePassValidator;
import deadlineteam.admission.quantritudien.validator.LoginValidator;
import deadlineteam.admission.quantritudien.validator.RegisterValidator;
import deadlineteam.admission.quantritudien.util.*;
/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {
	@Autowired
	private Users_SERVICE userService;

	@Autowired
	private Questionmanagement_SERVICE questionmanagementService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private MessageSource msgSrc;
	 @Autowired
	  public void AccountsController(MessageSource msgSrc) {
	     this.msgSrc = msgSrc;
	  }
	 
	 @ExceptionHandler(Exception.class)
		public ModelAndView handleAllException(Exception ex) {
			ModelAndView model = new ModelAndView("error");
			return model;
	 
		}
	 

	 
	 @RequestMapping(value="/mobile", method=RequestMethod.GET)
	 public String mobileget() {
		 return "mobile";
	 }
	 
	 @RequestMapping(value="/mobile", method=RequestMethod.POST)
	 public String mobilepost(	@RequestParam String actionmobile,RedirectAttributes attributes  ) {
		 if(actionmobile.equals("ok")){
			 return "mobile";
		 }else{
			 attributes.addFlashAttribute("isredirect", "ok");
			 return "redirect:/";
		 }
		
	 }
	 
	 
	 
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String login(HttpSession session, Model model,Locale locale) {
		if(checkLogin(session, locale)==false){
			model.addAttribute("users", new Users());
			return "login";
		}else{
			int UserID = Integer.parseInt(session.getAttribute("login").toString());
			List<Questionmanagement> ListQuestion=  questionmanagementService.getQuestionNotReplyByPageForUser(0,UserID);
			model.addAttribute("listquestionmanagement",ListQuestion);
			model.addAttribute("questionmanagements", new Questionmanagement());
			return "redirect:/home";
		}
	}
	
	@RequestMapping(value="/trogiup", method=RequestMethod.GET)
	public String trogiup(HttpSession session, Model model, Locale locale,RedirectAttributes attributes ) {
		if(checkLogin(session, locale)==false){			
			attributes.addFlashAttribute("users", new Users());
			attributes.addFlashAttribute("error", "Bạn chưa đăng nhập!.");
			session.setAttribute("redirectto", "trogiup");
			return "redirect:/";
		}else{
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		checkBusyStatus(0, UserID, session);
		Users users = userService.getUserByUserID(UserID);
		logger.info("Tài khoản "+ users.getUserName() +" đã vào trang trợ giúp");
		return "help";
		}
	}
	@RequestMapping(value="/tienich", method=RequestMethod.GET)
	public String tienich(HttpSession session, Model model, Locale locale,RedirectAttributes attributes) {
		if(checkLogin(session, locale)==false){			
			attributes.addFlashAttribute("users", new Users());
			attributes.addFlashAttribute("error", "Bạn chưa đăng nhập!.");
			session.setAttribute("redirectto", "tienich");
			return "redirect:/";
		}else{
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		checkBusyStatus(0, UserID, session);
		Users users = userService.getUserByUserID(UserID);
		logger.info("Tài khoản "+ users.getUserName() +" đã vào trang tiện ích");
		return "help2";
		}
	}
	@RequestMapping(value="/nguoidung", method=RequestMethod.GET)
	public String nguoidung(HttpSession session, Model model,@ModelAttribute("error") String error,Locale locale, RedirectAttributes attributes) {
		if(checkLogin(session, locale)==false){			
			attributes.addFlashAttribute("users", new Users());
			attributes.addFlashAttribute("error", "Bạn chưa đăng nhập!.");
			session.setAttribute("redirectto", "nguoidung");
			return "redirect:/";
		}else{
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		checkBusyStatus(0, UserID, session);
		Users users = userService.getUserByUserID(UserID);
		logger.info("Tài khoản "+ users.getUserName() +" đã vào trang người dùng");
		return "help3";
		}
	}
	/*
	 * Implement when submit login page
	 */
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String loginsubmit(@ModelAttribute("users") Users user, Model model, HttpSession session,BindingResult result, RedirectAttributes attribute) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		LoginValidator validator = new LoginValidator();
		validator.validate(user, result);	     
        if (result.hasErrors()){
        	model.addAttribute("users",user);
        	return "login";
        }else {
        	if(session.getValue("redirectto") != null){
        		String redirectto = session.getValue("redirectto").toString();
        		session.setAttribute("UserId",userService.getIdbyUsername(user.getUserName()));
				session.setAttribute("UserPassword",user.getPassword());
				session.setAttribute("sessionfullname",userService.getFullnameByID(userService.getIdbyUsername(user.getUserName())));				
				//xử lý
				session.setAttribute("login",userService.getIdbyUsername(user.getUserName()));
				//check is admin
				if(userService.checkIsAdmin(userService.getIdbyUsername(user.getUserName()))==true){
					session.setAttribute("Admin", "Yes");
				}
				
				logger.info("Tài khoản "+user.getUserName()+ " đã đăng nhập vào hệ thống");
				return "redirect:/"+redirectto;
        		
        	}else{
        	
        	String passmd5 = AeSimpleMD5.MD5(user.getPassword());
        	String checklogin = userService.checkLogin(user.getUserName(), passmd5);
			if(checklogin.equals("Right")){
				//session
				session.setAttribute("Page",1);
				session.setAttribute("UserId",userService.getIdbyUsername(user.getUserName()));
				session.setAttribute("UserPassword",user.getPassword());
				session.setAttribute("sessionfullname",userService.getFullnameByID(userService.getIdbyUsername(user.getUserName())));				
				//xử lý
				session.setAttribute("login",userService.getIdbyUsername(user.getUserName()));
				int UserID = Integer.parseInt(session.getAttribute("login").toString());
				List<Questionmanagement> ListQuestion;
				if(userService.checkIsAdmin(UserID)==true){
					ListQuestion= questionmanagementService.getQuestionNotReplyByPageForAdmin(0,  UserID);
				}else{
					ListQuestion= questionmanagementService.getQuestionNotReplyByPageForUser(0,  UserID);;
				}			
				for(int i=0;i < ListQuestion.size();i++){
					if(ListQuestion.get(i).getQuestion().length() >= 47){
						String abc = ListQuestion.get(i).getQuestion().toString();
						ListQuestion.get(i).setQuestion(abc.substring(0, 44)+ ".....");
					}
				}
				//model trả về
				Setting setting = userService.getSetting(UserID);
				int numOfRecord = setting.getRecordNotRep();
				int numOfPagin = setting.getPaginDisplayNotRep();
				
				attribute.addFlashAttribute("numOfPagin", ""+numOfPagin);
				attribute.addFlashAttribute("questionmanagements", new Questionmanagement());
				//paging
				attribute.addFlashAttribute("curentOfPage",1);
				attribute.addFlashAttribute("noOfPages", questionmanagementService.totalPageQuestionAndDictionary(1, UserID));
				attribute.addFlashAttribute("noOfDisplay", setting.getPaginDisplayNotRep());	
				//check is admin
				if(userService.checkIsAdmin(userService.getIdbyUsername(user.getUserName()))==true){
					session.setAttribute("Admin", "Yes");
				}				
				logger.info("Tài khoản "+user.getUserName()+ " đã đăng nhập vào hệ thống");
				logger.info("Tài khoản "+user.getUserName()+ " vào trang danh sách chưa trả lời");
				attribute.addFlashAttribute("listquestionmanagement",ListQuestion);	
				return "redirect:/home";
			}else{	
				if(checklogin.equals("WrongPass")){
					model.addAttribute("error", "Bạn đã nhập sai mật khẩu");	
				}else{
					if(checklogin.equals("notallow")){
						model.addAttribute("error", "Tài khoản bị cấm");	
					}else{
						model.addAttribute("error", "Bạn đã nhập sai tài khoản hoặc mật khẩu");	
					}
				}
				model.addAttribute("users",user);
	        	return "login";
			}
        	}
        }
	}
	
	//implement when call register page
	@RequestMapping(value="/registration", method=RequestMethod.GET)
	public String registration(HttpSession session, Model model) {
		model.addAttribute("users", new UsersBean());
		return "registration";
	}
	//implement when register submit
	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String registrationpost(@ModelAttribute("users") UsersBean userbean,HttpSession session, Model model,BindingResult result) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		RegisterValidator validator = new RegisterValidator();
		validator.validate(userbean, result);	     
        if (result.hasErrors()){
        	model.addAttribute("users",userbean);
        	 return "registration";
        }else {
			Users user = new Users();
			user.setFullName(userbean.getFullName());
			user.setUserName(userbean.getUserName());
			String passmd5 = AeSimpleMD5.MD5(userbean.getPassword());
			user.setPassword(passmd5);
			user.setEmail(userbean.getEmail());
			user.setAuthorization(0);
			user.setStatus(0);
			if(userService.checkUsername(user.getUserName()) ==true){
				model.addAttribute("error", "Tài khoản đã tồn tại. Xin nhập tài khoản khác.");	
			}else{
				if(checkemail(user.getEmail())){
					userService.addUser(user);
					List<Users> temp = userService.getAllUsers();
					int size = temp.size() -1;
					model.addAttribute("message","Bạn đã đăng ký thành công");
					logger.info("Tài khoản "+ user.getUserName()+" đã được tạo");
					
					Setting settings = new Setting();
					
					settings.setPaginDisplayDelete(3);
					settings.setPaginDisplayDictionary(3);
					settings.setPaginDisplayNotRep(3);
					settings.setPaginDisplayReplied(3);
					settings.setPaginDisplayTemp(3);
					settings.setRecordDelete(5);
					settings.setRecordDictionary(5);
					settings.setRecordNotRep(5);
					settings.setRecordRepied(5);
					settings.setRecordTemp(5);
					settings.setUserID(Integer.parseInt(temp.get(size).getID().toString()));
					userService.addSettingUser(settings);
				}else{
					model.addAttribute("error","Email không hợp lệ");
				}
				
				
			}
			return "registration";
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
	//Xử lý khi nhấp logout
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		checkBusyStatus(0, UserID, session);
		Users users = userService.getUserByUserID(UserID);
		logger.info("Tài khoản "+ users.getUserName() + " đã thoát khỏi hệ thống");
		session.invalidate();
		
	    return "redirect:/";
	}
	//Xử lý khi nhấp login
		@RequestMapping(value="/login", method=RequestMethod.GET)
		public String login(HttpSession session) {
			session.invalidate();
		    return "redirect:/";
		}
		
	//Xử lý khi nhấp chang pass
	@RequestMapping(value="/changepass", method=RequestMethod.GET)
	public String changpass(HttpSession session, Model model,@ModelAttribute("error") String error,Locale locale,RedirectAttributes attributes) {
		if(checkLogin(session, locale)==false){			
			attributes.addFlashAttribute("users", new Users());
			attributes.addFlashAttribute("error", "Bạn chưa đăng nhập!.");
			session.setAttribute("redirectto", "changepass");
			return "redirect:/";
		}else{
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		checkBusyStatus(0, UserID, session);
		model.addAttribute("fullname", userService.getFullnameByID(UserID));
		//check is admin
		if(userService.checkIsAdmin(UserID)==true){
			model.addAttribute("isAdmin","admin");
		}
		Users users = userService.getUserByUserID(UserID);
		logger.info("Tài khoản "+users.getUserName()+" vào trang đổi mật khẩu");
		model.addAttribute("users", new UsersBean());
		return "change-pass";
		}
	}
	
	//Xử lý khi nhấp chang pass
	@RequestMapping(value="/changepass", method=RequestMethod.POST)
	public ModelAndView changpasspost(@ModelAttribute("users") UsersBean user,
			@RequestParam String actionsubmit,
			Model model,BindingResult result ,HttpSession session) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		ChangePassValidator validator = new ChangePassValidator();
		validator.validate(user, result);	 
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		Users getinfor = userService.getUserByUserID(UserID);
		getinfor.getPassword();
        if (result.hasErrors()){
        	user.setPassword("");
        	user.setConfirmPassword("");
        	user.setNewPassword("");
        	 return new ModelAndView("change-pass", "users", user);
        }else{
        	if(actionsubmit.equals("change")){
        		String passmd = AeSimpleMD5.MD5(user.getPassword());
    			user.setPassword(passmd);
        		if(!(getinfor.getPassword()).equals(passmd)){
        			user.setNewPassword("");
        			user.setPassword("");
        			user.setConfirmPassword("");
        			
        			model.addAttribute("error", "Mật khẩu hiện tại không đúng");
        		}else{
        			String passmd5 = AeSimpleMD5.MD5(user.getNewPassword());
        			user.setPassword(passmd5);
                	int message = userService.changePasswordByUserID(UserID, passmd5);
                	if(message>0){
                		model.addAttribute("message", "Bạn đã thay đổi mật khẩu thành công");
                		logger.info("Tài khoản "+ user.getUserName() + " đã đổi mật khẩu");
                		user.setPassword("");
                		user.setNewPassword("");
                		user.setConfirmPassword("");
                	}
        		}
            	
        	}
        	//còn xét mật khẩu củ
        	
        	model.addAttribute("fullname", userService.getFullnameByID(UserID));
        	//check is admin
    		if(userService.checkIsAdmin(UserID)==true){
    			model.addAttribute("isAdmin","admin");
    		}
    		
        	return new ModelAndView("change-pass", "users", user);
        }
	}
	//Xử lý khi nhấp view profile
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(HttpSession session, Model model,@ModelAttribute("error") String error,Locale locale,RedirectAttributes attributes ) {
		if(checkLogin(session, locale)==false){			
			attributes.addFlashAttribute("users", new Users());
			attributes.addFlashAttribute("error", "Bạn chưa đăng nhập!.");
			session.setAttribute("redirectto", "profile");
			return "redirect:/";
		}else{
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		checkBusyStatus(0, UserID, session);
		model.addAttribute("fullname", userService.getFullnameByID(UserID));
		//check is admin
		if(userService.checkIsAdmin(UserID)==true){
			model.addAttribute("isAdmin","admin");
		}	
		Users users = userService.getUserByUserID(UserID);
		logger.info("Tài khoản  "+ users.getUserName() +" vào trang thông tin người dùng");
		model.addAttribute("users",userService.getUserByUserID(UserID));
		return "view-profile";
		}
	}
	
		//implement when call setting page - user setting default
		@RequestMapping(value="/cauhinh", method=RequestMethod.GET)
		public String settinguser(
				@RequestParam(value = "topic", required = false, defaultValue= "0")int Id, 
				HttpSession session, Model model,Locale locale,RedirectAttributes attributes) {
			if(checkLogin(session, locale)==false){			
				attributes.addFlashAttribute("users", new Users());
				attributes.addFlashAttribute("error", "Bạn chưa đăng nhập!.");
				session.setAttribute("redirectto", "cauhinh");
				return "redirect:/";
			}else{
				int UserID = Integer.parseInt(session.getAttribute("login").toString());
				if(session.getValue("Admin")==null){
					return "redirect:/notalow";
				}else{
				if(Id==0){
					//Get List Question
					List<Users> listUser= userService.getAllUsers();
					model.addAttribute("listUser", listUser);
					
					model.addAttribute("listUser2", new Users());
				}else{
					//Get List Question
					session.setAttribute("Id", Id);
					List<Users> listUser= userService.getAllUsers();
					
					model.addAttribute("listUser", listUser);
					Users getUser = userService.getUserByUserID(Id);
					
					Users test = new Users();
					test.setFullName(getUser.getFullName());
					model.addAttribute("listUser2", test);
					
					model.addAttribute("fullName", getUser.getFullName());
					model.addAttribute("userName", getUser.getUserName());
					model.addAttribute("email", getUser.getEmail());
					model.addAttribute("authorization", getUser.getAuthorization());
					model.addAttribute("status", getUser.getStatus());
				}
				Users users = userService.getUserByUserID(UserID);
				logger.info("Tài khoản "+ users.getUserName() +" vào trang cấu hình người dùng");
			
				return "setting-user";
				}
				}
			}
		

		
		
		//implement when register submit
		@RequestMapping(value = "/cauhinh", method = RequestMethod.POST)
		public String cauhinhpost( 	
				@RequestParam String actionsubmit ,
				@RequestParam(value = "authorization", required = false, defaultValue= "-1") String authorization, 
				@RequestParam(value = "fullname", required = false, defaultValue= "-1") String fullname,
				@RequestParam(value = "status", required = false, defaultValue= "-1") String status, 
				@ModelAttribute("listUser2") Users user,
				Model model,
				HttpSession session,Locale locale) {		

				if(actionsubmit.equals("change")){
					if(authorization.equals("1") || authorization.equals("0") ){
						int num = Integer.parseInt(authorization);
						int istatus = Integer.parseInt(status);
						int Id = Integer.parseInt(session.getAttribute("Id").toString());
						//userService.UpdateSetting(UserID, numOfRecord, numOfPagin);
						int login = Integer.parseInt(session.getAttribute("login").toString());
						userService.updateSettingDictionary(Id, num,fullname,istatus);
						
						List<Users> listUser= userService.getAllUsers();
						model.addAttribute("listUser", listUser);
						Users getUser = userService.getUserByUserID(Id);
						model.addAttribute("fullName", getUser.getFullName());
						model.addAttribute("userName", getUser.getUserName());
						model.addAttribute("email", getUser.getEmail());
						model.addAttribute("authorization", getUser.getAuthorization());
						model.addAttribute("status", getUser.getStatus());
					
						
						Users test = new Users();
						test.setFullName(getUser.getFullName());
						model.addAttribute("listUser2", test);
						Users nameuser = userService.getUserByUserID(login);
						Users users = userService.getUserByUserID(Id);
						logger.info("Tài khoản " + users.getUserName() +" đã được cấu hình bởi" +nameuser.getFullName());
						model.addAttribute("message", msgSrc.getMessage("message.changconfig.user.success", null,locale));
					}else{
						
						int Id = Integer.parseInt(session.getAttribute("Id").toString());
						//userService.UpdateSetting(UserID, numOfRecord, numOfPagin);
						
						
						List<Users> listUser= userService.getAllUsers();
						model.addAttribute("listUser", listUser);
						Users getUser = userService.getUserByUserID(Id);
						model.addAttribute("fullName", getUser.getFullName());
						model.addAttribute("userName", getUser.getUserName());
						model.addAttribute("email", getUser.getEmail());
						model.addAttribute("authorization", getUser.getAuthorization());
						model.addAttribute("status", getUser.getStatus());
					
						
						Users test = new Users();
						test.setFullName(getUser.getFullName());
						model.addAttribute("listUser2", test);
						model.addAttribute("message","Bạn phải nhập 0 hoặc 1 ");
					}
				}
				
				return "setting-user";
		}
		//implement when call setting page - user setting default
		
			@Value("${db.driver}")
	        private String driver;
			@Value("${db.url}")
	        private String url;
			@Value("${db.username}")
	        private String username;
			@Value("${db.password}")
	        private String password;
		 
				@RequestMapping(value="/cauhinhhethong", method=RequestMethod.GET)		
				public String settingsystem(HttpSession session, Model model,@ModelAttribute("error") String error,Locale locale, RedirectAttributes attributes) {
					if(checkLogin(session, locale)==false){			
						attributes.addFlashAttribute("users", new Users());
						attributes.addFlashAttribute("error", "Bạn chưa đăng nhập!.");
						session.setAttribute("redirectto", "cauhinhhethong");
						return "redirect:/";
					}else{
						if(session.getValue("Admin")==null){
							return "redirect:/notalow";
						}else{
							model.addAttribute("driver", driver);
							model.addAttribute("url", url);
							model.addAttribute("username", username);
							model.addAttribute("password", password);
							int login = Integer.parseInt(session.getAttribute("login").toString());
							Users users = userService.getUserByUserID(login);
							logger.info("Tài khoản "+users.getUserName() +" vào trang cấu hình hệ thống");
							return "setting-system";
						}
					}
				}
				//implement when register submit
				@RequestMapping(value = "/cauhinhhethong", method = RequestMethod.POST)
				public String cauhinhhethongpost( 	
						@RequestParam String actionsubmit ,
						@RequestParam(value = "username", required = false) String username, 
						@RequestParam(value = "password", required = false) String password, 
						@RequestParam(value = "url", required = false) String url, 
						@RequestParam(value = "driver", required = false) String driver, 
						@ModelAttribute("listUser") Users user,
						Model model,
						HttpSession session,Locale locale) throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {		
					if(actionsubmit.equals("change")){
						if(username !="" && password !=""){
							CrunchifyUpdateConfig config = new CrunchifyUpdateConfig();
							config.ConfigSystem(driver,username,password, url);
							model.addAttribute("driver", driver);
							model.addAttribute("url", url);
							model.addAttribute("username", username);
							model.addAttribute("password", password);
							model.addAttribute("message",  msgSrc.getMessage("message.changconfig.system.success", null,locale));
							int login = Integer.parseInt(session.getAttribute("login").toString());
							Users users = userService.getUserByUserID(login);
							logger.info("Tài khoản "+ users.getUserName()+" thay đổi cấu hình hệ thống");
						}else{
							model.addAttribute("error", msgSrc.getMessage("message.dictionary.setting.fail", null,locale));
						}
						
					}
					
					return "setting-system";
				}
		
				@Value("${mail.host}")
		        private String host;
				@Value("${mail.port}")
		        private String port;
				@Value("${mail.username}")
		        private String usernamemail;
				@Value("${mail.password}")
		        private String passwordmail;
				
				@RequestMapping(value="/cauhinhmail", method=RequestMethod.GET)
				public String settingmail(
						HttpSession session, Model model,@ModelAttribute("error") String error,Locale locale, RedirectAttributes attributes) {
					if(checkLogin(session, locale)==false){			
						attributes.addFlashAttribute("users", new Users());
						attributes.addFlashAttribute("error", "Bạn chưa đăng nhập!.");
						session.setAttribute("redirectto", "cauhinhmail");
						return "redirect:/";
					}else{
						if(session.getValue("Admin")==null){
							return "redirect:/notalow";
						}else{
							model.addAttribute("host", host);
							model.addAttribute("port", port);
							model.addAttribute("usernamemail", usernamemail);
							model.addAttribute("passwordmail", passwordmail);
							int login = Integer.parseInt(session.getAttribute("login").toString());
							Users users = userService.getUserByUserID(login);
							logger.info("Tài khoản "+users.getUserName() +" vào trang cấu hình mail");
							return "setting-mail";
						}
					}
				}
				//implement when register submit
				@RequestMapping(value = "/cauhinhmail", method = RequestMethod.POST)
				public String cauhinhmailpost( 	
						@RequestParam String actionsubmit ,
						@RequestParam(value = "usernamemail", required = false, defaultValue= "0") String usernamemail, 
						@RequestParam(value = "passwordmail", required = false, defaultValue= "0") String passwordmail, 
						@RequestParam(value = "host", required = false, defaultValue= "0") String host, 
						@RequestParam(value = "port", required = false, defaultValue= "0") String port, 
						@ModelAttribute("listUser") Users user,
						Model model,
						HttpSession session,Locale locale) throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {		
					if(actionsubmit.equals("change")){
						CrunchifyUpdateConfig config = new CrunchifyUpdateConfig();
						config.ConfigMail(host, port, usernamemail, passwordmail);
						model.addAttribute("host", host);
						model.addAttribute("port", port);
						model.addAttribute("usernamemail", usernamemail);
						model.addAttribute("passwordmail", passwordmail);
						model.addAttribute("message", msgSrc.getMessage("message.changconfig.mail.success", null,locale));
					}
					int login = Integer.parseInt(session.getAttribute("login").toString());
					Users users = userService.getUserByUserID(login);
					logger.info("Tài khoản "+ users.getUserName()+" thay đổi cấu hình mail");
						
					return "setting-mail";
				}
				
				
			
				@RequestMapping(value = "/notalow", method = RequestMethod.GET)
				public String notalow( ){
					return "notalow";
				}
				
				@RequestMapping(value = "/taoindex", method = RequestMethod.GET)
				public String taoindex(Model model){
					questionmanagementService.createIndex();
					model.addAttribute("message", "Tạo index thành công. Tính năng truyền file index lên công cụ hiển thị tạm dừng");
					return "create-index";
				}
				
				
				
				//
				public void checkBusyStatus(int Id,int UserID, HttpSession session){
					if(session.getValue("BusyStatus") != null){
						questionmanagementService.resetBusyStatusQuestion(Id,UserID); 
					}
				}
				
				public boolean checkLogin(HttpSession session, Locale locale){
					if(session.getValue("login") == null){
						return false;
					}else{
						return true;
					}
				}
}
