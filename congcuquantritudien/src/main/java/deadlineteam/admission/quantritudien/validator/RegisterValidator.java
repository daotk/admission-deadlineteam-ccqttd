package deadlineteam.admission.quantritudien.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import deadlineteam.admission.quantritudien.bean.UsersBean;;

public class RegisterValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return UsersBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "FullName", "fullname.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "UserName", "username.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Password", "password.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ConfirmPassword", "confirmpassword.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Email", "email.required");
		
		UsersBean user = (UsersBean)target;
		if(!user.getConfirmPassword().equals(user.getPassword())){
			errors.rejectValue("ConfirmPassword","confirmPassword.notequal");
		}
		if(user.getPassword().length()<6){
			errors.rejectValue("Password","password.notenough");
		}
		
		
	}

}
