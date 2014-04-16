package deadlineteam.admission.hienthitudien.domain;

import java.util.Date;

public class DictionaryManagement {
	private Integer ID;
	private String Question;
	private Integer CreateBy;
	private String Anwser;
	private Integer AnwserBy;
	private Date CreateDate;
	private Integer UpdateBy;
	private Date UpdateDate;
	private int Status;
	private int DeleteStatus;
	private Integer DeleteBy;
	private Date DeleteDate;
	private int BusyStatus;
	
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
	public Integer getBusyStatus() {
		return BusyStatus;
	}
	public void setBusyStatus(Integer busyStatus) {
		BusyStatus = busyStatus;
	}
}
