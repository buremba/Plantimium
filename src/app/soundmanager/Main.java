package app.soundmanager;
import app.soundmanager.Game;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class Main {
        public static void main (String[] args) {
               new JoglApplication(new Game(), "Game", 640, 480, false);
        }
}