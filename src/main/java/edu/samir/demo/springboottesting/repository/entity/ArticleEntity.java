package edu.samir.demo.springboottesting.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Articles")
@Entity
public class ArticleEntity {

    @Id
//    @SequenceGenerator(name = "seq_article", sequenceName = "seq_article")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private long price;

}
