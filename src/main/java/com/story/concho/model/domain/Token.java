package com.story.concho.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "token_table")
public class Token {
    @Id
    @Column(name = "token_email")
    private String tokenEmail;
    @Column(name="token_value")
    private int tokenValue;
    @Column(name = "token_date")
    private Timestamp tokenDate;
    @Column(name = "token_flag")
    private int tokenFlag;
    public Token(){}

    public Token(String tokenEmail, int tokenValue, Timestamp tokenDate, int tokenFlag) {
        this.tokenEmail = tokenEmail;
        this.tokenValue = tokenValue;
        this.tokenDate = tokenDate;
        this.tokenFlag = tokenFlag;
    }
}
