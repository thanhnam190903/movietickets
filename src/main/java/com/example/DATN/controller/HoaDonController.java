package com.example.DATN.controller;

import com.example.DATN.model.DatVe;
import com.example.DATN.model.HoaDon;
import com.example.DATN.model.Rap;
import com.example.DATN.model.User;
import com.example.DATN.repository.DatVeRepository;
import com.example.DATN.repository.HoaDonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quantri")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class HoaDonController {
   HoaDonRepository hoaDonRepository;
   DatVeRepository datVeRepository;

    @GetMapping("/hoadon")
    public String showListHoaDon(Model model, @Param("key") String key){
        List<HoaDon> hoaDons = hoaDonRepository.findAll();
        if (key != null){
                int number = Integer.parseInt(key);
                hoaDons = hoaDonRepository.searchHoaDonByKey(number);
                model.addAttribute("key",number);
        }
        model.addAttribute("hoadon",hoaDons);
        return "admin/hoadon";
    }
    @GetMapping("/update-hoadon")
    public String showUpdate(@RequestParam("id") Integer id,Model model){
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        model.addAttribute("hoadon",hoaDon);
        User user = hoaDonRepository.findUserByHoaDonId(id);
        model.addAttribute("user" ,user);
        DatVe datVe = hoaDonRepository.findDatVeByHoaDonId(id);
        model.addAttribute("datVe",datVe);
        Rap rap = datVeRepository.findRapByDatVeId(datVe.getId());
        model.addAttribute("rap",rap);
        return "admin/updatehoadon";
    }
    @PostMapping("/update-hd")
    public ResponseEntity<String> updateHoaDon(@ModelAttribute("hoadon") HoaDon hoaDon) {
        try {
            hoaDonRepository.saveAndFlush(hoaDon);
            return ResponseEntity.ok("Hóa đơn đã được cập nhật thành công.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra, vui lòng thử lại!");
        }
    }
    @GetMapping("/delete-hoadon")
    public String delete(@RequestParam("id") Integer id){
        hoaDonRepository.deleteById(id);
        return "redirect:/quantri/hoadon";
    }

    @GetMapping("/monthly-revenue")
    public String getMonthlyRevenue(@RequestParam(value = "rap", required = false) String rap, Model model) {
        if (rap != null) {
            List<Object[]> monthlyRevenue = hoaDonRepository.getMonthlyRevenueByRap(rap);
            model.addAttribute("revenueReport", monthlyRevenue);
            model.addAttribute("rap", rap);
        }
        return "admin/thongke";
    }
    @GetMapping("/thongkeve")
    public String getTicketStatistics(@RequestParam(value = "rap", required = false) String tenrap, Model model) {
        if (tenrap != null){
        List<Object[]> ticketStatistics = hoaDonRepository.getTicketStatisticsByRap(tenrap);
        model.addAttribute("ticketStatistics", ticketStatistics);
        model.addAttribute("rap", tenrap);
        }
        return "admin/thongkeluongve";
    }
}


