package ngrams;

public class Parser {

	public static void main(String[] args) {
		Parser p = new Parser();
		String s = "ALICE'S ADVENTURES IN WONDERLAND\n"
				+ "Lewis Carroll\n"
				+ "THE MILLENNIUM FULCRUM EDITION 3.0\n"
				+ "CHAPTER I. Down the Rabbit-Hole\n"
				+ "Alice was beginning to get very tired of sitting by her sister on the\n"
				+ "bank, and of having nothing to do: once or twice she had peeped into the\n"
				+ "book her sister was reading, but it had no pictures or conversations in\n"
				+ "it, 'and what is the use of a book,' thought Alice 'without pictures or\n"
				+ "conversations?'\n"
				+ "So she was considering in her own mind (as well as she could, for the\n"
				+ "hot day made her feel very sleepy and stupid), whether the pleasure\n"
				+ "of making a daisy-chain would be worth the trouble of getting up and\n"
				+ "picking the daisies, when suddenly a White Rabbit with pink eyes ran\n"
				+ "close by her.\n";
		p.parse(s);

	}
	
	public void parse(String s){
		s = s.replaceAll("\\p{Punct}+", "");
		System.out.println(s.toLowerCase());
	}
	
	

}
