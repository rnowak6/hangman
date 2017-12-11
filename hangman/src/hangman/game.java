package hangman;

import java.util.ArrayList;
import java.util.Scanner;

public class game {
	public static int correct = 0;
	public static int wrong = 0;
	public static String word="";
	public static String genre;
	public static String[][] answers = { {"tomato", "apple", "kiwi", "orange", "asparagus", "radish", "carrot", "banana", "peach", "zucchini"},
	                              {"dog", "kangaroo", "elephant", "giraffe", "panda", "crocodile", "hamster", "lemur", "jaguar", "aardvark"},
	                              {"illinois", "michigan", "mississippi", "montana", "kentucky", "arkansas", "alaska", "indiana", "nebraska", "california"}
	};
	public static ArrayList<String> letters = new ArrayList<String>();
	static String[] right = new String[word.length()]; //displays the letters correctly guessed
	static String[] split = word.split("");
	
	
	//create a method that chooses a random word and assigns it to the String word.
	public static void newWord(String[][] a) {
		int column = (int) (Math.random() * 3)  ;
		int row = (int)(Math.random()* 9);
		word = a[column][row];
		word = word.toUpperCase();
		if(column==0) {
			genre = "Fruits/Vegetables";
		}
		else if(column==1) {
			genre = "Animals";
		} 
		else {
			genre = "States";
		}
	}
	
	/*
	//makes word into array to check letters easier
	public static String[] toArray(String word) {
		String[] newArray = new String[word.length()];
		newArray = word.split("");
		return newArray;
	}
	*/
	
	//create a method that checks if a letter has been used
	public static boolean addLetter(String a)
	{
		a = a.toUpperCase();
		if(letters.isEmpty())
		{
			return true;
		}
		for(int i = 0; i<letters.size(); i++) {
			if(a.equals(letters.get(i)))
			{
				return false;
			}
		}
		letters.add(a);
		return true;
	}

	
	//create a method that returns a bool that checks if someone guessed a correct letter
	public static boolean isCorrect(String a) {
		a = a.toUpperCase();
		int checker = correct;
		for(int i = 0; i < split.length-1; i++) {
			if(a.equals(split[i].toUpperCase())) {
				correct++;
				right[i]=split[i].toUpperCase(); //puts it in the array that is being displayed
			}
		}
		if(checker==correct) { //checks if the correct amount increased and if it hadn't then it was wrong.
			wrong++;
			return false;
		}
		return true;
	}
	
	
	//main 
	public static void main(String[] args)
	{
		newWord(answers);
		System.out.println(genre);
		System.out.println(word);
		Scanner scan = new Scanner(System.in);
		while(correct<word.length()) {
			String guess = scan.nextLine().toUpperCase();
			if(addLetter(guess)==true) {
				if(isCorrect(guess)==true) {
					System.out.println("Yay! You guessed a letter right!");
					for(int i = 0; i<right.length; i++) {
						System.out.print(right[i]+" ");
					}
				}
				else {
					System.out.println("Sorry, you're wrong");
					System.out.println(wrong);
				}
			}
			else {
				System.out.println("You can't guess the same letter smh");
			}
		}
		
		
		
	}
	
	

}
