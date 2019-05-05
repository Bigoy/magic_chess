package com.tssss.bysj.game.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.game.core.GamePresenter;
import com.tssss.bysj.game.core.other.AnchorManager;
import com.tssss.bysj.game.core.other.Chessboard;
import com.tssss.bysj.game.core.other.ChessmanManager;
import com.tssss.bysj.game.core.other.GameManager;
import com.tssss.bysj.game.core.other.GameResult;
import com.tssss.bysj.game.core.other.GameRoleManager;
import com.tssss.bysj.game.core.other.GameUtil;
import com.tssss.bysj.game.core.other.Rule;
import com.tssss.bysj.game.core.other.Umpire;
import com.tssss.bysj.other.Logger;

import java.util.HashMap;
import java.util.Map;


public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public boolean isDrawing = false;

    private Canvas gameCanvas;
    private SurfaceHolder gameHolder;
    private boolean canTouch = false;

    private AnchorManager am;
    private ChessmanManager cm;
    private Chessboard chessboard;
    private GameRoleManager pm;
    private Umpire umpire;
    private GameUtil gameUtil;
    private GameManager gm;
    private Map<String, String> chessmanStateMap;
    private GamePresenter gamePresenter;

    public void setGamePresenter(GamePresenter gamePresenter) {
        this.gamePresenter = gamePresenter;

    }

    public void setCanTouch(boolean canTouch) {
        this.canTouch = canTouch;
    }

    public GameSurfaceView(Context context) {
        super(context);
        init();
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredSurfaceSize = MeasureSpec.getSize(widthMeasureSpec);
        gameUtil.setSurfaceSize(measuredSurfaceSize);
        am.createAnchors();
        setMeasuredDimension(measuredSurfaceSize, measuredSurfaceSize);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        // 开启绘制线程。
        Thread drawThread = new Thread(this);
        drawThread.setPriority(Thread.MAX_PRIORITY);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Logger.log("GameSurfaceView changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopGame();
        Logger.log("surfaceView destroyed");
    }

    public void stopGame() {
        canTouch = false;
        isDrawing = false;
    }

    public void touchTrue() {
        canTouch = true;

    }

    public void touchFalse() {
        canTouch = false;

    }

    @Override
    public void run() {
        gm.prepare(am, cm, pm);
        // 绘制。
        while (isDrawing) {
            try {
                mainDraw();
                Log.i("GameSurfaceView", "drawing");
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化GameSurfaceView
     */
    private void init() {
        gameHolder = getHolder();
        gameHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setZOrderOnTop(true);
        gameHolder.setFormat(PixelFormat.TRANSLUCENT);
        gameUtil = GameUtil.getGameUtil();
        am = AnchorManager.getAnchorManager();
        cm = ChessmanManager.getChessmanManager();
        pm = GameRoleManager.getGameRoleManager();
        umpire = new Umpire();
        chessboard = new Chessboard();
        gm = new GameManager();
        chessmanStateMap = new HashMap<>();
        gameUtil.setContext(getContext());
    }

    /*
    总绘制方法。
     */
    private void mainDraw() {
        try {
            gameCanvas = gameHolder.lockCanvas();
            clear(gameCanvas);
            chessboard.draw(gameCanvas);
            cm.drawChessmen(gameCanvas);
            if (cm.whoChecked() != null) {
                cm.drawMark(gameCanvas, cm.whoChecked());
            }
        } finally {
            if (gameCanvas != null) {
                gameHolder.unlockCanvasAndPost(gameCanvas);
            }
        }
    }

    /**
     * 清空画布
     */
    private void clear(Canvas gameCanvas) {
        Paint paint = new Paint();
        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        paint.setXfermode(xfermode);
        gameCanvas.drawPaint(paint);
    }

    /**
     * 处理用户的触摸事件
     */
    public void doTouch(MotionEvent event) {
        if (canTouch) {
            ChessmanManager cm = ChessmanManager.getChessmanManager();
            AnchorManager am = AnchorManager.getAnchorManager();
            Rule rule = Rule.getRule();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (cm.hasChessmanChecked()) {
                        // 用户选中了棋子，准备移动。
                        if (rule.canMoveChessman(event)) {
                            String chessman_index = cm.whoChecked();
                            cm.drawMark(gameCanvas, cm.whoChecked());
                            cm.update(event, cm.whoChecked(), am.identifyAnchor((int) event.getX(), (int) event.getY()));
                            canTouch = false;
                            gamePresenter.cancelAndResetTimer();
                            // 判断当前操作后是否有游戏结果
                            String result = umpire.umpire();
                            if (GameResult.COMPETING.equals(result)) {
                                // 没有游戏结果，交换下棋权
                                String update_chessman_position = cm.getChessman(chessman_index).getPosition();
                                chessmanStateMap.put("operation", "update");
                                chessmanStateMap.put("chessman_index", chessman_index);
                                chessmanStateMap.put("chessman_position", update_chessman_position);
                                String updateData = JSON.toJSONString(chessmanStateMap);
                                gamePresenter.sendMessage(updateData);
                                cm.resetChessmenCheckedState();
                                chessmanStateMap.put("operation", "turn");
                                gamePresenter.sendMessage(JSON.toJSONString(chessmanStateMap));

                            } else if (GameResult.WIN.equals(result)) {
                                // 游戏胜利
                                umpire.win();

                            } else if (GameResult.LOSE.equals(result)) {
                                // 游戏失败
                                umpire.lose();

                            }
                        } else {
                            // 用户准备选中某一个棋子。
                            cm.checkChessman(event);
                        }
                        break;
                    }
            }
        }
    }

    /**
     * 同步双方的棋子位置
     */
    public void syncChessmen(String chessmanKey, String position) {
        cm.updateArmyPosition(chessmanKey, position);
    }

}

