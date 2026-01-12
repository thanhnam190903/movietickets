package com.example.DATN.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "datve")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DatVe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "soluong")
    int soluong;
    @ManyToOne
    @JoinColumn(name = "id_cachieu")
    CaChieu caChieu;
    @ManyToOne
    @JoinColumn(name = "id_rap")
    Rap rap;
    @OneToMany(mappedBy = "datVe", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = false)
    List<Ghe> danhSachGhe;
    
    public double tinhTongGia() {
        return danhSachGhe.stream()
                .mapToDouble(ghe -> ghe.getLoaiGhe().getGiaGhe())
                .sum();
    }
}
