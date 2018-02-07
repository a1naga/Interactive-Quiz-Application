package nagarajan.assign3.quiz.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * This class manages the quiz session user interface, this also tracks the number of questions, correct answers.
 */
public class QuizFrame {

	private JFrame quizFrame;
	private String topic;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Dimension dim = toolkit.getScreenSize();

	private JPanel questionPane = new JPanel();
	private JPanel innerQuestionPane = new JPanel();

	private List<String> topicList;
	private int selectedTopicIndex;
	private Map<String, List<Entry>> topicMap;
	private List<Entry> questionList;
	private int quizScore = 0;
	private int correctAnswer = 0;
	private ButtonGroup inputButtonGrp;
	private JTextField inputTxt;
	private Entry currentQuestion;
	private JLabel lblScore;
	private JLabel lblCorrectAnswers;
	private JLabel lbltimer;
	private TimerListener timerListener;
	private JLabel lblTitle;
	private JLabel animation;
	private JButton btnSubmit;
	private JButton btnNextQestion;
	private JButton btnEndQuiz;
	private JComboBox<String> cmbChangeTopic;
	private Timer timer;
	private int questionCounter;
	private QuizScoreReport report;
	private Map<String, TopicWiseScore> topicWiseScoreMap = new HashMap<String, TopicWiseScore>();
	private Border raisedBorder = BorderFactory.createRaisedBevelBorder();

	private ImageIcon iconThumbsUp = new ImageIcon(getClass().getResource("/res/thumbsUp.png"));
	private ImageIcon iconVeryHappy = new ImageIcon(getClass().getResource("/res/VeryHappy.png"));
	private ImageIcon iconThinking = new ImageIcon(getClass().getResource("/res/iconThinking.png"));
	private ImageIcon iconTimeUp = new ImageIcon(getClass().getResource("/res/timeUp.png"));
	private ImageIcon iconCheckTime = new ImageIcon(getClass().getResource("/res/checkTime.png"));
	private ImageIcon iconWrongAnswer = new ImageIcon(getClass().getResource("/res/wrongAnswer.png"));

	public QuizFrame(List<String> topicList, Map<String, List<Entry>> topicMap) {
		this.topicMap = topicMap;
		this.topicList = topicList;
	}

	/**
	 * creates the quiz frame from the given selected topic index
	 * @param index
	 */
	public void createQuizFrame(int index) {
		this.selectedTopicIndex = index;
		topic = topicList.get(selectedTopicIndex);
		quizFrame = new JFrame();
		quizFrame.setSize(dim.width, dim.height);
		quizFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		quizFrame.getContentPane().setLayout(new BoxLayout(quizFrame.getContentPane(), BoxLayout.PAGE_AXIS));
		createTitle();
		createQuestion(selectedTopicIndex);
		addInnerQuestionPane();
		createOption();

		quizFrame.setVisible(true);
	}

	/**
	 * creates title panel to display the timer, number of correct answers, score
	 */
	public void createTitle() {
		JPanel titlePanel = new JPanel();
		JPanel titlePanel1 = new JPanel();
		JPanel titlePanel2 = new JPanel();
		titlePanel2.setLayout(new FlowLayout(FlowLayout.LEADING));
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

		lblTitle = new JLabel("TOP QUIZ");
		lblTitle.setFont(new Font("Comic Sans MS", Font.CENTER_BASELINE, 30));
		lblTitle.setBackground(new Color(0, 191, 255));
		lbltimer = new JLabel();
		lbltimer.setFont(new Font("Sans MS", Font.PLAIN, 25));

		timerListener = new TimerListener();
		timer = new Timer(1000, timerListener);
		lblScore = new JLabel();
		animation = new JLabel();
		lblCorrectAnswers = new JLabel();
		lblScore.setText("Your Score : 0");
		lblScore.setFont(new Font("Sans MS", Font.PLAIN, 25));
		lblCorrectAnswers.setText("Correct Answers : 0");
		lblCorrectAnswers.setFont(new Font("Sans MS", Font.PLAIN, 25));
		titlePanel1.add(lblTitle);

		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalStrut(30));
		box.add(lbltimer);
		box.add(Box.createHorizontalStrut(20));
		box.add(animation);
		box.add(Box.createHorizontalStrut(350));
		box.add(lblScore);
		box.add(Box.createHorizontalStrut(40));
		box.add(lblCorrectAnswers);
		box.add(Box.createHorizontalGlue());
		// And add it to this component
		titlePanel2.add(box);
		titlePanel.setBorder(new LineBorder(new Color(0, 191, 255), 3));
		titlePanel.setMinimumSize(new Dimension(dim.width, 300));
		titlePanel.setMaximumSize(new Dimension(dim.width, 300));

