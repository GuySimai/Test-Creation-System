package Test_Creation_System;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import Test_Creation_System.Question.level;

public class Exam {
	// Member variables
	static Scanner scan = new Scanner(System.in);
	private static final int EXIT = -1;
	protected int actualNumOfQuestion;
	protected Question[] questions;

	// Constructors
	public Exam() {
		actualNumOfQuestion = 0;
		questions = new Question[actualNumOfQuestion];
	}

	public Exam(int actualNumOfQuestion, Question[] Questions) {
		this.actualNumOfQuestion = actualNumOfQuestion;
		this.questions = new Question[actualNumOfQuestion];
		for (int i = 0; i < Questions.length; i++) {
			this.questions[i] = Questions[i];
		}
	}

	public void start() throws IOException, ClassNotFoundException {
		System.out
				.println("----------------------------Welcome to the exam creation system----------------------------");
		String fileName = chooseSubjectFile();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------");
		int choice = 0;
		while (choice != EXIT) {
			showMenu();
			System.out.println("What is your choice?");
			choice = scan.nextInt();
			if (choice != EXIT) {
				switch (choice) {
				case 1:
					if (actualNumOfQuestion != 0) {
						System.out.println(
								"-----------------------------------------------------------------------------------------------------------------");
						System.out.println(toString());
						System.out.println(
								"-----------------------------------------------------------------------------------------------------------------");
					} else
						System.out.println("There aren't questions in the stock.\n");
					break;
				case 2:
					if (actualNumOfQuestion != 0) {
						addAnswer(stockOfAnswers());
					} else
						System.out.println("There aren't questions in the stock.\n");
					break;
				case 3:
					System.out.println("For adding american question press 0/ For adding open question press 1");
					choice = scan.nextInt();
					if (choice == 0) {
						addQuestion(setAmericanQuestion(stockOfQuestion(), stockOfAnswers()));
					} else if (choice == 1) {
						addQuestion(setOpenQuestion(stockOfQuestion(), stockOfAnswers()));
					} else
						System.out.println("Invalid number");
					break;
				case 4:
					if (actualNumOfQuestion != 0) {
						deleteAnswer();
					} else
						System.out.println("There aren't questions in the stock.\n");
					break;
				case 5:
					if (actualNumOfQuestion != 0) {
						deleteQuestion();
					} else
						System.out.println("There aren't questions in the stock.\n");
					break;
				case 6:
					if (actualNumOfQuestion != 0) {
						CreateExam();
					} else
						System.out.println("There aren't questions in the stock.\n");
					break;
				default:
					System.out.println("Invalid number.\n");
					break;
				} // switch
			} // if
		} // while
		writeToBinaryFile(fileName);
		System.out.println("Goodbye!");
		scan.close();
	}

	public void showMenu() {
		System.out.println("Please select one of the following actions:");
		System.out.println("1  - Display all the questions in the system and the answers press");
		System.out.println("2 - Add a new answer to an existing question press");
		System.out.println("3 - Add a new question press");
		System.out.println("4 - Delete an answer of the a question press");
		System.out.println("5 - Delete a question press");
		System.out.println("6 - Create a exam file and a solution file press");
		System.out.println(EXIT + " - To exit");

	}

	// methods
	// Q1
	public String toString() {
		String str = "The questions and the answers are: \n\n";
		for (int i = 0, num = 1; i < actualNumOfQuestion; i++, num++)
			str += "#" + num + " " + questions[i].toString() + "\n";

		return str;
	}

	// Q2
	public void addAnswer(String[] strAnswers) {
		System.out.println("Which question would you like to add an answer?");
		int i = inputTestInt(scan.nextInt(), 1, actualNumOfQuestion);
		i--;
		if (questions[i] instanceof openQuestion && questions[i].getAnswer() != null) {
			System.out.println(
					"There is already an existing answer for this open question, delete the answer to create a new one.\n");
		} else if (questions[i] instanceof openQuestion && questions[i].getAnswer() == null) {
			openQuestion temp = (openQuestion) questions[i];
			temp.addAnswer(CreateAnswerOpen(strAnswers));
		} else {
			americanQuestion temp = (americanQuestion) questions[i];
			temp.addAnswer(CreateAnswerAmerican(strAnswers));
		}
	}

	// Q3
	public void addQuestion(Question question) {
		spaceCellArray();
		questions[actualNumOfQuestion] = question;
		actualNumOfQuestion++;
		System.out.println("You have successfully added the question.\n");
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------\n");
	}

	private void spaceCellArray() {
		Question[] likeQuestions = new Question[actualNumOfQuestion + 1];
		for (int i = 0; i < actualNumOfQuestion; i++) {
			likeQuestions[i] = questions[i];
		}

		questions = new Question[actualNumOfQuestion + 1];
		for (int i = 0; i < questions.length; i++) {
			questions[i] = likeQuestions[i];
		}
	}

