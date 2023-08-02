package platform.api.mappers;

import platform.api.models.user.User;
import platform.api.models.user.UserUpdate;
import platform.data.UserData;

public class UserMapper {
    public static User userDataToUser(UserData userData) {
        User user = new User();
        user.setUsername(userData.getUserName());
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setEmail(userData.getEmail());


        return user;
    }

    public static UserUpdate userToUserUpdate(User user){
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setId(user.getId());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setFirstName(user.getFirstName());
        userUpdate.setLastName(user.getLastName());
        userUpdate.setActive(user.isEnabled());
        return userUpdate;
    }
}
