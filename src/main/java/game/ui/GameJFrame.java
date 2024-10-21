package game.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


//游戏主界面
public class GameJFrame extends JFrame implements KeyListener,ActionListener{
    //创建一个二维数组用来管理图片数据，加载图片的时候会根据二维数组数据加载
    private int data[][]=new int[4][4];
    //x,y用来记录空白方块在二维数组中 的位置
    private int x=0;
    private int y=0;
    private String path="image\\animal\\animal1\\";
    //定义一个胜利后的数组用于与data数组进行比较
    private int[][]win ={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
        };
    //定义一个变量记录游戏用于统计步数
    private int step=0;
    //创建菜单项下的条目对象
    JMenuItem reolayItem = new JMenuItem("重新游戏");
    JMenuItem reloginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("公众号");
    JMenuItem gril = new JMenuItem("美女");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sport = new JMenuItem("运动");
    public GameJFrame(){
        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();
        //初始化数据（打乱图片）
        initData();
        //初始化图片
        initImage();

        //显示界面
        this.setVisible(true);
    }

    //初始化界面
    private void initJFrame() {
        //设置界面宽高
        this.setSize(603, 680);
        //设置标题
        this.setTitle("拼图单机版 v1.0");
        //设置置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认居中方式，只有取消了才会按照xy轴的方式添加组件
        this.setLayout(null);
        //添加事件
        this.addKeyListener(this);

    }
    //初始化菜单
    private void initJMenuBar() {
        //初始化菜单，设置菜单栏
        JMenuBar jMenuBar = new JMenuBar();

        //创建菜单项
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");
        JMenu changeImage=new JMenu("更换图片");

        //将每一个选项下的条目添加到选项中
        //功能模块
        functionJMenu.add(changeImage);
        functionJMenu.add(reolayItem);
        functionJMenu.add(reloginItem);
        functionJMenu.add(closeItem);
        //关于我们模块
        aboutJMenu.add(accountItem);
        //更换图片模块
        changeImage.add(gril);
        changeImage.add(animal);
        changeImage.add(sport);

        //为每个选项绑定点击事件
        reolayItem.addActionListener(this);
        reloginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        gril.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);
        //将选项添加到菜单中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);
        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }
    //初始化数据（打乱图片）
    private void initData() {
        int []array={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        //打乱数组中的数据的顺序
        Random r=new Random();

        //将数组中的每一个数据与随机索引上的数据进行交换
        for(int i=0;i<array.length;i++){
            //获取随机索引
            int index=r.nextInt(array.length);
            int temp=array[i];
            array[i]=array[index];
            array[index]=temp;
        }
        //给二维数组添加数据
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                //判断是不是空白方块，如果是记录其坐标
                if(array[i*4+j]==0){
                    x=i;
                    y=j;
                }
                data[i][j]=array[i*4+j];
            }
        }

    }
    //初始化图片
    private void initImage() {
        //清空所有已经出现的图片
        this.getContentPane().removeAll();
        //判断如果游戏胜利则显示胜利图标
        if(checkWin()){
            JLabel winLabel=new JLabel(new ImageIcon("image\\win.png"));
            winLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winLabel);
        }

        //实现步数统计功能
        JLabel stepCount=new JLabel("步数："+step);
        stepCount.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepCount);
        //设置一段文字提醒用户键盘按下A可查看完整图片
        JLabel tips = new JLabel("按住键盘A可查看完整图片");
        tips.setBounds(430, 30, 150, 20);
        this.getContentPane().add(tips);

        //加载图片
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                //获取当前要加载图像的序号
                int num=data[i][j];
                //创建一个ImageIcon对象
                //创建一个Jlabel对象--管理容器
                JLabel jlabel=new JLabel(new ImageIcon(path+num+".jpg"));
                //指定图片位置
                jlabel.setBounds(105*j+83, 105*i+134, 105, 105);
                //给图片添加边框
                jlabel.setBorder(new BevelBorder(1));//0：表示让图片凸起来 1：表示让图片凹下去
                //将管理容器添加至界面之中
                this.getContentPane().add(jlabel);
            }
        }
        //添加背景图片
        //注：先加载的图片在上方，后加载的图片在下方
        JLabel background=new JLabel(new ImageIcon("image\\background.png"));
        //指定图片位置
        background.setBounds(40, 40, 508, 560);
        //将管理容器添加至界面之中
        this.getContentPane().add(background);
        //刷新界面
        this.getContentPane().repaint();

    }
    //鼠标事件方法实现
    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前点击对象
    Object obj=e.getSource();
    if(obj==reolayItem){
        System.out.println("重新游戏");
        //计步器清零
        step=0;
        //重新生成数据
        initData();
        //重新生成图片
        initImage();

    }else if(obj==reloginItem){
        System.out.println("重新登录");
        //关闭当前界面
        this.setVisible(false);
        //打开登录界面
        new LoginJFrame();

    }else if(obj==closeItem){
        System.out.println("关闭游戏");
        System.exit(0);

    }else if(obj==accountItem){
        System.out.println("关于我们");
        //创建一个单框对象
        JDialog about=new JDialog(this,"有问题加微信联系我");
        //创建一个管理图片的容器对象JLable
        JLabel jlabel=new JLabel(new ImageIcon("image\\about.png"));
        //指定图片位置
        jlabel.setBounds(0, 0, 258, 258);
        //将管理容器添加至对话框中
        about.getContentPane().add(jlabel);
        //给弹框设置大小
        about.setSize(344,344);
        //让弹框置顶
        about.setAlwaysOnTop(true);
        //让弹框居中
        about.setLocationRelativeTo(null);
        //弹框不关闭则无法操作下面的界面
        about.setModal(true);
        //显示弹框
        about.setVisible(true);
    }else if(obj==gril){
        Random r=new Random();
        int num1=r.nextInt(13)+1;
        System.out.println(num1);
        path="image\\girl\\girl"+num1+"\\";
        //计步器清零
        step=0;
        //重新生成数据
        initData();
        //重新生成图片
        initImage();

    }else if(obj==animal){
        Random r=new Random();
        int num2=r.nextInt(8)+1;
        System.out.println(num2);
        path="image\\animal\\animal"+num2+"\\";
        //计步器清零
        step=0;
        //重新生成数据
        initData();
        //重新生成图片
        initImage();
    }else if(obj==sport){
        Random r=new Random();
        int num3=r.nextInt(10)+1;
        System.out.println(num3);
        path="image\\sport\\sport"+num3+"\\";
        //计步器清零
        step=0;
        //重新生成数据
        initData();
        //重新生成图片
        initImage();
    }

}


    //键盘事件方法实现
    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    //键盘按键按下不松触发事件
    public void keyPressed(KeyEvent e) {
        //实现按住键盘A不松时显示完整图片
        int code=e.getKeyCode();
        System.out.println(code);
        if(code==65){
            //把界面中所有图片删除
            this.getContentPane().removeAll();
            //加载完整图片
            JLabel all=new JLabel(new ImageIcon(path+"all.jpg"));
            //指定图片位置
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);
            //加载背景图片
            JLabel background=new JLabel(new ImageIcon("image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            this.getContentPane().repaint();
        }

    }

    @Override
    //键盘按键松开触发事件
    public void keyReleased(KeyEvent key) {
        //对上下左右进行判断---左：37  上：38  右：39   下：40
        int code=key.getKeyCode();
        System.out.println(code);
        //将白色方块上方的图片往下移动
        if(code==38){
            if(x==0){
                System.out.println("已达上边界无法移动");
                showJdialog();
                return;
            }
            data[x][y]=data[x-1][y];
            data[x-1][y]=0;
            x--;
            //每移动一次，计数器就自增一次
            step++;
            initImage();
            System.out.println("向上移动");
        }else if(code==40){
            //将白色方块下方的图片往上移动
            if(x==3){
                System.out.println("已达下边界无法移动");
                showJdialog();
                return;
            }
            data[x][y]=data[x+1][y];
            data[x+1][y]=0;
            x++;
            //每移动一次，计数器就自增一次
            step++;
            initImage();
            System.out.println("向下移动");
        }
        else if(code==37){
            if(y==0){
                System.out.println("已达左边界无法移动");
                showJdialog();
                return;
            }
            //将白色方块左方的图片往右移动
            data[x][y]=data[x][y-1];
            data[x][y-1]=0;
            y--;
            //每移动一次，计数器就自增一次
            step++;
            initImage();
            System.out.println("向左移动");
        }
        else if(code==39){
            if(y==3){
                System.out.println("已达右边界无法移动");
                showJdialog();
                return;
            }
            //将白色方块右方的图片往左移动
            data[x][y]=data[x][y+1];
            data[x][y+1]=0;
            y++;
            //每移动一次，计数器就自增一次
            step++;
            initImage();
            System.out.println("向右移动");
        }else if(code==65){
            //松开键盘按键A时加载图片
            initImage();
        }else if(code==87){
            //松开W键图片正常排列
            data=new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
            initImage();
        }

    }
    //判断游戏是否胜利
    public boolean checkWin(){
        //检查data数组内容是否和胜利时的数组内容相同
        for (int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[i].length; j++) {
                if(data[i][j]!=win[i][j])
                    return false;

            }
        }
        return true;

    }
    //移动时到达边界时设置一个单框用来提示
    public void showJdialog(){
        //创建一个单框对象
        JDialog about=new JDialog(this,"Error!!!");
        //创建一个包含文字的的容器对象JLable
        JLabel jlabel=new JLabel("已达边界无法移动!");
        //设置文字居中
        jlabel.setHorizontalAlignment(JLabel.CENTER);
        //设置文字颜色为红色
        jlabel.setForeground(Color.red);
        //指定容器位置
        jlabel.setBounds(0, 0, 50, 50);
        //将管理容器添加至对话框中
        about.getContentPane().add(jlabel);
        //给弹框设置大小
        about.setSize(100,100);
        //让弹框置顶
        about.setAlwaysOnTop(true);
        //让弹框居中
        about.setLocationRelativeTo(null);
        //弹框不关闭则无法操作下面的界面
        about.setModal(true);
        //显示弹框
        about.setVisible(true);
    }




}