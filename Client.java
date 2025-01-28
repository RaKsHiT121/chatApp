package org.yourcompany.yourproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


class Client implements ActionListener{

    JTextField  text;
    static JPanel a1;
    static Box vertical=Box.createVerticalBox();
    static DataInputStream din;
    static DataOutputStream don;
    static JFrame f=new JFrame();
    Client(){
        f.setLayout(null);
        f. setSize(450,650);
        f.setLocation(650,60);
        f.getContentPane().setBackground(Color.WHITE);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p=new JPanel();
        p.setBackground(new Color(7,94,84)); 
        p.setBounds(0,0,450,50);
        p.setLayout(null);
        f.add(p);
        String imagePath = "C:/Users/useer/Desktop/icons/3.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image i2=imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(0,10,25,25);
        p.add(back);
        
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                //setVisible(false);
                System.exit(0);
            }
        });

        String imagePath2 = "C:/Users/useer/Desktop/icons/2.png";
        ImageIcon imageIcon1 = new ImageIcon(imagePath2);
        Image i4=imageIcon1.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);
        ImageIcon i5=new ImageIcon(i4);
        JLabel profile=new JLabel(i5);
        profile.setBounds(40,6,35,35);
        p.add(profile);

        String imagePath3 = "C:/Users/useer/Desktop/icons/video.png";
        ImageIcon imageIcon6 = new ImageIcon(imagePath3);
        Image i6=imageIcon6.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);
        ImageIcon i7=new ImageIcon(i6);
        JLabel video=new JLabel(i7);
        video.setBounds(280,6,35,35);
        p.add(video);

        String imagePath4 = "C:/Users/useer/Desktop/icons/phone.png";
        ImageIcon imageIcon8 = new ImageIcon(imagePath4);
        Image i8=imageIcon8.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel call=new JLabel(i9);
        call.setBounds(345,6,35,35);
        p.add(call);

        String imagePath5 = "C:/Users/useer/Desktop/icons/3icon.png";
        ImageIcon imageIcon10 = new ImageIcon(imagePath5);
        Image i12=imageIcon10.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
        ImageIcon i11=new ImageIcon(i12);
        JLabel dots=new JLabel(i11);
        dots.setBounds(400,10,10,20);
        p.add(dots);

        JLabel name=new JLabel("Client ");
        name.setBounds(90,10,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,16));
        p.add(name);

        a1=new JPanel();
        a1.setBounds(5,60,440,530);
        f.add(a1);

        text=new JTextField();
        text.setBounds(5,600,310,40);
        text.setFont(new Font("SAN_SERIF",Font.BOLD,16));
        f.add(text);

        JButton send=new JButton("SEND");
        send.setBounds(330,600,80,35);
        send.setForeground(Color.BLUE);
        send.setBackground(Color.GREEN);
        send.setFont(new Font("SAN_SERIF",Font.BOLD,16));
        f.add(send);
        send.addActionListener(this);

        f.setUndecorated(true);
        f.setVisible(true);
    } 

    public void actionPerformed(ActionEvent e){
        try{
        String chatString=text.getText();
        a1.setLayout(new BorderLayout());
        JPanel panel=format(chatString);
        f.add(panel);
        JPanel right=new JPanel(new BorderLayout());
        right.add(panel,BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));

        a1.add(vertical,BorderLayout.PAGE_START);

        don.writeUTF(chatString);
        text.setText("");
        f.repaint();
        f.invalidate();
        f.validate();}
        catch(Exception ae){
            ae.printStackTrace();
        }
    }
    public static JPanel format(String out){
        JPanel panel=new JPanel();

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel label=new JLabel("<html><p style=\"width: 150px\">"+ out+"</p></html>");
        label.setFont(new Font("Tahoma",Font.BOLD,16));
        label.setBackground(new Color(37,211,102));
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(label);
        
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);


        return panel;
    }
    
    public static void main(String[] args){
        new Client();
        try {
            Socket s=new Socket("127.0.0.1",6001);
            din=new DataInputStream(s.getInputStream());
            don=new DataOutputStream(s.getOutputStream());
            while(true){
                a1.setLayout(new BorderLayout());
                String msg=din.readUTF();
                JPanel panel=format(msg);


                JPanel left=new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);
                f.validate();
                

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

