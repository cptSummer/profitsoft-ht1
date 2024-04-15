package com.profitsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Photo {
    private String photoName;
    private String photoFormat;
    private String photoPath;
    private String photoTags;
    private Date uploadDate;


}
