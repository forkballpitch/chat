package com.chatbot.hae.chatroom.repository;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import com.chatbot.hae.chatroom.model.ChatRoom;

@Repository
public class ChatBotRepository {
    private final Map<String, ChatRoom> chatRoomMap;
   // @Getter
    private Collection<ChatRoom> chatRooms;

    public Collection<ChatRoom> getChatRooms() {
		return chatRooms;
	}

	public ChatBotRepository() {
        chatRoomMap =  Stream.of(ChatRoom.create("Sample"))
                      .collect(Collectors.toMap(ChatRoom::getId, Function.identity()));

        chatRooms = chatRoomMap.values();
    }

    public ChatRoom getChatRoom(String id) {
        return chatRoomMap.get(id);
    }
    public void setChatRoom(String id, ChatRoom room) {
        chatRoomMap.put(id, room);
        chatRooms = chatRoomMap.values();
    }

    public void remove(WebSocketSession session) {
        this.chatRooms.parallelStream().forEach(chatRoom -> chatRoom.remove(session));
    }
}
