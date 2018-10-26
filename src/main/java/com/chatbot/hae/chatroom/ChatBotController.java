package com.chatbot.hae.chatroom;

import com.chatbot.hae.chatroom.model.ChatRoom;
import com.chatbot.hae.chatroom.repository.ChatBotRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/chat")
public class ChatBotController {

    private final ChatBotRepository repository;
    private final String listViewName;
    private final String detailViewName;
    private final AtomicInteger seq = new AtomicInteger(0);

    @Autowired
    public ChatBotController(ChatBotRepository repository, @Value("${viewname.chatroom.list}") String listViewName, @Value("${viewname.chatroom.detail}") String detailViewName) {
        this.repository = repository;
        this.listViewName = listViewName;
        this.detailViewName = detailViewName;
    }

    @GetMapping("/bots")
    public String rooms(Model model) {
        model.addAttribute("rooms", repository.getChatRooms());
        return listViewName;
    }

    @GetMapping("/bots/{domain}")
    public String room(@PathVariable String domain, Model model) {
        //ChatRoom room = repository.getChatRoom(id);
        //if(room == null) {
        ChatRoom room = ChatRoom.create("New");
        repository.setChatRoom(room.getId(), room);
        // }
        model.addAttribute("room", room);
        model.addAttribute("domain", domain);
        model.addAttribute("member", "member" + seq.incrementAndGet());

        return detailViewName;
    }

    @GetMapping("/hello")
    public String sendMessage(Model model) {
        //model.addAttribute("request", request);
        return "index";
    }

}
