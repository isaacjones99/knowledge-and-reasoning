package checkers.gui.board;

import checkers.gui.GUI;
import checkers.gui.Options;
import checkers.gui.panels.MenuPanel;
import checkers.gui.shapes.Counter;
import checkers.gui.shapes.MovePointer;
import checkers.gui.shapes.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class GameBoard {

    private BoardPosition[][] boardPositions;

    // Board tiles array
    private Tile[][] boardTiles;

    // Counter arrays
    private Vector<Counter> playerCounters;
    private Vector<Counter> AICounters;
    private Vector<Counter> capturedCounters;

    public int selectedCounterX;
    public int selectedCounterY;

    private Vector<MovesAndScores> successorEvaluations;

    private String userMessage = "";
    private boolean containsCaptureMove = false;

    private Vector<MovePointer> movePointers;

    public GameBoard(boolean isEval) {
        movePointers = new Vector<>();
        boardPositions = new BoardPosition[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++)
                boardPositions[y][x] = new BoardPosition(x, y);
        }
        createGameBoard();
        if (!isEval) {
            createPlayerCounters();
            createAICounters();
        } else {
            successorEvaluations = new Vector<>();
        }
        capturedCounters = new Vector<>();
    }

    private void createGameBoard() {
        boardTiles = new Tile[8][8];

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                boardTiles[y][x] = new Tile(boardPositions[y][x], x, y);

                // Set the tiles colour
                Color color = Color.black;
                if ((x + y) % 2 == 0) color = Color.white;
                boardTiles[y][x].setColor(color);
            }
        }
    }

    private void createAICounters() {
        AICounters = new Vector<>();

        int x = 1;
        int y = 0;
        for (int i = 0; i < 12; i++) {
            BoardPosition bPos = boardPositions[y][x];
            Counter counter = new Counter(bPos, true);
            counter.setColor(Color.blue);
            AICounters.add(counter);

            if (i == 3) {
                y++;
                x = 0;
            } else if (i == 7) {
                y++;
                x = 1;
            } else x += 2;
        }
    }

    private void createPlayerCounters() {
        playerCounters = new Vector<>();

        int x = 0;
        int y = 7;
        for (int i = 0; i < 12; i++) {
            BoardPosition bPos = boardPositions[y][x];
            Counter counter = new Counter(bPos, false);
            counter.setColor(Color.red);
            playerCounters.add(counter);

            if (i == 3) {
                y--;
                x = 1;
            } else if (i == 7) {
                y--;
                x = 0;
            } else x += 2;
        }
    }

    public void setUpEvalBoard(Vector<Counter> playerCounters, Vector<Counter> AICounters) {
        this.playerCounters = new Vector<>();
        this.AICounters = new Vector<>();

        for (int i = 0; i < playerCounters.size(); i++) {
            Counter eCounter = playerCounters.get(i);
            boolean isAI = eCounter.getIsAI();
            int counterX = eCounter.getBoardPosition().getX();
            int counterY = eCounter.getBoardPosition().getY();
            BoardPosition bp = boardPositions[counterY][counterX];
            this.playerCounters.add(new Counter(bp, isAI));
        }

        for (int i = 0; i < AICounters.size(); i++) {
            Counter eCounter = AICounters.get(i);
            boolean isAI = eCounter.getIsAI();
            int counterX = eCounter.getBoardPosition().getX();
            int counterY = eCounter.getBoardPosition().getY();
            BoardPosition bp = boardPositions[counterY][counterX];
            this.AICounters.add(new Counter(bp, isAI));
        }
    }

    public Move getBestMove() {
        System.out.println("Successor evaluations size: " + successorEvaluations.size());

        int max = -1;
        int best = 0;
        for (int i = 0; i < successorEvaluations.size(); i++) {
            if (max < successorEvaluations.get(i).score) {
                max = successorEvaluations.get(i).score;
                best = i;
            }
        }
        return successorEvaluations.get(best).move;
    }

    public void startEvaluations(int player) {
        System.out.println("*** STARTING EVALUATIONS ***");

        successorEvaluations = new Vector<>();
        minimax(1,  player);

        // Print out the successor evaluations list
        System.out.println("\n\t* SUCCESSOR EVALUATIONS *");
        for (int i = 0; i < successorEvaluations.size(); i++) {
            System.out.println("\t\t- " + successorEvaluations.get(i));
        }
    }

