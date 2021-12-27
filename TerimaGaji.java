import java.util.InputMismatchException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

import java.util.Scanner;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TerimaGaji extends Gaji{
    int iid;
    String inm;
    static Connection con;
    String url = "jdbc:mysql://localhost:3306/pegawai";
    Scanner i = new Scanner(System.in);

    int bonus;

    @Override
      //2011522012Rahmadina
    public long TotalGaji(int ijbt){
        if(ijbt==1){
            bonus = 1500000;
        }
        else if(ijbt == 2){
            bonus = 1000000;
        }
        else if(ijbt == 3){
            bonus = 500000;
        }
        else{
            bonus = 200000;
        }
        gajiTotal = GajiPokok(ijbt) + bonus;
        return gajiTotal;
    }

    public void selectData() throws SQLException{
        con = DriverManager.getConnection(url,"root","");
        String sql ="SELECT * FROM gaji";
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()){
            System.out.print("\nID\t  : ");
            System.out.print(result.getInt("NO_PEGAWAI"));
            System.out.print("\nNAMA\t  : ");
            System.out.print(result.getString("NAMA"));
            System.out.println("");
        }

    }

    public void insertData() throws SQLException{
        con = DriverManager.getConnection(url,"root","");
		System.out.println("\n===Tambah Data===");
		Scanner i = new Scanner (System.in);				
		try {
            System.out.print("ID      : ");
            int iid = Integer.parseInt(i.nextLine());
            System.out.print("Nama    : ");
            String inm = i.nextLine();
            String data = inm;
            String regex = "\\b[a-zA-Z]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(data);
            System.out.println("\nLevel Jabatan\n1. Direksi\n2. Manager\n3. Staff\n4. Customer Service");
            System.out.print("no : ");
            int ijbt = i.nextInt();
            System.out.print("\nLibur : ");
            int ilbr = i.nextInt();
	    	String sql = "INSERT INTO gaji (NO_PEGAWAI,NAMA,JABATAN,KEHADIRAN,GAJI_POKOK,POTONGAN,GAJI_TOTAL) VALUES ('"+NoPegawai(iid)+"','"+NamaPegawai(inm)+"','"+Jabatan(ijbt)+"','"+JumlahHariMasuk(ilbr)+"','"+GajiPokok(ijbt)+"','"+Potongan(ijbt, ilbr)+"','"+TotalGaji(ijbt)+"')";
            Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/pegawai","root","").createStatement();
            statement.execute(sql);
            System.out.println("Berhasil input data");
            i.close();
	    } 
        catch (SQLException e) {
	        System.err.println("Terjadi kesalahan input data");
	    } 
        catch (InputMismatchException e) {
	    	System.err.println("Inputlah dengan angka saja");
	   	}
  }

    public void updateData() throws SQLException{try {
        con = DriverManager.getConnection(url,"root","");
        selectData();
        System.out.print("ID data yang akan diedit : ");
        iid = Integer.parseInt(i.nextLine());
        String sql = "SELECT * FROM gaji WHERE NO_PEGAWAI = "+iid;
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(sql);
        
        if(result.next()){
            System.out.print("ID : "+result.getString("NO_PEGAWAI")+"\t ");
            System.out.print("Nama ["+result.getString("NAMA")+"]\t: ");
            inm = i.nextLine();    
            sql = "UPDATE gaji SET NAMA='"+inm+"' WHERE NO_PEGAWAI='"+iid+"'";
            //System.out.println(sql);
            if(statement.executeUpdate(sql) > 0){
                System.out.println("Berhasil memperbaharui data pegawai dengan ID "+iid);
            }
        }
        //System.out.println(sql);
        statement.close();
        i.close();        
    } 
    catch (SQLException e) {
        System.err.println("Terjadi kesalahan dalam mengedit data");
        System.err.println(e.getMessage());
    }
    }

    public void deleteData() throws SQLException{
        con = DriverManager.getConnection(url,"root","");
		try{
	        selectData();
	        System.out.print("ID yang akan dihapus : ");
	        iid= Integer.parseInt(i.nextLine());
	        
	        String sql = "DELETE FROM gaji WHERE NO_PEGAWAI = "+ iid;
	        Statement statement = con.createStatement();
	        //ResultSet result = statement.executeQuery(sql);
	        
	        if(statement.executeUpdate(sql) > 0){
	            System.out.println("Berhasil menghapus data pegawai dengan ID "+iid+")");
	        }
	   }catch(SQLException e){
	        System.out.println("Terjadi kesalahan dalam menghapus data barang");
	        }        
    }
}
