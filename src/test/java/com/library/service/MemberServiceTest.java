package com.library.service;

import com.library.model.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    @Test
    void invalidMemberIsRejected() {
        MemberService service = new MemberService();
        Member member = new Member("", "invalid-email", "", "");

        assertThrows(IllegalArgumentException.class, () -> service.add(member));
    }
}
