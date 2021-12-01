package checkers.gui.panels;

import checkers.gui.GUI;
import checkers.gui.board.BoardPosition;
import checkers.gui.board.GameBoard;
import checkers.gui.board.Move;
import checkers.gui.board.MovesAndScores;
import checkers.gui.shapes.Counter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import java.util.Vector;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

    private int firstPlayer;

    private GameBoard gameBoard;
    private BoardPosition[][] boardPositions;

    private boolean isGameFinished = false;
    private int selectedCounter = -1;
    private int selectedCounterX;
    private int selectedCounterY;
    private int playerScore = 0;
    private int AIScore = 0;

    private Vector<MovesAndScores> successorEvaluations;

    private JLabel lblInfo;

    public GamePanel(int firstPlayer) {
        setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT + 16));
        setLayout(null);

        lblInfo = new JLabel();
        lblInfo.setBounds(0, GUI.HEIGHT, GUI.WIDTH, 16);
        lblInfo.setFont(new Font("Ariel", Font.BOLD, 16));
        add(lblInfo);

        createGameBoard();

        this.firstPlayer = firstPlayer;

        if (firstPlayer == 1)
            AIPlay();

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void createGameBoard() {
        gameBoard = new GameBoard();
        boardPositions = gameBoard.getBoardPositions();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4));

        // Render the game board
        gameBoard.render(g2d);

        repaint();
    }

    private void AIPlay() {
        Random random = new Random();
        System.out.println();

        Vector<Move> validMoves = getValidAIMoves();

        // Check if the AI is able to make a capture
        for (int i = 0; i < validMoves.size(); i++) {
            if (validMoves.get(i).isCaptureMove()) {
                makeMove(validMoves.get(i));
                return;
            }
        }

        // If not able to make a capture, make a random move.
        int r = random.nextInt(validMoves.size());
        makeMove(validMoves.get(r));

        hasAIWon();
    }

    private boolean checkForWinner() {
        String message = "";
        boolean winner = false;

        if (hasHumanWon()) {
            message = "Human has won!";
            winner = true;
        } else if (hasAIWon()) {
            message = "AI has won!";
            winner = true;
        }

        if (winner) {
            JOptionPane.showMessageDialog(this, message);
            GUI.changePanel(new MenuPanel());
        }

        return winner;
    }

    private boolean hasHumanWon() {
        // Check if all AI counters have been captured
        if (gameBoard.getAICounters().size() == 0)
            return true;

        // Check if AI has no valid moves
        Vector<Counter> AICounters = gameBoard.getAICounters();
        Vector<Move> validMoves = new Vector<>();

        for (Counter counter : AICounters)
            validMoves.addAll(getValidMoves(counter));
        if (validMoves.isEmpty())
            return true;

        return false;
    }

    private boolean hasAIWon() {
        // Check if all player counters have been captured
        if (gameBoard.getPlayerCounters().isEmpty())
            return true;

        // Check if player has no valid moves
        Vector<Counter> playerCounters = gameBoard.getPlayerCounters();
        Vector<Move> validMoves = new Vector<>();
        for (Counter counter : playerCounters)
            validMoves.addAll(getValidMoves(counter));
        if (validMoves.isEmpty())
            return true;

        return false;
    }

    private boolean isMoveValid(Move move) {
        boolean isValid = false;
        Counter counter = move.getCounter();

        // Check to see if the move is valid
        // Get valid moves and see if move is in the list?

        Vector<Move> validMoves = getValidPlayerMoves(counter);
        for (int i = 0; i < validMoves.size(); i++) {
            if (validMoves.get(i).equals(move)) {
                isValid = true;
                if (validMoves.get(i).isCaptureMove()) move.setCaptureMove(true, validMoves.get(i).getCapturedCounter());
            }
        }

        if (isValid) {
            makeMove(move);
            lblInfo.setForeground(new Color(10, 175, 10));
            lblInfo.setText("Move valid");
        } else {
            // Move counter back to original location
            counter.x = selectedCounterX;
            counter.y = selectedCounterY;
            lblInfo.setForeground(Color.red);
            lblInfo.setText("Move invalid");
        }
        return isValid;
    }

    public void makeMove(Move move) {
        System.out.println("Making move - " + move);

        Counter counter = move.getCounter();
        int counterX = counter.getBoardPosition().getX();
        int counterY = counter.getBoardPosition().getY();

        boardPositions[counterY][counterX].removeCounter();
        boardPositions[move.getBoardY()][move.getBoardX()].setCounter(counter);

        counter.setBoardPosition(boardPositions[move.getBoardY()][move.getBoardX()]);
        counter.x = BoardPosition.getPixelX(move.getBoardX());
        counter.y = BoardPosition.getPixelY(move.getBoardY());

        if (move.isCaptureMove()) {
            Counter capCounter = move.getCapturedCounter();
            if (capCounter.getIsKing())
                counter.setIsKing(true);

            gameBoard.removeCounter(capCounter);
        }
    }

    private Vector<Move> getValidMoves(Counter counter) {
        Vector<Move> validMoves = new Vector<>();

        int boardX = counter.getBoardPosition().getX();
        int boardY = counter.getBoardPosition().getY();
        int yDir = counter.getyDir();

        if (counter.getIsKing()) {
            // TODO - Allow a king to move in either direction.
            System.out.println("GETTING KING MOVES");
            if (boardX > 0 && boardY < 7)
                validMoves.add(new Move(boardX - 1, boardY + 1, counter));
            if (boardX < 7 && boardY < 7)
                validMoves.add(new Move(boardX + 1, boardY + 1, counter));
            if (boardX > 0 && boardY > 0)
                validMoves.add(new Move(boardX - 1, boardY + -1, counter));
            if (boardX < 7 && boardY > 0)
                validMoves.add(new Move(boardX + 1, boardY + -1, counter));
        } else if (yDir == 1) {
            if (boardX > 0 && boardY < 7)
                validMoves.add(new Move(boardX - 1, boardY + yDir, counter));
            if (boardX < 7 && boardY < 7)
                validMoves.add(new Move(boardX + 1, boardY + yDir, counter));
        } else if (yDir == -1) {
            if (boardX > 0 && boardY > 0)
                validMoves.add(new Move(boardX - 1, boardY + yDir, counter));
            if (boardX < 7 && boardY > 0)
                validMoves.add(new Move(boardX + 1, boardY + yDir, counter));
        }

        Vector<Move> toBeRemoved = new Vector<>();
        for (int i = 0; i < validMoves.size(); i++) {
            Move move = validMoves.get(i);

            if (boardPositions[move.getBoardY()][move.getBoardX()].containsCounter()) {
                toBeRemoved.add(move);

                // Check if able to capture a counter
                Counter blockingCounter = boardPositions[move.getBoardY()][move.getBoardX()].getCounter();
                if (blockingCounter.isAI != counter.isAI) {
                    int dirX;
                    int dirY;
                    if (blockingCounter.getBoardPosition().getX() < counter.getBoardPosition().getX()) dirX = -1;
                    else dirX = 1;
                    if (blockingCounter.getBoardPosition().getY() < counter.getBoardPosition().getY()) dirY = -1;
                    else dirY = 1;

                    if (move.getBoardX() + dirX <= 7 && move.getBoardX() + dirX >= 0 && move.getBoardY() + dirY <= 7 && move.getBoardY() + dirY >= 0) {
                        if (!boardPositions[move.getBoardY() + dirY][move.getBoardX() + dirX].containsCounter()) {
                            Move captureMove = new Move(move.getBoardX() + dirX, move.getBoardY() + dirY, counter);
                            captureMove.setCaptureMove(true, blockingCounter);
                            validMoves.add(captureMove);
                        }
                    }
                }
            }
        }

        validMoves.removeAll(toBeRemoved);
        toBeRemoved.removeAllElements();

        return validMoves;
    }

    private Vector<Move> getValidAIMoves() {
        Vector<Counter> AICounters = gameBoard.getAICounters();
        Vector<Move> validMoves = new Vector<>();

        for (Counter counter : AICounters)
            validMoves.addAll(getValidMoves(counter));

        // Print valid moves
        System.out.println("Valid AI moves:");
        for (Move move : validMoves)
            System.out.println("\t" + move);

        return validMoves;
    }

    private Vector<Move> getValidPlayerMoves(Counter counter) {
        return getValidMoves(counter);
    }

    private Vector<Move> getValidPlayerMoves() {
        Vector<Counter> playerCounters = gameBoard.getPlayerCounters();
        Vector<Move> validMoves = new Vector<>();

        for (Counter counter : playerCounters)
            validMoves.addAll(getValidMoves(counter));
        return validMoves;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        Vector<Counter> counters = gameBoard.getPlayerCounters();

        if (selectedCounter == -1) {
            // Get the selected counter
            for (int i = 0; i < counters.size(); i++) {
                if (counters.get(i).contains(p)) {
                    selectedCounter = i;
                    selectedCounterX = (int) counters.get(i).x;
                    selectedCounterY = (int) counters.get(i).y;
                }
            }
        } else {
            // Unselect a counter
            Counter counter = counters.get(selectedCounter);
            int[] boardPos = BoardPosition.getBoardPos(p);
            Move move = new Move(boardPos[0], boardPos[1], counter);
            if (isMoveValid(move)) {
                // Check for winner
                checkForWinner();

                // Make AI move
                AIPlay();
                checkForWinner();
            }
            selectedCounter = -1;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (selectedCounter >= 0) {
            Point p = e.getPoint();
            int mouseX = (int) p.getX() - (75 / 2);
            int mouseY = (int) p.getY() - (75 / 2);

            Vector<Counter> counters = gameBoard.getPlayerCounters();
            counters.get(selectedCounter).x = mouseX;
            counters.get(selectedCounter).y = mouseY;
        }
    }
}
