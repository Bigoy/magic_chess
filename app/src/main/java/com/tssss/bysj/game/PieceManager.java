package com.tssss.bysj.game;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
 * 棋子管理者。
 * 管理棋子的位置，身份。
 */
public class PieceManager {
    @SuppressLint("StaticFieldLeak")
    private static PieceManager pieceManager;

    static String SELF_A = "SELF_A";
    static String SELF_B = "SELF_B";
    static String SELF_C = "SELF_C";
    static String RIVAL_A = "RIVAL_A";
    static String RIVAL_B = "RIVAL_B";
    static String RIVAL_C = "RIVAL_C";

    private Piece selfA;
    private Piece selfB;
    private Piece selfC;
    private Piece rivalA;
    private Piece rivalB;
    private Piece rivalC;

    private Map<String, Piece> pieces;


    private PieceManager() {
        pieces = new HashMap<>();

        initPieces();
    }

    static PieceManager getChessmanManager() {
        if (pieceManager == null)
            pieceManager = new PieceManager();

        return pieceManager;
    }

    private void initPieces() {
        selfA = new GraphicPiece(SELF_A, Piece.PIECE_CAMP_SELF);
        selfB = new GraphicPiece(SELF_B, Piece.PIECE_CAMP_SELF);
        selfC = new GraphicPiece(SELF_C, Piece.PIECE_CAMP_SELF);
        rivalA = new GraphicPiece(RIVAL_A, Piece.PIECE_CAMP_RIVAL);
        rivalB = new GraphicPiece(RIVAL_B, Piece.PIECE_CAMP_RIVAL);
        rivalC = new GraphicPiece(RIVAL_C, Piece.PIECE_CAMP_RIVAL);

        pieces.put(SELF_A, selfA);
        pieces.put(SELF_B, selfB);
        pieces.put(SELF_C, selfC);
        pieces.put(RIVAL_A, rivalA);
        pieces.put(RIVAL_B, rivalB);
        pieces.put(RIVAL_C, rivalC);

//        Log.wtf(getClass().getSimpleName(), "<------ pieces initialized ------>");
    }

    /**
     * Initialize position of pieces.
     */
    void initPiecesPosition() {
        selfA.setAnchor(AnchorManager.getAnchorManager().getAnchor(AnchorManager.ONE));
        selfB.setAnchor(AnchorManager.getAnchorManager().getAnchor(AnchorManager.FOUR));
        selfC.setAnchor(AnchorManager.getAnchorManager().getAnchor(AnchorManager.SEVEN));

        rivalA.setAnchor(AnchorManager.getAnchorManager().getAnchor(AnchorManager.THREE));
        rivalB.setAnchor(AnchorManager.getAnchorManager().getAnchor(AnchorManager.SIX));
        rivalC.setAnchor(AnchorManager.getAnchorManager().getAnchor(AnchorManager.NINE));

//        Log.wtf(getClass().getSimpleName(), "<------ the position of pieces initialized ------>");
    }

    Piece getPiece(String key) {
        return pieces.get(key);
    }

    /*
    Drawing pieces uniformly.
     */
    void drawPieces(Canvas gameCanvas) {
        selfA.draw(gameCanvas);
        selfB.draw(gameCanvas);
        selfC.draw(gameCanvas);

        rivalA.draw(gameCanvas);
        rivalB.draw(gameCanvas);
        rivalC.draw(gameCanvas);
    }

    private Piece identify(Anchor anchor) {
        if (selfA.getAnchor().equals(anchor)) {

            return selfA;

        } else if (selfB.getAnchor().equals(anchor)) {

            return selfB;

        } else if (selfC.getAnchor().equals(anchor)) {

            return selfC;

        } else if (rivalA.getAnchor().equals(anchor)) {

            return rivalA;

        } else if (rivalB.getAnchor().equals(anchor)) {

            return rivalB;

        } else if (rivalC.getAnchor().equals(anchor)) {

            return rivalC;

        }

        return null;
    }

    /**
     * Update position of pieces.
     */
    void update(Anchor newAnchor) {
        whoChecked().getAnchor().setUsed(false);
        whoChecked().setAnchor(newAnchor);

        newAnchor.setUsed(true);

        // 更新的棋子位置上传到服务器。

    }

    /*
    Update army's pieces position, which are from the server.
     */
    public void updateArmyPosition(Piece piece, Anchor anchor) {
        piece.setAnchor(anchor);
    }

    /*
    Determine if any chessman have been selected.
     */
    boolean hasChessmanChecked() {
        if (selfA.getCheckedState()) {
            return true;

        } else if (selfB.getCheckedState()) {
            return true;

        } else if (selfC.getCheckedState()) {
            return true;

        } else if (rivalA.getCheckedState()) {
            return true;

        } else if (rivalB.getCheckedState()) {
            return true;

        } else return rivalC.getCheckedState();

    }

    /**
     * Return the key of checked chessman.
     */
    Piece whoChecked() {
        if (selfA.getCheckedState())
            return selfA;

        else if (selfB.getCheckedState())
            return selfB;

        else if (selfC.getCheckedState())
            return selfC;

        else if (rivalA.getCheckedState())
            return rivalA;

        else if (rivalB.getCheckedState())
            return rivalB;

        else if (rivalC.getCheckedState())
            return rivalC;

        return null;
    }

    /*
    选中一个棋子。
     */
    void checkChessman(int x, int y) {
        AnchorManager am = AnchorManager.getAnchorManager();
        Rule rule = new Rule();

        if (rule.canSelect(x, y)) {
            Objects.requireNonNull(identify(am.identifyAnchor(x, y))).setChecked(true);
        }
    }

    /**
     * Cancel selection.
     */
    void cancelSelection() {
        resetChessmenCheckedState();
    }

    /*
    重置所有棋子的选中状态为false。
     */
    void resetChessmenCheckedState() {
        selfA.setChecked(false);
        selfB.setChecked(false);
        selfC.setChecked(false);
        rivalA.setChecked(false);
        rivalB.setChecked(false);
        rivalC.setChecked(false);
    }
}

