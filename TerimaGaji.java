import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.InputMismatchException;
import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class TerimaGaji extends Gaji{
    int iid,ijbt,ilbr,edit;
    String inm;
    static Connection con;
    String url = "jdbc:mysql://localhost:3306/pegawai";
    Scanner i = new Scanner(System.in);
    int bonus;
    private String nextLine = i.nextLine();

    @Override
      //2011522012Rahmadina
    public long TotalGaji(int ijbt, int ilbr){
        if(ilbr==0){
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
        }
        else if(ilbr>0){
            gajiTotal = GajiPokok(ijbt) + (long) ((-1) * Potongan(ijbt, ilbr));
        }
        return gajiTotal;
    }

    public void selectData() throws SQLException{
        try{
            con = DriverManager.getConnection(url,"root","");
            String sql ="SELECT * FROM gaji";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            System.out.println("\n\n---DAFTAR DATA GAJI PEGAWAI---");
            while (result.next()){
                System.out.print("\nID        : ");
                System.out.print(result.getInt("NO_PEGAWAI"));
                System.out.print("\nNama      : ");                    System.out.print(result.getString("NAMA"));
                System.out.print("\nJabatan   : ");
                System.out.print(result.getString("JABATAN"));
                System.out.print("\nKehadiran : ");
                System.out.print(result.getInt("KEHADIRAN")+" hari");
                System.out.print("\nGaji Pokok: ");                    System.out.print(result.getInt("GAJI_POKOK"));
                System.out.print("\nDenda     : ");
                System.out.print(result.getInt("POTONGAN"));
                System.out.print("\nGaji Total: ");
                System.out.print(result.getInt("GAJI_TOTAL"));
                System.out.println("");
                }
        }
        catch (SQLException e) {
	        System.err.println("Terjadi kesalahan input data");
	    } 
    }

    public void insertData() throws SQLException{
        con = DriverManager.getConnection(url,"root","");
		System.out.println("\n---TAMBAH DATA---");
		Scanner i = new Scanner (System.in);				
		try {
            System.out.print("ID      : ");
            int iid = Integer.parseInt(i.nextLine());
            System.out.print("Nama    : ");
            String inm = i.nextLine();
            System.out.println("\nLevel Jabatan\n1. Direksi\n2. Manager\n3. Staff\n4. Customer Service");
            System.out.print("no : ");
            int ijbt = i.nextInt();
            System.out.print("\nLibur : ");
            int ilbr = i.nextInt();
	    	String sql = "INSERT INTO gaji (NO_PEGAWAI,NAMA,JABATAN,KEHADIRAN,GAJI_POKOK,POTONGAN,GAJI_TOTAL) VALUES ('"+NoPegawai(iid)+"','"+NamaPegawai(inm)+"','"+Jabatan(ijbt)+"','"+JumlahHariMasuk(ilbr)+"','"+GajiPokok(ijbt)+"','"+Potongan(ijbt, ilbr)+"','"+TotalGaji(ijbt, ilbr)+"')";
            //201522012RAHMADINA
            Statement statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/pegawai","root","").createStatement();
            statement.execute(sql);
            System.out.println("Data dengan ID "+iid+" berhasil ditambahkan!");
            i.nextLine();
	    } 
        catch (SQLException e) {
	        System.err.println("Data dengan ID tersebut sudah ada TvT");
	    } 
        catch (InputMismatchException e) {
	    	System.err.println("Inputlah dengan angka saja");
	   	}
  }
 
    public void updateNama(int iid) throws SQLException{
        try {
            String sql = "SELECT * FROM gaji WHERE NO_PEGAWAI = " +iid;
            con = DriverManager.getConnection(url,"root","");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);

           if (result.next()){
                System.out.println("\n\nNama sebelumnya : "+result.getString("NAMA"));
                System.out.print("Nama terbaru : ");
                inm = i.next();
                sql = "UPDATE gaji SET NAMA='"+NamaPegawai(inm)+"' WHERE NO_PEGAWAI='"+iid+"'";
                if(statement.executeUpdate(sql) > 0){
                    System.out.println("Berhasil memperbaharui nama karyawan dengan ID "+iid+" menjadi "+NamaPegawai(inm));
                }
            }
            statement.close();  
            i.nextLine();      
        } 
        catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }
    }
    public void updateJabatan(int iid){
        try{
            String sql = "SELECT * FROM gaji WHERE NO_PEGAWAI = " +iid;
            con = DriverManager.getConnection(url,"root","");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
           if (result.next()){
                System.out.println("Jabatan sebelumnya : "+result.getString("JABATAN"));
                System.out.println("\nLevel Jabatan\n1. Direksi\n2. Manager\n3. Staff\n4. Customer Service");
                System.out.print("Jabatan terbaru (angka) : ");
                int ijbt = i.nextInt();
                ilbr = (30-result.getInt("KEHADIRAN"));
                sql = "UPDATE gaji SET JABATAN='"+Jabatan(ijbt)+"', GAJI_POKOK='"+GajiPokok(ijbt)+"', POTONGAN='"+Potongan(ijbt, ilbr)+"', GAJI_TOTAL='"+TotalGaji(ijbt, ilbr)+"' WHERE NO_PEGAWAI='"+iid+"'";
                if(statement.executeUpdate(sql) > 0){
                    System.out.println("Berhasil mengedit jabatan pegawai dengan ID "+NoPegawai(iid)+" menjadi "+Jabatan(ijbt)+"");
                }
            }
            i.nextLine();
            statement.close(); 
        }
        catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }
    }
    public void updateCuti(int iid) throws SQLException{
        try{
            String sql = "SELECT * FROM gaji WHERE NO_PEGAWAI = " +iid;
            con = DriverManager.getConnection(url,"root","");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
           if (result.next()){
                System.out.println("\nKehadiran sebelumnya : "+result.getInt("KEHADIRAN")+" hari (libur "+(30-result.getInt("KEHADIRAN"))+")");
                System.out.print("Jumlah libur terbaru (angka) : ");
                ilbr = i.nextInt();
                if(result.getString("JABATAN")=="DIREKSI"){
                    ijbt=1;
                }
                else if(result.getString("JABATAN")=="MANAGER"){
                    ijbt=2;
                }
                else if(result.getString("JABATAN")=="STAF"){
                    ijbt=3;
                }
                else if(result.getString("JABATAN")=="CS"){
                    ijbt=4;
                }
                sql = "UPDATE gaji SET KEHADIRAN='"+JumlahHariMasuk(ilbr)+"', POTONGAN='"+Potongan(ijbt, ilbr)+"', GAJI_TOTAL='"+TotalGaji(ijbt, ilbr)+"' WHERE NO_PEGAWAI='"+NoPegawai(iid)+"'";
                if(statement.executeUpdate(sql) > 0){
                    System.out.println("\nBerhasil mengedit kehadiran pegawai dengan ID "+NoPegawai(iid)+" menjadi "+JumlahHariMasuk(ilbr)+" hari");
                }
            }
            i.nextLine();
            statement.close(); 
        }
        catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }       
    }

    public void updateData() throws SQLException{
        try {
		System.out.println("\n\n---EDIT DATA---");
        System.out.println("Daftar Edit");
        System.out.println("1. Nama");
        System.out.println("2. Jabatan");
        System.out.println("3. Kehadiran");
        System.out.print("Jenis data yang diedit (angka) : ");
        edit = Integer.parseInt(i.nextLine());
        System.out.print("ID data yang ingin di edit : ");
        iid = i.nextInt();
        selectData();
        if(edit==1)
            { 
               updateNama(iid); 
            }
        else if(edit==2)
            {
                updateJabatan(iid);
            }
        else if(edit==3)
            {
                updateCuti(iid);
            }
            i.nextLine();
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
	        System.out.print("\nID yang akan dihapus : ");
	        iid= Integer.parseInt(i.nextLine());
	        
	        String sql = "DELETE FROM gaji WHERE NO_PEGAWAI = "+ iid;
	        Statement statement = con.createStatement();
	        //ResultSet result = statement.executeQuery(sql);
	        
	        if(statement.executeUpdate(sql) > 0){
	            System.out.println("Berhasil menghapus data pegawai dengan ID "+NoPegawai(iid));
	        }
	   }catch(SQLException e){
	        System.out.println("Terjadi kesalahan dalam menghapus data barang");
	        }        
    }

    public void searchData() throws SQLException{
        con = DriverManager.getConnection(url,"root","");
		System.out.println("\n===Cari Data===");
        System.out.println("1. Berdasarkan ID");
        System.out.println("2. Berdasarkan Nama");
        System.out.print("\npilihan (angka) : ");
        int menu = i.nextInt();
        System.out.print("\nKeyword yang dicari : ");
        String cari = i.next();
        if(menu==1){
            searchID(cari);
        }
        else if(menu==2){
            searchNama(cari);
        }
        else{
            System.out.println("Data yang dicar hanya bisa berdasarkan ID atau Nama pegawai");
        }
    }
    public void searchNama(String cari) throws SQLException{
        try {
            String sql = "SELECT * FROM gaji WHERE NAMA LIKE '%"+cari+"%'";
            con = DriverManager.getConnection(url,"root","");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);

           while (result.next()){
                System.out.println("\nID : "+result.getString("NO_PEGAWAI"));
                System.out.println("Nama : "+result.getString("NAMA"));
                System.out.println("Jabatan : "+result.getString("JABATAN"));
                System.out.println("Kehadiran : "+result.getString("KEHADIRAN")+" hari");
                System.out.println("Gaji Pokok : "+result.getString("GAJI_POKOK"));
                System.out.println("Potongan : "+result.getString("POTONGAN"));
                System.out.println("Gaji Total : "+result.getString("GAJI_TOTAL"));
            }
            statement.close();        
        } 
        catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mencari data");
            System.err.println(e.getMessage());
        }
    }
    public void searchID(String cari) throws SQLException{
        try {
            String sql = "SELECT * FROM gaji WHERE NO_PEGAWAI LIKE '%"+cari+"%'";
            con = DriverManager.getConnection(url,"root","");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
           if (result.next()){
                System.out.println("\nID : "+result.getString("NO_PEGAWAI"));
                System.out.println("Nama : "+result.getString("NAMA"));
                System.out.println("Jabatan : "+result.getString("JABATAN"));
                System.out.println("Kehadiran : "+result.getString("KEHADIRAN")+" hari");
                System.out.println("Gaji Pokok : "+result.getString("GAJI_POKOK"));
                System.out.println("Potongan : "+result.getString("POTONGAN"));
                System.out.println("Gaji Total : "+result.getString("GAJI_TOTAL"));
            }
            statement.close();        
        } 
        catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mencari data");
            System.err.println(e.getMessage());
        }
    }
}
