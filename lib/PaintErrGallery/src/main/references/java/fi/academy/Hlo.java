package fi.academy;

public class Hlo {

    String nimi, nimimerkki;
    int pituus, paino;
    double bmi;

    public void Hlo() {}

    public void Hlo (String nimi, String nimim, int pituus, int paino) {
        this.nimi = nimi;
        this.nimimerkki = nimim;
        this.pituus = pituus;
        this.paino = paino;

        double pituus2 = pituus / 100;
        if (pituus == 0) pituus2 = 1;

        this.bmi = paino / pituus2;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

    public int getPituus() {
        return pituus;
    }

    public void setPituus(int pituus) {
        this.pituus = pituus;
    }

    public int getPaino() {
        return paino;
    }

    public void setPaino(int paino) {
        this.paino = paino;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public void setBmi (int pituus, int paino) {

        if (pituus == 0) this.bmi = 1;

        double pituus2 = ((double) pituus / 100) * ((double) pituus / 100);

        this.bmi = paino / pituus2;

    }
}
