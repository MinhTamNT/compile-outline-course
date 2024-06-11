package com.ou.controllers;

import com.ou.pojo.Subject;
import com.ou.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@ControllerAdvice
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @ModelAttribute
    public void getSubjects(Model model) {
        model.addAttribute("subjects", this.subjectService.getSubjects());
    }

    @GetMapping("/")
    public String list(Model model) {
        return "subjects";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        model.addAttribute("subject", new Subject());
        return "subject";
    }

    @PostMapping("/create")
    public String createSubject(@ModelAttribute(value = "subject") Subject subject) {
        this.subjectService.addOrUpdate(subject);
        return "redirect:/subject/";
    }

    @GetMapping("/{subjectID}")
    public String updateSubject(Model model, @PathVariable(value = "subjectID") int id){
        model.addAttribute("subject", this.subjectService.getSubjectById(id));
        return "subject";
    }
}
