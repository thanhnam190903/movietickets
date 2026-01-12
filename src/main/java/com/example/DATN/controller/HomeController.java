package com.example.DATN.controller;

import com.example.DATN.model.HoaDon;
import com.example.DATN.model.Phim;
import com.example.DATN.model.User;
import com.example.DATN.repository.HoaDonRepository;
import com.example.DATN.repository.PhimRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.DATN.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/home")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class HomeController {
    PhimRepository phimRepository;
    UserRepository userRepository;
    HoaDonRepository hoaDonRepository;

    @GetMapping()
    public String showHome(Model model){
        LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        Date now = java.sql.Date.valueOf(localDate);
        Pageable pageable = PageRequest.of(0, 9, Sort.by(Sort.Direction.DESC, "ngayHieuLucTu"));
        List<Phim> phims = phimRepository.findByNgayHieuLucTuLessThanEqualAndNgayHieuLucDenGreaterThanEqualAndTrangThaiIsTrue(now, now, pageable).getContent();
        model.addAttribute("phims",phims);
        return "client/home";
    }
    @GetMapping({"/{tenphim}"})
    public String showChiTietPhim(Model model, @PathVariable("tenphim") String tenphim){
        Phim phim = phimRepository.findByTenPhim(tenphim);
        model.addAttribute("phim",phim);
        return "client/chitietphim";
    }
    @GetMapping("/phimdangchieu")
    public String showPhimDangChieu(Model model){
        LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        Date now = java.sql.Date.valueOf(localDate);
        List<Phim> phims = phimRepository.findByNgayHieuLucTuLessThanEqualAndNgayHieuLucDenGreaterThanEqualAndTrangThaiIsTrue(now,now);
        model.addAttribute("phims",phims);
        return "client/phimdagchieu";
    }
    @GetMapping("/phimsapchieu")
    public String showPhimSapChieu(Model model) {
        LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        Date currentDate = java.sql.Date.valueOf(localDate);
        List<Phim> phims = phimRepository.findAllComingSoonMovies(currentDate);
        model.addAttribute("phims", phims);
        return "client/phimsapchieu";
    }
    @GetMapping("/thongtincanhan")
    public String showThongTinCaNhan(Principal principal, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/showloginpage";
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        User user1 = userRepository.findById(user.getId()).orElse(null);
        model.addAttribute("user1", user1);

        return "client/thongtin";
    }

    @PostMapping("/submit-thongtin")
    public ResponseEntity<?> submitFormTT(@ModelAttribute("user1") User user,
                                          @RequestParam("password") String password,
                                          @RequestParam("passnew") String passnew,
                                          @RequestParam("restpass") String restpass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (passnew.isEmpty() && restpass.isEmpty()) {
            if (!passwordEncoder.matches(password, existingUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect current password");
            }
            existingUser = user.builder()
                    .fullName(user.getFullName())
                    .username(user.getUsername())
                    .sdt(user.getSdt())
                    .diaChi(user.getDiaChi())
                    .build();
            User savedUser = userRepository.saveAndFlush(existingUser);
            return ResponseEntity.ok(savedUser);
        } else {
            if (!passwordEncoder.matches(password, existingUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect current password");
            }
            // Kiểm tra mật khẩu mới và xác nhận mật khẩu mới có giống nhau không
            if (!passnew.equals(restpass)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password and confirmation do not match");
            }
            existingUser = user.builder()
                    .password(passwordEncoder.encode(passnew))
                    .fullName(user.getFullName())
                    .username(user.getUsername())
                    .sdt(user.getSdt())
                    .diaChi(user.getDiaChi())
                    .build();
            User savedUser = userRepository.saveAndFlush(existingUser);
            return ResponseEntity.ok(savedUser);
        }
    }
    @GetMapping("/lichsu")
    public String showLichSuDatVe(@RequestParam("id_hoadon") int id, Model model) {
        HoaDon hoaDon = hoaDonRepository.findById(id).orElse(null);
        model.addAttribute("hoadon", hoaDon);
        return "client/lichsuve";
    }
    @GetMapping("/lichsugiaodich")
    public String getHoaDonsForUser(Model model, Principal principal) {
        String username = principal.getName();
        List<HoaDon> hoaDons = hoaDonRepository.findByUser_Username(username);
        model.addAttribute("hoaDons", hoaDons);
        return "client/lichsu";
    }
    @GetMapping("/rap-3d")
    public String showRap3d(){
        return "client/rap3d";
    }
    @GetMapping("/lienhe")
    public String showLienHePage(){
        return "client/lienhe";
    }
}
