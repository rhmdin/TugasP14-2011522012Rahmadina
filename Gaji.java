
public class Gaji implements PTABC{

    int id;
    String nama;
    String jabatan;
    long gajiPokok;
    int hari;
    long gajiTotal;
    long denda;

    public Gaji(){
    }

    public int NoPegawai(int iid){
      id = iid;
      return id;
    }
    public String NamaPegawai(String inm){
        nama = inm;
        return nama.toUpperCase();
    }
    public String Jabatan(int ijbt){
        switch(ijbt){
            case 1:
                jabatan = "direksi";
                break;
            case 2:
                jabatan = "manager";
                break;
            case 3:
                jabatan = "staf";
                break;
            case 4:
                jabatan = "cs";
                break;
            default:
                jabatan = "Inputkan antara nomor 1 sampai 4";
                break;
        }
        return jabatan.toUpperCase();
    }
    //2011522012Rahmadina
    public int JumlahHariMasuk(int ilbr){
        hari = 30 - ilbr;
        return hari;
    }

    public long GajiPokok(int ijbt){
        if(ijbt==1){
            gajiPokok = 15000000;
        }
        else if(ijbt == 2){
            gajiPokok = 10000000;
        }
        else if(ijbt == 3){
            gajiPokok = 5000000;
        }
        else{
            gajiPokok = 2000000;
        }
        return gajiPokok;
    }
    public long Potongan(int ijbt, int ilbr){
        if(ijbt==1){
            denda = ilbr * 250000;
        }
        else if(ijbt==2){
            denda = ilbr * 150000;
        }
        else if(ijbt==3){
            denda = ilbr * 50000;
        }
        else{
            denda = ilbr * 15000;
        }
        return denda;
    }
    public long TotalGaji(int ijbt, int ilbr){
    if(ilbr>0){
        gajiTotal = gajiPokok + (long) ((-1) * denda);
    }
        return gajiTotal;
    }

    public int length(){
        return nama.length();
    }

}
