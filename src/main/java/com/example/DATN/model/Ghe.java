package com.example.DATN.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "ghe")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ghe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "hang",length = 1)
    String hang;
    @Column(name = "cot")
    int cot;
    @Column(name = "soghe")
    String soGhe;
    @Column(name = "trangthai")
    boolean trangThai;
    @ManyToOne
    @JoinColumn(name = "id_loaighe")
    LoaiGhe loaiGhe;
    @ManyToOne
    @JoinColumn(name = "id_phongchieu")
    PhongChieu phongChieu;
    @ManyToOne
    @JoinColumn(name = "datve_id")
    DatVe datVe;

    
}
