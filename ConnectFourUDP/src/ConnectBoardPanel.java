import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class ConnectBoardPanel extends JPanel {

    private boolean isEnabled;

    private static final int W = 50;
    private static final int D = 30;
    private static final int OFFSET = 5;

    private static final int X1 = W / 2;
    private static final int X2 = X1 + (ConnectBoard.COLS - 1) * W;

    private static final int Y1 = W / 2;
    private static final int Y2 = Y1 + (ConnectBoard.ROWS - 1) * W;

// Hidden data members.

    private ConnectBoard board;

// Exported constructors.

    /**
     * Construct a new Go board panel.
     *
     * @param board Go board.
     */
    public ConnectBoardPanel
    (ConnectBoard board) {
        super();
        this.board = board;
        this.isEnabled = false;

        Dimension dim = new Dimension(W * ConnectBoard.COLS, D * ConnectBoard.ROWS);
        setMinimumSize(dim);
        setPreferredSize(dim);
        setMaximumSize(dim);
    }

// Exported operations.
    public boolean getEnabled(){
        return this.isEnabled;
    }

    public void setEnabled
            (boolean enabled) // True to enable, false to disable
    {
        if (this.isEnabled != enabled)
        {
            this.isEnabled = enabled;
            repaint();
        }
    }

    /**
     * Determine the row on the Go board that was clicked.
     *
     * @param e Mouse event.
     * @return Row index.
     */
    public int clickToRow
    (MouseEvent e) {
        return e.getY() / D;
    }

    /**
     * Determine the column on the Go board that was clicked.
     *
     * @param e Mouse event.
     * @return Column index.
     */
    public int clickToColumn
    (MouseEvent e) {
        return e.getX() / W;
    }


    public void clear(){
        board.clearBoard();
    }

// Hidden operations.

    /**
     * Paint this Go board panel in the given graphics context.
     *
     * @param g Graphics context.
     */

    protected void paintComponent
    (Graphics g) {
        super.paintComponent(g);

        // Clone graphics context.
        Graphics2D g2d = (Graphics2D) g.create();

        // Turn on antialiasing.
        g2d.setRenderingHint
                (RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw spots.
        Ellipse2D.Double ellipse = new Ellipse2D.Double();
        ellipse.width = W-10;
        ellipse.height = D-5;
        if (isEnabled) {
            synchronized (board) {
                for (int r = 0; r < ConnectBoard.ROWS; ++r) {
                    for (int c = 0; c < ConnectBoard.COLS; ++c) {
                        Color color = board.getSpot(r, c).getColor();
                        if (color != null) {
                            ellipse.x = c * W + OFFSET;
                            ellipse.y = (6 - 1 - r) * D + 1;
                            g2d.setColor(color);
                            g2d.fill(ellipse);
                            g2d.setColor(Color.BLACK);
                            g2d.draw(ellipse);
                        }
                    }
                }
            }
        }
        else {
            synchronized (board) {
                for (int r = 0; r < ConnectBoard.ROWS; ++r) {
                    for (int c = 0; c < ConnectBoard.COLS; ++c) {
                        Color color = Color.BLACK;
                        if (color != null && board.getSpot(r,c).getOwner() ==5) {
                            ellipse.x = c * W + OFFSET;
                            ellipse.y = (6 - 1 - r) * D + 1;
                            g2d.setColor(color);
                            g2d.setColor(Color.BLACK);
                            g2d.draw(ellipse);
                        }else {
                            color = board.getSpot(r,c).getColor();
                            ellipse.x = c * W + OFFSET;
                            ellipse.y = (6 - 1 - r) * D + 1;
                            g2d.setColor(color);
                            g2d.fill(ellipse);
                            g2d.setColor(Color.BLACK);
                            g2d.draw(ellipse);

                        }
                    }
                }
            }
        }
    }
}
