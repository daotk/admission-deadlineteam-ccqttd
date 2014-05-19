package deadlineteam.admission.quantritudien.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;


	@XmlRootElement(name = "dictionary")
	public class DictionaryEntity {
		@XmlElement(nillable=true)
		public Integer ID;
		public String Question;
		public Integer CreateBy;
		public String Anwser;
		public Integer AnwserBy;
		public Date CreateDate;
		public Integer UpdateBy;
		public Date UpdateDate;
		public Integer Status;
		public Integer DeleteStatus;
		public Integer DeleteBy;
		public Date DeleteDate;
		public Integer BusyStatus;
	
		public DictionaryEntity(){
			
		}
		public DictionaryEntity(int ID, String Question, int CreateBy, String Anwser
				,int AnwserBy, Date CreateDate, int UpdateBy,Date UpdateDate, int Status, 
				int DeleteStatus, int DeleteBy, Date DeleteDate, int BusyStatus){
			this.ID = ID;
			this.Question = Question;
			this.CreateBy = CreateBy;
			this.Anwser = Anwser;
			this.AnwserBy = AnwserBy;
			this.CreateDate = CreateDate;
			this.UpdateBy = UpdateBy;
			this.UpdateDate = UpdateDate;
			this.Status = Status;
			this.DeleteStatus = DeleteStatus;
			this.DeleteBy = DeleteBy;
			this.DeleteDate = DeleteDate;
			this.BusyStatus = BusyStatus;
		}
	
	}

	


