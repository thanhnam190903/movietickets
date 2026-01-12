package com.example.DATN.controller;

import com.example.DATN.Service.CustomTimeEditor;
import com.example.DATN.model.CaChieu;
import com.example.DATN.model.NgayChieuS;
import com.example.DATN.model.Phim;
import com.example.DATN.model.PhongChieu;
import com.example.DATN.repository.CaChieuRepository;
import com.example.DATN.repository.NgayChieuRepository;
import com.example.DATN.repository.PhimRepository;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@Controller
@RequestMapping("/quantri")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class CaChieuController {
    CaChieuRepository caChieuRepository;
    PhongChieuRepository phongChieuRepository;
    PhimRepository phimRepository;
    NgayChieuRepository ngayChieuRepository;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Time.class, new CustomTimeEditor());
    }
    @GetMapping("/cachieu")
    public String showCaChieu(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String key){
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<CaChieu> caChieus;
        if (key != null){
            caChieus = caChieuRepository.searchCaChieu(key,pageable);
            model.addAttribute("key",key);
        }else {
            caChieus = caChieuRepository.findAllOrderByIdDesc(pageable);
        }
        model.addAttribute("caChieus",caChieus);
        model.addAttribute("totalPages", caChieus.getTotalPages());
        model.addAttribute("currentPage", page);
        return "admin/cachieu";
    }
    @GetMapping("/add-cachieu")
    public String addCaChieu(Model model){
        CaChieu caChieu = new CaChieu();
        model.addAttribute("cachieu",caChieu);
        List<NgayChieuS> ngayChieus = ngayChieuRepository.findAll();
        model.addAttribute("ngayChieus",ngayChieus);
        List<PhongChieu> phongChieus = phongChieuRepository.findAll();
        model.addAttribute("phongChieus",phongChieus);
        List<Phim> phims = phimRepository.findAll();
        model.addAttribute("phims",phims);
        return "admin/addcachieu";
    }
    @PostMapping("/add-cach")
    public String insert(@ModelAttribute("cachieu") CaChieu caChieu){
        System.out.println("Giờ kết thúc nhận được: " + caChieu.getGiokt());
        caChieuRepository.save(caChieu);
        return "redirect:/quantri/cachieu";
    }
    @GetMapping("/update-cachieu")
    public String updateCaChieu(@RequestParam("id") Integer id , Model model){
        CaChieu caChieu = caChieuRepository.findById(id).get();
        model.addAttribute("cachieu",caChieu);
        List<NgayChieuS> ngayChieus = ngayChieuRepository.findAll();
        model.addAttribute("ngayChieus",ngayChieus);
        List<PhongChieu> phongChieus = phongChieuRepository.findAll();
        model.addAttribute("phongChieus",phongChieus);
        List<Phim> phims = phimRepository.findAll();
        model.addAttribute("phims",phims);
        return "admin/updatecachieu";
    }
    @PostMapping("/update-cach")
    public String update(@ModelAttribute("cachieu") CaChieu caChieu){
        caChieuRepository.saveAndFlush(caChieu);
        return "redirect:/quantri/cachieu";
    }
    @GetMapping("/delete-cachieu")
    public String delete(@RequestParam("id") Integer id){
        caChieuRepository.deleteById(id);
        return "redirect:/quantri/cachieu";
    }

}
