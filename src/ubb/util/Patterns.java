package ubb.util;

import java.util.regex.Pattern;

public enum Patterns {
	CREATE_DB("^CREATE DATABASE [A-Za-z]+;$"),
	DROP_DB("^DROP DATABASE [A-Za-z]+;$"),
	CREATE_TABLE("^CREATE TABLE [A-Za-z]+.[A-Za-z]+ *\\([A-Za-z0-9]+ (?:VARCHAR\\([1-9]+[0-9]*\\)|INTEGER)+( *, *[A-Za-z0-9]+ (?:VARCHAR\\([1-9]+[0-9]*\\)|INTEGER))*\\);$"),
	DROP_TABLE("^DROP TABLE [A-Za-z]+.[A-Za-z]+;$");
	
	private String pattern;
	private Patterns(String pattern){
		this.pattern = pattern;
	}
	
	public Pattern getPattern(){
		return Pattern.compile(pattern);
	}
}
