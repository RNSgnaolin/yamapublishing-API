package com.yama.publishing.domain.author;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
public class Author implements Comparable<Author> {

    public Author(DataAuthor data) {
        this.name = data.name();
        this.phone = data.phone();
        this.email = data.email();
    }
    
    @Column(name = "author_name")
    private String name;

    @Column(name = "author_phone")
    private String phone;
    
    @Column(name = "author_email")
    private String email;

    @Override
    public int compareTo(Author otherAuthor) {
        return this.name.compareTo(otherAuthor.name);
    }

    public void setName(String name) {
        if (name != null)
            this.name = name;
    }

    public void setPhone(String phone) {
        if (phone != null)
            this.phone = phone;
    }

    public void setEmail(String email) {
        if (email != null)
            this.email = email;
    }

}
