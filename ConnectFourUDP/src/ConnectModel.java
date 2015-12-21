import java.io.IOException;

/**
 * Class GoModel provides the server-side model object in the Network Go Game.
 *
 * @author  Michael E. Rinos
 * @version
 */
public class ConnectModel implements ViewListener {

    // Hidden data members.
    private Player p1;
    private Player p2;
    private int pCount = 0;
    private int whosTurn = 0;
    private static final int DRAW = 9;
    private static final int BOARD_ID = 5;
    private static final int NUMHEAPS = 7;
    private static final int NUMOBJECTS = 6;

// Exported constructors.

    /**
     * Construct a new Go model.
     */
    public ConnectModel() {}



    public synchronized int getCount(){
        return this.pCount;
    }

// Exported operations.

    /**
     * Add the given model listener to this Nim Model
     * @param  modelListener  Model listener.
     * @param  id the players id
     * @param  name the name of the player
     */
    public synchronized void addModelListener(ModelListener modelListener, int id, String name) throws Exception{
        if (p1 == null){        //First person joins
            p1 = new Player(id, name, modelListener);

            //Tell this player their id name name and score
            p1.getModelListener().id(p1.getId());
            p1.getModelListener().name(p1.getId(), p1.getName());
            p1.getModelListener().score(p1.getId(), p1.getScore());
            pCount+=1;
        }
        else{                   //Second player joins
            p2 = new Player(id, name, modelListener);

            //Tell each player their id name and score as well as their opponents
            p2.getModelListener().id(p2.getId());
            p2.getModelListener().name(p2.getId(), p2.getName());
            p2.getModelListener().score(p2.getId(), p2.getScore());
            p1.getModelListener().name(p2.getId(), p2.getName());
            p1.getModelListener().score(p2.getId(), p2.getScore());
            p2.getModelListener().name(p1.getId(), p1.getName());
            p2.getModelListener().score(p1.getId(), p1.getScore());
            p1.getModelListener().turn(0);
        }
    }

    public synchronized int winner(Player p) {
        int winner = checkRow(p);
        if (winner != BOARD_ID) return winner;
        winner = checkCol(p);
        if (winner != BOARD_ID) return winner;
        winner = checkDiag(p);
        if (winner != BOARD_ID) return winner;

        // Now we need to check if there are empty positions, otherwise it is a draw
        for (int i = 0; i < NUMHEAPS; ++i)
            for (int j = 0; j < NUMOBJECTS; ++j)
                if (p1.getSpot(i,j).getOwner() == BOARD_ID) return BOARD_ID;

        return DRAW;
    }

    public synchronized int checkRow(Player p){
        for (int row = 0; row < NUMHEAPS; ++row) {
            int count = 0;
            // We will compare current element with the previous
            for (int column = 1; column < NUMOBJECTS; ++column) {
                if (p.getSpot(row,column).getOwner() != BOARD_ID &&
                        p.getSpot(row,column).getOwner() == p.getSpot(row,column-1).getOwner())
                    ++count;
                else
                    count = 1;

                // Check if there are 4 in a row.
                if (count >= 4) {
                    // Return color of the winner
                    return p.getSpot(row,column).getOwner();
                }
            }
        }
        // Otherwise return   character, which means nobody win in rows.
        return BOARD_ID;
    }

    public synchronized int checkCol(Player p){
        // Check rows and see if there are 4 disks of the same color
        for (int column = 0; column < NUMOBJECTS; ++column) {
            int count = 1;
            // We will compare current element with the previous
            for (int row = 1; row < NUMHEAPS; ++row) {
                if (p.getSpot(row,column).getOwner() != BOARD_ID &&
                        p.getSpot(row,column).getOwner() == p.getSpot(row-1,column).getOwner())
                    ++count;
                else
                    count = 1;

                // Check if there are 4 in a row.
                if (count >= 4) {
                    // Return color of the winner
                    return p.getSpot(row,column).getOwner();
                }
            }
        }
        // Otherwise return   character, which means nobody win in rows.
        return BOARD_ID;
    }

    public synchronized int checkDiag(Player p){
        for (int column = 0; column < NUMOBJECTS; ++column) {
            int count = 1;
            // Traverse diagonal that starts at [0][column], we start with the first row,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int row = 1; row < NUMHEAPS; ++row) {
                // Coordinates an the diagonal change as [row + i][column + i],
                // so we stop when column can get outside of the range
                if (column + row >= 6) break;
                if (p.getSpot(row,column+row).getOwner() != BOARD_ID &&
                        p.getSpot(row-1,column + row - 1).getOwner() == p.getSpot(row,column+row).getOwner())
                    ++count;
                else
                    count = 1;
                if (count >= 4) return p.getSpot(row,column+row).getOwner();
            }
        }

