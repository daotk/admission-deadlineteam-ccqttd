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


	@XmlRootElement(name = "questionprimary")
	public class QuestionmanagementEntity {
		@XmlElement(nillable=true)
		public Integer ID;
		public String Question;
		public String QuestionBy;
		public String QuestionEmail;
		public String Answer;
		public Integer AnswerBy;
		public Date AnwserDate;
		public Integer UpdateBy;
		public Date UpdateDate;
		public Integer Status;
		public Integer DeleteStatus;
		public Integer DeleteBy;
	
		public QuestionmanagementEntity(){
			
		}
		public QuestionmanagementEntity(int ID, String Question, String QuestionBy, String QuestionEmail
				,Date QuestionDate, String Answer, Date AnswerDate,Integer UploadBy, Date UpdateDate, 
				Integer Status, Integer DeleteStatus, Integer DeleteBy, Date DeleteDate, Integer AnswerBy){
			this.ID = ID;
			this.Question = Question;
			this.QuestionBy = QuestionBy;
			this.QuestionEmail = QuestionEmail;
			this.Answer = Answer;
			this.AnswerBy = AnswerBy;
			this.AnwserDate = AnswerDate;
			this.UpdateBy = UploadBy;
			this.UpdateDate = UpdateDate;
			this.Status = Status;
			this.DeleteStatus = DeleteStatus;
			this.DeleteBy = DeleteBy;
		}
	
	}

	


