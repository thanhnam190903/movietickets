package com.example.DATN.controller;

import com.example.DATN.model.LoaiPhong;
import com.example.DATN.repository.LoaiPhongRepository;
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
public class LoaiPhongController {

   LoaiPhongRepository loaiPhongRepository;

    @GetMapping("/loaiphong")
    public String showLoaiPhong(Model model, @Param("key") String key){
        List<LoaiPhong> loaiPhongs = loaiPhongRepository.findAll();
        if (key != null){
            loaiPhongs = loaiPhongRepository.searchLoaiPhong(key);
            model.addAttribute("key",key);
        }
        model.addAttribute("loaiPhongs",loaiPhongs);
        return "admin/loaiphong";
    }

    @GetMapping("/add-loaiphong")
    public String addLoaiPhong(Model model){
        LoaiPhong loaiPhong = new LoaiPhong();
        model.addAttribute("loaiphong",loaiPhong);
        return "admin/addloaiphong";
    }
    @PostMapping("/create-loaiphong")
    public String create(@ModelAttribute("loaiphong") LoaiPhong loaiPhong, Model model){
        LoaiPhong loaiPhong1 = loaiPhongRepository.findByTenLoaiPhong(loaiPhong.getTenLoaiPhong());
        if (loaiPhong1 == null){
            loaiPhongRepository.save(loaiPhong);
            return "redirect:/quantri/loaiphong";
        }else{
            model.addAttribute("loi","Bạn đã nhập trùng tên loại phòng đã có sẵn! Hãy nhập lại");
            return "admin/addloaiphong";
        }
    }
    @GetMapping("/update-loaiphong")
    public String showUpdateLoaiPhong(@RequestParam("id") Integer id,Model model){
        LoaiPhong loaiPhong = loaiPhongRepository.findById(id).get();
        model.addAttribute("loaiphong",loaiPhong);
        return "admin/updateloaiphong";
    }
    @PostMapping("/update-lp")
    public String update(@ModelAttribute("loaiphong") LoaiPhong loaiPhong){
            loaiPhongRepository.saveAndFlush(loaiPhong);
            return "redirect:/quantri/loaiphong";

    }
    @GetMapping("delete-loaiphong")
    public String delete(@RequestParam("id") Integer id){
        loaiPhongRepository.deleteById(id);
        return "redirect:/quantri/loaiphong";
    }

}
