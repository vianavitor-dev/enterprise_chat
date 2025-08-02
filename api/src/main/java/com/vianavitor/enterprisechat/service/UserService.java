package com.vianavitor.enterprisechat.service;

import com.vianavitor.enterprisechat.dao.impl.GpMemberDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.MessageDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.TaskDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.UserDAOImpl;
import com.vianavitor.enterprisechat.dto.mapper.UserDTOMapper;
import com.vianavitor.enterprisechat.dto.user.UserCreateDTO;
import com.vianavitor.enterprisechat.dto.user.UserLoginDTO;
import com.vianavitor.enterprisechat.dto.user.UserRespDTO;
import com.vianavitor.enterprisechat.dto.user.UserUpdateDTO;
import com.vianavitor.enterprisechat.exceptions.DuplicateRegisterException;
import com.vianavitor.enterprisechat.exceptions.NotFoundResourceException;
import com.vianavitor.enterprisechat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAOImpl dao;

    @Autowired
    private TaskDAOImpl taskDao;

    @Autowired
    private GpMemberDAOImpl memberDao;

    @Autowired
    private MessageDAOImpl messageDao;

    private final UserDTOMapper mapper = UserDTOMapper.getInstance();

    public void register(UserCreateDTO data) throws DuplicateRegisterException {
        // verify if the user have an account
        dao.getByEmail(data.email()).ifPresent(_ -> {
            throw new DuplicateRegisterException("user has already been registered, please proceed to login");
        });

        // map the DTO to an entity type before persisting it
        dao.create(mapper.createDTOtoEntity(data));
    }

    public UserRespDTO login(UserLoginDTO data) throws NotFoundResourceException {
        // verify if the user exists by he's email
        User user = dao.getByEmail(data.email())
                .orElseThrow(() -> new NotFoundResourceException("user not found"));

        // verify if the passwords are compatible
        // useful when one of them is hashed
        if (!user.getPassword().equals(data.password())) {
            throw new NotFoundResourceException("email or password invalid");
        }

        return mapper.entityToRespDTO(user);
    }

    public UserRespDTO get(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("user id is null");
        }

        // search by the user with the same ID
        User user = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("user not found"));

        return mapper.entityToRespDTO(user);
    }

    public List<UserRespDTO> getAll() {
        // it stores all the found users
        List<UserRespDTO> userList = new ArrayList<>();

        // transform each user from the query into UserRespDTO data
        dao.getAll().forEach(user -> {
            userList.add(mapper.entityToRespDTO(user));
        });

        return userList;
    }

    public void update(UserUpdateDTO data) {
        // map the DTO to an entity, for then modify it
        dao.modify(mapper.updateDTOtoEntity(data));
    }

    // erase the user account completely
    public void delete(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("user id is null");
        }

        // get the user by ID, and then delete it
        User user = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("user not found"));

        // remove the registers from the tables who the user is related too, such as
        // task, group-member, and message tables
        taskDao.getByUserId(id).forEach(task -> {
            taskDao.remove(task);
        });

        memberDao.getByUserId(id).forEach(member -> {
            memberDao.remove(member);
        });

        messageDao.getByCreatorId(id).forEach(message -> {
            messageDao.remove(message);
        });

        dao.remove(user);
    }

}
