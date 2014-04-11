package deadlineteam.admission.hienthitudien.domain;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name="Dictionary")
@Indexed(index="Dictionary")
public class Dictionary {
	@Id
	@DocumentId
	private Integer ID;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private String Question;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private String Answer;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
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

