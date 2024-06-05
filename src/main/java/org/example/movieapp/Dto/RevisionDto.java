package org.example.movieapp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevisionDto {
    private long entityId;
    private RevisionType revType;
    private LocalDateTime timestamp;
    private String username;
}
