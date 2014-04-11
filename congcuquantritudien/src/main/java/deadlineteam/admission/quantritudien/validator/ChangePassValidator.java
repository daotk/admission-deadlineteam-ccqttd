package deadlineteam.admission.quantritudien.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import deadlineteam.admission.quantritudien.bean.UsersBean;;

public class ChangePassValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return UsersBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Password", "password.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "NewPassword", "newpassword.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ConfirmPassword", "confirmpassword.required");
		
		UsersBean user = (UsersBean)target;
		if(!user.getNewPassword().equals(user.getConfirmPassword())){
			errors.rejectValue("ConfirmPassword","confirmPassword.notequal");
		}
		
		
	}

}
