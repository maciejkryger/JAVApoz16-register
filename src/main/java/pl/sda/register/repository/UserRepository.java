package pl.sda.register.repository;

import org.springframework.stereotype.Repository;
import pl.sda.register.exception.DuplicatedUsernameException;
import pl.sda.register.exception.UserNotFoundException;
import pl.sda.register.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private Set<User> users = initializeUsers();

    private Set<User> initializeUsers() {
        return new HashSet<>(Arrays.asList(new User("login", "Captain", "Jack")));
    }

    public Set<String> findAllUserNames(String firstName, boolean exactMatch) {

        if (firstName == null) {
            return users.stream().map(User::getUsername).collect(Collectors.toSet());
        }
        if (exactMatch) {
            return users.stream().filter(user -> user.getFirstName().equals(firstName)).map(User::getUsername).collect(Collectors.toSet());
        }
        return users.stream().filter(user -> user.getFirstName().contains(firstName)).map(User::getUsername).collect(Collectors.toSet());


    }

    public User findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny()
                .orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found"));
    }

    public void addUser(User user) {
        users.stream().filter(userInBase -> userInBase.getUsername().equals(user.getUsername()))
                .findFirst()
                .ifPresent(foundUser -> {
                    throw new DuplicatedUsernameException("User " + user.getUsername() + " is already exists");
                });
        users.add(user);
        //
//        if (!optionalUsers.isPresent()) {
//            users.add(user);
//        } else {
//            throw new DuplicatedUsernameException("User " + user.getUsername() + "is already exist");
//        }
    }


    public void removeUser(String username) {

        Optional<User> optionalUser= users.stream().filter(userInBase-> userInBase.getUsername().equals(username))
                .findFirst();
                if(optionalUser.isPresent()){
                    users.remove(optionalUser.get());
                }else {
                    throw new UserNotFoundException("User: "+username+" is not Found");
                }
    }

    public void updateUser(User user) {
        users.remove(user);
        users.add(user);
    }
}
