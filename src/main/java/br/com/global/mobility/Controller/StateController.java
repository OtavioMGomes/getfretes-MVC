package br.com.global.mobility.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.global.mobility.Model.State;
import br.com.global.mobility.Service.StateService;

@RestController
@RequestMapping("/api/state")
public class StateController {

    @Autowired
    StateService service;

    @GetMapping
    public Page<State> index( @PageableDefault(size = 10, sort = "id") Pageable pageable){
        return service.listAll(pageable);
    }

    @GetMapping("initials")
    public ResponseEntity<List<String>> listAllInitial(){
        return ResponseEntity.of(service.listAllInitial());
    }

    @GetMapping("{id}")
    public ResponseEntity<State> show(@PathVariable Integer id){
        return ResponseEntity.of(service.findById(id));   
    }

    @PostMapping
    public ResponseEntity<State> create(@RequestBody @Valid State state){
        service.save(state);
        return ResponseEntity.status(HttpStatus.CREATED).body(state);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<State> delete(@PathVariable Integer id){
        Optional<State> optional = service.findById(id);

        if (optional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }else{
            service.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<State> update(@PathVariable Integer id, @RequestBody @Valid State newState){
        Optional<State> optional = service.findById(id);

        if (optional.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        }else{

            State state = optional.get();
            BeanUtils.copyProperties(newState, state);
            state.setId(id);
    
            service.save(state);
    
            return ResponseEntity.ok(state);

        }
        
    }

}
