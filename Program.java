import java.util.InputMismatchException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 

public class Program {

    static Connection con;

    public static void main(String[] args){
        
    String url = "jdbc:mysql://localhost:3306/pegawai";
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(url,"root","");
		System.out.println("Driver Ready");

        Scanner i = new Scanner(System.in);
        Gaji p = new Gaji();
        TerimaGaji q = new TerimaGaji();
        Date date = new Date();
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);

        System.out.println("==================================");
        System.out.println("              MENU");
        System.out.println("==================================");
        System.out.println("1. Lihat Data");
        System.out.println("2. Tambah Data");
        System.out.println("3. Edit Data");
        System.out.println("4. Hapus Data");
        System.out.print("no : ");
        int menu = i.nextInt();

        switch (menu){
            case 1: q.selectData();
                break;
            case 2: q.insertData();
                break;
            case 3: q.updateData();
                break;
            case 4: q.deleteData();
                break;
            default: System.out.println("Menu tidak tersedia");
                break;
            }
        } 
    catch (ClassNotFoundException ex) {
        System.err.println("Driver eror");
        System.exit(0);
    }
    catch(SQLException e){
        System.err.println("Tidak berhasil Koneksi");
    }
    }
}
