package deadlineteam.admission.quantritudien.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import deadlineteam.admission.quantritudien.domain.Users;

public class LoginValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return Users.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "UserName", "username.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Password", "password.required");
	}

}
