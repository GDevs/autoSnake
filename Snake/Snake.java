import java.awt.*;
import javax.swing.*;
public class Snake extends JPanel
{
    JFrame mainFrame;
    int[][] knots;
    int currend;
    int s = 500;
    int length;
    public Snake()
    {
        mainFrame = new JFrame();
        mainFrame.setSize(1000,1000);
        
        this.setBounds(0,0,1000,1000);
        mainFrame.add(this);
        this.setVisible(true);
        mainFrame.setVisible(true);
        int[][] knots = {{s,s},{s,s},{s,s},{s,s},{s,s},{s,s},{s,s}};
        length = knots.length;
        this.knots = knots;

        System.out.println(knots.length);
        System.out.println(knots[0].length);
        
        currend = 0;
    }

    public void start(){
        while(true){
            try {
                Thread.sleep(250);
                update();
                this.repaint();
                mainFrame.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void update(){
        int last = currend;
        currend = (currend+1)%length;
        knots[currend][0] =(knots[last][0] + (Math.abs((int)(Math.random() * 100)) -50)%1000);
        knots[currend][1] =(knots[last][1] + (Math.abs((int)(Math.random() * 100)) -50)%1000);
    }

    public void paint(Graphics g){
        System.out.println("paint");
        g.setColor(Color.black);
        for(int i = 0; i<length-1; i++){
            System.out.println((currend +i)%length + "-->" +(currend +i +1)%length);
            g.drawLine(knots[(currend +i)%length][0], knots[(currend +i)%length][1], knots[(currend +i+1)%length][0], knots[(currend +i+1)%length][1]);
        }
        g.setColor(Color.white);
        //g.drawLine(knots[(currend +length-2)%length][0], knots[(currend +length-2)%length][1], knots[(currend +length-1)%length][0], knots[(currend +length-1)%length][1]);
    }
}
