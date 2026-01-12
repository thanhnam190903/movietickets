package com.example.DATN.controller;

import com.example.DATN.model.Role;
import com.example.DATN.model.User;
import com.example.DATN.dto.UserDTO;
import com.example.DATN.repository.RoleRepository;
import com.example.DATN.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/quantri")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/khachhang")
    public String showList(Model model, @Param("key") String key){
        List<User> users = userRepository.findAll();
        if (key != null){
            users = userRepository.searchUser(key);
            model.addAttribute("key",key);
        }
        model.addAttribute("user",users);
        return "admin/nguoidung";
    }
    @GetMapping("/add-nguoidung")
    public String showFormThemMoi(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO",userDTO);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("role",roles);
        return "admin/addnguoidung";
    }
    @InitBinder
    public void initBinder(WebDataBinder data){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        data.registerCustomEditor(String.class,stringTrimmerEditor);
    }
    @PostMapping("/create-user")
    public String addkhachhang(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result ,@RequestParam("role") Role role, Model model ){
        String username = userDTO.getUsername();
        if (result.hasErrors()){
            List<Role> roles = roleRepository.findAll();
            model.addAttribute("role", roles);
            return "admin/addnguoidung";
        }
        User userExit = userRepository.findByUsername(username);
        if (userExit != null){
            List<Role> roles = roleRepository.findAll();
            model.addAttribute("role",roles);
            model.addAttribute("userDTO",new UserDTO());
            model.addAttribute("my_error","Tài khoản đã tồn tại");
            return "admin/addnguoidung";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setSdt(userDTO.getSdt());
        user.setEmail(userDTO.getEmail());
        user.setDiaChi(userDTO.getDiaChi());
        user.setTrangThai(1);

        Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        return "redirect:/quantri/khachhang";
    }
    @GetMapping("/update-nguoidung")
    public String showEditUserForm(@RequestParam("id") int id, Model model) {
        User user = userRepository.findById(id).get();

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setFullName(user.getFullName());
        userDTO.setSdt(user.getSdt());
        userDTO.setEmail(user.getEmail());
        userDTO.setDiaChi(user.getDiaChi());
        int userRoleId = user.getRoles().iterator().next().getId();


        model.addAttribute("user", user);
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("role", roleRepository.findAll());
        model.addAttribute("userRoleId", userRoleId);
        return "admin/updateuser";
    }
    @PostMapping("/update-user")
    public String updateUser(@RequestParam("id") int id, @RequestParam("role") int roleId, @Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("role", roleRepository.findAll());
            model.addAttribute("userRoleId", roleId);
            return "admin/updateuser";
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        user.setSdt(userDTO.getSdt());
        user.setEmail(userDTO.getEmail());
        user.setDiaChi(userDTO.getDiaChi());

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + roleId));
        user.getRoles().clear();
        user.getRoles().add(role);

        userRepository.save(user);

        return "redirect:/quantri/khachhang";
    }

    @GetMapping("/delete-nguoidung")
    public String delete(@RequestParam("id") Integer id){
        User user = userRepository.findById(id).get();
        user.getRoles().clear();
        userRepository.save(user);
        userRepository.deleteById(id);
        return "redirect:/quantri/khachhang";
    }
}
