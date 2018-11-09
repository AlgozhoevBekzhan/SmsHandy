/**
 * Класс PrepaidSmsHandys наследуется от суперкласса SmsHandy.
 */
public class PrepaidSmsHandys extends SmsHandy{
    private String type = "PrepaidSmsHandy";

    /**
     * Конструктор для PrepaidSmsHandys.
     * @param rufnummer типа String
     * @param vladelez типа String
     */
    public PrepaidSmsHandys(String rufnummer, String vladelez) {
        super(rufnummer, vladelez);
        super.setTypeofsmshandy(type);
    }


    /**
     * Метод отправляет сообщение пользователю, учитывая большинство нюансов.
     * Если содержимое сообщения inhalt будет еквивалентно "*500#"
     * то выдается текущий баланс отправителя.
     * @param empfaenger типа SmsHandy
     * @param inhalt типа String
     */
    public void sendeSms1CherezProvider(SmsHandy empfaenger, String inhalt) {
        Nachricht message = new Nachricht(this.getVladelez(), inhalt, empfaenger.getVladelez());
        if (message.getInhalt() == this.getProvider().getGuthabenAbfrage()) {
            System.out.println("Ваш текущий баланс составляет: " + this.getBalance());
            System.out.println();
        } else if (message.getEmpfaenger() == this.getVladelez()) {
            System.out.println("Пожалуйста введите имя получателя, отличный от вашего.");
            System.out.println();
        }
        else if (this.getProvider()==null || empfaenger.getProvider()==null){
            System.out.println
                    ("Вы или получатель не зарегистрированы у какого либо провайдера!\nПожалуйста проверьте чтобы ваш провайдер и провайдер вашего получателя были одинаковыми.");
            System.out.println();
        }
        else if (this.getProvider().kannSendenZu(empfaenger.getVladelez()) == true) {
            this.getProvider().sende(message);
            this.getGesendet().add(message);
            this.setBalance(this.getBalance()-this.getProvider().getPreisProSms());
            if(this.getProvider().getGuthaben().containsKey(this.getRufnummer())){
                this.getProvider().getGuthaben().put(this.getRufnummer(),this.getBalance());
            }
            else{
            }
            System.out.println("Сообщение успешно доставлено!");
            System.out.println();
        }

        else if ((this.getProvider().getHandynetz()!=null &&
                empfaenger.getProvider().getHandynetz()==null)||
                (this.getProvider().getHandynetz()==null &&
                        empfaenger.getProvider().getHandynetz()!=null||
                        this.getProvider().getHandynetz()==null &&
                                empfaenger.getProvider().getHandynetz()==null)){
            System.out.println("Ваш провайдер или провайдер получателя еще не зарегизстрировались в базе Handynetz!");
            System.out.println();
        }
        else if (this.getProvider().getHandynetz() != empfaenger.getProvider().getHandynetz()) {
            System.out.println
                    ("Ваш провайдер и провайдер получателя зарегистрированы (или вовсе не зарегистрированы) в разных Handynetz!");
            System.out.println();
        }
        else if (this.getProvider().getHandynetz().findeProviderFuer(this.getVladelez()) !=null
                && empfaenger.getProvider().getHandynetz().findeProviderFuer(empfaenger.getVladelez())!=null
                && this.getProvider().getHandynetz() == empfaenger.getProvider().getHandynetz()) {

            this.getProvider().sende(message);
            empfaenger.getProvider().getHraniliche().add(message);
            this.getGesendet().add(message);
            this.setBalance(this.getBalance()-this.getProvider().getPreisProSms());
            if(this.getProvider().getGuthaben().containsKey(this.getRufnummer())){
                this.getProvider().getGuthaben().put(this.getRufnummer(),this.getBalance());
            }
            else{
            }
            System.out.println("Сообщение успешно доставлено!");
            System.out.println();
        }

    }

    /**
     * Метод пополняет баланс на заданное колличество menge.
     * @param menge типа int
     */
    public void aufladen(int menge)
    {
        this.getProvider().aufladen(this,menge);
    }
}


