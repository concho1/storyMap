package com.story.concho.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Data
@Entity             // 테이블 형식 같은 VO
@Table(name="user_table") // 테이블 이름은 user
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
    @Column(name="user_img_cnt")
    int imgCnt;
    @Column(name="user_max_img")
    int imgCntMax;
    @Column(name="user_signup_date")
    Timestamp signupDate;

    public User(){}

    public User(String email, String pw, String nickname, String name, String age, String gender, int imgCnt, int imgCntMax) {
        this.email = email.trim();
        this.pw = pw.trim();
        this.nickname = nickname.trim();
        this.name = name.trim();
        this.age = age.trim();
        this.gender = gender.trim();
        this.imgCnt = imgCnt;
        this.imgCntMax = imgCntMax;
    }


}
