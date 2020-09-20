package com.example.demo.methodhandle;

public interface UserMapper {

    //public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    default String getUserName(String userName, Boolean yes, int age) {

        //logger.info("bruce");

        return "bruce " + userName + " " + yes + " " + age;
    }


}
