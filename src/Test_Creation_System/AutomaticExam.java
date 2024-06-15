package Test_Creation_System;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Random;

public class AutomaticExam implements Examable {
	private static final int MAX_NUM_OF_QUESTIONS = 10;
	Random rand = new Random();

	@Override
	public void createExam(Exam ex) throws IOException {
		String strExam = "Exam:\n\n";
		String strSolution = "Solution:\n\n";
		System.out.println("How many questions do you want on the exam?");
		int numOfQuestions = tryCheckNumberOfQuestions(ex);
		int i = 1;
		int actualNumTrueAnswers = 0;
		while (numOfQuestions >= i) {
			int numQuestion = tryCheckNumberOfAnswes(ex); // Chooses a question from the stock.
			strExam += "#" + i + " " + ex.questions[numQuestion - 1].getString() + "\n";
			strSolution += "#" + i + " " + ex.questions[numQuestion - 1].getString() + "\n";
			if (ex.questions[numQuestion - 1] instanceof americanQuestion) {
				int numberOfAnswers = 3; // Number of answers.
				int numTrueAnswerSerialNumber = 0;
				int serialNumOfAnswers = 1; // Displaying the serial number.
				for (int j = 0; j < numberOfAnswers; j++, serialNumOfAnswers++) {
					int numTemp = rand.nextInt(((americanQuestion) ex.questions[numQuestion - 1]).getActualNumAnswers())
							+ 1;// Chooses a random answer.
					strExam += serialNumOfAnswers + ") " + ex.questions[numQuestion - 1].chooseAnswers(numTemp);
					if (ex.questions[numQuestion - 1].getStatus(numTemp)) {
						actualNumTrueAnswers++;
						numTrueAnswerSerialNumber = j + 1;
					}
					((americanQuestion) ex.questions[numQuestion - 1]).deleteAnswer(numTemp); // delete the answer.
				} // for
				strExam += serialNumOfAnswers + ") " + "There is no correct answer\n";
				serialNumOfAnswers = 1;
				if (actualNumTrueAnswers == 0) {
					for (int j = 0; j < numberOfAnswers; j++, serialNumOfAnswers++) {
						strSolution += serialNumOfAnswers + ") false\n";
					}
					strSolution += serialNumOfAnswers + ") true\n";
				} else if (actualNumTrueAnswers >= 1) {
					for (int j = 0; j < numberOfAnswers; j++, serialNumOfAnswers++) {
						if (numTrueAnswerSerialNumber == serialNumOfAnswers) {
							strSolution += serialNumOfAnswers + ") true\n";
						} else
							strSolution += serialNumOfAnswers + ") false\n";
					}
					strSolution += serialNumOfAnswers + ") false\n";
				} // End
				strSolution += "\n";
				strExam += "\n";
				actualNumTrueAnswers = 0;
			} else {
				strExam += "\n";
				strSolution += ex.questions[numQuestion - 1].chooseAnswers(0) + "\n\n";
			}
			ex.deleteQuestionHelper(numQuestion - 1); // delete the question.
			i++;
		} // while
		creatingExamFiles(strExam, "Automatic_Exem_");
		creatingExamFiles(strSolution, "Automatic_Solution_");
		System.out.println("The files have been created successfully");
	}

	private void creatingExamFiles(String str, String nameFile) throws IOException {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm");
		File file = new File(nameFile + ldt.format(dtf));
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print(str);
		writer.close();
	}

	private int tryCheckNumberOfQuestions(Exam ex) {
		boolean okNumber = false;
		int num = 0;
		while (!okNumber) {
			try {
				num = Exam.scan.nextInt();
				checkNumberOfQuestions(num);
				checkNumberOfValidQuestions(num, ex);
				okNumber = true;
			} catch (NumberOfQuestionsException e) {
				System.out.println(e.getMessage());
			} catch (numberOfCValidQuestionsException e) {
				System.out.println(e.getMessage());
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, Please enter a number.");
				Exam.scan.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return num;
	}

	private void checkNumberOfQuestions(int num) throws NumberOfQuestionsException {
		if (num > MAX_NUM_OF_QUESTIONS || num <= 0) {
			throw new NumberOfQuestionsException(num);
		}
	}

	private int tryCheckNumberOfAnswes(Exam ex) {
		boolean okNumber = false;
		int num = 0;
		while (!okNumber) {
			num = rand.nextInt(ex.actualNumOfQuestion);
			if (ex.questions[num] instanceof americanQuestion) {
				americanQuestion temp;
				temp = (americanQuestion) ex.questions[num];
				if (temp.getActualNumAnswers() > 3 && temp.getActualNumFalseAnswers() >= 2) {
					okNumber = true;
				}
			} else { // Open question;
				okNumber = true;
			}
		}

		return num + 1;
	}

	private void checkNumberOfValidQuestions(int num, Exam ex) throws numberOfCValidQuestionsException {
		int validQuestions = numberOfCValidQuestions(ex);
		if (num > validQuestions) {
			throw new numberOfCValidQuestionsException(validQuestions);
		}

	}

	private int numberOfCValidQuestions(Exam ex) {
		int numValidQuestions = 0;
		for (int i = 0; i < ex.getActualNumOfQuestion(); i++) {
			if (ex.questions[i] instanceof americanQuestion) {
				if (((americanQuestion) ex.questions[i]).getActualNumFalseAnswers() >= 2
						&& ((americanQuestion) ex.questions[i]).getActualNumAnswers() > 3) {
					numValidQuestions++;
				}
			} else
				numValidQuestions++;
		}
		return numValidQuestions;
	}

}
