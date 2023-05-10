package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Comments")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "Comments")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentModel extends RepresentationModel<CommentModel> {
    private Long id;
    private Long userID;
    private Long vehicleID;
    private String content;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private int rating;
 }
