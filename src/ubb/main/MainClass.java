package ubb.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import ubb.util.LastMatch;
import ubb.util.Patterns;

public class MainClass {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Socket socket;
	private static DataOutputStream outToServer;
	private static BufferedReader inFromServer;
	
	
	public static void main(String[] args) {
		boolean run = true;
		try {
			initializeSocket();
		} catch (UnknownHostException e1) {
			System.out.println("Could not find host: " + e1.getMessage());
		} catch (IOException e1) {
			System.out.println("Could not open Soket: " + e1.getMessage());
		}
		String line;
		String[] tokens;
		while (run) {
			try {
				line = br.readLine();
				line = line.toUpperCase();
				tokens = line.split(" +");
				String option = tokens[0] + (tokens.length > 1 ? " " + tokens[1] : "");
				switch (option) {
				case "EXIT":
					run = false;
					socket.close();
					break;
				case "CREATE DATABASE":
					handleRequest(Patterns.CREATE_DB, line);
					break;
				case "DROP DATABASE":
					handleRequest(Patterns.DROP_DB, line);
					break;
				case "CREATE TABLE":
					handleRequest(Patterns.CREATE_TABLE, line);
					break;
				case "DROP TABLE":
					handleRequest(Patterns.DROP_TABLE, line);
					break;
				case "SET SCHEMA":
					handleRequest(Patterns.SET_SCHEMA, line);
					break;
				case "CREATE INDEX":
					handleRequest(Patterns.CREATE_INDEX, line);
					break;
				case "INSERT":
					handleRequest(Patterns.INSERT_ROW, line);
					break;
				case "DELETE":
					handleRequest(Patterns.DELETE_ROW, line);
					break;
				case "HELP":
					displayHelp();
					break;
				default:
					System.out.println("Unknown syntax. Please use HELP to see the syntax.\n");
					break;
				}
			} catch (IOException e) {
				System.out.println("Communication with server is broken: " + e.getMessage());
			}

		}

	}

	private static void initializeSocket() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 5678);
		outToServer = new DataOutputStream(socket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	private static void handleRequest(Patterns pattern, String line) throws IOException {
		if(matchesPattern(pattern.getPattern(), line)){
			outToServer.writeBytes(line + '\n');
			String response = inFromServer.readLine();
			System.out.println(response + '\n');
		}else{
			int errorIndex = LastMatch.indexOfLastMatch(pattern.getPattern(), line);
			System.out.println("Error at index: " + errorIndex);
			System.out.println(line.substring(errorIndex));
		}
	}

	private static void displayHelp() {
		System.out.println("CREATE DATABASE database_name");
		System.out.println("DROP DATABASE database_name");
		System.out.println("CREATE TABLE table_name (column_name [VARCHAR(10) | NUMBER] [,column_name [VARCHAR(10)|NUMBER]])");
		System.out.println("DROP TABLE table_name");
		System.out.println("SET SCHEMA schema_name");
		System.out.println("CREATE INDEX index_name ON table_name (column_name [, column_name])");
		System.out.println("EXIT");
	}

	private static boolean matchesPattern(Pattern pattern, String input) {
		return pattern.matcher(input).matches();
	}
}
