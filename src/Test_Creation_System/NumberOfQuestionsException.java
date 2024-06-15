package Test_Creation_System;

public class NumberOfQuestionsException extends Exception {

	public NumberOfQuestionsException(int NumberOfQuestions) {
		super("You entered the number " + NumberOfQuestions + ", the number of questions can be between 1 to 10");
	}

}
