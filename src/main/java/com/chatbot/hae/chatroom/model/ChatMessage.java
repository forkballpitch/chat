package com.chatbot.hae.chatroom.model;

/*
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor*/
public class ChatMessage {
    private String chatRoomId;
    private String writer;
  
	private String message;
    private MessageType type;
	private String domain;
    
    public String getChatRoomId() {
  		return chatRoomId;
  	}
  	public void setChatRoomId(String chatRoomId) {
  		this.chatRoomId = chatRoomId;
  	}
  	public String getWriter() {
  		return writer;
  	}
  	public void setWriter(String writer) {
  		this.writer = writer;
  	}
  	public String getMessage() {
  		return message;
  	}
  	public void setMessage(String message) {
  		this.message = message;
  	}
  	public MessageType getType() {
  		return type;
  	}
  	public void setType(MessageType type) {
  		this.type = type;
  	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
