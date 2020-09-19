package com.bulletin_board.app.entity;


import lombok.*;
import lombok.experimental.FieldNameConstants;
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

    @Lob
    @Column(name = "text",nullable = false)
    @Length(min = 20,max = 1000)
    private String text;

    @Column(name = "date",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;


    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
}
