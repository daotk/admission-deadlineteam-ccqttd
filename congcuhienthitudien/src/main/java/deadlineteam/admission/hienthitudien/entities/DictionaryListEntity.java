package deadlineteam.admission.hienthitudien.entities;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "dictionarylist")
public class DictionaryListEntity {
	@XmlElement(name = "dictionary")
	private List<DictionaryEntity> dics = null;

	public List<DictionaryEntity> getDictionaryList() {
		return dics;
	}

	public void setDictionaryList(List<DictionaryEntity> dics) {
		this.dics = dics;
	}
}
