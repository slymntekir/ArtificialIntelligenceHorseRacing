package yapayzekason;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class FXMLDocumentController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private ComboBox<Integer> cmbatyas1;
    @FXML
    private ComboBox<Integer> cmbatkilo1;
    @FXML
    private ComboBox<String> cmbgaloran1;
    @FXML
    private ComboBox<Integer> cmbjkykilo1;
    @FXML
    private ComboBox<Integer> cmbatyas2;
    @FXML
    private ComboBox<Integer> cmbatkilo2;
    @FXML
    private ComboBox<String> cmbgaloran2;
    @FXML
    private ComboBox<Integer> cmbjkykilo2;
    @FXML
    private ComboBox<Integer> cmbatyas3;
    @FXML
    private ComboBox<Integer> cmbatkilo3;
    @FXML
    private ComboBox<String> cmbgaloran3;
    @FXML
    private ComboBox<Integer> cmbjkykilo3;
    @FXML
    private ComboBox<Integer> cmbatyas4;
    @FXML
    private ComboBox<Integer> cmbatkilo4;
    @FXML
    private ComboBox<String> cmbgaloran4;
    @FXML
    private ComboBox<Integer> cmbjkykilo4;
    @FXML
    private ComboBox<Integer> cmbatyas5;
    @FXML
    private ComboBox<Integer> cmbatkilo5;
    @FXML
    private ComboBox<String> cmbgaloran5;
    @FXML
    private ComboBox<Integer> cmbjkykilo5;
    @FXML
    private ComboBox<Integer> cmbatyas6;
    @FXML
    private ComboBox<Integer> cmbatkilo6;
    @FXML
    private ComboBox<String> cmbgaloran6;
    @FXML
    private ComboBox<Integer> cmbjkykilo6;
    @FXML
    private Label lbl_sonuc;
    
    ObservableList<Integer> atYas = FXCollections.observableArrayList(2,3,4,5,6,7);

    double dizi[][];    
    Egitim e = new Egitim();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            dosyaOKu();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for(int i=1;i<=6;i++)
        {
            ComboBox<Integer> cmb = (ComboBox<Integer>) nesneBul("cmbatyas"+i);
            cmb.setItems(atYas);
        }
        List<Integer> atkilo = new ArrayList<>();
        for(int i=150;i<301;i++)
        {
            atkilo.add(i);
        }
        ObservableList<Integer> atKilo = FXCollections.observableArrayList(atkilo);
        for(int i=1;i<=6;i++)
        {
            ComboBox<Integer> cmb = (ComboBox<Integer>) nesneBul("cmbatkilo"+i);
            cmb.setItems(atKilo);
        }
        ObservableList<String> galOran = FXCollections.observableArrayList(
                "%0","%10","%20","%30","%40","%50","%60","%70","%80","%90","%100");
        for(int i=1;i<=6;i++)
        {
            ComboBox<String> cmb = (ComboBox<String>) nesneBul("cmbgaloran"+i);
            cmb.setItems(galOran);
        }
        List<Integer> jokeykilo = new ArrayList<>();
        for(int i=60;i<=90;i++)
        {
            jokeykilo.add(i);
        }
        ObservableList<Integer> jokeyKilo = FXCollections.observableArrayList(jokeykilo);
        for(int i=1;i<=6;i++)
        {
            ComboBox<Integer> cmb = (ComboBox<Integer>) nesneBul("cmbjkykilo"+i);
            cmb.setItems(jokeyKilo);
        }
    }    

    @FXML
    private void hesapla(ActionEvent event) 
    {
        egitimYap();
        double veri[][] = new double[6][4];
        for(int i=1;i<=veri.length;i++)
        {
            ComboBox<Integer> cmbyas = (ComboBox<Integer>)nesneBul("cmbatyas"+i);
            ComboBox<Integer> cmbatklo = (ComboBox<Integer>)nesneBul("cmbatkilo"+i);
            ComboBox<String> cmbgloran = (ComboBox<String>)nesneBul("cmbgaloran"+i);
            ComboBox<Integer> cmbjkykil = (ComboBox<Integer>)nesneBul("cmbjkykilo"+i);
            double veri1 = Double.parseDouble(String.valueOf(cmbyas.getValue()));
            veri[i-1][0] = Double.parseDouble(String.valueOf(veri1*0.1).substring(0,3));
            double veri2 = Double.parseDouble(String.valueOf(cmbatklo.getValue()));
            veri[i-1][1] = Double.parseDouble(String.valueOf(veri2*0.001).substring(0,5));
            double veri3 = Double.parseDouble(String.valueOf(cmbgloran.getValue()).substring(1));
            veri[i-1][2] = Double.parseDouble(String.valueOf(veri3*0.01).substring(0,3));
            double veri4 = Double.parseDouble(String.valueOf(cmbjkykil.getValue()));
            veri[i-1][3] = Double.parseDouble(String.valueOf(veri4*0.01).substring(0,4));
        }
        int rakam = e.sonucBul(veri);
        lbl_sonuc.setText(""+rakam);
    }
    
    public Object nesneBul(String gelen)
    {
        for(int i=0;i<root.getChildren().size();i++) 
        {
            String isim = root.getChildren().get(i).getId();
            if(isim != null)
                if(isim.equals(gelen))
                    return root.getChildren().get(i);
        }
        return null;
    }
    
    public void dosyaOKu() throws Exception
    {
        File f = new File("C:\\Users\\SüleymanTKR\\Desktop\\veriler.txt");
        FileReader fr = new FileReader(f);
        if(!f.exists())
            f.createNewFile();
        BufferedReader bReader = new BufferedReader(fr);
        int satir=0,sutun=0;
        for(int i=0;i<510;i++)
        {
            String text = bReader.readLine();
            if(text != null)
            {
                satir++;
                if(i==1)
                    sutun = text.split("-----").length;
            }
            else if(text == null)
            {
                break;
            }
        }
        
        System.out.println("Satir : "+satir + "\nSutun : "+sutun);
        dizi = new double[satir][sutun];
        
        BufferedReader bReader1 = new BufferedReader(new FileReader(f));
        
        for(int i=0;i<satir;i++)
        {
            String text = bReader1.readLine();
            for(int j=0;j<sutun;j++)
            {
                if(j==0)
                {
                    dizi[i][j] = Double.parseDouble(text.split("-----")[j])*0.1;
                    try
                    {
                        dizi[i][j] = Double.parseDouble(String.valueOf(dizi[i][j]).substring(0,3));
                    }
                    catch(Exception e){}
                 }
                else if(j==1)
                {
                    dizi[i][j] = Double.parseDouble(text.split("-----")[j])*0.001;
                    try
                    {
                        dizi[i][j] = Double.parseDouble(String.valueOf(dizi[i][j]).substring(0,5));
                    }
                    catch(Exception e){}
                }   
                else if(j==2)
                {
                    dizi[i][j] = Double.parseDouble(text.split("-----")[j])*0.01;
                    try
                    {
                        dizi[i][j] = Double.parseDouble(String.valueOf(dizi[i][j]).substring(0,4));
                    }
                    catch(Exception e){}
                }
                else if(j==3)
                {
                    dizi[i][j] = Double.parseDouble(text.split("-----")[j])*0.01;
                    try
                    {
                        dizi[i][j] = Double.parseDouble(String.valueOf(dizi[i][j]).substring(0,4));
                    }
                    catch(Exception e){}
                }
                else if(j==4)
                {
                    dizi[i][j] = Double.parseDouble(text.split("-----")[j])*0.01;
                    try
                    {
                        dizi[i][j] = Double.parseDouble(String.valueOf(dizi[i][j]).substring(0,4));
                    }catch(Exception e){}
                }
            }                                                     
        }
        for(int a=0;a<100;a++)
        {
            System.out.println(dizi[a][0]+"  "+dizi[a][1]+"  "+dizi[a][2]+"  "+dizi[a][3]+"  "+dizi[a][4]);
        }
        
        System.out.println(dizi.length);
        System.out.println(dizi[0].length);
        
    }
    
    public void egitimYap()
    {
        e.agirliklariAyarla(4);
        e.hesapla(dizi);        
    }
    
}
