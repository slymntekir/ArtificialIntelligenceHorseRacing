package yapayzekason;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Egitim
{
    // öğrenme katsayısı
    double ogrenmeKatsayısı = 0.4;
    // eşik değeri
    double esikDegeri = 0.6;
    // ağırlıklar
    double[] agirliklar;
    double[] ilkAgirliklar;
    double[] sonAgirliklar = null;
    
    //ağırlıkların atanması
    public void agirliklariAyarla(int sayi)
    {
        agirliklar = new double[sayi];
        ilkAgirliklar = new double[sayi];
        sonAgirliklar = new double[sayi];
        for(int i=0;i<sayi;i++)
        {
            agirliklar[i] = 0.5;
        }
    }
    
    // hata,eğitim gibi hesaplamaların yapıldığı method
    public void hesapla(double veri[][])
    {
        int satir = veri.length;
        int sutun = veri[0].length - 1;
        while(true)
        {
            // eğitim algoritması
            for(int i=0;i<satir;i++)
            {
                System.out.println("Başlangıc agırlıkları : "+Arrays.toString(agirliklar));
                if(i==0)
                    System.arraycopy(agirliklar,0,ilkAgirliklar,0,sutun);
                double agirliklarToplami = 0;
                for(int j=0;j<sutun;j++)
                {
                    agirliklarToplami += veri[i][j]*agirliklar[j];
                }
                agirliklarToplami = sigmoid(agirliklarToplami) + esikDegeri;
                double hedefCıktı = veri[i][sutun];
                System.out.println("Hedef çıktı : "+hedefCıktı+
                        "\nHesaplanan çıktı : "+agirliklarToplami);
                double hata = hedefCıktı-agirliklarToplami;
                System.out.println("Hata : "+hata);
                for(int j=0;j<sutun;j++)
                {
                    agirliklar[j] = ogrenmeKatsayısı * hata * veri[i][j];
                }
                esikDegeri += ogrenmeKatsayısı * hata;
                System.out.println("Yeni ağırlıklar : "+Arrays.toString(agirliklar));
                System.out.println("---------------");
                if(i==satir)
                    System.arraycopy(agirliklar,0,sonAgirliklar,0,sutun);
            }
            //sistemin hatasını hiç bir zaman sıfıra eşitleyemediğim için
            //ilk ağırlık ile son ağırlık arasındaki farkın mutlak değerini 
            //olabildiğince minimuma indirmeyi amaçladım.
            double fark1 = sonAgirliklar[0]-ilkAgirliklar[0];
            double fark2 = sonAgirliklar[1]-ilkAgirliklar[1];
            double fark3 = sonAgirliklar[2]-ilkAgirliklar[2];
            double fark4 = sonAgirliklar[3]-ilkAgirliklar[3];
            if(fark1<0)
                fark1 *=-0.1;
            if(fark2<0)
                fark2 *=-0.1;
            if(fark3<0)
                fark3 *=-0.1;
            if(fark4<0)
                fark4 *=-0.1;
            
            if(String.valueOf(fark1).startsWith("0.00") ||
                    String.valueOf(fark2).startsWith("0.00") ||
                    String.valueOf(fark3).startsWith("0.00") ||
                    String.valueOf(fark4).startsWith("0.00"))
            {
                System.out.println("Final agirliklar : "+Arrays.toString(agirliklar));
                System.out.println();
                break;
            }
        } 
    }
    
    //Aktivasyon fonksiyonu
    public double sigmoid(double sayi)
    {
        return 1.0/(1+1/(Math.pow(Math.E,sayi)));
    } 
    

    //parametre olarak gelen yarış bilgilerini hesaplayarak hangi
    //atın birinci geleceğini(hata) ile bulup onun indisi döndüren method
    //(ekrana basmak için)
    public int sonucBul(double veri[][])
    {
        // matrisin satır ve sütunu
        int satir = veri.length;
        int sutun = veri[0].length;
        //sonucun tutulduğu list
        List<Double> sonuc = new ArrayList<>();
        for(int i=0;i<satir;i++)
        {
            //list'in doldurulması
            sonuc.add(agirliklar[0]*veri[i][0] + agirliklar[1]*veri[i][1] + 
                    agirliklar[2]*veri[i][2] + agirliklar[3]*veri[i][3]);
        }
        int index;
        // geçici değişken
        double temp;
        for(int i=0;i<satir;i++)
        {
            for(int j=0;j<satir; j++)
            {
                //sıralama algoritması
                if(sonuc.get(j)>sonuc.get(j+1))
                {
                    temp = sonuc.get(j);
                    sonuc.set(j,sonuc.get(j+1));
                    sonuc.set(j+1,temp);
                }//Önce gelen elaman bir sonrakinden büyükse ikisi yer değiştiriyor
            }//Dizinin ardışık elamanlarını karşılaştırmak için kullandığımız döngü
        }
        //en küçük elemanın indisini döndüren kısım
        index = sonuc.indexOf(sonuc.get(0));
        return index;
    }   
}