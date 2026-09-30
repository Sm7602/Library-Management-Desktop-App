package com.library.model;

import java.time.LocalDate;

public class Member {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String status;
    private LocalDate memberSince;

    public Member() {
    }

    public Member(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.status = "Active";
        this.memberSince = LocalDate.now();
    }

    public Member(int id, String name, String email, String phone, String address,
                  String status, LocalDate memberSince) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.memberSince = memberSince;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getMemberSince() { return memberSince; }
    public void setMemberSince(LocalDate memberSince) { this.memberSince = memberSince; }
}
