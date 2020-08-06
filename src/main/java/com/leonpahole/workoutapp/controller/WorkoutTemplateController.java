package com.leonpahole.workoutapp.controller;

import com.leonpahole.workoutapp.dto.WorkoutDto;
import com.leonpahole.workoutapp.dto.WorkoutTemplateDto;
import com.leonpahole.workoutapp.service.WorkoutService;
import com.leonpahole.workoutapp.service.WorkoutTemplateService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Data
@AllArgsConstructor
class CreateWorkoutTemplateResponse {
    long id;
}

@RestController
@RequestMapping("/template")
@AllArgsConstructor
public class WorkoutTemplateController {

    private final WorkoutTemplateService workoutTemplateService;

    @PostMapping
    public ResponseEntity<CreateWorkoutTemplateResponse> createWorkoutTemplate(@RequestBody @Valid WorkoutTemplateDto template) {
        Long createdTemplateId = workoutTemplateService.createWorkoutTemplate(template);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateWorkoutTemplateResponse(createdTemplateId));
    }

    @GetMapping
    public ResponseEntity<List<WorkoutTemplateDto>> getWorkouts() {
        return ResponseEntity.status(HttpStatus.OK).body(workoutTemplateService.getWorkoutTemplates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutTemplateDto> getWorkoutTemplateById(@PathVariable("id") Long workoutTemplateId) {
        return ResponseEntity.status(HttpStatus.OK).body(workoutTemplateService.getWorkoutTemplate(workoutTemplateId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CreateWorkoutTemplateResponse> updateWorkoutTemplate(@PathVariable("id") Long workoutTemplateId,
                                                                               @RequestBody @Valid WorkoutTemplateDto template) {
        Long updatedTemplateId = workoutTemplateService.updateWorkoutTemplate(workoutTemplateId, template);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateWorkoutTemplateResponse(updatedTemplateId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CreateWorkoutTemplateResponse> deleteWorkoutTemplate(@PathVariable("id") Long workoutTemplateId) {
        Long deletedTemplateId = workoutTemplateService.deleteWorkoutTemplate(workoutTemplateId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateWorkoutTemplateResponse(deletedTemplateId));
    }
}
