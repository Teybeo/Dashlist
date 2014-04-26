package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Guillaume on 26/04/14.
 */
public class RoundedPanel extends JPanel {

    /** Défini la taille de la bordure */
    protected int borderSize = 0;
    /** Couleur de l'ombre */
    protected Color shadowColor = Color.black;
    /** Met une ombre ou non */
    protected boolean enableShadow = true;
    /** Active désactive l'antialiasing */
    protected boolean highQuality = true;
    /** Angle de l'arrondi du Panel */
    protected Dimension arcs = new Dimension(20, 20);
    /** Taille de l'ombre */
    protected int shadowGap = 4;
    /** Décalage de l'ombre  */
    protected int shadowOffset = 3;
    /** Transparence de l'ombre ( 0 - 255) */
    protected int shadowAlpha = 150;

    public RoundedPanel() {
        super();
        setOpaque(false);
    }

    public RoundedPanel(int borderSize, boolean enableShadow){
        super();
        this.borderSize = borderSize;
        this.enableShadow = enableShadow;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int shadowGap = this.shadowGap;
        Color shadowColorA = new Color(shadowColor.getRed(),
                shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
        Graphics2D graphics = (Graphics2D) g;

        //Sets antialiasing if HQ.
        if (highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

        //Draws shadow borders if any.
        if (enableShadow) {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(
                    shadowOffset,// X position
                    shadowOffset,// Y position
                    width - borderSize - shadowOffset, // width
                    height - borderSize - shadowOffset, // height
                    arcs.width, arcs.height);// arc Dimension
        } else {
            shadowGap = 1;
        }

        //Draws the rounded opaque panel with borders.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - shadowGap,
                height - shadowGap, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.setStroke(new BasicStroke(borderSize));
        graphics.drawRoundRect(0, 0, width - shadowGap,
                height - shadowGap, arcs.width, arcs.height);

        //Sets strokes to default, is better.
        graphics.setStroke(new BasicStroke());
    }
}