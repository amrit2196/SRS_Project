/* This code was developed by Team 21
* code was developed by  Srinivas,Pavan,Sandeep,Shivani and Amrit */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.awt.event.MouseEvent;



public class ConvaysGameOfLife extends JFrame implements ActionListener {
    private static final Dimension D_Size = new Dimension(800, 600);
    private static final int Cell_Size = 15;
    private static final Dimension Min_Size = new Dimension(450, 450);

    private static JComboBox C_Box;
    private Thread thrd;
    private GBrd grid;
    private JButton Strt, Stp, Rst, hlp;
    private int mps = 3;
    private static JToolBar Task_Bar;


    public static void main(String[] args) {

        JFrame mainFrame = new ConvaysGameOfLife();
        System.out.println("Entered into Main method");
        Container CntPne = mainFrame.getContentPane();
        mainFrame.setTitle("Game of Life by Team 21");
        mainFrame.setVisible(true);
        mainFrame.setSize(D_Size);
        mainFrame.setMinimumSize(Min_Size);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        CntPne.add(Task_Bar, BorderLayout.SOUTH);

    }

    public ConvaysGameOfLife() {

        Strt = new JButton("Start");
        Task_Bar = new JToolBar();
        System.out.println("Entered into the constructor");
        Strt.setBackground(Color.WHITE);
        Task_Bar.setBackground(Color.LIGHT_GRAY);
        Task_Bar.setFloatable(false);



        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();

        Task_Bar.add(Strt);
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Stp = new JButton("Stop");
        String arr1[] = { "Autofill Board","5%","15%","30%", "45%", "60%", "75%","90%" };

        // Combobox is established
        C_Box = new JComboBox(arr1);
        C_Box.setMaximumSize( C_Box.getPreferredSize() );
        Task_Bar.add(Stp);
        Stp.setBackground(Color.WHITE);
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Rst = new JButton("Reset");
        Task_Bar.add(Rst);
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        hlp = new JButton("Rules");
        Task_Bar.add(hlp);
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.addSeparator();
        Task_Bar.add(C_Box);
        Rst.setBackground(Color.WHITE);
        Task_Bar.addSeparator();

        C_Box.addActionListener(this);
        Rst.addActionListener(this);
        Stp.addActionListener(this);
        Strt.addActionListener(this);
        hlp.addActionListener(this);

        grid = new GBrd();
        add(grid);

    }

    public void sGmBPlyd(boolean isGameAlive) {
        System.out.println("Entered into the  game being played method");

        if (isGameAlive) {
            Stp.setEnabled(true);
            Strt.setEnabled(false);
            thrd = new Thread(grid);
            thrd.start();
        } else {
            Stp.setEnabled(false);
            Strt.setEnabled(true);
            thrd.interrupt();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(Rst)) {
            grid.rstBrd();
            grid.repaint();
        } else if (event.getSource().equals(Strt)) {
            sGmBPlyd(true);
        } else if (event.getSource().equals(Stp)) {
            sGmBPlyd(false);
        }else if (event.getSource().equals(hlp)) {
            JOptionPane.showMessageDialog(null, "For a space that is populated:\n\n1.Each cell with one or no neighbors dies, as if by solitude.\n2.Each cell with four or more neighbors dies, as if by overpopulation.\n3.Each cell with two or three neighbors survives.\n\nFor a space that is empty or unpopulated\n\n1.Each cell with three neighbors becomes populated.");
        }
        else if (event.getSource().equals(C_Box)) {

            if (C_Box.getSelectedIndex() > 0) {
                if(C_Box.getSelectedItem()!="Autofill Board"){
                    System.out.println("Something");
                    grid.rstBrd();
                    String s = (String) C_Box.getSelectedItem();
                    String[] str = s.split("%");
                    int k = Integer.parseInt(str[0]);
                    grid.autoClr(k);

                }
            }
        }
    }

    private class GBrd extends JPanel implements ComponentListener, MouseListener, MouseMotionListener, Runnable {
        private ArrayList<Point> pt = new ArrayList<Point>(0);
        private Dimension dGBS = null;


        public GBrd() {
            addMouseListener(this);
            addComponentListener(this);
            addMouseMotionListener(this);
        }

        private void modifyArrSize() {
            ArrayList<Point> rLst = new ArrayList<Point>(0);
            System.out.println("Entered into modify array size method");
            for (Point crnt : pt) {
                if ((crnt.x > dGBS.width - 1) || (crnt.y > dGBS.height - 1)) {
                    rLst.add(crnt);
                }
            }
            pt.removeAll(rLst);
            repaint();
        }

