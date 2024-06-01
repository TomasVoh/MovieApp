package org.example.movieapp.Model;

import jakarta.persistence.Entity;
import lombok.Data;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity(CustomRevisionListener.class)
@Data
public class CustomRevisionEntity extends DefaultRevisionEntity {
    private String username;
}
