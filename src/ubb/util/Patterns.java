package ubb.util;

import java.util.regex.Pattern;

public enum Patterns {
	CREATE_DB("^CREATE DATABASE [A-Za-z]+;$"),
	DROP_DB("^DROP DATABASE [A-Za-z]+;$"),
	CREATE_TABLE("^CREATE TABLE [A-Za-z]+ *\\([A-Za-z0-9]+ (?:CHAR\\([1-9]+[0-9]*\\)|NUMBER)+( NOT NULL)?(?: PRIMARY KEY| UNIQUE)?( *, *[A-Za-z0-9]+ (?:CHAR\\([1-9]+[0-9]*\\)|NUMBER)( NOT NULL)?(?: PRIMARY KEY| UNIQUE)?)*\\);$"),
	DROP_TABLE("^DROP TABLE [A-Za-z]+.[A-Za-z]+;$"),
	SET_SCHEMA("^SET SCHEMA [A-Za-z0-9]+;$");
	
	private String pattern;
	private Patterns(String pattern){
		this.pattern = pattern;
	}
	
	public Pattern getPattern(){
		return Pattern.compile(pattern);
	}
}
