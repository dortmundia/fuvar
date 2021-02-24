import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.io.*;
import java.util.stream.*;


public class Feladatok {
    private ArrayList<Fuvar> fuvarLista;


    public Feladatok(){
        Beolvas();
        System.out.println("3. Feladat: Fuvarok száma: " + fuvarLista.size());


       /* var szurt = fuvarLista.stream()
                .filter(i->i.getTaxi_id() ==6185)
                .toArray(Fuvar szurt = new Fuvar);

        System.out.println("4. Feladat: " + szurt.length + " alatt fuvarra: " + fuvarLista.stream(szurt).filter(k ->k.getViteldij()).sum());
        */



        System.out.println("5. Feladat:");
        fuvarLista.stream()
                .collect(Collectors.groupingBy(k -> k.getFizetes_modja(), Collectors.counting()))
                .forEach((mod, db) -> System.out.println(mod + ": " + db + " db"));


        System.out.printf("6. Feladat: %.2f km\n", fuvarLista.stream().mapToDouble(k -> k.getTavolsag()).sum() * 1.6D);
        fuvarLista.stream()
                .max(Comparator.comparingInt(k -> k.getIdotartam()))
                .ifPresent(k -> System.out.printf("7. Feladat: %d mp, azonosito: %d, távolság: %.2f km, díj: %.2f$\n", k.getIdotartam(), k.getTaxi_id(), k.getTavolsag(), k.getViteldij()));


        String header = "taxi_id;indulas;idotartam;tavolsag;viteldij;borravalo;fizetes_modja\n";
        var hibasAdatok = fuvarLista.stream()
                .filter(k -> k.getIdotartam() > 0 && k.getViteldij() > 0F && k.getTavolsag() == 0F)
                .sorted(Comparator.comparing(k -> k.getIndulas()))
                .map(k -> k.getTaxi_id() + ";" + k.getIndulas() + ";" + k.getIdotartam() + ";" + k.getTavolsag() + ";" + k.getViteldij() + ";" + k.getBorravalo() + ";" + k.getFizetes_modja())
                .collect(Collectors.joining("\n"));


        try {
            FileWriter writer = new FileWriter("hibak.txt");
            writer.write(header + hibasAdatok);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void Beolvas() {
        fuvarLista = new ArrayList<>();

        try{
            BufferedReader r= new BufferedReader(new FileReader("fuvar.csv"));
            r.readLine();
            String sor= r.readLine();
            while(sor != null){
                fuvarLista.add(new Fuvar(sor));
                sor=r.readLine();
            }

        }catch(IOException e){
            e.getMessage();
        }
    }


}


