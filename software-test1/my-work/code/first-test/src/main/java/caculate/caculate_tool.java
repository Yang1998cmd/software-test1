package caculate;

import java.util.List;

public class caculate_tool {

    private List<Packages> passenger_Package;//行李集合
    private Ticket passenger_Ticket;//票价
    private static double package_transport_price;//行李总运输价格

    public caculate_tool(List<Packages> passenger_Package, Ticket passenger_Ticket)
    {
        this.passenger_Package=passenger_Package;
        this.passenger_Ticket=passenger_Ticket;
        package_transport_price=0.0;
    }

    public void do_caluate()
    {
        if(passenger_Ticket.TO_in_out ().equals ("国内航班"))//国内航班跳转国内行李计费
        {
            for(int i=0;i<passenger_Package.size();i++){
                Packages the_package=passenger_Package.get(i);
                System.out.println (the_package.To_length ()+" "+the_package.To_width ()+" "+the_package.To_height ()+" "+the_package.To_weight ());
                in_china (the_package);
            }
        }
        else//国际航班
        {
            for(int i=0;i<passenger_Package.size();i++){
                Packages the_package=passenger_Package.get(i);
                out_china (the_package,i+1);//第i+1件行李
            }
        }
        System.out.println (passenger_Ticket.TO_area ()+"总运输费用:  "+package_transport_price);
    }


    public double to_all_tran_price(){
        return  package_transport_price;
    }


    public void in_china (Packages the_package)
    {
        String passenger_kind=passenger_Ticket.TO_passenge_kind ();
        String class_kind=passenger_Ticket.TO_class_kind ();
        double ticket_price=passenger_Ticket.TO_ticket_price ();
//        double the_package_length=the_package.To_length ();
//        double the_package_width=the_package.To_width ();
//        double the_package_height=the_package.To_height ();
        double the_package_weight=the_package.To_weight ();

//        if(the_package_length>100.0||the_package_width>60.0||the_package_height>40.0)
//        {
////            package_transport_price+=0.0;
//            return 0;//提示不能超过尺寸不能超，建议分开行李
//        }

        if(passenger_kind.equals ("成人")||passenger_kind.equals ("儿童"))
        {
            if(class_kind.equals ("头等舱")&&the_package_weight>40.0&&the_package_weight<=32.0)
            {
                package_transport_price+=0.015*ticket_price*(the_package_weight-40.0);
            }
            else if(class_kind.equals ("公务舱")&&the_package_weight>30.0&&the_package_weight<=32.0)
            {
                package_transport_price+=0.015*ticket_price*(the_package_weight-30.0);
            }
            else if(class_kind.equals ("经济舱")&&the_package_weight>20.0&&the_package_weight<=32.0)
            {
                package_transport_price+=0.015*ticket_price*(the_package_weight-20.0);
            }
        }
        else if(passenger_kind.equals ("婴儿")){
            if(the_package_weight>10.0)
            {
                package_transport_price+=0.015*ticket_price*(the_package_weight-10.0);
            }
        }

    }


