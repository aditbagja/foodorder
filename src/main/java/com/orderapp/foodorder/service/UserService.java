package com.orderapp.foodorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.orderapp.foodorder.dto.request.LoginRequestDTO;
import com.orderapp.foodorder.dto.request.RegisterRequestDTO;
import com.orderapp.foodorder.dto.response.MessageResponse;
import com.orderapp.foodorder.dto.response.ResponseBodyDTO;
import com.orderapp.foodorder.dto.response.UserInfoResponse;
import com.orderapp.foodorder.exception.classes.AlreadyExistException;
import com.orderapp.foodorder.exception.classes.BadRequestException;
import com.orderapp.foodorder.model.mongoDb.UsersMongo;
import com.orderapp.foodorder.repository.UsersMongoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UsersMongoRepository usersMongoRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final HttpStatus statusOk = HttpStatus.OK;

    @Transactional
    public ResponseEntity<Object> register(RegisterRequestDTO request) {
        UsersMongo userExist = usersMongoRepository.findByUsername(request.getUsername());

        if (userExist != null) {
            throw new AlreadyExistException("Username sudah digunakan");
        } else {
            UsersMongo newUser = UsersMongo.builder()
                    .id(sequenceGenerator.generateSequence(UsersMongo.SEQUENCE_NAME))
                    .username(request.getUsername())
                    .fullname(request.getFullname())
                    .alamat(request.getAlamat())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();

            usersMongoRepository.save(newUser);
            kafkaProducerService.sendUserData(newUser);

            String message = "Berhasil Registrasi User: " + request.getUsername() + " !";
            log.info(message);

            MessageResponse response = MessageResponse.builder()
                    .message(message)
                    .code(statusOk.value())
                    .status(statusOk.getReasonPhrase())
                    .build();

            return new ResponseEntity<>(response, statusOk);
        }
    }

    public UsersMongo findByEmailAndPassword(String username, String password) {
        Query query = new Query(Criteria.where("username").is(username));
        UsersMongo user = mongoTemplate.findOne(query, UsersMongo.class);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    public ResponseEntity<Object> login(LoginRequestDTO request) {
        UsersMongo userExist = findByEmailAndPassword(request.getUsername(), request.getPassword());

        if (userExist == null) {
            throw new BadRequestException("Username atau password salah");
        } else {
            String message = "Berhasil Login dengan username " + request.getUsername();

            ResponseBodyDTO response = ResponseBodyDTO.builder()
                    .total(1)
                    .data(new UserInfoResponse(userExist.getId(), userExist.getUsername()))
                    .message(message)
                    .code(statusOk.value())
                    .status(statusOk.getReasonPhrase())
                    .build();

            log.info(message);

            return new ResponseEntity<>(response, statusOk);
        }
    }
}
