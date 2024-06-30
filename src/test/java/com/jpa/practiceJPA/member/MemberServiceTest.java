package com.jpa.practiceJPA.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);
        Member findMember = memberRepository.findOne(savedId);

        //then
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    void 중복회원_예외발생() {
        //given
        Member member1 = new Member();
        Member member2 = new Member();

        //when
        member1.setName("중복");
        member2.setName("중복");
        memberRepository.save(member1);

        //then
        assertThatThrownBy(() ->
                memberService.join(member2)).isInstanceOf(IllegalStateException.class);
    }

}