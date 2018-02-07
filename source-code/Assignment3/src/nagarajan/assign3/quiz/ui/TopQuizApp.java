package nagarajan.assign3.quiz.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * This class is the entry point to the quiz application, this creates the
 * initial screen and calls the QuizFrame to create the actual quiz questions.
 *
 */
public class TopQuizApp {

	private JFrame homeScreen;
	private Map<String, List<Entry>> topicMap;

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension dim = toolkit.getScreenSize();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {

				}
				try {
					TopQuizApp window = new TopQuizApp();
					window.homeScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TopQuizApp() {
		QuestionData data = new QuestionData();
		data.loadDataFromXml();
		topicMap = data.getTopicMap();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		createHomeScreen();

	}

	/**
	 * creates home screen components
	 */
	private void createHomeScreen() {
		homeScreen = new JFrame();
		JPanel bgPanel = new JPanel();
		bgPanel.setLayout(new BoxLayout(bgPanel, BoxLayout.PAGE_AXIS));
		Box box = Box.createVerticalBox();
		ImageIcon icon = new ImageIcon(getClass().getResource("/quiz-images/quizBackgrond.jpeg"));

		homeScreen.setSize(dim.width, dim.height);
		homeScreen.setContentPane(bgPanel);
		homeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel titleImage = new JLabel(icon);
		titleImage.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel title = new JLabel("Welcome to Top Quiz for MiddleSchool kids");
		title.setFont(new Font("Sans MS", Font.BOLD, 30));
		title.setForeground(Color.BLUE);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblDesc = new JLabel("Choose a topic below and press START button to start the quiz");
		lblDesc.setFont(new Font("Sans MS", Font.PLAIN, 20));
		lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

		List<String> topicList = new ArrayList<String>();
		// Get all Keys from the map as a set and iterate. Then add to the
		// topicList
		Set<String> set = topicMap.keySet();
		for (String topic : set) {
			topicList.add(topic);
		}

		JComboBox<String> topicSelection = new JComboBox<String>();
		topicSelection.setBackground(new Color(135, 206, 235));
		topicSelection.setMaximumSize(new Dimension(150, 40));
		topicSelection.setFont(new Font("Sans MS", Font.PLAIN, 14));
		for (String topic : topicList) {
			topicSelection.addItem(topic);
		}

		topicSelection.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnStart = new JButton(" START ");
		btnStart.setFont(new Font("Sans MS", Font.BOLD, 15));
		btnStart.setBackground(new Color(135, 206, 235));
		btnStart.setMaximumSize(new Dimension(150, 40));
		btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);

		box.add(Box.createVerticalStrut(10));
		box.add(titleImage);
		box.add(Box.createVerticalStrut(10));
		box.add(title);
		box.add(Box.createVerticalStrut(40));
		box.add(lblDesc);
		box.add(Box.createVerticalStrut(30));
		box.add(topicSelection);
		box.add(Box.createVerticalStrut(30));
		box.add(btnStart);
		box.add(Box.createVerticalStrut(30));

		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				QuizFrame frame = new QuizFrame(topicList, topicMap);

				frame.createQuizFrame(topicSelection.getSelectedIndex());
				homeScreen.dispose();

			}
		});

		box.add(Box.createVerticalStrut(30));

		JPanel tipsPanel = new JPanel();
		Box tipsBox = Box.createVerticalBox();
		JLabel lblTips = new JLabel("Tips:");
		lblTips.setFont(new Font("Sans MS", Font.PLAIN, 14));
		lblTips.setAlignmentX(Component.LEFT_ALIGNMENT);
		tipsBox.add(lblTips);
		tipsBox.add(Box.createVerticalStrut(10));

		JLabel lblTip1 = new JLabel(
				"1. You will have a maximum of 8 questions to attempt and 15 secs to answer each question");
		lblTip1.setFont(new Font("Sans MS", Font.PLAIN, 14));
		lblTip1.setAlignmentX(Component.LEFT_ALIGNMENT);
		tipsBox.add(lblTip1);
		tipsBox.add(Box.createVerticalStrut(5));

		JLabel lblTips1 = new JLabel("2. There will be an option to select the topic before going to next question");
		lblTips1.setFont(new Font("Sans MS", Font.PLAIN, 14));
		lblTips1.setAlignmentX(Component.LEFT_ALIGNMENT);
		tipsBox.add(lblTips1);
		tipsBox.add(Box.createVerticalStrut(5));

		JLabel lblTips2 = new JLabel(
				"3. An emoji will be displayed on the left top, which will change based on the time left.");
		lblTips2.setFont(new Font("Sans MS", Font.PLAIN, 14));
		lblTips2.setAlignmentX(Component.LEFT_ALIGNMENT);
		tipsBox.add(lblTips2);
		tipsBox.add(Box.createVerticalStrut(5));

		JLabel lblTips3 = new JLabel("4. An image will be displayed as hint for some of the questions");
		lblTips3.setFont(new Font("Sans MS", Font.PLAIN, 14));
		lblTips3.setAlignmentX(Component.LEFT_ALIGNMENT);
		tipsBox.add(lblTips3);
		tipsBox.add(Box.createVerticalStrut(10));
		tipsPanel.add(tipsBox);

		bgPanel.add(box);
		bgPanel.add(tipsPanel);
	}

}
