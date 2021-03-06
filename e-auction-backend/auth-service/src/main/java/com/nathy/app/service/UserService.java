package com.nathy.app.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.nathy.app.entity.User;
import com.nathy.app.repository.UserRepository;

@Service
@Slf4j
public class UserService {

    private UserRepository repository;
    //private final RestTemplate restTemplate;

//    @Autowired
//    public UserService(UserRepository repository,
//                       RestTemplate restTemplate) {
//        this.repository = repository;
//        this.restTemplate = restTemplate;
//    }


    public User save(User user) {
        return repository.save(user);
    }

    public User getById(String id) {
        return this.repository.findById(id).orElse(null);
    }

//    public ResponseTemplateVO getUserWithDepartment(String id) {
//        User user = this.getById(new ObjectId(id));
//
//        Department department = restTemplate.getForObject("http://department-service/departments/" + user.getDepartmentId(), Department.class);
//
//        return new ResponseTemplateVO(user, department);
//    }
}
