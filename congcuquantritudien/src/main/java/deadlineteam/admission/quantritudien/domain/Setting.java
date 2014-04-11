package deadlineteam.admission.quantritudien.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Setting")
public class Setting {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer ID;
	private Integer RecordNotRep;
	private Integer UserID;
	private Integer PaginDisplayNotRep;
	private Integer RecordTemp;
	private Integer PaginDisplayTemp;
	private Integer RecordRepied;
	private Integer PaginDisplayReplied;
	private Integer RecordDictionary;
	private Integer PaginDisplayDictionary;
	private Integer RecordDelete;
	private Integer PaginDisplayDelete;
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getRecordNotRep() {
		return RecordNotRep;
	}
	public void setRecordNotRep(Integer record) {
		RecordNotRep = record;
	}
	public Integer getPaginDisplayNotRep() {
		return PaginDisplayNotRep;
	}
	public void setPaginDisplayNotRep(Integer paginDisplay) {
		PaginDisplayNotRep = paginDisplay;
	}
	public Integer getUserID() {
		return UserID;
	}
	public void setUserID(Integer userID) {
		UserID = userID;
	}
	public Integer getRecordTemp() {
		return RecordTemp;
	}
	public void setRecordTemp(Integer recordTemp) {
		RecordTemp = recordTemp;
	}
	public Integer getPaginDisplayTemp() {
		return PaginDisplayTemp;
	}
	public void setPaginDisplayTemp(Integer paginDisplayTemp) {
		PaginDisplayTemp = paginDisplayTemp;
	}
	public Integer getRecordRepied() {
		return RecordRepied;
	}
	public void setRecordRepied(Integer recordRepied) {
		RecordRepied = recordRepied;
	}
	public Integer getPaginDisplayReplied() {
		return PaginDisplayReplied;
	}
	public void setPaginDisplayReplied(Integer paginDisplayReplied) {
		PaginDisplayReplied = paginDisplayReplied;
	}
	public Integer getRecordDictionary() {
		return RecordDictionary;
	}
	public void setRecordDictionary(Integer recordDictionary) {
		RecordDictionary = recordDictionary;
	}
	public Integer getPaginDisplayDictionary() {
		return PaginDisplayDictionary;
	}
	public void setPaginDisplayDictionary(Integer paginDisplayDictionary) {
		PaginDisplayDictionary = paginDisplayDictionary;
	}
	public Integer getRecordDelete() {
		return RecordDelete;
	}
	public void setRecordDelete(Integer recordDelete) {
		RecordDelete = recordDelete;
	}
	public Integer getPaginDisplayDelete() {
		return PaginDisplayDelete;
	}
	public void setPaginDisplayDelete(Integer paginDisplayDelete) {
		PaginDisplayDelete = paginDisplayDelete;
	}
	

}
