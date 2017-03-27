package ubb.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String option;
		while (run) {
			displayMenu();

			try {
				option = br.readLine();

				switch (option) {
				case "0":
					run = false;
					socket.close();
					break;
				case "1":
					handleCreateDabase();
					break;
				case "2":
					handleDeleteDatabase();
					break;
				case "3":
					handleCreateTable();
					break;
				case "4":
					handleDeleteTable();
					break;
				case "5":
					handleSetSchema();
				default:
					System.out.println("Optiunea introdusa nu exista!\n");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private static void initializeSocket() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 5678);
		outToServer = new DataOutputStream(socket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	private static void handleSetSchema() throws IOException {
		String line = br.readLine();
		if (matchesPattern(Patterns.SET_SCHEMA.getPattern(), line)) {
			outToServer.writeBytes(line+"\n");
			String response = inFromServer.readLine();
			System.out.println(response +"\n");
		} else {
			System.out.println("Sintaxa gresita!");
		}
		
	}
	
	private static void handleDeleteTable() throws IOException {
		System.out.println("Introduceti comanda de stergere a tabelului!\n");
		String line = br.readLine();
		if (matchesPattern(Patterns.DROP_TABLE.getPattern(), line)) {
			outToServer.writeBytes(line+"\n");
			String response = inFromServer.readLine();
			System.out.println(response +"\n");
		} else {
			System.out.println("Sintaxa gresita!");
		}

	}

	private static void handleCreateTable() throws IOException {
		System.out.println("Introduceti comanda de creare a tabelului!\n");
		String line = br.readLine();
		if (matchesPattern(Patterns.CREATE_TABLE.getPattern(), line)) {
			outToServer.writeBytes(line+"\n");
			String response = inFromServer.readLine();
			System.out.println(response +"\n");
			
		} else {
			System.out.println("Sintaxa gresita!");
		}
	}

	private static void handleDeleteDatabase() throws IOException {
		System.out.println("Introduceti comanda de stergere a bazei de date!\n");
		String line = br.readLine();
		if (matchesPattern(Patterns.DROP_DB.getPattern(), line)) {
			outToServer.writeBytes(line+"\n");
			String response = inFromServer.readLine();
			System.out.println(response +"\n");
		} else {
			System.out.println("Sintaxa gresita!");
		}

	}

	private static void handleCreateDabase() throws IOException {
		System.out.println("Introduceti comanda de creare a bazei de date!\n");
		String line = br.readLine();
		if (matchesPattern(Patterns.CREATE_DB.getPattern(), line)) {
			outToServer.writeBytes(line+"\n");
			String response = inFromServer.readLine();
			System.out.println(response +"\n");
			
		} else {
			System.out.println("Please verify your query!\n");
		}

	}

	private static void displayMenu() {
		System.out.println("Optiuni disponibile:\n");

		System.out.println("1.Crearea unei baze de date.\n2.Stergerea unei baze de date.\n");
		System.out.println("3.Crearea unui tabel.\n4.Stergerea unui tabel.\n");

		System.out.println("0.Iesire\n");

	}

	private static boolean matchesPattern(Pattern pattern, String input) {
		return pattern.matcher(input).matches();
	}
}
