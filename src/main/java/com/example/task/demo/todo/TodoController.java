package com.example.task.demo.todo;

import com.sun.xml.bind.v2.TODO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("api/todos")

public class TodoController {

    private TodoRepository todoRepository;

    public  TodoController(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @GetMapping("/{id}")
    public Optional<Todo> getTodo(@PathVariable long id){
        return  todoRepository.findById(id);
    }


    @GetMapping
    public List<Todo> getTodos(){
        return todoRepository.findAll();
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo){
        todoRepository.save(todo);
        return todo;
    }
    @PutMapping("/id")
    public Todo editTodo(@PathVariable long id, @RequestBody Todo todo){
         Todo existingTodo = new Todo();
         try{
             existingTodo = todoRepository.findById(id).get();
             existingTodo.setDescription(todo.getDescription());
             existingTodo.setComplete(todo.isComplete());
             existingTodo.setTitle(todo.getTitle());
             todoRepository.save(existingTodo);
         }catch (NoSuchElementException e) {
             System.out.println(e.getMessage());
             existingTodo = todoRepository.save(todo);
         }
         return existingTodo;
    }
    @DeleteMapping
    public void deleteTodo(@PathVariable long id){
        todoRepository.deleteById(id);
    }
}
