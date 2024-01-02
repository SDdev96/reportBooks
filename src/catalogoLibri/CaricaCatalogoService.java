package catalogoLibri;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

public class CaricaCatalogoService extends Service<ObservableList<Libro>> {
    private final URL url;
    private final String tipoVol;
    private final Integer annoStart;
    private final Integer annoEnd;
    private final Integer limite;

    public CaricaCatalogoService(URL url, String tipoVol, Integer annoStart, Integer annoEnd, Integer limit) {
        this.url = url;
        this.tipoVol = tipoVol;
        this.annoStart = (annoStart >= 2000) ? annoStart - 2000 : annoStart - 1900;
        this.annoEnd = (annoEnd >= 2000) ? annoEnd - 2000 : annoEnd - 1900;
        this.limite = limit;
    }

    public final URL getUrl() {
        return url;
    }

    public final Integer getLimite() {
        return limite;
    }

    public final String getTipoVol() {
        return tipoVol;
    }

    public final Integer getAnnoStart() {
        return annoStart;
    }

    public final Integer getAnnoEnd() {
        return annoEnd;
    }

    @Override
    protected Task<ObservableList<Libro>> createTask() {
        return new Task<ObservableList<Libro>>() {
            @Override
            protected ObservableList<Libro> call() throws Exception {
                ObservableList<Libro> list = FXCollections.observableArrayList();
                try (Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(url.openStream())))) {
                    sc.useDelimiter("[;\n]");
                    sc.useLocale(Locale.US);
                    Thread.sleep(1000);

                    if (sc.hasNextLine())
                        sc.nextLine();

                    int i = 0;
                    while (sc.hasNext() && i < limite) {
                        String tipoVolume = sc.next();
                        String gred = sc.next();
                        String isbn = sc.next();
                        String codVol = sc.next();
                        String titolo = sc.next();
                        int anno = sc.nextInt();
                        float prezzo = sc.nextFloat();
                        int peso = sc.nextInt();
                        int pagine = sc.nextInt();

                        Libro libro = new Libro(tipoVolume, gred, isbn, codVol, titolo, anno, prezzo, peso, pagine);

                        if (tipoVol.equals(libro.getTipoVol())
                                && (libro.getAnno() >= annoStart && libro.getAnno() <= annoEnd)) {
                            list.add(libro);
                            i++;
                            updateProgress(i, limite);
                            updateValue(list);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return list;
                }
                return list;
            }
        };
    }
}
