package caculate;

public class Ticket {
    private String in_out_china;//是否国内航班
    private String area;//区域
    private String class_kind;//舱类型
    private double ticket_price;//票价
    private String passenge_kind;//乘客类型

    public Ticket(String in_out_china,String area,String class_kind ,double ticket_price,String passenge_kind){
        this.in_out_china=in_out_china;
        this.area=area;
        this.class_kind=class_kind;
        this.ticket_price=ticket_price;
        this.passenge_kind=passenge_kind;
    }
    
    public String TO_area(){
        return area;
    }
    
    public String TO_in_out(){
        return in_out_china;
    }

    public String TO_class_kind(){
        return class_kind;
    }

    public double TO_ticket_price(){
        return ticket_price;
    }
    
    public String TO_passenge_kind(){
        return passenge_kind;
    }
}
