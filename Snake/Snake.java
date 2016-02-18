import java.awt.*;
import javax.swing.*;
public class Snake extends JPanel
{
    JFrame mainFrame;
    int[][] knots;
    int currend;
    int s = 500;
    public Snake()
    {
        mainFrame = new JFrame();
        mainFrame.setSize(1000,1000);

        this.setBounds(0,0,1000,1000);
        mainFrame.add(this);
        this.setVisible(true);
        mainFrame.setVisible(true);
        int[][] knots = {{s,s,s,s,s},{0,0}};
        this.knots = knots;

        currend = 0;
    }

    public void start(){
        while(true){
            try {
                Thread.sleep(2000);
                this.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paint(Graphics g){
        currend = (currend+1)%5;
        knots[currend][0] = Math.abs((int)(Math.random()) * 100 -50);
        knots[currend][1] = Math.abs((int)(Math.random()) * 100 -50);

        g.setColor(Color.black);
        for(int i = 0; i<3; i++){
            
            g.drawLine(knots[(currend +i)%5][0], knots[currend +i][1], knots[(currend +i)%5+1][0], knots[(currend +i)%5+1][1]);
        }
        g.setColor(Color.white);
        g.drawLine(knots[(currend +4)%5][0], knots[(currend +4)%5][1], knots[(currend +5)%5][0], knots[(currend +5)%5][1]);
    }
}
