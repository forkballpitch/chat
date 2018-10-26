package com.chatbot.hae.chatroom.nostomp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.chatbot.hae.chatroom.model.ChatMessage;
import com.chatbot.hae.chatroom.model.ChatRoom;
import com.chatbot.hae.chatroom.repository.ChatBotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("!stomp")
@Component
public class ChatHandler extends TextWebSocketHandler {
	

	    
	private BufferedReader br = null;
	private BufferedReader keyboard = null;
	private PrintWriter  pw= null;
    
    private final ObjectMapper objectMapper;
    private final ChatBotRepository repository;
    Socket socket = null;

    HashMap<String, Socket> dataMap = new HashMap<>();

	@Value("${url.chat.list}")
	String chatUrl;


	@Autowired
    public ChatHandler(ObjectMapper objectMapper, ChatBotRepository chatRoomRepository) {
        this.objectMapper = objectMapper;
        this.repository = chatRoomRepository;
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    	String payload = message.getPayload();
    	ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

    	try{

    		String urlstr = chatUrl;
    		URL url = new URL(urlstr); // URL to your application

    		StringBuilder postData = new StringBuilder();
            String type = "start";
			String userutter = "안녕";
			String domain = "";

    		if(!chatMessage.getType().toString().equals("JOIN")) {
				type = "ing";
			}

			userutter = chatMessage.getMessage().toString();
			domain = chatMessage.getDomain().toString();

			if(userutter.equals(""))
				userutter = "null";


			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			String json = "{\n" +
					"\"session\":\""+ session.getId() +"\",\n" +
					"\"domain\":\"IS-1-@-"+domain+"\",\n" +
					"\"userutter\":\""+ userutter +"\",\n" +
					"\"type\":\""+ type + "\"\n" +
					"}";
			// Connect, easy
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// Tell server that this is POST and in which format is the data
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			conn.setDoOutput(true);


			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes("UTF-8"));
			os.close();

			// read the response
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));


			String inputLine = "";
			String value = "";
			while ((inputLine = in.readLine()) != null) {
				value = inputLine;
			}

			if (in.readLine() != null) {
				inputLine = in.readLine();
			}

			JSONObject jsonObj = new JSONObject(value);
			String greeting = (String) jsonObj.get("content");

			in.close();
			conn.disconnect();

			chatMessage.setMessage(greeting);
			ChatRoom chatRoom = repository.getChatRoom(chatMessage.getChatRoomId());
			chatRoom.handleMessage(session, chatMessage, objectMapper);



    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}


    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	  if(dataMap.get(session.getId()) != null) {
    		  socket = dataMap.get(session.getId());
    		  dataMap.remove(session.getId());
    		  socket.close();
    	  }

        repository.remove(session);
    }
    
    
}

