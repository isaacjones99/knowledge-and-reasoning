package checkers.gui.board;

public class MovesAndScores {

    int score;
    Move move;

    public MovesAndScores(int score, Move move) {
        this.score = score;
        this.move = move;
    }

    public int getScore() {
        return score;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public String toString() {
        return "Score: " + score + ", move: " + move;
    }
}
