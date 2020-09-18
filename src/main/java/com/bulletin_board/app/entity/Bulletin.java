package com.bulletin_board.app.entity;

import com.bulletin_board.app.config.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "bulletins")
public class Bulletin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bulletin_id", unique = true, nullable = false)
    private Integer id;


    @Column(name = "header",nullable = false)
    @Length(min = 8, max = 30)
    private String header;

    @Column(name = "text",nullable = false)
    @Length(min = 20,max = 100)
    private String text;

    @Column(name = "date",nullable = false)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "EEE MMM dd HH:mm:ss zzz yyyy")
    private Date date;

    @ManyToOne
    private User author;
}
