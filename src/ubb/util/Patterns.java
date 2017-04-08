package ubb.util;

import java.util.regex.Pattern;

public enum Patterns {
	CREATE_DB("^CREATE DATABASE [A-Z_a-z]+$"),
	DROP_DB("^DROP DATABASE [A-Z_a-z]+$"),
	CREATE_TABLE("^CREATE TABLE [A-Z_a-z]+ +\\([A-Z_a-z0-9]+ *(?:CHAR\\([1-9]+[0-9]*\\)|NUMBER)+( NOT NULL)?(?: PRIMARY KEY| UNIQUE)?( *, *[A-Z_a-z0-9]+ (?:CHAR\\([1-9]+[0-9]*\\)|NUMBER)( NOT NULL)?(?: PRIMARY KEY| UNIQUE)?)*\\)$"),
	DROP_TABLE("^DROP TABLE [A-Z_a-z]+.[A-Z_a-z]+$"),
	SET_SCHEMA("^SET SCHEMA [A-Z_a-z0-9]+$"),
	CREATE_INDEX("^CREATE *(UNIQUE)? INDEX [A-Z_a-z]+ ON [A-Z_a-z]+ +\\( *[A-Z_a-z]+ *(, *[A-Z_a-z]+ *)*\\)$"),
	INSERT_ROW(".*"),
	DELETE_ROW(".*");
	private String pattern;
	private Patterns(String pattern){
		this.pattern = pattern;
	}
	
	public Pattern getPattern(){
		return Pattern.compile(pattern);
	}
}
