package game.ui;

import Util.CodeUtil;
import dao.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

//登录界面
public class LoginJFrame extends JFrame implements MouseListener  {
    //定义登录按钮和注册按钮
    private  JButton login = new JButton();
    private  JButton register = new JButton();
    //定义用户名输入框
    private JTextField username = new JTextField();
    //定义密码输入框
    private JPasswordField password = new JPasswordField();
    //定义验证码输入框
    JTextField code = new JTextField();
    //获取随机验证码
    String codeStr = CodeUtil.getCode();
    //定义一个JLabel装验证码
    JLabel rightCode = new JLabel();
    public LoginJFrame(){
        //界面初始化
        initJFrame();
        //往界面中添加内容
        initView();
        //显示界面
        this.setVisible(true);
    }
    public void initJFrame(){
        //设置界面宽高
        this.setSize(488, 430);
        //设置标题
        this.setTitle("拼图游戏 V1.0登录");
        //设置置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消内部默认布局
        this.setLayout(null);

    }
    public void initView(){
        //1. 添加用户名文字
        JLabel usernameLabel = new JLabel(new ImageIcon("image\\login\\用户名.png"));
        usernameLabel.setBounds(133, 135, 47, 17);
      this.getContentPane().add(usernameLabel);
        //2. 设置用户名输入框

        username.setBounds(195, 134, 200, 30);
        this.getContentPane().add(username);

        //3.添加密码文字
        JLabel passwordLabel = new JLabel(new ImageIcon("image\\login\\密码.png"));
        passwordLabel.setBounds(147, 195, 32, 16);
        this.getContentPane().add(passwordLabel);
        //4. 设置密码输入框

        password.setBounds(195, 195, 200, 30);
        this.getContentPane().add(password);
        //验证码提示
        JLabel codeText = new JLabel(new ImageIcon("image\\login\\验证码.png"));
        codeText.setBounds(133, 256, 50, 30);
        this.getContentPane().add(codeText);
        //设置验证码的输入框
        code.setBounds(195, 256, 100, 30);
        this.getContentPane().add(code);

        rightCode.addMouseListener(this);
        //设置验证码内容
        rightCode.setText(codeStr);
        //验证码位置和宽高
        rightCode.setBounds(300, 256, 50, 30);
        //添加到界面
        this.getContentPane().add(rightCode);
        //设置登录按钮
        login.setBounds(123, 310, 128, 47);
        login.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
        //去除按钮的默认边框
        login.setBorderPainted(false);
        //去除按钮的默认背景
        login.setContentAreaFilled(false);
        this.getContentPane().add(login);
        login.addMouseListener(this);

        //设置注册按钮
        register.setBounds(256, 310, 128, 47);
        register.setIcon(new ImageIcon("image\\login\\注册按钮.png"));
        register.setBorderPainted(false);
        register.setContentAreaFilled(false);
        this.getContentPane().add(register);
        register.addMouseListener(this);


        //添加背景图片
        JLabel background=new JLabel(new ImageIcon("image\\login\\background.png"));
        //指定图片位置
        background.setBounds(0, 0, 470, 390);
        //将管理容器添加至界面之中
        this.getContentPane().add(background);




    }
    //要展示用户名或密码错误
    public void showJDialog(String title){

        //创建一个单框对象
        JDialog about=new JDialog(this,"登录失败!!!");
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

    @Override
    //点击登录和注册时的方法实现
    public void mouseClicked(MouseEvent e) {
      try{

          if(e.getSource()==login){
              //连接数据库
              JDBC jdbc = new JDBC();
              jdbc.getConnection();
              //TODO 点击登录时验证
              String usernameText=username.getText();
              String passwordText=password.getText();
              String codeText=code.getText();

              if(usernameText.isEmpty() || passwordText.isEmpty())
              {
                  showJDialog("用户名和密码不能为空！！！");
              }else if(codeText.equals(codeStr)){
                  if( jdbc.queryuser(usernameText, passwordText)){
                      new GameJFrame();
                      this.setVisible(false);
                  }else{
                      showJDialog("用户名或密码输入错误！！！");
                      username.setText("");
                      password.setText("");

                  }
              }else {
                  showJDialog("验证码输入错误");
                  code.setText("");
              }

          } else if(e.getSource()==register){
              //TODO 点击注册时跳转到注册界面
              new RegistJFrame();
              this.setVisible(false);
          }else if(e.getSource()==rightCode){
              //刷新验证码
              System.out.println("111");
              codeStr=CodeUtil.getCode();
              rightCode.setText(codeStr);

          }
      } catch (SQLException ex) {
          throw new RuntimeException(ex);
      } catch (ClassNotFoundException ex) {
          throw new RuntimeException(ex);
      }

    }

    @Override
    //鼠标按下登录和注册按钮时
    public void mousePressed(MouseEvent e) {
      if(e.getSource()==login){
          login.setIcon(new ImageIcon("image\\login\\登录按下.png"));
      }else if(e.getSource()==register){
          register.setIcon(new ImageIcon("image\\login\\注册按下.png"));
      }

    }

    @Override
    //鼠标松开登录和注册按钮时
    public void mouseReleased(MouseEvent e) {
       if(e.getSource()==login){
           login.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
       }else if (e.getSource() == register) {
            register.setIcon(new ImageIcon("image\\login\\注册按钮.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
