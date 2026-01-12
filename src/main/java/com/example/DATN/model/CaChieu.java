package com.example.DATN.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;

@Entity
@Table(name = "cachieu")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaChieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "giochieu")
    String gioChieu;
    @Column(name = "giokt")
    Time giokt;
    @ManyToOne
    @JoinColumn(name = "id_ngaychieu")
    NgayChieuS ngayChieu;
    @ManyToOne
    @JoinColumn(name = "id_phongchieu")
    PhongChieu phongChieu;
    @ManyToOne
    @JoinColumn(name = "id_phim")
    Phim phim;
    
}
