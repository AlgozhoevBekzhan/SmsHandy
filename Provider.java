import java.util.*;
/**
 * модель Провайдера,через который можно перевадать сообщения.
 * Для телефонов требуется баланс для отправки сообщения.
 * Все номера хранятся в двух HashMap коллекциях.
 * В  teilnehmer ключом является номер телефона, а значение сам телефон.
 * В guthaben ключом является номер телефона,а значением - его текущий баланс.
 * @author Algozhoev Bekzhan
 * @version 16.04.2018
 */
public class Provider {
    private String name;
    private static final Integer STARTGUTHABEN = 25;
    private static final Integer STARTGUTHABENFORTARIF = 100;
    private static final Integer PREIS_PRO_SMS = 2;
    private static final Integer PREIS_FOR_TARIF = 1;
    private static final String GUTHABEN_ABFRAGE = "*500#";
    private Handynetz handynetz;
    private HashMap<String,SmsHandy> teilnehmer = new HashMap<String,SmsHandy>();
    private HashMap<String, Integer> guthaben = new HashMap<String, Integer>();
    private ArrayList<Nachricht> hraniliche = new ArrayList<Nachricht>();


    /**
     * Конструктор класса Povider.
     * @param name типа String
     * @param handynetz типа Handynetz
     */
    public Provider(String name, Handynetz handynetz) {
        this.name = name;
        handynetz.fuegeProviderHinzu(this);
        hraniliche = new ArrayList<Nachricht>();
        this.teilnehmer = new HashMap<String, SmsHandy>();
        this.guthaben = new HashMap<String, Integer>();
    }


    /**
     * Конструктор класса Povider.
     * @param name типа String
     */
    public Provider(String name) {
        this.name = name;
        hraniliche = new ArrayList<Nachricht>();
        this.teilnehmer = new HashMap<String, SmsHandy>();
        this.guthaben = new HashMap<String, Integer>();
    }

    /**
     * Метод возвращает цену отправки одного смс для телефонов с типом
     * TariffPlanHandy.
     * @return PREIS_FOR_TARIF типа Integer
     */
    public static Integer getPreisForTarif() {
        return PREIS_FOR_TARIF;
    }

    /**
     * Метод возвращает базу Handynetz, в которой хранится данный провайдер.
     * @return PREIS_FOR_TARIF типа Integer
     */
    public Handynetz getHandynetz() {
        return handynetz;
    }

    /**
     *
     * @param handynetz
     */
    public void setHandynetz(Handynetz handynetz) {
        this.handynetz = handynetz;
    }

