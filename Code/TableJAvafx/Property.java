
package TableJAvafx;




public class Property {
    private int day;
    private String month;
    private int year;
    private String category;
    private double amount;

    public Property() {
        this.day=0;
        this.month="";
        this.year=0;
        this.category="";
        this.amount=0;
    }
    
    public Property(String s){
        s=s.trim();
        s=s.replace("*", " ");
        String[] ad=s.split(" ");
        day=Integer.parseInt(ad[0]);
        month=ad[1];
        year=Integer.parseInt(ad[2]);
        category=ad[3];
        amount=Double.parseDouble(ad[4]);
    }

    public Property(int day, String month, int year, String category, double amount) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.category = category;
        this.amount = amount;
    }
    
    

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String toString(){
        return ""+day+"*"+month+"*"+year+"*"+category+"*"+amount+"#";
    }
    
    
}