//    Vector<Counter> replaceCounters = new Vector<>();
    int maxDepth = 2;
    public int minimax(int depth, int player) {
        // Player 1  = AI
        // Player -1 = Human

        // Terminal tests
        if (player == 1 && hasAIWon())
            return 1;
        if (player == 1 && hasHumanWon())
            return -1;
        if (player == -1 && hasAIWon())
            return -1;
        if (player == -1 && hasHumanWon())
            return 1;

        // TODO - Check for a draw (if there are no more available moves)

        int bestScore;
        if (player == 1)
            bestScore = -1;
        else
            bestScore = 1;

        Move moveToMake = null;
        if (player == 1) {
            Vector<Move> availableMoves = getValidAIMoves();
            for (int i = 0; i < availableMoves.size(); i++) {
                // Make move
                moveToMake = availableMoves.get(i);
                Move reverseMove = new Move(moveToMake.getCounter().getBoardPosition().getX(), moveToMake.getCounter().getBoardPosition().getY(), moveToMake.getCounter());
                makeMove(moveToMake);

                int currentScore = 0;
                if (depth != maxDepth) {
                    currentScore = minimax(depth + 1, player * -1);
                }
                bestScore = Math.max(bestScore, currentScore);

                // Reverse move
                makeMove(reverseMove);
            }
        } else {
            Vector<Move> availableMoves = getValidPlayerMoves();
            for (int i = 0; i < availableMoves.size(); i++) {
                // Make move
                moveToMake = availableMoves.get(i);
                Move reverseMove = new Move(moveToMake.getCounter().getBoardPosition().getX(), moveToMake.getCounter().getBoardPosition().getY(), moveToMake.getCounter());
                makeMove(moveToMake);


                int currentScore = 0;
                if (depth != maxDepth) {
                    currentScore = minimax(depth + 1, player * -1);
                }
                bestScore = Math.min(bestScore, currentScore);

                // Reverse move
                makeMove(reverseMove);
            }
        }

        if (depth == 0) {
            System.out.println("DEPTH = 0");
            successorEvaluations.add(new MovesAndScores(bestScore, moveToMake));
        }

        System.out.println("Returning best score");
        return bestScore;
    }

    public void removeCounter(Counter counter) {
        int counterX = counter.getBoardPosition().getX();
        int counterY = counter.getBoardPosition().getY();
        boardPositions[counterY][counterX].removeCounter();

        if (counter.isAI)
            AICounters.remove(counter);
        else
            playerCounters.remove(counter);
    }

    private void addCounter(Counter counter) {
        if (counter.isAI) {
            AICounters.add(counter);
        } else {
            playerCounters.add(counter);
        }
    }

    public boolean checkForWinner() {
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
            JOptionPane.showMessageDialog(null, message);
            GUI.changePanel(new MenuPanel());
        }

        return winner;
    }

    public boolean hasHumanWon() {
        // Check if all AI counters have been captured
        if (getAICounters().size() == 0)
            return true;

        // Check if AI has no valid moves
        Vector<Counter> AICounters = getAICounters();
        Vector<Move> validMoves = new Vector<>();

        for (Counter counter : AICounters)
            validMoves.addAll(getValidMoves(counter));
        if (validMoves.isEmpty())
            return true;

        return false;
    }

    public boolean hasAIWon() {
        if (getPlayerCounters().isEmpty())
            return true;

        // Check if player has no valid moves
        Vector<Counter> playerCounters = getPlayerCounters();
        Vector<Move> validMoves = new Vector<>();
        for (Counter counter : playerCounters)
            validMoves.addAll(getValidMoves(counter));
        if (validMoves.isEmpty())
            return true;

        return false;
    }

    public void selectCounter(Counter counter) {
        // Get the counters valid moves
        Vector<Move> validMoves = getValidMoves(counter);
        for (int i = 0; i < validMoves.size(); i++) {
            movePointers.add(new MovePointer(boardPositions[validMoves.get(i).getBoardY()][validMoves.get(i).getBoardX()]));
        }
    }

    public boolean isMoveValid(Move move) {
        boolean isValid = false;
        Counter counter = move.getCounter();

        Vector<Move> validMoves = getValidPlayerMoves(); // getValidMoves(counter);
        for (int i = 0; i < validMoves.size(); i++) {
            if (validMoves.get(i).equals(move)) {
                isValid = true;
                if (validMoves.get(i).isCaptureMove()) move.setCaptureMove(true, validMoves.get(i).getCapturedCounter());
            }
        }

        if (isValid) {
            makeMove(move);
        } else {
            // Move counter back to original location
            counter.x = selectedCounterX;
            counter.y = selectedCounterY;
            movePointers.removeAllElements();

            if (!move.isCaptureMove && containsCaptureMove)
                userMessage = "Move invalid - You must make a capture.";
            else
                userMessage = "Move invalid.";
        }
        containsCaptureMove = false;
        return isValid;
    }

    public Vector<Move> getValidMoves(Counter counter) {
        Vector<Move> validMoves = new Vector<>();

        int boardX = counter.getBoardPosition().getX();
        int boardY = counter.getBoardPosition().getY();
        int yDir = counter.getYDir();

        if (counter.getIsKing()) {
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

    public Vector<Move> getValidAIMoves() {
        Vector<Counter> AICounters = getAICounters();
        Vector<Move> validMoves = new Vector<>();

        for (Counter counter : AICounters)
            validMoves.addAll(getValidMoves(counter));
        return validMoves;
    }

    private Vector<Move> getValidPlayerMoves() {
        Vector<Counter> playerCounters = getPlayerCounters();
        Vector<Move> validMoves = new Vector<>();

        for (Counter counter : playerCounters)
            validMoves.addAll(getValidMoves(counter));

        // Remove any non-capture moves
        System.out.println("REMOVING CAPTURE MOVES");
        Vector<Move> toBeRemoved = new Vector<>();

        for (int i = 0; i < validMoves.size(); i++) {
            Move m = validMoves.get(i);
            if (!m.isCaptureMove()) {
                toBeRemoved.add(m);
                System.out.println("\tRemoving: " + m + ". For counter: " + m.getCounter());
            } else {
                containsCaptureMove = true;
            }
        }

        if (containsCaptureMove && Options.forceCapture) {
            validMoves.removeAll(toBeRemoved);
            toBeRemoved.removeAllElements();
        }

        return validMoves;
    }

    public void makeMove(Move move) {
        userMessage = "Making move - " + move;
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

            removeCounter(capCounter);
        }
        movePointers.removeAllElements();
    }

    public void render(Graphics2D g2d) {
        // Render tiles
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Tile tile = boardTiles[y][x];
                g2d.setColor(tile.color);
                g2d.fill(tile);
            }
        }

        // Render move pointers
        if (Options.showMoves) {
            for (int i = 0; i < movePointers.size(); i++) {
                g2d.setColor(movePointers.get(i).getColor());
                g2d.fill(movePointers.get(i));
            }
        }

        // Render counters
        Vector<Counter> allCounters = new Vector<>();
        allCounters.addAll(AICounters);
        allCounters.addAll(playerCounters);

        for (Counter counter : allCounters) {
            g2d.setColor(counter.color);
            g2d.fill(counter);

            if (counter.getIsKing()) {
                g2d.setColor(new Color(217, 174, 48));
                g2d.draw(counter);
            }
        }
    }

    public Vector<Counter> getPlayerCounters() { return playerCounters; }
    public Vector<Counter> getAICounters() { return AICounters; }
    public Tile[][] getBoardTiles() {
        return boardTiles;
    }

    public BoardPosition[][] getBoardPositions() {
        return boardPositions;
    }

    public String getUserMessage() {
        return userMessage;
    }

    @Override
    public String toString() {
        return "Game board - Player counters: " + playerCounters.size() + ", AI counters: " + AICounters.size();
    }
}
