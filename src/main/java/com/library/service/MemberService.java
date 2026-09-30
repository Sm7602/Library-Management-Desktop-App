package com.library.service;

import com.library.dao.MemberDAO;
import com.library.model.Member;
import com.library.util.ValidationUtils;

import java.util.List;

public class MemberService {
    private final MemberDAO dao = new MemberDAO();

    public boolean add(Member member) {
        validate(member);
        return dao.add(member);
    }

    public boolean update(Member member) {
        validate(member);
        return dao.update(member);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public List<Member> findAll() {
        return dao.findAll();
    }

    public List<Member> search(String keyword) {
        return dao.search(keyword);
    }

    public int count() {
        return dao.count();
    }

    private void validate(Member m) {
        ValidationUtils.required(m.getName(), "Name");
        ValidationUtils.email(m.getEmail());
        ValidationUtils.required(m.getPhone(), "Phone");
        ValidationUtils.required(m.getAddress(), "Address");
    }
}