	// Q6
	public void deleteAnswer() {
		System.out.println("Which question would you like to delete an answer?");
		int i = inputTestInt(scan.nextInt(), 1, actualNumOfQuestion);
		i--;
		if (0 <= i && i < actualNumOfQuestion) {
			if (questions[i] instanceof americanQuestion) {
				System.out.println("Which answer would you like to delete?");
				int num = inputTestInt(scan.nextInt(), 1, ((americanQuestion) questions[i]).getActualNumAnswers());
				if (((americanQuestion) questions[i]).deleteAnswer(num)) {
					System.out.println("The answer has successfully deleted.\n");
				} else
					System.out.println(
							"The number you entered does not match the number of answers, the answer has not deleted.\n");
			} else
				((openQuestion) questions[i]).deleteAnswer();
		} else
			System.out.println("Invalid number.\n");
	}

	// Q7
	public void deleteQuestion() {
		System.out.println("Which question would you like to delete");
		int num = inputTestInt(scan.nextInt(), 1, actualNumOfQuestion);
		num -= 1;
		if (num >= 0 && num < actualNumOfQuestion) {
			deleteQuestionHelper(num);
			System.out.println("The question was successfully deleted.\n");
		} else
			System.out.println(
					"The number you entered does not match the number of questions, the question has not deleted.\n");
	}

	public void deleteQuestionHelper(int num) {
		for (int i = num; i + 1 < actualNumOfQuestion; i++) {
			questions[i] = questions[i + 1];
		}
		questions[actualNumOfQuestion - 1] = null;
		actualNumOfQuestion--;
		deleteCellArray();
	}

	private void deleteCellArray() {
		Question[] likeQuestions = new Question[actualNumOfQuestion];
		for (int i = 0; i < actualNumOfQuestion; i++) {
			likeQuestions[i] = questions[i];
		}
		questions = new Question[actualNumOfQuestion];
		for (int i = 0; i < actualNumOfQuestion; i++) {
			questions[i] = likeQuestions[i];
		}
	}

	// Other methods
	private americanQuestion setAmericanQuestion(String[] strQuestion, String[] strAnswers) {
		System.out.println("----------------------------Create an american question----------------------------");
		System.out.println("Choose a question from the Stock:");
		int numQuestion = chooseString(strQuestion, 1, stockOfQuestion().length);
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------");
		System.out.println("How many answers do you want? Up to 10 answers.");
		int numAnswers = scan.nextInt();
		numAnswers = inputTestInt(numAnswers, 1, 10);
		Answer[] answers = new Answer[numAnswers];
		for (int i = 0; i < numAnswers; i++) {
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------");
			answers[i] = CreateAnswerAmerican(strAnswers);
		}
		level lev = chooselevel();
		americanQuestion aQuestionTemp = new americanQuestion(strQuestion[numQuestion], answers.length, answers, lev);
		return aQuestionTemp;
	}

	private static int chooseString(String[] str, int low, int high) {
		showStrings(str);
		int numTemp = scan.nextInt();
		numTemp = inputTestInt(numTemp, low, high);

		return numTemp - 1;
	}

	private Answer CreateAnswerAmerican(String[] str) {
		System.out.println("Choose an answer from the stock:");
		showStrings(str);
		int numTemp = scan.nextInt();
		numTemp = inputTestInt(numTemp, 1, stockOfAnswers().length);
		System.out.println("What is the status of the question?");
		boolean booleanTemp = scan.nextBoolean();
		if (booleanTemp == true) {
		}
		Answer answerTemp = new Answer(str[numTemp - 1], booleanTemp);
		return answerTemp;
	}

	private static void showStrings(String[] str) {
		for (int i = 0; i < str.length; i++) {
			System.out.println(i + 1 + ") " + str[i] + "\n");
		}
	}

	public static int inputTestInt(int numEnter, int numLow, int numHigh) {
		boolean test = false;
		int likeNumEnter = numEnter;
		do {
			if (likeNumEnter >= numLow && likeNumEnter <= numHigh) {
				test = true;
			} else {
				System.out.println("Please enter a number from " + numLow + " to " + numHigh);
				likeNumEnter = scan.nextInt();
			}

		} while (test == false);

		return likeNumEnter;
	}

	// Open question
	private openQuestion setOpenQuestion(String[] strQuestion, String[] strAnswers) {
		System.out.println("----------------------------Create an open question----------------------------");
		System.out.println("Choose a question from the Stock:");
		int numQuestion = chooseString(strQuestion, 1, stockOfQuestion().length);
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------\n");

		System.out.println("Choose an answer from the Stock:");
		int numAnswer = chooseString(strAnswers, 1, stockOfAnswers().length);
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------\n");
		level lev = chooselevel();

		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------\n");

		openQuestion oQuestion = new openQuestion(strQuestion[numQuestion], strAnswers[numAnswer], lev);
		return oQuestion;

	}

