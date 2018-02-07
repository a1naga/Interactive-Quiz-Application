package nagarajan.assign3.quiz.ui;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * this class represents one question entry in the quiz.xml file
 */
@XmlRootElement
public class Entry {

	private String question;
	private String answer;
	private String type;
	private String image;
	private String options;
	private int score;

	public String getQuestion() {
		return question;
	}
	
	@XmlElement
	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}
	
	@XmlElement
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getType() {
		return type;
	}
	@XmlElement
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getImage() {
		return image;
	}

	@XmlElement
	public void setImage(String image) {
		this.image = image;
	}

	public String getOptions() {
		return options;
	}

	@XmlElement
	public void setOptions(String options) {
		this.options = options;
	}

	public int getScore() {
		return score;
	}

	@XmlElement
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Entry [question=" + question + ", answer=" + answer + ", type=" + type + ", image=" + image
				+ ", options=" + options + ", score=" + score + "]";
	}

}
