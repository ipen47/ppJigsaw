package game.ui;

import Util.CodeUtil;
import dao.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

//注册界面
public class RegistJFrame extends JFrame implements MouseListener {
    //定义注册按钮和重置按钮
    private  JButton register = new JButton();
    private  JButton reset = new JButton();
    //定义注册用户名输入框
    private   JTextField username = new JTextField();
    //定义注册密码输入框
    private JPasswordField password = new JPasswordField();
    //定义再次输入密码输入框
    private JPasswordField repassword = new JPasswordField();

    public RegistJFrame() {

        //界面初始化
        initJFrame();
        //往界面中添加内容
        initView();
        //显示界面
        this.setVisible(true);
    }

    //界面初始化
    public void initView(){
        //1. 添加注册用户名文字
        JLabel usernameLabel = new JLabel(new ImageIcon("image\\register\\注册用户名.png"));
        usernameLabel.setBounds(105, 135, 79, 17);
        this.getContentPane().add(usernameLabel);
        //2. 设置注册用户名输入框
        username.setBounds(195, 134, 200, 30);
        this.getContentPane().add(username);

        //3.添加注册密码文字
        JLabel passwordLabel = new JLabel(new ImageIcon("image\\register\\注册密码.png"));
        passwordLabel.setBounds(120, 195, 64, 16);
        this.getContentPane().add(passwordLabel);
        //4. 设置密码输入框
        password.setBounds(195, 195, 200, 30);
        this.getContentPane().add(password);
        //5、再次输入密码提示
        JLabel codeText = new JLabel(new ImageIcon("image\\register\\再次输入密码.png"));
        codeText.setBounds(88, 256, 96, 17);
        this.getContentPane().add(codeText);
        //设置再次输入密码的输入框
        repassword.setBounds(195, 256,200, 30);
        this.getContentPane().add(repassword);
        //设置注册按钮
        register.setBounds(123, 310, 128, 47);
        register.setIcon(new ImageIcon("image\\register\\注册按钮.png"));
        //去除按钮的默认边框
        register.setBorderPainted(false);
        //去除按钮的默认背景
        register.setContentAreaFilled(false);
        this.getContentPane().add(register);
        register.addMouseListener(this);
        //设置重置按钮
        reset.setBounds(256, 310, 128, 47);
        reset.setIcon(new ImageIcon("image\\register\\重置按钮.png"));
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        this.getContentPane().add(reset);
        reset.addMouseListener(this);
        //添加背景图片
        JLabel background=new JLabel(new ImageIcon("image\\register\\background.png"));
        //指定图片位置
        background.setBounds(0, 0, 470, 390);
        //将管理容器添加至界面之中
        this.getContentPane().add(background);

    }
    //往界面中添加内容
    private void initJFrame() {
        //设置界面宽高
        this.setSize(488, 430);
        //设置标题
        this.setTitle("拼图游戏 V1.0注册");
        //设置置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消内部默认布局
        this.setLayout(null);
    }
    //用弹框展示错误信息
    public void showJDialog(String title){

        //创建一个单框对象
        JDialog about=new JDialog(this,"注册失败!!!");
        //创建一个包含文字的的容器对象JLable
        JLabel jlabel=new JLabel(title);
        //设置文字居中
        jlabel.setHorizontalAlignment(JLabel.CENTER);
        //设置文字颜色为红色
        jlabel.setForeground(Color.red);
        //指定容器位置
        jlabel.setBounds(0, 0, 100, 100);
        //将管理容器添加至对话框中
        about.getContentPane().add(jlabel);
        //给弹框设置大小
        about.setSize(200,150);
        //让弹框置顶
        about.setAlwaysOnTop(true);
        //让弹框居中
        about.setLocationRelativeTo(null);
        //弹框不关闭则无法操作下面的界面
        about.setModal(true);
        //显示弹框
        about.setVisible(true);

    }
    //检验用户名是否规范
    public boolean checkUsername(String username){
        //用户名长度必须在2-12位
        int len=username.length();
        if(len<2||len>12)
            return false;
        return true;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        try{

            if(e.getSource()==register){
                //连接数据库
                JDBC jdbc = new JDBC();
                jdbc.getConnection();
                String usernameText=username.getText();
                String passwordText=password.getText();
                String repasswordText=repassword.getText();
                if(usernameText.isEmpty()||passwordText.isEmpty()){
                    showJDialog("用户名和密码不能为空！！！");
                }else if(!checkUsername(usernameText)){
                    showJDialog("用户名长度必须在2-12位范围内");
                } else if(passwordText.equals(repasswordText)){
                    if(jdbc.queryuser(usernameText,passwordText)){
                        showJDialog("用户名已存在，请登录！");
                    }else{
                        jdbc.insert(usernameText,passwordText);
                        showJDialog("注册成功！！！");
                    }
                    this.setVisible(false);
                    new LoginJFrame();
                }else {
                    showJDialog("两次输入的密码不一致！！！");
                }

            }
            if(e.getSource()==reset){
                username.setText("");
                password.setText("");
                repassword.setText("");
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
       if (e.getSource() == register) {
           register.setIcon(new ImageIcon("image\\register\\注册按下.png"));
       }else if (e.getSource() == reset) {
           reset.setIcon(new ImageIcon("image\\register\\重置按下.png"));
       }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
       if (e.getSource() == register) {
           register.setIcon(new ImageIcon("image\\register\\注册按钮.png"));
       }else if (e.getSource() == reset) {
           reset.setIcon(new ImageIcon("image\\register\\重置按钮.png"));
       }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
