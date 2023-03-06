package com.example.chatservice.chat.service;

import com.example.chatservice.chat.dto.ChatDto;
import com.example.chatservice.chat.entity.Chat;
import com.example.chatservice.chat.entity.ChatRoom;
import com.example.chatservice.chat.repository.ChatRepository;
import com.example.chatservice.chat.repository.ChatRoomRepository;
import com.example.chatservice.common.exception.CustomException;
import com.example.chatservice.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final ModelMapper modelMapper;

    public List<ChatDto> searchChatByRoomId(String roomId) {
        List<Chat> chatList = chatRepository.findAllByRoomId(roomId);
        return chatList
                .stream()
                .map(c -> modelMapper.map(c, ChatDto.class))
                .collect(Collectors.toList());
    }

    public List<ChatDto> searchChatByRoomIdSize(String roomId, String size) {
        List<Chat> chatList = chatRepository.findByRoomIdSize(roomId, Long.parseLong(size));
        return chatList
                .stream()
                .map(c -> modelMapper.map(c, ChatDto.class))
                .collect(Collectors.toList());
    }

    public List<ChatDto> searchPrevChatByRoomId(String roomId, String chatId, String size) {
        List<Chat> chatList = chatRepository.findByRoomIdAndChatId(roomId, Long.parseLong(chatId), Long.parseLong(size));
        return chatList
                .stream()
                .map(c -> modelMapper.map(c, ChatDto.class))
                .collect(Collectors.toList());
    }

    public List<ChatDto> searchChatByRoomIdPaging(String roomId, Pageable pageable) {
        List<Chat> chatList = chatRepository.findByRoomIdPaging(roomId, pageable);
        return chatList
                .stream()
                .map(c -> modelMapper.map(c, ChatDto.class))
                .collect(Collectors.toList());
    }

    public List<ChatDto> searchImageChatByRoomIdSize(String roomId, String size) {
        List<Chat> chatList = chatRepository.findByImageChatSize(roomId, Long.parseLong(size));
        return chatList
                .stream()
                .map(c -> modelMapper.map(c, ChatDto.class))
                .collect(Collectors.toList());
    }

    public List<ChatDto> searchChatByMessage(String roomId, String message) {
        List<Chat> chatList = chatRepository.findByMessage(roomId, message);
        return chatList
                .stream()
                .map(c -> modelMapper.map(c, ChatDto.class))
                .collect(Collectors.toList());
    }

    public ChatDto searchChatById(String chatId) {
        Long id = Long.parseLong(chatId);
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CHAT_NOT_FOUND_ERROR, chatId));
        return modelMapper.map(chat, ChatDto.class);
    }

    public Long saveChat(ChatDto chatDto) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(chatDto.getRoomId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND_ERROR, chatDto.getRoomId()));
        Chat chat = new Chat(chatDto);
        chat.setChatRoom(chatRoom);
        Chat saveChat = chatRepository.save(chat);

        return saveChat.getId();
    }

    public ChatDto searchChatByRoomIdAndChatId(String chatId, String roomId) {
        Long id = Long.parseLong(chatId);
        Chat chat = chatRepository.findByRoomIdAndChatId(roomId, id);
        return modelMapper.map(chat, ChatDto.class);
    }

    public String searchChatCount(String roomId) {
        Long id = chatRepository.countByRoomId(roomId);
        return id.toString();
    }
}
