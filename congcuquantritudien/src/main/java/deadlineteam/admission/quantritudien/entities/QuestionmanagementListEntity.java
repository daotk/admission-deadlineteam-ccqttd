package deadlineteam.admission.quantritudien.entities;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



	@XmlRootElement(name = "questionlist")
	public class QuestionmanagementListEntity {
		@XmlElement(name = "questionprimary")
		private List<QuestionmanagementEntity> ques = null;

		public List<QuestionmanagementEntity> getQuestionList() {
			return ques;
		}

		public void setQuestionList(List<QuestionmanagementEntity> ques) {
			this.ques = ques;
		}
	
	}

	


