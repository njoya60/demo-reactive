package com.wf.br.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "societe")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Societe {
    @Id
    private String id;
    private String name;
    private double price;
}
