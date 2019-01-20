/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toko;

import java.sql.*;
import java.util.logging.*;

/**
 *
 * @author ashary
 */
public class Koneksi {
    /** 
     * Data base connection
     */
    private Connection con;
    
    /**
     * Data base statement
     */
    private Statement stat;
    
    /**
     * Data base Driver
     */
    private String Driver;
    /**
     * Data base connection account
     */
    private final String dbHost;
    private final String dbUser;
    private final String dbPass;
    private final String dbName;

   Koneksi(String dbHost, String dbUser, String dbPass, String dbName) {
        this.dbHost = dbHost;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
        this.dbName = dbName;
        
        this.makeConnection();
    }

    Koneksi() {
        this.dbHost = "localhost";
        this.dbUser = "root";
        this.dbPass = "12345678";
        this.dbName = "Toko";
        
        this.makeConnection();
    }
    
    private void makeConnection() {
        this.setDriver();
        
        try {
            this.con = DriverManager.getConnection(
                    this.getDbHost(),
                    this.dbUser,
                    this.dbPass);
            this.stat = this.con.createStatement();
            Logger.getLogger(Koneksi.class.getName()).log(
                    Level.INFO, 
                    "Koneksi Berhasil");
        } catch (SQLException ex) {
            Logger.getLogger(Koneksi.class.getName()).log(
                    Level.WARNING, 
                    "Tidak bisa melakukan koneksi ke database,"
                            + "periksa konfigurasi database tersebut",
                    ex);
        }
    }
    
    public Statement getStat() {
        return this.stat;
    }
    
    public Connection getCon() {
        return this.con;
    }
     
    public String getDbHost() {
        String host = "";
        host += "jdbc:";
        host += this.Driver;
        host += "://";
        host += this.dbHost;
        host += "/";
        host += this.dbName;
        return host;
    }
    
    private void setDriver() {
        if (isClass("org.mariadb.jdbc.Driver")) {
            this.Driver = "mariadb";
        } else if (isClass("com.mysql.jdbc.Driver")) {
            this.Driver = "mysql";
        } else {
            Logger.getLogger(Koneksi.class.getName()).log(
                    Level.WARNING, 
                    "Library MySQL/MariaDB tidak ditemukan !"
            );
        }
    }
    
    public boolean isClass(String className) {
        try {
            Class.forName(className);
            Logger.getLogger(Koneksi.class.getName()).log(
                    Level.INFO,
                    "Menggunakan class {0} untuk koneksi", 
                    className);
            return true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Koneksi.class.getName()).log(
                    Level.INFO, 
                    "Menggunakan class " + className + " untuk koneksi", 
                    ex);
            return false;
        }
    }
    
}
