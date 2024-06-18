package com.mx.development.said.olano.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News implements Serializable {
    private String id;
    private String author;
    private String type;
    private String typeVersionNumber;
    private String feedTitle;
    private String category;
    private String entryTitle;



}
