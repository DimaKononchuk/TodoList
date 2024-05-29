package com.example.todolist.Service;


import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;
import java.util.random.RandomGenerator;

@Service
public class CodeGeneratorService {

    private static final SecureRandom random = new SecureRandom();

    private final int number=6;


    public String generateNumber(){
        StringBuilder stringBuilder=new StringBuilder();
        for( int i=0;i<number;i++){
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

}
