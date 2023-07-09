package teammeme.code;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    Element myelement;
    ArrayList<Bullet> bullets=new ArrayList<Bullet>();
    int thoigiannapdan=0; //bang 10 moi ban tiep duoc, tao do tre khi ban
//    Bullet viendan;
    ParallaxBackground background; //bien hinh nen chuyen dong
    private MainThread thread;
    private Bitmap bitmap;
    int mX;
    int mY;

    ArrayList<Enemies> enemies=new ArrayList<Enemies>();
    int thoigiankethu=0;//thoi gian ra ke thu, 10 se ra
    Enemies motkethu;


    public GamePanel(Context context) {
        super(context);
        background=new ParallaxBackground(this.getResources());
//        viendan = new Bullet(getResources(),0,0,R.drawable.lua);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hinhre);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes if needed
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        // Update game state here
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            background.doDrawRunning(canvas);
            thoigiannapdan++;
            thoigiankethu++;
            //canvas.drawBitmap(bitmap, mX, mY, null);

            if (myelement != null){
                myelement.doDraw(canvas);
                this.doDrawBullet(canvas);
                this.doDrawEnemies(canvas);
//                viendan.doDraw(canvas);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (myelement == null) {
            myelement = new Element(getResources(), (int) event.getX(), (int) event.getY());
            Log.d("abc", "khoi tao dau tien");
            return true;
        } else {
            myelement.mX = (int) event.getX() - myelement.bitmap.getWidth() / 2;
            myelement.mY = (int) event.getY() - myelement.bitmap.getHeight() / 2;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            myelement.mX = (int) event.getX() - myelement.bitmap.getWidth() / 2;
            myelement.mY = (int) event.getY() - myelement.bitmap.getHeight() / 2;
            Log.d("abc", "ddddddddddddddddddddddddddddown");
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            myelement.mX = (int) event.getX() - myelement.bitmap.getWidth() / 2;
            myelement.mY = (int) event.getY() - myelement.bitmap.getHeight() / 2;
            Log.d("abc", "uuuuuuuuuuuuuuuuuuuuuuuuuuuup");
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            myelement.mX = (int) event.getX() - myelement.bitmap.getWidth() / 2;
            myelement.mY = (int) event.getY() - myelement.bitmap.getHeight() / 2;
            Log.d("abc", "mmmmmmmmmmmmmmmmmmmmmmmmmmove");
        }

        return true;//super.onTouchEvent(event);

    }
    //ve tap hop cac vien dan
    public void doDrawBullet(Canvas canvas)
    {
        int rectLeft = 10;
        int rectTop = 30;
        int rectRight = rectLeft + (thoigiannapdan * 10);
        int rectBottom = 10;
        Paint p=new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(50);
        canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, p);
        canvas.drawText("Nạp đạn:"+thoigiannapdan*10, 70, 70,p);

        if(thoigiannapdan>=30)
        {
            thoigiannapdan=0;
            Bullet motviendan=
                    new Bullet(getResources(), myelement.mX, myelement.mY,R.drawable.lua);

            bullets.add(motviendan);
        }
        for(int i=0;i<bullets.size();i++)
            bullets.get(i).doDraw(canvas);
        for(int i=0;i<bullets.size();i++)
            if(bullets.get(i).x>canvas.getWidth())
                bullets.remove(i);

        Log.d("viendan", "sovien: "+bullets.size());
        
    }
    public void doDrawEnemies(Canvas canvas) {
        if(thoigiankethu>=10)
        {
            thoigiankethu=0;
            Enemies motkethu=new Enemies(getResources(),
                    canvas.getWidth(),canvas.getHeight());
            enemies.add(motkethu);
        }
        for(int i=0;i<enemies.size();i++)
            enemies.get(i).doDraw(canvas);

        for(int i=0;i<enemies.size();i++)
            if(enemies.get(i).y<0)
                enemies.remove(i);
        Log.d("viendan","so vien: "+enemies.size());

    }

}
