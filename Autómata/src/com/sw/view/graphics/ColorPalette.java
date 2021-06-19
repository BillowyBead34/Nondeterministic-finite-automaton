package com.sw.view.graphics;

import com.sw.model.utils.Pair;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author HikingCarrot7
 */
public class ColorPalette {

    public static enum ColorKey {
        FILL_COLOR_KEY,
        STROKE_COLOR_KEY,
        TEXT_COLOR_KEY
    }

    public static class ColorPaletteBuilder {

        List<Pair<ColorKey, Color>> palette;

        public ColorPaletteBuilder() {
            palette = new ArrayList<>();
        }

        public ColorPaletteBuilder addColor(ColorKey key, Color color) {
            palette.add(new Pair<>(key, color));
            return this;
        }

        public ColorPalette build() {
            return new ColorPalette(palette.stream().collect(Collectors.toMap(Pair::getLeft, Pair::getRight)));
        }

    }

    private final Map<ColorKey, Color> colorCatalog;

    public ColorPalette(Map<ColorKey, Color> colors) {
        this.colorCatalog = colors;
    }

    public Color getSpecificColor(ColorKey key) {
        return colorCatalog.get(key);
    }

    public Map<ColorKey, Color> getColorCatalog() {
        return colorCatalog;
    }

}
