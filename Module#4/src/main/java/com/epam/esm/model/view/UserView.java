package com.epam.esm.model.view;

import com.epam.esm.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Data
@Relation(collectionRelation = "users")
@EqualsAndHashCode(callSuper = false)
public class UserView extends RepresentationModel<UserView> {
    private Long id;

    private String firstName;

    private String lastName;

    @JsonIgnoreProperties("user")
    private List<OrderView> orders;

    public static UserView fromUserToUserView(User user) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setFirstName(user.getFirstName());
        userView.setLastName(user.getLastName());
        return userView;
    }
}
