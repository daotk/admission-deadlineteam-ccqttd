package deadlineteam.admission.quantritudien.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.soap.SAAJResult;

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
import org.springframework.web.servlet.ModelAndView;

import deadlineteam.admission.quantritudien.util.AeSimpleMD5;











import deadlineteam.admission.quantritudien.bean.UsersBean;
import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;
import deadlineteam.admission.quantritudien.service.Dictionary.Dictionary_SERVICE;
import deadlineteam.admission.quantritudien.service.QuestionManagement.Questionmanagement_SERVICE;
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
	private Questionmanagement_SERVICE QuestionmanagementService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	// Implement when call login page
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String login(HttpSession session, Model model,@ModelAttribute("error") String error) {
	
		//check user login 
		
		QuestionmanagementService.createIndex();
		if(session.getValue("login") == null){
			model.addAttribute("users", new Users());	
			return "login";
		}else{
			int UserID = Integer.parseInt(session.getAttribute("login").toString());
			List<Questionmanagement> ListQuestion=  QuestionmanagementService.getQuestionmanagementbyPage(0,UserID);
			model.addAttribute("listquestionmanagement",ListQuestion);
			model.addAttribute("questionmanagements", new Questionmanagement());
			return "redirect:/home";
		}
	}
	
	//Implement when submit login page
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ModelAndView loginsubmit(@ModelAttribute("users") Users user, Model model, HttpSession session,BindingResult result) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		LoginValidator validator = new LoginValidator();
		validator.validate(user, result);	     
        if (result.hasErrors()){
        	 return new ModelAndView("login", "users", user);
        }else {
        	String passmd5 = AeSimpleMD5.MD5(user.getPassword());
        	String checklogin = userService.checkLogin(user.getUserName(), passmd5);
			if(checklogin.equals("Right")){
				//session
				
				session.setAttribute("Page",1);
				session.setAttribute("UserId",userService.getIdbyUsername(user.getUserName()));
				
				//xử lý
				session.setAttribute("login",userService.getIdbyUsername(user.getUserName()));
				int UserID = Integer.parseInt(session.getAttribute("login").toString());
				List<Questionmanagement> ListQuestion=  QuestionmanagementService.getQuestionmanagementbyPage(0,UserID);
				
				
				//model trả về
				Setting setting = userService.getSetting(UserID);
				
				int numOfRecord = setting.getRecordNotRep();
				int numOfPagin = setting.getPaginDisplayNotRep();
				
				model.addAttribute("numOfRecord", ""+numOfRecord);
				model.addAttribute("numOfPagin", ""+numOfPagin);
				model.addAttribute("questionmanagements", new Questionmanagement());
				model.addAttribute("message", "Không mục nào được chọn để xem.");
				//paging
				model.addAttribute("curentOfPage",1);
				model.addAttribute("noOfPages", QuestionmanagementService.totalPageQuestiomanagement(1, UserID));
				model.addAttribute("noOfDisplay", setting.getPaginDisplayNotRep());

				model.addAttribute("fullname", userService.getFullnameByID(userService.getIdbyUsername(user.getUserName())));
				//check is admin
				if(userService.checkIsAdmin(userService.getIdbyUsername(user.getUserName()))==true){
					model.addAttribute("isAdmin","admin");
				}				
				return new ModelAndView("home", "listquestionmanagement",ListQuestion );
			}else{	
				if(checklogin.equals("WrongPass")){
					model.addAttribute("error", "Bạn đã nhập sai mật khẩu");	
				}else{
					model.addAttribute("error", "Bạn đã nhập sai tài khoản hoặc mật khẩu");	
				}
				return new ModelAndView("login", "users", user);
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
			user.setAuthorization(1);
			if(userService.checkUsername(user.getUserName()) ==true){
				model.addAttribute("error", "Tài khoản đã tồn tại. Xin nhập tài khoản khác.");	
			}else{
				userService.addUser(user);
				List<Users> temp = userService.getAllUsers();
				int size = temp.size() -1;
				model.addAttribute("message","Bạn đã đăng ký thành công");
				
				
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
				
			}
			return "registration";
        }
	}
	//Xử lý khi nhấp logout
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
	    return "redirect:/";
	}
		
	//Xử lý khi nhấp chang pass
	@RequestMapping(value="/changepass", method=RequestMethod.GET)
	public ModelAndView changpass(HttpSession session, Model model) {
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		model.addAttribute("fullname", userService.getFullnameByID(UserID));
		//check is admin
		if(userService.checkIsAdmin(UserID)==true){
			model.addAttribute("isAdmin","admin");
		}	
		return new ModelAndView("change-pass", "users", new UsersBean());
	}
	
	//Xử lý khi nhấp chang pass
	@RequestMapping(value="/changepass", method=RequestMethod.POST)
	public ModelAndView changpasspost(@ModelAttribute("users") UsersBean user, Model model,BindingResult result ,HttpSession session) {
		ChangePassValidator validator = new ChangePassValidator();
		validator.validate(user, result);	     
        if (result.hasErrors()){
        	 return new ModelAndView("change-pass", "users", user);
        }else{
        	//còn xét mật khẩu củ
        	int UserID = Integer.parseInt(session.getAttribute("login").toString());
        	int message = userService.changePassword(UserID, user.getNewPassword());
        	if(message>0){
        		model.addAttribute("message", "Bạn đã thay đổi mật khẩu thành công");
        	}
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
	public ModelAndView profile(HttpSession session, Model model) {
		int UserID = Integer.parseInt(session.getAttribute("login").toString());
		model.addAttribute("fullname", userService.getFullnameByID(UserID));
		//check is admin
		if(userService.checkIsAdmin(UserID)==true){
			model.addAttribute("isAdmin","admin");
		}	
		return new ModelAndView("view-profile", "users", userService.getUser(UserID));
	}
}
