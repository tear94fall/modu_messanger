package com.example.modumessenger.member.service;

import com.example.modumessenger.chat.entity.ChatRoom;
import com.example.modumessenger.chat.entity.ChatRoomMember;
import com.example.modumessenger.chat.repository.ChatRoomRepository;
import com.example.modumessenger.member.entity.Member;
import com.example.modumessenger.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void initialize() {
        ChatRoom roomA = new ChatRoom("roomA", "roomA", "roomA", "roomA");
        ChatRoom roomB = new ChatRoom("roomB", "roomB", "roomB", "roomB");
        ChatRoom roomC = new ChatRoom("roomC", "roomC", "roomC", "roomC");
        ChatRoom roomD = new ChatRoom("roomD", "roomD", "roomD", "roomD");

        Member memberA = new Member("memberA", "memberA@test.com");
        Member memberB = new Member("memberB", "memberB@test.com");
        Member memberC = new Member("memberC", "memberC@test.com");
        Member memberD = new Member("memberD", "memberD@test.com");
        Member memberE = new Member("memberE", "memberE@test.com");
        Member memberF = new Member("memberF", "memberF@test.com");

        // roomA
        ChatRoomMember roomAMemberA = new ChatRoomMember();
        ChatRoomMember roomAMemberB = new ChatRoomMember();
        ChatRoomMember roomAMemberC = new ChatRoomMember();
        ChatRoomMember roomAMemberD = new ChatRoomMember();
        ChatRoomMember roomAMemberE = new ChatRoomMember();
        ChatRoomMember roomAMemberF = new ChatRoomMember();

        // roomB
        ChatRoomMember roomBMemberA = new ChatRoomMember();
        ChatRoomMember roomBMemberB = new ChatRoomMember();
        ChatRoomMember roomBMemberC = new ChatRoomMember();
        ChatRoomMember roomBMemberD = new ChatRoomMember();
        ChatRoomMember roomBMemberE = new ChatRoomMember();
        ChatRoomMember roomBMemberF = new ChatRoomMember();

        // roomC
        ChatRoomMember roomCMemberA = new ChatRoomMember();

        // roomD
        ChatRoomMember roomDMemberA = new ChatRoomMember();

        // create roomA
        roomAMemberA.setChatRoom(roomA);
        roomAMemberB.setChatRoom(roomA);
        roomAMemberC.setChatRoom(roomA);
        roomAMemberD.setChatRoom(roomA);
        roomAMemberE.setChatRoom(roomA);
        roomAMemberF.setChatRoom(roomA);

        roomAMemberA.setMember(memberA);
        roomAMemberB.setMember(memberB);
        roomAMemberC.setMember(memberC);
        roomAMemberD.setMember(memberD);
        roomAMemberE.setMember(memberE);
        roomAMemberF.setMember(memberF);

        memberA.getChatRoomMemberList().add(roomAMemberA);
        memberB.getChatRoomMemberList().add(roomAMemberB);
        memberC.getChatRoomMemberList().add(roomAMemberC);
        memberD.getChatRoomMemberList().add(roomAMemberD);
        memberE.getChatRoomMemberList().add(roomAMemberE);
        memberF.getChatRoomMemberList().add(roomAMemberF);

        roomA.getChatRoomMemberList().add(roomAMemberA);
        roomA.getChatRoomMemberList().add(roomAMemberB);
        roomA.getChatRoomMemberList().add(roomAMemberC);
        roomA.getChatRoomMemberList().add(roomAMemberD);
        roomA.getChatRoomMemberList().add(roomAMemberE);
        roomA.getChatRoomMemberList().add(roomAMemberF);

        // create roomB
        roomBMemberA.setChatRoom(roomB);
        roomBMemberB.setChatRoom(roomB);
        roomBMemberC.setChatRoom(roomB);
        roomBMemberD.setChatRoom(roomB);
        roomBMemberE.setChatRoom(roomB);
        roomBMemberF.setChatRoom(roomB);

        roomBMemberA.setMember(memberA);
        roomBMemberB.setMember(memberB);
        roomBMemberC.setMember(memberC);
        roomBMemberD.setMember(memberD);
        roomBMemberE.setMember(memberE);
        roomBMemberF.setMember(memberF);

        memberA.getChatRoomMemberList().add(roomBMemberA);
        memberB.getChatRoomMemberList().add(roomBMemberB);
        memberC.getChatRoomMemberList().add(roomBMemberC);
        memberD.getChatRoomMemberList().add(roomBMemberD);
        memberE.getChatRoomMemberList().add(roomBMemberE);
        memberF.getChatRoomMemberList().add(roomBMemberF);

        roomB.getChatRoomMemberList().add(roomBMemberA);
        roomB.getChatRoomMemberList().add(roomBMemberB);
        roomB.getChatRoomMemberList().add(roomBMemberC);
        roomB.getChatRoomMemberList().add(roomBMemberD);
        roomB.getChatRoomMemberList().add(roomBMemberE);
        roomB.getChatRoomMemberList().add(roomBMemberF);

        // create roomC
        roomCMemberA.setChatRoom(roomC);
        roomCMemberA.setMember(memberA);
        memberA.getChatRoomMemberList().add(roomCMemberA);
        roomC.getChatRoomMemberList().add(roomCMemberA);

        // create roomD
        roomDMemberA.setChatRoom(roomD);
        roomDMemberA.setMember(memberA);
        memberA.getChatRoomMemberList().add(roomDMemberA);
        roomD.getChatRoomMemberList().add(roomDMemberA);

        // save
        em.persist(roomA);
        em.persist(roomB);
        em.persist(roomC);
        em.persist(roomD);

        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);
        em.persist(memberD);
        em.persist(memberE);
        em.persist(memberF);

        em.persist(roomAMemberA);
        em.persist(roomAMemberB);
        em.persist(roomAMemberC);
        em.persist(roomAMemberD);
        em.persist(roomAMemberE);
        em.persist(roomAMemberF);

        em.persist(roomBMemberA);
        em.persist(roomBMemberB);
        em.persist(roomBMemberC);
        em.persist(roomBMemberD);
        em.persist(roomBMemberE);
        em.persist(roomBMemberF);

        em.persist(roomCMemberA);

        em.persist(roomDMemberA);
    }

    @Test
    @DisplayName("기본 테스트")
    public void basicTest() {
    }

    @Test
    @Rollback(value = false)
    @DisplayName("memberA 의 채팅방 조회 테스트")
    public void getMemberRooms() {
        initialize();
        Member findMember = memberRepository.findByUserId("memberA");
        findMember.getChatRoomMemberList().forEach(room->{
            System.out.println(room.getChatRoom().getRoomName());
        });

        int count = findMember.getChatRoomMemberList().size();
        assertEquals(count, 4);
    }
}