package com.celoron.test;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class Main {
        public static void main(String[] args) {
                new JoglApplication(new MyGame(), "My Game", 480, 320, false);
        }
}