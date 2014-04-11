package deadlineteam.admission.quantritudien.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;


@Entity
@Table(name="Questionmanagement")
//This annotation tells hibernate search that this class has to be indexed
@Indexed(index="Questionmanagement")
public class Questionmanagement {
	
	@Id
	@DocumentId
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer ID;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private String Title;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private String Question;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private String QuestionBy;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private String QuestionEmail;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Date QuestionDate;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private String Answer;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Integer AnswerBy;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Date AnwserDate;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Integer UpdateBy;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Date UpdateDate;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Integer Status;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Integer DeleteStatus;
	private Integer DeleteBy;
	private Date DeleteDate;

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
	public Integer getDeleteBy() {
		return DeleteBy;
	}
	public void setDeleteBy(Integer deleteBy) {
		DeleteBy = deleteBy;
	}
	public Date getDeleteDate() {
		return DeleteDate;
	}
	public void setDeleteDate(Date deleteDate) {
		DeleteDate = deleteDate;
	}

}
