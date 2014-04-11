package deadlineteam.admission.hienthitudien.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;


public class Questionmanagement {

	private Integer ID;
	private String Title;
	private String Question;
	private String QuestionBy;
	private String QuestionEmail;
	private Date QuestionDate;
	private String Answer;
	private Integer AnswerBy;
	private Date AnwserDate;
	private Integer UpdateBy;
	private Date UpdateDate;
	private Integer Status;
	private Integer DeleteStatus;

	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}

	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}

	public String getQuestion() {
		return Question;
	}
	public void setQuestion(String question) {
		Question = question;
	}

	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	
	public Integer getStatus() {
		return Status;
	}
	public void setStatus(Integer status) {
		Status = status;
	}
	
	public Integer getDeleteStatus() {
		return DeleteStatus;
	}
	public void setDeleteStatus(Integer deleteStatus) {
		DeleteStatus = deleteStatus;
	}
	
	public String getQuestionBy() {
		return QuestionBy;
	}
	public void setQuestionBy(String questionBy) {
		QuestionBy = questionBy;
	}

	public String getQuestionEmail() {
		return QuestionEmail;
	}
	public void setQuestionEmail(String questionEmail) {
		QuestionEmail = questionEmail;
	}

	public Date getQuestionDate() {
		return QuestionDate;
	}
	public void setQuestionDate(Date questionDate) {
		QuestionDate = questionDate;
	}
	
	public Integer getAnswerBy() {
		return AnswerBy;
	}
	public void setAnswerBy(Integer answerBy) {
		AnswerBy = answerBy;
	}
	
	public Date getAnwserDate() {
		return AnwserDate;
	}
	public void setAnwserDate(Date anwserDate) {
		AnwserDate = anwserDate;
	}

	public Integer getUpdateBy() {
		return UpdateBy;
	}
	public void setUpdateBy(Integer updateBy) {
		UpdateBy = updateBy;
	}

	public Date getUpdateDate() {
		return UpdateDate;
	}
	public void setUpdateDate(Date updateDate) {
		UpdateDate = updateDate;
	}

}
