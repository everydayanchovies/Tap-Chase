package io.github.skulltah.colorseek.ZBHelpers;

public class Classes {
    public static class Colour {
        public float r;
        public float g;
        public float b;
        public float a;

        public Colour(float r, float g, float b, float a) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }

        public Colour(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = 1;
        }

        public Colour(int r, int g, int b, int a) {
            this.r = r / 255.0f;
            this.g = g / 255.0f;
            this.b = b / 255.0f;
            this.a = a / 255.0f;
        }

        public Colour(int r, int g, int b) {
            this.r = r / 255.0f;
            this.g = g / 255.0f;
            this.b = b / 255.0f;
            this.a = 1;
        }
    }
}
