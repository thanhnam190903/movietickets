package com.example.DATN.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Collection;

@Entity
@Table(name = "phim")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Phim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "tenphim")
    String tenPhim;
    @Column(name = "daodien" , length = 100)
    String daoDien;
    @Column(name = "dienvien")
    String dienVien;
    @Column(name = "noidung",columnDefinition = "text")
    String noiDung;
    @Column(name = "thoiluong")
    String thoiLuong;
    @Column(name = "ngonngu")
    String ngonNghu;
    @Column(name = "video")
    String video;
    @Column(name = "anh")
    String anh;
    @Column(name = "ngayhieuluctu")
    Date ngayHieuLucTu;
    @Column(name = "ngayhieulucden")
    Date ngayHieuLucDen;
    @Column(name = "trangthai")
    boolean trangThai;
    @Column(name = "theloai")
    String theLoai;
}
