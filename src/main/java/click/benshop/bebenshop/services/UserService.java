package click.benshop.bebenshop.services;


import click.benshop.bebenshop.entities.UserEntity;

public interface UserService {

    void createAdmin(UserEntity userEntity);

    void createUser(UserEntity userEntity);

    String getUserName();

    Boolean isRoleAdmin();

    UserEntity getCurrentUser();
}
