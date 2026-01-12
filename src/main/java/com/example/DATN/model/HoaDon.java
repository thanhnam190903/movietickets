package com.example.DATN.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "hoadon")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "ngaylap")
    Date ngayLap;
    @Column(name = "giolap")
    Time gioLap;
    @Column(name = "tongtien")
    double tongTien;
    @Column(name = "trangthai")
    Boolean trangThai;
    @ManyToOne
    @JoinColumn(name = "id_user")
    User user;
    @ManyToOne
    @JoinColumn(name = "id_datve")
    DatVe datVe;
    @Column(name = "httt")
    String httt;

    
}
