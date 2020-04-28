package window;

import caculate.Packages;
import caculate.Ticket;
import caculate.caculate_tool;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class caculate_window {
    private JFrame jFrame;
    private JPanel panel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;

    private JComboBox box1;
    private JComboBox box2;
    private JTextField text3;
    private JComboBox box4;
    private JComboBox box5;

    private JTextArea textarea;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    private JButton start;
    private JButton add_package;
    private JButton delete_package;
    private JButton help;
    private JButton do_caculate;
    private JCheckBox checkBox;
    private static int i_package=0;
    private List<Packages> passenger_Package;//行李集合
    private Ticket ticket;


    public caculate_window() throws IOException {
        passenger_Package = new ArrayList<Packages> ();
        panel = new JPanel();
        jFrame = new JFrame("行李托运费用计算器");
        jFrame.setBounds(200,200,760,550);
        jFrame.setContentPane(panel);
        jFrame.setLayout(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label1=new JLabel ("注意事项:");
        label1.setBounds(10,20,60,40);
        panel.add (label1);

        String pathname = "注意事项.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        File filename = new File(pathname); // 要读取以上路径的input。txt文件
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream (filename)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        String line = "";
        String all="";
        line = br.readLine();
        while (line != null) {
            all+=line+"\n";
            line = br.readLine(); // 一次读入一行数据
        }
        textarea = new JTextArea ();
        textarea.setFont(new Font("微软雅黑", 0, 14));
        textarea.setBounds(80,30,600,400);
        textarea.setText (all);
        textarea.setLineWrap(true);//自动换行
        textarea.setEditable(false);//不可编辑
        panel.add(textarea);

        scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(80, 30, 600, 400);
        scrollPane1.setViewportView(textarea);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane1);

        start=new JButton ("开始");
        start.setBounds (400,450,100,30);
        start.setEnabled (false);
        start.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                main_window();
            }
        });
        panel.add (start);

        checkBox = new JCheckBox("已阅读");
        checkBox.setBounds (200,450,100,30);
        panel.add (checkBox);
        checkBox.addChangeListener (new ChangeListener () {
            @Override
            public void stateChanged(ChangeEvent e) {
                // 获取事件源（即复选框本身）
                JCheckBox checkBox = (JCheckBox) e.getSource ();
                if(checkBox.isSelected ())
                {
                    start.setEnabled (true);
                }
                else
                {
                    start.setEnabled (false);
                }
            }
        });

        jFrame.setVisible (true);

    }


    public void main_window()
    {
        jFrame.remove (label1);
        jFrame.remove (textarea);
        jFrame.remove (scrollPane1);
        jFrame.remove (start);
        jFrame.remove (checkBox);

        String[] inorout_china=new String[]{"国内航班","国外航班"};
        label2=new JLabel ("航班类型:");
        label2.setBounds(10,40,100,50);
        panel.add (label2);
        box1 = new JComboBox (inorout_china);
        box1.setBounds(100,55,100,20);
        panel.add (box1);

        String[] area=new String[]{"区域一","区域二","区域三","区域四","区域五"};
        label3=new JLabel ("航班区域:" );
        label3.setBounds(10,120,100,50);
        panel.add (label3);
        box2= new JComboBox (area);
        box2.setBounds(100,135,100,20);
        panel.add (box2);

        label4=new JLabel ("航班票价:");
        label4.setBounds(10,200,100,50);
        panel.add (label4);
        text3 = new JTextField ();
        text3.setBounds(100,215,100,20);
        panel.add (text3);

        String[] class_kind=new String[]{"豪华头等舱","头等舱","公务舱","悦享经济舱","超级经济舱","经济舱"};
        label5=new JLabel ("座舱类型:");
        label5.setBounds(10,280,100,50);
        panel.add (label5);
        box4 = new JComboBox (class_kind);
        box4.setBounds(100,295,100,20);
        panel.add (box4);

        String[] passenge_kind=new String[]{"成人","儿童","婴儿"};
        label6=new JLabel ("旅客类型:");
        label6.setBounds(10,360,100,50);
        panel.add (label6);
        box5 = new JComboBox (passenge_kind);
        box5.setBounds(100,375,100,20);
        panel.add (box5);

        JLabel label7=new JLabel ("计算结果:");
        label7.setBounds(400,360,100,50);
        panel.add (label7);
        final JTextArea last=new JTextArea ();
        last.setText ("0.0");
        last.setEditable (false);
        last.setBounds(500,375,100,20);
        panel.add (last);

        final Vector vData = new Vector();
        final Vector vName = new Vector();
        vName.add("件数(第i件)");
        vName.add("长(cm)");
        vName.add("宽(cm)");
        vName.add("高(cm)");
        vName.add("重量(kg)");

        final DefaultTableModel model = new DefaultTableModel(vData, vName);
        final JTable jTable1 = new JTable();
        jTable1.setModel(model);

        scrollPane2=new JScrollPane();
        scrollPane2.setBounds(300,50,400,250);
        scrollPane2.setViewportView(jTable1);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane2);

        help=new JButton ("帮助");
        help.setBounds (50,450,100,20);
        help.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 消息对话框无返回, 仅做通知作用
                JOptionPane.showMessageDialog(
                        jFrame,
                        "区域一：美洲（除美国/加拿大外）/加勒比海地区与欧洲/非洲/中东/亚洲/西南太平洋之间的航线；\n" +
                                "区域二：欧洲/中东与非洲/亚洲/西南太平洋之间航线；日本与西南太平洋之间航线；\n"+
                                "                 日本/西南太平洋与亚洲（不含日本及西南太平洋）/非洲之间航线；\n"+
                                "区域三：加拿大与美洲（除美国/加拿大外）/加勒比海地区/欧洲/非洲/中东/亚洲/西南太平洋之间航线；\n" +
                                "区域四：美国（含夏威夷）与美洲（除美国外）/加勒比海地区/欧洲/非洲/中东/亚洲/西南太平洋之间航线；\n"+
                                "区域五：非洲与亚洲（除日本外)之间航线；欧洲与中东之间航线；亚洲（除日本)内航线；\n"+
                                "                 美洲（除美国/加拿大）及加勒比海地区内航线；上述未列明的航线;\n"
                        ,
                        "帮助",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        panel.add (help);


        add_package=new JButton ("增加行李");
        add_package.setBounds (200,450,100,20);
        add_package.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                final JFrame JF=new JFrame ("增加行李");
                JF.setBounds(300,300,500,150);
                JF.setLayout(null);
                JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JLabel label1=new JLabel ("长(cm)");
                label1.setBounds (10,15,60,20);
                JF.add (label1);
                final JTextField text1=new JTextField ();
                text1.setBounds (60,15,30,20);
                JF.add (text1);

                JLabel label2=new JLabel ("宽(cm)");
                label2.setBounds (110,15,60,20);
                JF.add (label2);
                final JTextField text2=new JTextField ();
                text2.setBounds (160,15,30,20);
                JF.add (text2);

                JLabel label3=new JLabel ("高(cm)");
                label3.setBounds (210,15,60,20);
                JF.add (label3);
                final JTextField text3=new JTextField ();
                text3.setBounds (260,15,30,20);
                JF.add (text3);

                JLabel label4=new JLabel ("重量(kg)");
                label4.setBounds (310,15,60,20);
                JF.add (label4);
                final JTextField text4=new JTextField ();
                text4.setBounds (370,15,30,20);
                JF.add (text4);

                String  l="";
                String wid="";
                String h="";
                String wei="";
                JButton button=new JButton ("确定");
                button.setBounds (370,60,60,20);
                button.addActionListener(new ActionListener () {
                    public void actionPerformed(ActionEvent e) {
                        last.setText ("0.0");
                        String  l=text1.getText ();
                        String wid=text2.getText ();
                        String h=text3.getText ();
                        String wei=text4.getText ();

                        if(l.equals ("")||wid.equals ("")||h.equals ("")||wei.equals (""))
                        {
                            JF.dispose ();
                            JOptionPane.showMessageDialog(
                                    jFrame,
                                    "长度，宽度，高度或者重量都不能为空！！",
                                    "警告",
                                    JOptionPane.WARNING_MESSAGE
                            );
                            return;
                        }

                        if(Double.parseDouble (l)+Double.parseDouble (wid)+Double.parseDouble (h)<60||Double.parseDouble (l)+Double.parseDouble (wid)+Double.parseDouble (h)>203)
                        {
                            JF.dispose ();
                            JOptionPane.showMessageDialog(
                                    jFrame,
                                    "输入的行李总尺寸不符合要求（要求大于等于60cm或者且小于等于203cm）！！",
                                    "警告",
                                    JOptionPane.WARNING_MESSAGE
                            );
                            return;

                        }

                        if(Double.parseDouble (wei)<2)
                        {
                            JF.dispose ();
                            JOptionPane.showMessageDialog(
                                    jFrame,
                                    "输入的行李重量不符合要求（要求大于等于2kg或者且小于等于32kg）！！",
                                    "警告",
                                    JOptionPane.WARNING_MESSAGE
                            );
                            return;
                        }
                        else if(Double.parseDouble (wei)>32)
                        {
                            JF.dispose ();
                            JOptionPane.showMessageDialog(
                                    jFrame,
                                    "大于32kg的行李需要分两份！！",
                                    "警告",
                                    JOptionPane.WARNING_MESSAGE
                            );
                            return;
                        }

                        if(box1.getSelectedItem().toString ().equals ("国内航班"))
                        {
                            if(Double.parseDouble (l)>100||Double.parseDouble (wid)>60||Double.parseDouble (h)>40)
                            {
                                JF.dispose ();
                                JOptionPane.showMessageDialog(
                                        jFrame,
                                        "国内航班所输入的行李尺寸不符合要求（长要求小于等于100cm，宽要求小于等于60cm，高要求小于等于40cmw）！！",
                                        "警告",
                                        JOptionPane.WARNING_MESSAGE
                                );
                                return;
                            }
                            else
                            {
                                if(!add_pack(l,wid,h,wei).equals("正确类型可以添加行李"))
                                {
                                    JF.dispose ();
                                    JOptionPane.showMessageDialog(
                                            jFrame,
                                            add_pack(l,wid,h,wei),
                                            "警告",
                                            JOptionPane.WARNING_MESSAGE
                                    );
                                    return;
                                }
                                i_package++;
                                Vector newvRow = new Vector();
                                newvRow.add(i_package);
                                newvRow.add(l);
                                newvRow.add(wid);
                                newvRow.add(h);
                                newvRow.add(wei);
                                vData.add(newvRow.clone());
                                model.setDataVector (vData, vName);
                                jTable1.setModel(model);

                                JF.dispose ();
                            }

                        }
                        else
                        {
                            if(!add_pack(l,wid,h,wei).equals("正确类型可以添加行李"))
                            {
                                JF.dispose ();
                                JOptionPane.showMessageDialog(
                                        jFrame,
                                        add_pack(l,wid,h,wei),
                                        "警告",
                                        JOptionPane.WARNING_MESSAGE
                                );
                                return;
                            }
                            i_package++;
                            Vector newvRow = new Vector();
                            newvRow.add(i_package);
                            newvRow.add(l);
                            newvRow.add(wid);
                            newvRow.add(h);
                            newvRow.add(wei);
                            vData.add(newvRow.clone());
                            model.setDataVector (vData, vName);
                            jTable1.setModel(model);

                            JF.dispose ();
                        }

                    }
                });
                JF.add (button);
                JF.setVisible (true);

            }
        });
        panel.add (add_package);

        delete_package=new JButton ("删除行李");
        delete_package.setBounds (400,450,100,20);
        delete_package.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                  last.setText ("0.0");
                  if(jTable1.getSelectedRow()!=-1)
                  {
                      i_package--;
                      int col = jTable1.getSelectedColumn();//获取知选中的列号
                      int row = jTable1.getSelectedRow();//获取选中的行号
                      int rows = jTable1.getRowCount();

                      passenger_Package.remove (row);//删除行李行李集合中的行李

                      for(int i=row;i<rows;i++)
                      {
                          Object val = model.getValueAt(i, col);
                          model.setValueAt(Integer.parseInt (val.toString ().trim ())-1, i, col);//修改某单元格的值
                      }

                      int numrow=jTable1.getSelectedRows().length;
                      for (int i=0;i<numrow;i++){
                          model.removeRow(jTable1.getSelectedRow());
                      }



                  }

            }
        });
        panel.add (delete_package);


        do_caculate=new JButton ("计算");
        do_caculate.setBounds (580,450,100,20);
        do_caculate.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 消息对话框无返回, 仅做通知作用
                String inorout_china=box1.getSelectedItem().toString ().trim ();
                String area=box2.getSelectedItem().toString ().trim ();
                String class_kind=box4.getSelectedItem().toString ().trim ();
                String passenge_kind=box5.getSelectedItem().toString ().trim ();
                String ticket_price= text3.getText ();
                if(ticket_price.equals (""))
                {
                    JOptionPane.showMessageDialog(
                            jFrame,
                            "票价不能为空！！",
                            "警告",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                if(inorout_china.equals (""))
                {
                    JOptionPane.showMessageDialog(
                            jFrame,
                            "类型不能为空！！",
                            "警告",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                if(area.equals (""))
                {
                    JOptionPane.showMessageDialog(
                            jFrame,
                            "区域不能为空！！",
                            "警告",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                if(class_kind.equals (""))
                {
                    JOptionPane.showMessageDialog(
                            jFrame,
                            "座舱类型不能为空！！",
                            "警告",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                if(passenge_kind.equals (""))
                {
                    JOptionPane.showMessageDialog(
                            jFrame,
                            "乘客类型不能为空！！",
                            "警告",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                if(passenger_Package.size ()==0)
                {
                    JOptionPane.showMessageDialog(
                            jFrame,
                            "托运行李不能为空！！",
                            "警告",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

                if(!inorout_china.equals ("")&&!area.equals ("")&&!class_kind.equals ("")&&!ticket_price.equals ("")&&!passenge_kind.equals (""))
                {
                    if(!add_ticket(inorout_china,area,class_kind,ticket_price,passenge_kind).equals("正确类型可以机票"))
                    {
                        JOptionPane.showMessageDialog(
                                jFrame,
                                add_ticket(inorout_china,area,class_kind,ticket_price,passenge_kind),
                                "警告",
                                JOptionPane.WARNING_MESSAGE
                        );

                    }
                    caculate_tool ca=new caculate_tool (passenger_Package,ticket);
                    ca.do_caluate ();
                    Double dou_obj = new Double(ca.to_all_tran_price ());
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setGroupingUsed(false);
                    String dou_str = nf.format(dou_obj);
                    last.setText (dou_str);
                }

                for(int i=0;i<passenger_Package.size ();i++)
                {
                    System.out.println (passenger_Package.get (i).To_length ());
                }
                System.out.println (inorout_china+" "+area+" "+class_kind+" "+passenge_kind+" "+ticket_price);
            }
        });
        panel.add (do_caculate);

        jFrame.validate ();
        jFrame.repaint ();
        jFrame.setVisible (true);





    }

    public String add_pack(String l,String wid,String h,String wei)
    {
        if(isNumeric(l)&&isNumeric(wid)&&isNumeric(h)&&isNumeric(wei))
        {
            double l_=Double.parseDouble (l.trim ());
            double wid_=Double.parseDouble (wid.trim ());
            double h_=Double.parseDouble (h.trim ());
            double wei_=Double.parseDouble (wei.trim ());
            if(l_+wid_+h_<60.0)
            {
                return "行李总长度不能小于60.0cm";
            }
            else if(l_+wid_+h_>203.0)
            {
                return "行李总长度不能大于203.0cm";
            }
            else if(wei_<2.0)
            {
                return "行李重量不能小于2kg";
            }
            else if(wei_>32.0)
            {
                return "行李重量不能大于32kg";
            }
            else
            {
                Packages new_package=new Packages(Double.parseDouble (l.trim ()),Double.parseDouble (wid.trim ()),Double.parseDouble (h.trim ()),Double.parseDouble (wei.trim ()));
                passenger_Package.add (new_package);
            }
        }
        else
        {
            return "非DOUBLE类型不允许";
        }
        return "正确类型可以添加行李";
    }

    public String add_ticket(String inorout_china,String area,String class_kind,String ticket_price,String passenge_kind)
    {
        if(isNumeric(ticket_price))
        {
            double ticket_price_=Double.parseDouble (ticket_price.trim ());

            if(ticket_price_<0.0)
            {
                return "票价负数不允许";
            }
            else
            {
                ticket=new Ticket (inorout_china,area,class_kind,Double.parseDouble (ticket_price),passenge_kind);
            }
        }
        else
        {
            return "非DOUBLE类型不允许";
        }
        return "正确类型可以机票";
    }

    public static boolean isNumeric(final String str) {
        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }

        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException ex) {
                try {
                    Float.parseFloat(str);
                    return false;
                } catch (NumberFormatException exx) {
                    return false;
                }
            }
        }
    }

    public static void main(String args[]) throws IOException {
        caculate_window window=new caculate_window ();

    }
}
