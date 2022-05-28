package com.cgh.server.domain.dto;

import com.cgh.server.domain.Member;

public class MemberRecordDTO {
    Member member;
    String record;


    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
