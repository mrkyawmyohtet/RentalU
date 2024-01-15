package com.example.rentalu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 3000;

    Animation top, bot, heartbeat;
    ImageView imgView;
    TextView logo, slogan, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //animations
        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bot = AnimationUtils.loadAnimation(this, R.anim.bot_animation);
        heartbeat = AnimationUtils.loadAnimation(this, R.anim.heartbeat_animation);

        //Elements
        imgView = (ImageView) findViewById(R.id.imageView);
        logo = (TextView) findViewById(R.id.Logo);
        slogan = (TextView) findViewById(R.id.slogan);
        btnLogin = (TextView) findViewById(R.id.btnLogin);

        imgView.setAnimation(top);
        logo.setAnimation(bot);
        slogan.setAnimation(bot);

        //adding two animation for the login button
        AnimationSet AS = new AnimationSet(false);
        AS.addAnimation(bot);
        AS.addAnimation(heartbeat);
        btnLogin.setAnimation(AS);

        //job of btnLogin
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, login_page.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}