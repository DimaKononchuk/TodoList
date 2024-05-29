package com.example.todolist.Controller;


import com.example.todolist.Service.noteServices;
import com.example.todolist.Service.peopleServices;
import com.example.todolist.model.MyUserDetailService;
import com.example.todolist.model.Notes;
import com.example.todolist.model.People;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private peopleServices peopleService;
    @Autowired
    private MyUserDetailService userDetailService;
    @Autowired
    private noteServices NotesServices;
//
//    @ModelAttribute("Username")
//    public String getUserAuthetification(@AuthenticationPrincipal UserDetails people){
//        return people.getUsername();
//    }
    @ModelAttribute
    public void getUserImg(@AuthenticationPrincipal UserDetails people,Model model)
    {
        System.out.println("getimg controller");
        Optional<People> people1=peopleService.findByUsername(people.getUsername());
        model.addAttribute("Username",people1.get().getName());
        model.addAttribute("image",people1.get().getImg());
    }

    @Before(value = "print() && args(people, model)")
    @GetMapping("/main")
    public String main(@AuthenticationPrincipal UserDetails people, Model model){
        System.out.println("----");
        Optional<People> people1=peopleService.findByUsername(people.getUsername());
        List<Notes> notesList=people1.get().getNotesList();
        //notesList=people1.get().getNotesList();
        System.out.println("main execute");
        model.addAttribute("image",people1.get().getImg());
        model.addAttribute("userList",notesList);
        //model.addAttribute("Username",people.getUsername());
        //model.addAttribute("myNotes",new Notes());
        return "index";
    }

    @GetMapping("/main/add")
    public String addNotes(@ModelAttribute("desiredNote")  Notes notes, Model model, BindingResult bindingResult){
        model.addAttribute("TextBtn","Add Notes");
        model.addAttribute("TextLogo","Adding a note");
        model.addAttribute("url","/todo/main/add");
        model.addAttribute("urlMethod","POST");
        model.addAttribute("class","btnDispalyNone");
        return "notes";
    }
    @PostMapping("/main/add")
    public String addPostNotes(Authentication authentication, @ModelAttribute("desiredNote") @Valid Notes notes,BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("TextBtn","Add Notes");
            model.addAttribute("TextLogo","Adding a note");
            model.addAttribute("url","/todo/main/add");
            model.addAttribute("urlMethod","POST");
            model.addAttribute("class","btnDispalyNone");
            return "notes";
        }else {
            Optional<People> user = peopleService.findByUsername(authentication.getName());
            NotesServices.addNote(notes,user);
            return "redirect:/todo/main";
        }

    }
    //    @PostMapping("/main")
//    public String addNotes( @Valid @ModelAttribute("myNotes") Notes myNotes,Authentication authentication,BindingResult bindingResult){
//        Optional<People> user = peopleService.findByUsername(authentication.getName());
//        NotesServices.addNote(myNotes,user);
//
//        return "redirect:/main";
//    }
    @GetMapping("/notes/{id}")
    public String NotesForm(@PathVariable("id") int id, @AuthenticationPrincipal UserDetails people, Model model){
        List<Notes> userList=peopleService.findByUsername(people.getUsername()).get().getNotesList();
        Notes desiredNote = userList.stream()
                .filter(note -> note.getId()==id)
                .findFirst()
                .orElse(null);
        model.addAttribute("desiredNote",desiredNote);
        //model.addAttribute("Username",people.getUsername());
        model.addAttribute("TextBtn","Update Notes");
        model.addAttribute("TextLogo","Note");
        model.addAttribute("url","/todo/notes/"+id);
        model.addAttribute("urlMethod","PUT");
        model.addAttribute("class","btn btn-danger");

        return "notes";
    }
    @GetMapping("/notes/{id}/delete")
    public String deleteNotes(@PathVariable("id") int id,Model model){
        NotesServices.deleteNote(id);
        return "redirect:/todo/main";
    }
    @PutMapping("/notes/{id}")
    public String updateNotesId(@PathVariable ("id") int id, @ModelAttribute("desiredNote") @Valid Notes notes, BindingResult bindingResult, Model model){

        if(bindingResult.hasFieldErrors()){
            model.addAttribute("TextBtn","Update Notes");
            model.addAttribute("TextLogo","Note");
            model.addAttribute("url","/todo/notes/"+id);
            model.addAttribute("urlMethod","PUT");
            model.addAttribute("class","btn btn-danger");
            model.addAttribute("desiredNote", notes);
            return "notes";
        }else{
            System.out.println(notes.getNotes());
            System.out.println(notes.getDescription());
            System.out.println(notes.getId());
            NotesServices.updateNotes(notes);
            return "redirect:/todo/main";
        }

    }


}
