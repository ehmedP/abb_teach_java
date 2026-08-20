package src.model;

import java.io.Serial;
import java.io.Serializable;

public class ChatMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String senderName;
    private String content;
    private long timestamp;

    // transient = this field is NOT serialized
    // The session token is like a password: if we sent it over the network or wrote it
    // into chat_history.dat, someone could steal it and pretend to be this user.
    private transient String sessionToken;

    public ChatMessage(String senderName, String content, long timestamp, String sessionToken) {
        this.senderName = senderName;
        this.content = content;
        this.timestamp = timestamp;
        this.sessionToken = sessionToken;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    @Override
    public String toString() {
        return String.format(
                "ChatMessage{senderName='%s', content='%s', timestamp=%d, sessionToken='%s'}",
                senderName,
                content,
                timestamp,
                sessionToken
        );
    }
}
