import java.util.ArrayList;

/**
 * модель Базы Провайдера.
 */
public class Handynetz {
    private String name;
    private ArrayList<Provider> providerArrayList = new ArrayList<Provider>();

    /**
     * Конструктор для класса Handynetz.
     * @param name типа String
     */
    public Handynetz(String name){
        this.name = name;
        this.providerArrayList = new ArrayList<Provider>();
    }

    /**
     * Метод возвращает имя Базы Провайдера.
     * @return name типа String
     */
    public String getName()
    {
        return name;
    }


    /**
     * Метод Для добавления провайдеров в базу providerArrayList.
     * @param provider типа Provider
     */
    public void fuegeProviderHinzu(Provider provider){
        if(this.findeProviderInProviderArrayList(provider)==true){
            System.out.println("Извините, но провайдер с таким именем: "+provider.getName()+" уже существует в базе!");
            System.out.println();
        }
        else{
            provider.setHandynetz(this);
            this.providerArrayList.add(provider);
            System.out.println("Поздравляем: "+provider.getName()+
                    "! Вы успешно зарегистрировались в нашей базе "+
                    this.getName());
            System.out.println();
        }
    }

    /**
     * Метод производит поиск в коллекции providerArrayList.
     * Если есть подходящий провайдер у которого зарегистрирован данный пользователь
     * возвращается этот провайдер. В других случаях null.
     * @param empfaenger типа String
     * @return providerlist типа Provider, если таковой есть, иначе null
     */
    public Provider findeProviderFuer(String empfaenger){
        for(Provider providerList: providerArrayList){
            if(providerList.kannSendenZu(empfaenger)==true){
                return providerList;
            }
        }
        return null;
    }

    /**
     * Метод производит поиск в коллекции providerArrayList.
     * Если есть провайдер, у которого имя аналогично имени заданного провайдера
     * выводится true, иначе false.
     * @param provider типа Provider
     * @return boolean
     */
    public boolean findeProviderInProviderArrayList(Provider provider)
    {
        for(Provider providerListe: providerArrayList){
            if(providerListe.getName()==provider.getName()){
                return true;}
        }
        return false;
    }
}
