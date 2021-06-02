package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBconnection {

	//////private/////
	private double gameHash;
	private double score;
	private int myId;
	private int myId2;
	private int myId3;

	/**
	 * this method is a constructor.
	 */
	public DBconnection() {
		this.gameHash=0;
		this.score=0;
		this.myId=0;
		this.myId2=0;
		this.myId3=0;
	}

	/**
	 * this method is a constructor.
	 */
	public DBconnection(double gameHash, double score, int id, int id2, int id3) {
		this.gameHash=gameHash;
		this.score=score;
		this.myId=id;
		this.myId2=id2;
		this.myId3=id3;
	}

	/**
	 * this method clears the dbConnection.
	 */
	public void clear() {
		this.setGameHash(0);
		this.setMyId(0);
		this.setMyId2(0);
		this.setMyId3(0);
		this.setScore(0);
	}

	/**
	 * this method returns the game hash.
	 */
	public double getGameHash() {
		return gameHash;
	}

	/**
	 * this method sets the game hash.
	 */
	public void setGameHash(double gameHash) {
		this.gameHash = gameHash;
	}

	/**
	 * this method returns the score.
	 */
	public double getScore() {
		return score;
	}
	/**
	 * this method sets the score.
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * this method returns the first id.
	 */
	public int getMyId() {
		return myId;
	}

	/**
	 * this method sets the first id.
	 */
	public void setMyId(int myId) {
		this.myId = myId;
	}

	/**
	 * this method returns the second id.
	 */
	public int getMyId2() {
		return myId2;
	}

	/**
	 * this method sets the second id.
	 */
	public void setMyId2(int myId2) {
		this.myId2 = myId2;
	}

	/**
	 * this method returns the third id.
	 */
	public int getMyId3() {
		return myId3;
	}

	/**
	 * this method sets the third id.
	 */
	public void setMyId3(int myId3) {
		this.myId3 = myId3;
	}

	/**
	 * this method returns a string. the string represents the string for the sql database, so it sorts the database
	 * by the hash code.
	 */
	public String dbInfoToString() {
		String ans= "SELECT * FROM logs WHERE ";
		ans+="SomeDouble=" + this.gameHash;	
		return ans;
	}

	/**
	 * this method returns the rank of the last game. the method takes in consideration all the games that
	 * were played and exists in the database (in a specific game board).
	 */
	public int rank() {
		int rank=1;
		String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop"; //?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
		String jdbcUser="student";
		String jdbcPassword="student";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
			Statement statement = connection.createStatement();
			String ans = this.dbInfoToString();      //making the string with the specific game board's hash code.
			
			//select data
			String allCustomersQuery = ans;         //reading from the database all the games in the specific game board.
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			while(resultSet.next())
			{
				double point = resultSet.getDouble("Point");      //the current game score.
				if(point>this.getScore()) {                       //if the current score is greater than this score.
					rank+=1;
				}
			}
			resultSet.close();		
			statement.close();		
			connection.close();		
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rank;
	}	
}
