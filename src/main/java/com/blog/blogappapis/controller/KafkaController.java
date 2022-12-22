package com.blog.blogappapis.controller;

import com.blog.blogappapis.entities.Role;
import com.blog.blogappapis.payloads.RoleDTO;
import com.blog.blogappapis.payloads.UserDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class KafkaController {
    @Autowired
    private KafkaTemplate<String,Object> template;
    private String topic="Subhajit";
    @GetMapping("/publish/{name}")
    public String publishMessage(@PathVariable String name){
        template.send(topic,"Hi "+name+" welcome");
        return "Data Publish";
    }
    @GetMapping("/publishJson")
    public String publishMessage(){
        RoleDTO roleDTO=new RoleDTO(1,"ROLE_NORMAL");
        List<RoleDTO> roleDTOList=new ArrayList<>();
        roleDTOList.add(roleDTO);
        roleDTOList.add(roleDTO);
        UserDTO userDTO=new UserDTO(1,"abc","abc@gmail.com","123","a",roleDTOList);

        template.send(topic,userDTO);
        return "Data Publish";
    }
}
