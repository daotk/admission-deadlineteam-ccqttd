package deadlineteam.admission.quantritudien.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import deadlineteam.admission.quantritudien.domain.Dictionary;

public class DictionaryValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return Dictionary.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Question", "question.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Anwser", "anwser.required");
	}

}
