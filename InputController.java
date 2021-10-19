package com.myproject.myproject.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import com.myproject.myproject.data.Student;
import com.myproject.myproject.data.User;
import com.myproject.myproject.datainterface.StudentInterface;
import com.myproject.myproject.datainterface.UserInterface;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class InputController {

    @Autowired
    private StudentInterface studentInterface;

    @Autowired
    private UserInterface userInterface;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String display() {
        return "index";
    }

    @GetMapping("/slogin")
    public String slogin() {
        return "slogin";
    }

    
    
    @GetMapping("/index")
    public String showhome() {
        return "index";
    }

    @GetMapping("/about")
    public String show() {
        return "about";
    }

    @GetMapping("/courses")
    public String view() {
        return "courses";
    }

    @GetMapping("/lab")
    public String viewshow() {
        return "lab";
    }

    @GetMapping("/cell")
    public String see() {
        return "cell";
    }

    @GetMapping("/fees")
    public String seeview() {
        return "fees";
    }

    @GetMapping("/faculty")
    public String fac() {
        return "faculty";
    }

    @GetMapping("/track")
    public String trackrec() {
        return "track";
    }

    @GetMapping("/contactus")
    public String contact() {
    return "contactus";
    }

    @GetMapping("/enrollmentform")
    public String enrollment(Model model) {
        model.addAttribute("student", new Student());
        return "enrollmentform";
    }

    @PostMapping("/enrollmentform")
    public String enrollment(@Valid Student student, BindingResult bindingResult, MultipartFile file,
            RedirectAttributes redirectAttributes, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "enrollmentform";
        }

        boolean fileOK = false;
        byte[] bytes = file.getBytes();

        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename);

        if (filename.endsWith("jpg") || filename.endsWith("png")) {
            fileOK = true;
        }
        redirectAttributes.addFlashAttribute("message", "account created");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        String studentname = student.getName();
        Student studentExists = studentInterface.findByName(studentname);
        if (!fileOK) {
            redirectAttributes.addFlashAttribute("message", "Image must be a jpg or a png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("student", student);
        }

        else if (studentExists != null) {
            redirectAttributes.addFlashAttribute("message", "Course exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("student", student);
        } else {
            student.setImage(filename);
            studentInterface.save(student);

            Files.write(path, bytes);
        }

        return "redirect:/enrollmentform";
    }

    
    @GetMapping("/retrieve")
    public String retrieve(Model model, Principal principal) {
        //principal stores the details of current user logged in
        if (principal != null) {
            model.addAttribute("principal", principal.getName());
        }

        List<Student> students = studentInterface.findAll();
        model.addAttribute("students", students);
        return "retrieve";
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable int id, Model model, Principal principal) {

        if (principal != null) {
            model.addAttribute("principal", principal.getName());
        }

        Student student = studentInterface.getOne(id);
        model.addAttribute("student", student);
        return "update";
    }

    @PostMapping("/update")
    public String edit(@Valid Student student, BindingResult bindingResult, MultipartFile file,
            RedirectAttributes redirectAttributes, Model model) throws IOException {

        
        Student currentStudent = studentInterface.getOne(student.getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("student", student);
            model.addAttribute("student", currentStudent.getName());
            return "update";
        }

        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename);

        if (!file.isEmpty()) {
            if (filename.endsWith("jpg") || filename.endsWith("png")) {
                fileOK = true;
            }
        } else {
            fileOK = true;
        }

        String studentname = student.getName();
        String studentimage = student.getImage();

        Student studentExists = studentInterface.findByIdAndName(student.getId(), studentname);
        

        if (!fileOK) {
            redirectAttributes.addFlashAttribute("message", "Image must be a jpg or a png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("student", student);
        } else if (studentExists != null) {
            if (!file.isEmpty()) {
                Path path2 = Paths.get("src/main/resources/static/media/" + currentStudent.getImage());
                Files.delete(path2);
                student.setImage(filename);
                Files.write(path, bytes);
            } else {
                student.setImage(currentStudent.getImage());
            }
            studentInterface.save(student);
        }
        
        redirectAttributes.addFlashAttribute("message", "Student details edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/update/" + student.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model, RedirectAttributes redirectAttributes, Principal principal)
            throws IOException {

        if (principal != null) {
            model.addAttribute("principal", principal.getName());
        }

        
        Student student = studentInterface.getOne(id);
        Student currentStudent = studentInterface.getOne(student.getId());

        Path path2 = Paths.get("src/main/resources/static/media/" + currentStudent.getImage());
        Files.delete(path2);
        studentInterface.deleteById(id);

        List<Student> students = studentInterface.findAll();
        model.addAttribute("students", students);
        redirectAttributes.addFlashAttribute("message", "Student's Details Deleted Successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/retrieve";

    }


    @GetMapping("/register")
    public String add(@ModelAttribute User user) {
        return "register";
    }

    @PostMapping("/register")
    public String add(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        String name = user.getName();
        User userExists = userInterface.findByName(name);

        if (userExists != null) {
            redirectAttributes.addFlashAttribute("message", "Student already exist");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        } else {

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userInterface.save(user);
            redirectAttributes.addFlashAttribute("message", "Student added");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        }
        return "redirect:register";
    }

    @GetMapping("/studentdetails")
    public String details(@Valid User user, Model model, Principal principal) {
        
        if (principal != null) {
            model.addAttribute("principal", principal.getName());
        }

        
        User users = userInterface.findByUsername(principal.getName());
       
        model.addAttribute("users", users);
        return "studentdetails";
    }

    
    @GetMapping("/alogin")
    public String index(Model model) {
        
        return "alogin";
    }

}

        







    
