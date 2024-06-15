package Test_Creation_System;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;

public class ManualExam implements Examable {
	private static final int MAX_NUM_OF_QUESTIONS = 10;

	@Override
	public void createExam(Exam ex) throws IOException {
		String strExam = "Exam:\n\n";
		String strSolution = "Solution:\n\n";
		System.out.println("How many questions do you want on the exam?");
		int numOfQuestions = tryCheckNumberOfQuestions();
		int actualNumTrueAnswers = 0;
		int i = 1;
		while (numOfQuestions >= i) {
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------\n");
			System.out.println("Choose a question from the stock:");
			System.out.println(ex.toString());
			int numQuestion = tryCheckNumberOfAnswes(ex);
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------\n");
			strExam += "#" + i + " " + ex.questions[numQuestion - 1].getString() + "\n";
			strSolution += "#" + i + " " + ex.questions[numQuestion - 1].getString() + "\n";
			if (ex.questions[numQuestion - 1] instanceof americanQuestion) {
				System.out.println("How many answers do you want on the question?");
				int numberOfAnswers = Exam.inputTestInt(Exam.scan.nextInt(), 1, 8); // Number of answers.
				int numTrueAnswer = 0; // Number of the correct answer, in a single case.
				int serialNumOfAnswers = 1; // Displaying the serial number.
				for (int j = 0; j < numberOfAnswers; j++, serialNumOfAnswers++) {
					System.out.println(ex.questions[numQuestion - 1].toString());
					System.out.println("Choose an answer:");
					int numTemp = Exam.inputTestInt(Exam.scan.nextInt(), 1,
							((americanQuestion) ex.questions[numQuestion - 1]).getActualNumAnswers());
					strExam += serialNumOfAnswers + ") " + ex.questions[numQuestion - 1].chooseAnswers(numTemp);
					if (ex.questions[numQuestion - 1].getStatus(numTemp)) {
						actualNumTrueAnswers++;
						numTrueAnswer = j + 1;
					}
				} // for
				strExam += serialNumOfAnswers + ") " + "There is no correct answer\n";
				strExam += serialNumOfAnswers + 1 + ") " + "More than one answer is correct\n\n";
				serialNumOfAnswers = 1; // Renew the serialNumOfAnswers for the solution.
				// Adding data for the solution string, depends on the amount of
				// actualNumTrueAnswers.
				if (actualNumTrueAnswers == 0) {
					for (int j = 0; j < numberOfAnswers; j++, serialNumOfAnswers++) {
						strSolution += serialNumOfAnswers + ") false\n";
					}
					strSolution += serialNumOfAnswers + ") true\n";
					strSolution += serialNumOfAnswers + 1 + ") false\n";
				} else if (actualNumTrueAnswers == 1) {
					for (int j = 0; j < numberOfAnswers; j++, serialNumOfAnswers++) {
						if (numTrueAnswer == serialNumOfAnswers) {
							strSolution += serialNumOfAnswers + ") true\n";
						} else
							strSolution += serialNumOfAnswers + ") false\n";
					}
					strSolution += serialNumOfAnswers + ") false\n";
					strSolution += serialNumOfAnswers + 1 + ") false\n";
				} else { // actualNumTrueAnswers > 1.
					for (int j = 0; j < numberOfAnswers; j++, serialNumOfAnswers++) {
						strSolution += serialNumOfAnswers + ") false\n";
					}
					strSolution += serialNumOfAnswers + ") false\n";
					strSolution += serialNumOfAnswers + 1 + ") true\n";
				}
				strSolution += "\n"; // End adding data.
				System.out.println(
						"-----------------------------------------------------------------------------------------------------------------\n");
				actualNumTrueAnswers = 0; // Renew the actualNumTrueAnswers for the next question.
			} else { // Open question case.
				strExam += "\n\n";
				strSolution += ex.questions[numQuestion - 1].chooseAnswers(0) + "\n\n";
			}
			i++;
		} // while
		creatingExamFiles(strExam, "Exem_");
		creatingExamFiles(strSolution, "Solution_");
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

	private int tryCheckNumberOfQuestions() {
		boolean okNumber = false;
		int num = 0;
		while (!okNumber) {
			try {
				num = Exam.scan.nextInt();
				checkNumberOfQuestions(num);
				okNumber = true;
			} catch (NumberOfQuestionsException e) {
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
			try {
				num = Exam.scan.nextInt();
				checkNumberOfAnswes(num, ex);
				okNumber = true;
			} catch (NumberOfAnswersException e) {
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

	private void checkNumberOfAnswes(int num, Exam ex) throws NumberOfAnswersException {
		if (ex.questions[num - 1] instanceof americanQuestion) {
			americanQuestion temp;
			temp = (americanQuestion) ex.questions[num - 1];
			if (temp.getActualNumAnswers() <= 3) {
				throw new NumberOfAnswersException();
			}
		}
	}
}
