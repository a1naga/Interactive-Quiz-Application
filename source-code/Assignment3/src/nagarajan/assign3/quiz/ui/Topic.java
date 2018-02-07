package nagarajan.assign3.quiz.ui;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Topic {
	
	private String name;
	private List<Entry> entry;

	public String getName() {
		return name;
	}
	
	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Entry> getEntry() {
		return entry;
	}
	
	@XmlElement
	public void setEntry(List<Entry> question) {
		this.entry = question;
	}

	@Override
	public String toString() {
		return "Topic [name=" + name + ", entry=" + entry + "]";
	}

}
