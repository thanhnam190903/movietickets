package com.example.DATN.controller;

import com.example.DATN.model.Ghe;
import com.example.DATN.model.LoaiGhe;
import com.example.DATN.model.PhongChieu;
import com.example.DATN.repository.GheRepository;
import com.example.DATN.repository.LoaiGheRepository;
import com.example.DATN.repository.LoaiPhongRepository;
import com.example.DATN.repository.PhongChieuRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quantri")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class GheController {

    GheRepository gheRepository;
    LoaiGheRepository loaiGheRepository;
    PhongChieuRepository phongChieuRepository;

    @GetMapping("/ghe")
    public String showGhe(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String key) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Ghe> ghes;

        if (!key.isEmpty()) {
            try {
                int cot = Integer.parseInt(key);
                ghes = gheRepository.searchGheByInt(cot, pageable);
                model.addAttribute("key", cot);
            } catch (NumberFormatException e) {
                ghes = gheRepository.searchGheByString(key, pageable);
                model.addAttribute("key", key);
            }
        } else {
            ghes = gheRepository.findAllGhe(pageable);
        }

        model.addAttribute("ghes", ghes.getContent());
        model.addAttribute("totalPages", ghes.getTotalPages());
        model.addAttribute("currentPage", page);
        return "admin/ghe";
    }
    @GetMapping("/create-ghe")
    public String showCreateGhe(Model model){
        Ghe ghe = new Ghe();
        model.addAttribute("ghe",ghe);

        List<LoaiGhe> loaiGhes = loaiGheRepository.findAll();
        model.addAttribute("loaiGhe",loaiGhes);

        List<PhongChieu> phongChieus = phongChieuRepository.findAll();
        model.addAttribute("phongChieu",phongChieus);
        return "admin/addghe";
    }
    @PostMapping("/create-g")
    public String create(@ModelAttribute("ghe") Ghe ghe){
        ghe.setTrangThai(false);
        gheRepository.save(ghe);
        return "redirect:/quantri/ghe";
    }
    @GetMapping("/update-ghe")
    public String showUpdateGhe(@RequestParam("id") Integer id,Model model){
        Ghe ghe = gheRepository.findById(id).get();
        model.addAttribute("ghe",ghe);

        List<LoaiGhe> loaiGhes = loaiGheRepository.findAll();
        model.addAttribute("loaiGhe",loaiGhes);

        List<PhongChieu> phongChieus = phongChieuRepository.findAll();
        model.addAttribute("phongChieu",phongChieus);
        return "admin/updateghe";
    }
    @PostMapping("/update-g")
    public String update(@ModelAttribute("ghe") Ghe ghe){
        gheRepository.saveAndFlush(ghe);
        return "redirect:/quantri/ghe";
    }
    @GetMapping("/delete-ghe")
    public String delete(@RequestParam("id") Integer id){
        gheRepository.deleteById(id);
        return "redirect:/quantri/ghe";
    }

}
