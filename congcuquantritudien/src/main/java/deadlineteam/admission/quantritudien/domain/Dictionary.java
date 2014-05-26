package deadlineteam.admission.quantritudien.domain;

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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer ID;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private String Question;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Integer CreateBy;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private String Anwser;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Integer AnwserBy;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Date CreateDate;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Integer UpdateBy;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Date UpdateDate;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private int Status;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private int DeleteStatus;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Integer DeleteBy;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private Date DeleteDate;
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
	public Integer getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(Integer createBy) {
		CreateBy = createBy;
	}
	public String getAnwser() {
		return Anwser;
	}
	public void setAnwser(String anwser) {
		Anwser = anwser;
	}
	public Integer getAnwserBy() {
		return AnwserBy;
	}
	public void setAnwserBy(Integer anwserBy) {
		AnwserBy = anwserBy;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
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
