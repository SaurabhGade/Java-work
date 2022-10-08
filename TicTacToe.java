import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class MyGame extends JFrame implements Runnable, ActionListener {

  ///Graphical instance memeber:->
  public int x, y; ///to make comptiable ready for  any screen size.
  public int mfX, mfY; ///size of the main frame.
  public JPanel mainScreen;
  public JButton[] bArr = new JButton[15];
  private JLabel title;
  private JLabel clock;
  Font font = new Font("", Font.BOLD, 20);

  //Logical instance memeber::-->
  int gameChances[] = { 10, 10, 10, 10, 10, 10, 10, 10, 10 };
  int activePlayer = 99; ///99== 0 and 100 == X;
  int entryS = 0;
  int winChance[][] = {
    { 3, 4, 5 },
    { 0, 1, 2 },
    { 6, 7, 8 },
    { 7, 4, 1 },
    { 8, 5, 2 },
    { 0, 4, 8 },
    { 2, 4, 6 },
    { 0, 3, 6 },
    { 1, 4, 7 },
  };
  int winner = 0; //No one is winner till now;

  MyGame() {
    System.out.println("Instance created");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    x = (int) screenSize.width; ///Width of the screen
    y = (int) screenSize.height; ///Hight of the screen.
    mfX = x / 4;
    mfY = y / 2;
    title = new JLabel();
    mainScreen = new JPanel();
  }

  public void launchGame() {
    mainFrameAndIcon();
  }

  public void mainFrameAndIcon() { //Main Frame coding in this function....
    this.setTitle("Tic Tac Toe");
    this.setLocation(0, 0);
    this.setSize(mfX, mfY);
    ImageIcon img = new ImageIcon("D:/JAVA/My Projects/TikTacToe/Img/6.png");
    this.setIconImage(img.getImage());

    this.getContentPane().setBackground(Color.decode("#aab8cd")); //Using this we can choose any colour ...
    //this.getContentPane().setBackground(Color.orange);  ///Limited number of colour available in java library.

    this.setVisible(true);

    InsertLayout();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void InsertLayout() {
    setLayout(new BorderLayout());
    insertTitle();
  }

  public void insertTitle() {
    title.setText("TIC TAC TOE By Saurabh");
    // Font f = new Font("", Font.BOLD, 10);
    title.setFont(font);
    //title.setForeground(Color.decode("#cc0000"));
    title.setForeground(Color.darkGray);
    title.setHorizontalAlignment(JLabel.CENTER);
    title.setHorizontalTextPosition(SwingConstants.CENTER);
    this.add(title, BorderLayout.NORTH);
    addInnerPanel();
  }

  public void addInnerPanel() {
    mainScreen.setLayout(new GridLayout(3, 3));
    // Font f = new Font("", Font.BOLD, 10);
    for (int i = 0; i < 9; i++) {
      bArr[i] = new JButton();
      bArr[i].setBackground(Color.white);
      // bArr[i].setIcon(new ImageIcon("D:/JAVA/My Projects/TikTacToe/Img/0.png"));
      bArr[i].setName(i + "");
      bArr[i].addActionListener(this);
      mainScreen.add(bArr[i]);
    }
    this.add(mainScreen, BorderLayout.CENTER);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton currentButton = (JButton) e.getSource();
    int pressedButtonId = Integer.parseInt(currentButton.getName().trim());
    if (gameChances[pressedButtonId] == 10) {
      if (activePlayer == 99) {
        currentButton.setIcon(
          new ImageIcon("D:/JAVA/My Projects/TikTacToe/Img/0.png")
        );
        entryS++;
       // System.out.println(entryS);
        gameChances[pressedButtonId] = activePlayer;

        activePlayer = 100;
      } else {
        currentButton.setIcon(
          new ImageIcon("D:/JAVA/My Projects/TikTacToe/Img/X.png")
        );
        entryS++;
     //   System.out.println(entryS);

        gameChances[pressedButtonId] = activePlayer;
        activePlayer = 99;
      }
      chooseWinner();
    } else {
      JOptionPane.showMessageDialog(this, "Position already occupy");
      //   boolean isGameOver = true;  /// but using one more veriable is more iffisient so we'll go for that approach.
      //   for (int i = 0; i < 9; i++) {
      //     if (gameChances[i] == 0) {
      //       isGameOver = false;
      //     }
      //   }
      //   if (isGameOver) {
      //     System.out.println("game over");
      //     return;
      //   }
    }
    if (entryS >= 9) {
      JOptionPane.showMessageDialog(this, "GMAE DRAW");
      int k = JOptionPane.showConfirmDialog(this, "Play more?");
      if (k == 0) {
        MyGame g = new MyGame();
        g.launchGame();
      } else if (k == 1) {
        System.exit(199);
      } else {}
    }
  }

  public void chooseWinner() {
    int temp[];
    for (int i = 0; i < winChance.length; i++) {
      temp = winChance[i];

      if (
        gameChances[temp[0]] == gameChances[temp[1]] &&
        gameChances[temp[1]] == gameChances[temp[2]] &&
        gameChances[temp[2]] != 10
      ) {
        winner = gameChances[temp[0]];
        if (winner == 99) {
          JOptionPane.showMessageDialog(this, "O Win's");
        } else {
          JOptionPane.showMessageDialog(this, "X Win's");
        }
        int k = JOptionPane.showConfirmDialog(this, "Play More?");
        System.out.println(k);
        if (k == 0) {
          setVisible(false);
          MyGame a = new MyGame();
          a.launchGame();
        } else if (k == 1) {
          System.exit(9339);
        } else {}

        break;
      }
    }
  }

  public void run() {
    try {
      while (true) {
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yy , hh:mm:ss aa");
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        String strDate = dateFormat.format(date);
        clock.setText(strDate);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void addClock() {
    clock = new JLabel("CLOCK");
    clock.setFont(font);
    clock.setForeground(Color.blue);
    clock.setHorizontalAlignment(JLabel.CENTER);
    this.add(clock, BorderLayout.SOUTH);
    Thread t = new Thread(this);
    t.start();
    addInnerPanel();
  }
}

class TestGame {

  public static void main(String[] args) {
    MyGame a = new MyGame();
    a.launchGame();
  }
}
