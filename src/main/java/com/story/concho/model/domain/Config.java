package com.story.concho.model.domain;


import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name="config_table") // 테이블 이름은 user
public class Config {
    @Id
    @Column(name="config_key")
    String key;
    @Column(name="config_value")
    String value;
}
