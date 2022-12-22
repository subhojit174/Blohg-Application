package com.blog.blogappapis.service;

import com.blog.blogappapis.entities.Role;
import com.blog.blogappapis.entities.User;
import com.blog.blogappapis.payloads.RoleDTO;
import com.blog.blogappapis.payloads.UserDTO;
import com.blog.blogappapis.repository.RoleRepo;
import com.blog.blogappapis.repository.UserRepo;
import com.blog.blogappapis.service.impl.UserServiceImpl;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;


//@PrepareForTest(UserServiceImpl.class)
@RunWith(PowerMockRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@PrepareForTest(fullyQualifiedNames = "com.blog.blogappapis.service.impl.*")
//@SpringBootTest
//@PrepareForTest(UserServiceImpl.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private ModelMapper modelMapper;
    @Autowired
    private ModelMapper modelMapper1;
    //@Spy
    @Mock
    private UserRepo userRepo;
    @Spy
    private UserServiceImpl A;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepo roleRepo;
    @Before
    public void setUp() {
       /* MockitoAnnotations.initMocks(ModelMapper.class);
        MockitoAnnotations.initMocks(UserRepo.class);
        MockitoAnnotations.initMocks(PasswordEncoder.class);
        MockitoAnnotations.initMocks(RoleRepo.class);*/
        MockitoAnnotations.initMocks(this);

    }
    @Test
    public void registerUser() throws Exception {
        Role role=new Role(1,"ROLE_NORMAL");
        //PowerMockito.mockStatic(UserServiceImpl.class);

        RoleDTO roleDTO=new RoleDTO(1,"ROLE_NORMAL");
        List<Role> roleList=new ArrayList<>();
        List<RoleDTO> roleDTOList=new ArrayList<>();
        roleList.add(role);
        roleDTOList.add(roleDTO);

        User user=new User(1,"abc","abc@gmail.com","123","a",null,null,roleList);
        UserDTO userDTO=new UserDTO(1,"abc","abc@gmail.com","123","a",roleDTOList);
        /*RoleRepo roleRepo=mock(RoleRepo.class);
        ModelMapper modelMapper=mock(ModelMapper.class);
        PasswordEncoder passwordEncoder=mock(PasswordEncoder.class);
        UserServiceImpl userServiceImpl=mock(UserServiceImpl.class);
        UserRepo userRepo=mock(UserRepo.class);*/
        Mockito.when(roleRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(role));
        Mockito.when(modelMapper.map(userDTO,User.class)).thenReturn(user);
        Mockito.when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
        //Mockito.when()
        //UserServiceImpl userServiceImpl=new UserServiceImpl();
        //UserServiceImpl A=spy(userServiceImpl);

        //System.out.println(A.getClass());
        //UserServiceImpl userServiceImpl= new UserServiceImpl();
       // doReturn(userDTO).when(A, "userToDTO", user);
        //System.out.println("WhiteBOx:"+userDTO1.getName());
       // doReturn(userDTO).when(A,"ModelMapper",ArgumentMatchers.any());
       // doReturn(userDTO).when(A, "userToDTO", ArgumentMatchers.any());
        Mockito.when(userRepo.save(any())).thenReturn(user);
        Mockito.when(passwordEncoder.encode(any())).thenReturn("123");

        UserDTO registeredUser=userServiceImpl.registerNewUser(userDTO);
        System.out.println(registeredUser.getName());
        assertThat(registeredUser).usingRecursiveComparison().isEqualTo(userDTO);


    }


}
