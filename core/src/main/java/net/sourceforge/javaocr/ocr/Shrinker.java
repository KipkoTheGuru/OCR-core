package net.sourceforge.javaocr.ocr;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageShrinker;

/**
 * shrink wrap given image
 * TODO: not sure whether this have to be full blown class, if somebody has better ideas -speak up
 */
public class Shrinker implements ImageShrinker {

    final int empty;

    public Shrinker(int empty) {
        this.empty = empty;

    }

    public Image shrink(Image source) {
        int originX;
        int originY;
        boolean spanEmpty;
        for (originX = 0; originX < source.getWidth(); originX++) {
            source.iterateV(originX);
            if (!spanEmpty(source))
                break;
        }
        for (originY = 0; originY < source.getHeight(); originY++) {
            source.iterateH(originY);
            if (!spanEmpty(source))
                break;
        }

        int borderX;
        for (borderX = source.getWidth() - 1; borderX > originX; borderX--) {
            source.iterateV(borderX);
             if (!spanEmpty(source))
                break;
        }
        int borderY;
        for (borderY = source.getHeight() - 1; borderY > originY; borderY--) {
            source.iterateH(borderY);
             if (!spanEmpty(source))
                break;
        }
       
        return source.chisel(originX, originY, borderX + 1 - originX, borderY + 1 - originY);
    }

    private boolean spanEmpty(Image source) {
        boolean spanEmpty = true;
        while (source.hasNext())
            if (source.next() != empty) {
                spanEmpty = false;
                break;
            }
        return spanEmpty;
    }
}
