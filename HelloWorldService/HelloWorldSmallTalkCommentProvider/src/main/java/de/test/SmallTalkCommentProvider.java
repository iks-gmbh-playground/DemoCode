package de.test;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import de.test.util.CommentStringUtils;

public class SmallTalkCommentProvider {

	static final HashMap<String, String> KNOWN_NAMES = new HashMap<String, String>();
	static final String DEFAULT_COMMENT_STUB = ", do you know a prominent person of your name?";
	
	static {
		KNOWN_NAMES.put("Angela", "Funny, you have the same name as the German chancellor!");
		KNOWN_NAMES.put("Albert", "Interesting, you have the same name as most famous scientific genius, Albert Einstein!");
	}
	
	public String getComment(final String name) {
		if (StringUtils.isEmpty(name)) {
			return "";
		}

		if (KNOWN_NAMES.containsKey(name)) {
			return KNOWN_NAMES.get(name);
		}
		
		return CommentStringUtils.appendStrings(name, DEFAULT_COMMENT_STUB);
	}
}
