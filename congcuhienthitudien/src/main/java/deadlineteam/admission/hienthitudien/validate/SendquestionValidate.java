package deadlineteam.admission.hienthitudien.validate;



import deadlineteam.admission.hienthitudien.domain.Questionmanagement;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
public class SendquestionValidate implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		return Questionmanagement.class.isAssignableFrom(clazz);
	}
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "QuestionBy", "fullnameuser.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "QuestionEmail", "emailuser.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Title", "titleusers.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Question", "content.required");
		
	}
}
