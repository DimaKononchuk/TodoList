package com.example.todolist.Controller;


import com.example.todolist.Service.CodeGeneratorService;
import com.example.todolist.Service.EmailSenderService;
import com.example.todolist.Service.noteServices;
import com.example.todolist.Service.peopleServices;

import com.example.todolist.model.ForgotPassword;
import com.example.todolist.model.MyUserDetailService;
import com.example.todolist.model.Notes;
import com.example.todolist.model.People;
import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
@Aspect
@RequestMapping("/")
public class MainController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private peopleServices peopleService;
    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private CodeGeneratorService generatorService;
    private String codeGenerate;

    private ForgotPassword forgotPassword=new ForgotPassword();
    @GetMapping("/login")
    public String login(){
        return "login";
    }




    @GetMapping("/signUp")
    public String registration( People people){

        return "registration";
    }
    @PostMapping("/signUp")
    public String signUp(  @Valid People people,BindingResult bindingResult,@RequestParam("image") MultipartFile file) throws IOException {


        if(bindingResult.hasErrors()){
            return "registration";
        }
            people.setImg(Base64.getEncoder().encodeToString(file.getBytes()));
            people.setPassword(passwordEncoder.encode(people.getPassword()));
            people.setRole("USER");
            peopleService.save(people);
            return "redirect:/todo/main";


    }

    @GetMapping("/forgotpassword")
    public String forgotPassword(Model model){

        model.addAttribute("forgotPassword",new ForgotPassword());
        return "email";
    }
    @PostMapping("/forgotpassword")
    public String postforgotPassword(@ModelAttribute("forgotPassword")  ForgotPassword forgotPassword,BindingResult bindingResult){

            if(!forgotPassword.getCode().equals(this.forgotPassword.getCode() )&& !forgotPassword.getEmail().equals(this.forgotPassword.getEmail())){
                //this.forgotPassword.setEmail(forgotPassword.getEmail());
                bindingResult.rejectValue("code", "error.forgotPassword", "Invalid code!");

            }
            if(bindingResult.hasErrors()){
                return "email";
            }

        return "redirect:/forgotpassword/changepassword";
    }
    @GetMapping("/forgotpassword/changepassword")
    public String changePassword(@ModelAttribute("forgotPassword") ForgotPassword forgotPassword){
        System.out.println(this.forgotPassword.getEmail());
        return"changepassword";
    }

    @PostMapping("/forgotpassword/changepassword")
    public String changePasswordPOST(@ModelAttribute("forgotPassword") ForgotPassword forgotPassword,BindingResult bindingResult){
        this.forgotPassword.setPassword(forgotPassword.getPassword());
        this.forgotPassword.setConfirmPassword(forgotPassword.getConfirmPassword());

        if(!peopleService.changePassword(this.forgotPassword)){
            bindingResult.rejectValue("password","error.forgotPassword","Passwords do not match!");
        };
        if (bindingResult.hasErrors()){

            return"changepassword";
        }
        codeGenerate=null;
        return "redirect:/";
    }

    @PostMapping("/sendCode")
    @ResponseBody
    public Map<String, String> sendCode(@RequestBody Map<String, String> status) {
        // Обробка даних з payload
        Context context = new Context();
        codeGenerate=generatorService.generateNumber();
        this.forgotPassword.setCode(codeGenerate);
        this.forgotPassword.setEmail(status.get("email"));
        context.setVariable("code",codeGenerate );
        System.out.println("Received data: " + status.get("email"));
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("code",this.forgotPassword.getCode());
        emailSenderService.sendHTMLSimpleMessage(status.get("email"),context);
        return response;
    }

}
