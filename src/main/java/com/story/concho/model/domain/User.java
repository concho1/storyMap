package com.story.concho.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity             // 테이블 형식 같은 VO
@Table(name="user") // 테이블 이름은 user
public class User {

    @Id
    @Column(name="user_email")
    String email;
    @Column(name="user_pw")
    String pw;
    @Column(name="user_nickname")
    String nickname;
    @Column(name="user_name")
    String name;
    @Column(name="user_age")
    String age;
    @Column(name="user_gender")
    String gender;
    @Column(name="user_folder_id")
    String folderId;
    @Column(name="user_signup_date")
    String signupDate;

    public User(){}

    public User(String email, String pw, String nickname, String name, String age, String gender, String folderId) {
        this.email = email.trim();
        this.pw = pw.trim();
        this.nickname = nickname.trim();
        this.name = name.trim();
        this.age = age.trim();
        this.gender = gender.trim();
        this.folderId = folderId.trim();
    }


}
