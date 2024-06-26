package com.story.concho.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity             // 테이블 형식 같은 VO
@Table(name="img_table") // 테이블 이름은 user
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="img_id")
    Integer id;
    @Column(name="img_email")
    String email;
    @Column(name="img_latitude")
    String latitude;
    @Column(name="img_longitude")
    String longitude;
    @Column(name="img_date")
    String date;
    @Column(name="img_path")
    String path;
    @Column(name="img_name")
    String name;

    public Img(){}

    public Img(String email, String latitude, String longitude, String date, String path, String name) {
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.path = path;
        this.name = name;
    }
}