        public void adPnt(int a, int b) {
            if (!pt.contains(new Point(a, b))) {
                pt.add(new Point(a, b));
            }
            repaint();
        }

        public void adPnt(MouseEvent mouse_evnt) {
            int a = mouse_evnt.getPoint().x / Cell_Size - 1;
            int b = mouse_evnt.getPoint().y / Cell_Size - 1;
            if ((a >= 0) && (a < dGBS.width) && (b >= 0) && (b < dGBS.height)) {
                adPnt(a, b);
            }
        }

        public void rPnt(int a, int b) {
            pt.remove(new Point(a, b));
        }

        public void rstBrd() {
            pt.clear();
        }


        public void autoClr(int k) {
            int a = 0;
            while (a < dGBS.width) {
                int b = 0;
                while  (b < dGBS.height) {
                    if (Math.random()*100 < k) {
                        adPnt(a, b);
                    }
                    b++;
                }
                a++;
            }
        }

        @Override
        public void paintComponent(Graphics gph) {
            super.paintComponent(gph);
            try {
                for (Point nPt : pt) {
                    gph.setColor(Color.BLACK);
                    gph.fillRect(Cell_Size + (Cell_Size * nPt.x), Cell_Size + (Cell_Size * nPt.y), Cell_Size, Cell_Size);
                }
            } catch (ConcurrentModificationException ConModExcption) {
            }
            gph.setColor(Color.BLUE);
            int a = 0;
            while (a <= dGBS.width) {
                gph.drawLine(((a * Cell_Size) + Cell_Size), Cell_Size, (a * Cell_Size) + Cell_Size, Cell_Size + (Cell_Size * dGBS.height));
                a++;
            }
            int b = 0;
            while (b <= dGBS.height) {
                gph.drawLine(Cell_Size, ((b * Cell_Size) + Cell_Size), Cell_Size * (dGBS.width + 1), ((b * Cell_Size) + Cell_Size));
                b++;
            }
        }

        @Override
        public void componentResized(ComponentEvent evnt) {

            dGBS = new Dimension(getWidth() / Cell_Size - 2, getHeight() / Cell_Size - 2);
            modifyArrSize();
        }

        @Override
        public void componentHidden(ComponentEvent evnt) {
        }

        @Override
        public void componentShown(ComponentEvent evnt) {
        }

        @Override
        public void componentMoved(ComponentEvent evnt) {
        }



        @Override
        public void mousePressed(MouseEvent evnt) {
        }

        @Override
        public void mouseClicked(MouseEvent evnt) {
        }

        @Override
        public void mouseReleased(MouseEvent evnt) {

            adPnt(evnt);

        }

        @Override
        public void mouseMoved(MouseEvent evnt) {
        }

        @Override
        public void mouseEntered(MouseEvent evnt) {
        }

        @Override
        public void mouseDragged(MouseEvent evnt) {
        }

        @Override
        public void mouseExited(MouseEvent evnt) {
        }


        @Override
        public void run() {
            boolean[][] grid = new boolean[dGBS.width + 2][dGBS.height + 2];
            for (Point crnt : pt) {
                grid[crnt.x + 1][crnt.y + 1] = true;
            }
            ArrayList<Point> livingclls = new ArrayList<Point>(0);
            int x = 1;
            while (x < grid.length - 1) {
                int y = 1;
                while (y < grid[0].length - 1) {
                    int adjacent = 0;
                    int[][]Arr = {{-1, -1, -1, 0,0,+1, +1, +1},
                            { -1, 0,+1, -1, +1,-1,0,+1}};
                    for(int z=0;z<8;z++){
                        if (grid[x +Arr[0][z] ][ y +Arr[1][z] ]) {
                            adjacent++;
                        }
                    }

                    if (grid[x][y]) {
                        if ((adjacent == 3) || (adjacent == 2)) {
                            livingclls.add(new Point(x - 1, y - 1));
                        }
                    } else {
                        if (adjacent == 3) {
                            livingclls.add(new Point(x - 1, y - 1));
                        }
                    }
                    y++;
                }
                x++;
            }
            rstBrd();
            pt.addAll(livingclls);
            repaint();
            try {
                Thread.sleep(1005 / mps);
                run();
            } catch (InterruptedException ex) {
            }
        }
    }
}