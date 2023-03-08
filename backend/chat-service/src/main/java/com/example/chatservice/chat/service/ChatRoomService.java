package com.example.chatservice.chat.service;

import com.example.chatservice.chat.dto.ChatRoomDto;
import com.example.chatservice.chat.entity.ChatRoom;
import com.example.chatservice.chat.entity.ChatRoomMember;
import com.example.chatservice.chat.repository.ChatRoomMemberRepository;
import com.example.chatservice.chat.repository.ChatRoomRepository;
import com.example.chatservice.common.exception.CustomException;
import com.example.chatservice.common.exception.ErrorCode;
import com.example.chatservice.member.entity.Member;
import com.example.chatservice.member.repository.MemberRepository;
import com.example.chatservice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public List<ChatRoomDto> searchChatRoomByUserId(String userId) {
        List<ChatRoomMember> chatRoomMemberList = chatRoomMemberRepository.findAllByMemberUserId(userId);

        return chatRoomMemberList.stream()
                .map(chatRoomMember -> new ChatRoomDto(chatRoomMember.getChatRoom()))
                .collect(Collectors.toList());
    }

    public ChatRoomDto searchChatRoomByRoomId(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND_ERROR, roomId));
        return new ChatRoomDto(chatRoom);
    }

    public List<ChatRoomDto> searchOneOnOneChatRoom(String userId, String roomUserId) {
        List<ChatRoomMember> chatRoomMemberList = chatRoomMemberRepository.findAllByMemberUserId(userId);
        List<ChatRoom> chatRoomList = chatRoomMemberList.stream()
//                .filter(ChatRoomMember::isOneOnOne)
                .map(ChatRoomMember::getChatRoom)
                .filter(chatRoom -> {
                    List<String> roomMemberId = chatRoom.getChatRoomMemberList().stream()
                            .map(chatRoomMember -> chatRoomMember.getMember().getUserId())
                            .collect(Collectors.toList());

                    return roomMemberId.size() == 2 && roomMemberId.contains(userId) && roomMemberId.contains(roomUserId);
                })
                .collect(Collectors.toList());

        return chatRoomList.stream()
                .map(ChatRoomDto::new)
                .collect(Collectors.toList());
    }

    public ChatRoomDto createChatRoom(List<String> userId) {
        List<Member> members = memberRepository.findAllByUserIds(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USERID_NOT_FOUND_ERROR, userId.toString()));

        String chatRoomCreateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ChatRoom chatRoom = new ChatRoom(UUID.randomUUID().toString(), "새로운 채팅방", "", "", "", chatRoomCreateTime);

        List<ChatRoomMember> chatRoomMemberList = members.stream()
                        .map(member -> {
                            ChatRoomMember chatRoomMember = new ChatRoomMember(member, chatRoom);
                            chatRoom.getChatRoomMemberList().add(chatRoomMember);
                            return chatRoomMember;
                        })
                .collect(Collectors.toList());

        chatRoomMemberRepository.saveAll(chatRoomMemberList);
        ChatRoom newRoom = chatRoomRepository.save(chatRoom);

        return modelMapper.map(newRoom, ChatRoomDto.class);
    }

    public ChatRoomDto removeChatRoomMember(String roomId, String userId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND_ERROR, roomId));
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByUserIdAndRoomId(userId, roomId);

        chatRoom.getChatRoomMemberList().remove(chatRoomMember);

        chatRoomMemberRepository.delete(chatRoomMember);
        chatRoomRepository.save(chatRoom);

        return new ChatRoomDto(chatRoom);
    }

    public ChatRoomDto updateChatRoom(String roomId, ChatRoomDto chatRoomDto) {
        ChatRoom findChatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND_ERROR, roomId));

        findChatRoom.setRoomName(chatRoomDto.getRoomName());
        findChatRoom.setRoomImage(chatRoomDto.getRoomImage());
        findChatRoom.setLastChatMsg(chatRoomDto.getLastChatMsg());
        findChatRoom.setLastChatId(chatRoomDto.getLastChatId());
        findChatRoom.setLastChatTime(chatRoomDto.getLastChatTime());

        ChatRoom updateChatRoom = chatRoomRepository.save(findChatRoom);
        return modelMapper.map(updateChatRoom, ChatRoomDto.class);
    }

    public ChatRoomDto addMemberChatRoom(String roomId, List<String> userIds) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND_ERROR, roomId));
        List<Member> members = memberRepository.findAllByUserIds(userIds)
                .orElseThrow(() -> new CustomException(ErrorCode.USERID_FRIENDS_NOT_FOUND_ERROR, userIds.toString()));

        chatRoom.getChatRoomMemberList().forEach(chatRoomMember -> members.remove(chatRoomMember.getMember()));

        List<ChatRoomMember> chatRoomMemberList = members.stream()
                .map(member -> {
                    ChatRoomMember chatRoomMember = new ChatRoomMember(member, chatRoom);
                    chatRoom.getChatRoomMemberList().add(chatRoomMember);
                    return chatRoomMember;
                })
                .collect(Collectors.toList());

        chatRoomMemberRepository.saveAll(chatRoomMemberList);
        ChatRoom newRoom = chatRoomRepository.save(chatRoom);

        return modelMapper.map(newRoom, ChatRoomDto.class);
    }
}