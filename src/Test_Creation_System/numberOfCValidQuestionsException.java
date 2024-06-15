package Test_Creation_System;

public class numberOfCValidQuestionsException extends Exception {

	public numberOfCValidQuestionsException(int validQuestions) {
		super("There are only " + validQuestions
				+ " valid questions, according to the requirement that \"there cannot be more than one correct answer to a question and more then 4 answers for american questions\"");
	}

}