		titlePanel.add(titlePanel1);
		titlePanel.add(titlePanel2);

		quizFrame.add(titlePanel);
	}

	private void addInnerQuestionPane() {
		questionPane.setLayout(new GridBagLayout());
		questionPane.setMinimumSize(new Dimension(dim.width, 600));
		questionPane.setMaximumSize(new Dimension(dim.width, 600));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		questionPane.add(innerQuestionPane, gbc);
		quizFrame.add(questionPane);
	}

	/**
	 * this chooses a random question from the selected topic and displays the question 
	 * and options, image if available 
	 * @param index
	 */
	private void createQuestion(int index) {

		topic = topicList.get(index);
		questionList = topicMap.get(topic);

		TopicWiseScore score = topicWiseScoreMap.get(topic);
		if (score == null) {
			score = new TopicWiseScore();
			topicWiseScoreMap.put(topic, score);

		}
		score.numberOfAttemptedQuestions++;

		String choiceArray[] = new String[4];
		questionCounter++;

		int questionIndex = new Random().nextInt(questionList.size());
		Entry question = questionList.get(questionIndex);
		currentQuestion = question;
		questionList.remove(questionIndex);

		innerQuestionPane.setLayout(new GridBagLayout());

		JPanel topicPanel = new JPanel();
		GridBagConstraints tpnl = new GridBagConstraints();
		tpnl.fill = GridBagConstraints.HORIZONTAL;
		tpnl.ipady = 20;
		tpnl.gridwidth = 1;
		tpnl.gridx = 0;
		tpnl.gridy = 0;
		innerQuestionPane.add(topicPanel, tpnl);

		topicPanel.setLayout(new GridBagLayout());
		GridBagConstraints topicGbc = new GridBagConstraints();
		topicGbc.weightx = topicGbc.weighty = 1.0;

		JLabel selectedTopic = new JLabel(topic);
		selectedTopic.setFont(new Font("Sans MS", Font.BOLD, 30));
		selectedTopic.setForeground(new Color(0, 191, 255));
		topicPanel.add(selectedTopic, topicGbc);

		JPanel questionOptionsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints qpnl = new GridBagConstraints();
		qpnl.fill = GridBagConstraints.BOTH;
		qpnl.ipady = 0;
		qpnl.weightx = qpnl.weighty = 1.0;
		qpnl.gridx = 0;
		qpnl.gridy = 1;
		innerQuestionPane.add(questionOptionsPanel, qpnl);

		JPanel questionTxtPanel = new JPanel(new GridLayout(0, 1));
		GridBagConstraints qgbc = new GridBagConstraints();
		qgbc.fill = GridBagConstraints.HORIZONTAL;
		qgbc.weightx = 1.0;
		qgbc.gridx = 0;
		qgbc.gridy = 0;
		qgbc.insets = new Insets(40, 100, 0, 0);
		questionOptionsPanel.add(questionTxtPanel, qgbc);

		GridBagConstraints gbc1 = new GridBagConstraints();

		JLabel lblQuestion = new JLabel(questionCounter + ". " + question.getQuestion());
		lblQuestion.setFont(new Font("SansSerif", Font.BOLD, 21));
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		gbc1.weightx = qpnl.weighty = 1.0;
		questionTxtPanel.add(lblQuestion, gbc1);

		JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 4;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(60, 150, 0, 0);
		questionOptionsPanel.add(optionsPanel, gbc);
		JLabel lblEmpty = new JLabel();
		lblEmpty.setPreferredSize(new Dimension(200, 40));

		if ("multiple_choice".equals(question.getType())) {
			Box box = Box.createVerticalBox();
			choiceArray = question.getOptions().split("#");
			ButtonGroup choices = new ButtonGroup();
			inputButtonGrp = choices;
			for (int i = 0; i < choiceArray.length; i++) {
				JRadioButton choicei = new JRadioButton(choiceArray[i]);
				choicei.setFont(new Font("SansSerif", Font.PLAIN, 14));
				choices.add(choicei);
				box.add(choicei);
				box.add(Box.createVerticalStrut(10));
				optionsPanel.add(box, Component.LEFT_ALIGNMENT);

			}
		}

		else {
			inputTxt = new JTextField();
			inputTxt.setPreferredSize(new Dimension(300, 40));
			optionsPanel.add(inputTxt, Component.LEFT_ALIGNMENT);
		}
		
		if (currentQuestion.getImage() != null && !currentQuestion.getImage().isEmpty()) {		
			ImageIcon icon = new ImageIcon(getClass().getResource(currentQuestion.getImage()));
			JLabel lblImage = new JLabel(icon);
			optionsPanel.add(lblEmpty);
			optionsPanel.add(lblImage);
		}

		lbltimer.setText("Time Left : " + Integer.toString(timerListener.elapsedSeconds));
		timer.start();
		animation.setIcon(iconVeryHappy);
	}

	/**
	 * This method creates the bottom section that contains button to submit, choose topic, display next question. 
	 */
	private void createOption() {

		JPanel btnPanel = new JPanel();
		Font font = new Font("Comic Sans MS", Font.PLAIN, 13);
		btnPanel.setMinimumSize(new Dimension(dim.width, 200));
		btnPanel.setMaximumSize(new Dimension(dim.width, 200));
		Color color = new Color(135,206,235);
		Dimension dim = new Dimension(150, 40);
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setBackground(color);
		btnSubmit.setFont(font);
		btnSubmit.setPreferredSize(dim);
		//btnSubmit.setBorder(raisedBorder);
		
		btnNextQestion = new JButton("NEXT QUESTION");
		btnNextQestion.setBackground(color);
		btnNextQestion.setFont(font);
		btnNextQestion.setPreferredSize(dim);
		//btnNextQestion.setBorder(raisedBorder);
		
		btnEndQuiz = new JButton("END QUIZ");
		btnEndQuiz.setBackground(new Color(192, 57, 43));
		btnEndQuiz.setPreferredSize(dim);
		btnEndQuiz.setFont(font);
		//btnEndQuiz.setBorder(raisedBorder);

		cmbChangeTopic = new JComboBox<String>();
		cmbChangeTopic.setBackground(color);
		cmbChangeTopic.setFont(font);
		cmbChangeTopic.setPreferredSize(dim);
		//cmbChangeTopic.setBorder(raisedBorder);

		for (String topic : topicList) {
			cmbChangeTopic.addItem(topic);
		}

		cmbChangeTopic.setSelectedIndex(selectedTopicIndex);
		btnPanel.add(btnSubmit);
		btnSubmit.setEnabled(true);

		btnPanel.add(cmbChangeTopic);
		cmbChangeTopic.setEnabled(false);

		btnPanel.add(btnNextQestion);
		btnNextQestion.setEnabled(false);

		btnPanel.add(btnEndQuiz);
		btnEndQuiz.setEnabled(true);
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performSubmission();
				if (questionCounter >= 8) {
					report = new QuizScoreReport(topicWiseScoreMap, questionCounter, correctAnswer, quizScore);
				} else {
					btnNextQestion.setEnabled(true);
					cmbChangeTopic.setEnabled(true);
				}
			}
		});

		btnNextQestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				innerQuestionPane.removeAll();
				createQuestion(cmbChangeTopic.getSelectedIndex());
				btnNextQestion.setEnabled(false);
				cmbChangeTopic.setEnabled(false);
				innerQuestionPane.revalidate();
				btnSubmit.setEnabled(true);
				timerListener.resetTimer();
				timer.start();
				animation.removeAll();
				animation.setIcon(iconVeryHappy);

			}
		});
		
		btnEndQuiz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				disableInputControls();
				timer.stop();
				report = new QuizScoreReport(topicWiseScoreMap, questionCounter, correctAnswer, quizScore);
			}
		});

		quizFrame.add(btnPanel);

	}

	private void performSubmission() {
		disableInputControls();
		timer.stop();
		calculateScore();
	}
	
	private void disableInputControls() {
		btnSubmit.setEnabled(false);
		if ("multiple_choice".equals(currentQuestion.getType())) {
			Enumeration<AbstractButton> buttons = inputButtonGrp.getElements();
			while (buttons.hasMoreElements()) {
				AbstractButton button = buttons.nextElement();
				button.setEnabled(false);
			}
		} else {
			inputTxt.setEditable(false);
		}
	}

	/**
	 * This method checks if the answer provided is correct or not and updates the score
	 */
	private void calculateScore() {

		String answer = "";

		if ("multiple_choice".equals(currentQuestion.getType())) {
			answer = getSelectedButtonText(inputButtonGrp);
		} else {
			answer = inputTxt.getText();
		}

		if (answer == null) {
			answer = "";
		}

		if (currentQuestion.getAnswer().equalsIgnoreCase(answer.trim())) {

			TopicWiseScore score = topicWiseScoreMap.get(topic);
			score.numberOfCorrectAnswers++;
			quizScore = quizScore + currentQuestion.getScore();
			correctAnswer++;
			lblScore.setText("Your Score : " + Integer.toString(quizScore));

			animation.removeAll();
			animation.setIcon(iconThumbsUp);

		} else {
			animation.removeAll();
			animation.setIcon(iconWrongAnswer);
		}
		lblCorrectAnswers.setText("Correct Answers : " + Integer.toString(correctAnswer) + "/" + questionCounter);
	}

	private String getSelectedButtonText(ButtonGroup buttonGroup) {
		Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		while (buttons.hasMoreElements()) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				return button.getText();
			}
		}
		return null;
	}

	public List<Entry> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Entry> questionList) {
		this.questionList = questionList;
	}

	public int getQuizScore() {
		return quizScore;
	}

	public void setQuizScore(int quizScore) {
		this.quizScore = quizScore;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	/**
	 * This listener class updates the time left for the user to answer and updates the controls accordingly
	 * This also changes the emojis based on the time left. 
	 */
	class TimerListener implements ActionListener {

		private int elapsedSeconds = 15;

		public void actionPerformed(ActionEvent evt) {
			elapsedSeconds--;
			if (elapsedSeconds <= 0) {
				timer.stop();
				disableInputControls();
				calculateScore();
				btnNextQestion.setEnabled(true);
				cmbChangeTopic.setEnabled(true);
				animation.setIcon(iconTimeUp);
			}
			lbltimer.setText("Time Left : " + timerListener.elapsedSeconds);
			if (elapsedSeconds == 5) {
				animation.removeAll();
				animation.setIcon(iconCheckTime);
			}
			if (elapsedSeconds == 10) {
				animation.removeAll();
				animation.setIcon(iconThinking);
			}
			if (elapsedSeconds == 15) {
				animation.removeAll();
				animation.setIcon(iconVeryHappy);
			}
		}

		public void resetTimer() {
			elapsedSeconds = 15;
			lbltimer.setText("Time Left : " + timerListener.elapsedSeconds);
		}

	}

}
