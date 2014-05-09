package deadlineteam.admission.quantritudien.entities;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



	@XmlRootElement(name = "user")
	public class UserEntity {
		@XmlElement(nillable=true)
		public Integer ID;
		public String FullName;
		public Integer Authorization;
	
		public UserEntity(){
			
		}

	}

	


