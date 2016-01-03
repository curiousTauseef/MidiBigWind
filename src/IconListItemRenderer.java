import javax.swing.*;
import java.awt.*;
public class IconListItemRenderer extends JLabel implements ListCellRenderer{
        
        public IconListItemRenderer(){
            setOpaque(true);
        }
        
        public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                JLabel lb = (JLabel) value;
                this.setIcon(lb.getIcon());
                this.setText(lb.getText());
                setBackground(isSelected ? Color.getHSBColor((float)0.6,(float)0.3,(float)1.0) : Color.white);
                setForeground(isSelected ? Color.black : Color.black);
                return this;
        }
}






