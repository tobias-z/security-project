package com.insession.securityproject.infrastructure.entities;

import com.insession.securityproject.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "imagefile")
    private String imageFile;

    //@OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "user")
    @Column(name = "username")
    private String userName;

    public ImageEntity() {
    }

    public ImageEntity(String imageFile, String userName) {
        this.imageFile = imageFile;
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
