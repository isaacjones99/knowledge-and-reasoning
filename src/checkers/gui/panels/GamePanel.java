package checkers.gui.panels;

import checkers.gui.Difficulty;
import checkers.gui.GUI;
import checkers.gui.Options;
import checkers.gui.board.BoardPosition;
import checkers.gui.board.GameBoard;
import checkers.gui.board.Move;
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
    private GameBoard evaluationBoard;

    private boolean isGameFinished = false;
    private int selectedCounter = -1;
    private int playerScore = 0;
    private int AIScore = 0;

    private JLabel lblInfo;

    public GamePanel() {
        setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT + 16));
        setLayout(null);

        lblInfo = new JLabel();
        lblInfo.setBounds(0, GUI.HEIGHT, GUI.WIDTH, 16);
        lblInfo.setFont(new Font("Ariel", Font.BOLD, 16));
        add(lblInfo);

        createGameBoard();

        firstPlayer = Options.firstPlayer;

        if (firstPlayer == 1)
            AIPlay();

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void createGameBoard() {
        gameBoard = new GameBoard(false);
//        boardPositions = gameBoard.getBoardPositions();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4));

        // Render the game board
        gameBoard.render(g2d);

        repaint();
    }

    private void makeMinimaxMove() {
        System.out.println("NEW MAKE MINIMAX MOVE");

        evaluationBoard = new GameBoard(true);
        evaluationBoard.setUpEvalBoard(gameBoard.getPlayerCounters(), gameBoard.getAICounters());
        System.out.println("Evaluation board: " + evaluationBoard);

        System.out.println(evaluationBoard.minimax(0, 1));
        System.out.println("Best move: " + evaluationBoard.getBestMove() + ", counter: " + evaluationBoard.getBestMove().getCounter());

        Move bestMove = evaluationBoard.getBestMove();
        Counter bestMoveCounter = bestMove.getCounter();
        int bestMoveCounterX = bestMoveCounter.getBoardPosition().getX();
        int bestMoveCounterY = bestMoveCounter.getBoardPosition().getY();
        Counter counterToAssign = gameBoard.getBoardPositions()[bestMoveCounterY][bestMoveCounterX].getCounter();
        Move moveToMake = new Move(bestMove.getBoardX(), bestMove.getBoardY(), counterToAssign);
        gameBoard.makeMove(moveToMake);

        /***
         evaluationBoard = new GameBoard(true);
         evaluationBoard.setUpEvalBoard(gameBoard.getPlayerCounters(), gameBoard.getAICounters());

         // Start evaluation
         evaluationBoard.startEvaluations(1);
         // Get the best move
         Move bestMove = evaluationBoard.getBestMove();
         gameBoard.makeMove(bestMove);
         **/

//        Move bestMove = simBoard.getBestMove();
//        int counterLocX = bestMove.getCounter().getBoardPosition().getX();
//        int counterLocY = bestMove.getCounter().getBoardPosition().getY();
//        System.out.println("\tBest move: " + bestMove);
//        Counter gameBoardCounter = gameBoard.getBoardPositions()[counterLocY][counterLocX].getCounter();
//        Move gameBoardMove = new Move(bestMove.getBoardX(), bestMove.getBoardY(), gameBoardCounter);
//
//        gameBoard.makeMove(bestMove);
//        gameBoard.hasAIWon();
    }

    private void makeRandomMove() {
        Random random = new Random();

        // Check if the AI is able to make a capture
        Vector<Move> validMoves = gameBoard.getValidAIMoves();
        for (Move validMove : validMoves) {
            if (validMove.isCaptureMove()) {
                gameBoard.makeMove(validMove);
                return;
            }
        }

        // If unable to make a capture, make a random move.
        int r = random.nextInt(validMoves.size());
        gameBoard.makeMove(validMoves.get(r));
    }

    private void AIPlay() {
        if (Options.difficulty == Difficulty.Random)
            makeRandomMove();
        if (Options.difficulty == Difficulty.minimax)
            makeMinimaxMove();
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
                    gameBoard.selectedCounterX = (int) counters.get(i).x;
                    gameBoard.selectedCounterY = (int) counters.get(i).y;
                    gameBoard.selectCounter(counters.get(i));
                }
            }
        } else {
            // Unselect a counter
            Counter counter = counters.get(selectedCounter);
            int[] boardPos = BoardPosition.getBoardPos(p);
            Move move = new Move(boardPos[0], boardPos[1], counter);
            if (gameBoard.isMoveValid(move)) {
                // Check for winner
                if (gameBoard.checkForWinner())
                    return;

                // Make AI move
                AIPlay();
                gameBoard.checkForWinner();
            }
            selectedCounter = -1;
        }

        lblInfo.setText(gameBoard.getUserMessage());
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