    public void out_china(Packages the_package, int i)//第i件行李
    {
        String passenger_kind=passenger_Ticket.TO_passenge_kind ();
        String class_kind=passenger_Ticket.TO_class_kind ();
        String area=passenger_Ticket.TO_area ();
        double the_package_length=the_package.To_length ();
        double the_package_width=the_package.To_width ();
        double the_package_height=the_package.To_height ();
        double the_package_weight=the_package.To_weight ();
        double all_length =the_package_length+the_package_width+the_package_height;
       // (界面过滤必须2-32kg，不然拆分两件，尺寸必须60-203cm,否者不给托运)，但是为了严谨一下判断情况还是包含了
        if(area.equals ("区域一"))
        {
            if(class_kind.equals ("头等舱")||class_kind.equals ("公务舱"))
            {
                if(passenger_kind.equals ("成人")||passenger_kind.equals ("儿童"))
                {
                    if(all_length>=60.0 && all_length<=158.0)
                    {
                        if(i==3)//超件第1件
                        {
                            package_transport_price+=1400.0;
                        }
                        else if(i==4)//超件第2件
                        {
                            package_transport_price+=2000.0;
                        }
                        else if(i>4)//三件以上
                        {
                            package_transport_price+=3000.0;
                        }
                    }
                    else if(all_length>158.0 && all_length<=203.0)//只有可能出现超过尺寸的情况
                    {
                        package_transport_price+=980.0;
                        if(i==3)//超件第1件
                        {
                            package_transport_price+=1400.0;
                        }
                        else if(i==4)//超件第2件
                        {
                            package_transport_price+=2000.0;
                        }
                        else if(i>4)//三件以上
                        {
                            package_transport_price+=3000.0;
                        }
                    }

                }
            }
            else if(class_kind.equals ("悦享经济舱")||class_kind.equals ("超级经济舱")||class_kind.equals ("经济舱"))
            {
                if(all_length>=60.0 && all_length<=158.0)//不超尺寸
                {
                    if(the_package_weight>23.0&&the_package_weight<=28.0)//第一种超重情况
                    {
                        package_transport_price+=380.0;
                    }
                    else if(the_package_weight>28.0&&the_package_weight<=32.0)//第二种超重情况
                    {
                        package_transport_price+=980.0;
                    }

                    if(i==3)//超件第1件
                    {
                        package_transport_price+=1400.0;
                    }
                    else if(i==4)//超件第2件
                    {
                        package_transport_price+=2000.0;
                    }
                    else if(i>4)//三件以上
                    {
                        package_transport_price+=3000.0;
                    }
                }
                else if(all_length>158.0 && all_length<=203.0)//超尺寸
                {
                    if(the_package_weight>=2.0&&the_package_weight<=23.0)//第一种不超重情况
                    {
                        package_transport_price+=980.0;
                    }
                    else if(the_package_weight>23.0&&the_package_weight<=32.0)//第二种超重情况
                    {
                        package_transport_price+=1400.0;
                    }

                    if(i==3)//超件第1件
                    {
                        package_transport_price+=1400.0;
                    }
                    else if(i==4)//超件第2件
                    {
                        package_transport_price+=2000.0;
                    }
                    else if(i>4)//三件以上
                    {
                        package_transport_price+=3000.0;
                    }

                }
            }

        }
        else if(area.equals ("区域二"))
        {
            if(class_kind.equals ("头等舱")||class_kind.equals ("公务舱"))
            {
                if(passenger_kind.equals ("成人")||passenger_kind.equals ("儿童"))
                {
                    if(all_length>=60.0 && all_length<=158.0)
                    {
                        if(i==3||i==4)//超件第1,2件
                        {
                            package_transport_price+=1100.0;
                        }
                        else if(i>4)//三件以上
                        {
                            package_transport_price+=1590.0;
                        }
                    }
                    else if(all_length>158.0 && all_length<=203.0)//只有可能出现超过尺寸的情况
                    {
                        package_transport_price+=690.0;
                        if(i==3||i==4)//超件第1,2件
                        {
                            package_transport_price+=1100.0;
                        }
                        else if(i>4)//三件以上
                        {
                            package_transport_price+=1590.0;
                        }
                    }

                }
            }
            else if(class_kind.equals ("悦享经济舱")||class_kind.equals ("超级经济舱")||class_kind.equals ("经济舱"))
            {
                if(all_length>=60.0 && all_length<=158.0)//不超尺寸
                {
                    if(the_package_weight>23.0&&the_package_weight<=28.0)//第一种超重情况
                    {
                        package_transport_price+=280.0;
                    }
                    else if(the_package_weight>28.0&&the_package_weight<=32.0)//第二种超重情况
                    {
                        package_transport_price+=690.0;
                    }

                    if(i==3||i==4)//超件第1,2件
                    {
                        package_transport_price+=1100.0;
                    }
                    else if(i>4)//三件以上
                    {
                        package_transport_price+=1590.0;
                    }
                }
                else if(all_length>158.0 && all_length<=203.0)//超尺寸
                {
                    if(the_package_weight>=2.0&&the_package_weight<=23.0)//第一种不超重情况
                    {
                        package_transport_price+=690.0;
                    }
                    else if(the_package_weight>23.0&&the_package_weight<=32.0)//第二种超重情况
                    {
                        package_transport_price+=1100.0;
                    }

                    if(i==3||i==4)//超件第1,2件
                    {
                        package_transport_price+=1100.0;
                    }
                    else if(i>4)//三件以上
                    {
                        package_transport_price+=1590.0;
                    }
                }
            }

        }
        else if(area.equals ("区域三"))
        {
            if(class_kind.equals ("头等舱")||class_kind.equals ("公务舱"))
            {
                if(passenger_kind.equals ("成人")||passenger_kind.equals ("儿童"))
                {
                    if(all_length>=60.0 && all_length<=158.0)
                    {
                        if(i==3||i==4)//超件第1,2件
                        {
                            package_transport_price+=1170.0;
                        }
                        else if(i>4)//三件以上
                        {
                            package_transport_price+=1590.0;
                        }
                    }
                    else if(all_length>158.0 && all_length<=203.0)//只有可能出现超过尺寸的情况
                    {
                        package_transport_price+=520.0;
                        if(i==3||i==4)//超件第1,2件
                        {
                            package_transport_price+=1170.0;
                        }
                        else if(i>4)//三件以上
                        {
                            package_transport_price+=1590.0;
                        }
                    }

                }
            }
            else if(class_kind.equals ("悦享经济舱")||class_kind.equals ("超级经济舱"))
            {
                if((all_length>=158.0 && all_length<=203.0)||(the_package_weight>23.0&&the_package_weight<=32.0))
                {
                    package_transport_price+=520.0;

                    if(i==3||i==4)//超件第1,2件
                    {
                        package_transport_price+=1170.0;
                    }
                    else if(i>4)//三件以上
                    {
                        package_transport_price+=1590.0;
                    }
                }
            }
            else if(class_kind.equals ("经济舱"))
            {
                if((all_length>=158.0 && all_length<=203.0)||(the_package_weight>23.0&&the_package_weight<=32.0))
                {
                    package_transport_price+=520.0;

                    if(i==2||i==3)//超件第1,2件
                    {
                        package_transport_price+=1170.0;
                    }
                    else if(i>3)//三件以上
                    {
                        package_transport_price+=1590.0;
                    }
                }

            }

        }
        else if(area.equals ("区域四"))
        {
            if(class_kind.equals ("头等舱")||class_kind.equals ("公务舱"))
            {
                if(passenger_kind.equals ("成人")||passenger_kind.equals ("儿童"))
                {
                    if(all_length>=60.0 && all_length<=158.0)
                    {
                        if(i==3||i==4)//超件第1件
                        {
                            package_transport_price+=1380.0;
                        }
                        else if(i>4)//三件以上
                        {
                            package_transport_price+=1590.0;
                        }
                    }
                    else if(all_length>158.0 && all_length<=203.0)//只有可能出现超过尺寸的情况
                    {
                        package_transport_price+=1040.0;
                        if(i==3||i==4)//超件第1件
                        {
                            package_transport_price+=1380.0;
                        }
                        else if(i>4)//三件以上
                        {
                            package_transport_price+=1590.0;
                        }
                    }

                }
            }
            else if(class_kind.equals ("悦享经济舱")||class_kind.equals ("超级经济舱"))
            {
                if(all_length>=60.0 && all_length<=158.0)//不超尺寸
                {
                    if(the_package_weight>23.0&&the_package_weight<=28.0)//第一种超重情况
                    {
                        package_transport_price+=690.0;
                    }
                    else if(the_package_weight>28.0&&the_package_weight<=32.0)//第二种超重情况
                    {
                        package_transport_price+=1040.0;
                    }

                    if(i==3||i==4)//超件第1件
                    {
                        package_transport_price+=1380.0;
                    }
                    else if(i>4)//三件以上
                    {
                        package_transport_price+=1590.0;
                    }
                }
                else if(all_length>158.0 && all_length<=203.0)//超尺寸
                {
                    if(the_package_weight>=2.0&&the_package_weight<=23.0)//第一种不超重情况
                    {
                        package_transport_price+=1040.0;
                    }
                    else if(the_package_weight>23.0&&the_package_weight<=32.0)//第二种超重情况
                    {
                        package_transport_price+=2050.0;
                    }

                    if(i==3||i==4)//超件第1件
                    {
                        package_transport_price+=1380.0;
                    }
                    else if(i>4)//三件以上
                    {
                        package_transport_price+=1590.0;
                    }

                }
            }
            else if(class_kind.equals ("经济舱"))
            {
                if(all_length>=60.0 && all_length<=158.0)//不超尺寸
                {
                    if(the_package_weight>23.0&&the_package_weight<=28.0)//第一种超重情况
                    {
                        package_transport_price+=690.0;
                    }
                    else if(the_package_weight>28.0&&the_package_weight<=32.0)//第二种超重情况
                    {
                        package_transport_price+=1040.0;
                    }

                    if(i==2||i==3)//超件第1件
                    {
                        package_transport_price+=1380.0;
                    }
                    else if(i>3)//三件以上
                    {
                        package_transport_price+=1590.0;
                    }
                }
                else if(all_length>158.0 && all_length<=203.0)//超尺寸
                {
                    if(the_package_weight>=2.0&&the_package_weight<=23.0)//第一种不超重情况
                    {
                        package_transport_price+=1040.0;
                    }
                    else if(the_package_weight>23.0&&the_package_weight<=32.0)//第二种超重情况
                    {
                        package_transport_price+=2050.0;
                    }

                    if(i==2||i==3)//超件第1件
                    {
                        package_transport_price+=1380.0;
                    }
                    else if(i>3)//三件以上
                    {
                        package_transport_price+=1590.0;
                    }

                }
            }

        }
        else if(area.equals ("区域五"))
        {
            if(class_kind.equals ("头等舱")||class_kind.equals ("公务舱"))
            {
                if(passenger_kind.equals ("成人")||passenger_kind.equals ("儿童"))
                {
                    if(all_length>=60.0 && all_length<=158.0)
                    {
                        if(i==3)//超件第1件
                        {
                            package_transport_price+=830.0;
                        }
                        else if(i==4)//超件第2件
                        {
                            package_transport_price+=1100.0;
                        }
                        else if(i>4)//三件以上
                        {
                            package_transport_price+=1590.0;
                        }
                    }
                    else if(all_length>158.0 && all_length<=203.0)//只有可能出现超过尺寸的情况
                    {
                        package_transport_price+=520.0;
                        if(i==3)//超件第1件
                        {
                            package_transport_price+=830.0;
                        }
                        else if(i==4)//超件第2件
                        {
                            package_transport_price+=1100.0;
                        }
                        else if(i>4)//三件以上
                        {
                            package_transport_price+=1590.0;
                        }
                    }

                }
            }
            else if(class_kind.equals ("悦享经济舱")||class_kind.equals ("超级经济舱"))
            {
                if(all_length>=60.0 && all_length<=158.0)//不超尺寸
                {
                    if(the_package_weight>23.0&&the_package_weight<=28.0)//第一种超重情况
                    {
                        package_transport_price+=210.0;
                    }
                    else if(the_package_weight>28.0&&the_package_weight<=32.0)//第二种超重情况
                    {
                        package_transport_price+=520.0;
                    }

                    if(i==3)//超件第1件
                    {
                        package_transport_price+=830.0;
                    }
                    else if(i==4)//超件第2件
                    {
                        package_transport_price+=1100.0;
                    }
                    else if(i>4)//三件以上
                    {
                        package_transport_price+=1590.0;
                    }
                }
                else if(all_length>158.0 && all_length<=203.0)//超尺寸
                {
                    if(the_package_weight>=2.0&&the_package_weight<=23.0)//第一种不超重情况
                    {
                        package_transport_price+=520.0;
                    }
                    else if(the_package_weight>23.0&&the_package_weight<=32.0)//第二种超重情况
                    {
                        package_transport_price+=830.0;
                    }

                    if(i==3)//超件第1件
                    {
                        package_transport_price+=830.0;
                    }
                    else if(i==4)//超件第2件
                    {
                        package_transport_price+=1100.0;
                    }
                    else if(i>4)//三件以上
                    {
                        package_transport_price+=1590.0;
                    }

                }
            }
            else if(class_kind.equals ("经济舱"))//限制一件免费
            {
                if(all_length>=60.0 && all_length<=158.0)//不超尺寸
                {
                    if(the_package_weight>23.0&&the_package_weight<=28.0)//第一种超重情况
                    {
                        package_transport_price+=210.0;
                    }
                    else if(the_package_weight>28.0&&the_package_weight<=32.0)//第二种超重情况
                    {
                        package_transport_price+=520.0;
                    }

                    if(i==2)//超件第1件
                    {
                        package_transport_price+=830.0;
                    }
                    else if(i==3)//超件第2件
                    {
                        package_transport_price+=1100.0;
                    }
                    else if(i>3)//三件以上
                    {
                        package_transport_price+=1590.0;
                    }
                }
                else if(all_length>158.0 && all_length<=203.0)//超尺寸
                {
                    if(the_package_weight>=2.0&&the_package_weight<=23.0)//第一种不超重情况
                    {
                        package_transport_price+=520.0;
                    }
                    else if(the_package_weight>23.0&&the_package_weight<=32.0)//第二种超重情况
                    {
                        package_transport_price+=830.0;
                    }

                    if(i==2)//超件第1件
                    {
                        package_transport_price+=830.0;
                    }
                    else if(i==3)//超件第2件
                    {
                        package_transport_price+=1100.0;
                    }
                    else if(i>3)//三件以上
                    {
                        package_transport_price+=1590.0;
                    }

                }
            }

        }

    }


    public static void main(String args[])
    {

        System.out.println ((true || true )&& false);

    }

}
