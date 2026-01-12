package com.example.DATN.controller;

import com.example.DATN.model.LoaiGhe;
import com.example.DATN.repository.LoaiGheRepository;
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
public class LoaiGheController {

    LoaiGheRepository loaiGheRepository;

    @GetMapping("/loaighe")
    public String showLoaiGhe(Model model, @Param("key") String key){
        List<LoaiGhe> loaiGhes = loaiGheRepository.findAll();
        if (key != null){
            try {
                int number = Integer.parseInt(key);
                loaiGhes = loaiGheRepository.searchLoaiGheByInt(number);
                model.addAttribute("key",key);
            } catch (NumberFormatException e) {
                loaiGhes = loaiGheRepository.searchLoaiGhe(key);
                model.addAttribute("key",key);
            }
        }
        model.addAttribute("loaiGhes",loaiGhes);
        return "admin/loaighe";
    }

    @GetMapping("/add-loaighe")
    public String showCreateLGhe(Model model){
        LoaiGhe loaiGhe = new LoaiGhe();
        model.addAttribute("loaighe",loaiGhe);
        return "admin/addloaighe";
    }
    @PostMapping("/create-lg")
    public String insert(@ModelAttribute("loaighe") LoaiGhe loaiGhe ,Model model){
        LoaiGhe loaiGhe1 = loaiGheRepository.findByTenLoai(loaiGhe.getTenLoai());
        if (loaiGhe1 == null){
            loaiGheRepository.save(loaiGhe);
            return "redirect:/quantri/loaighe";
        }else{
            model.addAttribute("loi","Bạn đã nhập trùng tên loại ghế đã có! Hãy nhập lại ");
            return "admin/addloaighe";
        }
    }
    @GetMapping("/update-loaighe")
    public String showUpdateLG(@RequestParam("id") Integer id ,Model model){
        LoaiGhe loaiGhe = loaiGheRepository.findById(id).get();
        model.addAttribute("loaighe",loaiGhe);
        return "admin/updateloaighe";
    }
    @PostMapping("/update-lg")
    public String update(@ModelAttribute("loaighe") LoaiGhe loaiGhe){
        loaiGheRepository.saveAndFlush(loaiGhe);
        return "redirect:/quantri/loaighe";
    }
    @GetMapping("/delete-loaighe")
    public String delete(@RequestParam("id") Integer id){
        loaiGheRepository.deleteById(id);
        return "redirect:/quantri/loaighe";
    }
}
