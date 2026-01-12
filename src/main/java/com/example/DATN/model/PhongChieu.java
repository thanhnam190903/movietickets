package com.example.DATN.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "phongchieu")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhongChieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "tenphong" ,length = 100)
    String tenPhong;
    @Column(name = "trangthai")
    boolean trangThai;
    @ManyToOne
    @JoinColumn(name = "id_rap")
    Rap rap;
    @ManyToOne
    @JoinColumn(name = "id_loaiphong")
    LoaiPhong loaiPhong;
    @OneToMany(mappedBy = "phongChieu")
    List<Ghe> gheList;
    @OneToMany(mappedBy = "phongChieu")
    List<CaChieu> caChieus;


}
