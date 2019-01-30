public class Items {

    private  String title;
    private  String number;
    private  String id;


    public Items(String title, String number,String id) {
        this.title=title;
        this.number=number;
        this.id=id;

    }

    public String getTitle() {
        return title;
    }

    public String getNumber() {
        return number;
    }

    public String getId(){ return id;}

    public void assignnumber(String str){
        this.number=str;


    }
}
