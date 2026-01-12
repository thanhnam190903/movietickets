package com.example.DATN.controller;

import com.example.DATN.model.NgayChieuS;
import com.example.DATN.repository.NgayChieuRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quantri")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class NgayChieuController {
   NgayChieuRepository ngayChieuRepository;

    @GetMapping("/ngaychieu")
    public String showListNgayChieu(Model model, @Param("key") String key){
        List<NgayChieuS> ngayChieus = ngayChieuRepository.findAll();
        if (key != null){
            int number = Integer.parseInt(key);
            ngayChieus = ngayChieuRepository.searchNgayChieu(number);
            model.addAttribute("key",key);
        }
        model.addAttribute("ngayChieus",ngayChieus);
        return "admin/ngaychieu";
    }
    @GetMapping("/add-ngaychieu")
    public String showform(Model model){
        NgayChieuS ngayChieu = new NgayChieuS();
        model.addAttribute("ngaychieu",ngayChieu);
        return "admin/addngaychieu";
    }
    @PostMapping("/create-ngaych")
    public String insert(@ModelAttribute NgayChieuS ngayChieu, Model model) {
            ngayChieuRepository.save(ngayChieu);
            return "redirect:/quantri/ngaychieu";
    }
    @GetMapping("/update-ngaychieu")
    public String showFormUpdate(@RequestParam("id") Integer id , Model model){
        NgayChieuS ngayChieu = ngayChieuRepository.findById(id).get();
        model.addAttribute("ngaychieu",ngayChieu);
        return "admin/updatengaychieu";
    }
    @PostMapping("/update-ngaych")
    public String update(@ModelAttribute NgayChieuS ngayChieu , Model model){
            ngayChieuRepository.saveAndFlush(ngayChieu);
            return "redirect:/quantri/ngaychieu";
    }
    @GetMapping("/delete-ngaychieu")
    public String delete(@RequestParam("id") Integer id){
        ngayChieuRepository.deleteById(id);
        return "redirect:/quantri/ngaychieu";
    }
}