        // There are diagonals, that starts on left of each row, let's check them
        for (int row = 0; row < NUMHEAPS; ++row) {
            int count = 1;
            // Traverse diagonal that starts at [row][0], we start with the first column,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int column = 1; column < NUMOBJECTS; ++column) {
                // Coordinates an the diagonal change as [row + i][column + i],
                // so we stop when column can get outside of the range
                if (column + row >= 7) break;
                if (p.getSpot(row + column, column).getOwner() != BOARD_ID &&
                        p.getSpot(row + column - 1, column - 1).getOwner() == p.getSpot(row + column, column).getOwner()){
                    ++count;
                    System.out.println("This is count: "+ count);
                }
                else
                    count = 1;
                if (count >= 4) return p.getSpot(row + column,column).getOwner();
            }
        }

        // Now we need to do the same for diagonals that go from top-right to bottom-left
        // There are diagonals, that starts on top of each column, let's check them
        for (int column = 0; column < NUMOBJECTS; ++column) {
            int count = 1;
            // Traverse diagonal that starts at [0][column], we start with the first row,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int row = 1; row < NUMHEAPS; ++row) {
                // Coordinates an the diagonal change as [row + i][column + i],
                // so we stop when column can get outside of the range
                if (column - row < 0) break;
                if (p.getSpot(row,column-row).getOwner() != BOARD_ID &&
                        p.getSpot(row - 1,column - row + 1).getOwner() == p.getSpot(row,column-row).getOwner())
                    ++count;
                else
                    count = 1;
                if (count >= 4) return p.getSpot(row,column-row).getOwner();
            }
        }

        // There are diagonals, that starts on left of each row, let's check them
        for (int row = 0; row < NUMHEAPS; ++row) {
            int count = 1;
            // Traverse diagonal that starts at [row][0], we start with the first column,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int column = NUMOBJECTS-1; column >= 0; --column) {
                // Coordinates an the diagonal change as [row + i][column + i],
                // so we stop when column can get outside of the range
                if (column - row < 0) break;
                if (column + 1 > 5) break;
                if (p.getSpot(column - row,column).getOwner() != BOARD_ID &&
                        p.getSpot(column - row - 1,column + 1).getOwner() == p.getSpot(column - row,column).getOwner())
                    ++count;
                else
                    count = 1;
                if (count >= 4) return p.getSpot(column - row,column).getOwner();
            }
        }

        // Otherwise return   character, which means nobody win in rows.
        return BOARD_ID;
    }

    /**
     * Join the given session.
     *
     * @param  proxy    Reference to view proxy object.
     * @param  session  Session name.
     */
    @Override
    public synchronized void join(ViewProxy proxy, String session) {}


    /**
     * Takes a element/s off of the board for the player who is up
     *
     * @throws IOException
     */
    @Override
    public synchronized void placed(int id, int x, int y) throws IOException {

        Piece p = new Piece(id);

        if (whosTurn == p1.getId()){
            //makes the move on each players board.
            p1.move(x,y,p);
            p2.move(x,y,p);

            //tells the players the board has changed.
            p1.getModelListener().move(id, x, y);
            p2.getModelListener().move(id, x, y);

            int winner = winner(p1);
            if (winner == p1.getId() || winner == p2.getId() || winner == DRAW){
                p1.incScore();
                p1.getModelListener().score(0,p1.getScore());
                p2.getModelListener().score(0,p1.getScore());
                p1.getModelListener().win(winner);
                p2.getModelListener().win(winner);
            }
            else {
                this.whosTurn = 1;
                //tells each player who's turn it is.
                p1.getModelListener().turn(whosTurn);
                p2.getModelListener().turn(whosTurn);
            }
        }
        else {
            //makes the move on each players board.
            p1.move(x,y,p);
            p2.move(x,y,p);

            //tells the players the board has changed.
            p1.getModelListener().move(id, x, y);
            p2.getModelListener().move(id, x, y);

            int winner = winner(p2);
            if (winner == p1.getId() || winner == p2.getId() || winner == DRAW){
                p2.incScore();
                p1.getModelListener().score(1,p2.getScore());
                p2.getModelListener().score(1,p2.getScore());
                p1.getModelListener().win(winner);
                p2.getModelListener().win(winner);
            }
            else {
                this.whosTurn = 0;
                //tells each player who's turn it is.
                p1.getModelListener().turn(whosTurn);
                p2.getModelListener().turn(whosTurn);
            }
        }

    }

    /**
     * resets the board to for both players
     * @throws IOException
     */
    @Override
    public synchronized void newgame() throws IOException {
        whosTurn = 0;
        p1.clearBoard();
        p2.clearBoard();
        p1.getModelListener().reset();
        p2.getModelListener().reset();
        p1.getModelListener().turn(whosTurn);
        p2.getModelListener().turn(whosTurn);
    }

    /**
     * Quits for any player that is still connected
     * @throws IOException
     */
    @Override
    public synchronized void quit() throws IOException {
        if (p1 != null) {
            p1.getModelListener().quit();
        }
        if (p2 != null) {
            p2.getModelListener().quit();
        }
        pCount = 0;
    }
}