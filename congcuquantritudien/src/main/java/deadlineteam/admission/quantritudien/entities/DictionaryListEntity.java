package deadlineteam.admission.quantritudien.entities;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



	@XmlRootElement(name = "dictionarylist")
	public class DictionaryListEntity {
		@XmlElement(name = "dictionary")
		private List<DictionaryEntity> dic = null;

		public List<DictionaryEntity> getDictionaryList() {
			return dic;
		}

		public void setDictionaryList(List<DictionaryEntity> dic) {
			this.dic = dic;
		}
	
	}

	


