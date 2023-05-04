package edu.ntnu.idatt2106_2023_06.backend.service.users;

import edu.ntnu.idatt2106_2023_06.backend.model.users.User;

public interface IUserInfoService {
    String getAuthenticatedUserEmail();
    String getAuthenticatedUserFirstName();
    String getAuthenticatedUserLastName();
    String getAuthenticatedUserUsername();
    User getAuthenticatedUserObject();
    Long getAuthenticatedUserId();
}
