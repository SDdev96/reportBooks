package catalogoLibri;

import java.util.Objects;

public class Libro implements Comparable<Libro> {
    private String tipoVol;
    private String gred;
    private String isbn;
    private String codVol;
    private String titolo;
    private Integer anno;
    private Float prezzo;
    private Integer peso;
    private Integer pagine;

    public Libro(String tipoVol, String gred, String isbn, String codVol, String titolo, int anno, float prezzo,
            int peso,
            int pagine) {
        this.tipoVol = tipoVol;
        this.gred = gred;
        this.isbn = isbn;
        this.codVol = codVol;
        this.titolo = titolo;
        this.anno = anno;
        this.prezzo = prezzo;
        this.peso = peso;
        this.pagine = pagine;
    }

    public Libro() {
    }

    public String getTipoVol() {
        return tipoVol;
    }

    public void setTipoVol(String tipoVol) {
        this.tipoVol = tipoVol;
    }

    public String getGred() {
        return gred;
    }

    public void setGred(String gred) {
        this.gred = gred;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCodVol() {
        return codVol;
    }

    public void setCodVol(String codVol) {
        this.codVol = codVol;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Integer getPagine() {
        return pagine;
    }

    public void setPagine(int pagine) {
        this.pagine = pagine;
    }

    // Generato dall'IDE
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Libro libro = (Libro) o;
        return isbn == libro.isbn;
    }

    // Generato dall'IDE
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public int compareTo(Libro o) {
        return isbn.compareTo(o.getIsbn());
    }

    @Override
    public String toString() {
        return "Libro{" +
                "tipoVol='" + tipoVol + '\'' +
                ", gred='" + gred + '\'' +
                ", isbn=" + isbn +
                ", codVol=" + codVol +
                ", titolo='" + titolo + '\'' +
                ", anno=" + anno +
                ", prezzo=" + prezzo +
                ", peso=" + peso +
                ", pagine=" + pagine +
                '}';
    }
}
