package com.bulletin_board.app.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT STRATEGY
  @Column(name = "user_id", unique = true, nullable = false)
  private Integer id;

  @Length(min = 2, max = 25)
  @Column(name = "first_name", unique = true, nullable =  false)
  private String firstName;

  @Length(min = 2, max = 25)
  @Column(name = "last_name", unique = true, nullable =  false)
  private String lastName;

  @Column(name = "password", nullable =  false)
  private String password;

  @Length(min = 10, max = 50)
  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "author")
  private List<Bulletin> posts;
}
