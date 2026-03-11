package com.example.Server4.Controllers;

import com.example.Server4.entity.Problem;
import com.example.Server4.repository.ProblemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/problems")
public class ProblemsController {

    private final ProblemRepository problemRepository;

    public ProblemsController(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @GetMapping
    public List<Problem> getAllProblems(){
        return problemRepository.findAll();
    }
}
