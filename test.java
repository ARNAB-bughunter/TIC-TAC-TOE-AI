import java.awt.*;
import javax.swing.*;
import java.awt.GraphicsDevice.WindowTranslucency.*;
import java.awt.event.*;
import java.util.*;
/**
 * @author Arnab Naha
 */
public class test extends JFrame{
   make panel;
 static  Container c;
    public test() {
        setLayout(null);
        setSize(412,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("TIC-TAC-TOE");
        panel=new make();
       add(panel);
    }
    public static void main(String [] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
         test frame = new test();
         frame.setResizable(false);
         frame.setOpacity(0.9f);        
         frame.setVisible(true);
         c=frame.getContentPane(); 
         c.setBackground(Color.darkGray);
     }
}
class make extends JPanel implements ActionListener{
JPanel board;
AI obj;
JToggleButton tgbutton;
ButtonGroup group;
Cursor cur;
char ch_pieces='o';
char charx;
JCheckBox bx,bo;
int move=0;
boolean player_win=false;
boolean player_x_win=false,player_o_win=false;
boolean mode=true;
boolean comp_win=false;
JButton board_bt[][],restart;
char input_arr[][];
String best_move;
public  void paint(Graphics g){
super.paint(g);
g.setFont(new Font("Black Oblique",Font.BOLD,50));
Graphics2D g2=(Graphics2D)g;
g2.setStroke(new BasicStroke(5));
g2.setColor(Color.blue);
g2.drawLine(182,370,182,478);
g2.drawLine(0,480,370,480);
if(move==8 && comp_win!=true && player_win!=true && mode==true){
  g.setColor(Color.yellow);
  g.drawString("TIE",41,435);  
  lock_bt();
  }
if(move==9 && player_o_win!=true && player_x_win!=true && mode==false){
g.setColor(Color.yellow);
  g.drawString("TIE",41,435);  
  lock_bt();
  }
if(comp_win==true && mode==true){
g.setFont(new Font("Black Oblique",Font.BOLD,32));
g.setColor(Color.red);
g.drawString("COMPUTER",0,410);
g.drawString("WIN",41,450);
     }
if(player_win==true && mode==true){
g.setFont(new Font("Black Oblique",Font.BOLD,32));  
g.setColor(Color.green);    
g.drawString("YOU",35,410);
g.drawString("WIN",41,450);
       }
if(player_x_win==true && mode==false){       
g.setFont(new Font("Black Oblique",Font.BOLD,27));  
g.setColor(Color.red);      
g.drawString("PLAYER-X",25,410);
g.drawString("WIN",55,450);     
   }
if(player_o_win==true && mode==false){       
g.setFont(new Font("Black Oblique",Font.BOLD,27));        
g.setColor(Color.green);
g.drawString("PLAYER-O",25,410);
g.drawString("WIN",55,450);     
   }     
g.setFont(new Font("Black Oblique",Font.BOLD,20)); 
g.setColor(Color.magenta);        
g.drawString("YOUR PIECES",10,510);
g.drawString("GAME MODE",13,560);
    }
make(){
obj=new AI();
tgbutton=new JToggleButton("ONE PLAYER");
tgbutton.setBounds(180,530,180,50);
tgbutton.addActionListener(this);
tgbutton.setFont(new Font("Black Oblique",Font.BOLD,20));
tgbutton.setForeground(Color.green);
tgbutton.setBackground(Color.gray);

cur=new Cursor(Cursor.HAND_CURSOR); 
restart=new JButton("RESTART");
restart.setForeground(Color.cyan);
restart.setBackground(Color.black);
restart.setBounds(185,370,185,110);
restart.setFont(new Font("Black Oblique",Font.BOLD,32));
restart.addActionListener(this);
board=new JPanel(){
public  void paint(Graphics g){
super.paint(g);
if((move==8 || comp_win==true) && player_win!=true && mode==true){
g.setFont(new Font("Black Oblique",Font.BOLD,22));	
g.setColor(new Color(0f,0f,0f,0.5f));
g.fillRect(15,165,340,40);
g.setColor(Color.green);
g.drawString("YOU CAN NOT DEFEAT ME!!!!!",20,182);
g.drawString("PRESS 'RESTART'",97,200);
      }
this.repaint();   
   }
};
board.setLayout(new GridLayout(3,3));
board.setBounds(0,0,370,370);
board_bt=new JButton[3][3];
input_arr=new char[3][3];
bx=new JCheckBox("X");
bo=new JCheckBox("O");
bx.setBounds(180,485,95,30);
bo.setBounds(275,485,95,30);
bx.setFont(new Font("Black Oblique",Font.BOLD,30));
bo.setFont(new Font("Black Oblique",Font.BOLD,30));
bx.setBackground(Color.black);
bo.setBackground(Color.black);
bo.setForeground(Color.green);
bx.setForeground(Color.green);
bx.addActionListener(this);
bo.addActionListener(this);
bo.setSelected(true);
group=new ButtonGroup();
group.add(bo);
group.add(bx);
for(int i=0;i<3;i++){
    for(int j=0;j<3;j++){
    board_bt[i][j]=new JButton();
    UIManager.put("Button.disabledText",Color.pink);
    board_bt[i][j].setFont(new Font("Black Oblique",Font.BOLD,110));
    board_bt[i][j].setCursor(cur);
    board_bt[i][j].addActionListener(this);
    board.add(board_bt[i][j]);
              }   
           }
board_make();
this.setBounds(15,15,370,580);
this.setLayout(null);
this.setBackground(Color.black);
this.add(board);
this.add(restart);   
this.add(bx);
this.add(bo);
this.add(tgbutton);
   }
public void board_make(){
    comp_win=false;
    player_win=false;
    player_x_win=false;
    player_o_win=false;
    move=0;
    repaint();
    for(int i=0;i<3;i++)
    for(int j=0;j<3;j++){
         board_bt[i][j].setText(null);
             board_bt[i][j].setBackground(Color.black);
         board_bt[i][j].setEnabled(true);
         if((board_bt[i][j].getIcon())==null)
            input_arr[i][j]='_';
         }
     }

public void actionPerformed(ActionEvent act){
if(act.getSource()==tgbutton){
if(tgbutton.isSelected()){
	tgbutton.setText("TWO PLAYER");
	tgbutton.setForeground(Color.red);
	mode=false;
   }
else{
	tgbutton.setText("ONE PLAYER");
    tgbutton.setForeground(Color.green);
    mode=true;
    }
   }
/**/
try{
if(act.getSource()==board_bt[0][0]){
if(mode==true){	
board_bt[0][0].setText(""+Character.toUpperCase(ch_pieces));
input_arr[0][0]=Character.toLowerCase(ch_pieces);
board_bt[0][0].setEnabled(false);    
make_ai_move();
move+=2;
        }
else{
charx=(move%2!=0)?'x':'o';
board_bt[0][0].setText(""+Character.toUpperCase(charx));
input_arr[0][0]=Character.toLowerCase(charx);
board_bt[0][0].setEnabled(false);
 move++;
        }        
     }

if(act.getSource()==board_bt[0][1]){
if(mode==true){	
board_bt[0][1].setText(""+Character.toUpperCase(ch_pieces));
input_arr[0][1]=Character.toLowerCase(ch_pieces);
board_bt[0][1].setEnabled(false);    
make_ai_move();
move+=2;
     }
else{
charx=(move%2!=0)?'x':'o';
board_bt[0][1].setText(""+Character.toUpperCase(charx));
input_arr[0][1]=Character.toLowerCase(charx);
board_bt[0][1].setEnabled(false);
 move++;
        }
   }

if(act.getSource()==board_bt[0][2]){
if(mode==true){	
board_bt[0][2].setText(""+Character.toUpperCase(ch_pieces));
input_arr[0][2]=Character.toLowerCase(ch_pieces);
board_bt[0][2].setEnabled(false); 
make_ai_move();   
move+=2;
       }
else{
charx=(move%2!=0)?'x':'o';
board_bt[0][2].setText(""+Character.toUpperCase(charx));
input_arr[0][2]=Character.toLowerCase(charx);
board_bt[0][2].setEnabled(false);
 move++;
        }
   }

if(act.getSource()==board_bt[1][0]){
if(mode==true){	
board_bt[1][0].setText(""+Character.toUpperCase(ch_pieces));
input_arr[1][0]=Character.toLowerCase(ch_pieces);
board_bt[1][0].setEnabled(false);    
make_ai_move();
move+=2;
       }
else{
charx=(move%2!=0)?'x':'o';
board_bt[1][0].setText(""+Character.toUpperCase(charx));
input_arr[1][0]=Character.toLowerCase(charx);
board_bt[1][0].setEnabled(false);
 move++;
        }
   }

if(act.getSource()==board_bt[1][1]){
if(mode==true){	
board_bt[1][1].setText(""+Character.toUpperCase(ch_pieces));
input_arr[1][1]=Character.toLowerCase(ch_pieces);
board_bt[1][1].setEnabled(false);    
make_ai_move();
move+=2;
      }
else{
charx=(move%2!=0)?'x':'o';
board_bt[1][1].setText(""+Character.toUpperCase(charx));
input_arr[1][1]=Character.toLowerCase(charx);
board_bt[1][1].setEnabled(false);
 move++;
        }
   }

if(act.getSource()==board_bt[1][2]){
if(mode==true){	
board_bt[1][2].setText(""+Character.toUpperCase(ch_pieces));
input_arr[1][2]=Character.toLowerCase(ch_pieces);
board_bt[1][2].setEnabled(false);    
make_ai_move();
move+=2;
      }
else{
charx=(move%2!=0)?'x':'o';
board_bt[1][2].setText(""+Character.toUpperCase(charx));
input_arr[1][2]=Character.toLowerCase(charx);
board_bt[1][2].setEnabled(false);
 move++;
        }      
   }

if(act.getSource()==board_bt[2][0]){
if(mode==true){	
board_bt[2][0].setText(""+Character.toUpperCase(ch_pieces));
input_arr[2][0]=Character.toLowerCase(ch_pieces);
board_bt[2][0].setEnabled(false);    
make_ai_move();
move+=2;
       }
else{
charx=(move%2!=0)?'x':'o';
board_bt[2][0].setText(""+Character.toUpperCase(charx));
input_arr[2][0]=Character.toLowerCase(charx);
board_bt[2][0].setEnabled(false);
 move++;
        }       
   }   

if(act.getSource()==board_bt[2][1]){
if(mode==true){	
board_bt[2][1].setText(""+Character.toUpperCase(ch_pieces));
input_arr[2][1]=Character.toLowerCase(ch_pieces);
board_bt[2][1].setEnabled(false);    
make_ai_move();
move+=2;
       }
else{
charx=(move%2!=0)?'x':'o';
board_bt[2][1].setText(""+Character.toUpperCase(charx));
input_arr[2][1]=Character.toLowerCase(charx);
board_bt[2][1].setEnabled(false);
 move++;
        }
   }
if(act.getSource()==board_bt[2][2]){
if(mode==true){	
board_bt[2][2].setText(""+Character.toUpperCase(ch_pieces));
input_arr[2][2]=Character.toLowerCase(ch_pieces);
board_bt[2][2].setEnabled(false);    
make_ai_move();
move+=2;
        }
else{
charx=(move%2!=0)?'x':'o';
board_bt[2][2].setText(""+Character.toUpperCase(charx));
input_arr[2][2]=Character.toLowerCase(charx);
board_bt[2][2].setEnabled(false);
 move++;
        }        
     }       
  }catch(Exception e){}
/*/**/

if(act.getSource()==restart){
   board_make();
   }
if(act.getSource()==bo && bo.isSelected()){
ch_pieces='o';
   }
if(act.getSource()==bx && bx.isSelected()){
ch_pieces='x';
  }
if(move==0){
tgbutton.setEnabled(true);
  }
else{
tgbutton.setEnabled(false);
 }
if(move==0 && mode==true){
bo.setEnabled(true);
bx.setEnabled(true);
  } 
else {
  	bo.setEnabled(false);
  	bx.setEnabled(false);
  }  
check_win_set_color();
repaint();
board.repaint();  
}

public  void make_ai_move(){
   best_move=obj.input_data(input_arr,ch_pieces);
   int row=Integer.parseInt(String.valueOf(best_move.charAt(0)));
   int col=Integer.parseInt(String.valueOf(best_move.charAt(1)));
   if(ch_pieces=='x'){
   input_arr[row][col]='o';
   board_bt[row][col].setText("O");
  }
 if(ch_pieces=='o'){
   input_arr[row][col]='x';
   board_bt[row][col].setText("X");
  }
  
   board_bt[row][col].setEnabled(false);    
}  
public void check_win_set_color(){
  for(int i=0;i<3;i++)
    for(int j=0;j<3;j++)
       if(input_arr[i][j]=='x')
        board_bt[i][j].setBackground(new Color(125,21,0));
       else
        if(input_arr[i][j]=='o')
          board_bt[i][j].setBackground(new Color(163,0,49));

    player_win=comp_win=false;
if(mode==true){    
for(int i=0;i<3;i++){
if(input_arr[i][0]!='_' && input_arr[i][0]==input_arr[i][1] && input_arr[i][1]==input_arr[i][2] ){
    lock_bt();
    if( input_arr[i][2]==ch_pieces)
    player_win=true;
    else
        comp_win=true;
      repaint();
     break;    
         }
if(input_arr[0][i]!='_' &&  input_arr[0][i]==input_arr[1][i] && input_arr[1][i]==input_arr[2][i] ){
    lock_bt();
    if(input_arr[2][i]==ch_pieces)
        player_win=true;
    else
        comp_win=true;
      repaint();
    break;
          }            
     }          
if(input_arr[0][0]==input_arr[1][1] && input_arr[1][1]==input_arr[2][2] && input_arr[0][0]!='_' ){
   if(input_arr[0][0]==ch_pieces)
        player_win=true;
    else
        comp_win=true;
      repaint();
    lock_bt();
    }

if(input_arr[0][2]==input_arr[1][1] && input_arr[1][1]==input_arr[2][0] && input_arr[1][1]!='_' ){
  if(input_arr[0][2]==ch_pieces)
        player_win=true;
    else
        comp_win=true;
      repaint();
    lock_bt();
       }
     }

if(mode==false)
{
	for(int i=0;i<3;i++){
		if(input_arr[i][0]!='_' && input_arr[i][0]==input_arr[i][1] && input_arr[i][1]==input_arr[i][2]){
			if(input_arr[i][2]=='x')
				player_x_win=true;
			else
				player_o_win=true;
			repaint();
			lock_bt();
        break;
      		}
        if(input_arr[0][i]!='_' &&  input_arr[0][i]==input_arr[1][i] && input_arr[1][i]==input_arr[2][i]){
    		if(input_arr[0][i]=='x')
				player_x_win=true;
			else
				player_o_win=true;
			repaint();
			lock_bt();
        break;
           }
     	}
     	if(input_arr[0][0]==input_arr[1][1] && input_arr[1][1]==input_arr[2][2] && input_arr[0][0]!='_' ){
     		if(input_arr[0][0]=='x')
     			player_x_win=true;
     		else
     			player_o_win=true;
     		repaint();
     		lock_bt();
     	}
     	if(input_arr[0][2]==input_arr[1][1] && input_arr[1][1]==input_arr[2][0] && input_arr[1][1]!='_' ){
  if(input_arr[0][2]=='x')
        player_x_win=true;
    else
        player_o_win=true;
      repaint();
    lock_bt();
       }



     }     
  }    
public  void lock_bt(){
for(int i=0;i<3;i++)
    for(int j=0;j<3;j++){
        board_bt[i][j].setEnabled(false);
      }
   }   
}

class AI 
{ 
 class Move 
{ 
    int row, col; 
}; 
 char player,opponent;
 Boolean isMovesLeft(char board[][]) 
{ 
    for (int i = 0; i < 3; i++) 
        for (int j = 0; j < 3; j++) 
            if (board[i][j] == '_') 
                return true; 
    return false; 
}  
 int evaluate(char b[][]) 
{ 
    for (int row = 0; row < 3; row++) 
    { 
        if (b[row][0] == b[row][1] && 

            b[row][1] == b[row][2]) 
        { 
            if (b[row][0] == player) 
                return +10; 
            else if (b[row][0] == opponent) 
                return -10; 
        } 
    } 
    for (int col = 0; col < 3; col++) 
    { 
        if (b[0][col] == b[1][col] && 
            b[1][col] == b[2][col]) 
        { 
            if (b[0][col] == player) 
                return +10; 
            else if (b[0][col] == opponent) 
                return -10; 
        } 
    } 
    if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) 
    { 
        if (b[0][0] == player) 
            return +10; 
        else if (b[0][0] == opponent) 
            return -10; 
    } 
    if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) 
    { 
        if (b[0][2] == player) 
            return +10; 
        else if (b[0][2] == opponent) 
            return -10; 
    } 
    return 0; 
} 
   int minimax(char board[][],int depth, Boolean isMax) 
{ 
    int score = evaluate(board); 
    if (score == 10) 
        return score; 
    if (score == -10) 
        return score; 
    if (isMovesLeft(board) == false) 
        return 0; 
    if (isMax) 
    { 
        int best = -1000; 
        for (int i = 0; i < 3; i++) 
        { 
            for (int j = 0; j < 3; j++) 
            { 
                if (board[i][j]=='_') 
                { 
                    board[i][j] = player; 
                    best = Math.max(best, minimax(board,depth + 1, !isMax)); 
                    board[i][j] = '_'; 
                } 
            } 
        } 
        return best; 
    } 
    else
    { 
        int best = 1000; 
        for (int i = 0; i < 3; i++) 
        { 
            for (int j = 0; j < 3; j++) 
            { 
                if (board[i][j] == '_') 
                { 
                    board[i][j] = opponent; 
                    best = Math.min(best, minimax(board,depth + 1, !isMax)); 
                    board[i][j] = '_'; 
                } 
            } 
        } 
        return best; 
    } 
} 
 Move findBestMove(char board[][]) 
{ 
    int bestVal = -1000; 
    Move bestMove = new Move(); 
    bestMove.row = -1; 
    bestMove.col = -1; 
    for (int i = 0; i < 3; i++) 
    { 
        for (int j = 0; j < 3; j++) 
        { 
            if (board[i][j] == '_') 
            { 
                board[i][j] = player; 
                int moveVal = minimax(board, 0, false); 
                board[i][j] = '_'; 
                if (moveVal > bestVal) 
                { 
                    bestMove.row = i; 
                    bestMove.col = j; 
                    bestVal = moveVal; 
                } 
            } 
        } 
    } 
    return bestMove; 
} 


public String input_data(char [][ ]board,char ch){ 
if(ch=='x'){
  opponent='x';
  player='o';
}
if(ch=='o'){
  opponent='o'; 
  player='x';
 }
    Move bestMove = findBestMove(board); 
    return (""+bestMove.row+bestMove.col);
    } 
}