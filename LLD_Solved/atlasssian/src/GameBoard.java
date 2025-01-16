class GameBoard{
    private int boardWidth;
    private int boardheight;

    public GameBoard(int boardheight, int boardWidth) {
        this.boardheight = boardheight;
        this.boardWidth = boardWidth;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardheight() {
        return boardheight;
    }
}