	private Answer CreateAnswerOpen(String[] str) {
		System.out.println("Choose an answer from the stock:");
		showStrings(str);
		int numTemp = scan.nextInt();
		numTemp = inputTestInt(numTemp, 1, stockOfAnswers().length);
		Answer answerTemp = new Answer(str[numTemp - 1], true);

		return answerTemp;
	}

	// Choosing a level.
	private level chooselevel() {
		level[] lev = Question.level.values();
		System.out.println("Choose a number of level for the question:");
		for (int i = 0; i < lev.length; i++) {
			System.out.println(1 + lev[i].ordinal() + ") " + lev[i].name());
		}
		int numTemp = inputTestInt(scan.nextInt(), 1, 3);
		numTemp = inputTestInt(numTemp, 1, lev.length);

		return lev[numTemp - 1];
	}

	private void CreateExam() throws IOException {
		Exam ex = new Exam(actualNumOfQuestion, questions);
		Examable[] interfaces = new Examable[2];
		System.out.println("To create an exan manually press 0, to create an exan automatically press 1");
		int num = inputTestInt(scan.nextInt(), 0, 1);
		interfaces[0] = new ManualExam();
		interfaces[1] = new AutomaticExam();
		if (num == 0) {
			interfaces[0].createExam(ex);
		} else if (num == 1) {
			interfaces[1].createExam(ex);
		} else
			System.out.println("Invalid number.");
	}

	public int getActualNumOfQuestion() {
		return actualNumOfQuestion;
	}

	private String chooseSubjectFile() throws IOException {
		boolean next = false;
		String str = "";
		while (next == false) {
			System.out.println("Please choose one of the following options:");
			System.out.println("0 - To create a stock of a new subject.");
			System.out.println("1 - To bring structured questions from an existing file.");
			System.out.println("What is your choice?");
			int num = scan.nextInt();
			if (num == 0) {
				System.out.println("Please enter the name of the new subject stock:");
				str = scan.next() + ".dat";
				File file = new File(str);
				file.createNewFile();
				next = true;
			} else if (num == 1) {
				System.out.println("Please enter the name of the stock file:");
				String strTemp = scan.next();
				File file = new File(strTemp);
				if (file.exists()) {
					readToBinaryFile(strTemp);
					str = strTemp;
					next = true;
				} else {
					System.out.println("A file with the name you entered does not exist.");
				}
			} else
				System.out.println("Invalid number");
		}
		return str;
	}

	private void writeToBinaryFile(String str) {
		WritingBinaryFile Writing = new WritingBinaryFile();
		Writing.openFile(str);
		Writing.writeData(questions);
		Writing.closeFile();
	}

	private void readToBinaryFile(String str) {
		ReadingBinaryFile reading = new ReadingBinaryFile();
		reading.openFile(str);
		questions = reading.readData();
		actualNumOfQuestion = questions.length;
		reading.closeFile();
	}

	// Basic stock
	private String[] stockOfQuestion() {
		String questionStock[] = new String[6];
		questionStock[0] = "What is the smallest star in the solar system?";
		questionStock[1] = "What is the biggest star in the solar system after The San?";
		questionStock[2] = "In which city was the establishment of the State of Israel announced?";
		questionStock[3] = "How many warriors fought against Persia in the ancient period of the ancient Greek kingdom?";
		questionStock[4] = "How much is this 7+7X7 ?";
		questionStock[5] = "What is the limit of 1/x, x--> infinite ?";
		return questionStock;
	}

	private String[] stockOfAnswers() {
		String answerStockStars[] = new String[20];
		answerStockStars[0] = "Mercury";
		answerStockStars[1] = "Venus";
		answerStockStars[2] = "Earth";
		answerStockStars[3] = "Mars";
		answerStockStars[4] = "Jupiter";
		answerStockStars[5] = "Saturn";
		answerStockStars[6] = "Uranus";
		answerStockStars[7] = "Neptune";
		answerStockStars[8] = "Jerusalem";
		answerStockStars[9] = "Haifa";
		answerStockStars[10] = "Be'er Ya'akov";
		answerStockStars[11] = "Tel Aviv";
		answerStockStars[12] = "Eilat";
		answerStockStars[13] = "1";
		answerStockStars[14] = "0";
		answerStockStars[15] = "300";
		answerStockStars[16] = "4";
		answerStockStars[17] = "20";
		answerStockStars[18] = "6";
		answerStockStars[19] = "56";
		return answerStockStars;
	}
}