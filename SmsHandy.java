import java.util.ArrayList;
/**
 * модель Телефона,через который можно перевадать сообщения.
 * Телефоны делятся на TariffPlanHandy и PrepaidSmsHandy.
 * Баланс учитывается для каждого типа отдельно.
 * Класс является абстрактным.
 *
 * @author Bekzhan Algozhoev
 * @version 23.04.2018
 */
public abstract class  SmsHandy {
    private String vladelez;
    private String rufnummer;
    private String typeofsmshandy;
    private Provider provider;
    private int balance;
    private ArrayList<Nachricht> gesendet = new ArrayList<Nachricht>();// отправленные сообщения
    private ArrayList<Nachricht> empfangen = new ArrayList<Nachricht>();// полученные сообщения

    /**
     * Конструктор данного класса SmsHandy.
     * @param rufnummer типа String
     * @param vladelez типа String
     */
    public SmsHandy(String
                            rufnummer, String vladelez) {
        this.rufnummer = rufnummer;
        this.vladelez = vladelez;
        this.gesendet = new ArrayList<Nachricht>();
        this.empfangen = new ArrayList<Nachricht>();
    }

    /**
     *
     * @return
     */
    public String getTypeofsmshandy() {
        return typeofsmshandy;
    }

    /**
     *
     * @param typeofsmshandy
     */
    public void setTypeofsmshandy(String typeofsmshandy) {
        this.typeofsmshandy = typeofsmshandy;
    }

    /**
     *
     * @return
     */
    public int getBalance() {
        return balance;
    }

    /**
     *
     * @param balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     *
     * @return
     */
    public String getVladelez() {
        return vladelez;
    }

    /**
     *
     * @return
     */
    public String getRufnummer() {
        return rufnummer;
    }

    /**
     *
     * @return
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     *
     * @param provider
     */
    public void wechselProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     *
     * @return
     */
    public ArrayList<Nachricht> getGesendet() {
        return gesendet;
    }

    /**
     *
     * @param sms
     */
    public void empfangeSms(Nachricht sms){
        empfangen.add(sms);
    }

    /**
     *
     * @param empfaenger
     * @param inhalt
     */
    public void sendeSms1CherezProvider(String empfaenger, String inhalt){
        Nachricht message = new Nachricht(this.getVladelez(), inhalt, empfaenger);
        if(message.getInhalt()== provider.getGuthabenAbfrage()){
            System.out.println("Ваш текущий баланс составляет: "+this.getBalance());
        }
        else if (message.getEmpfaenger()==this.getVladelez()){
            System.out.println("Пожалуйста введите имя получателя, отличный от вашего.");
        }
        else if(provider.kannSendenZu(empfaenger)==true) {
            provider.sende(message);
            gesendet.add(message);
            this.balance = balance-provider.getPreisProSms();
        }
        else if(this.provider.getHandynetz().findeProviderFuer(empfaenger)!=this.getProvider()){
            provider.sende(message);
            gesendet.add(message);
            this.balance = balance - provider.getPreisProSms();
        }

        else{System.out.println
                ("Вы или получатель не зарегистрированы у данного провайдера!\nПожалуйста проверьте чтобы ваш провайдер и провайдем вашего получателя были одинаковыми.");}
    }

    /**
     *
     * @param empfaenger
     * @param inhalt
     */
    public void sendeSmsNapryamuyu(SmsHandy empfaenger, String inhalt){
        Nachricht message = new Nachricht(this.getVladelez(), inhalt, empfaenger.getVladelez());
        empfaenger.empfangeSms(message);
        this.gesendet.add(message);
    }


    /**
     *
     */
    public void listeEmpfangeneSms(){
        System.out.println("Ваш спиcок входящих сообщений:");
        for(Nachricht nachricht:empfangen)
        {
            System.out.println(nachricht);
        }
        System.out.println();
    }

    /**
     *
     */
    public void listeGesendeteSms(){
        System.out.println("Ваш список отправленных сообщений:");
        for(Nachricht nachricht:gesendet)
        {
            System.out.println(nachricht);
        }
        System.out.println();
    }

    /**
     *
     * @param nachricht
     */
    private void gebeNachrichtAus(Nachricht nachricht){
        if(nachricht.getInhalt().equals(provider.getGuthabenAbfrage()) && nachricht.getAbsender().equals(this.getVladelez())){
            System.out.println("Ваш текущий баланс составляет: "+this.getBalance());
        }
        else if (nachricht.getEmpfaenger()==this.getVladelez()){
            System.out.println("У данного сообщения получаетелем являетесь вы.\nПожалуйста укажите в сообщении другого получателя.");
        }
        else if(this.getVladelez()!=nachricht.getAbsender()){
            System.out.println("Вы пытаетесь отправить сообщение получателю под чужим именем!");
        }
        else if(provider.kannSendenZu(nachricht.getEmpfaenger())==true) {
            provider.sende(nachricht);
            gesendet.add(nachricht);
            this.balance = balance-provider.getPreisProSms();
        }
        else{System.out.println
                ("Вы или получатель, указанный в сообщении, не зарегистрированы у данного провайдера!\nПожалуйста проверьте чтобы ваш провайдер и провайдер получателя, указанного в сообщении были одинаковыми.");
        }
    }
}
