package deadlineteam.admission.quantritudien.bean;


import java.util.Date;



public class DictionaryRestful {

	private Integer ID;
	
	private String Question;
	
	private String Answer;
	
	private Date CreateDate;

	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getQuestion() {
		return Question;
	}
	public void setQuestion(String question) {
		Question = question;
	}
	public String getAnwser() {
		return Answer;
	}
	public void setAnwser(String anwser) {
		Answer = anwser;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
}
