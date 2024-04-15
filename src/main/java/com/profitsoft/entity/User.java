package com.profitsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String email;
    private Date joinDate;

}
