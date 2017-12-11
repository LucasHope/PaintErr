package fi.academy;


import org.springframework.stereotype.Service;

@Service
public class Numero {

    int arvo;

    public int getAndIncrement() {
        return arvo++;
    }

    public int getArvo() {
        return arvo;
    }

    public void setArvo(int arvo) {
        this.arvo = arvo;
    }

}
