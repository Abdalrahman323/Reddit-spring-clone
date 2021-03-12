package com.springredditclone.redditclone.exceptions;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exceptionMsg , Exception exception) {
        super(exceptionMsg ,exception);
    }
    public SpringRedditException(String exMessage) {
        super(exMessage);
    }
}
