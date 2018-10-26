package com.chatbot.hae.chatroom.model;

import com.chatbot.hae.chatroom.nostomp.utils.MessageSendUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/*@Getter*/
public class ChatRoom {
    private String id;
    private String name;
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<WebSocketSession> getSessions() {
		return sessions;
	}

	public void setSessions(Set<WebSocketSession> sessions) {
		this.sessions = sessions;
	}

	private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoom create(@NonNull String name) {
        ChatRoom created = new ChatRoom();
        created.id = UUID.randomUUID().toString();
        created.name = name;
        return created;
    }

    public void handleMessage(WebSocketSession session, ChatMessage chatMessage, ObjectMapper objectMapper) {

        if (chatMessage.getType() == MessageType.JOIN) {
            join(session);
            chatMessage.setMessage(chatMessage.getMessage());
        }
        
        send(chatMessage, objectMapper,session);
    }

    private void join(WebSocketSession session) {
        sessions.add(session);
    }

    private <T> void send(T messageObject, ObjectMapper objectMapper, WebSocketSession session) {
        try {
            TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));
            //sessions.parallelStream().forEach(session -> MessageSendUtils.sendMessage(session, message));
            MessageSendUtils.sendMessage(session, message);
     /*       
            TextMessage message2 = new TextMessage(objectMapper.writeValueAsString("chatbot 대답하기"));
            sessions.parallelStream().forEach(session -> MessageSendUtils.sendMessage(session, message2));*/
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void remove(WebSocketSession target) {

        String targetId = target.getId();
        sessions.removeIf(session -> session.getId().equals(targetId));
    }
}
