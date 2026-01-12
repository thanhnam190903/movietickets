package com.example.DATN.controller;

import com.example.DATN.model.*;
import com.example.DATN.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Controller
@RequestMapping("/home")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class DatVeController {
    DatVeRepository datVeRepository;
    RapRepository rapRepository;
    CaChieuRepository caChieuRepository;
    GheRepository gheRepository;
    PhimRepository phimRepository;
    

    @GetMapping("/datve")
    public String datve(Model model, @RequestParam("id") Integer id_Phim) {
        Phim phim = phimRepository.findById(id_Phim).orElse(null);

        if (phim != null) {
            model.addAttribute("phim", phim);
        } else {
            return "error-page";
        }

        LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        Date today = java.sql.Date.valueOf(localDate);
        System.out.println("Today's date: " + today);

        List<Date> dates = caChieuRepository.findAllNgayChieuByIdPhim(id_Phim, today);
        System.out.println("Dates fetched from DB: " + dates);
        model.addAttribute("dates", dates);

        List<String> raps = rapRepository.findDistinctDiaChi();
        model.addAttribute("raps", raps);

        List<String> loaiPhong = caChieuRepository.findLoaiPhongNamesByPhimId(id_Phim);
        model.addAttribute("loaiPhong", loaiPhong);

        return "client/datve";
    }

    @GetMapping("/datve/loadgiochieu")
    @ResponseBody
    public List<Map<String, Object>> loadGioChieu(@RequestParam("idPhim") int idPhim,
                                                  @RequestParam("ngaychieu") String ngayChieu,
                                                  @RequestParam("diachi") String diaChi,
                                                  @RequestParam("loaiphong") String loaiPhong) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = null;

        try {
            java.util.Date utilDate = formatter.parse(ngayChieu);
            sqlDate = new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<CaChieu> caChieus = caChieuRepository.findGioChieuByPhimNgayChieuDiaChiLoaiPhong(idPhim, sqlDate, diaChi, loaiPhong);
        System.out.println("CaChieus: " + caChieus);
        List<Map<String, Object>> response = new ArrayList<>();
        for (CaChieu caChieu : caChieus) {
            Map<String, Object> map = new HashMap<>();
            map.put("gioChieu", caChieu.getGioChieu());
            map.put("tenRap", caChieu.getPhongChieu().getRap().getTenRap());
            response.add(map);
            System.out.println(map);
        }

        return response;
    }

    @PostMapping("/chonghe")
    public String chonGhe(@RequestParam("phimId") int phimId,
                          @RequestParam("ngayChieu") String ngayChieu,
                          @RequestParam("diaChi") String diaChi,
                          @RequestParam("loaiPhong") String loaiPhong,
                          @RequestParam("gioChieu") String gioChieu,
                          Model model) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate;
        try {
            java.util.Date utilDate = formatter.parse(ngayChieu);
            sqlDate = new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "error-page";
        }

        List<CaChieu> caChieus = caChieuRepository.findGioChieuByPhimNgayChieuDiaChiLoaiPhong(phimId, sqlDate, diaChi, loaiPhong);
        if (caChieus.isEmpty()) {
            return "error-page";
        }

        CaChieu caChieu = caChieus.stream()
                .filter(cc -> cc.getGioChieu().equals(gioChieu))
                .findFirst()
                .orElse(null);
        if (caChieu == null) {
            return "error-page";
        }
        List<Ghe> gheA = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"A");
        List<Ghe> gheB = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"B");
        List<Ghe> gheC = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"C");
        List<Ghe> gheD = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"D");
        List<Ghe> gheE = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"E");
        List<Ghe> gheF = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"F");
        List<Ghe> gheG = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"G");
        List<Ghe> gheH = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"H");
        List<Ghe> gheI = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"I");
        List<Ghe> gheJ = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"J");
        List<Ghe> gheK = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"K");
        List<Ghe> gheL = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"L");
        List<Ghe> gheM = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"M");
        List<Ghe> gheN = gheRepository.findByPhongChieuIdAndHang(caChieu.getPhongChieu().getId(),"N");
        List<Ghe> gheDaDat = gheRepository.findByPhongChieuIdAndTrangThai(caChieu.getPhongChieu().getId(), true);

        Map<Integer, Boolean> gheDaDatMap = new HashMap<>();
        for (Ghe ghe : gheDaDat) {
            gheDaDatMap.put(ghe.getId(), true);
        }

        model.addAttribute("phim", phimRepository.findById(phimId).orElse(null));
        model.addAttribute("ngayChieu", ngayChieu);
        model.addAttribute("diaChi", diaChi);
        model.addAttribute("loaiPhong", loaiPhong);
        model.addAttribute("gioChieu", gioChieu);
        model.addAttribute("tenPhong", caChieu.getPhongChieu().getTenPhong());
        model.addAttribute("gheA", gheA);
        model.addAttribute("gheB", gheB);
        model.addAttribute("gheC", gheC);
        model.addAttribute("gheD", gheD);
        model.addAttribute("gheE", gheE);
        model.addAttribute("gheF", gheF);
        model.addAttribute("gheG", gheG);
        model.addAttribute("gheH", gheH);
        model.addAttribute("gheI", gheI);
        model.addAttribute("gheJ", gheJ);
        model.addAttribute("gheK", gheK);
        model.addAttribute("gheL", gheL);
        model.addAttribute("gheM", gheM);
        model.addAttribute("gheN", gheN);
        model.addAttribute("gheDaDat", gheDaDatMap);

        return "client/chonghe";
    }

    @PostMapping("/confirmBooking")
    @Transactional
    public ResponseEntity<Map<String, Object>> confirmBooking(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();

        try {
            int phimId = Integer.parseInt((String) requestBody.get("phimId"));
            String ngayChieuString = (String) requestBody.get("ngayChieu");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date sqlDate = new Date(formatter.parse(ngayChieuString).getTime());

            List<Integer> selectedSeats = (List<Integer>) requestBody.get("selectedSeats");
            String diaChi = (String) requestBody.get("diaChi");
            String loaiPhong = (String) requestBody.get("loaiPhong");
            String gioChieu = (String) requestBody.get("gioChieu");
            String tenPhong = (String) requestBody.get("tenPhong");

            if (selectedSeats.size() > 5) {
                response.put("success", false);
                response.put("message", "Bạn không thể chọn quá 5 ghế!");
                return ResponseEntity.badRequest().body(response);
            }

            List<CaChieu> caChieuList = caChieuRepository.findCaChieuByPhimNgayChieuDiaChiLoaiPhong(phimId, sqlDate, diaChi, loaiPhong, gioChieu);
            if (caChieuList.isEmpty()) {
                response.put("success", false);
                response.put("message", "Không tìm thấy ca chiếu!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CaChieu caChieu = caChieuList.get(0);
            Rap rap = caChieu.getPhongChieu().getRap();
            List<Ghe> gheList = new ArrayList<>();

            for (Integer seatId : selectedSeats) {
                Ghe ghe = gheRepository.findById(seatId).orElseThrow(() -> new IllegalArgumentException("Ghế không hợp lệ"));
                if (ghe.isTrangThai()) {
                    response.put("success", false);
                    response.put("message", "Ghế đã được đặt! Vui lòng chọn ghế khác.");
                    return ResponseEntity.badRequest().body(response);
                }
                gheList.add(ghe);
            }

            String firstSeatType = gheList.get(0).getLoaiGhe().getTenLoai();
            for (Ghe ghe : gheList) {
                if (!ghe.getLoaiGhe().getTenLoai().equals(firstSeatType)) {
                    response.put("success", false);
                    response.put("message", "Chỉ được chọn ghế có loại ghế giống nhau!");
                    return ResponseEntity.badRequest().body(response);
                }
            }

            DatVe datVe = new DatVe();
            datVe.setSoluong(selectedSeats.size());
            datVe.setCaChieu(caChieu);
            datVe.setRap(rap);

            if (datVe.getDanhSachGhe() == null) {
                datVe.setDanhSachGhe(new ArrayList<>());
            }

            for (Ghe ghe : gheList) {
                ghe.setTrangThai(true);
                ghe.setDatVe(datVe);
                datVe.getDanhSachGhe().add(ghe);
            }

            datVeRepository.save(datVe);

            response.put("success", true);
            response.put("message", "Đặt vé thành công!");
            //response.put("redirectUrl", paymentUrl);
            response.put("datVeId", datVe.getId());
            return ResponseEntity.ok(response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Dữ liệu đầu vào không hợp lệ!");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Có lỗi xảy ra. Vui lòng thử lại!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}


