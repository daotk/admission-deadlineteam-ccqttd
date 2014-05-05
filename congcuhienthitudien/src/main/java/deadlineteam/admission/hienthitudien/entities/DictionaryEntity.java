package deadlineteam.admission.hienthitudien.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "dictionary")
public class DictionaryEntity {
	@XmlElement(nillable=true)
	public Integer ID;
	public String Question;
	public String Answer;
	public Date CreateDate;
	
	public DictionaryEntity(){
		
	}
	public DictionaryEntity(int ID, String Question, String Answer, Date CreateDate){
		this.ID = ID;
		this.Question = Question;
		this.Answer = Answer;
		this.CreateDate = CreateDate;	
	}
}