    /**
     *
     * @param guthaben
     */
    public void setGuthaben(HashMap<String, Integer> guthaben) {
        this.guthaben = guthaben;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public ArrayList<Nachricht> getHraniliche() {
        return hraniliche;
    }

    /**
     *
     * @return
     */
    public  Integer getSTARTGUTHABEN() {
        return STARTGUTHABEN;
    }

    /**
     *
     * @return
     */
    public  Integer getSTARTGUTHABENFORTARIF() {
        return STARTGUTHABENFORTARIF;
    }

    /**
     *
     * @return
     */
    public static Integer getPreisProSms() {
        return PREIS_PRO_SMS;
    }

    /**
     *
     * @return
     */
    public static String getGuthabenAbfrage() {
        return GUTHABEN_ABFRAGE;
    }

    /**
     *
     * @return
     */
    public HashMap<String, Integer> getGuthaben() {
        return guthaben;
    }



    /**
     * Метод проверяет есть ли такие пользователи в базе,
     * а также хватает ли пользователю средств для отправки сообщения.
     * @param sms
     * @return
     */
    public boolean sende(Nachricht sms) {

        SmsHandy empfaengeProvider = this.findeContactValue(sms.getEmpfaenger());
        SmsHandy absendereProvider = this.findeContactValue(sms.getAbsender());
        if(absendereProvider.getTypeofsmshandy()=="PrepaidSmsHandy"){
            if(absendereProvider.getBalance()<this.getPreisProSms()){
                System.out.println("На вашем балансе не хватает средств для отправки сообщения!\nПожалуйста пополните баланс.");
                return false;
            }
            else if(empfaengeProvider!=null && absendereProvider!=null){
                empfaengeProvider.empfangeSms(sms);
                hraniliche.add(sms);
                return true;
            }
        }
        else
        {
            if(absendereProvider.getBalance()<this.getPreisForTarif()){
                System.out.println("На вашем балансе не хватает средств для отправки сообщения!\nПожалуйста пополните баланс.");
                return false;
            }
            else if(empfaengeProvider!=null && absendereProvider!=null){
                empfaengeProvider.empfangeSms(sms);
                hraniliche.add(sms);
                return true;
            }
        }
        return false;
    }

    /**
     * Сообщения отправляются напрямую получателю и не стоит денег.
     * Сообщения отпраленные данным методом не будут сохраняться у провайдера.
     * @param sms типа Nachricht
     * @return
     */
    public void otpravkaBalance(Nachricht sms) {
        SmsHandy empfaengeProvider = this.findeContactValue(sms.getEmpfaenger());
        if (empfaengeProvider != null) {
            empfaengeProvider.empfangeSms(sms);
        }
        else{
            System.out.println("Данный пользователь: "+sms.getEmpfaenger()+" не найден.");
            System.out.println();
        }

    }



    /**
     * Метод регистрирует данного пользователя в базу провайдера.
     * @param klient
     */
    public void anmelden(SmsHandy klient){
        if(klient.getTypeofsmshandy()=="PrepaidSmsHandy"){
            if(teilnehmer.size()==0)
            {
                sendeAnmeldenNachricht(klient);
                teilnehmer.put(klient.getRufnummer(),klient);
                klient.setBalance(this.getSTARTGUTHABEN());
                guthaben.put(klient.getRufnummer(),this.getSTARTGUTHABEN());
                klient.wechselProvider(this);
            }

            else if(findeContact(klient.getRufnummer()) == klient.getRufnummer()){
                System.out.println("Извините, в нашем списке уже существует абонент с таким номером.");
                System.out.println();
            }
            else{
                sendeAnmeldenNachricht(klient);
                teilnehmer.put(klient.getRufnummer(),klient);
                klient.setBalance(this.getSTARTGUTHABEN());
                guthaben.put(klient.getRufnummer(),this.getSTARTGUTHABEN());
                klient.wechselProvider(this);
            }
        }
        else
        {
            if(teilnehmer.size()==0)
            {
                sendeAnmeldenNachricht(klient);
                teilnehmer.put(klient.getRufnummer(),klient);
                klient.setBalance(this.getSTARTGUTHABENFORTARIF());
                guthaben.put(klient.getRufnummer(),klient.getBalance());
                klient.wechselProvider(this);
            }

            else if(findeContact(klient.getRufnummer()) == klient.getRufnummer()){
                System.out.println("Извините, в нашем списке уже существует абонент с таким номером.");
                System.out.println();
            }
            else{
                sendeAnmeldenNachricht(klient);
                teilnehmer.put(klient.getRufnummer(),klient);
                klient.setBalance(this.getSTARTGUTHABENFORTARIF());
                guthaben.put(klient.getRufnummer(),klient.getBalance());
                klient.wechselProvider(this);
            }
        }
    }



    /**
     * Метод удаляет данного пользователя из базы.
     * @param klient
     */
    public void abmelden(SmsHandy klient){
        if(teilnehmer.size()==0)
        {
            System.out.println("Извините, в нашем списке абонентов совпадений не найдено.");
            System.out.println();
        }
        else if(findeContact(klient.getRufnummer()) == klient.getRufnummer()){
            sendeAbmeldenNachricht(klient);
            teilnehmer.remove(klient.getRufnummer());
            guthaben.remove(klient.getRufnummer());
        }
        else{
            System.out.println("Извините, в нашем списке абонентов совпадений не найдено.");
            System.out.println();
        }
    }

    /**
     * Метод ищет в базе провайдера пользователя при помощи данного номера телефона.
     * @param rufnummer
     * @return
     */
    public String findeContact(String rufnummer)
    {
        for(HashMap.Entry<String, SmsHandy> entry: teilnehmer.entrySet()){
            if(entry.getKey().equals(rufnummer)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Метод оправляет данному пользователю(*если он есть в базе) приветственное сообщение.
     * @param klient
     */
    private void sendeAnmeldenNachricht(SmsHandy klient) {
        String smsAnmelden = "Добрый день "+klient.getVladelez()+"."+
                "!\nИскренне благодарим Вас, \nЧто вы выбрали наш сотовый оператор "+getName()+".";
        System.out.println(smsAnmelden);
        System.out.println();
        Nachricht nachricht = new Nachricht(getName(),smsAnmelden,klient.getVladelez());
        klient.empfangeSms(nachricht);
        hraniliche.add(nachricht);
    }

    /**
     * Метод оправляет данному пользователю(*если он есть в базе) прощальное сообщение.
     * @param klient
     */
    private void sendeAbmeldenNachricht(SmsHandy klient) {
        String smsAbmelden = "Здравствуйте "+klient.getVladelez()+
                "!\nВы успешно отписались от своего сотового оператора "+getName()+
                "\nСпасибо что были с нами!";
        System.out.println(smsAbmelden);
        System.out.println();
        Nachricht nachricht = new Nachricht(getName(),smsAbmelden,klient.getVladelez());
        klient.empfangeSms(nachricht);
        hraniliche.add(nachricht);
    }


    /**
     * Метод ищет пользователя в базе провайдера, с данным именем.
     * @param empfaenger
     * @return
     */
    public SmsHandy findeContactValue(String empfaenger)
    {
        for(HashMap.Entry<String, SmsHandy> entry: teilnehmer.entrySet()){
            if(entry.getValue().getVladelez().equals(empfaenger)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Метод возвращает true, когда
     * участник с номером empfaenger зарегистрирован, иначе false.
     * @param empfaenger
     * @return
     */
    public boolean kannSendenZu(String empfaenger)
    {
        for(HashMap.Entry<String, SmsHandy> entry: teilnehmer.entrySet()){
            if(entry.getValue().getVladelez().equals(empfaenger)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод осуществляет проверку в базе.
     * Если есть пользователь с данным номером, и значение guthaben - положительное,
     * то у пользователя пополняется баланс на значение guthaben.
     * @param teilnehm
     * @param guthabenwert
     */
    public void aufladen(SmsHandy teilnehm, int guthabenwert) {
        if (guthabenwert <= 0) {
            System.out.println("Пополняемая сумма должна быть больше чем 0 сом.");
        } else if (findeContact(teilnehm.getRufnummer()) == teilnehm.getRufnummer()) {
            teilnehm.setBalance(teilnehm.getBalance() + guthabenwert);
            HashMap<String ,Integer> hashmap = new HashMap<String, Integer>();
            hashmap.put(teilnehm.getRufnummer(), guthaben.get(teilnehm.getRufnummer())+guthabenwert);
            this.setGuthaben(hashmap);
            this.sendeGuthabenNachricht(teilnehm);
        }
        else
        {
            System.out.println("Такого номера: "+"["+teilnehm.getRufnummer()+"]"+" не существует!\nПожалуйста проверьте правильность введенных значений!");
        }
    }

    /**
     * Метод осуществляет проверку в базе.
     * Если есть пользователь с данныйм номером, то
     * у пользователя обновляется лимит(*отправки смс), и становится равной 100.
     * @param klient
     */
    public void refreshLimit(SmsHandy klient){
        if (findeContact(klient.getRufnummer()) == klient.getRufnummer()) {
            klient.setBalance(100);
            HashMap<String ,Integer> hashmap = new HashMap<String, Integer>();
            hashmap.put(klient.getRufnummer(), 100);
            this.setGuthaben(hashmap);
            this.sendeGuthabenNachricht(klient);
        }
        else{
            System.out.println("Такого номера: "+"["+klient.getRufnummer()+"]"+" не существует!\nПожалуйста проверьте правильность введенных значений!");
        }
    }

    /**
     * Метод отправляет смс данному пользователю.
     * Смс хранит в себе актуальную информацию о балансе пользователя.
     * @param klient
     */
    private void sendeGuthabenNachricht(SmsHandy klient) {
        String details = "Вы успешно пополнили Ваш баланс.\nВаш текущий баланс составляет: "+guthaben.get(klient.getRufnummer());
        Nachricht sms = new Nachricht(this.getName(),details,klient.getVladelez());
        this.otpravkaBalance(sms);
    }
}
