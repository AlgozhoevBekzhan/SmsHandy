import java.util.Date;
/**
 * модель Сообщения,через него можно отправлять от абонента к абоненту
 * или также от провайдера к абоненту.
 *
 * @author Algozhoev Bekzhan
 * @version 16.04.2018
 */
public class Nachricht {
    private String absender;
    private String empfaenger;
    private Date sendeDatum;
    private String inhalt;

    /**
     * Конструктор данного класса Nachricht.
     * @param absender типа String
     */
    public Nachricht(String absender) {
        this.absender = absender;
        this.empfaenger = empfaenger;
        this.sendeDatum = sendeDatum;
        this.inhalt = inhalt;
        sendeDatum = new Date();
    }

    /**
     * Конструктор данного класса Nachricht.
     * @param absender типа String
     * @param inhalt типа String
     */
    public Nachricht(String absender, String inhalt) {
        this.absender = absender;
        this.empfaenger = empfaenger;
        this.sendeDatum = sendeDatum;
        this.inhalt = inhalt;
        sendeDatum = new Date();
    }

    /**
     * Конструктор данного класса Nachricht.
     * @param absender типа String
     * @param inhalt типа String
     * @param empfaenger типа String
     */
    public Nachricht(String absender,String inhalt, String empfaenger) {
        this.absender = absender;
        this.empfaenger = empfaenger;
        this.sendeDatum = sendeDatum;
        this.inhalt = inhalt;
        sendeDatum = new Date();
    }

    /**
     * Метод возвращает имя Отправителя сообщения.
     * @return absender типа String
     */
    public String getAbsender() {
        return absender;
    }

    /**
     * Метод устанавливает имя Отправителя сообщения.
     * @param absender типа String
     */
    public void setAbsender(String absender) {
        this.absender = absender;
    }

    /**
     * Метод возвращает имя Получателя сообщения.
     * @return empfaenger типа String
     */
    public String getEmpfaenger() {
        return empfaenger;
    }

    /**
     * Метод устанавливает имя Получателя сообщения.
     * @param empfaenger типа String
     */
    public void setEmpfaenger(String empfaenger) {
        this.empfaenger = empfaenger;
    }

    /**
     * возвращает Содержание сообщения.
     * @return inhalt типа String
     */
    public String getInhalt() {
        return inhalt;
    }

    /**
     * устанавливает Содержание сообщения.
     * @param inhalt типа String
     */
    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

    /**
     * Метод который создает внещний вид сообщения.
     * @return details типа String
     */
    public String toString() {
        String details = "Отправитель: "+absender+"\nПолучатель: "
                +empfaenger+"\nСообщение: " + inhalt+"\nДата отправки: "+sendeDatum;
        return details;
    }
}
