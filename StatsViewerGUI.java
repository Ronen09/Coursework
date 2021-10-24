import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class StatsViewerGUI
{   
    private JLabel NumOfProp_Label;
    private JLabel AvgPrice_Label;
    private PropertyViewer viewer;
    private boolean is_open = false;
    private JFrame frame;
    public StatsViewerGUI(PropertyViewer viewer)
    {
        this.viewer = viewer;
        showStats();
    }

    public void showStats()
        {      
            if(is_open)
            {
                frame.toFront();                    //refocuses stats window if it is already displayed.
            }
            else
            {
                is_open = true;
            // much of the code here is almost directly copied from the makeFrame() function with some adjustmenets.
                frame = new JFrame("Statistics");
                JPanel contentPane = (JPanel)frame.getContentPane();
                contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));

                contentPane.setLayout(new BorderLayout(6, 6));
                NumOfProp_Label = new JLabel("default"); // Label is used to show the Number of properties viewed by the user till now.
                contentPane.add(NumOfProp_Label, BorderLayout.NORTH);
                NumOfProp_Label.setFont(new Font("Arial", Font.BOLD, 26));
        
                AvgPrice_Label = new JLabel(" ");   // Label is used to show the Average Price of the properties viewed by the user until now.
                contentPane.add(AvgPrice_Label, BorderLayout.SOUTH);
                AvgPrice_Label.setFont(new Font("Arial", Font.BOLD, 26));
        
                frame.setPreferredSize(new Dimension(610,135));
        
            // building is done - arrange the components     
                frame.pack();
        
            // place the frame at the center of the screen and show
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
                frame.setVisible(true);
        
                ActionListener action = new ActionListener(){                                                                       //make an action listener to update the statistical values
                public void actionPerformed(ActionEvent e)
                {
                    NumOfProp_Label.setText("Number of Properties Viewed :" + viewer.getNumberOfPropertiesViewed());
                    AvgPrice_Label.setText("Average Cost of the Properties Viewed : " + viewer.averagePropertyPrice());
                }
            };
        
                Timer timer = new Timer(100,action);        //using a swing timer to constantly update the JLabels every 100ms to keep up with the user looking for new properties.
                timer.setInitialDelay(0);   
                timer.start();
            }
    }
}